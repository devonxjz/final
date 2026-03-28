package com.example.view;

import com.example.dao.ThanhToanDAO;
import com.example.entity.ThanhToan;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ThanhToanView extends JFrame {
    private JTextField txtTenKhachHang;
    private JButton btnThem, btnReload;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private ThanhToanDAO thanhToanDAO = new ThanhToanDAO();

    public ThanhToanView() {
        setTitle("Quản lý thanh toán");
        setSize(1150, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(0, 204, 204)); // Màu nền chủ đạo

        // Tiêu đề
        JLabel lblTitle = new JLabel("QUẢN LÝ THANH TOÁN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(173, 255, 255));
        lblTitle.setBounds(400, 5, 350, 30);
        getContentPane().add(lblTitle);

        // Nhập tên khách hàng
        JLabel lblTenKH = new JLabel("Nhập tên khách hàng:");
        lblTenKH.setFont(new Font("Arial", Font.BOLD, 14));
        lblTenKH.setBounds(40, 50, 180, 25);
        getContentPane().add(lblTenKH);

        txtTenKhachHang = new JTextField();
        txtTenKhachHang.setBounds(220, 50, 300, 25);
        getContentPane().add(txtTenKhachHang);

        // Nút thêm thanh toán
        btnThem = new JButton("Thêm thanh toán");
        btnThem.setBounds(960, 50, 150, 25);
        btnThem.setBackground(Color.WHITE);
        btnThem.setForeground(Color.BLUE);
        getContentPane().add(btnThem);

        // Bảng hiển thị
        tableModel = new DefaultTableModel(new Object[]{
                "Mã HĐBH", "Mã KH", "Ngày thanh toán", "Tiền thanh toán", "Hình thức TT"
        }, 0);

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 90, 1090, 600);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getContentPane().add(scrollPane);

        // Nút Reload
        btnReload = new JButton("Reload");
        btnReload.setBounds(1030, 700, 80, 30);
        btnReload.setBackground(new Color(255, 255, 204));
        btnReload.setForeground(Color.RED);
        getContentPane().add(btnReload);

        setVisible(true);
    }

    // ====== Các phương thức getter cho controller dùng ======
    public JTextField getTxtTenKhachHang() {
        return txtTenKhachHang;
    }

    public JButton getBtnThem() {
        return btnThem;
    }

    public JButton getBtnReload() {
        return btnReload;
    }

    public JTable getTable() {
        return table;
    }

    // ====== Phương thức load lại dữ liệu sau khi thêm ======
    public void taiLaiDuLieu() {
        List<ThanhToan> danhSachMoi = thanhToanDAO.layTatCaThanhToan();
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        for (ThanhToan tt : danhSachMoi) {
            tableModel.addRow(new Object[]{
                    tt.getHoaDonBanHang() != null ? tt.getHoaDonBanHang().getMaHDBH() : "",
                    tt.getKhachHang() != null ? tt.getKhachHang().getMaKH() : "",
                    tt.getNgayTT(),
                    tt.getTienThanhToan(),
                    tt.getHinhThucTT()
            });
        }
    }
}