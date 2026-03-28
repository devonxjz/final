package com.example.view;

import com.example.config.UITheme;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import java.util.List;

public class ThemThanhToanView extends JFrame {
    public JTextField txtMaHDBH, txtMaKH, txtTenKH, txtTienThanhToan, txtNhapTenKH;
    public JComboBox<String> cmbHinhThucTT;
    public JDateChooser dateChooserNgayThanhToan;
    public JButton btnXuatHoaDon;
    public JTextArea txtAreaThongTin;
    public JTable table;
    private DefaultTableModel tableModel;

    public ThemThanhToanView() {
        setTitle("Thêm thanh toán");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        getContentPane().setBackground(UITheme.BG_DARK);

        // === TOP: Form nhập ===
        JPanel pnlTop = UITheme.createCard();
        pnlTop.setLayout(new GridLayout(3, 4, 10, 10));
        pnlTop.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, UITheme.ACCENT),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)));

        txtMaHDBH = UITheme.createTextField();
        txtMaKH = UITheme.createTextField(); txtMaKH.setEditable(false);
        txtTenKH = UITheme.createTextField(); txtTenKH.setEditable(false);
        txtTienThanhToan = UITheme.createTextField();
        dateChooserNgayThanhToan = new JDateChooser();
        dateChooserNgayThanhToan.setDateFormatString("dd/MM/yyyy");
        cmbHinhThucTT = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản"});
        cmbHinhThucTT.setBackground(UITheme.BG_INPUT);
        cmbHinhThucTT.setForeground(UITheme.TEXT_PRIMARY);

        pnlTop.add(UITheme.createLabel("Mã HĐBH:")); pnlTop.add(txtMaHDBH);
        pnlTop.add(UITheme.createLabel("Tiền thanh toán:")); pnlTop.add(txtTienThanhToan);
        pnlTop.add(UITheme.createLabel("Mã KH:")); pnlTop.add(txtMaKH);
        pnlTop.add(UITheme.createLabel("Ngày TT:")); pnlTop.add(dateChooserNgayThanhToan);
        pnlTop.add(UITheme.createLabel("Tên KH:")); pnlTop.add(txtTenKH);
        pnlTop.add(UITheme.createLabel("Hình thức:")); pnlTop.add(cmbHinhThucTT);
        add(pnlTop, BorderLayout.NORTH);

        // === CENTER: Search + Table ===
        JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
        pnlCenter.setBackground(UITheme.BG_DARK);

        JPanel pnlSearch = new JPanel(new BorderLayout(8, 0));
        pnlSearch.setBackground(UITheme.BG_DARK);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        JLabel lblSearch = UITheme.createLabel("Tên khách hàng:");
        lblSearch.setFont(UITheme.FONT_SUBTITLE);
        txtNhapTenKH = UITheme.createTextField();
        btnXuatHoaDon = UITheme.createPrimaryButton("Xuất hóa đơn");
        pnlSearch.add(lblSearch, BorderLayout.WEST);
        pnlSearch.add(txtNhapTenKH, BorderLayout.CENTER);
        pnlSearch.add(btnXuatHoaDon, BorderLayout.EAST);
        pnlCenter.add(pnlSearch, BorderLayout.NORTH);

        String[] cols = {"Mã HĐBH", "Mã KH", "Tên KH", "Tổng tiền", "Tiền góp/tháng", "Ngày tạo"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        UITheme.styleTable(table);
        pnlCenter.add(UITheme.createScrollPane(table), BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // === SOUTH: Info ===
        txtAreaThongTin = new JTextArea(4, 40);
        txtAreaThongTin.setEditable(false);
        txtAreaThongTin.setFont(UITheme.FONT_BODY);
        txtAreaThongTin.setBackground(UITheme.BG_CARD);
        txtAreaThongTin.setForeground(UITheme.TEXT_PRIMARY);
        JScrollPane scrollInfo = new JScrollPane(txtAreaThongTin);
        scrollInfo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UITheme.BORDER), "Thông tin thanh toán",
            0, 0, UITheme.FONT_BODY, UITheme.ACCENT));
        add(scrollInfo, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addLabelAndField(JPanel panel, String labelText, JComponent inputField, Font font) {
        JLabel label = UITheme.createLabel(labelText);
        label.setFont(font);
        panel.add(label);
        panel.add(inputField);
    }

    public JTable getTable() { return table; }

    public void loadData(List<Map<String, Object>> hoaDonList) {
        tableModel.setRowCount(0);
        for (Map<String, Object> row : hoaDonList) {
            tableModel.addRow(new Object[]{
                row.get("maHDBH"), row.get("maKH"), row.get("tenKH"),
                row.get("tongTien"), row.get("tienGopThang"), row.get("ngayTao")
            });
        }
    }
}