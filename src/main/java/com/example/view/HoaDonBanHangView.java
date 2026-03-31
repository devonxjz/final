package com.example.view;

import com.example.config.UIThemeConfig;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * HoaDonBanHangView — Sales order management (Glassmorphism dark mode).
 */
public class HoaDonBanHangView extends JPanel {
    public JDateChooser dateChooser;
    public JButton btnTimKiem, btnThem, btnReload;
    public JTable tableDsHDBH, tableChiTietHD, tableThanhToan;

    public HoaDonBanHangView() {
        setBackground(UIThemeConfig.BG_DARK);
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // ── Header ──
        JPanel pnlHeader = UIThemeConfig.createGlassPanel(new BorderLayout(10, 0));
        pnlHeader.setBorder(new EmptyBorder(12, 18, 12, 18));

        JLabel lblTitle = new JLabel("Quản lý đơn hàng bán");
        lblTitle.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblTitle.setForeground(UIThemeConfig.TEXT_PRIMARY);

        JPanel pnlTools = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlTools.setOpaque(false);
        pnlTools.add(UIThemeConfig.createLabel("Ngày:"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setPreferredSize(new Dimension(130, 30));
        dateChooser.setBackground(UIThemeConfig.BG_INPUT);
        pnlTools.add(dateChooser);
        btnTimKiem = UIThemeConfig.createPrimaryButton("Tìm kiếm");
        pnlTools.add(btnTimKiem);
        btnThem = UIThemeConfig.createSuccessButton("+ Thêm đơn hàng");
        pnlTools.add(btnThem);
        btnReload = UIThemeConfig.createButton("Tải lại", UIThemeConfig.ACCENT_YELLOW);
        pnlTools.add(btnReload);

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(pnlTools, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // ── Main table ──
        tableDsHDBH = new JTable();
        UIThemeConfig.styleTable(tableDsHDBH);
        add(UIThemeConfig.createScrollPane(tableDsHDBH), BorderLayout.CENTER);

        // ── Bottom: Order detail + Payment ──
        JPanel pnlBottom = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlBottom.setOpaque(false);
        pnlBottom.setPreferredSize(new Dimension(0, 200));

        // Chi tiết
        JPanel pnlChiTiet = UIThemeConfig.createGlassPanel(new BorderLayout());
        pnlChiTiet.setBorder(new EmptyBorder(10, 12, 10, 12));
        JLabel lblCT = new JLabel("Chi tiết đơn hàng");
        lblCT.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblCT.setForeground(UIThemeConfig.ACCENT);
        pnlChiTiet.add(lblCT, BorderLayout.NORTH);
        tableChiTietHD = new JTable();
        UIThemeConfig.styleTable(tableChiTietHD);
        pnlChiTiet.add(UIThemeConfig.createScrollPane(tableChiTietHD), BorderLayout.CENTER);
        pnlBottom.add(pnlChiTiet);

        // Thanh toán
        JPanel pnlTT = UIThemeConfig.createGlassPanel(new BorderLayout());
        pnlTT.setBorder(new EmptyBorder(10, 12, 10, 12));
        JLabel lblTT = new JLabel("Thanh toán");
        lblTT.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblTT.setForeground(UIThemeConfig.ACCENT);
        pnlTT.add(lblTT, BorderLayout.NORTH);
        tableThanhToan = new JTable();
        UIThemeConfig.styleTable(tableThanhToan);
        pnlTT.add(UIThemeConfig.createScrollPane(tableThanhToan), BorderLayout.CENTER);
        pnlBottom.add(pnlTT);

        add(pnlBottom, BorderLayout.SOUTH);
    }
}
