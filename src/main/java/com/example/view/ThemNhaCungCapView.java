package com.example.view;

import com.example.controller.ThemNhaCungCapController;
import com.example.dao.NhaCungCapDAO;
import javax.swing.*;
import java.awt.*;

public class ThemNhaCungCapView extends JFrame {
    public JTextField txtTenNCC, txtSDT, txtDiaChi;
    public JButton btnThem, btnClear;
    private ThemNhaCungCapController controller;

    public ThemNhaCungCapView() {
        setTitle("Form_themNCC");
        setSize(700, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.CYAN);
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("THÊM NHÀ CUNG CẤP");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(200, 10, 300, 40);
        panel.add(lblTitle);

        // Tên nhà cung cấp
        JLabel lblTen = new JLabel("Tên nhà cung cấp:");
        lblTen.setBounds(50, 80, 150, 25);
        panel.add(lblTen);

        txtTenNCC = new JTextField();
        txtTenNCC.setBounds(200, 80, 150, 25);
        panel.add(txtTenNCC);

        // Số điện thoại
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(380, 80, 100, 25);
        panel.add(lblSDT);

        txtSDT = new JTextField();
        txtSDT.setBounds(480, 80, 150, 25);
        panel.add(txtSDT);

        // Địa chỉ
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setBounds(50, 130, 150, 25);
        panel.add(lblDiaChi);

        txtDiaChi = new JTextField();
        txtDiaChi.setBounds(200, 130, 430, 25);
        panel.add(txtDiaChi);

        // Nút chức năng
        btnThem = new JButton("Thêm");
        btnThem.setBounds(580, 30, 80, 30);
        btnThem.setForeground(Color.BLUE);
        panel.add(btnThem);

        btnClear = new JButton("Clear");
        btnClear.setBounds(580, 200, 80, 30);
        btnClear.setForeground(Color.RED);
        panel.add(btnClear);

        add(panel);

        controller = new ThemNhaCungCapController(new NhaCungCapDAO(), this);
    }
}