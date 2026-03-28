package com.example.view;

import com.example.config.UITheme;
import com.example.entity.NhaCungCap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Giao diện quản lý Nhà Cung Cấp — Dark Mode
 */
public class NhaCungCapView extends JFrame {
    public JTextField txtMaNCC, txtTenNCC, txtSDT, txtDiaChi, txtTimKiem;
    public JButton btnThem, btnReload, btnSave, btnUpdate, btnDelete;
    public JTable tableNCC;

    public NhaCungCapView() {
        setTitle("Quản lý nhà cung cấp");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(10, 10));

        // === HEADER ===
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(UITheme.BG_DARK);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));
        JLabel lblTitle = UITheme.createTitleLabel("QUẢN LÝ NHÀ CUNG CẤP");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(UITheme.ACCENT);
        pnlHeader.add(lblTitle, BorderLayout.CENTER);
        btnThem = UITheme.createPrimaryButton("＋ Thêm NCC");
        pnlHeader.add(btnThem, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // === FORM ===
        JPanel pnlForm = UITheme.createCard();
        pnlForm.setLayout(new GridLayout(2, 4, 15, 10));
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER), BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        txtMaNCC = UITheme.createTextField(); txtMaNCC.setEditable(false);
        txtTenNCC = UITheme.createTextField();
        txtSDT = UITheme.createTextField();
        txtDiaChi = UITheme.createTextField();

        pnlForm.add(UITheme.createLabel("Mã NCC:")); pnlForm.add(txtMaNCC);
        pnlForm.add(UITheme.createLabel("Số điện thoại:")); pnlForm.add(txtSDT);
        pnlForm.add(UITheme.createLabel("Tên NCC:")); pnlForm.add(txtTenNCC);
        pnlForm.add(UITheme.createLabel("Địa chỉ:")); pnlForm.add(txtDiaChi);

        JPanel pnlFormWrap = new JPanel(new BorderLayout());
        pnlFormWrap.setBackground(UITheme.BG_DARK);
        pnlFormWrap.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        pnlFormWrap.add(pnlForm, BorderLayout.CENTER);
        add(pnlFormWrap, BorderLayout.NORTH);
        // re-add header on top
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(UITheme.BG_DARK);
        pnlTop.add(pnlHeader, BorderLayout.NORTH);
        pnlTop.add(pnlFormWrap, BorderLayout.CENTER);

        // Search bar
        JPanel pnlSearch = new JPanel(new BorderLayout(10, 0));
        pnlSearch.setBackground(UITheme.BG_DARK);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
        JLabel lblSearch = UITheme.createLabel("Tìm kiếm:");
        lblSearch.setFont(UITheme.FONT_SUBTITLE);
        txtTimKiem = UITheme.createTextField();
        btnReload = UITheme.createButton("Reload", UITheme.ACCENT_YELLOW);
        pnlSearch.add(lblSearch, BorderLayout.WEST);
        pnlSearch.add(txtTimKiem, BorderLayout.CENTER);
        pnlSearch.add(btnReload, BorderLayout.EAST);
        pnlTop.add(pnlSearch, BorderLayout.SOUTH);
        add(pnlTop, BorderLayout.NORTH);

        // === TABLE ===
        tableNCC = new JTable();
        UITheme.styleTable(tableNCC);
        JScrollPane sp = UITheme.createScrollPane(tableNCC);
        sp.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        add(sp, BorderLayout.CENTER);

        // === BUTTONS ===
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlButtons.setBackground(UITheme.BG_DARK);
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(5, 20, 15, 20));
        btnUpdate = UITheme.createButton("Sửa", UITheme.ACCENT_PURPLE);
        btnDelete = UITheme.createDangerButton("Xóa");
        btnSave = UITheme.createSuccessButton("Lưu");
        pnlButtons.add(btnUpdate); pnlButtons.add(btnDelete); pnlButtons.add(btnSave);
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

    public void hienThiDanhSachNCC(List<NhaCungCap> danhSach) {
        String[] cols = {"Mã NCC", "Tên NCC", "SĐT", "Địa chỉ"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (NhaCungCap ncc : danhSach) {
            model.addRow(new Object[]{ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSdt(), ncc.getDiaChi()});
        }
        tableNCC.setModel(model);
        UITheme.styleTable(tableNCC);
    }
}