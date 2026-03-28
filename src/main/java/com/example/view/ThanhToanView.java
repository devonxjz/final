package com.example.view;

import com.example.config.UITheme;
import com.example.dao.ThanhToanDAO;
import com.example.dao.impl.ThanhToanDAOImpl;
import com.example.entity.ThanhToan;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ThanhToanView extends JFrame {
    private JTextField txtTenKhachHang;
    private JButton btnThem, btnReload;
    private JTable table;
    private DefaultTableModel tableModel;
    private ThanhToanDAO thanhToanDAO = new ThanhToanDAOImpl();

    public ThanhToanView() {
        setTitle("Quáº£n lÃ½ thanh toÃ¡n");
        setSize(1050, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(10, 10));

        // === HEADER ===
        JPanel pnlHeader = new JPanel(new BorderLayout(10, 0));
        pnlHeader.setBackground(UITheme.BG_DARK);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(12, 20, 5, 20));

        JLabel lblTitle = UITheme.createTitleLabel("QUáº¢N LÃ THANH TOÃN");
        lblTitle.setForeground(UITheme.ACCENT);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pnlHeader.add(lblTitle, BorderLayout.CENTER);

        JPanel pnlSearch = new JPanel(new BorderLayout(8, 0));
        pnlSearch.setBackground(UITheme.BG_DARK);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        JLabel lblTenKH = UITheme.createLabel("TÃªn khÃ¡ch hÃ ng:");
        lblTenKH.setFont(UITheme.FONT_SUBTITLE);
        txtTenKhachHang = UITheme.createTextField();
        btnThem = UITheme.createPrimaryButton("ï¼‹ ThÃªm TT");
        pnlSearch.add(lblTenKH, BorderLayout.WEST);
        pnlSearch.add(txtTenKhachHang, BorderLayout.CENTER);
        pnlSearch.add(btnThem, BorderLayout.EAST);

        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setBackground(UITheme.BG_DARK);
        pnlNorth.add(pnlHeader, BorderLayout.NORTH);
        pnlNorth.add(pnlSearch, BorderLayout.SOUTH);
        add(pnlNorth, BorderLayout.NORTH);

        // === TABLE ===
        tableModel = new DefaultTableModel(new Object[]{
            "MÃ£ HÄBH", "MÃ£ KH", "NgÃ y thanh toÃ¡n", "Tiá»n thanh toÃ¡n", "HÃ¬nh thá»©c TT"
        }, 0);
        table = new JTable(tableModel);
        UITheme.styleTable(table);
        add(UITheme.createScrollPane(table), BorderLayout.CENTER);

        // === BOTTOM ===
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlBottom.setBackground(UITheme.BG_DARK);
        btnReload = UITheme.createButton("Reload", UITheme.ACCENT_YELLOW);
        pnlBottom.add(btnReload);
        add(pnlBottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    public JTextField getTxtTenKhachHang() { return txtTenKhachHang; }
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnReload() { return btnReload; }
    public JTable getTable() { return table; }

    public void taiLaiDuLieu() {
        List<ThanhToan> danhSachMoi = thanhToanDAO.layTatCaThanhToan();
        tableModel.setRowCount(0);
        for (ThanhToan tt : danhSachMoi) {
            tableModel.addRow(new Object[]{
                tt.getHoaDonBanHang() != null ? tt.getHoaDonBanHang().getMaHDBH() : "",
                tt.getKhachHang() != null ? tt.getKhachHang().getMaKH() : "",
                tt.getNgayTT(), tt.getTienThanhToan(), tt.getHinhThucTT()
            });
        }
    }
}