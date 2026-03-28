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
    private JTextField txtCustomerName;
    private JButton btnAdd, btnReload;
    private JTable table;
    private DefaultTableModel tableModel;
    private ThanhToanDAO thanhToanDAO = new ThanhToanDAOImpl();

    public ThanhToanView() {
        setTitle("Payment Management");
        setSize(1050, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel pnlHeader = new JPanel(new BorderLayout(10, 0));
        pnlHeader.setBackground(UITheme.BG_DARK);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(12, 20, 5, 20));

        JLabel lblTitle = UITheme.createTitleLabel("PAYMENT MANAGEMENT");
        lblTitle.setForeground(UITheme.ACCENT);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pnlHeader.add(lblTitle, BorderLayout.CENTER);

        // Search bar
        JPanel pnlSearch = new JPanel(new BorderLayout(8, 0));
        pnlSearch.setBackground(UITheme.BG_DARK);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        JLabel lblCustomer = UITheme.createLabel("Customer Name:");
        lblCustomer.setFont(UITheme.FONT_SUBTITLE);
        txtCustomerName = UITheme.createTextField();
        btnAdd = UITheme.createPrimaryButton("+ Add Payment");
        pnlSearch.add(lblCustomer, BorderLayout.WEST);
        pnlSearch.add(txtCustomerName, BorderLayout.CENTER);
        pnlSearch.add(btnAdd, BorderLayout.EAST);

        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setBackground(UITheme.BG_DARK);
        pnlNorth.add(pnlHeader, BorderLayout.NORTH);
        pnlNorth.add(pnlSearch, BorderLayout.SOUTH);
        add(pnlNorth, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new Object[]{
                "Order ID", "Customer ID", "Payment Date", "Amount", "Payment Method"
        }, 0);
        table = new JTable(tableModel);
        UITheme.styleTable(table);
        add(UITheme.createScrollPane(table), BorderLayout.CENTER);

        // Bottom
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlBottom.setBackground(UITheme.BG_DARK);
        btnReload = UITheme.createButton("Reload", UITheme.ACCENT_YELLOW);
        pnlBottom.add(btnReload);
        add(pnlBottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    public JTextField getTxtTenKhachHang() { return txtCustomerName; }
    public JButton getBtnThem()            { return btnAdd; }
    public JButton getBtnReload()          { return btnReload; }
    public JTable getTable()               { return table; }

    public void taiLaiDuLieu() {
        List<ThanhToan> list = thanhToanDAO.layTatCaThanhToan();
        tableModel.setRowCount(0);
        for (ThanhToan tt : list) {
            tableModel.addRow(new Object[]{
                    tt.getHoaDonBanHang() != null ? tt.getHoaDonBanHang().getMaHDBH() : "",
                    tt.getKhachHang()     != null ? tt.getKhachHang().getMaKH()        : "",
                    tt.getNgayTT(), tt.getTienThanhToan(), tt.getHinhThucTT()
            });
        }
    }
}