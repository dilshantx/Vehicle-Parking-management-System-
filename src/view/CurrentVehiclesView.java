package view;

import controller.ParkingController;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CurrentVehiclesView {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color TEXT_COLOR = new Color(51, 51, 51);
    private static final Color TABLE_HEADER_COLOR = new Color(240, 240, 240);
    private static final Color TABLE_ALTERNATE_COLOR = new Color(248, 250, 252);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    // Rest of the code remains exactly the same...
    public CurrentVehiclesView() {
        ParkingController parkingController = new ParkingController();

        JFrame frame = new JFrame("Currently Parked Vehicles");
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(20, 20));
        frame.getContentPane().setBackground(BACKGROUND_COLOR);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Currently Parked Vehicles");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        JTextField searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBackground(BACKGROUND_COLOR);
        tablePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Get data and create table
        List<String[]> currentVehicles = parkingController.getCurrentParkingVehicles();
        String[] columnNames = {"Vehicle Number", "Vehicle Type", "Slot Number", "Entry Time", "Duration"};

        // Process data to include duration
        String[][] data = new String[currentVehicles.size()][5];
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < currentVehicles.size(); i++) {
            String[] vehicle = currentVehicles.get(i);
            data[i][0] = vehicle[0]; // Vehicle Number
            data[i][1] = vehicle[1]; // Vehicle Type
            data[i][2] = vehicle[2]; // Slot Number
            data[i][3] = vehicle[3]; // Entry Time

            // Calculate duration
            LocalDateTime entryTime = LocalDateTime.parse(vehicle[3], TIME_FORMATTER);
            Duration duration = Duration.between(entryTime, now);
            data[i][4] = formatDuration(duration);
        }

        // Create custom table model with auto-updating duration
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionBackground(PRIMARY_COLOR.brighter());
        table.setSelectionForeground(Color.WHITE);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Auto-update duration every minute
        Timer timer = new Timer(60000, e -> {
            LocalDateTime currentTime = LocalDateTime.now();
            for (int i = 0; i < model.getRowCount(); i++) {
                String entryTimeStr = (String) model.getValueAt(i, 3);
                LocalDateTime entryTime = LocalDateTime.parse(entryTimeStr, TIME_FORMATTER);
                Duration duration = Duration.between(entryTime, currentTime);
                model.setValueAt(formatDuration(duration), i, 4);
            }
        });
        timer.start();

        // Custom header renderer
        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBorder(BorderFactory.createEmptyBorder());
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        // Custom cell renderer for alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : TABLE_ALTERNATE_COLOR);
                }
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return c;
            }
        });

        // Add sorting capability
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Add search functionality
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String search = searchField.getText().toLowerCase();
                sorter.setRowFilter(new RowFilter<TableModel, Integer>() {
                    @Override
                    public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                        for (int i = 0; i < entry.getValueCount(); i++) {
                            if (entry.getStringValue(i).toLowerCase().contains(search)) {
                                return true;
                            }
                        }
                        return false;
                    }
                });
            }
        });

        // Create scroll pane with custom styling
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Status Panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(BACKGROUND_COLOR);
        JLabel totalVehicles = new JLabel("Total Vehicles: " + currentVehicles.size());
        totalVehicles.setFont(new Font("Arial", Font.BOLD, 14));
        totalVehicles.setForeground(TEXT_COLOR);
        statusPanel.add(totalVehicles);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(statusPanel, BorderLayout.SOUTH);

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();

        if (hours > 0) {
            return String.format("%dh %dm", hours, minutes);
        } else {
            return String.format("%dm", minutes);
        }
    }
}
