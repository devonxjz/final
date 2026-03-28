package com.example.view;

import com.example.config.UITheme;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;

public class HoaDonBanHangView extends JFrame {
    public JDateChooser dateChooser;
    public JButton btnTimKiem, btnThem, btnReload;
    public JTable tableDsHDBH, tableChiTietHD, tableThanhToan;

    public HoaDonBanHangView() {
        setTitle("Quản lý hoá đơn bán hàng");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(8, 8));

        // === HEADER ===
        JPanel pnlHeader = new JPanel(new BorderLayout(10, 0));
        pnlHeader.setBackground(UITheme.BG_DARK);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(12, 20, 5, 20));

        JLabel lblTitle = UITheme.createTitleLabel("QUẢN LÝ HOÁ ĐƠN BÁN HÀNG");
        lblTitle.setForeground(UITheme.ACCENT);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pnlHeader.add(lblTitle, BorderLayout.CENTER);

        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlTop.setBackground(UITheme.BG_DARK);
        pnlTop.add(UITheme.createLabel("Chọn ngày:"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setPreferredSize(new Dimension(130, 30));
        pnlTop.add(dateChooser);
        btnTimKiem = UITheme.createPrimaryButton("Tìm kiếm");
        pnlTop.add(btnTimKiem);
        btnThem = UITheme.createSuccessButton("＋ Thêm HĐ");
        pnlTop.add(btnThem);

        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setBackground(UITheme.BG_DARK);
        pnlNorth.add(pnlHeader, BorderLayout.NORTH);
        pnlNorth.add(pnlTop, BorderLayout.SOUTH);
        add(pnlNorth, BorderLayout.NORTH);

        // === TABLE DS HOÁ ĐƠN (Center) ===
        tableDsHDBH = new JTable();
        UITheme.styleTable(tableDsHDBH);
        JScrollPane spHD = UITheme.createScrollPane(tableDsHDBH);
        add(spHD, BorderLayout.CENTER);

        // === BOTTOM: Chi tiết + Thanh toán ===
        JPanel pnlBottom = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlBottom.setBackground(UITheme.BG_DARK);
        pnlBottom.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
        pnlBottom.setPreferredSize(new Dimension(0, 200));

        // Chi tiết
        JPanel pnlChiTiet = new JPanel(new BorderLayout());
        pnlChiTiet.setBackground(UITheme.BG_CARD);
        JLabel lblCT = UITheme.createLabel("Chi tiết hóa đơn");
        lblCT.setFont(UITheme.FONT_SUBTITLE); lblCT.setForeground(UITheme.ACCENT);
        lblCT.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
        pnlChiTiet.add(lblCT, BorderLayout.NORTH);
        tableChiTietHD = new JTable(); UITheme.styleTable(tableChiTietHD);
        pnlChiTiet.add(UITheme.createScrollPane(tableChiTietHD), BorderLayout.CENTER);
        pnlBottom.add(pnlChiTiet);

        // Thanh toán
        JPanel pnlTT = new JPanel(new BorderLayout());
        pnlTT.setBackground(UITheme.BG_CARD);
        JLabel lblTT = UITheme.createLabel("Thanh toán");
        lblTT.setFont(UITheme.FONT_SUBTITLE); lblTT.setForeground(UITheme.ACCENT);
        lblTT.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
        pnlTT.add(lblTT, BorderLayout.NORTH);
        tableThanhToan = new JTable(); UITheme.styleTable(tableThanhToan);
        pnlTT.add(UITheme.createScrollPane(tableThanhToan), BorderLayout.CENTER);
        pnlBottom.add(pnlTT);
        add(pnlBottom, BorderLayout.SOUTH);

        // Reload
        btnReload = UITheme.createButton("Reload", UITheme.ACCENT_YELLOW);
        JPanel pnlReload = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlReload.setBackground(UITheme.BG_DARK);
        pnlReload.add(btnReload);
        pnlNorth.add(pnlReload, BorderLayout.CENTER);

        setVisible(true);
    }
}