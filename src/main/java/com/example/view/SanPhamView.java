package com.example.view;

import com.example.config.UITheme;
import com.example.entity.SanPham;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SanPhamView extends JPanel {

    public JTable tableSanPham;
    public DefaultTableModel tableModel;
    public JTextField txtMaSP, txtTenSP, txtLoaiMay, txtCPU, txtGPU, txtRAM,
            txtOCung, txtKTManHinh, txtDPGManHinh, txtCanNang,
            txtSLTrongKho, txtGiaBan, txtGiaNhap, txtThoiGianBaoHanh, txtTimKiem;
    public JButton btnThem, btnUpdate, btnDelete, btnSave, btnReload;

    public SanPhamView() {
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

        JLabel lblTitle = new JLabel("Product Management");
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

        pnlHeader.add(lblTitle,   BorderLayout.WEST);
        pnlHeader.add(pnlSearch,  BorderLayout.EAST);

        // Table
        String[] columns = {
                "ID", "Type", "Product Name", "CPU", "GPU", "RAM (GB)",
                "Storage", "Screen Size", "Resolution", "Weight (kg)",
                "Stock", "Sale Price", "Import Price", "Warranty (months)"
        };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableSanPham = new JTable(tableModel);
        UITheme.styleTable(tableSanPham);
        JScrollPane scrollPane = UITheme.createScrollPane(tableSanPham);

        // Form (East)
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(UITheme.BG_CARD);
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER, 1),
                new EmptyBorder(15, 15, 15, 15)));
        pnlForm.setPreferredSize(new Dimension(320, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 5, 4, 5);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel lblFormTitle = new JLabel("Product Details");
        lblFormTitle.setFont(UITheme.FONT_SUBTITLE);
        lblFormTitle.setForeground(UITheme.ACCENT);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        pnlForm.add(lblFormTitle, gbc);
        gbc.gridwidth = 1;

        txtMaSP            = UITheme.createTextField();
        txtTenSP           = UITheme.createTextField();
        txtLoaiMay         = UITheme.createTextField();
        txtCPU             = UITheme.createTextField();
        txtGPU             = UITheme.createTextField();
        txtRAM             = UITheme.createTextField();
        txtOCung           = UITheme.createTextField();
        txtKTManHinh       = UITheme.createTextField();
        txtDPGManHinh      = UITheme.createTextField();
        txtCanNang         = UITheme.createTextField();
        txtSLTrongKho      = UITheme.createTextField();
        txtGiaBan          = UITheme.createTextField();
        txtGiaNhap         = UITheme.createTextField();
        txtThoiGianBaoHanh = UITheme.createTextField();

        String[] labels = {
                "Product ID:", "Name:", "Type:", "CPU:", "GPU:", "RAM (GB):",
                "Storage:", "Screen Size:", "Resolution:", "Weight:",
                "Stock:", "Sale Price:", "Import Price:", "Warranty:"
        };
        JTextField[] fields = {
                txtMaSP, txtTenSP, txtLoaiMay, txtCPU, txtGPU,
                txtRAM, txtOCung, txtKTManHinh, txtDPGManHinh,
                txtCanNang, txtSLTrongKho, txtGiaBan, txtGiaNhap, txtThoiGianBaoHanh
        };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridy = i + 1;
            gbc.gridx = 0; gbc.weightx = 0;
            JLabel lbl = UITheme.createLabel(labels[i]);
            lbl.setPreferredSize(new Dimension(100, 25));
            pnlForm.add(lbl, gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            pnlForm.add(fields[i], gbc);
        }

        // Buttons
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        pnlButtons.setBackground(UITheme.BG_DARK);
        pnlButtons.setBorder(new EmptyBorder(10, 0, 5, 0));

        btnThem   = UITheme.createSuccessButton("+ Add New");
        btnUpdate = UITheme.createPrimaryButton("Edit");
        btnSave   = UITheme.createButton("Save", UITheme.ACCENT_PURPLE);
        btnDelete = UITheme.createDangerButton("Delete");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnUpdate);
        pnlButtons.add(btnSave);
        pnlButtons.add(btnDelete);

        add(pnlHeader,   BorderLayout.NORTH);
        add(scrollPane,  BorderLayout.CENTER);
        add(pnlForm,     BorderLayout.EAST);
        add(pnlButtons,  BorderLayout.SOUTH);
    }

    public void hienThiSanPham(List<SanPham> ds) {
        tableModel.setRowCount(0);
        for (SanPham sp : ds) {
            tableModel.addRow(new Object[]{
                    sp.getMaSP(), sp.getLoaiMay(), sp.getTenSP(),
                    sp.getCPU(), sp.getGPU(), sp.getRAM(),
                    sp.getOCung(), sp.getKichThuocMH(), sp.getDoPhanGiaiMH(),
                    sp.getCanNang(), sp.getSoLuongTrongKho(),
                    sp.getGiaBan()  != null ? String.format("%,.0f", sp.getGiaBan())  : "",
                    sp.getGiaNhap() != null ? String.format("%,.0f", sp.getGiaNhap()) : "",
                    sp.getThoiGianBaoHanh()
            });
        }
    }
}