package com.example;

import com.example.config.HibernateUtil;
import com.example.config.UITheme;
import com.example.view.HomeView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Lớp khởi động ứng dụng Quản lý Cửa Hàng Laptop.
 * Áp dụng Dark Mode theme → khởi tạo Hibernate → mở HomeView.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Đang khởi tạo ứng dụng Quản lý Cửa Hàng Laptop...");

        SwingUtilities.invokeLater(() -> {
            try {
                // 1. Áp dụng Look & Feel hệ thống
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

                // 2. Áp dụng Dark Mode theme toàn cục
                UITheme.applyGlobalTheme();

                // 3. Khởi tạo Hibernate/JPA connection pool
                System.out.println("Đang kết nối Database bằng Hibernate JPA...");
                HibernateUtil.getEntityManager().close();
                System.out.println("Kết nối Database thành công!");

                // 4. Mở giao diện chính
                new HomeView();

            } catch (Exception ex) {
                System.err.println("Lỗi khởi chạy ứng dụng: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
}