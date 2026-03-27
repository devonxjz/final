package com.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KhachHangView extends JPanel {
    public JTable tableKhachHang;
    public DefaultTableModel model;
    public JTextField txtMaKH, txtTenKH, txtSDT, txtDiaChi, txtEmail, txtTimKiem;
    public JComboBox<String> cbGioiTinh;
    public JButton btnAdd, btnUpdate, btnDelete, btnSave, btnReload;

    public KhachHangView() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // --- Panel Tìm kiếm (North) ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTimKiem = new JTextField(20);
        btnReload = new JButton("Làm mới");
        pnlSearch.add(new JLabel("Tìm kiếm (Tên/SĐT): "));
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnReload);

        // --- Table (Center) ---
        String[] headers = {"Mã KH", "Tên khách hàng", "SĐT", "Địa chỉ", "Email", "Giới tính"};
        model = new DefaultTableModel(headers, 0);
        tableKhachHang = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableKhachHang);

        // --- Form nhập liệu (East) ---
        JPanel pnlInfo = new JPanel(new GridBagLayout());
        pnlInfo.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        pnlInfo.setPreferredSize(new Dimension(300, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaKH = new JTextField(); txtTenKH = new JTextField();
        txtSDT = new JTextField(); txtDiaChi = new JTextField();
        txtEmail = new JTextField();
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});

        addFormField(pnlInfo, "Mã KH:", txtMaKH, gbc, 0);
        addFormField(pnlInfo, "Họ tên:", txtTenKH, gbc, 1);
        addFormField(pnlInfo, "SĐT:", txtSDT, gbc, 2);
        addFormField(pnlInfo, "Địa chỉ:", txtDiaChi, gbc, 3);
        addFormField(pnlInfo, "Email:", txtEmail, gbc, 4);
        addFormField(pnlInfo, "Giới tính:", cbGioiTinh, gbc, 5);

        // --- Buttons (South) ---
        JPanel pnlAction = new JPanel();
        btnAdd = new JButton("Thêm mới");
        btnUpdate = new JButton("Sửa");
        btnSave = new JButton("Lưu");
        btnDelete = new JButton("Xóa");
        pnlAction.add(btnAdd); pnlAction.add(btnUpdate);
        pnlAction.add(btnSave); pnlAction.add(btnDelete);

        add(pnlSearch, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(pnlInfo, BorderLayout.EAST);
        add(pnlAction, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel p, String label, JComponent c, GridBagConstraints gbc, int y) {
        gbc.gridx = 0; gbc.gridy = y; p.add(new JLabel(label), gbc);
        gbc.gridx = 1; p.add(c, gbc);
    }

    public void hienThiKhachHang(java.util.List<com.example.model.KhachHang> ds) {
        model.setRowCount(0);
        for (var kh : ds) {
            model.addRow(new Object[]{kh.getMaKH(), kh.getTenKH(), kh.getSdt(), kh.getDiaChi(), kh.getEmail(), kh.getGioiTinh()});
        }
    }
}