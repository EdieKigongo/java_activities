package com.kqu.ui;

import com.kqu.config.ColorTheme;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * UIComponents.java
 * Reusable custom UI components styled with the official King's and Queen's University palette:
 * - Royal Blue headers & primary actions
 * - Gold accents & active states
 * - Warm Cream panels & background canvas
 * - Black high-contrast text and structural borders
 */
public class UIComponents {

    /**
     * Creates a styled primary button (Royal Blue with Gold text and border).
     */
    public static JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(ColorTheme.BODY_BOLD);
        btn.setForeground(ColorTheme.GOLD);
        btn.setBackground(ColorTheme.ROYAL_BLUE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CompoundBorder(
                new LineBorder(ColorTheme.GOLD, 1, true),
                new EmptyBorder(8, 18, 8, 18)
        ));
        return btn;
    }

    /**
     * Creates a secondary/outline button (Gold background with Royal Blue text).
     */
    public static JButton createGoldButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(ColorTheme.BODY_BOLD);
        btn.setForeground(ColorTheme.ROYAL_BLUE_DARK);
        btn.setBackground(ColorTheme.GOLD);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CompoundBorder(
                new LineBorder(ColorTheme.GOLD_DARK, 1, true),
                new EmptyBorder(8, 18, 8, 18)
        ));
        return btn;
    }

    /**
     * Creates a warm cream styled card container.
     */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ColorTheme.CREAM_LIGHT);
        panel.setBorder(new CompoundBorder(
                new LineBorder(ColorTheme.CREAM_BORDER, 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));
        return panel;
    }

    /**
     * Creates a header banner for the university with Royal Blue background and Gold typography.
     */
    public static JPanel createUniversityHeader(String subTitle) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ColorTheme.ROYAL_BLUE);
        header.setBorder(new EmptyBorder(15, 25, 15, 25));

        JLabel titleLabel = new JLabel("KING'S AND QUEEN'S UNIVERSITY");
        titleLabel.setFont(ColorTheme.TITLE_FONT);
        titleLabel.setForeground(ColorTheme.GOLD);

        JLabel subLabel = new JLabel(subTitle != null ? subTitle : "Smart Campus Student Management & Academic Information System");
        subLabel.setFont(ColorTheme.BODY_FONT);
        subLabel.setForeground(Color.WHITE);

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(subLabel);

        header.add(textPanel, BorderLayout.WEST);
        return header;
    }

    /**
     * Creates a standard styled text field.
     */
    public static JTextField createTextField(int columns) {
        JTextField tf = new JTextField(columns);
        tf.setFont(ColorTheme.BODY_FONT);
        tf.setBackground(Color.WHITE);
        tf.setForeground(ColorTheme.BLACK);
        tf.setCaretColor(ColorTheme.ROYAL_BLUE);
        tf.setBorder(new CompoundBorder(
                new LineBorder(ColorTheme.CREAM_BORDER, 1),
                new EmptyBorder(6, 8, 6, 8)
        ));
        return tf;
    }
}
