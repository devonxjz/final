package com.example.view;

import com.example.config.UIThemeConfig;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * LoginView — Màn hình đăng nhập phong cách Glassmorphism.
 */
public class LoginView extends JFrame {

    public JTextField txtUsername;
    public JPasswordField txtPassword;
    public JButton btnLogin;
    public JCheckBox chkRemember;

    public LoginView() {
        setTitle("LaptopPU — Đăng Nhập Hệ Thống");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIThemeConfig.BG_DARK);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        setContentPane(mainPanel);

        // Center Content Container
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        // 1. Logo/Title Section
        JLabel lblLogo = new JLabel("LaptopPU");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblLogo.setForeground(UIThemeConfig.ACCENT);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Hệ Thống Quản Lý Cửa Hàng");
        lblSub.setFont(UIThemeConfig.FONT_BODY);
        lblSub.setForeground(UIThemeConfig.TEXT_MUTED);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 2. Input Section (Glass Panel)
        JPanel inputPanel = UIThemeConfig.createGlassPanel(new GridBagLayout());
        inputPanel.setBorder(new EmptyBorder(25, 20, 25, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.gridx = 0;

        // Username
        JLabel lblUser = UIThemeConfig.createLabel("Tên đăng nhập:");
        gbc.gridy = 0;
        inputPanel.add(lblUser, gbc);

        txtUsername = UIThemeConfig.createTextField();
        txtUsername.setPreferredSize(new Dimension(0, 38));
        gbc.gridy = 1;
        inputPanel.add(txtUsername, gbc);

        // Password
        JLabel lblPass = UIThemeConfig.createLabel("Mật khẩu:");
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 0, 5, 0);
        inputPanel.add(lblPass, gbc);

        txtPassword = new JPasswordField();
        // Áp dụng style từ UIThemeConfig cho JPasswordField
        txtPassword.setBackground(UIThemeConfig.BG_INPUT);
        txtPassword.setForeground(UIThemeConfig.TEXT_PRIMARY);
        txtPassword.setCaretColor(UIThemeConfig.ACCENT);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIThemeConfig.BORDER, 1),
            new EmptyBorder(0, 10, 0, 10)));
        txtPassword.setPreferredSize(new Dimension(0, 38));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 5, 0);
        inputPanel.add(txtPassword, gbc);

        // Remember me
        chkRemember = new JCheckBox("Duy trì đăng nhập");
        chkRemember.setOpaque(false);
        chkRemember.setForeground(UIThemeConfig.TEXT_MUTED);
        chkRemember.setFont(UIThemeConfig.FONT_SMALL);
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 0, 0);
        inputPanel.add(chkRemember, gbc);

        // 3. Button Section
        btnLogin = UIThemeConfig.createPrimaryButton("ĐĂNG NHẬP");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(0, 45));
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Footer Text
        JLabel lblFooter = new JLabel("© 2026 LaptopPU — Developed by Hoang Anh");
        lblFooter.setFont(UIThemeConfig.FONT_SMALL);
        lblFooter.setForeground(UIThemeConfig.TEXT_MUTED);
        lblFooter.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Assembly
        content.add(Box.createVerticalGlue());
        content.add(lblLogo);
        content.add(lblSub);
        content.add(Box.createRigidArea(new Dimension(0, 35)));
        content.add(inputPanel);
        content.add(Box.createRigidArea(new Dimension(0, 25)));
        content.add(btnLogin);
        content.add(Box.createRigidArea(new Dimension(0, 40)));
        content.add(lblFooter);
        content.add(Box.createVerticalGlue());

        mainPanel.add(content, BorderLayout.CENTER);
    }
}
