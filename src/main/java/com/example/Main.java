package com.example;

import com.example.config.AppConfig;
import com.example.config.HibernateConfig;
import com.example.config.UIThemeConfig;
import com.example.controller.LoginController;
import com.example.view.LoginView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Font;

/**
 * Lớp khởi động ứng dụng Quản lý Cửa Hàng Laptop.
 * Cập nhật: Khởi tạo DI -> Mở màn hình Login.
 */
public class Main {
    public static void main(String[] args) {
        // Ép kiểu bảng mã UTF-8 cho toàn bộ JVM để tránh lỗi font tiếng Việt
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        System.setProperty("stdout.encoding", "UTF-8");
        System.setProperty("stderr.encoding", "UTF-8");

        System.out.println("Starting Laptop Store Management App...");

        SwingUtilities.invokeLater(() -> {
            try {
                // 1. Áp dụng giao diện Cross-platform (tránh lỗi hiển thị trên các OS khác nhau)
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

                // 2. Thiết lập Font chữ mặc định hỗ trợ tiếng Việt tốt (Segoe UI)
                setGlobalFont(new Font("Segoe UI", Font.PLAIN, 13));

                // 3. Áp dụng Theme Dark Mode (Glassmorphism)
                UIThemeConfig.applyGlobalTheme();

                // 4. Kiểm tra kết nối Database qua Hibernate JPA
                System.out.println("Connecting to Database via Hibernate JPA...");
                HibernateConfig.getEntityManager().close();
                System.out.println("Database connection successful!");

                // 5. Khởi tạo Container quản lý các Service và DAO (Dependency Injection)
                AppConfig.initialize();
                System.out.println("AppConfig initialized.");

                // 6. Đăng ký Shutdown Hook để đóng kết nối Database khi thoát app
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    System.out.println("Đang dọn dẹp kết nối Database...");
                    HibernateConfig.shutdown();
                    System.out.println("Đã đóng kết nối Database.");
                }));

                // 7. Mở màn hình Đăng nhập (Thay thế cho việc mở HomeView trực tiếp)
                LoginView loginView = new LoginView();
                // Kết nối View với Controller và Service lấy từ AppConfig
                new LoginController(loginView, AppConfig.getTaiKhoanService());

                loginView.setVisible(true);
                System.out.println("Login screen displayed.");

            } catch (Exception ex) {
                System.err.println("Application startup error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    /**
     * Thiết lập Font mặc định cho TOÀN BỘ thành phần Swing.
     * Đảm bảo hiển thị đúng dấu tiếng Việt trên mọi máy tính.
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
