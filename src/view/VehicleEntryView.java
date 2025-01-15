package view;

import controller.ParkingController;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VehicleEntryView {
    // Define consistent colors matching the dashboard
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color TEXT_COLOR = new Color(51, 51, 51);
    private static final Color HOVER_COLOR = new Color(60, 110, 160);

    private final JFrame frame;
    private final ParkingController parkingController;

    public VehicleEntryView() {
        parkingController = new ParkingController();
        frame = new JFrame("Vehicle Entry Registration");
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setBackground(BACKGROUND_COLOR);

        // Main container with padding
        JPanel mainContainer = new JPanel(new BorderLayout(20, 20));
        mainContainer.setBackground(BACKGROUND_COLOR);
        mainContainer.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Add components
        mainContainer.add(createHeaderPanel(), BorderLayout.NORTH);
        mainContainer.add(createFormPanel(), BorderLayout.CENTER);
        mainContainer.add(createButtonPanel(), BorderLayout.SOUTH);

        frame.add(mainContainer);
        frame.setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel("Register New Vehicle");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);

        JLabel subtitleLabel = new JLabel("Enter vehicle details to register entry");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_COLOR);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(BACKGROUND_COLOR);
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 4, 8, 4);

        // Vehicle Number
        JTextField vehicleNumberField = createStyledTextField();
        addFormRow(formPanel, "Vehicle Number:", vehicleNumberField, gbc, 0);

        // Vehicle Type
        String[] vehicleTypes = {"Car", "Bike", "Truck"};
        JComboBox<String> vehicleTypeCombo = createStyledComboBox(vehicleTypes);
        addFormRow(formPanel, "Vehicle Type:", vehicleTypeCombo, gbc, 1);

        // Slot Number
        JComboBox<Integer> slotNumberCombo = createStyledComboBox(new Integer[]{});
        refreshAvailableSlots(slotNumberCombo);
        addFormRow(formPanel, "Available Slots:", slotNumberCombo, gbc, 2);

        // Entry Time
        JTextField entryTimeField = createStyledTextField();
        entryTimeField.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        entryTimeField.setEditable(false);
        entryTimeField.setBackground(new Color(240, 240, 240));
        addFormRow(formPanel, "Entry Time:", entryTimeField, gbc, 3);

        // Add action listener for form submission
        JButton submitButton = createStyledButton("Register Entry");
        submitButton.addActionListener(e -> handleSubmission(vehicleNumberField, vehicleTypeCombo, slotNumberCombo, entryTimeField));

        gbc.gridy = 4;
        gbc.gridx = 1;
        gbc.insets = new Insets(20, 4, 8, 4);
        formPanel.add(submitButton, gbc);

        return formPanel;
    }

    private void addFormRow(JPanel panel, String labelText, JComponent component, GridBagConstraints gbc, int row) {
        gbc.gridy = row;

        // Label
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
        panel.add(label, gbc);

        // Component
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(component, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton cancelButton = createStyledButton("Cancel");
        cancelButton.setBackground(new Color(180, 180, 180));
        cancelButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(cancelButton);
        return buttonPanel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        return field;
    }

    private <T> JComboBox<T> createStyledComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setPreferredSize(new Dimension(200, 35));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        return comboBox;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                super.paintComponent(g);
                g2.dispose();
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private void handleSubmission(JTextField vehicleNumberField, JComboBox<String> vehicleTypeCombo,
                                  JComboBox<Integer> slotNumberCombo, JTextField entryTimeField) {
        String vehicleNumber = vehicleNumberField.getText().trim();
        String vehicleType = (String) vehicleTypeCombo.getSelectedItem();
        Integer slotNumber = (Integer) slotNumberCombo.getSelectedItem();
        String entryTime = entryTimeField.getText();

        if (vehicleNumber.isEmpty()) {
            showError("Vehicle number is required!");
            return;
        }

        if (parkingController.registerVehicle(vehicleNumber, vehicleType, slotNumber, entryTime)) {
            showSuccess("Vehicle registered successfully!");
            frame.dispose();
        } else {
            showError("Failed to register vehicle.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshAvailableSlots(JComboBox<Integer> slotNumberCombo) {
        List<Integer> availableSlots = parkingController.getAvailableSlotsFill();
        slotNumberCombo.removeAllItems();
        for (Integer slot : availableSlots) {
            slotNumberCombo.addItem(slot);
        }
    }
}
