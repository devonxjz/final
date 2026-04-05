package com.example.controller;

import com.example.config.AppConfig;
import com.example.entity.TaiKhoan;
import com.example.services.TaiKhoanService;
import com.example.view.LoginView;
import com.example.view.HomeView;
import com.example.util.SwingWorkerUtils;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginController {
    private final LoginView view;
    private final TaiKhoanService service;

    public LoginController(LoginView view, TaiKhoanService service) {
        this.view = view;
        this.service = service;
        initController();
    }

    private void initController() {
        // Sự kiện khi nhấn nút Đăng nhập
        view.btnLogin.addActionListener(e -> handleLogin());

        // Hỗ trợ nhấn Enter để đăng nhập nhanh
        KeyAdapter enterKey = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) handleLogin();
            }
        };
        view.txtUsername.addKeyListener(enterKey);
        view.txtPassword.addKeyListener(enterKey);
    }

    private void handleLogin() {
        String user = view.txtUsername.getText().trim();
        String pass = new String(view.txtPassword.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
            return;
        }

        // Chạy Async để tránh treo giao diện khi check Database
        SwingWorkerUtils.runAsync(
            view.btnLogin,
            () -> service.authenticate(user, pass), // Hàm này bạn cần viết trong Service
            taiKhoan -> {
                if (taiKhoan != null) {
                    // Lưu thông tin phiên đăng nhập (Session)
                    AppConfig.setCurrentUser(taiKhoan);

                    JOptionPane.showMessageDialog(view, "Đăng nhập thành công! Chào " + taiKhoan.getNhanVien().getTenNV());

                    // Chuyển sang màn hình chính
                    view.dispose();
                    new HomeView();
                } else {
                    JOptionPane.showMessageDialog(view, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            },
            ex -> JOptionPane.showMessageDialog(view, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage())
        );
    }
}
