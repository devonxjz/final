package com.example.controller;

import com.example.dao.HoaDonBanHangDAO;
import com.example.entity.KhachHang;
import com.example.entity.SanPham;
import com.example.view.ThemHoaDonBanView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class ThemHoaDonBanController {
    private final HoaDonBanHangDAO dao;
    private final ThemHoaDonBanView view;
    private List<SanPham> sanPhamList;
    private Map<Integer, Integer> gioHang = new HashMap<>(); // MaSP -> SoLuong

    public ThemHoaDonBanController(HoaDonBanHangDAO dao, ThemHoaDonBanView view) {
        this.dao = dao;
        this.view = view;
        initController();
        loadSanPhamData();
        setupTables();
    }

    private void setupTables() {
        String[] headerSP = {"Mã SP", "Tên SP", "Giá bán", "Tồn kho"};
        view.tableSanPham.setModel(new DefaultTableModel(headerSP, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        });

        String[] headerChon = {"Mã SP", "Tên SP", "Số lượng", "Giá bán", "Thành tiền"};
        view.tableSanPhamChon.setModel(new DefaultTableModel(headerChon, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        });
    }

    private void initController() {
        // Tự động điền khách hàng khi nhập sđt
        view.txtSDT.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String sdt = view.txtSDT.getText().trim();
                if (!sdt.isEmpty()) {
                    KhachHang kh = dao.timKiemKhachHangTheoSDT(sdt);
                    if (kh != null) {
                        view.txtTenKH.setText(kh.getTenKH());
                        // Giả sử có thêm các set field nếu có, view.txtDiaChi...
                    }
                }
            }
        });

        // Chọn sản phẩm vào panel điền số lượng
        view.tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.tableSanPham.getSelectedRow();
                if (row >= 0) {
                    view.txtMaSP.setText(view.tableSanPham.getValueAt(row, 0).toString());
                    view.txtTenSP.setText(view.tableSanPham.getValueAt(row, 1).toString());
                    view.txtSoLuong.setText("1");
                    view.txtSoLuong.requestFocus();
                }
            }
        });

        // Thêm vào giỏ
        view.btnThem.addActionListener(e -> themVaoGio());

        // Hủy chọn
        view.btnHuyChon.addActionListener(e -> huyChonSanPham());

        // Xuất hóa đơn
        view.btnXuatHoaDon.addActionListener(e -> xuatHoaDon());
    }

    private void loadSanPhamData() {
        sanPhamList = dao.getAllSanPham();
        DefaultTableModel model = (DefaultTableModel) view.tableSanPham.getModel();
        model.setRowCount(0);
        for (SanPham sp : sanPhamList) {
            model.addRow(new Object[]{sp.getMaSP(), sp.getTenSP(), sp.getGiaBan(), sp.getSoLuongTrongKho()});
        }
    }

    private void themVaoGio() {
        if (view.txtMaSP.getText().isEmpty() || view.txtSoLuong.getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm và nhập số lượng!");
            return;
        }

        try {
            int maSP = Integer.parseInt(view.txtMaSP.getText());
            int soLuong = Integer.parseInt(view.txtSoLuong.getText());

            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(view, "Số lượng phải lớn hơn 0!");
                return;
            }

            SanPham found = sanPhamList.stream().filter(sp -> sp.getMaSP() == maSP).findFirst().orElse(null);
            if (found == null) {
                JOptionPane.showMessageDialog(view, "Không tìm thấy sản phẩm!");
                return;
            }

            if (found.getSoLuongTrongKho() < soLuong) {
                JOptionPane.showMessageDialog(view, "Không đủ hàng trong kho!");
                return;
            }

            gioHang.put(maSP, gioHang.getOrDefault(maSP, 0) + soLuong);
            updateGioHangUI();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Số lượng không hợp lệ!");
        }
    }

    private void huyChonSanPham() {
        int row = view.tableSanPhamChon.getSelectedRow();
        if (row >= 0) {
            int maSP = Integer.parseInt(view.tableSanPhamChon.getValueAt(row, 0).toString());
            gioHang.remove(maSP);
            updateGioHangUI();
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm trong giỏ để hủy!");
        }
    }

    private void updateGioHangUI() {
        DefaultTableModel model = (DefaultTableModel) view.tableSanPhamChon.getModel();
        model.setRowCount(0);

        for (Map.Entry<Integer, Integer> entry : gioHang.entrySet()) {
            int maSP = entry.getKey();
            int qty = entry.getValue();
            SanPham sp = sanPhamList.stream().filter(s -> s.getMaSP() == maSP).findFirst().orElse(null);
            if (sp != null) {
                model.addRow(new Object[]{maSP, sp.getTenSP(), qty, sp.getGiaBan(), sp.getGiaBan() * qty});
            }
        }
    }

    private void xuatHoaDon() {
        if (gioHang.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Giỏ hàng trống!");
            return;
        }

        String sdt = view.txtSDT.getText().trim();
        String tenKH = view.txtTenKH.getText().trim();
        if (sdt.isEmpty() || tenKH.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập số điện thoại và tên khách hàng!");
            return;
        }

        Date ngayTao = view.dateChooser.getDate();
        if (ngayTao == null) {
            ngayTao = new Date(); // Mặc định hôm nay
        }

        String loaiHD = view.cbLoaiHD.getSelectedItem().toString();
        String hinhThucTT = view.cbHinhThucTT.getSelectedItem().toString();
        String gioiTinh = view.cbGioiTinh.getSelectedItem().toString();
        String diaChi = view.txtDiaChi.getText().trim();

        double laiSuat = 0;
        int thoiHanTG = 0;

        if ("Trả góp".equals(loaiHD)) {
            try {
                laiSuat = Double.parseDouble(view.txtLaiSuat.getText().trim());
                // Mặc định thời hạn trả góp vì view không có field này.
                thoiHanTG = 6; 
                String inputThoiHan = JOptionPane.showInputDialog(view, "Nhập thời hạn trả góp (tháng):", "6");
                if (inputThoiHan != null && !inputThoiHan.isEmpty()) {
                     thoiHanTG = Integer.parseInt(inputThoiHan);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Lãi suất / Thời hạn trả góp không hợp lệ!");
                return;
            }
        }

        List<Map<Integer, Integer>> chiTietList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : gioHang.entrySet()) {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(entry.getKey(), entry.getValue());
            chiTietList.add(map);
        }

        // Tính tổng tiền từ giỏ hàng
        double tongTien = 0;
        for (Map.Entry<Integer, Integer> entry2 : gioHang.entrySet()) {
            SanPham found2 = sanPhamList.stream().filter(s -> s.getMaSP() == entry2.getKey()).findFirst().orElse(null);
            if (found2 != null) {
                tongTien += found2.getGiaBan() * entry2.getValue();
            }
        }

        boolean success = dao.themHoaDonVaChiTietVaThanhToan(
                ngayTao, loaiHD, tongTien, 0, chiTietList,
                tenKH, sdt, diaChi, hinhThucTT, "Chua thanh toan"
        );

        if (success) {
            JOptionPane.showMessageDialog(view, "Xuất hóa đơn thành công!");
            view.dispose(); // Đóng form
        } else {
            JOptionPane.showMessageDialog(view, "Lỗi khi xuất hóa đơn!");
        }
    }
}
