package view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class HomeView {
    // Define colors
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color CARD_BACKGROUND = new Color(149, 203, 255, 228);
    private static final Color TEXT_COLOR = new Color(51, 51, 51);
    private static final Color HOVER_COLOR = new Color(60, 110, 160);

    public HomeView() {
        JFrame frame = new JFrame("Parking Management Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setBackground(BACKGROUND_COLOR);

        // Main container with padding
        JPanel mainContainer = new JPanel(new BorderLayout(20, 20));
        mainContainer.setBackground(BACKGROUND_COLOR);
        mainContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top panel with welcome message and logout
        JPanel topPanel = createTopPanel();

        // Dashboard grid panel
        JPanel dashboardPanel = createDashboardPanel();

        mainContainer.add(topPanel, BorderLayout.NORTH);
        mainContainer.add(dashboardPanel, BorderLayout.CENTER);

        frame.add(mainContainer);
        frame.setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Welcome text
        JLabel welcomeLabel = new JLabel("Parking Management System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(TEXT_COLOR);

        // Logout button
        JButton logoutButton = createStyledButton("Logout", new ImageIcon("path/to/logout-icon.png"));
        logoutButton.setPreferredSize(new Dimension(120, 40));
        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                ((JFrame) SwingUtilities.getWindowAncestor(logoutButton)).dispose();
                new LoginView();
            }
        });

        topPanel.add(welcomeLabel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        dashboardPanel.setBackground(BACKGROUND_COLOR);

        // Create dashboard cards
        dashboardPanel.add(createDashboardCard(
                "Vehicle Entry",
                "Register new vehicle entries",
                "path/to/entry-icon.png",
                e -> new VehicleEntryView()
        ));

        dashboardPanel.add(createDashboardCard(
                "Available Slots",
                "View and manage parking slots",
                "path/to/slots-icon.png",
                e -> new AvailableSlotsView()
        ));

        dashboardPanel.add(createDashboardCard(
                "Parking Fee",
                "Calculate parking fees",
                "path/to/fee-icon.png",
                e -> new ParkingFeeView()
        ));

        dashboardPanel.add(createDashboardCard(
                "Fee Rates",
                "Manage parking fee rates",
                "path/to/rates-icon.png",
                e -> new ParkingFeeRatesView()
        ));

        dashboardPanel.add(createDashboardCard(
                "Current Vehicles",
                "View currently parked vehicles",
                "path/to/vehicles-icon.png",
                e -> new CurrentVehiclesView()
        ));

        dashboardPanel.add(createDashboardCard(
                "Parking Records",
                "Access parking history",
                "path/to/records-icon.png",
                e -> new ParkingRecordsView()
        ));

        return dashboardPanel;
    }

    private JPanel createDashboardCard(String title, String description, String iconPath, java.awt.event.ActionListener action) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BACKGROUND);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
            }
        };

        card.setLayout(new BorderLayout(10, 10));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.setBackground(CARD_BACKGROUND);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(CARD_BACKGROUND);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Description
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(TEXT_COLOR);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(descLabel);

        card.add(contentPanel, BorderLayout.CENTER);

        // Add hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(PRIMARY_COLOR.brighter());
                contentPanel.setBackground(PRIMARY_COLOR.brighter());
                titleLabel.setForeground(Color.WHITE);
                descLabel.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(CARD_BACKGROUND);
                contentPanel.setBackground(CARD_BACKGROUND);
                titleLabel.setForeground(TEXT_COLOR);
                descLabel.setForeground(TEXT_COLOR);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                action.actionPerformed(null);
            }
        });

        return card;
    }

    private JButton createStyledButton(String text, Icon icon) {
        JButton button = new JButton(text, icon) {
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
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
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
}
