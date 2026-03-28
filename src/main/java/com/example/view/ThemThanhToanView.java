package com.example.view;

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
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        // Panel trên: nhập thông tin
        JPanel pnlTop = new JPanel(new GridLayout(3, 4, 10, 15));
        pnlTop.setBorder(BorderFactory.createTitledBorder(" "));
        pnlTop.setBackground(new Color(0, 204, 204));

        txtMaHDBH = new JTextField(10);
        txtMaKH = new JTextField(10);
        txtTenKH = new JTextField(10);
        txtTienThanhToan = new JTextField(10);

        txtMaKH.setEditable(false);
        txtTenKH.setEditable(false);

        dateChooserNgayThanhToan = new JDateChooser();
        dateChooserNgayThanhToan.setDateFormatString("dd/MM/yyyy");

        cmbHinhThucTT = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản"});

        // Add components với label
        addLabelAndField(pnlTop, "Mã HĐBH:", txtMaHDBH, labelFont);
        addLabelAndField(pnlTop, "Tiền thanh toán:", txtTienThanhToan, labelFont);
        addLabelAndField(pnlTop, "Mã KH:", txtMaKH, labelFont);
        addLabelAndField(pnlTop, "Ngày thanh toán:", dateChooserNgayThanhToan, labelFont);
        addLabelAndField(pnlTop, "Tên KH:", txtTenKH, labelFont);
        addLabelAndField(pnlTop, "Hình thức TT:", cmbHinhThucTT, labelFont);

        add(pnlTop, BorderLayout.NORTH);

        // Panel giữa: bảng + tìm kiếm
        JPanel pnlCenter = new JPanel(new BorderLayout());

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSearch.setBackground(new Color(0, 204, 204));
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JLabel lblNhapTenKH = new JLabel("Nhập tên khách hàng:");
        lblNhapTenKH.setFont(labelFont);

        txtNhapTenKH = new JTextField(25);
        btnXuatHoaDon = new JButton("Xuất hóa đơn");

        pnlSearch.add(lblNhapTenKH);
        pnlSearch.add(txtNhapTenKH);
        pnlSearch.add(btnXuatHoaDon);

        // Thêm cột vào bảng
        String[] columnNames = {"Mã HĐBH", "Mã KH", "Tên KH", "Tổng tiền", "Tiền góp/tháng", "Ngày tạo"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);

        pnlCenter.add(pnlSearch, BorderLayout.NORTH);
        pnlCenter.add(tableScroll, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);

        // Panel dưới: Thông tin chi tiết
        txtAreaThongTin = new JTextArea(5, 40);
        txtAreaThongTin.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaThongTin);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Thông tin thanh toán"));

        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Tiện ích thêm label và field với font
    private void addLabelAndField(JPanel panel, String labelText, JComponent inputField, Font font) {
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label);
        panel.add(inputField);
    }

    public JTable getTable() {
        return table;
    }

    public void loadData(List<Map<String, Object>> hoaDonList) {
        // Xóa dữ liệu cũ
        tableModel.setRowCount(0);

        for (Map<String, Object> row : hoaDonList) {
            tableModel.addRow(new Object[]{
                    row.get("maHDBH"),
                    row.get("maKH"),
                    row.get("tenKH"),
                    row.get("tongTien"),
                    row.get("tienGopThang"),
                    row.get("ngayTao")
            });
        }
    }
}