package com.example.view;

import com.example.config.UIThemeConfig;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Form thêm thanh toán — nhúng vào ContentPanel, không mở cửa sổ mới.
 */
public class ThemThanhToanView extends JPanel {

    public JTextField txtMaHDBH, txtMaKH, txtTenKH, txtTienThanhToan, txtNhapTenKH;
    public JComboBox<String> cmbHinhThucTT;
    public JDateChooser dateChooserNgayThanhToan;
    public JButton btnXuatHoaDon;
    public JTextArea txtAreaThongTin;
    public JTable table;
    private DefaultTableModel tableModel;

    public ThemThanhToanView() {
        setLayout(new BorderLayout(0, 0));
        setBackground(UIThemeConfig.BG_DARK);

        // ── HEADER ──
        JPanel pnlHeader = UIThemeConfig.createGlassPanel(new BorderLayout());
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, UIThemeConfig.ACCENT),
                new EmptyBorder(14, 24, 14, 24)));

        JLabel lblTitle = UIThemeConfig.createTitleLabel("GHI NHẬN THANH TOÁN");
        lblTitle.setForeground(UIThemeConfig.ACCENT);
        JLabel lblSub = UIThemeConfig.createLabel("Ghi nhận thanh toán cho một đơn hàng hiện có.");
        lblSub.setForeground(UIThemeConfig.TEXT_MUTED);

        JPanel pnlHdrText = new JPanel();
        pnlHdrText.setOpaque(false);
        pnlHdrText.setLayout(new BoxLayout(pnlHdrText, BoxLayout.Y_AXIS));
        pnlHdrText.add(lblTitle);
        pnlHdrText.add(Box.createRigidArea(new Dimension(0, 3)));
        pnlHdrText.add(lblSub);
        pnlHeader.add(pnlHdrText, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        // ── BODY ──
        JPanel pnlBody = new JPanel(new BorderLayout(0, 10));
        pnlBody.setBackground(UIThemeConfig.BG_DARK);
        pnlBody.setBorder(new EmptyBorder(14, 20, 10, 20));

        // Top form card
        JPanel pnlForm = UIThemeConfig.createGlassPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, UIThemeConfig.ACCENT),
                new EmptyBorder(16, 20, 16, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaHDBH = UIThemeConfig.createTextField();
        txtMaKH = UIThemeConfig.createTextField();
        txtMaKH.setEditable(false);
        txtTenKH = UIThemeConfig.createTextField();
        txtTenKH.setEditable(false);
        txtTienThanhToan = UIThemeConfig.createTextField();
        dateChooserNgayThanhToan = new JDateChooser();
        dateChooserNgayThanhToan.setDateFormatString("dd/MM/yyyy");
        dateChooserNgayThanhToan.setBackground(UIThemeConfig.BG_INPUT);
        cmbHinhThucTT = UIThemeConfig.createComboBox(new String[] { "Tiền mặt", "Chuyển khoản" });

        Object[][] rows = {
                { "Mã đơn hàng:", txtMaHDBH, "Số tiền thanh toán:", txtTienThanhToan },
                { "Mã khách hàng:", txtMaKH, "Ngày thanh toán:", dateChooserNgayThanhToan },
                { "Tên khách hàng:", txtTenKH, "Phương thức:", cmbHinhThucTT },
        };

        for (int r = 0; r < rows.length; r++) {
            gbc.gridy = r;
            gbc.gridx = 0;
            gbc.weightx = 0;
            JLabel lA = UIThemeConfig.createLabel((String) rows[r][0]);
            lA.setPreferredSize(new Dimension(120, 24));
            pnlForm.add(lA, gbc);
            gbc.gridx = 1;
            gbc.weightx = 1;
            pnlForm.add((Component) rows[r][1], gbc);
            gbc.gridx = 2;
            gbc.weightx = 0;
            JLabel lB = UIThemeConfig.createLabel((String) rows[r][2]);
            lB.setPreferredSize(new Dimension(120, 24));
            pnlForm.add(lB, gbc);
            gbc.gridx = 3;
            gbc.weightx = 1;
            pnlForm.add((Component) rows[r][3], gbc);
        }

        pnlBody.add(pnlForm, BorderLayout.NORTH);

        // Center: search + table
        JPanel pnlCenter = new JPanel(new BorderLayout(0, 8));
        pnlCenter.setBackground(UIThemeConfig.BG_DARK);

        JPanel pnlSearch = UIThemeConfig.createGlassPanel(new BorderLayout(10, 0));
        pnlSearch.setBorder(new EmptyBorder(10, 14, 10, 14));
        JLabel lblSearch = UIThemeConfig.createLabel("Tìm theo tên khách hàng:");
        lblSearch.setFont(UIThemeConfig.FONT_SUBTITLE);
        txtNhapTenKH = UIThemeConfig.createTextField();
        btnXuatHoaDon = UIThemeConfig.createPrimaryButton("Xuất biên lai");
        btnXuatHoaDon.setPreferredSize(new Dimension(150, 34));
        pnlSearch.add(lblSearch, BorderLayout.WEST);
        pnlSearch.add(txtNhapTenKH, BorderLayout.CENTER);
        pnlSearch.add(btnXuatHoaDon, BorderLayout.EAST);
        pnlCenter.add(pnlSearch, BorderLayout.NORTH);

        String[] cols = { "Mã đơn", "Mã KH", "Tên khách hàng", "Tổng tiền", "Tiền góp tháng", "Ngày tạo" };
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        UIThemeConfig.styleTable(table);
        pnlCenter.add(UIThemeConfig.createScrollPane(table), BorderLayout.CENTER);

        pnlBody.add(pnlCenter, BorderLayout.CENTER);

        // Bottom: info area
        txtAreaThongTin = new JTextArea(4, 0);
        txtAreaThongTin.setEditable(false);
        txtAreaThongTin.setFont(UIThemeConfig.FONT_BODY);
        txtAreaThongTin.setBackground(UIThemeConfig.BG_CARD);
        txtAreaThongTin.setForeground(UIThemeConfig.TEXT_PRIMARY);
        txtAreaThongTin.setLineWrap(true);
        txtAreaThongTin.setWrapStyleWord(true);

        JScrollPane scrollInfo = new JScrollPane(txtAreaThongTin);
        scrollInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(UIThemeConfig.BORDER),
                        "Thông tin thanh toán", 0, 0, UIThemeConfig.FONT_BODY, UIThemeConfig.ACCENT),
                new EmptyBorder(4, 6, 4, 6)));
        scrollInfo.setPreferredSize(new Dimension(0, 110));
        pnlBody.add(scrollInfo, BorderLayout.SOUTH);

        add(pnlBody, BorderLayout.CENTER);
    }

    public JTable getTable() {
        return table;
    }

    public void loadData(List<Map<String, Object>> list) {
        tableModel.setRowCount(0);
        for (Map<String, Object> row : list) {
            tableModel.addRow(new Object[] {
                    row.get("maHDBH"), row.get("maKH"), row.get("tenKH"),
                    row.get("tongTien"), row.get("tienGopThang"), row.get("ngayTao")
            });
        }
    }
}