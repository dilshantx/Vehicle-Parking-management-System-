package view;

import model.ParkingModel;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class AvailableSlotsView {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color TEXT_COLOR = new Color(51, 51, 51);
    private static final Color CARD_COLOR = new Color(255, 255, 255);
    private static final Color AVAILABLE_SLOT_COLOR = new Color(46, 204, 113);

    public AvailableSlotsView() {
        ParkingModel parkingModel = new ParkingModel();

        JFrame frame = new JFrame("Available Parking Slots");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(20, 20));
        frame.getContentPane().setBackground(BACKGROUND_COLOR);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Available Parking Slots");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Stats Panel
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        statsPanel.setOpaque(false);

        List<Integer> availableSlots = parkingModel.getAvailableSlots();
        JLabel statsLabel = new JLabel("Total Available: " + availableSlots.size());
        statsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statsLabel.setForeground(Color.WHITE);
        statsPanel.add(statsLabel);

        headerPanel.add(statsPanel, BorderLayout.EAST);

        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create custom cell renderer for the list
        DefaultListModel<Integer> listModel = new DefaultListModel<>();
        availableSlots.forEach(listModel::addElement);

        JList<Integer> slotsList = new JList<>(listModel);
        slotsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//        slotsList.setVisualRowCount(-1);
        slotsList.setFixedCellWidth(150);
        slotsList.setFixedCellHeight(100);
        slotsList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JPanel cellPanel = new JPanel(new BorderLayout());
            cellPanel.setBackground(CARD_COLOR);
            cellPanel.setBorder(BorderFactory.createCompoundBorder(
                    new EmptyBorder(5, 5, 5, 5),
                    BorderFactory.createLineBorder(AVAILABLE_SLOT_COLOR, 2, true)
            ));

            JLabel slotLabel = new JLabel("SLOT " + value);
            slotLabel.setHorizontalAlignment(SwingConstants.CENTER);
            slotLabel.setFont(new Font("Arial", Font.BOLD, 16));
            slotLabel.setForeground(TEXT_COLOR);

            JLabel statusLabel = new JLabel("AVAILABLE");
            statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
            statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            statusLabel.setForeground(AVAILABLE_SLOT_COLOR);

            cellPanel.add(slotLabel, BorderLayout.CENTER);
            cellPanel.add(statusLabel, BorderLayout.SOUTH);

            return cellPanel;
        });

        JScrollPane scrollPane = new JScrollPane(slotsList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(BACKGROUND_COLOR);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
