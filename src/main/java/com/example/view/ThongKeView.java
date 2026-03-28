package com.example.view;

import com.example.config.UITheme;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Giao diện Thống Kê — Dark Mode.
 * Extends JPanel để nhúng vào content area của HomeView.
 */
public class ThongKeView extends JPanel {

    public JButton btnSubmit;
    public JButton btnExportExcel;

    private JDateChooser dateTuNgay;
    private JDateChooser dateDenNgay;

    public JLabel lblTongDoanhThu;
    public JLabel lblTongDonHang;
    public JLabel lblLoiNhuan;

    public JTable tableBestSeller;
    public JPanel panelChart;

    public ThongKeView() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(UITheme.BG_DARK);

        // --- TOP PANEL: Bộ lọc ngày ---
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlTop.setBackground(UITheme.BG_CARD);
        pnlTop.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER),
                new EmptyBorder(8, 12, 8, 12)));

        JLabel lblFilter = UITheme.createLabel("\uD83D\uDCC5  B\u1ED9 l\u1ECDc th\u1EDDi gian");
        lblFilter.setFont(UITheme.FONT_SUBTITLE);
        lblFilter.setForeground(UITheme.ACCENT);
        pnlTop.add(lblFilter);

        pnlTop.add(UITheme.createLabel("T\u1EEB ng\u00E0y:"));
        dateTuNgay = new JDateChooser();
        dateTuNgay.setDateFormatString("yyyy-MM-dd");
        dateTuNgay.setPreferredSize(new Dimension(150, 30));
        pnlTop.add(dateTuNgay);

        pnlTop.add(UITheme.createLabel("\u0110\u1EBFn ng\u00E0y:"));
        dateDenNgay = new JDateChooser();
        dateDenNgay.setDateFormatString("yyyy-MM-dd");
        dateDenNgay.setPreferredSize(new Dimension(150, 30));
        pnlTop.add(dateDenNgay);

        btnSubmit = UITheme.createSuccessButton("Th\u1ED1ng k\u00EA");
        pnlTop.add(btnSubmit);

        btnExportExcel = UITheme.createPrimaryButton("Xu\u1EA5t Excel");
        pnlTop.add(btnExportExcel);

        add(pnlTop, BorderLayout.NORTH);

        // --- CENTER PANEL: Hiển thị Thống kê ---
        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setBackground(UITheme.BG_DARK);

        // 1. Panel Tổng quan (Cards) — Styled cho dark mode
        JPanel pnlCards = new JPanel(new GridLayout(1, 3, 15, 0));
        pnlCards.setBackground(UITheme.BG_DARK);
        pnlCards.setBorder(new EmptyBorder(10, 0, 10, 0));

        lblTongDoanhThu = new JLabel("0 VN\u0110");
        lblTongDonHang = new JLabel("0");
        lblLoiNhuan = new JLabel("0 VN\u0110");

        pnlCards.add(createCard("\uD83D\uDCB0 T\u1ED4NG DOANH THU", lblTongDoanhThu,
                new Color(251, 146, 60), new Color(30, 41, 59)));
        pnlCards.add(createCard("\uD83D\uDCE6 T\u1ED4NG \u0110\u01A0N H\u00C0NG", lblTongDonHang,
                new Color(56, 189, 248), new Color(30, 41, 59)));
        pnlCards.add(createCard("\uD83D\uDCCA T\u1ED4NG L\u1EE2I NHU\u1EAEN", lblLoiNhuan,
                new Color(52, 211, 153), new Color(30, 41, 59)));

        pnlCenter.add(pnlCards, BorderLayout.NORTH);

        // 2. Content: Biểu đồ và Bảng Best Seller
        JPanel pnlContent = new JPanel(new BorderLayout(10, 10));
        pnlContent.setBackground(UITheme.BG_DARK);
        pnlContent.setBorder(new EmptyBorder(5, 0, 0, 0));

        // Bảng Best Seller
        JPanel pnlBestSeller = new JPanel(new BorderLayout());
        pnlBestSeller.setBackground(UITheme.BG_CARD);
        pnlBestSeller.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER),
                new EmptyBorder(8, 8, 8, 8)));
        pnlBestSeller.setPreferredSize(new Dimension(350, 0));

        JLabel lblBestSeller = UITheme.createLabel("\uD83C\uDFC6 Top 5 S\u1EA3n Ph\u1EA9m B\u00E1n Ch\u1EA1y");
        lblBestSeller.setFont(UITheme.FONT_SUBTITLE);
        lblBestSeller.setForeground(UITheme.ACCENT_YELLOW);
        lblBestSeller.setBorder(new EmptyBorder(0, 5, 8, 0));
        pnlBestSeller.add(lblBestSeller, BorderLayout.NORTH);

        tableBestSeller = new JTable(new DefaultTableModel(new Object[]{"T\u00EAn S\u1EA3n Ph\u1EA9m", "S\u1ED1 L\u01B0\u1EE3ng \u0110\u00E3 B\u00E1n"}, 0));
        UITheme.styleTable(tableBestSeller);
        tableBestSeller.setRowHeight(30);
        pnlBestSeller.add(UITheme.createScrollPane(tableBestSeller), BorderLayout.CENTER);

        pnlContent.add(pnlBestSeller, BorderLayout.WEST);

        // Chart
        panelChart = new JPanel(new BorderLayout());
        panelChart.setBackground(UITheme.BG_CARD);
        panelChart.setBorder(BorderFactory.createLineBorder(UITheme.BORDER));
        pnlContent.add(panelChart, BorderLayout.CENTER);

        pnlCenter.add(pnlContent, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, JLabel valueLabel, Color accentColor, Color bgColor) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(accentColor.getRed(), accentColor.getGreen(),
                        accentColor.getBlue(), 80), 1),
                new EmptyBorder(18, 18, 18, 18)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        lblTitle.setForeground(accentColor);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLabel.setForeground(UITheme.TEXT_PRIMARY);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(lblTitle);
        panel.add(valueLabel);
        return panel;
    }

    public String getTuNgay() {
        Date d = dateTuNgay.getDate();
        if (d != null) return new SimpleDateFormat("yyyy-MM-dd").format(d);
        return null;
    }

    public String getDenNgay() {
        Date d = dateDenNgay.getDate();
        if (d != null) return new SimpleDateFormat("yyyy-MM-dd").format(d);
        return null;
    }
}
