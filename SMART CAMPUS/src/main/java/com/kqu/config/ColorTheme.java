package com.kqu.config;

import java.awt.Color;
import java.awt.Font;

/**
 * ColorTheme.java
 * King's and Queen's University (KQU) - Visual Palette Constants
 * 
 * Strict university branding theme:
 * 1. Royal Blue: Dominant academic authority & elegance (#0F2557 / #1A365D)
 * 2. Gold: Excellence and prestige (#D4AF37 / #C5A059)
 * 3. Cream: Clean, warm, parchment background (#FAF7EE / #FFFDD0)
 * 4. Black: High-contrast typography & structural framing (#0B0F19 / #121826)
 */
public class ColorTheme {

    // Primary Royal Blue
    public static final Color ROYAL_BLUE = new Color(15, 37, 87);       // #0F2557
    public static final Color ROYAL_BLUE_DARK = new Color(10, 25, 60);  // #0A193C
    public static final Color ROYAL_BLUE_LIGHT = new Color(27, 59, 111); // #1B3B6F

    // Prestige Gold
    public static final Color GOLD = new Color(212, 175, 55);           // #D4AF37
    public static final Color GOLD_DARK = new Color(184, 134, 11);       // #B8860B
    public static final Color GOLD_LIGHT = new Color(243, 229, 171);     // Soft gold highlight

    // Warm Cream (Backgrounds & Cards)
    public static final Color CREAM = new Color(250, 247, 238);         // #FAF7EE
    public static final Color CREAM_LIGHT = new Color(255, 253, 240);   // #FFFDF0
    public static final Color CREAM_BORDER = new Color(229, 222, 203);  // Muted separator

    // Deep Black (Text & Deep Accents)
    public static final Color BLACK = new Color(11, 15, 25);            // #0B0F19
    public static final Color BLACK_MUTED = new Color(30, 41, 59);      // #1E293B
    public static final Color TEXT_MUTED = new Color(100, 116, 139);    // Secondary text

    // Typography
    public static final Font TITLE_FONT = new Font("Georgia", Font.BOLD, 22);
    public static final Font SUBTITLE_FONT = new Font("Georgia", Font.BOLD, 15);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font BODY_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font MONO_FONT = new Font("Monospaced", Font.PLAIN, 12);
}
