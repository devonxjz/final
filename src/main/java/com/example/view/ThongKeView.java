package com.example.view;

import com.example.config.UIThemeConfig;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ThongKeView — Analytics / Reports (Glassmorphism dark mode).
 */
public class ThongKeView extends JPanel {

    public JButton btnSubmit;
    public JButton btnExportExcel;

    private JDateChooser dateFrom;
    private JDateChooser dateTo;

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
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(UIThemeConfig.BG_DARK);

        // ── Header: Date filter ──
        JPanel pnlTop = UIThemeConfig.createGlassPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlTop.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel lblFilter = new JLabel("Analytics");
        lblFilter.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblFilter.setForeground(UIThemeConfig.TEXT_PRIMARY);
        pnlTop.add(lblFilter);

        pnlTop.add(UIThemeConfig.createLabel("From:"));
        dateFrom = new JDateChooser();
        dateFrom.setDateFormatString("yyyy-MM-dd");
        dateFrom.setPreferredSize(new Dimension(150, 30));
        pnlTop.add(dateFrom);

        pnlTop.add(UIThemeConfig.createLabel("To:"));
        dateTo = new JDateChooser();
        dateTo.setDateFormatString("yyyy-MM-dd");
        dateTo.setPreferredSize(new Dimension(150, 30));
        pnlTop.add(dateTo);

        btnSubmit = UIThemeConfig.createSuccessButton("Generate Report");
        btnExportExcel = UIThemeConfig.createPrimaryButton("Export Excel");
        pnlTop.add(btnSubmit);
        pnlTop.add(btnExportExcel);

        add(pnlTop, BorderLayout.NORTH);

        // ── Center ──
        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setOpaque(false);

        // Summary stat cards (using glassmorphism stat cards)
        JPanel pnlCards = new JPanel(new GridLayout(1, 3, 15, 0));
        pnlCards.setOpaque(false);
        pnlCards.setBorder(new EmptyBorder(10, 0, 10, 0));

        lblTongDoanhThu = new JLabel("0 VND");
        lblTongDonHang = new JLabel("0");
        lblLoiNhuan = new JLabel("0 VND");

        pnlCards.add(createStatCard("TOTAL REVENUE", lblTongDoanhThu, new Color(251, 146, 60)));
        pnlCards.add(createStatCard("TOTAL ORDERS", lblTongDonHang, new Color(56, 189, 248)));
        pnlCards.add(createStatCard("TOTAL PROFIT", lblLoiNhuan, new Color(52, 211, 153)));

        pnlCenter.add(pnlCards, BorderLayout.NORTH);

        // Content: Best Seller table + Chart
        JPanel pnlContent = new JPanel(new BorderLayout(10, 10));
        pnlContent.setOpaque(false);

        // Best seller table
        JPanel pnlBestSeller = UIThemeConfig.createGlassPanel(new BorderLayout(0, 8));
        pnlBestSeller.setBorder(new EmptyBorder(12, 12, 12, 12));
        pnlBestSeller.setPreferredSize(new Dimension(350, 0));

        JLabel lblBestSeller = new JLabel("Top 5 Best Sellers");
        lblBestSeller.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblBestSeller.setForeground(UIThemeConfig.ACCENT_YELLOW);
        pnlBestSeller.add(lblBestSeller, BorderLayout.NORTH);

        tableBestSeller = new JTable(new DefaultTableModel(
                new Object[]{"Product Name", "Quantity Sold"}, 0));
        UIThemeConfig.styleTable(tableBestSeller);
        tableBestSeller.setRowHeight(30);
        pnlBestSeller.add(UIThemeConfig.createScrollPane(tableBestSeller), BorderLayout.CENTER);

        pnlContent.add(pnlBestSeller, BorderLayout.WEST);

        // Chart panel
        panelChart = UIThemeConfig.createGlassPanel(new BorderLayout());
        panelChart.setBorder(new EmptyBorder(12, 12, 12, 12));
        pnlContent.add(panelChart, BorderLayout.CENTER);

        pnlCenter.add(pnlContent, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);
    }

    /** Glassmorphism stat card with accent glow */
    private JPanel createStatCard(String title, JLabel valueLabel, Color accentColor) {
        JPanel panel = new JPanel(new GridLayout(2, 1)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIThemeConfig.BG_GLASS);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                // Accent top bar
                g2.setColor(accentColor);
                g2.fillRoundRect(0, 0, getWidth(), 3, 4, 4);
                // Border
                g2.setColor(UIThemeConfig.BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        panel.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(accentColor);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        valueLabel.setFont(UIThemeConfig.FONT_STAT);
        valueLabel.setForeground(UIThemeConfig.TEXT_PRIMARY);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(lblTitle);
        panel.add(valueLabel);
        return panel;
    }

    public String getTuNgay() {
        Date d = dateFrom.getDate();
        return d != null ? new SimpleDateFormat("yyyy-MM-dd").format(d) : null;
    }

    public String getDenNgay() {
        Date d = dateTo.getDate();
        return d != null ? new SimpleDateFormat("yyyy-MM-dd").format(d) : null;
    }
}
