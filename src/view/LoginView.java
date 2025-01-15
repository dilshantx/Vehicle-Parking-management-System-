package view;

import controller.AuthController;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LoginView {
    private final AuthController authController;

    public LoginView() {
        authController = new AuthController();

        // Define colors and fonts
        Color primaryColor = new Color(70, 130, 180);
        Color backgroundColor = new Color(245, 245, 245);
        Color textColor = new Color(51, 51, 51);
        Font headerFont = new Font("Arial", Font.BOLD, 24);
        Font regularFont = new Font("Arial", Font.PLAIN, 14);

        // Create and setup main frame
        JFrame frame = new JFrame("Welcome Back");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Create background panel with image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Replace "path/to/your/image.jpg" with your actual image path
                ImageIcon imageIcon = new ImageIcon("D://Tharindu Dilshan/VehicleParkingSystem/image/background.jpg");
                Image image = imageIcon.getImage();
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // Create semi-transparent overlay panel
        JPanel overlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 230));
                g2d.fillRoundRect(20, 20, getWidth() - 40, getHeight() - 40, 20, 20);
            }
        };
        overlayPanel.setOpaque(false);
        overlayPanel.setLayout(new BorderLayout());

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainPanel.setOpaque(false);

        // Create header
        JLabel headerLabel = new JLabel("Sign In");
        headerLabel.setFont(headerFont);
        headerLabel.setForeground(textColor);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Create form components
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(regularFont);
        usernameLabel.setForeground(textColor);

        JTextField usernameField = new JTextField();
        styleTextField(usernameField, regularFont);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(regularFont);
        passwordLabel.setForeground(textColor);

        JPasswordField passwordField = new JPasswordField();
        styleTextField(passwordField, regularFont);

        // Create buttons with rounded corners
        JButton loginButton = createStyledButton("Sign In", primaryColor, Color.WHITE, regularFont);
        JButton registerButton = createStyledButton("Create Account", Color.WHITE, primaryColor, regularFont);

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
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)), gbc);
        gbc.gridy = 6;
        formPanel.add(loginButton, gbc);
        gbc.gridy = 7;
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
        gbc.gridy = 8;
        formPanel.add(registerButton, gbc);

        // Add action listeners
        loginButton.addActionListener(e -> {
            loginButton.setEnabled(false);
            loginButton.setText("Signing in...");

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Simulate network delay
            Timer timer = new Timer(800, evt -> {
                if (authController.loginUser(username, password)) {
                    frame.dispose();
                    new HomeView();
                } else {
                    loginButton.setEnabled(true);
                    loginButton.setText("Sign In");
                    showErrorDialog(frame, "Unable to Sign In",
                            "Please check your username and password and try again.");
                }
            });
            timer.setRepeats(false);
            timer.start();
        });

        registerButton.addActionListener(e -> new RegisterView());

        // Add components to main panel
        mainPanel.add(headerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(formPanel);

        // Build the component hierarchy
        overlayPanel.add(mainPanel, BorderLayout.CENTER);
        backgroundPanel.add(overlayPanel, BorderLayout.CENTER);
        frame.add(backgroundPanel);

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
