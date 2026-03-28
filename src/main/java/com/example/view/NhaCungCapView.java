package com.example.view;

import com.example.config.UITheme;
import com.example.entity.NhaCungCap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NhaCungCapView extends JFrame {
    public JTextField txtMaNCC, txtTenNCC, txtSDT, txtDiaChi, txtTimKiem;
    public JButton btnThem, btnReload, btnSave, btnUpdate, btnDelete;
    public JTable tableNCC;

    public NhaCungCapView() {
        setTitle("Supplier Management");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(UITheme.BG_DARK);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));
        JLabel lblTitle = UITheme.createTitleLabel("SUPPLIER MANAGEMENT");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(UITheme.ACCENT);
        pnlHeader.add(lblTitle, BorderLayout.CENTER);
        btnThem = UITheme.createPrimaryButton("+ Add Supplier");
        pnlHeader.add(btnThem, BorderLayout.EAST);

        // Form
        JPanel pnlForm = UITheme.createCard();
        pnlForm.setLayout(new GridLayout(2, 4, 15, 10));
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        txtMaNCC  = UITheme.createTextField(); txtMaNCC.setEditable(false);
        txtTenNCC = UITheme.createTextField();
        txtSDT    = UITheme.createTextField();
        txtDiaChi = UITheme.createTextField();

        pnlForm.add(UITheme.createLabel("Supplier ID:"));  pnlForm.add(txtMaNCC);
        pnlForm.add(UITheme.createLabel("Phone:"));        pnlForm.add(txtSDT);
        pnlForm.add(UITheme.createLabel("Supplier Name:")); pnlForm.add(txtTenNCC);
        pnlForm.add(UITheme.createLabel("Address:"));      pnlForm.add(txtDiaChi);

        JPanel pnlFormWrap = new JPanel(new BorderLayout());
        pnlFormWrap.setBackground(UITheme.BG_DARK);
        pnlFormWrap.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        pnlFormWrap.add(pnlForm, BorderLayout.CENTER);

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(UITheme.BG_DARK);
        pnlTop.add(pnlHeader,   BorderLayout.NORTH);
        pnlTop.add(pnlFormWrap, BorderLayout.CENTER);

        // Search bar
        JPanel pnlSearch = new JPanel(new BorderLayout(10, 0));
        pnlSearch.setBackground(UITheme.BG_DARK);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
        JLabel lblSearch = UITheme.createLabel("Search:");
        lblSearch.setFont(UITheme.FONT_SUBTITLE);
        txtTimKiem = UITheme.createTextField();
        btnReload  = UITheme.createButton("Reload", UITheme.ACCENT_YELLOW);
        pnlSearch.add(lblSearch,   BorderLayout.WEST);
        pnlSearch.add(txtTimKiem,  BorderLayout.CENTER);
        pnlSearch.add(btnReload,   BorderLayout.EAST);
        pnlTop.add(pnlSearch, BorderLayout.SOUTH);

        add(pnlTop, BorderLayout.NORTH);

        // Table
        tableNCC = new JTable();
        UITheme.styleTable(tableNCC);
        JScrollPane sp = UITheme.createScrollPane(tableNCC);
        sp.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        add(sp, BorderLayout.CENTER);

        // Buttons
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlButtons.setBackground(UITheme.BG_DARK);
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(5, 20, 15, 20));
        btnUpdate = UITheme.createButton("Edit",   UITheme.ACCENT_PURPLE);
        btnDelete = UITheme.createDangerButton("Delete");
        btnSave   = UITheme.createSuccessButton("Save");
        pnlButtons.add(btnUpdate);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnSave);
        add(pnlButtons, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setEditableFields(boolean editable) {
        txtMaNCC.setEditable(false);
        txtTenNCC.setEditable(editable);
        txtDiaChi.setEditable(editable);
        txtSDT.setEditable(editable);
        Color bg = editable ? UITheme.BG_INPUT : UITheme.BG_INPUT.darker();
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
        UITheme.styleTable(tableNCC);
    }
}