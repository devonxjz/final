package com.example.controller;

import com.example.dto.ChiTietHDBHDTO;
import com.example.dto.SanPhamDTO;
import com.example.dto.ChiTietHDBHDTO;
import com.example.dto.SanPhamDTO;
import com.example.services.HoaDonBanHangService;
import com.example.services.SanPhamService;
import com.example.view.HoaDonView;
import com.example.util.InputValidator;
import com.example.util.ValidationResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller xử lý nghiệp vụ Bán hàng và Trả góp
 * Kết hợp mô hình 3 layer (View <-> Controller <-> Service)
 */
public class HoaDonController {
    private final HoaDonBanHangService hoaDonService;
    private final SanPhamService sanPhamService;
    private final HoaDonView view;
    private List<ChiTietHDBHDTO> gioHang = new ArrayList<>();

    public HoaDonController(HoaDonBanHangService hdService, SanPhamService spService, HoaDonView view) {
        this.hoaDonService = hdService;
        this.sanPhamService = spService;
        this.view = view;
        initController();
        loadInitialData();
    }

    private void initController() {
        // 1. Chọn sản phẩm từ danh sách bên trái để thêm vào giỏ hàng
        view.tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
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
        // Lấy DTO từ Service để hiển thị giao diện, không dùng Entity
        List<SanPhamDTO> dsSP = sanPhamService.getAllSanPham();
        // Giả sử View của bạn tạm thời vẫn lấy List<SanPham>, nhưng thực tế ở đây ta phải fix View để lấy List<SanPhamDTO>
        // Vì vậy thay vì gọi hàm cũ, ta sẽ duyệt model trực tiếp nếu hàm view chưa chuẩn:
        DefaultTableModel tModel = (DefaultTableModel) view.tableSanPham.getModel();
        tModel.setRowCount(0);
        for (SanPhamDTO dto : dsSP) {
            tModel.addRow(new Object[]{dto.maSP(), dto.tenSP(), dto.giaBan(), dto.soLuongTrongKho()});
        }
        updateUIPayMode();
    }

    private void updateUIPayMode() {
        boolean isTraGop = view.cbLoaiHD.getSelectedItem().toString().equals("Trả góp");
        view.panelTraGop.setVisible(isTraGop);
    }

    private void themSanPhamVaoGio() {
        int row = view.tableSanPham.getSelectedRow();
        if (row < 0) return;

        int maSP = Integer.parseInt(view.tableSanPham.getValueAt(row, 0).toString());
        String tenSP = view.tableSanPham.getValueAt(row, 1).toString();
        double giaBan = Double.parseDouble(view.tableSanPham.getValueAt(row, 2).toString());
        int tonKho = Integer.parseInt(view.tableSanPham.getValueAt(row, 3).toString());

        String inputSL = JOptionPane.showInputDialog(view, "Nhập số lượng cho " + tenSP + ":", "1");
        if (inputSL != null && !inputSL.isEmpty()) {
            ValidationResult<Integer> rSL = InputValidator.parseIntSafe(inputSL, "Số lượng");
            if (!rSL.isValid()) {
                JOptionPane.showMessageDialog(view, rSL.getErrorMessage());
                return;
            }
            int sl = rSL.getValue();
            if (sl <= 0) {
                JOptionPane.showMessageDialog(view, "Số lượng phải > 0!");
                return;
            }
            if (sl > tonKho) {
                JOptionPane.showMessageDialog(view, "Số lượng tồn kho không đủ!");
                return;
            }
            
            // Kiểm tra xem giỏ hàng đã có sp này chưa, nếu có thì cộng dồn!
            boolean found = false;
            for (int i = 0; i < gioHang.size(); i++) {
                if (gioHang.get(i).maSP() == maSP) {
                    ChiTietHDBHDTO current = gioHang.get(i);
                    int newSl = current.soLuong() + sl;
                    if(newSl > tonKho) {
                        JOptionPane.showMessageDialog(view, "Số lượng mua vượt tồn kho!");
                        return;
                    }
                    gioHang.set(i, new ChiTietHDBHDTO(0, maSP, tenSP, newSl, giaBan, newSl * giaBan));
                    found = true;
                    break;
                }
            }
            if (!found) {
                gioHang.add(new ChiTietHDBHDTO(0, maSP, tenSP, sl, giaBan, sl * giaBan));
            }
            updateTableGioHang();
        }
    }

    private void updateTableGioHang() {
        double tongTien = 0;
        DefaultTableModel model = (DefaultTableModel) view.tableGioHang.getModel();
        model.setRowCount(0);

        for (ChiTietHDBHDTO ct : gioHang) {
            tongTien += ct.tongTien();
            model.addRow(new Object[]{ct.maSP(), ct.tenSP(), ct.soLuong(), ct.giaBan(), ct.tongTien()});
        }
        view.txtTongTien.setText(String.format("%.0f", tongTien));
    }

    private void xuLyThanhToan() {
        if (gioHang.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Giỏ hàng đang trống!");
            return;
        }

        ValidationResult<Integer> rMaKH = InputValidator.parseIntSafe(view.txtMaKH.getText(), "Mã khách hàng");
        if (!rMaKH.isValid()) { JOptionPane.showMessageDialog(view, rMaKH.getErrorMessage()); return; }

        ValidationResult<Double> rTongTien = InputValidator.parseCurrency(view.txtTongTien.getText(), "Tổng tiền");
        if (!rTongTien.isValid()) { JOptionPane.showMessageDialog(view, rTongTien.getErrorMessage()); return; }

        int maKH = rMaKH.getValue();
        double tongTien = rTongTien.getValue();
        String loaiHD = view.cbLoaiHD.getSelectedItem().toString();
        
        double laiSuat = 0;
        int thoiHan = 0;
        if (loaiHD.equals("Trả góp")) {
            ValidationResult<Float> rLaiSuat = InputValidator.parseFloatSafe(view.txtLaiSuat.getText(), "Lãi suất");
            if (!rLaiSuat.isValid()) { JOptionPane.showMessageDialog(view, rLaiSuat.getErrorMessage()); return; }
            laiSuat = rLaiSuat.getValue();

            ValidationResult<Integer> rThoiHan = InputValidator.parseIntSafe(view.txtThoiHan.getText(), "Thời hạn");
            if (!rThoiHan.isValid()) { JOptionPane.showMessageDialog(view, rThoiHan.getErrorMessage()); return; }
            thoiHan = rThoiHan.getValue();
        }

        // Giao cho Service xử lý toàn bộ transaction xuống DAO
        boolean isSuccess = hoaDonService.thanhToanHoaDon(maKH, loaiHD, tongTien, laiSuat, thoiHan, gioHang);

        if (isSuccess) {
            JOptionPane.showMessageDialog(view, "Thanh toán thành công! Đã tự động trừ chi tiết và kho hàng.");
            resetForm();
            loadInitialData(); // Reload kho hàng
        } else {
            JOptionPane.showMessageDialog(view, "Lỗi khi lưu hóa đơn! (Khách Hàng không tồn tại?)");
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
        view.txtLaiSuat.setText("");
        view.txtThoiHan.setText("");
        // Reset các field khác ở View (VD: SDT, Địa chỉ)
    }
}