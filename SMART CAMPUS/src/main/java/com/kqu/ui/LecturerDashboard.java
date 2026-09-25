package com.kqu.ui;

import com.kqu.config.ColorTheme;
import com.kqu.services.LecturerService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * LecturerDashboard.java
 * Dedicated portal for course unit lecturers to:
 * - View assigned courses and enrolled students
 * - Record and track daily attendance (Present, Absent, Late, Excused)
 * - Enter coursework marks (/40) and exam marks (/60)
 * - Auto-generate course reports and student grades
 */
public class LecturerDashboard extends JFrame {

    private final LecturerService lecturerService;

    public LecturerDashboard() {
        this.lecturerService = new LecturerService();
        initUI();
    }

    private void initUI() {
        setTitle("KQU - Lecturer Portal & Course Unit Management");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(ColorTheme.CREAM);
        setLayout(new BorderLayout());

        JPanel header = UIComponents.createUniversityHeader("Lecturer Academic Portal - Attendance & Marks Entry");
        add(header, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(ColorTheme.BODY_BOLD);

        tabbedPane.addTab("Assigned Course Units", createAssignedUnitsTab());
        tabbedPane.addTab("Take Attendance Register", createAttendanceRegisterTab());
        tabbedPane.addTab("Enter Marks & Grades", createMarksEntryTab());

        add(tabbedPane, BorderLayout.CENTER);

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

    private JPanel createAssignedUnitsTab() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(ColorTheme.CREAM);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel info = new JLabel("Currently Assigned: Dr. Emmanuel Okello (Faculty of Computing & Information Science)");
        info.setFont(ColorTheme.SUBTITLE_FONT);
        info.setForeground(ColorTheme.ROYAL_BLUE);
        panel.add(info, BorderLayout.NORTH);

        String[] cols = {"Unit Code", "Course Unit Title", "Programme", "Credit Units", "Enrolled Students", "Semester"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"CSC1101", "Structured Programming in Java", "BSc Computer Science", "4 CUs", "125 Students", "Semester 1"});
        model.addRow(new Object[]{"CSC1202", "Data Structures & Algorithms", "BSc Computer Science", "4 CUs", "118 Students", "Semester 2"});

        JTable table = new JTable(model);
        table.setRowHeight(28);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAttendanceRegisterTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(ColorTheme.CREAM);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        controlPanel.setBackground(ColorTheme.CREAM_LIGHT);

        controlPanel.add(new JLabel("Unit: CSC1101"));
        controlPanel.add(new JLabel("Date: 2026-09-21"));
        controlPanel.add(new JLabel("Session: Lecture 7 - OOP Encapsulation"));

        JButton markAllPresent = UIComponents.createGoldButton("Mark All Present");
        JButton saveAttendance = UIComponents.createPrimaryButton("Save Attendance Sheet");

        saveAttendance.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Attendance records successfully saved to the database!", "Attendance Logged", JOptionPane.INFORMATION_MESSAGE));

        controlPanel.add(markAllPresent);
        controlPanel.add(saveAttendance);

        String[] cols = {"Reg Number", "Student Name", "Present", "Absent", "Late", "Excused"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"KQU/2024/001", "Samuel Mukasa", "✓", "", "", ""});
        model.addRow(new Object[]{"KQU/2024/002", "Sarah Namubiru", "✓", "", "", ""});
        model.addRow(new Object[]{"KQU/2024/003", "Emmanuel Ssenkumba", "", "✗", "", ""});
        model.addRow(new Object[]{"KQU/2024/004", "Grace Kigozi", "✓", "", "", ""});

        JTable table = new JTable(model);
        table.setRowHeight(26);

        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createMarksEntryTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(ColorTheme.CREAM);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        String[] cols = {"Reg Number", "Student Name", "Coursework (/40)", "Exam (/60)", "Total (/100)", "Grade", "GP"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"KQU/2024/001", "Samuel Mukasa", "36.0", "52.0", "88.0", "A", "5.0"});
        model.addRow(new Object[]{"KQU/2024/002", "Sarah Namubiru", "38.5", "54.0", "92.5", "A", "5.0"});
        model.addRow(new Object[]{"KQU/2024/003", "Emmanuel Ssenkumba", "28.0", "43.0", "71.0", "B", "4.0"});
        model.addRow(new Object[]{"KQU/2024/004", "Grace Kigozi", "32.0", "47.0", "79.0", "B+", "4.5"});

        JTable table = new JTable(model);
        table.setRowHeight(26);

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomBar.setBackground(ColorTheme.CREAM);
        JButton saveMarksBtn = UIComponents.createPrimaryButton("Submit to Senate Exam Board");
        saveMarksBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Marks locked and published to student portals.", "Marks Published", JOptionPane.INFORMATION_MESSAGE));
        bottomBar.add(saveMarksBtn);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(bottomBar, BorderLayout.SOUTH);
        return panel;
    }
}
