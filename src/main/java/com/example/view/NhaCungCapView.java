package com.example.view;

import com.example.config.UIThemeConfig;
import com.example.entity.NhaCungCap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * NhaCungCapView — Supplier management (Glassmorphism dark mode).
 * Layout: Header (NORTH) | Table (CENTER) | Form panel (EAST) | Buttons (SOUTH)
 */
public class NhaCungCapView extends JPanel {

    public JTable tableNCC;
    public DefaultTableModel model;
    public JTextField txtMaNCC, txtTenNCC, txtSDT, txtDiaChi, txtTimKiem;
    public JButton btnThem, btnUpdate, btnDelete, btnSave, btnReload;

    public NhaCungCapView() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIThemeConfig.BG_DARK);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // ── Header ──
        JPanel pnlHeader = UIThemeConfig.createGlassPanel(new BorderLayout(10, 0));
        pnlHeader.setBorder(new EmptyBorder(12, 18, 12, 18));

        JLabel lblTitle = new JLabel("Quản lý nhà cung cấp");
        lblTitle.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblTitle.setForeground(UIThemeConfig.TEXT_PRIMARY);

        JPanel pnlSearch = new JPanel(new BorderLayout(8, 0));
        pnlSearch.setOpaque(false);
        pnlSearch.setPreferredSize(new Dimension(350, 32));
        txtTimKiem = UIThemeConfig.createTextField();
        btnReload = UIThemeConfig.createPrimaryButton("Làm mới");
        pnlSearch.add(UIThemeConfig.createLabel("Tìm kiếm:"), BorderLayout.WEST);
        pnlSearch.add(txtTimKiem, BorderLayout.CENTER);
        pnlSearch.add(btnReload, BorderLayout.EAST);

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(pnlSearch, BorderLayout.EAST);

        // ── Table ──
        String[] headers = { "Mã NCC", "Tên nhà cung cấp", "Số điện thoại", "Địa chỉ" };
        model = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tableNCC = new JTable(model);
        UIThemeConfig.styleTable(tableNCC);
        JScrollPane scrollPane = UIThemeConfig.createScrollPane(tableNCC);

        // ── Form (East) ──
        JPanel pnlInfo = UIThemeConfig.createGlassPanel(new GridBagLayout());
        pnlInfo.setBorder(new EmptyBorder(15, 15, 15, 15));
        pnlInfo.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblFormTitle = new JLabel("Thông tin nhà cung cấp");
        lblFormTitle.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblFormTitle.setForeground(UIThemeConfig.ACCENT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        pnlInfo.add(lblFormTitle, gbc);
        gbc.gridwidth = 1;

        txtMaNCC = UIThemeConfig.createTextField();
        txtTenNCC = UIThemeConfig.createTextField();
        txtSDT = UIThemeConfig.createTextField();
        txtDiaChi = UIThemeConfig.createTextField();

        addFormField(pnlInfo, "Mã NCC:", txtMaNCC, gbc, 1);
        addFormField(pnlInfo, "Tên NCC:", txtTenNCC, gbc, 2);
        addFormField(pnlInfo, "Số điện thoại:", txtSDT, gbc, 3);
        addFormField(pnlInfo, "Địa chỉ:", txtDiaChi, gbc, 4);

        // ── Buttons ──
        JPanel pnlAction = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        pnlAction.setOpaque(false);
        pnlAction.setBorder(new EmptyBorder(10, 0, 5, 0));

        btnThem = UIThemeConfig.createSuccessButton("+ Thêm mới");
        btnUpdate = UIThemeConfig.createPrimaryButton("Sửa");
        btnSave = UIThemeConfig.createButton("Lưu", UIThemeConfig.ACCENT_PURPLE);
        btnDelete = UIThemeConfig.createDangerButton("Xóa");

        pnlAction.add(btnThem);
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

    public void setEditableFields(boolean editable) {
        txtMaNCC.setEditable(false); // Mã NCC luôn read-only
        txtTenNCC.setEditable(editable);
        txtSDT.setEditable(editable);
        txtDiaChi.setEditable(editable);
        Color bg = editable ? UIThemeConfig.BG_INPUT : UIThemeConfig.BG_INPUT.darker();
        txtTenNCC.setBackground(bg);
        txtSDT.setBackground(bg);
        txtDiaChi.setBackground(bg);
    }

    public void hienThiDanhSachNCC(List<NhaCungCap> list) {
        model.setRowCount(0);
        for (NhaCungCap ncc : list) {
            model.addRow(new Object[] {
                    ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSdt(), ncc.getDiaChi()
            });
        }
    }
}