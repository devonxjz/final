package com.example.view;

import com.example.model.SanPham;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SanPhamView extends JPanel {
    // Các thành phần giao diện (Components)
    public JTable tableSanPham;
    public DefaultTableModel tableModel;
    public JTextField txtMaSP, txtTenSP, txtLoaiMay, txtCPU, txtGPU, txtRAM,
            txtOCung, txtKTManHinh, txtDPGManHinh, txtCanNang,
            txtSLTrongKho, txtGiaBan, txtGiaNhap, txtThoiGianBaoHanh, txtTimKiem;
    public JButton btnThem, btnUpdate, btnDelete, btnSave, btnReload;

    public SanPhamView() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // --- 1. Phần tiêu đề và tìm kiếm (North) ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBorder(BorderFactory.createTitledBorder("Tìm kiếm sản phẩm"));
        txtTimKiem = new JTextField();
        btnReload = new JButton("Làm mới");
        pnlHeader.add(new JLabel(" Nhập tên laptop: "), BorderLayout.WEST);
        pnlHeader.add(txtTimKiem, BorderLayout.CENTER);
        pnlHeader.add(btnReload, BorderLayout.EAST);

        // --- 2. Bảng hiển thị (Center) ---
        String[] columns = {"Mã SP", "Loại máy", "Tên sản phẩm", "CPU", "GPU", "RAM", "Ổ cứng", "Màn hình", "Độ phân giải", "Cân nặng", "Tồn kho", "Giá bán", "Giá nhập", "Bảo hành"};
        tableModel = new DefaultTableModel(columns, 0);
        tableSanPham = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableSanPham);

        // --- 3. Form nhập liệu chi tiết (East) ---
        JPanel pnlForm = new JPanel(new GridLayout(14, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Chi tiết cấu hình"));
        pnlForm.setPreferredSize(new Dimension(350, 0));

        txtMaSP = new JTextField(); txtTenSP = new JTextField(); txtLoaiMay = new JTextField();
        txtCPU = new JTextField(); txtGPU = new JTextField(); txtRAM = new JTextField();
        txtOCung = new JTextField(); txtKTManHinh = new JTextField(); txtDPGManHinh = new JTextField();
        txtCanNang = new JTextField(); txtSLTrongKho = new JTextField(); txtGiaBan = new JTextField();
        txtGiaNhap = new JTextField(); txtThoiGianBaoHanh = new JTextField();

        pnlForm.add(new JLabel("Mã SP:")); pnlForm.add(txtMaSP);
        pnlForm.add(new JLabel("Tên SP:")); pnlForm.add(txtTenSP);
        pnlForm.add(new JLabel("Loại máy:")); pnlForm.add(txtLoaiMay);
        pnlForm.add(new JLabel("CPU:")); pnlForm.add(txtCPU);
        pnlForm.add(new JLabel("GPU:")); pnlForm.add(txtGPU);
        pnlForm.add(new JLabel("RAM (GB):")); pnlForm.add(txtRAM);
        pnlForm.add(new JLabel("Ổ cứng:")); pnlForm.add(txtOCung);
        pnlForm.add(new JLabel("Kích thước MH:")); pnlForm.add(txtKTManHinh);
        pnlForm.add(new JLabel("Độ phân giải:")); pnlForm.add(txtDPGManHinh);
        pnlForm.add(new JLabel("Cân nặng (kg):")); pnlForm.add(txtCanNang);
        pnlForm.add(new JLabel("Số lượng kho:")); pnlForm.add(txtSLTrongKho);
        pnlForm.add(new JLabel("Giá bán:")); pnlForm.add(txtGiaBan);
        pnlForm.add(new JLabel("Giá nhập:")); pnlForm.add(txtGiaNhap);
        pnlForm.add(new JLabel("Bảo hành (tháng):")); pnlForm.add(txtThoiGianBaoHanh);

        // --- 4. Các nút chức năng (South) ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnThem = new JButton("Mở Form Thêm");
        btnUpdate = new JButton("Sửa");
        btnSave = new JButton("Lưu cập nhật");
        btnDelete = new JButton("Xóa");

        pnlButtons.add(btnThem); pnlButtons.add(btnUpdate);
        pnlButtons.add(btnSave); pnlButtons.add(btnDelete);

        // Gom tất cả vào giao diện chính
        add(pnlHeader, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(pnlForm, BorderLayout.EAST);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    // Phương thức hiển thị danh sách sản phẩm lên Table
    public void hienThiSanPham(List<SanPham> ds) {
        tableModel.setRowCount(0);
        for (SanPham sp : ds) {
            tableModel.addRow(new Object[]{
                    sp.getMaSP(), sp.getLoaiMay(), sp.getTenSP(), sp.getCPU(), sp.getGPU(),
                    sp.getRAM(), sp.getOCung(), sp.getKichThuocMH(), sp.getDoPhanGiaiMH(),
                    sp.getCanNang(), sp.getSoLuongTrongKho(),
                    String.format("%,.0f", sp.getGiaBan()),
                    String.format("%,.0f", sp.getGiaNhap()),
                    sp.getThoiGianBaoHanh()
            });
        }
    }
}