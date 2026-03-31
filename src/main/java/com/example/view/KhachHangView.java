package com.example.view;

import com.example.config.UIThemeConfig;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * KhachHangView — Customer management (Glassmorphism dark mode).
 */
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
        setBackground(UIThemeConfig.BG_DARK);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // ── Header ──
        JPanel pnlHeader = UIThemeConfig.createGlassPanel(new BorderLayout(10, 0));
        pnlHeader.setBorder(new EmptyBorder(12, 18, 12, 18));

        JLabel lblTitle = new JLabel("Customer Management");
        lblTitle.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblTitle.setForeground(UIThemeConfig.TEXT_PRIMARY);

        JPanel pnlSearch = new JPanel(new BorderLayout(8, 0));
        pnlSearch.setOpaque(false);
        pnlSearch.setPreferredSize(new Dimension(350, 32));
        txtTimKiem = UIThemeConfig.createTextField();
        btnReload = UIThemeConfig.createPrimaryButton("Refresh");
        pnlSearch.add(UIThemeConfig.createLabel("Search:"), BorderLayout.WEST);
        pnlSearch.add(txtTimKiem, BorderLayout.CENTER);
        pnlSearch.add(btnReload, BorderLayout.EAST);

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(pnlSearch, BorderLayout.EAST);

        // ── Table ──
        String[] headers = {"ID", "Full Name", "Phone", "Address", "Email", "Gender"};
        model = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tableKhachHang = new JTable(model);
        UIThemeConfig.styleTable(tableKhachHang);
        JScrollPane scrollPane = UIThemeConfig.createScrollPane(tableKhachHang);

        // ── Form (East) ──
        JPanel pnlInfo = UIThemeConfig.createGlassPanel(new GridBagLayout());
        pnlInfo.setBorder(new EmptyBorder(15, 15, 15, 15));
        pnlInfo.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblFormTitle = new JLabel("Customer Info");
        lblFormTitle.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblFormTitle.setForeground(UIThemeConfig.ACCENT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        pnlInfo.add(lblFormTitle, gbc);
        gbc.gridwidth = 1;

        txtMaKH = UIThemeConfig.createTextField();
        txtTenKH = UIThemeConfig.createTextField();
        txtSDT = UIThemeConfig.createTextField();
        txtDiaChi = UIThemeConfig.createTextField();
        txtEmail = UIThemeConfig.createTextField();
        cbGioiTinh = UIThemeConfig.createComboBox(new String[]{"Male", "Female", "Other"});

        addFormField(pnlInfo, "Customer ID:", txtMaKH, gbc, 1);
        addFormField(pnlInfo, "Full Name:", txtTenKH, gbc, 2);
        addFormField(pnlInfo, "Phone:", txtSDT, gbc, 3);
        addFormField(pnlInfo, "Address:", txtDiaChi, gbc, 4);
        addFormField(pnlInfo, "Email:", txtEmail, gbc, 5);
        addFormField(pnlInfo, "Gender:", cbGioiTinh, gbc, 6);

        // ── Buttons ──
        JPanel pnlAction = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        pnlAction.setOpaque(false);
        pnlAction.setBorder(new EmptyBorder(10, 0, 5, 0));

        btnAdd = UIThemeConfig.createSuccessButton("+ Add New");
        btnUpdate = UIThemeConfig.createPrimaryButton("Edit");
        btnSave = UIThemeConfig.createButton("Save", UIThemeConfig.ACCENT_PURPLE);
        btnDelete = UIThemeConfig.createDangerButton("Delete");

        pnlAction.add(btnAdd);
        pnlAction.add(btnUpdate);
        pnlAction.add(btnSave);
        pnlAction.add(btnDelete);

        add(pnlHeader, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(pnlInfo, BorderLayout.EAST);
        add(pnlAction, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel p, String label, JComponent c, GridBagConstraints gbc, int y) {
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.weightx = 0;
        JLabel lbl = UIThemeConfig.createLabel(label);
        lbl.setPreferredSize(new Dimension(90, 25));
        p.add(lbl, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
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
