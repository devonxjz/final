package com.example.view;

import com.example.config.UITheme;
import com.example.controller.ThemSanPhamController;
import com.example.dao.SanPhamDAO;
import com.example.dao.impl.SanPhamDAOImpl;
import javax.swing.*;
import java.awt.*;

public class ThemSanPhamView extends JFrame {
    public JTextField txtLoaiMay, txtTenSP, txtCPU, txtGPU, txtRAM, txtOCung, txtCanNang;
    public JTextField txtKTManHinh, txtDPGiaiMH, txtSoLuong, txtGiaBan, txtGiaNhap, txtBaoHanh;
    public JButton btnThem, btnClear;
    private ThemSanPhamController controller;

    public ThemSanPhamView() {
        setTitle("ThÃªm sáº£n pháº©m");
        setSize(750, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = UITheme.createTitleLabel("THÃŠM Sáº¢N PHáº¨M");
        lblTitle.setForeground(UITheme.ACCENT);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlForm = UITheme.createCard();
        pnlForm.setLayout(new GridLayout(7, 4, 12, 8));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        txtLoaiMay = UITheme.createTextField(); txtTenSP = UITheme.createTextField();
        txtCPU = UITheme.createTextField(); txtGPU = UITheme.createTextField();
        txtRAM = UITheme.createTextField(); txtOCung = UITheme.createTextField();
        txtCanNang = UITheme.createTextField(); txtKTManHinh = UITheme.createTextField();
        txtDPGiaiMH = UITheme.createTextField(); txtSoLuong = UITheme.createTextField();
        txtGiaBan = UITheme.createTextField(); txtGiaNhap = UITheme.createTextField();
        txtBaoHanh = UITheme.createTextField();

        pnlForm.add(UITheme.createLabel("Loáº¡i mÃ¡y:")); pnlForm.add(txtLoaiMay);
        pnlForm.add(UITheme.createLabel("KT mÃ n hÃ¬nh:")); pnlForm.add(txtKTManHinh);
        pnlForm.add(UITheme.createLabel("TÃªn SP:")); pnlForm.add(txtTenSP);
        pnlForm.add(UITheme.createLabel("Äá»™ phÃ¢n giáº£i:")); pnlForm.add(txtDPGiaiMH);
        pnlForm.add(UITheme.createLabel("CPU:")); pnlForm.add(txtCPU);
        pnlForm.add(UITheme.createLabel("Sá»‘ lÆ°á»£ng:")); pnlForm.add(txtSoLuong);
        pnlForm.add(UITheme.createLabel("GPU:")); pnlForm.add(txtGPU);
        pnlForm.add(UITheme.createLabel("GiÃ¡ bÃ¡n:")); pnlForm.add(txtGiaBan);
        pnlForm.add(UITheme.createLabel("RAM (GB):")); pnlForm.add(txtRAM);
        pnlForm.add(UITheme.createLabel("GiÃ¡ nháº­p:")); pnlForm.add(txtGiaNhap);
        pnlForm.add(UITheme.createLabel("á»” cá»©ng:")); pnlForm.add(txtOCung);
        pnlForm.add(UITheme.createLabel("Báº£o hÃ nh (thÃ¡ng):")); pnlForm.add(txtBaoHanh);
        pnlForm.add(UITheme.createLabel("CÃ¢n náº·ng (kg):")); pnlForm.add(txtCanNang);
        pnlForm.add(new JLabel()); pnlForm.add(new JLabel()); // spacer
        add(pnlForm, BorderLayout.CENTER);

        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlBtns.setBackground(UITheme.BG_DARK);
        btnThem = UITheme.createSuccessButton("ThÃªm SP");
        btnClear = UITheme.createDangerButton("Clear");
        pnlBtns.add(btnThem); pnlBtns.add(btnClear);
        add(pnlBtns, BorderLayout.SOUTH);

        controller = new ThemSanPhamController(new SanPhamDAOImpl(), this);
    }
}