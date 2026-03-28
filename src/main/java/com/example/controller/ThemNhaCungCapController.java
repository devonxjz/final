package com.example.controller;

import com.example.dao.NhaCungCapDAO;
import com.example.entity.NhaCungCap;
import com.example.view.ThemNhaCungCapView;

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
        String ten = view.txtTenNCC.getText().trim();
        String sdt = view.txtSDT.getText().trim();
        String diaChi = view.txtDiaChi.getText().trim();

        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập tên nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

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
