package com.example.view;

import com.example.config.UITheme;
import com.example.controller.ThemNhaCungCapController;
import com.example.dao.impl.NhaCungCapDAOImpl;
import javax.swing.*;
import java.awt.*;

public class ThemNhaCungCapView extends JFrame {
    public JTextField txtTenNCC, txtSDT, txtDiaChi;
    public JButton btnThem, btnClear;
    private ThemNhaCungCapController controller;

    public ThemNhaCungCapView() {
        setTitle("Add New Supplier");
        setSize(600, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = UITheme.createTitleLabel("ADD NEW SUPPLIER");
        lblTitle.setForeground(UITheme.ACCENT);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlForm = UITheme.createCard();
        pnlForm.setLayout(new GridLayout(3, 2, 15, 10));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        txtTenNCC = UITheme.createTextField();
        txtSDT    = UITheme.createTextField();
        txtDiaChi = UITheme.createTextField();
        pnlForm.add(UITheme.createLabel("Supplier Name:")); pnlForm.add(txtTenNCC);
        pnlForm.add(UITheme.createLabel("Phone:"));        pnlForm.add(txtSDT);
        pnlForm.add(UITheme.createLabel("Address:"));      pnlForm.add(txtDiaChi);
        add(pnlForm, BorderLayout.CENTER);

        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlBtns.setBackground(UITheme.BG_DARK);
        btnThem  = UITheme.createSuccessButton("Add");
        btnClear = UITheme.createDangerButton("Clear");
        pnlBtns.add(btnThem); pnlBtns.add(btnClear);
        add(pnlBtns, BorderLayout.SOUTH);

        controller = new ThemNhaCungCapController(new NhaCungCapDAOImpl(), this);
    }
}