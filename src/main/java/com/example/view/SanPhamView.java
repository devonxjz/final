package com.example.view;

import com.example.config.UIThemeConfig;
import com.example.dto.SanPhamDTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * SanPhamView — Product management (Glassmorphism dark mode).
 */
public class SanPhamView extends JPanel {
    public JTextField txtMaSP, txtLoaiMay, txtTenSP, txtCPU, txtGPU;
    public JTextField txtRAM, txtOCung, txtKTManHinh, txtDPGManHinh;
    public JTextField txtCanNang, txtSLTrongKho, txtGiaBan, txtGiaNhap, txtThoiGianBaoHanh;
    public JTextField txtTimKiem;
    public JButton btnThem, btnSave, btnUpdate, btnDelete, btnReload;
    public JTable tableSanPham;
    public DefaultTableModel model;

    public SanPhamView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIThemeConfig.BG_DARK);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // ── Header ──
        JPanel pnlHeader = UIThemeConfig.createGlassPanel(new BorderLayout(10, 0));
        pnlHeader.setBorder(new EmptyBorder(12, 18, 12, 18));
        JLabel lblTitle = new JLabel("Product Management");
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
        String[] headers = {"ID", "Type", "Name", "CPU", "GPU", "RAM", "Storage",
                "Screen", "Resolution", "Weight", "Stock", "Price", "Import", "Warranty"};
        model = new DefaultTableModel(headers, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableSanPham = new JTable(model);
        UIThemeConfig.styleTable(tableSanPham);
        JScrollPane scrollPane = UIThemeConfig.createScrollPane(tableSanPham);

        // ── Form (East) ──
        JPanel pnlInfo = UIThemeConfig.createGlassPanel(new GridBagLayout());
        pnlInfo.setBorder(new EmptyBorder(15, 15, 15, 15));
        pnlInfo.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 5, 4, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblFormTitle = new JLabel("Product Details");
        lblFormTitle.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblFormTitle.setForeground(UIThemeConfig.ACCENT);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weightx = 1;
        pnlInfo.add(lblFormTitle, gbc);
        gbc.gridwidth = 1;

        txtMaSP = UIThemeConfig.createTextField(); txtMaSP.setEditable(false);
        txtLoaiMay = UIThemeConfig.createTextField();
        txtTenSP = UIThemeConfig.createTextField();
        txtCPU = UIThemeConfig.createTextField();
        txtGPU = UIThemeConfig.createTextField();
        txtRAM = UIThemeConfig.createTextField();
        txtOCung = UIThemeConfig.createTextField();
        txtKTManHinh = UIThemeConfig.createTextField();
        txtDPGManHinh = UIThemeConfig.createTextField();
        txtCanNang = UIThemeConfig.createTextField();
        txtSLTrongKho = UIThemeConfig.createTextField();
        txtGiaBan = UIThemeConfig.createTextField();
        txtGiaNhap = UIThemeConfig.createTextField();
        txtThoiGianBaoHanh = UIThemeConfig.createTextField();

        addFormField(pnlInfo, "Product ID:", txtMaSP, gbc, 1);
        addFormField(pnlInfo, "Type:", txtLoaiMay, gbc, 2);
        addFormField(pnlInfo, "Name:", txtTenSP, gbc, 3);
        addFormField(pnlInfo, "CPU:", txtCPU, gbc, 4);
        addFormField(pnlInfo, "GPU:", txtGPU, gbc, 5);
        addFormField(pnlInfo, "RAM:", txtRAM, gbc, 6);
        addFormField(pnlInfo, "Storage:", txtOCung, gbc, 7);
        addFormField(pnlInfo, "Screen:", txtKTManHinh, gbc, 8);
        addFormField(pnlInfo, "Resolution:", txtDPGManHinh, gbc, 9);
        addFormField(pnlInfo, "Weight:", txtCanNang, gbc, 10);
        addFormField(pnlInfo, "Stock:", txtSLTrongKho, gbc, 11);
        addFormField(pnlInfo, "Price:", txtGiaBan, gbc, 12);
        addFormField(pnlInfo, "Import Price:", txtGiaNhap, gbc, 13);
        addFormField(pnlInfo, "Warranty:", txtThoiGianBaoHanh, gbc, 14);

        // ── Action Buttons ──
        JPanel pnlAction = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pnlAction.setOpaque(false);
        pnlAction.setBorder(new EmptyBorder(10, 0, 5, 0));

        btnThem = UIThemeConfig.createSuccessButton("+ Add New");
        btnUpdate = UIThemeConfig.createPrimaryButton("Edit");
        btnSave = UIThemeConfig.createButton("Save", UIThemeConfig.ACCENT_PURPLE);
        btnDelete = UIThemeConfig.createDangerButton("Delete");

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
        gbc.gridx = 0; gbc.weightx = 0;
        JLabel lbl = UIThemeConfig.createLabel(label);
        lbl.setPreferredSize(new Dimension(90, 25));
        p.add(lbl, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        p.add(c, gbc);
    }

    public void hienThiSanPham(List<SanPhamDTO> ds) {
        model.setRowCount(0);
        for (SanPhamDTO sp : ds) {
            model.addRow(new Object[]{
                    sp.maSP(), sp.loaiMay(), sp.tenSP(), sp.CPU(), sp.GPU(),
                    sp.RAM(), sp.oCung(), sp.kichThuocMH(), sp.doPhanGiaiMH(),
                    sp.canNang(), sp.soLuongTrongKho(), sp.giaBan(), sp.giaNhap(), sp.thoiGianBaoHanh()
            });
        }
    }
}
