package com.kqu.ui;

import com.kqu.config.ColorTheme;
import com.kqu.services.AdminService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * AdminDashboard.java
 * High-privilege administrative management portal for King's and Queen's University.
 * Allows adding students, adding lecturers, course allocation, and viewing statistics.
 */
public class AdminDashboard extends JFrame {

    private final AdminService adminService;
    private JTabbedPane tabbedPane;

    public AdminDashboard() {
        this.adminService = new AdminService();
        initUI();
    }

    private void initUI() {
        setTitle("KQU - Administrator Control Center");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(ColorTheme.CREAM);
        setLayout(new BorderLayout());

        // Header
        JPanel header = UIComponents.createUniversityHeader("Administrator Console - System Control & User Management");
        add(header, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(ColorTheme.BODY_BOLD);
        tabbedPane.setBackground(ColorTheme.CREAM);

        tabbedPane.addTab("System Overview", createOverviewTab());
        tabbedPane.addTab("Manage Students (500)", createStudentsTab());
        tabbedPane.addTab("Manage Lecturers (20)", createLecturersTab());
        tabbedPane.addTab("Faculties & Courses (8)", createFacultiesTab());

        add(tabbedPane, BorderLayout.CENTER);

        // Bottom status bar
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(ColorTheme.CREAM);
        JButton logoutBtn = UIComponents.createGoldButton("LOGOUT");
        logoutBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        footer.add(logoutBtn);
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel createOverviewTab() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBackground(ColorTheme.CREAM);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        panel.add(createMetricCard("Total Enrolled Students", "500 Accounts Active", "Distributed across all 8 faculties"));
        panel.add(createMetricCard("Appointed Lecturers", "20 Academic Staff", "100% course unit allocations"));
        panel.add(createMetricCard("Recognized Faculties", "8 Faculties / Schools", "43 Accredited Degree & Diploma Programs"));
        panel.add(createMetricCard("Campus Average Attendance", "88.4% Regularity", "Clearance threshold 75% for examinations"));

        return panel;
    }

    private JPanel createMetricCard(String title, String val, String subtitle) {
        JPanel card = UIComponents.createCardPanel();
        card.setLayout(new GridLayout(3, 1));
        JLabel t = new JLabel(title);
        t.setFont(ColorTheme.BODY_BOLD);
        t.setForeground(ColorTheme.ROYAL_BLUE);

        JLabel v = new JLabel(val);
        v.setFont(ColorTheme.TITLE_FONT);
        v.setForeground(ColorTheme.GOLD_DARK);

        JLabel s = new JLabel(subtitle);
        s.setFont(ColorTheme.BODY_FONT);
        s.setForeground(ColorTheme.TEXT_MUTED);

        card.add(t);
        card.add(v);
        card.add(s);
        return card;
    }

    private JPanel createStudentsTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(ColorTheme.CREAM);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.setBackground(ColorTheme.CREAM);
        JButton addBtn = UIComponents.createPrimaryButton("+ Enroll New Student");
        addBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "New Student Registration Modal: Assigns Reg Number, Faculty, Course, and default credentials.",
                "Register Student", JOptionPane.INFORMATION_MESSAGE));
        topBar.add(addBtn);

        String[] cols = {"Reg Number", "Student Name", "Faculty", "Programme", "Year", "CGPA", "Attendance"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        // Sample seed row representations
        model.addRow(new Object[]{"KQU/2024/001", "Samuel Mukasa", "Faculty of Computing", "BSc Computer Science", "Year 1", "4.45", "92%"});
        model.addRow(new Object[]{"KQU/2024/002", "Sarah Namubiru", "Faculty of Health Sciences", "MBChB (Medicine)", "Year 2", "4.60", "96%"});
        model.addRow(new Object[]{"KQU/2024/003", "Emmanuel Ssenkumba", "Faculty of Engineering", "Civil Engineering", "Year 1", "3.92", "85%"});
        model.addRow(new Object[]{"KQU/2024/004", "Grace Kigozi", "Faculty of Law", "Bachelor of Laws (LL.B)", "Year 3", "4.20", "90%"});
        model.addRow(new Object[]{"...", "... 496 more student records loaded ...", "All 8 Faculties", "All 43 Courses", "Y1 - Y5", "Mean 3.8", "Mean 88%"});

        JTable table = new JTable(model);
        table.setRowHeight(26);
        table.setFont(ColorTheme.BODY_FONT);
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLecturersTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(ColorTheme.CREAM);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.setBackground(ColorTheme.CREAM);
        JButton addBtn = UIComponents.createPrimaryButton("+ Appoint New Lecturer");
        addBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "New Lecturer Modal: Appoints Staff ID, Faculty, Department, and Course Unit.",
                "Appoint Lecturer", JOptionPane.INFORMATION_MESSAGE));
        topBar.add(addBtn);

        String[] cols = {"Staff ID", "Lecturer Name", "Title", "Faculty", "Department", "Assigned Unit"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"KQU-LEC-001", "Dr. Emmanuel Okello", "Dr.", "Faculty of Computing", "Computer Science", "CSC1101 Java Programming"});
        model.addRow(new Object[]{"KQU-LEC-002", "Dr. Julian Atuhaire", "Dr.", "Faculty of Computing", "Software Engineering", "SWE2101 Software Design"});
        model.addRow(new Object[]{"KQU-LEC-005", "Dr. Joseph Mukwaya", "Dr.", "Faculty of Health Sciences", "Clinical Medicine", "MED1101 Human Anatomy"});
        model.addRow(new Object[]{"KQU-LEC-008", "Eng. Moses Byaruhanga", "Eng.", "Faculty of Engineering", "Civil Engineering", "CIV1101 Statics & Mechanics"});
        model.addRow(new Object[]{"KQU-LEC-016", "Dr. Peter Mayanja", "Dr.", "Faculty of Law", "Public Law", "LLB1101 Constitutional Law"});

        JTable table = new JTable(model);
        table.setRowHeight(26);
        table.setFont(ColorTheme.BODY_FONT);
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFacultiesTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorTheme.CREAM);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        String[] listData = {
            "1. Faculty of Health Sciences / Medicine (MBChB, BDS, PHA, BNS, Public Health)",
            "2. Faculty of Engineering, Technology & Design (Civil, Electrical, Mechanical, Biosystems, Architecture, Surveying)",
            "3. Faculty of Computing & Information Science (Computer Science, Software Eng, IT, Business Computing, BLIS...)",
            "4. Faculty of Agriculture & Animal Sciences (Agriculture, Food Bioscience, Food Science & Tech, Animal Prod, Hort...)",
            "5. Faculty of Business, Economics & Management (BBA, BCom, Economics, Accounting & Finance, Procurement...)",
            "6. Faculty of Law (Bachelor of Laws LL.B, Diploma in Law)",
            "7. Faculty of Education & Humanities (BSc Education Biological, Physical, Math & Econ, Early Childhood...)",
            "8. School of Art and Industrial Design (Fine Art, Industrial & Commercial Art, Visual Communication Graphics)"
        };

        JList<String> list = new JList<>(listData);
        list.setFont(ColorTheme.BODY_BOLD);
        list.setFixedCellHeight(40);
        list.setBackground(Color.WHITE);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        return panel;
    }
}
