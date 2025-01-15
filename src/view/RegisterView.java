package view;

import controller.AuthController;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RegisterView {
    public RegisterView() {
        // Define colors and fonts
        Color primaryColor = new Color(70, 130, 180);
        Color backgroundColor = new Color(245, 245, 245);
        Color textColor = new Color(51, 51, 51);
        Font headerFont = new Font("Arial", Font.BOLD, 24);
        Font regularFont = new Font("Arial", Font.PLAIN, 14);

        // Create and setup main frame
        JFrame frame = new JFrame("Create Account");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setBackground(backgroundColor);

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(backgroundColor);

        // Create header
        JLabel headerLabel = new JLabel("Create Account");
        headerLabel.setFont(headerFont);
        headerLabel.setForeground(textColor);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Create form components
        JLabel usernameLabel = new JLabel("Choose a username");
        usernameLabel.setFont(regularFont);
        usernameLabel.setForeground(textColor);

        JTextField usernameField = new JTextField();
        styleTextField(usernameField, regularFont);

        JLabel passwordLabel = new JLabel("Create a password");
        passwordLabel.setFont(regularFont);
        passwordLabel.setForeground(textColor);

        JPasswordField passwordField = new JPasswordField();
        styleTextField(passwordField, regularFont);

        JLabel confirmPasswordLabel = new JLabel("Confirm password");
        confirmPasswordLabel.setFont(regularFont);
        confirmPasswordLabel.setForeground(textColor);

        JPasswordField confirmPasswordField = new JPasswordField();
        styleTextField(confirmPasswordField, regularFont);

        // Create buttons
        JButton registerButton = createStyledButton("Create Account", primaryColor, Color.WHITE, regularFont);
        JButton cancelButton = createStyledButton("Cancel", Color.WHITE, primaryColor, regularFont);

        // Add components to form panel
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);
        gbc.gridy = 1;
        formPanel.add(usernameField, gbc);
        gbc.gridy = 2;
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
        gbc.gridy = 3;
        formPanel.add(passwordLabel, gbc);
        gbc.gridy = 4;
        formPanel.add(passwordField, gbc);
        gbc.gridy = 5;
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
        gbc.gridy = 6;
        formPanel.add(confirmPasswordLabel, gbc);
        gbc.gridy = 7;
        formPanel.add(confirmPasswordField, gbc);
        gbc.gridy = 8;
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)), gbc);
        gbc.gridy = 9;
        formPanel.add(registerButton, gbc);
        gbc.gridy = 10;
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
        gbc.gridy = 11;
        formPanel.add(cancelButton, gbc);

        // Add action listeners
        AuthController authController = new AuthController();

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.trim().isEmpty() || password.isEmpty()) {
                showErrorDialog(frame, "Registration Error", "Please fill in all fields.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showErrorDialog(frame, "Registration Error", "Passwords do not match.");
                return;
            }

            registerButton.setEnabled(false);
            registerButton.setText("Creating Account...");

            // Simulate network delay
            Timer timer = new Timer(800, evt -> {
                if (authController.registerUser(username, password)) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Account created successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    frame.dispose();
                } else {
                    registerButton.setEnabled(true);
                    registerButton.setText("Create Account");
                    showErrorDialog(frame, "Registration Failed",
                            "Unable to create account. Please try again.");
                }
            });
            timer.setRepeats(false);
            timer.start();
        });

        cancelButton.addActionListener(e -> frame.dispose());

        // Add components to main panel
        mainPanel.add(headerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(formPanel);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void styleTextField(JTextField textField, Font font) {
        textField.setPreferredSize(new Dimension(300, 35));
        textField.setFont(font);
        textField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(8, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor, Font font) {
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

        button.setPreferredSize(new Dimension(300, 40));
        button.setFont(font.deriveFont(Font.BOLD));
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void showErrorDialog(JFrame parent, String title, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                title,
                JOptionPane.ERROR_MESSAGE
        );
    }

    // Custom rounded border implementation
    private static class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
    }
}
