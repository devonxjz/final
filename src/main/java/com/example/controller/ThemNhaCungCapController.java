package com.example.controller;

import com.example.dao.NhaCungCapDAO;
import com.example.view.ThemNhaCungCapView;
import com.example.util.InputValidator;
import com.example.util.ValidationResult;

import javax.swing.*;

public class ThemNhaCungCapController {
    private final NhaCungCapDAO dao;
    private final ThemNhaCungCapView view;

    public ThemNhaCungCapController(NhaCungCapDAO dao, ThemNhaCungCapView view) {
        this.dao = dao;
        this.view = view;
        initController();
    }

    private void initController() {
        view.btnThem.addActionListener(e -> themNhaCungCap());
        view.btnClear.addActionListener(e -> clearForm());
    }

    private void themNhaCungCap() {
        ValidationResult<String> rTen = InputValidator.normalizeName(view.txtTenNCC.getText(), "Tên nhà cung cấp");
        if (!rTen.isValid()) { JOptionPane.showMessageDialog(view, rTen.getErrorMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); return; }

        ValidationResult<String> rSdt = InputValidator.validatePhone(view.txtSDT.getText(), true);
        if (!rSdt.isValid()) { JOptionPane.showMessageDialog(view, rSdt.getErrorMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); return; }

        ValidationResult<String> rDiaChi = InputValidator.normalizeAddress(view.txtDiaChi.getText(), "Địa chỉ");
        if (!rDiaChi.isValid()) { JOptionPane.showMessageDialog(view, rDiaChi.getErrorMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); return; }

        String ten = rTen.getValue();
        String sdt = rSdt.getValue();
        String diaChi = rDiaChi.getValue();

        try {
            if (dao.themNhaCungCap(ten, diaChi, sdt)) {
                JOptionPane.showMessageDialog(view, "Thêm nhà cung cấp thành công!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        view.txtTenNCC.setText("");
        view.txtSDT.setText("");
        view.txtDiaChi.setText("");
    }
}
