package com.example.view;

import com.example.config.UITheme;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;

public class ThemHoaDonBanView extends JFrame {
    public JTextField txtTimKiem, txtSDT, txtTenKH, txtDiaChi, txtMaSP, txtTenSP, txtSoLuong, txtLaiSuat;
    public JComboBox<String> cbGioiTinh, cbLoaiHD, cbHinhThucTT;
    public JDateChooser dateChooser;
    public JTable tableSanPham, tableSanPhamChon;
    public JButton btnHuyChon, btnThem, btnXuatHoaDon;

    public ThemHoaDonBanView() {
        setTitle("Thêm hoá đơn bán hàng");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 650);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(8, 8));

        // === HEADER ===
        JPanel pnlHeader = new JPanel(new BorderLayout(10, 0));
        pnlHeader.setBackground(UITheme.BG_DARK);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        JLabel lblSearch = UITheme.createLabel("Tìm kiếm SP:");
        lblSearch.setFont(UITheme.FONT_SUBTITLE);
        txtTimKiem = UITheme.createTextField();
        btnXuatHoaDon = UITheme.createSuccessButton("Xuất hóa đơn");
        pnlHeader.add(lblSearch, BorderLayout.WEST);
        pnlHeader.add(txtTimKiem, BorderLayout.CENTER);
        pnlHeader.add(btnXuatHoaDon, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // === LEFT: Tables ===
        JPanel pnlLeft = new JPanel(new BorderLayout(5, 5));
        pnlLeft.setBackground(UITheme.BG_DARK);
        pnlLeft.setPreferredSize(new Dimension(520, 0));
        pnlLeft.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 5));

        tableSanPham = new JTable(); UITheme.styleTable(tableSanPham);
        JScrollPane spSP = UITheme.createScrollPane(tableSanPham);
        pnlLeft.add(spSP, BorderLayout.CENTER);

        JPanel pnlChon = new JPanel(new BorderLayout(5, 2));
        pnlChon.setBackground(UITheme.BG_DARK);
        pnlChon.setPreferredSize(new Dimension(0, 180));
        JPanel pnlChonHeader = new JPanel(new BorderLayout());
        pnlChonHeader.setBackground(UITheme.BG_DARK);
        JLabel lblChon = UITheme.createLabel("Sản phẩm đã chọn");
        lblChon.setFont(UITheme.FONT_SUBTITLE); lblChon.setForeground(UITheme.ACCENT);
        btnHuyChon = UITheme.createDangerButton("Hủy chọn");
        pnlChonHeader.add(lblChon, BorderLayout.WEST);
        pnlChonHeader.add(btnHuyChon, BorderLayout.EAST);
        pnlChon.add(pnlChonHeader, BorderLayout.NORTH);
        tableSanPhamChon = new JTable(); UITheme.styleTable(tableSanPhamChon);
        pnlChon.add(UITheme.createScrollPane(tableSanPhamChon), BorderLayout.CENTER);
        pnlLeft.add(pnlChon, BorderLayout.SOUTH);
        add(pnlLeft, BorderLayout.WEST);

        // === RIGHT: Info form ===
        JPanel pnlRight = new JPanel(new BorderLayout(5, 8));
        pnlRight.setBackground(UITheme.BG_DARK);
        pnlRight.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 15));

        JPanel pnlKH = UITheme.createCard();
        pnlKH.setLayout(new GridLayout(7, 2, 8, 6));
        pnlKH.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)));

        txtSDT = UITheme.createTextField(); txtTenKH = UITheme.createTextField();
        txtDiaChi = UITheme.createTextField(); txtLaiSuat = UITheme.createTextField();
        cbGioiTinh = UITheme.createComboBox(new String[]{"Nam", "Nữ", "Khác"});
        dateChooser = new JDateChooser(); dateChooser.setDateFormatString("dd/MM/yyyy");
        cbLoaiHD = UITheme.createComboBox(new String[]{"Trả thẳng", "Trả góp"});
        cbHinhThucTT = UITheme.createComboBox(new String[]{"Tiền mặt", "Chuyển khoản", "Thẻ tín dụng"});

        pnlKH.add(UITheme.createLabel("SĐT:")); pnlKH.add(txtSDT);
        pnlKH.add(UITheme.createLabel("Tên KH:")); pnlKH.add(txtTenKH);
        pnlKH.add(UITheme.createLabel("Giới tính:")); pnlKH.add(cbGioiTinh);
        pnlKH.add(UITheme.createLabel("Địa chỉ:")); pnlKH.add(txtDiaChi);
        pnlKH.add(UITheme.createLabel("Ngày tạo:")); pnlKH.add(dateChooser);
        pnlKH.add(UITheme.createLabel("Loại HĐ:")); pnlKH.add(cbLoaiHD);
        pnlKH.add(UITheme.createLabel("Lãi suất:")); pnlKH.add(txtLaiSuat);
        pnlRight.add(pnlKH, BorderLayout.CENTER);

        JPanel pnlSP = UITheme.createCard();
        pnlSP.setLayout(new GridLayout(3, 2, 8, 6));
        pnlSP.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        pnlSP.setPreferredSize(new Dimension(0, 140));
        txtMaSP = UITheme.createTextField(); txtTenSP = UITheme.createTextField();
        txtSoLuong = UITheme.createTextField();
        pnlSP.add(UITheme.createLabel("Mã SP:")); pnlSP.add(txtMaSP);
        pnlSP.add(UITheme.createLabel("Tên SP:")); pnlSP.add(txtTenSP);
        pnlSP.add(UITheme.createLabel("Số lượng:")); pnlSP.add(txtSoLuong);

        JPanel pnlBottom = new JPanel(new BorderLayout(0, 5));
        pnlBottom.setBackground(UITheme.BG_DARK);
        pnlBottom.add(pnlSP, BorderLayout.CENTER);
        btnThem = UITheme.createPrimaryButton("Thêm vào đơn");
        JPanel pnlBtnAdd = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtnAdd.setBackground(UITheme.BG_DARK);
        pnlBtnAdd.add(btnThem);
        pnlBottom.add(pnlBtnAdd, BorderLayout.SOUTH);
        pnlRight.add(pnlBottom, BorderLayout.SOUTH);

        JPanel pnlHTTT = new JPanel(new GridLayout(1, 2, 8, 0));
        pnlHTTT.setBackground(UITheme.BG_CARD);
        pnlHTTT.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        pnlHTTT.setPreferredSize(new Dimension(0, 35));
        pnlHTTT.add(UITheme.createLabel("Hình thức TT:")); pnlHTTT.add(cbHinhThucTT);
        pnlRight.add(pnlHTTT, BorderLayout.NORTH);

        add(pnlRight, BorderLayout.CENTER);
        setVisible(true);
    }
}