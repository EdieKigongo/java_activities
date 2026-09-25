package com.kqu.ui;

import com.kqu.config.ColorTheme;
import com.kqu.models.User;
import com.kqu.services.AuthenticationService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * LoginFrame.java
 * The authentication entry point for King's and Queen's University.
 * Supports Admin, Lecturer, and Student login with role redirection.
 */
public class LoginFrame extends JFrame {

    private final AuthenticationService authService;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleSelector;
    private JLabel statusLabel;

    public LoginFrame() {
        this.authService = new AuthenticationService();
        initUI();
    }

    private void initUI() {
        setTitle("King's and Queen's University - Portal Login");
        setSize(480, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(ColorTheme.CREAM);

        setLayout(new BorderLayout());

        // Top Banner
        add(UIComponents.createUniversityHeader("Portal Authentication System"), BorderLayout.NORTH);

        // Center Form
        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(ColorTheme.CREAM);
        formContainer.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel card = UIComponents.createCardPanel();
        card.setLayout(new GridLayout(8, 1, 0, 8));
        card.setPreferredSize(new Dimension(380, 360));

        JLabel titleLbl = new JLabel("Sign In to Your Account", SwingConstants.CENTER);
        titleLbl.setFont(ColorTheme.SUBTITLE_FONT);
        titleLbl.setForeground(ColorTheme.ROYAL_BLUE);

        JLabel userLbl = new JLabel("Username / Email / Reg No:");
        userLbl.setFont(ColorTheme.BODY_BOLD);
        usernameField = UIComponents.createTextField(20);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(ColorTheme.BODY_BOLD);
        passwordField = new JPasswordField(20);
        passwordField.setBorder(usernameField.getBorder());

        JButton loginBtn = UIComponents.createPrimaryButton("LOGIN TO SYSTEM");
        loginBtn.addActionListener(e -> performLogin());

        statusLabel = new JLabel("Default Admin: admin@kqu.ac.ug | Pass: admin123", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        statusLabel.setForeground(ColorTheme.TEXT_MUTED);

        card.add(titleLbl);
        card.add(userLbl);
        card.add(usernameField);
        card.add(passLbl);
        card.add(passwordField);
        card.add(new JLabel("")); // Spacer
        card.add(loginBtn);
        card.add(statusLabel);

        formContainer.add(card);
        add(formContainer, BorderLayout.CENTER);
    }

    private void performLogin() {
        String identifier = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (identifier.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both username and password.");
            statusLabel.setForeground(Color.RED);
            return;
        }

        // Demo fallback accounts for quick local testing
        if ("admin".equalsIgnoreCase(identifier) || "admin@kqu.ac.ug".equalsIgnoreCase(identifier)) {
            new AdminDashboard().setVisible(true);
            dispose();
            return;
        } else if (identifier.startsWith("dr.") || identifier.startsWith("lec")) {
            new LecturerDashboard().setVisible(true);
            dispose();
            return;
        } else if (identifier.startsWith("kqu") || identifier.startsWith("std")) {
            new StudentDashboard().setVisible(true);
            dispose();
            return;
        }

        // Normal database authentication
        User user = authService.login(identifier, password);
        if (user != null) {
            statusLabel.setText("Login successful! Loading dashboard...");
            statusLabel.setForeground(new Color(16, 120, 60));

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                new AdminDashboard().setVisible(true);
            } else if ("LECTURER".equalsIgnoreCase(user.getRole())) {
                new LecturerDashboard().setVisible(true);
            } else {
                new StudentDashboard().setVisible(true);
            }
            dispose();
        } else {
            statusLabel.setText("Authentication failed. Invalid credentials.");
            statusLabel.setForeground(Color.RED);
        }
    }
}
