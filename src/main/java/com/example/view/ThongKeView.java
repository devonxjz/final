package com.example.view;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        setBackground(Color.WHITE);

        // --- TOP PANEL: Bộ lọc ngày ---
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlTop.setBackground(Color.WHITE);
        pnlTop.setBorder(new TitledBorder("Bộ lọc thời gian"));

        pnlTop.add(new JLabel("Từ ngày:"));
        dateTuNgay = new JDateChooser();
        dateTuNgay.setDateFormatString("yyyy-MM-dd");
        dateTuNgay.setPreferredSize(new Dimension(150, 30));
        pnlTop.add(dateTuNgay);

        pnlTop.add(new JLabel("Đến ngày:"));
        dateDenNgay = new JDateChooser();
        dateDenNgay.setDateFormatString("yyyy-MM-dd");
        dateDenNgay.setPreferredSize(new Dimension(150, 30));
        pnlTop.add(dateDenNgay);

        btnSubmit = new JButton("Thống kê");
        btnSubmit.setBackground(new Color(0, 153, 102));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        pnlTop.add(btnSubmit);

        btnExportExcel = new JButton("Xuất Excel");
        btnExportExcel.setBackground(new Color(51, 102, 255));
        btnExportExcel.setForeground(Color.WHITE);
        btnExportExcel.setFocusPainted(false);
        pnlTop.add(btnExportExcel);

        add(pnlTop, BorderLayout.NORTH);

        // --- CENTER PANEL: Hiển thị Thống kê ---
        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setBackground(Color.WHITE);

        // 1. Panel Tổng quan (Cards)
        JPanel pnlCards = new JPanel(new GridLayout(1, 3, 15, 0));
        pnlCards.setBackground(Color.WHITE);

        pnlCards.add(createCard("TỔNG DOANH THU", lblTongDoanhThu = new JLabel("0 VNĐ"), new Color(255, 235, 204), new Color(230, 81, 0)));
        pnlCards.add(createCard("TỔNG ĐƠN HÀNG MỚI", lblTongDonHang = new JLabel("0"), new Color(224, 247, 250), new Color(0, 96, 100)));
        pnlCards.add(createCard("TỔNG LỢI NHUẬN", lblLoiNhuan = new JLabel("0 VNĐ"), new Color(232, 245, 233), new Color(27, 94, 32)));

        pnlCenter.add(pnlCards, BorderLayout.NORTH);

        // 2. Content: Biểu đồ và Bảng Best Seller
        JPanel pnlContent = new JPanel(new BorderLayout(10, 10));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(15, 0, 0, 0));

        // Bảng Best Seller
        JPanel pnlBestSeller = new JPanel(new BorderLayout());
        pnlBestSeller.setBackground(Color.WHITE);
        pnlBestSeller.setBorder(new TitledBorder("Top 5 Sản Phẩm Bán Chạy"));
        pnlBestSeller.setPreferredSize(new Dimension(350, 0));
        
        tableBestSeller = new JTable(new DefaultTableModel(new Object[]{"Tên Sản Phẩm", "Số Lượng Đã Bán"}, 0));
        tableBestSeller.setRowHeight(30);
        tableBestSeller.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(tableBestSeller);
        pnlBestSeller.add(scrollPane, BorderLayout.CENTER);
        
        pnlContent.add(pnlBestSeller, BorderLayout.WEST);

        // Chart 
        panelChart = new JPanel(new BorderLayout());
        panelChart.setBackground(Color.WHITE);
        panelChart.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        pnlContent.add(panelChart, BorderLayout.CENTER);

        pnlCenter.add(pnlContent, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, JLabel valueLabel, Color bgColor, Color fgColor) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(fgColor, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setForeground(fgColor);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(fgColor);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(lblTitle);
        panel.add(valueLabel);
        return panel;
    }

    public String getTuNgay() {
        Date d = dateTuNgay.getDate();
        if (d != null) {
            return new SimpleDateFormat("yyyy-MM-dd").format(d);
        }
        return null; // Không cấu hình ngày bắt đầu
    }

    public String getDenNgay() {
        Date d = dateDenNgay.getDate();
        if (d != null) {
            return new SimpleDateFormat("yyyy-MM-dd").format(d);
        }
        return null;
    }
}
