package com.example.controller;

import com.example.dto.ChiTietHDBHDTO;
import com.example.dto.SanPhamDTO;
import com.example.services.HoaDonBanHangService;
import com.example.services.SanPhamService;
import com.example.view.HoaDonView;

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
            try {
                int sl = Integer.parseInt(inputSL);
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
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Số lượng phải là số nguyên!");
            }
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
        try {
            int maKH = Integer.parseInt(view.txtMaKH.getText());
            double tongTien = Double.parseDouble(view.txtTongTien.getText().replace(",", ""));
            String loaiHD = view.cbLoaiHD.getSelectedItem().toString();
            
            double laiSuat = 0;
            int thoiHan = 0;
            if (loaiHD.equals("Trả góp")) {
                laiSuat = Double.parseDouble(view.txtLaiSuat.getText());
                thoiHan = Integer.parseInt(view.txtThoiHan.getText());
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Vui lòng kiểm tra Mã Khách Hàng và thông tin trả góp hợp lệ!\nChi tiết: " + e.getMessage());
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