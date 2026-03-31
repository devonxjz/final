package com.example.view;

import com.example.config.UIThemeConfig;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Xác nhận hóa đơn — nhúng vào ContentPanel, không mở cửa sổ mới.
 */
public class XacNhanHoaDonView extends JPanel {

    public JTextArea txtThongTinHoaDon;
    public JTable tableSanPham;
    public JButton btnXacNhan;
    public JButton btnHuy;

    public XacNhanHoaDonView() {
        setLayout(new BorderLayout(0, 0));
        setBackground(UIThemeConfig.BG_DARK);

        // ── HEADER ──
        JPanel pnlHeader = UIThemeConfig.createGlassPanel(new BorderLayout());
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, UIThemeConfig.ACCENT),
                new EmptyBorder(16, 26, 16, 26)));

        JLabel lblTitle = UIThemeConfig.createTitleLabel("XÁC NHẬN ĐƠN HÀNG");
        lblTitle.setForeground(UIThemeConfig.ACCENT);
        JLabel lblSub = UIThemeConfig.createLabel("Kiểm tra chi tiết đơn hàng trước khi xác nhận.");
        lblSub.setForeground(UIThemeConfig.TEXT_MUTED);

        JPanel pnlHdrText = new JPanel();
        pnlHdrText.setOpaque(false);
        pnlHdrText.setLayout(new BoxLayout(pnlHdrText, BoxLayout.Y_AXIS));
        pnlHdrText.add(lblTitle);
        pnlHdrText.add(Box.createRigidArea(new Dimension(0, 3)));
        pnlHdrText.add(lblSub);
        pnlHeader.add(pnlHdrText, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        // ── CENTER ──
        JPanel pnlCenter = new JPanel(new BorderLayout(0, 12));
        pnlCenter.setBackground(UIThemeConfig.BG_DARK);
        pnlCenter.setBorder(new EmptyBorder(20, 24, 12, 24));

        // Info section
        JPanel pnlInfo = UIThemeConfig.createGlassPanel(new BorderLayout());
        pnlInfo.setBorder(new EmptyBorder(10, 14, 10, 14));
        pnlInfo.setPreferredSize(new Dimension(0, 170));

        JLabel lblInfo = UIThemeConfig.createLabel("Thông tin đơn hàng");
        lblInfo.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblInfo.setForeground(UIThemeConfig.ACCENT);
        lblInfo.setBorder(new EmptyBorder(0, 0, 6, 0));
        pnlInfo.add(lblInfo, BorderLayout.NORTH);

        txtThongTinHoaDon = new JTextArea();
        txtThongTinHoaDon.setEditable(false);
        txtThongTinHoaDon.setFont(UIThemeConfig.FONT_BODY);
        txtThongTinHoaDon.setBackground(UIThemeConfig.BG_CARD);
        txtThongTinHoaDon.setForeground(UIThemeConfig.TEXT_PRIMARY);
        txtThongTinHoaDon.setLineWrap(true);
        txtThongTinHoaDon.setWrapStyleWord(true);
        pnlInfo.add(new JScrollPane(txtThongTinHoaDon), BorderLayout.CENTER);
        pnlCenter.add(pnlInfo, BorderLayout.NORTH);

        // Table section
        JPanel pnlTable = UIThemeConfig.createGlassPanel(new BorderLayout());
        pnlTable.setBorder(new EmptyBorder(10, 14, 10, 14));

        JLabel lblSP = UIThemeConfig.createLabel("Sản phẩm trong đơn hàng");
        lblSP.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblSP.setForeground(UIThemeConfig.ACCENT_YELLOW);
        lblSP.setBorder(new EmptyBorder(0, 0, 6, 0));
        pnlTable.add(lblSP, BorderLayout.NORTH);

        tableSanPham = new JTable();
        UIThemeConfig.styleTable(tableSanPham);
        pnlTable.add(UIThemeConfig.createScrollPane(tableSanPham), BorderLayout.CENTER);
        pnlCenter.add(pnlTable, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);

        // ── BUTTONS ──
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 12));
        pnlBtns.setBackground(UIThemeConfig.BG_DARK);
        pnlBtns.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, UIThemeConfig.BORDER),
                new EmptyBorder(4, 20, 4, 20)));

        btnHuy = UIThemeConfig.createDangerButton("Hủy");
        btnXacNhan = UIThemeConfig.createSuccessButton("Xác nhận đơn hàng");
        btnXacNhan.setPreferredSize(new Dimension(160, 36));

        pnlBtns.add(btnHuy);
        pnlBtns.add(btnXacNhan);
        add(pnlBtns, BorderLayout.SOUTH);
    }
}