package com.example.view;

import com.example.config.UIThemeConfig;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Form thêm hóa đơn bán hàng — nhúng vào ContentPanel, không mở cửa sổ mới.
 */
public class ThemHoaDonBanView extends JPanel {

    public JTextField txtTimKiem, txtSDT, txtTenKH, txtDiaChi, txtMaSP, txtTenSP, txtSoLuong, txtLaiSuat;
    public JComboBox<String> cbGioiTinh, cbLoaiHD, cbHinhThucTT;
    public JDateChooser dateChooser;
    public JTable tableSanPham, tableSanPhamChon;
    public JButton btnHuyChon, btnThem, btnXuatHoaDon;

    public ThemHoaDonBanView() {
        setLayout(new BorderLayout(0, 8));
        setBackground(UIThemeConfig.BG_DARK);

        // ── HEADER ──
        JPanel pnlHeader = UIThemeConfig.createGlassPanel(new BorderLayout(12, 0));
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, UIThemeConfig.ACCENT),
                new EmptyBorder(12, 20, 12, 20)));

        JLabel lblSearch = UIThemeConfig.createLabel("Search Product:");
        lblSearch.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblSearch.setForeground(UIThemeConfig.TEXT_PRIMARY);
        txtTimKiem = UIThemeConfig.createTextField();

        btnXuatHoaDon = UIThemeConfig.createSuccessButton("Issue Invoice");
        btnXuatHoaDon.setPreferredSize(new Dimension(150, 34));

        pnlHeader.add(lblSearch, BorderLayout.WEST);
        pnlHeader.add(txtTimKiem, BorderLayout.CENTER);
        pnlHeader.add(btnXuatHoaDon, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // ── BODY: LEFT (tables) + RIGHT (form) ──
        JPanel pnlBody = new JPanel(new BorderLayout(10, 0));
        pnlBody.setBackground(UIThemeConfig.BG_DARK);
        pnlBody.setBorder(new EmptyBorder(8, 16, 8, 16));

        // LEFT — Product list + selected
        JPanel pnlLeft = new JPanel(new BorderLayout(0, 8));
        pnlLeft.setBackground(UIThemeConfig.BG_DARK);
        pnlLeft.setPreferredSize(new Dimension(530, 0));

        JLabel lblCatalog = UIThemeConfig.createLabel("Product Catalog");
        lblCatalog.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblCatalog.setForeground(UIThemeConfig.ACCENT);
        lblCatalog.setBorder(new EmptyBorder(0, 2, 6, 0));

        tableSanPham = new JTable();
        UIThemeConfig.styleTable(tableSanPham);

        JPanel pnlCatalog = new JPanel(new BorderLayout());
        pnlCatalog.setBackground(UIThemeConfig.BG_DARK);
        pnlCatalog.add(lblCatalog, BorderLayout.NORTH);
        pnlCatalog.add(UIThemeConfig.createScrollPane(tableSanPham), BorderLayout.CENTER);
        pnlLeft.add(pnlCatalog, BorderLayout.CENTER);

        // Selected products panel
        JPanel pnlChonHeader = new JPanel(new BorderLayout(8, 0));
        pnlChonHeader.setBackground(UIThemeConfig.BG_DARK);
        pnlChonHeader.setBorder(new EmptyBorder(4, 0, 6, 0));
        JLabel lblChon = UIThemeConfig.createLabel("Selected Items");
        lblChon.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblChon.setForeground(UIThemeConfig.ACCENT_YELLOW);
        btnHuyChon = UIThemeConfig.createDangerButton("Remove");
        btnHuyChon.setPreferredSize(new Dimension(100, 30));
        pnlChonHeader.add(lblChon, BorderLayout.WEST);
        pnlChonHeader.add(btnHuyChon, BorderLayout.EAST);

        tableSanPhamChon = new JTable();
        UIThemeConfig.styleTable(tableSanPhamChon);

        JPanel pnlSelected = new JPanel(new BorderLayout());
        pnlSelected.setBackground(UIThemeConfig.BG_DARK);
        pnlSelected.setPreferredSize(new Dimension(0, 180));
        pnlSelected.add(pnlChonHeader, BorderLayout.NORTH);
        pnlSelected.add(UIThemeConfig.createScrollPane(tableSanPhamChon), BorderLayout.CENTER);
        pnlLeft.add(pnlSelected, BorderLayout.SOUTH);

        pnlBody.add(pnlLeft, BorderLayout.WEST);

        // RIGHT — Customer info + Product entry
        JPanel pnlRight = new JPanel(new BorderLayout(0, 8));
        pnlRight.setBackground(UIThemeConfig.BG_DARK);

        // Customer card
        JPanel pnlKH = UIThemeConfig.createGlassPanel(new GridBagLayout());
        pnlKH.setBorder(new EmptyBorder(14, 16, 14, 16));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 6, 5, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblKHTitle = UIThemeConfig.createLabel("Customer & Order Info");
        lblKHTitle.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblKHTitle.setForeground(UIThemeConfig.ACCENT);
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 4;
        g.weightx = 1;
        pnlKH.add(lblKHTitle, g);
        g.gridwidth = 1;

        txtSDT = UIThemeConfig.createTextField();
        txtTenKH = UIThemeConfig.createTextField();
        txtDiaChi = UIThemeConfig.createTextField();
        txtLaiSuat = UIThemeConfig.createTextField();
        cbGioiTinh = UIThemeConfig.createComboBox(new String[] { "Male", "Female", "Other" });
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setBackground(UIThemeConfig.BG_INPUT);
        cbLoaiHD = UIThemeConfig.createComboBox(new String[] { "Direct Pay", "Installment" });
        cbHinhThucTT = UIThemeConfig.createComboBox(new String[] { "Cash", "Bank Transfer", "Credit Card" });

        Object[][] formRows = {
                { "Phone:", txtSDT, "Gender:", cbGioiTinh },
                { "Customer Name:", txtTenKH, "Address:", txtDiaChi },
                { "Order Date:", dateChooser, "Order Type:", cbLoaiHD },
                { "Interest Rate:", txtLaiSuat, "Payment:", cbHinhThucTT },
        };

        for (int i = 0; i < formRows.length; i++) {
            g.gridy = i + 1;
            g.gridx = 0;
            g.weightx = 0;
            JLabel lA = UIThemeConfig.createLabel((String) formRows[i][0]);
            lA.setPreferredSize(new Dimension(110, 24));
            pnlKH.add(lA, g);
            g.gridx = 1;
            g.weightx = 1;
            pnlKH.add((Component) formRows[i][1], g);
            g.gridx = 2;
            g.weightx = 0;
            JLabel lB = UIThemeConfig.createLabel((String) formRows[i][2]);
            lB.setPreferredSize(new Dimension(100, 24));
            pnlKH.add(lB, g);
            g.gridx = 3;
            g.weightx = 1;
            pnlKH.add((Component) formRows[i][3], g);
        }

        pnlRight.add(pnlKH, BorderLayout.CENTER);

        // Product entry card
        JPanel pnlSP = UIThemeConfig.createGlassPanel(new GridBagLayout());
        pnlSP.setBorder(new EmptyBorder(12, 16, 12, 16));

        GridBagConstraints gs = new GridBagConstraints();
        gs.insets = new Insets(5, 6, 5, 6);
        gs.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblSPTitle = UIThemeConfig.createLabel("Add Product to Order");
        lblSPTitle.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblSPTitle.setForeground(UIThemeConfig.ACCENT_YELLOW);
        gs.gridx = 0;
        gs.gridy = 0;
        gs.gridwidth = 4;
        gs.weightx = 1;
        pnlSP.add(lblSPTitle, gs);
        gs.gridwidth = 1;

        txtMaSP = UIThemeConfig.createTextField();
        txtTenSP = UIThemeConfig.createTextField();
        txtSoLuong = UIThemeConfig.createTextField();

        Object[][] spRows = {
                { "Product ID:", txtMaSP, "Name:", txtTenSP },
                { "Qty:", txtSoLuong, null, null },
        };

        for (int i = 0; i < spRows.length; i++) {
            gs.gridy = i + 1;
            gs.gridx = 0;
            gs.weightx = 0;
            pnlSP.add(UIThemeConfig.createLabel((String) spRows[i][0]), gs);
            gs.gridx = 1;
            gs.weightx = 1;
            pnlSP.add((Component) spRows[i][1], gs);
            if (spRows[i][2] != null) {
                gs.gridx = 2;
                gs.weightx = 0;
                pnlSP.add(UIThemeConfig.createLabel((String) spRows[i][2]), gs);
                gs.gridx = 3;
                gs.weightx = 1;
                pnlSP.add((Component) spRows[i][3], gs);
            }
        }

        btnThem = UIThemeConfig.createPrimaryButton("Add to Order");
        btnThem.setPreferredSize(new Dimension(140, 34));
        gs.gridy = 3;
        gs.gridx = 3;
        gs.gridwidth = 1;
        gs.anchor = GridBagConstraints.EAST;
        pnlSP.add(btnThem, gs);

        JPanel pnlSPWrap = new JPanel(new BorderLayout());
        pnlSPWrap.setBackground(UIThemeConfig.BG_DARK);
        pnlSPWrap.setPreferredSize(new Dimension(0, 160));
        pnlSPWrap.add(pnlSP, BorderLayout.CENTER);
        pnlRight.add(pnlSPWrap, BorderLayout.SOUTH);

        pnlBody.add(pnlRight, BorderLayout.CENTER);
        add(pnlBody, BorderLayout.CENTER);
    }
}