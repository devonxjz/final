package com.example.view;

import com.example.config.UITheme;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(UITheme.BG_DARK);

        // Top: Date filter
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlTop.setBackground(UITheme.BG_CARD);
        pnlTop.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER),
                new EmptyBorder(8, 12, 8, 12)));

        JLabel lblFilter = UITheme.createLabel("Date Filter");
        lblFilter.setFont(UITheme.FONT_SUBTITLE);
        lblFilter.setForeground(UITheme.ACCENT);
        pnlTop.add(lblFilter);

        pnlTop.add(UITheme.createLabel("From:"));
        dateFrom = new JDateChooser();
        dateFrom.setDateFormatString("yyyy-MM-dd");
        dateFrom.setPreferredSize(new Dimension(150, 30));
        pnlTop.add(dateFrom);

        pnlTop.add(UITheme.createLabel("To:"));
        dateTo = new JDateChooser();
        dateTo.setDateFormatString("yyyy-MM-dd");
        dateTo.setPreferredSize(new Dimension(150, 30));
        pnlTop.add(dateTo);

        btnSubmit      = UITheme.createSuccessButton("Generate Report");
        btnExportExcel = UITheme.createPrimaryButton("Export Excel");
        pnlTop.add(btnSubmit);
        pnlTop.add(btnExportExcel);

        add(pnlTop, BorderLayout.NORTH);

        // Center
        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setBackground(UITheme.BG_DARK);

        // Summary cards
        JPanel pnlCards = new JPanel(new GridLayout(1, 3, 15, 0));
        pnlCards.setBackground(UITheme.BG_DARK);
        pnlCards.setBorder(new EmptyBorder(10, 0, 10, 0));

        lblTongDoanhThu = new JLabel("0 VND");
        lblTongDonHang  = new JLabel("0");
        lblLoiNhuan     = new JLabel("0 VND");

        pnlCards.add(createCard("TOTAL REVENUE",  lblTongDoanhThu, new Color(251, 146, 60),  new Color(30, 41, 59)));
        pnlCards.add(createCard("TOTAL ORDERS",   lblTongDonHang,  new Color(56, 189, 248),  new Color(30, 41, 59)));
        pnlCards.add(createCard("TOTAL PROFIT",   lblLoiNhuan,     new Color(52, 211, 153),  new Color(30, 41, 59)));

        pnlCenter.add(pnlCards, BorderLayout.NORTH);

        // Content: Chart + Best Seller table
        JPanel pnlContent = new JPanel(new BorderLayout(10, 10));
        pnlContent.setBackground(UITheme.BG_DARK);
        pnlContent.setBorder(new EmptyBorder(5, 0, 0, 0));

        // Best seller table
        JPanel pnlBestSeller = new JPanel(new BorderLayout());
        pnlBestSeller.setBackground(UITheme.BG_CARD);
        pnlBestSeller.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER),
                new EmptyBorder(8, 8, 8, 8)));
        pnlBestSeller.setPreferredSize(new Dimension(350, 0));

        JLabel lblBestSeller = UITheme.createLabel("Top 5 Best Sellers");
        lblBestSeller.setFont(UITheme.FONT_SUBTITLE);
        lblBestSeller.setForeground(UITheme.ACCENT_YELLOW);
        lblBestSeller.setBorder(new EmptyBorder(0, 5, 8, 0));
        pnlBestSeller.add(lblBestSeller, BorderLayout.NORTH);

        tableBestSeller = new JTable(new DefaultTableModel(
                new Object[]{"Product Name", "Quantity Sold"}, 0));
        UITheme.styleTable(tableBestSeller);
        tableBestSeller.setRowHeight(30);
        pnlBestSeller.add(UITheme.createScrollPane(tableBestSeller), BorderLayout.CENTER);

        pnlContent.add(pnlBestSeller, BorderLayout.WEST);

        // Chart panel
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
                BorderFactory.createLineBorder(
                        new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 80), 1),
                new EmptyBorder(18, 18, 18, 18)));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
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
        Date d = dateFrom.getDate();
        return d != null ? new SimpleDateFormat("yyyy-MM-dd").format(d) : null;
    }

    public String getDenNgay() {
        Date d = dateTo.getDate();
        return d != null ? new SimpleDateFormat("yyyy-MM-dd").format(d) : null;
    }
}