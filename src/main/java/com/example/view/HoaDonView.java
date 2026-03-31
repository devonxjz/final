
package com.example.view;

import com.example.config.UIThemeConfig;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * HoaDonView — POS split-pane (Glassmorphism dark mode).
 */
public class HoaDonView extends JPanel {
    public JTable tableSanPham, tableGioHang;
    public JTextField txtMaKH, txtTenKH, txtTongTien, txtLaiSuat, txtThoiHan;
    public JComboBox<String> cbLoaiHD;
    public JButton btnAddToCard, btnRemoveFromCard, btnThanhToan, btnHuy;
    public JPanel panelTraGop;

    public HoaDonView() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(UIThemeConfig.BG_DARK);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(8, 8, 8, 8);

        // ── LEFT: Product Catalog ──
        JPanel pnlLeft = UIThemeConfig.createGlassPanel(new BorderLayout(0, 8));
        pnlLeft.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblCatalog = new JLabel("Danh mục sản phẩm");
        lblCatalog.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblCatalog.setForeground(UIThemeConfig.ACCENT);
        pnlLeft.add(lblCatalog, BorderLayout.NORTH);

        tableSanPham = new JTable(new DefaultTableModel(
                new Object[]{"Mã SP", "Tên sản phẩm", "Giá bán", "Số lượng"}, 0));
        UIThemeConfig.styleTable(tableSanPham);
        pnlLeft.add(UIThemeConfig.createScrollPane(tableSanPham), BorderLayout.CENTER);

        btnAddToCard = UIThemeConfig.createSuccessButton("Thêm vào giỏ >>");
        btnAddToCard.setPreferredSize(new Dimension(0, 36));
        pnlLeft.add(btnAddToCard, BorderLayout.SOUTH);

        // ── RIGHT: Cart + Payment ──
        JPanel pnlRight = new JPanel(new BorderLayout(0, 8));
        pnlRight.setOpaque(false);

        // Cart table
        JPanel pnlCart = UIThemeConfig.createGlassPanel(new BorderLayout(0, 8));
        pnlCart.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblCart = new JLabel("Chi tiết đơn hàng");
        lblCart.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblCart.setForeground(UIThemeConfig.ACCENT);
        pnlCart.add(lblCart, BorderLayout.NORTH);

        tableGioHang = new JTable(new DefaultTableModel(
                new Object[]{"Mã SP", "Sản phẩm", "SL", "Giá", "Thành tiền"}, 0));
        UIThemeConfig.styleTable(tableGioHang);
        pnlCart.add(UIThemeConfig.createScrollPane(tableGioHang), BorderLayout.CENTER);

        // Payment form
        JPanel pnlPayment = UIThemeConfig.createGlassPanel(new GridBagLayout());
        pnlPayment.setBorder(new EmptyBorder(15, 15, 15, 15));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 6, 4, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblPayment = new JLabel("Thanh toán");
        lblPayment.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblPayment.setForeground(UIThemeConfig.ACCENT);
        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 2; gc.weightx = 1;
        pnlPayment.add(lblPayment, gc);
        gc.gridwidth = 1;

        txtMaKH    = UIThemeConfig.createTextField();
        txtTenKH   = UIThemeConfig.createTextField();
        txtTongTien = UIThemeConfig.createTextField();
        txtTongTien.setEditable(false);
        cbLoaiHD = UIThemeConfig.createComboBox(new String[]{"Trực tiếp", "Trả góp"});

        addField(pnlPayment, gc, 1, "Mã khách hàng:", txtMaKH);
        addField(pnlPayment, gc, 2, "Tên khách hàng:", txtTenKH);
        addField(pnlPayment, gc, 3, "Hình thức:", cbLoaiHD);
        addField(pnlPayment, gc, 4, "TỔNG TIỀN:", txtTongTien);

        // Installment panel
        panelTraGop = new JPanel(new GridLayout(1, 4, 6, 0));
        panelTraGop.setOpaque(false);
        txtLaiSuat = UIThemeConfig.createTextField();
        txtThoiHan = UIThemeConfig.createTextField();
        panelTraGop.add(UIThemeConfig.createLabel("Lãi suất (%):"));
        panelTraGop.add(txtLaiSuat);
        panelTraGop.add(UIThemeConfig.createLabel("Kì hạn (tháng):"));
        panelTraGop.add(txtThoiHan);

        gc.gridx = 0; gc.gridy = 5; gc.gridwidth = 2;
        pnlPayment.add(panelTraGop, gc);

        // Action buttons
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pnlBtns.setOpaque(false);
        btnRemoveFromCard = UIThemeConfig.createDangerButton("Xóa");
        btnThanhToan = UIThemeConfig.createPrimaryButton("Thanh toán & In");
        btnHuy = UIThemeConfig.createButton("Hủy", UIThemeConfig.BG_INPUT);
        pnlBtns.add(btnRemoveFromCard);
        pnlBtns.add(btnThanhToan);
        pnlBtns.add(btnHuy);

        gc.gridy = 6;
        gc.insets = new Insets(12, 6, 4, 6);
        pnlPayment.add(pnlBtns, gc);

        pnlRight.add(pnlCart, BorderLayout.CENTER);
        pnlRight.add(pnlPayment, BorderLayout.SOUTH);

        // Layout
        gbc.gridx = 0; gbc.weightx = 0.4; gbc.weighty = 1.0;
        add(pnlLeft, gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        add(pnlRight, gbc);
    }

    private void addField(JPanel panel, GridBagConstraints gc, int row, String label, JComponent field) {
        gc.gridy = row;
        gc.gridx = 0; gc.weightx = 0;
        JLabel lbl = UIThemeConfig.createLabel(label);
        lbl.setPreferredSize(new Dimension(110, 28));
        panel.add(lbl, gc);
        gc.gridx = 1; gc.weightx = 1;
        panel.add(field, gc);
    }

    public void hienThiDanhSachSanPham(java.util.List<com.example.entity.SanPham> ds) {
        DefaultTableModel m = (DefaultTableModel) tableSanPham.getModel();
        m.setRowCount(0);
        for (var sp : ds) {
            m.addRow(new Object[]{sp.getMaSP(), sp.getTenSP(), sp.getGiaBan(), sp.getSoLuongTrongKho()});
        }
    }
}
