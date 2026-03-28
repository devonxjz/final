package com.example.view;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;

public class ThemHoaDonBanView extends JFrame {
    // Khai báo các thành phần giao diện
    public JTextField txtTimKiem, txtSDT, txtTenKH, txtDiaChi, txtMaSP, txtTenSP, txtSoLuong, txtLaiSuat;
    public JComboBox<String> cbGioiTinh, cbLoaiHD, cbHinhThucTT;
    public JDateChooser dateChooser;
    public JTable tableSanPham, tableSanPhamChon;
    public JButton btnHuyChon, btnThem, btnXuatHoaDon;

    public ThemHoaDonBanView() {
        setTitle("Form_ThemHoaDonBanHang");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        // Ô tìm kiếm
        JLabel lblTimKiem = new JLabel("Tìm kiếm");
        lblTimKiem.setBounds(10, 10, 80, 25);
        add(lblTimKiem);

        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(90, 10, 300, 25);
        add(txtTimKiem);

        // Nút xuất hóa đơn
        btnXuatHoaDon = new JButton("Xuất hóa đơn");
        btnXuatHoaDon.setBounds(860, 10, 120, 25);
        add(btnXuatHoaDon);

        // Danh sách sản phẩm
        tableSanPham = new JTable();
        JScrollPane scrollSanPham = new JScrollPane(tableSanPham);
        scrollSanPham.setBounds(10, 40, 500, 250);
        add(scrollSanPham);

        // Sản phẩm đã chọn
        JLabel lblSanPhamDaChon = new JLabel("Sản phẩm đã chọn");
        lblSanPhamDaChon.setBounds(10, 300, 150, 25);
        add(lblSanPhamDaChon);

        tableSanPhamChon = new JTable();
        JScrollPane scrollSanPhamChon = new JScrollPane(tableSanPhamChon);
        scrollSanPhamChon.setBounds(10, 330, 500, 150);
        add(scrollSanPhamChon);

        btnHuyChon = new JButton("Hủy chọn");
        btnHuyChon.setBounds(420, 295, 90, 25);
        add(btnHuyChon);

        // Thông tin khách hàng bên phải
        JPanel panelThongTin = new JPanel();
        panelThongTin.setLayout(null);
        panelThongTin.setBounds(520, 40, 450, 300);
        add(panelThongTin);

        // SDT
        JLabel lblSDT = new JLabel("SDT:");
        lblSDT.setBounds(10, 10, 100, 25);
        panelThongTin.add(lblSDT);

        txtSDT = new JTextField();
        txtSDT.setBounds(120, 10, 300, 25);
        panelThongTin.add(txtSDT);

        // Tên KH
        JLabel lblTenKH = new JLabel("Tên KH:");
        lblTenKH.setBounds(10, 45, 100, 25);
        panelThongTin.add(lblTenKH);

        txtTenKH = new JTextField();
        txtTenKH.setBounds(120, 45, 300, 25);
        panelThongTin.add(txtTenKH);

        // Giới tính
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setBounds(10, 80, 100, 25);
        panelThongTin.add(lblGioiTinh);

        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        cbGioiTinh.setBounds(120, 80, 300, 25);
        panelThongTin.add(cbGioiTinh);

        // Địa chỉ
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setBounds(10, 115, 100, 25);
        panelThongTin.add(lblDiaChi);

        txtDiaChi = new JTextField();
        txtDiaChi.setBounds(120, 115, 300, 25);
        panelThongTin.add(txtDiaChi);

        // Ngày tạo
        JLabel lblNgayTao = new JLabel("Ngày tạo:");
        lblNgayTao.setBounds(10, 150, 100, 25);
        panelThongTin.add(lblNgayTao);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setBounds(120, 150, 300, 25);
        panelThongTin.add(dateChooser);

        // Loại hóa đơn
        JLabel lblLoaiHD = new JLabel("Loại hóa đơn:");
        lblLoaiHD.setBounds(10, 185, 100, 25);
        panelThongTin.add(lblLoaiHD);

        cbLoaiHD = new JComboBox<>(new String[]{"Trả thẳng", "Trả góp"});
        cbLoaiHD.setBounds(120, 185, 300, 25);
        panelThongTin.add(cbLoaiHD);

        // Lãi suất
        JLabel lblLaiSuat = new JLabel("Lãi suất:");
        lblLaiSuat.setBounds(10, 220, 100, 25);
        panelThongTin.add(lblLaiSuat);

        txtLaiSuat = new JTextField();
        txtLaiSuat.setBounds(120, 220, 300, 25);
        panelThongTin.add(txtLaiSuat);

        // Hình thức thanh toán
        JLabel lblHinhThuc = new JLabel("Hình thức TT:");
        lblHinhThuc.setBounds(10, 255, 100, 25);
        panelThongTin.add(lblHinhThuc);

        cbHinhThucTT = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản", "Thẻ tín dụng"});
        cbHinhThucTT.setBounds(120, 255, 300, 25);
        panelThongTin.add(cbHinhThucTT);

        // Panel sản phẩm (Chi tiết nhập)
        JPanel panelSanPham = new JPanel();
        panelSanPham.setLayout(null);
        panelSanPham.setBounds(520, 350, 450, 130);
        add(panelSanPham);

        JLabel lblMaSP = new JLabel("Mã sản phẩm:");
        lblMaSP.setBounds(10, 10, 100, 25);
        panelSanPham.add(lblMaSP);

        txtMaSP = new JTextField();
        txtMaSP.setBounds(120, 10, 300, 25);
        panelSanPham.add(txtMaSP);

        JLabel lblTenSP = new JLabel("Tên sản phẩm:");
        lblTenSP.setBounds(10, 45, 100, 25);
        panelSanPham.add(lblTenSP);

        txtTenSP = new JTextField();
        txtTenSP.setBounds(120, 45, 300, 25);
        panelSanPham.add(txtTenSP);

        JLabel lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setBounds(10, 80, 100, 25);
        panelSanPham.add(lblSoLuong);

        txtSoLuong = new JTextField();
        txtSoLuong.setBounds(120, 80, 300, 25);
        panelSanPham.add(txtSoLuong);

        btnThem = new JButton("Thêm");
        btnThem.setBounds(350, 110, 80, 25);
        panelSanPham.add(btnThem);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ThemHoaDonBanView::new);
    }
}