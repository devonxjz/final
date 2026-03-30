package com.example.controller;

import com.example.dao.SanPhamDAO;
import com.example.entity.SanPham;
import com.example.view.ThemSanPhamView;
import com.example.util.InputValidator;
import com.example.util.ValidationResult;

import javax.swing.*;

public class ThemSanPhamController {
    private final SanPhamDAO dao;
    private final ThemSanPhamView view;

    public ThemSanPhamController(SanPhamDAO dao, ThemSanPhamView view) {
        this.dao = dao;
        this.view = view;
        view.btnThem.addActionListener(e -> themSanPham());
        view.btnClear.addActionListener(e -> clearForm());
    }

    private void themSanPham() {
        try {
            ValidationResult<String> rLoaiMay = InputValidator.validateRequiredString(view.txtLoaiMay.getText(), "Loại máy", 255);
            if (!rLoaiMay.isValid()) { JOptionPane.showMessageDialog(view, rLoaiMay.getErrorMessage(), "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE); view.txtLoaiMay.requestFocus(); return; }
            
            ValidationResult<String> rTenSP = InputValidator.validateRequiredString(view.txtTenSP.getText(), "Tên sản phẩm", 255);
            if (!rTenSP.isValid()) { JOptionPane.showMessageDialog(view, rTenSP.getErrorMessage(), "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE); view.txtTenSP.requestFocus(); return; }
            
            ValidationResult<Integer> rRAM = InputValidator.parseIntSafe(view.txtRAM.getText(), "RAM");
            if (!rRAM.isValid()) { JOptionPane.showMessageDialog(view, "RAM không hợp lệ! Ví dụ: 16 hoặc 32gb", "Lỗi", JOptionPane.ERROR_MESSAGE); view.txtRAM.requestFocus(); return; }
            
            ValidationResult<Float> rCanNang = InputValidator.parseFloatSafe(view.txtCanNang.getText(), "Cân nặng");
            if (!rCanNang.isValid()) { JOptionPane.showMessageDialog(view, "Cân nặng không hợp lệ! Ví dụ: 1.5", "Lỗi", JOptionPane.ERROR_MESSAGE); view.txtCanNang.requestFocus(); return; }
            
            ValidationResult<Float> rKT = InputValidator.parseFloatSafe(view.txtKTManHinh.getText(), "Kích thước màn hình");
            if (!rKT.isValid()) { JOptionPane.showMessageDialog(view, "Kích thước màn hình không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE); view.txtKTManHinh.requestFocus(); return; }
            
            ValidationResult<Integer> rSL = InputValidator.parseIntSafe(view.txtSoLuong.getText(), "Số lượng");
            if (!rSL.isValid()) { JOptionPane.showMessageDialog(view, "Số lượng không hợp lệ! Ví dụ: 45", "Lỗi", JOptionPane.ERROR_MESSAGE); view.txtSoLuong.requestFocus(); return; }
            
            ValidationResult<Double> rGiaBan = InputValidator.parseCurrency(view.txtGiaBan.getText(), "Giá bán");
            if (!rGiaBan.isValid()) { JOptionPane.showMessageDialog(view, rGiaBan.getErrorMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); view.txtGiaBan.requestFocus(); return; }
            
            ValidationResult<Double> rGiaNhap = InputValidator.parseCurrency(view.txtGiaNhap.getText(), "Giá nhập");
            if (!rGiaNhap.isValid()) { JOptionPane.showMessageDialog(view, rGiaNhap.getErrorMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); view.txtGiaNhap.requestFocus(); return; }
            
            ValidationResult<Integer> rBH = InputValidator.parseIntSafe(view.txtBaoHanh.getText(), "Thời gian bảo hành");
            if (!rBH.isValid()) { JOptionPane.showMessageDialog(view, "Thời gian bảo hành không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE); view.txtBaoHanh.requestFocus(); return; }

            SanPham sp = new SanPham();
            sp.setLoaiMay(rLoaiMay.getValue());
            sp.setTenSP(rTenSP.getValue());
            sp.setCPU(view.txtCPU.getText());
            sp.setGPU(view.txtGPU.getText());
            sp.setRAM(rRAM.getValue());
            sp.setOCung(view.txtOCung.getText());
            sp.setCanNang(rCanNang.getValue());
            sp.setKichThuocMH(rKT.getValue());
            sp.setDoPhanGiaiMH(view.txtDPGiaiMH.getText());
            sp.setSoLuongTrongKho(rSL.getValue());
            sp.setGiaBan(rGiaBan.getValue());
            sp.setGiaNhap(rGiaNhap.getValue());
            sp.setThoiGianBaoHanh(rBH.getValue());

            if (dao.insertOrUpdate(sp)) {
                JOptionPane.showMessageDialog(view, "Thêm sản phẩm thành công!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm sản phẩm thất bại! Kiểm tra lại kết nối database.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        view.txtLoaiMay.setText("");
        view.txtTenSP.setText("");
        view.txtCPU.setText("");
        view.txtGPU.setText("");
        view.txtRAM.setText("");
        view.txtOCung.setText("");
        view.txtCanNang.setText("");
        view.txtKTManHinh.setText("");
        view.txtDPGiaiMH.setText("");
        view.txtSoLuong.setText("");
        view.txtGiaBan.setText("");
        view.txtGiaNhap.setText("");
        view.txtBaoHanh.setText("");
    }
}
