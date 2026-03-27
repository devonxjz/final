package com.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HoaDonView extends JPanel {
    public JTable tableSanPham, tableGioHang;
    public JTextField txtMaKH, txtTenKH, txtTongTien, txtLaiSuat, txtThoiHan;
    public JComboBox<String> cbLoaiHD;
    public JButton btnAddToCard, btnRemoveFromCard, btnThanhToan, btnHuy;
    public JPanel panelTraGop;

    public HoaDonView() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // 1. Danh sách sản phẩm (Bên trái)
        JPanel pnlLeft = new JPanel(new BorderLayout());
        pnlLeft.setBorder(BorderFactory.createTitledBorder("Danh mục Laptop"));
        tableSanPham = new JTable(new DefaultTableModel(new Object[]{"Mã", "Tên SP", "Giá bán", "Kho"}, 0));
        pnlLeft.add(new JScrollPane(tableSanPham), BorderLayout.CENTER);
        btnAddToCard = new JButton("Thêm vào giỏ >>");
        pnlLeft.add(btnAddToCard, BorderLayout.SOUTH);

        // 2. Giỏ hàng & Thanh toán (Bên phải)
        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBorder(BorderFactory.createTitledBorder("Chi tiết đơn hàng"));

        tableGioHang = new JTable(new DefaultTableModel(new Object[]{"Mã", "Tên SP", "SL", "Đơn giá", "Thành tiền"}, 0));
        pnlRight.add(new JScrollPane(tableGioHang), BorderLayout.CENTER);

        // Form thông tin khách hàng & Loại HD
        JPanel pnlThanhToan = new JPanel(new GridLayout(0, 2, 5, 5));
        txtMaKH = new JTextField(); txtTenKH = new JTextField();
        txtTongTien = new JTextField(); txtTongTien.setEditable(false);
        cbLoaiHD = new JComboBox<>(new String[]{"Trực tiếp", "Trả góp"});

        pnlThanhToan.add(new JLabel("Mã khách hàng:")); pnlThanhToan.add(txtMaKH);
        pnlThanhToan.add(new JLabel("Tên khách hàng:")); pnlThanhToan.add(txtTenKH);
        pnlThanhToan.add(new JLabel("Loại hình:")); pnlThanhToan.add(cbLoaiHD);
        pnlThanhToan.add(new JLabel("TỔNG TIỀN:")); pnlThanhToan.add(txtTongTien);

        // Panel phụ cho Trả góp
        panelTraGop = new JPanel(new GridLayout(1, 4, 5, 5));
        txtLaiSuat = new JTextField(); txtThoiHan = new JTextField();
        panelTraGop.add(new JLabel("Lãi suất (%):")); panelTraGop.add(txtLaiSuat);
        panelTraGop.add(new JLabel("Kỳ hạn (tháng):")); panelTraGop.add(txtThoiHan);

        JPanel pnlBottomRight = new JPanel(new BorderLayout());
        pnlBottomRight.add(pnlThanhToan, BorderLayout.NORTH);
        pnlBottomRight.add(panelTraGop, BorderLayout.CENTER);

        JPanel pnlBtns = new JPanel();
        btnThanhToan = new JButton("Thanh toán & In");
        btnHuy = new JButton("Hủy đơn");
        btnRemoveFromCard = new JButton("Xóa khỏi giỏ");
        pnlBtns.add(btnRemoveFromCard); pnlBtns.add(btnThanhToan); pnlBtns.add(btnHuy);
        pnlBottomRight.add(pnlBtns, BorderLayout.SOUTH);

        pnlRight.add(pnlBottomRight, BorderLayout.SOUTH);

        // Layout chính
        gbc.gridx = 0; gbc.weightx = 0.4; gbc.weighty = 1.0; add(pnlLeft, gbc);
        gbc.gridx = 1; gbc.weightx = 0.6; add(pnlRight, gbc);
    }

    public void hienThiDanhSachSanPham(java.util.List<com.example.model.SanPham> ds) {
        DefaultTableModel m = (DefaultTableModel) tableSanPham.getModel();
        m.setRowCount(0);
        for (var sp : ds) {
            m.addRow(new Object[]{sp.getMaSP(), sp.getTenSP(), sp.getGiaBan(), sp.getSoLuongTrongKho()});
        }
    }
}