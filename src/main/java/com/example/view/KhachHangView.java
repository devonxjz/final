package com.example.view;

import com.example.config.UITheme;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setBackground(UITheme.BG_DARK);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Header
        JPanel pnlHeader = new JPanel(new BorderLayout(10, 0));
        pnlHeader.setBackground(UITheme.BG_CARD);
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER, 1),
                new EmptyBorder(12, 18, 12, 18)));

        JLabel lblTitle = new JLabel("Customer Management");
        lblTitle.setFont(UITheme.FONT_SUBTITLE);
        lblTitle.setForeground(UITheme.TEXT_PRIMARY);

        JPanel pnlSearch = new JPanel(new BorderLayout(8, 0));
        pnlSearch.setOpaque(false);
        pnlSearch.setPreferredSize(new Dimension(350, 32));
        txtTimKiem = UITheme.createTextField();
        btnReload  = UITheme.createPrimaryButton("Refresh");
        pnlSearch.add(new JLabel("Search: "), BorderLayout.WEST);
        pnlSearch.add(txtTimKiem, BorderLayout.CENTER);
        pnlSearch.add(btnReload,  BorderLayout.EAST);

        pnlHeader.add(lblTitle,  BorderLayout.WEST);
        pnlHeader.add(pnlSearch, BorderLayout.EAST);

        // Table
        String[] headers = {"ID", "Full Name", "Phone", "Address", "Email", "Gender"};
        model = new DefaultTableModel(headers, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableKhachHang = new JTable(model);
        UITheme.styleTable(tableKhachHang);
        JScrollPane scrollPane = UITheme.createScrollPane(tableKhachHang);

        // Form (East)
        JPanel pnlInfo = new JPanel(new GridBagLayout());
        pnlInfo.setBackground(UITheme.BG_CARD);
        pnlInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER, 1),
                new EmptyBorder(15, 15, 15, 15)));
        pnlInfo.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        JLabel lblFormTitle = new JLabel("Customer Info");
        lblFormTitle.setFont(UITheme.FONT_SUBTITLE);
        lblFormTitle.setForeground(UITheme.ACCENT);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weightx = 1;
        pnlInfo.add(lblFormTitle, gbc);
        gbc.gridwidth = 1;

        txtMaKH   = UITheme.createTextField();
        txtTenKH  = UITheme.createTextField();
        txtSDT    = UITheme.createTextField();
        txtDiaChi = UITheme.createTextField();
        txtEmail  = UITheme.createTextField();
        cbGioiTinh = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        cbGioiTinh.setFont(UITheme.FONT_BODY);
        cbGioiTinh.setBackground(UITheme.BG_INPUT);
        cbGioiTinh.setForeground(UITheme.TEXT_PRIMARY);

        addFormField(pnlInfo, "Customer ID:", txtMaKH,    gbc, 1);
        addFormField(pnlInfo, "Full Name:",   txtTenKH,   gbc, 2);
        addFormField(pnlInfo, "Phone:",       txtSDT,     gbc, 3);
        addFormField(pnlInfo, "Address:",     txtDiaChi,  gbc, 4);
        addFormField(pnlInfo, "Email:",       txtEmail,   gbc, 5);
        addFormField(pnlInfo, "Gender:",      cbGioiTinh, gbc, 6);

        // Buttons
        JPanel pnlAction = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        pnlAction.setBackground(UITheme.BG_DARK);
        pnlAction.setBorder(new EmptyBorder(10, 0, 5, 0));

        btnAdd    = UITheme.createSuccessButton("+ Add New");
        btnUpdate = UITheme.createPrimaryButton("Edit");
        btnSave   = UITheme.createButton("Save", UITheme.ACCENT_PURPLE);
        btnDelete = UITheme.createDangerButton("Delete");

        pnlAction.add(btnAdd);
        pnlAction.add(btnUpdate);
        pnlAction.add(btnSave);
        pnlAction.add(btnDelete);

        add(pnlHeader,  BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(pnlInfo,    BorderLayout.EAST);
        add(pnlAction,  BorderLayout.SOUTH);
    }

    private void addFormField(JPanel p, String label, JComponent c, GridBagConstraints gbc, int y) {
        gbc.gridy = y;
        gbc.gridx = 0; gbc.weightx = 0;
        JLabel lbl = UITheme.createLabel(label);
        lbl.setPreferredSize(new Dimension(90, 25));
        p.add(lbl, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        p.add(c, gbc);
    }

    public void hienThiKhachHang(java.util.List<com.example.dto.KhachHangDTO> ds) {
        model.setRowCount(0);
        for (var kh : ds) {
            model.addRow(new Object[]{
                    kh.maKH(), kh.tenKH(), kh.sdt(),
                    kh.diaChi(), kh.email(), kh.gioiTinh()
            });
        }
    }
}