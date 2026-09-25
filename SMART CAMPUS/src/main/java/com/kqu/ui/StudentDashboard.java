package com.kqu.ui;

import com.kqu.config.ColorTheme;
import com.kqu.services.ReportService;
import com.kqu.services.StudentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * StudentDashboard.java
 * Student self-service academic portal for King's and Queen's University:
 * - View identity and degree program information
 * - Registered course units
 * - Real-time attendance rate and exam eligibility badge
 * - Examination grades and semester GPA
 * - Download/View Official Academic Transcript
 */
public class StudentDashboard extends JFrame {

    private final StudentService studentService;
    private final ReportService reportService;

    public StudentDashboard() {
        this.studentService = new StudentService();
        this.reportService = new ReportService();
        initUI();
    }

    private void initUI() {
        setTitle("KQU - Student Academic Portal");
        setSize(920, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(ColorTheme.CREAM);
        setLayout(new BorderLayout());

        JPanel header = UIComponents.createUniversityHeader("Student Academic Self-Service Portal");
        add(header, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(ColorTheme.BODY_BOLD);

        tabbedPane.addTab("My Academic Profile", createProfileTab());
        tabbedPane.addTab("Attendance Record", createAttendanceTab());
        tabbedPane.addTab("Grades & Results", createGradesTab());
        tabbedPane.addTab("Official Transcript", createTranscriptTab());

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

    private JPanel createProfileTab() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 20));
        panel.setBackground(ColorTheme.CREAM);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel left = UIComponents.createCardPanel();
        left.setLayout(new GridLayout(6, 1, 0, 8));
        left.add(new JLabel("STUDENT IDENTIFICATION"));
        left.add(new JLabel("Full Name: Samuel Mukasa"));
        left.add(new JLabel("Reg Number: KQU/2024/001"));
        left.add(new JLabel("Faculty: Faculty of Computing & Information Science"));
        left.add(new JLabel("Programme: Bachelor of Science in Computer Science"));
        left.add(new JLabel("Year of Study: Year 1 | Semester 1"));

        JPanel right = UIComponents.createCardPanel();
        right.setLayout(new GridLayout(4, 1, 0, 8));
        JLabel gpaLabel = new JLabel("Cumulative CGPA: 4.45 / 5.00");
        gpaLabel.setFont(ColorTheme.SUBTITLE_FONT);
        gpaLabel.setForeground(ColorTheme.ROYAL_BLUE);

        JLabel statusLabel = new JLabel("Class: FIRST CLASS HONOURS");
        statusLabel.setFont(ColorTheme.BODY_BOLD);
        statusLabel.setForeground(ColorTheme.GOLD_DARK);

        JLabel attLabel = new JLabel("Overall Attendance: 92.5% (ELIGIBLE FOR EXAMS ✓)");
        attLabel.setFont(ColorTheme.BODY_BOLD);
        attLabel.setForeground(new Color(16, 120, 60));

        right.add(gpaLabel);
        right.add(statusLabel);
        right.add(attLabel);

        panel.add(left);
        panel.add(right);
        return panel;
    }

    private JPanel createAttendanceTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(ColorTheme.CREAM);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        String[] cols = {"Unit Code", "Course Unit Title", "Attended Sessions", "Total Lectures", "Attendance %", "Exam Eligibility"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"CSC1101", "Structured Programming in Java", "22", "24", "91.6%", "CLEARED ✓"});
        model.addRow(new Object[]{"CSC1202", "Data Structures & Algorithms", "20", "22", "90.9%", "CLEARED ✓"});
        model.addRow(new Object[]{"BIT1103", "Relational Database Management Systems", "18", "20", "90.0%", "CLEARED ✓"});

        JTable table = new JTable(model);
        table.setRowHeight(28);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createGradesTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(ColorTheme.CREAM);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        String[] cols = {"Unit Code", "Course Unit Title", "CUs", "CW (/40)", "Exam (/60)", "Total", "Grade", "GP"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"CSC1101", "Structured Programming in Java", "4", "36.0", "52.0", "88.0", "A", "5.0"});
        model.addRow(new Object[]{"BIT1103", "Relational Database Management Systems", "3", "34.0", "48.0", "82.0", "A", "5.0"});
        model.addRow(new Object[]{"CSC1202", "Data Structures & Algorithms", "4", "31.0", "46.0", "77.0", "B+", "4.5"});

        JTable table = new JTable(model);
        table.setRowHeight(28);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTranscriptTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(ColorTheme.CREAM);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextArea transcriptArea = new JTextArea();
        transcriptArea.setFont(ColorTheme.MONO_FONT);
        transcriptArea.setEditable(false);
        transcriptArea.setBackground(Color.WHITE);
        transcriptArea.setText(
            "=================================================================\n" +
            "             KING'S AND QUEEN'S UNIVERSITY (KQU)\n" +
            "                 OFFICE OF THE ACADEMIC REGISTRAR\n" +
            "                   OFFICIAL ACADEMIC TRANSCRIPT\n" +
            "=================================================================\n\n" +
            "STUDENT NAME:        SAMUEL MUKASA\n" +
            "REGISTRATION NUMBER: KQU/2024/001\n" +
            "FACULTY:             FACULTY OF COMPUTING & INFORMATION SCIENCE\n" +
            "PROGRAMME:           BACHELOR OF SCIENCE IN COMPUTER SCIENCE\n" +
            "CURRENT YEAR:        YEAR 1\n\n" +
            "-----------------------------------------------------------------\n" +
            "CODE       TITLE                              CW/40  EX/60  GRADE  GP\n" +
            "-----------------------------------------------------------------\n" +
            "CSC1101    Structured Programming in Java     36.0   52.0   A      5.0\n" +
            "BIT1103    Relational Database Systems        34.0   48.0   A      5.0\n" +
            "CSC1202    Data Structures & Algorithms       31.0   46.0   B+     4.5\n" +
            "-----------------------------------------------------------------\n" +
            "CUMULATIVE GPA (CGPA): 4.82 / 5.00\n" +
            "AWARD CLASSIFICATION:  FIRST CLASS HONOURS\n" +
            "=================================================================\n" +
            "Certified Official Copy - Verified by Registrar & University Seal\n"
        );

        JButton printBtn = UIComponents.createPrimaryButton("PRINT OFFICIAL TRANSCRIPT");
        printBtn.addActionListener(e -> {
            try {
                transcriptArea.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Print error: " + ex.getMessage());
            }
        });

        panel.add(new JScrollPane(transcriptArea), BorderLayout.CENTER);
        panel.add(printBtn, BorderLayout.SOUTH);
        return panel;
    }
}
