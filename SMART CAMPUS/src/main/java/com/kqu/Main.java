package com.kqu;

import com.kqu.ui.LoginFrame;

import javax.swing.*;

/**
 * Main.java
 * Application entry point for King's and Queen's University Smart Campus System.
 * 
 * Coursework Project:
 * Design and Development of a Smart Campus Student Management and Academic
 * Information System Using Java.
 * 
 * Target Users:
 * 1. Administrator: Controls all powers, adds students & lecturers, manages faculties & courses
 * 2. Lecturer: Assigned course units, attendance registers, coursework & exam marks entry
 * 3. Student: Profile, course units, attendance tracking, GPA & official transcript
 */
public class Main {

    public static void main(String[] args) {
        // Set modern system look and feel if available
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Using default Swing LookAndFeel.");
        }

        // Launch GUI safely on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            System.out.println("=================================================");
            System.out.println(" KING'S AND QUEEN'S UNIVERSITY (KQU)");
            System.out.println(" Smart Campus Student Management System Started");
            System.out.println(" Default Admin: admin@kqu.ac.ug | Pass: admin123");
            System.out.println("=================================================");
        });
    }
}
