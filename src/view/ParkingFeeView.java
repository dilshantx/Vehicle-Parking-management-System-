package view;

import controller.ParkingController;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class ParkingFeeView {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color TEXT_COLOR = new Color(51, 51, 51);
    private static final Color SUCCESS_COLOR = new Color(53, 192, 255);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ParkingFeeView() {
        ParkingController parkingController = new ParkingController();

        JFrame frame = new JFrame("Parking Fee Calculation");
        frame.setSize(800, 800);  // Increased height for new fields
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(BACKGROUND_COLOR);

        // Main container with padding
        JPanel mainContainer = new JPanel(new BorderLayout(20, 20));
        mainContainer.setBackground(BACKGROUND_COLOR);
        mainContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Parking Fee Calculator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(CARD_BACKGROUND);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true)
        ));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(CARD_BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Slot Number Input
        JLabel slotNumberLabel = new JLabel("Slot Number:");
        slotNumberLabel.setFont(new Font("Arial", Font.BOLD, 14));
        slotNumberLabel.setForeground(TEXT_COLOR);

        JTextField slotNumberField = createStyledTextField();
        slotNumberField.setPreferredSize(new Dimension(200, 35));

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(slotNumberLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(slotNumberField, gbc);

        // Vehicle Details Panel
        JPanel vehicleDetailsPanel = new JPanel(new GridBagLayout());
        vehicleDetailsPanel.setBackground(CARD_BACKGROUND);
        vehicleDetailsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Vehicle Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
        ));

        // Vehicle Number
        JLabel vehicleNumberLabel = new JLabel("Vehicle Number:");
        JTextField vehicleNumberField = createStyledTextField();
        vehicleNumberField.setEditable(false);
        vehicleNumberField.setBackground(new Color(240, 240, 240));

        // Vehicle Type
        JLabel vehicleTypeLabel = new JLabel("Vehicle Type:");
        JTextField vehicleTypeField = createStyledTextField();
        vehicleTypeField.setEditable(false);
        vehicleTypeField.setBackground(new Color(240, 240, 240));

        // Entry Time
        JLabel entryTimeLabel = new JLabel("Entry Time:");
        JTextField entryTimeField = createStyledTextField();
        entryTimeField.setEditable(false);
        entryTimeField.setBackground(new Color(240, 240, 240));

        // Exit Time (Current Time)
        JLabel exitTimeLabel = new JLabel("Exit Time:");
        JTextField exitTimeField = createStyledTextField();
        exitTimeField.setEditable(false);
        exitTimeField.setBackground(new Color(240, 240, 240));

        // Duration
        JLabel durationLabel = new JLabel("Parking Duration:");
        JTextField durationField = createStyledTextField();
        durationField.setEditable(false);
        durationField.setBackground(new Color(240, 240, 240));

        // Fee Display
        JLabel feeLabel = new JLabel("Calculated Fee:");
        JTextField feeField = createStyledTextField();
        feeField.setEditable(false);
        feeField.setBackground(new Color(240, 240, 240));

        // Add components to vehicle details panel
        gbc.gridx = 0; gbc.gridy = 0;
        vehicleDetailsPanel.add(vehicleNumberLabel, gbc);
        gbc.gridx = 1;
        vehicleDetailsPanel.add(vehicleNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        vehicleDetailsPanel.add(vehicleTypeLabel, gbc);
        gbc.gridx = 1;
        vehicleDetailsPanel.add(vehicleTypeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        vehicleDetailsPanel.add(entryTimeLabel, gbc);
        gbc.gridx = 1;
        vehicleDetailsPanel.add(entryTimeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        vehicleDetailsPanel.add(exitTimeLabel, gbc);
        gbc.gridx = 1;
        vehicleDetailsPanel.add(exitTimeField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        vehicleDetailsPanel.add(durationLabel, gbc);
        gbc.gridx = 1;
        vehicleDetailsPanel.add(durationField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        vehicleDetailsPanel.add(feeLabel, gbc);
        gbc.gridx = 1;
        vehicleDetailsPanel.add(feeField, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(CARD_BACKGROUND);

        JButton calculateFeeButton = createStyledButton("Calculate Fee", PRIMARY_COLOR);
        JButton releaseSlotButton = createStyledButton("Release Slot", SUCCESS_COLOR);
        releaseSlotButton.setEnabled(false);
        JButton printBillButton = createStyledButton("Print Bill", PRIMARY_COLOR);

        buttonPanel.add(calculateFeeButton);
        buttonPanel.add(releaseSlotButton);
        buttonPanel.add(printBillButton);

        // Add components to content panel
        contentPanel.add(inputPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(vehicleDetailsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(buttonPanel);

        // Add action listeners
        calculateFeeButton.addActionListener(e -> {
            try {
                int slotNumber = Integer.parseInt(slotNumberField.getText());
                double fee = parkingController.calculateFee(slotNumber);

                // Fetch current parking vehicles to get details
                List<String[]> currentVehicles = parkingController.getCurrentParkingVehicles();
                boolean vehicleFound = false;

                for (String[] vehicle : currentVehicles) {
                    if (Integer.parseInt(vehicle[2]) == slotNumber) {
                        vehicleNumberField.setText(vehicle[0]);
                        vehicleTypeField.setText(vehicle[1]);
                        entryTimeField.setText(vehicle[3]);

                        // Set exit time (current time)
                        LocalDateTime currentTime = LocalDateTime.now();
                        String formattedCurrentTime = currentTime.format(DATE_TIME_FORMATTER);
                        exitTimeField.setText(formattedCurrentTime);

                        // Calculate and display duration
                        LocalDateTime entryTime = LocalDateTime.parse(vehicle[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.S]"));
                        Duration parkingDuration = Duration.between(entryTime, currentTime);

                        long hours = parkingDuration.toHours();
                        long minutes = parkingDuration.toMinutesPart();
                        durationField.setText(String.format("%d hours, %d minutes", hours, minutes));

                        vehicleFound = true;
                        break;
                    }
                }

                if (fee >= 0 && vehicleFound) {
                    feeField.setText(String.format("LKR%.2f", fee));
                    releaseSlotButton.setEnabled(true);
                    feeField.setBackground(new Color(240, 255, 240));
                } else {
                    showError(frame, "Invalid slot number or calculation error.");
                    releaseSlotButton.setEnabled(false);
                    feeField.setBackground(new Color(255, 240, 240));
                    // Clear all fields
                    clearFields(vehicleNumberField, vehicleTypeField, entryTimeField, exitTimeField, durationField, feeField);
                }
            } catch (NumberFormatException ex) {
                showError(frame, "Please enter a valid slot number.");
                clearFields(vehicleNumberField, vehicleTypeField, entryTimeField, exitTimeField, durationField, feeField);
            }
        });

        releaseSlotButton.addActionListener(e -> {
            try {
                int slotNumber = Integer.parseInt(slotNumberField.getText());
                double fee = Double.parseDouble(feeField.getText().replace("LKR", ""));

                if (parkingController.releaseSlot(slotNumber, fee)) {
                    showSuccess(frame, "Slot released successfully!");
                    // Clear all fields
                    clearFields(slotNumberField, vehicleNumberField, vehicleTypeField, entryTimeField,
                            exitTimeField, durationField, feeField);
                    releaseSlotButton.setEnabled(false);
                } else {
                    showError(frame, "Failed to release slot. Please check the slot number.");
                }
            } catch (NumberFormatException ex) {
                showError(frame, "Please calculate the fee before releasing the slot.");
            }
        });

        printBillButton.addActionListener(e -> {
            // Display the bill in a popup
            String bill = generateBill(vehicleNumberField.getText(), vehicleTypeField.getText(),
                    entryTimeField.getText(), exitTimeField.getText(), durationField.getText(), feeField.getText());
            showBillPopup(frame, bill);
        });

        mainContainer.add(headerPanel, BorderLayout.NORTH);
        mainContainer.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainContainer);
        frame.setVisible(true);
    }

    // Method to generate bill text
    private String generateBill(String vehicleNumber, String vehicleType, String entryTime, String exitTime,
                                String duration, String fee) {
        return String.format("Parking Bill\n\nVehicle Number: %s\nVehicle Type: %s\nEntry Time: %s\nExit Time: %s\n" +
                        "Parking Duration: %s\nCalculated Fee: %s \n\n Thank you! Welcome!", vehicleNumber, vehicleType, entryTime, exitTime,
                duration, fee);
    }

    private void showBillPopup(JFrame parent, String bill) {
        JTextArea textArea = new JTextArea(bill);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBackground(Color.WHITE);
        textArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JOptionPane.showMessageDialog(parent, new JScrollPane(textArea), "Parking Bill", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
            field.setBackground(new Color(240, 240, 240));
        }
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        textField.setPreferredSize(new Dimension(200, 35));
        return textField;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                super.paintComponent(g);
                g2.dispose();
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void showError(JFrame parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void showSuccess(JFrame parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
