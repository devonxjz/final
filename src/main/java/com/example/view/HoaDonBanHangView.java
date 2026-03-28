package com.example.view;

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
        setLayout(null);

        JPanel panelMain = new JPanel();
        panelMain.setLayout(null);
        panelMain.setBackground(new Color(0, 204, 204));
        panelMain.setBounds(0, 0, 1000, 700);
        add(panelMain);

        // Tiêu đề chính
        JLabel lblTitle = new JLabel("QUẢN LÝ HOÁ ĐƠN BÁN HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(173, 240, 255));
        lblTitle.setBounds(250, 10, 500, 40);
        panelMain.add(lblTitle);

        // Phần chọn ngày và tìm kiếm
        JLabel lblNgay = new JLabel("Chọn ngày:");
        lblNgay.setBounds(30, 60, 100, 25);
        panelMain.add(lblNgay);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setBounds(110, 60, 120, 25);
        panelMain.add(dateChooser);

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBounds(240, 60, 100, 25);
        btnTimKiem.setBackground(Color.WHITE);
        btnTimKiem.setForeground(Color.RED);
        panelMain.add(btnTimKiem);

        btnThem = new JButton("Thêm hóa đơn");
        btnThem.setBounds(850, 60, 120, 25);
        btnThem.setForeground(Color.BLUE);
        panelMain.add(btnThem);

        // Bảng danh sách hóa đơn bán hàng
        tableDsHDBH = new JTable();
        JScrollPane scrollPaneDsHDBH = new JScrollPane(tableDsHDBH);
        scrollPaneDsHDBH.setBounds(30, 100, 940, 300);
        panelMain.add(scrollPaneDsHDBH);

        // Nút Reload
        btnReload = new JButton("Reload");
        btnReload.setBounds(890, 420, 80, 30);
        btnReload.setBackground(new Color(255, 255, 204));
        btnReload.setForeground(Color.RED);
        panelMain.add(btnReload);

        // Bảng chi tiết hóa đơn
        JLabel lblChiTiet = new JLabel("Chi tiết hóa đơn");
        lblChiTiet.setFont(new Font("Arial", Font.BOLD, 16));
        lblChiTiet.setBounds(30, 460, 150, 25);
        panelMain.add(lblChiTiet);

        tableChiTietHD = new JTable();
        JScrollPane scrollPaneChiTietHD = new JScrollPane(tableChiTietHD);
        scrollPaneChiTietHD.setBounds(30, 490, 450, 150);
        panelMain.add(scrollPaneChiTietHD);

        // Bảng thanh toán
        JLabel lblThanhToan = new JLabel("Thanh toán");
        lblThanhToan.setFont(new Font("Arial", Font.BOLD, 16));
        lblThanhToan.setBounds(520, 460, 150, 25);
        panelMain.add(lblThanhToan);

        tableThanhToan = new JTable();
        JScrollPane scrollPaneThanhToan = new JScrollPane(tableThanhToan);
        scrollPaneThanhToan.setBounds(520, 490, 450, 150);
        panelMain.add(scrollPaneThanhToan);

        setVisible(true); // Hiển thị form
    }
}