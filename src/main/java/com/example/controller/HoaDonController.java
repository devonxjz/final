package org.example.controller;

import com.example.model.HoaDon;
import com.example.model.ChiTietHoaDon;
import com.example.model.SanPham;
import com.example.dao.HoaDonDAO;
import com.example.dao.SanPhamDAO;
import com.example.view.HoaDonView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller xử lý nghiệp vụ Bán hàng và Trả góp
 * Kết nối giữa Hóa đơn, Sản phẩm và Khách hàng
 */
public class HoaDonController {
    private final HoaDonDAO hoadonDao;
    private final SanPhamDAO sanphamDao;
    private final HoaDonView view;
    private List<ChiTietHoaDon> gioHang = new ArrayList<>();

    public HoaDonController(HoaDonDAO hoadonDao, SanPhamDAO sanphamDao, HoaDonView view) {
        this.hoadonDao = hoadonDao;
        this.sanphamDao = sanphamDao;
        this.view = view;
        initController();
        loadInitialData();
    }

    private void initController() {
        // 1. Chọn sản phẩm từ danh sách bên trái để thêm vào giỏ hàng
        view.tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double click để chọn
                    themSanPhamVaoGio();
                }
            }
        });

        // 2. Nút Thêm vào giỏ hàng
        view.btnAddToCard.addActionListener(e -> themSanPhamVaoGio());

        // 3. Nút Xóa khỏi giỏ hàng
        view.btnRemoveFromCard.addActionListener(e -> xoaSanPhamKhoiGio());

        // 4. Tính toán khi chọn loại hình (Trực tiếp / Trả góp)
        view.cbLoaiHD.addActionListener(e -> updateUIPayMode());

        // 5. Nút Thanh toán & Xuất hóa đơn
        view.btnThanhToan.addActionListener(e -> xuLyThanhToan());

        // 6. Nút Hủy đơn hàng
        view.btnHuy.addActionListener(e -> resetForm());
    }

    private void loadInitialData() {
        // Load danh sách sản phẩm còn hàng để bán
        List<SanPham> dsSP = sanphamDao.getAllSanPham();
        view.hienThiDanhSachSanPham(dsSP);
        updateUIPayMode();
    }

    private void updateUIPayMode() {
        boolean isTraGop = view.cbLoaiHD.getSelectedItem().toString().equals("Trả góp");
        view.panelTraGop.setVisible(isTraGop); // Hiện/ẩn khung nhập lãi suất, thời hạn
    }

    private void themSanPhamVaoGio() {
        int row = view.tableSanPham.getSelectedRow();
        if (row < 0) return;

        int maSP = Integer.parseInt(view.tableSanPham.getValueAt(row, 0).toString());
        String tenSP = view.tableSanPham.getValueAt(row, 1).toString();
        double giaBan = Double.parseDouble(view.tableSanPham.getValueAt(row, 2).toString());

        // Nhập số lượng mua
        String inputSL = JOptionPane.showInputDialog(view, "Nhập số lượng cho " + tenSP + ":", "1");
        if (inputSL != null && !inputSL.isEmpty()) {
            try {
                int sl = Integer.parseInt(inputSL);
                // Kiểm tra tồn kho (Logic nghiệp vụ quan trọng)
                int tonKho = Integer.parseInt(view.tableSanPham.getValueAt(row, 3).toString());
                if (sl > tonKho) {
                    JOptionPane.showMessageDialog(view, "Số lượng tồn kho không đủ!");
                    return;
                }

                ChiTietHoaDon ct = new ChiTietHoaDon(maSP, tenSP, sl, giaBan);
                gioHang.add(ct);
                updateTableGioHang();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Số lượng phải là số nguyên!");
            }
        }
    }

    private void updateTableGioHang() {
        double tongTien = 0;
        DefaultTableModel model = (DefaultTableModel) view.tableGioHang.getModel();
        model.setRowCount(0);

        for (ChiTietHoaDon ct : gioHang) {
            double thanhTien = ct.getSoLuong() * ct.getGiaBan();
            tongTien += thanhTien;
            model.addRow(new Object[]{ct.getMaSP(), ct.getTenSP(), ct.getSoLuong(), ct.getGiaBan(), thanhTien});
        }
        view.txtTongTien.setText(String.format("%.0f", tongTien));
    }

    private void xuLyThanhToan() {
        if (gioHang.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Giỏ hàng đang trống!");
            return;
        }

        try {
            HoaDon hd = new HoaDon();
            hd.setMaKH(Integer.parseInt(view.txtMaKH.getText()));
            hd.setTongTien(Double.parseDouble(view.txtTongTien.getText()));
            hd.setLoaiHD(view.cbLoaiHD.getSelectedItem().toString());
            hd.setTrangThai("Đã thanh toán");

            // Nếu trả góp, lấy thêm thông tin từ panel trả góp
            if (hd.getLoaiHD().equals("Trả góp")) {
                hd.setLaiSuat(Double.parseDouble(view.txtLaiSuat.getText()));
                hd.setThoiHan(Integer.parseInt(view.txtThoiHan.getText()));
            }

            // Lưu vào DB qua DAO (Hàm này thường xử lý Transaction: Lưu HD -> Lưu CT -> Trừ kho)
            if (hoadonDao.saveHoaDonFull(hd, gioHang)) {
                JOptionPane.showMessageDialog(view, "Thanh toán thành công!");
                resetForm();
                loadInitialData(); // Reload kho hàng
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi khi lưu hóa đơn!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Vui lòng kiểm tra đầy đủ thông tin khách hàng và thanh toán!");
        }
    }

    private void xoaSanPhamKhoiGio() {
        int row = view.tableGioHang.getSelectedRow();
        if (row >= 0) {
            gioHang.remove(row);
            updateTableGioHang();
        }
    }

    private void resetForm() {
        gioHang.clear();
        updateTableGioHang();
        view.txtMaKH.setText("");
        view.txtTenKH.setText("");
        // Reset các field khác...
    }
}