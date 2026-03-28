package com.example.view;

import com.example.config.UITheme;
import javax.swing.*;
import java.awt.*;

public class XacNhanHoaDonView extends JFrame {
    public JTextArea txtThongTinHoaDon;
    public JTable tableSanPham;
    public JButton btnXacNhan;
    public JButton btnHuy;

    public XacNhanHoaDonView() {
        setTitle("Xác nhận hóa đơn bán hàng");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel lblTitle = UITheme.createTitleLabel("XÁC NHẬN HÓA ĐƠN");
        lblTitle.setForeground(UITheme.ACCENT);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(12, 0, 5, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Center: Info + Table
        JPanel pnlCenter = new JPanel(new BorderLayout(5, 8));
        pnlCenter.setBackground(UITheme.BG_DARK);
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JPanel pnlInfo = new JPanel(new BorderLayout());
        pnlInfo.setBackground(UITheme.BG_CARD);
        pnlInfo.setPreferredSize(new Dimension(0, 160));
        JLabel lblInfo = UITheme.createLabel("Thông tin hóa đơn:");
        lblInfo.setFont(UITheme.FONT_SUBTITLE); lblInfo.setForeground(UITheme.ACCENT);
        lblInfo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
        pnlInfo.add(lblInfo, BorderLayout.NORTH);
        txtThongTinHoaDon = new JTextArea();
        txtThongTinHoaDon.setEditable(false);
        txtThongTinHoaDon.setFont(UITheme.FONT_BODY);
        txtThongTinHoaDon.setBackground(UITheme.BG_CARD);
        txtThongTinHoaDon.setForeground(UITheme.TEXT_PRIMARY);
        pnlInfo.add(new JScrollPane(txtThongTinHoaDon), BorderLayout.CENTER);
        pnlCenter.add(pnlInfo, BorderLayout.NORTH);

        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBackground(UITheme.BG_CARD);
        JLabel lblSP = UITheme.createLabel("Danh sách sản phẩm:");
        lblSP.setFont(UITheme.FONT_SUBTITLE); lblSP.setForeground(UITheme.ACCENT);
        lblSP.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
        pnlTable.add(lblSP, BorderLayout.NORTH);
        tableSanPham = new JTable(); UITheme.styleTable(tableSanPham);
        pnlTable.add(UITheme.createScrollPane(tableSanPham), BorderLayout.CENTER);
        pnlCenter.add(pnlTable, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // Buttons
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlBtns.setBackground(UITheme.BG_DARK);
        pnlBtns.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));
        btnXacNhan = UITheme.createSuccessButton("Xác nhận");
        btnHuy = UITheme.createDangerButton("Hủy");
        pnlBtns.add(btnXacNhan); pnlBtns.add(btnHuy);
        add(pnlBtns, BorderLayout.SOUTH);

        setVisible(true);
    }
}