package com.example.view;

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
        setLayout(null);

        // Tiêu đề
        JLabel lblTitle = new JLabel("XÁC NHẬN HÓA ĐƠN", JLabel.CENTER);
        lblTitle.setBounds(0, 10, 600, 30);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitle);

        // Khu vực hiển thị thông tin hóa đơn
        JLabel lblThongTin = new JLabel("Thông tin hóa đơn:");
        lblThongTin.setBounds(20, 50, 150, 25);
        add(lblThongTin);

        txtThongTinHoaDon = new JTextArea();
        txtThongTinHoaDon.setEditable(false);
        txtThongTinHoaDon.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollThongTin = new JScrollPane(txtThongTinHoaDon);
        scrollThongTin.setBounds(20, 80, 540, 150);
        add(scrollThongTin);

        // Bảng danh sách sản phẩm
        JLabel lblSanPham = new JLabel("Danh sách sản phẩm:");
        lblSanPham.setBounds(20, 240, 150, 25);
        add(lblSanPham);

        tableSanPham = new JTable();
        JScrollPane scrollSanPham = new JScrollPane(tableSanPham);
        scrollSanPham.setBounds(20, 270, 540, 150);
        add(scrollSanPham);

        // Nút Xác nhận
        btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.setBounds(350, 430, 100, 30);
        add(btnXacNhan);

        // Nút Hủy
        btnHuy = new JButton("Hủy");
        btnHuy.setBounds(460, 430, 100, 30);
        add(btnHuy);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(XacNhanHoaDonView::new);
    }
}