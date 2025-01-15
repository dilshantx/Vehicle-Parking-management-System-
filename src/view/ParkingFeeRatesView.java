package view;

import controller.RateController;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Map;

public class ParkingFeeRatesView {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color TEXT_COLOR = new Color(51, 51, 51);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color CARD_COLOR = new Color(255, 255, 255);

    public ParkingFeeRatesView() {
        RateController rateController = new RateController();

        JFrame frame = new JFrame("Parking Fee Rates");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(BACKGROUND_COLOR);
        frame.setLayout(new BorderLayout(20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Parking Fee Rates Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Main Content Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Rates Panel
        JPanel ratesPanel = new JPanel();
        ratesPanel.setLayout(new GridLayout(0, 1, 10, 10));
        ratesPanel.setBackground(BACKGROUND_COLOR);

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        Map<String, Double> rates = rateController.getRates();

        for (String vehicleType : rates.keySet()) {
            JPanel rateCard = new JPanel(new BorderLayout(15, 15));
            rateCard.setBackground(CARD_COLOR);
            rateCard.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(230, 230, 230), 1, true),
                    new EmptyBorder(15, 15, 15, 15)
            ));

            // Vehicle Type Panel
            JPanel vehicleTypePanel = new JPanel(new BorderLayout());
            vehicleTypePanel.setOpaque(false);

            JLabel vehicleTypeLabel = new JLabel(vehicleType);
            vehicleTypeLabel.setFont(new Font("Arial", Font.BOLD, 16));
            vehicleTypeLabel.setForeground(TEXT_COLOR);
            vehicleTypePanel.add(vehicleTypeLabel, BorderLayout.WEST);

            // Rate Input Panel
            JPanel rateInputPanel = new JPanel(new BorderLayout(10, 0));
            rateInputPanel.setOpaque(false);

            JLabel currencyLabel = new JLabel("LKR");
            currencyLabel.setFont(new Font("Arial", Font.BOLD, 16));
            currencyLabel.setForeground(TEXT_COLOR);

            JTextField rateField = new JTextField(decimalFormat.format(rates.get(vehicleType)));
            rateField.setFont(new Font("Arial", Font.PLAIN, 16));
            rateField.setPreferredSize(new Dimension(120, 35));
            rateField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(200, 200, 200), 1, true),
                    new EmptyBorder(5, 10, 5, 10)
            ));

            JButton updateButton = new JButton("Update Rate") {
                {
                    setOpaque(true);
                    setBackground(PRIMARY_COLOR);
                    setForeground(Color.WHITE);
                    setFocusPainted(false);
                    setBorderPainted(false);
                    setFont(new Font("Arial", Font.BOLD, 14));
                    setPreferredSize(new Dimension(120, 35));
                }
            };

            updateButton.addActionListener(e -> {
                try {
                    double newRate = Double.parseDouble(rateField.getText().replace(",", ""));
                    rateController.updateRate(vehicleType, newRate);

                    // Show success message with animation
                    JLabel successLabel = new JLabel("Rate Updated Successfully!");
                    successLabel.setFont(new Font("Arial", Font.BOLD, 14));
                    successLabel.setForeground(SUCCESS_COLOR);

                    rateField.setText(decimalFormat.format(newRate));
                    JOptionPane.showMessageDialog(
                            frame,
                            successLabel,
                            "Success",
                            JOptionPane.PLAIN_MESSAGE
                    );
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Please enter a valid rate",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            });

            // Add hover effect to button
            updateButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    updateButton.setBackground(PRIMARY_COLOR.darker());
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    updateButton.setBackground(PRIMARY_COLOR);
                }
            });

            rateInputPanel.add(currencyLabel, BorderLayout.WEST);
            rateInputPanel.add(rateField, BorderLayout.CENTER);
            rateInputPanel.add(updateButton, BorderLayout.EAST);

            rateCard.add(vehicleTypePanel, BorderLayout.WEST);
            rateCard.add(rateInputPanel, BorderLayout.EAST);
            ratesPanel.add(rateCard);
        }

        JScrollPane scrollPane = new JScrollPane(ratesPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(BACKGROUND_COLOR);

        mainPanel.add(scrollPane);

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
