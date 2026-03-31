package com.example;

import com.example.config.HibernateConfig;
import com.example.config.UIThemeConfig;
import com.example.view.HomeView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Font;

/**
 * Lớp khởi động ứng dụng Quản lý Cửa Hàng Laptop.
 * Áp dụng Dark Mode theme → khởi tạo Hibernate → mở HomeView.
 */
public class Main {
    public static void main(String[] args) {
        // Force UTF-8 encoding for the entire JVM
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        System.setProperty("stdout.encoding", "UTF-8");
        System.setProperty("stderr.encoding", "UTF-8");

        System.out.println("Starting Laptop Store Management App...");

        SwingUtilities.invokeLater(() -> {
            try {
                // 1. Apply cross-platform Look & Feel
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

                // 2. Set a global default font that supports Vietnamese
                setGlobalFont(new Font("Segoe UI", Font.PLAIN, 13));

                // 3. Apply Dark Mode theme
                UIThemeConfig.applyGlobalTheme();

                // 4. Initialize Hibernate/JPA connection pool
                System.out.println("Connecting to Database via Hibernate JPA...");
                HibernateConfig.getEntityManager().close();
                System.out.println("Database connection successful!");

                // 5. Initialize Dependency Injection container
                com.example.config.AppConfig.initialize();
                System.out.println("AppConfig initialized.");

                // 6. Open main UI
                new HomeView();

            } catch (Exception ex) {
                System.err.println("Application startup error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    /**
     * Sets a default font for ALL Swing components globally.
     * This ensures Vietnamese diacritics render correctly everywhere.
     */
    private static void setGlobalFont(Font font) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, new javax.swing.plaf.FontUIResource(font));
            }
        }
    }
}
