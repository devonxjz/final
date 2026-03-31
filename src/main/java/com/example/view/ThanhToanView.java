package com.example.view;

import com.example.config.UIThemeConfig;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * ThanhToanView — Payment management (Glassmorphism dark mode).
 */
public class ThanhToanView extends JPanel {

    private JTable tableThanhToan;
    private DefaultTableModel tableModel;
    private JTextField txtTimKiem;
    private JButton btnThem, btnReload, btnXoa;

    public ThanhToanView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIThemeConfig.BG_DARK);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // ── Header ──
        JPanel pnlHeader = UIThemeConfig.createGlassPanel(new BorderLayout(10, 0));
        pnlHeader.setBorder(new EmptyBorder(12, 18, 12, 18));

        JLabel lblTitle = new JLabel("Quản lý thanh toán");
        lblTitle.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblTitle.setForeground(UIThemeConfig.TEXT_PRIMARY);

        JPanel pnlTools = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlTools.setOpaque(false);
        txtTimKiem = UIThemeConfig.createTextField();
        txtTimKiem.setPreferredSize(new Dimension(200, 30));
        btnThem = UIThemeConfig.createSuccessButton("+ Thêm thanh toán");
        btnReload = UIThemeConfig.createPrimaryButton("Làm mới");
        btnXoa = UIThemeConfig.createDangerButton("Xóa");
        pnlTools.add(UIThemeConfig.createLabel("Tìm kiếm:"));
        pnlTools.add(txtTimKiem);
        pnlTools.add(btnThem);
        pnlTools.add(btnXoa);
        pnlTools.add(btnReload);

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(pnlTools, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // ── Table ──
        String[] cols = {"Mã thanh toán", "Mã hóa đơn", "Khách hàng", "Số tiền", "Ngày", "Phương thức", "Trạng thái"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableThanhToan = new JTable(tableModel);
        UIThemeConfig.styleTable(tableThanhToan);
        add(UIThemeConfig.createScrollPane(tableThanhToan), BorderLayout.CENTER);
    }

    // ── Getters ──
    public JTable getTableThanhToan() { return tableThanhToan; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTextField getTxtTimKiem() { return txtTimKiem; }
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnReload() { return btnReload; }
    public JButton getBtnXoa() { return btnXoa; }
}