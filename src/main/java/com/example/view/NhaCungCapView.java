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
 */
public class NhaCungCapView extends JPanel {
    public JTextField txtMaNCC, txtTenNCC, txtSDT, txtDiaChi, txtTimKiem;
    public JButton btnThem, btnReload, btnSave, btnUpdate, btnDelete;
    public JTable tableNCC;

    public NhaCungCapView() {
        setBackground(UIThemeConfig.BG_DARK);
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // ── Header ──
        JPanel pnlHeader = UIThemeConfig.createGlassPanel(new BorderLayout(10, 0));
        pnlHeader.setBorder(new EmptyBorder(12, 18, 12, 18));
        JLabel lblTitle = new JLabel("Supplier Management");
        lblTitle.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblTitle.setForeground(UIThemeConfig.TEXT_PRIMARY);
        btnThem = UIThemeConfig.createSuccessButton("+ Add Supplier");
        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(btnThem, BorderLayout.EAST);

        // ── Form ──
        JPanel pnlForm = UIThemeConfig.createGlassPanel(new GridLayout(2, 4, 15, 10));
        pnlForm.setBorder(new EmptyBorder(15, 20, 15, 20));

        txtMaNCC = UIThemeConfig.createTextField(); txtMaNCC.setEditable(false);
        txtTenNCC = UIThemeConfig.createTextField();
        txtSDT = UIThemeConfig.createTextField();
        txtDiaChi = UIThemeConfig.createTextField();

        pnlForm.add(UIThemeConfig.createLabel("Supplier ID:"));  pnlForm.add(txtMaNCC);
        pnlForm.add(UIThemeConfig.createLabel("Phone:"));        pnlForm.add(txtSDT);
        pnlForm.add(UIThemeConfig.createLabel("Supplier Name:")); pnlForm.add(txtTenNCC);
        pnlForm.add(UIThemeConfig.createLabel("Address:"));      pnlForm.add(txtDiaChi);

        JPanel pnlFormWrap = new JPanel(new BorderLayout());
        pnlFormWrap.setOpaque(false);
        pnlFormWrap.setBorder(new EmptyBorder(8, 0, 0, 0));
        pnlFormWrap.add(pnlForm, BorderLayout.CENTER);

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setOpaque(false);
        pnlTop.add(pnlHeader, BorderLayout.NORTH);
        pnlTop.add(pnlFormWrap, BorderLayout.CENTER);

        // ── Search bar ──
        JPanel pnlSearch = new JPanel(new BorderLayout(10, 0));
        pnlSearch.setOpaque(false);
        pnlSearch.setBorder(new EmptyBorder(10, 0, 5, 0));
        JLabel lblSearch = UIThemeConfig.createLabel("Search:");
        lblSearch.setFont(UIThemeConfig.FONT_SUBTITLE);
        txtTimKiem = UIThemeConfig.createTextField();
        btnReload = UIThemeConfig.createButton("Reload", UIThemeConfig.ACCENT_YELLOW);
        pnlSearch.add(lblSearch, BorderLayout.WEST);
        pnlSearch.add(txtTimKiem, BorderLayout.CENTER);
        pnlSearch.add(btnReload, BorderLayout.EAST);
        pnlTop.add(pnlSearch, BorderLayout.SOUTH);

        add(pnlTop, BorderLayout.NORTH);

        // ── Table ──
        tableNCC = new JTable();
        UIThemeConfig.styleTable(tableNCC);
        JScrollPane sp = UIThemeConfig.createScrollPane(tableNCC);
        add(sp, BorderLayout.CENTER);

        // ── Buttons ──
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlButtons.setOpaque(false);
        btnUpdate = UIThemeConfig.createButton("Edit", UIThemeConfig.ACCENT_PURPLE);
        btnDelete = UIThemeConfig.createDangerButton("Delete");
        btnSave = UIThemeConfig.createSuccessButton("Save");
        pnlButtons.add(btnUpdate);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnSave);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    public void setEditableFields(boolean editable) {
        txtMaNCC.setEditable(false);
        txtTenNCC.setEditable(editable);
        txtDiaChi.setEditable(editable);
        txtSDT.setEditable(editable);
        Color bg = editable ? UIThemeConfig.BG_INPUT : UIThemeConfig.BG_INPUT.darker();
        txtTenNCC.setBackground(bg);
        txtDiaChi.setBackground(bg);
        txtSDT.setBackground(bg);
    }

    public void hienThiDanhSachNCC(List<NhaCungCap> list) {
        String[] cols = {"Supplier ID", "Supplier Name", "Phone", "Address"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (NhaCungCap ncc : list) {
            model.addRow(new Object[]{
                    ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSdt(), ncc.getDiaChi()
            });
        }
        tableNCC.setModel(model);
        UIThemeConfig.styleTable(tableNCC);
    }
}
