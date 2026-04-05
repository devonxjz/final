package com.example.view;

import com.example.config.UIThemeConfig;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

/**
 * Giao diện Tạo Hóa Đơn Bán Hàng Mới (Popup)
 * Đầy đủ thông tin Khách hàng, Giỏ hàng và Thanh toán
 */
public class ThemHoaDonBanView extends JPanel {

    // 1. Thông tin khách hàng & Hóa đơn
    public JTextField txtSDT, txtTenKH, txtDiaChi;
    public JComboBox<String> cbGioiTinh, cbLoaiHD, cbHinhThucTT;
    public JDateChooser dateChooser;

    // 2. Thông tin chọn sản phẩm
    public JTextField txtMaSP, txtTenSP, txtSoLuong;
    public JButton btnThem, btnHuyChon, btnXuatHoaDon;
    public JTable tableSanPham, tableSanPhamChon;

    public ThemHoaDonBanView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIThemeConfig.BG_DARK);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // =========================================================
        // PHẦN BẮC (NORTH): THÔNG TIN KHÁCH HÀNG & HÓA ĐƠN
        // =========================================================
        JPanel pnlTop = UIThemeConfig.createGlassPanel(new BorderLayout(10, 10));
        pnlTop.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, UIThemeConfig.ACCENT),
            new EmptyBorder(10, 15, 10, 15)));

        JLabel lblTitle = UIThemeConfig.createTitleLabel("THÔNG TIN KHÁCH HÀNG & ĐƠN HÀNG");
        lblTitle.setForeground(UIThemeConfig.ACCENT);
        pnlTop.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlInfo = new JPanel(new GridLayout(3, 4, 15, 10));
        pnlInfo.setOpaque(false);

        // Khởi tạo components
        txtSDT = UIThemeConfig.createTextField();
        txtTenKH = UIThemeConfig.createTextField();
        txtDiaChi = UIThemeConfig.createTextField();
        cbGioiTinh = UIThemeConfig.createComboBox(new String[]{"Nam", "Nữ", "Khác"});
        cbLoaiHD = UIThemeConfig.createComboBox(new String[]{"Trả thẳng", "Trả góp"});
        cbHinhThucTT = UIThemeConfig.createComboBox(new String[]{"Tiền mặt", "Chuyển khoản", "Quẹt thẻ"});

        cbLoaiHD.addActionListener(e -> {
            boolean isTraGop = "Trả góp".equals(cbLoaiHD.getSelectedItem());
            cbHinhThucTT.setEnabled(!isTraGop); // Vô hiệu hóa (làm mờ) nếu là Trả góp
        });

        dateChooser = new JDateChooser(new Date());
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setBackground(UIThemeConfig.BG_INPUT);

        // Hàng 1
        pnlInfo.add(UIThemeConfig.createLabel("Số điện thoại:"));
        pnlInfo.add(txtSDT);
        pnlInfo.add(UIThemeConfig.createLabel("Loại hóa đơn:"));
        pnlInfo.add(cbLoaiHD);

        // Hàng 2
        pnlInfo.add(UIThemeConfig.createLabel("Tên KH:"));
        pnlInfo.add(txtTenKH);
        pnlInfo.add(UIThemeConfig.createLabel("Hình thức TT:"));
        pnlInfo.add(cbHinhThucTT);

        // Hàng 3
        pnlInfo.add(UIThemeConfig.createLabel("Giới tính:"));
        pnlInfo.add(cbGioiTinh);
        pnlInfo.add(UIThemeConfig.createLabel("Ngày tạo:"));
        pnlInfo.add(dateChooser);

        pnlTop.add(pnlInfo, BorderLayout.CENTER);

        // Thêm địa chỉ (Nằm riêng cho dài)
        JPanel pnlDiaChi = new JPanel(new BorderLayout(10, 0));
        pnlDiaChi.setOpaque(false);
        JLabel lblDiaChi = UIThemeConfig.createLabel("Địa chỉ:");
        lblDiaChi.setPreferredSize(new Dimension(85, 30));
        pnlDiaChi.add(lblDiaChi, BorderLayout.WEST);
        pnlDiaChi.add(txtDiaChi, BorderLayout.CENTER);
        pnlTop.add(pnlDiaChi, BorderLayout.SOUTH);

        add(pnlTop, BorderLayout.NORTH);

        // =========================================================
        // PHẦN GIỮA (CENTER): CHỌN SẢN PHẨM & GIỎ HÀNG
        // =========================================================
        JPanel pnlCenter = new JPanel(new GridLayout(2, 1, 0, 15));
        pnlCenter.setBackground(UIThemeConfig.BG_DARK);

        // ---- 1. BẢNG DANH SÁCH SẢN PHẨM (KHO) ----
        JPanel pnlKho = UIThemeConfig.createGlassPanel(new BorderLayout(5, 5));
        pnlKho.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIThemeConfig.BORDER),
            "Danh sách sản phẩm", 0, 0, UIThemeConfig.FONT_BODY, UIThemeConfig.TEXT_PRIMARY));

        tableSanPham = new JTable();
        UIThemeConfig.styleTable(tableSanPham);
        pnlKho.add(UIThemeConfig.createScrollPane(tableSanPham), BorderLayout.CENTER);

        // Thanh công cụ thêm vào giỏ
        JPanel pnlAddCart = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlAddCart.setOpaque(false);
        txtMaSP = UIThemeConfig.createTextField(); txtMaSP.setPreferredSize(new Dimension(50, 30)); txtMaSP.setEditable(false);
        txtTenSP = UIThemeConfig.createTextField(); txtTenSP.setPreferredSize(new Dimension(200, 30)); txtTenSP.setEditable(false);
        txtSoLuong = UIThemeConfig.createTextField(); txtSoLuong.setPreferredSize(new Dimension(60, 30));
        btnThem = UIThemeConfig.createPrimaryButton("Thêm vào giỏ");

        pnlAddCart.add(UIThemeConfig.createLabel("Mã SP:")); pnlAddCart.add(txtMaSP);
        pnlAddCart.add(UIThemeConfig.createLabel("Tên SP:")); pnlAddCart.add(txtTenSP);
        pnlAddCart.add(UIThemeConfig.createLabel("SL:")); pnlAddCart.add(txtSoLuong);
        pnlAddCart.add(btnThem);
        pnlKho.add(pnlAddCart, BorderLayout.SOUTH);

        pnlCenter.add(pnlKho);

        // ---- 2. BẢNG GIỎ HÀNG ----
        JPanel pnlGio = UIThemeConfig.createGlassPanel(new BorderLayout(5, 5));
        pnlGio.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIThemeConfig.BORDER),
            "Giỏ hàng của khách", 0, 0, UIThemeConfig.FONT_BODY, UIThemeConfig.ACCENT));

        tableSanPhamChon = new JTable();
        UIThemeConfig.styleTable(tableSanPhamChon);
        pnlGio.add(UIThemeConfig.createScrollPane(tableSanPhamChon), BorderLayout.CENTER);

        JPanel pnlRemoveCart = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlRemoveCart.setOpaque(false);
        btnHuyChon = UIThemeConfig.createDangerButton("Bỏ sản phẩm");
        pnlRemoveCart.add(btnHuyChon);
        pnlGio.add(pnlRemoveCart, BorderLayout.SOUTH);

        pnlCenter.add(pnlGio);

        add(pnlCenter, BorderLayout.CENTER);

        // =========================================================
        // PHẦN NAM (SOUTH): NÚT XUẤT HÓA ĐƠN
        // =========================================================
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlBottom.setOpaque(false);
        btnXuatHoaDon = UIThemeConfig.createSuccessButton("✔ XUẤT HÓA ĐƠN");
        btnXuatHoaDon.setPreferredSize(new Dimension(200, 40));
        btnXuatHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnlBottom.add(btnXuatHoaDon);

        add(pnlBottom, BorderLayout.SOUTH);
    }
}
