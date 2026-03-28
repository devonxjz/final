package com.example.view;

import com.example.config.UITheme;
import com.example.controller.ThemSanPhamController;
import com.example.dao.impl.SanPhamDAOImpl;
import javax.swing.*;
import java.awt.*;

public class ThemSanPhamView extends JFrame {
    public JTextField txtLoaiMay, txtTenSP, txtCPU, txtGPU, txtRAM, txtOCung, txtCanNang;
    public JTextField txtKTManHinh, txtDPGiaiMH, txtSoLuong, txtGiaBan, txtGiaNhap, txtBaoHanh;
    public JButton btnThem, btnClear;
    private ThemSanPhamController controller;

    public ThemSanPhamView() {
        setTitle("Add New Product");
        setSize(750, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = UITheme.createTitleLabel("ADD NEW PRODUCT");
        lblTitle.setForeground(UITheme.ACCENT);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlForm = UITheme.createCard();
        pnlForm.setLayout(new GridLayout(7, 4, 12, 8));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        txtLoaiMay   = UITheme.createTextField(); txtTenSP     = UITheme.createTextField();
        txtCPU       = UITheme.createTextField(); txtGPU       = UITheme.createTextField();
        txtRAM       = UITheme.createTextField(); txtOCung     = UITheme.createTextField();
        txtCanNang   = UITheme.createTextField(); txtKTManHinh = UITheme.createTextField();
        txtDPGiaiMH  = UITheme.createTextField(); txtSoLuong   = UITheme.createTextField();
        txtGiaBan    = UITheme.createTextField(); txtGiaNhap   = UITheme.createTextField();
        txtBaoHanh   = UITheme.createTextField();

        pnlForm.add(UITheme.createLabel("Type:"));            pnlForm.add(txtLoaiMay);
        pnlForm.add(UITheme.createLabel("Screen Size:"));     pnlForm.add(txtKTManHinh);
        pnlForm.add(UITheme.createLabel("Product Name:"));    pnlForm.add(txtTenSP);
        pnlForm.add(UITheme.createLabel("Resolution:"));      pnlForm.add(txtDPGiaiMH);
        pnlForm.add(UITheme.createLabel("CPU:"));             pnlForm.add(txtCPU);
        pnlForm.add(UITheme.createLabel("Stock Qty:"));       pnlForm.add(txtSoLuong);
        pnlForm.add(UITheme.createLabel("GPU:"));             pnlForm.add(txtGPU);
        pnlForm.add(UITheme.createLabel("Sale Price:"));      pnlForm.add(txtGiaBan);
        pnlForm.add(UITheme.createLabel("RAM (GB):"));        pnlForm.add(txtRAM);
        pnlForm.add(UITheme.createLabel("Import Price:"));    pnlForm.add(txtGiaNhap);
        pnlForm.add(UITheme.createLabel("Storage:"));         pnlForm.add(txtOCung);
        pnlForm.add(UITheme.createLabel("Warranty (months):")); pnlForm.add(txtBaoHanh);
        pnlForm.add(UITheme.createLabel("Weight (kg):"));     pnlForm.add(txtCanNang);
        pnlForm.add(new JLabel()); pnlForm.add(new JLabel()); // spacer
        add(pnlForm, BorderLayout.CENTER);

        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlBtns.setBackground(UITheme.BG_DARK);
        btnThem  = UITheme.createSuccessButton("Add Product");
        btnClear = UITheme.createDangerButton("Clear");
        pnlBtns.add(btnThem); pnlBtns.add(btnClear);
        add(pnlBtns, BorderLayout.SOUTH);

        controller = new ThemSanPhamController(new SanPhamDAOImpl(), this);
    }
}