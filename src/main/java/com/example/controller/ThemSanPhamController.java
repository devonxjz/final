package com.example.controller;

import com.example.dao.SanPhamDAO;
import com.example.entity.SanPham;
import com.example.view.ThemSanPhamView;

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
            SanPham sp = new SanPham();
            sp.setLoaiMay(view.txtLoaiMay.getText().trim());
            sp.setTenSP(view.txtTenSP.getText().trim());
            sp.setCPU(view.txtCPU.getText().trim());
            sp.setGPU(view.txtGPU.getText().trim());
            sp.setRAM(Integer.parseInt(view.txtRAM.getText().trim()));
            sp.setOCung(view.txtOCung.getText().trim());
            sp.setCanNang(Float.parseFloat(view.txtCanNang.getText().trim()));
            sp.setKichThuocMH(Float.parseFloat(view.txtKTManHinh.getText().trim()));
            sp.setDoPhanGiaiMH(view.txtDPGiaiMH.getText().trim());
            sp.setSoLuongTrongKho(Integer.parseInt(view.txtSoLuong.getText().trim()));
            sp.setGiaBan(Double.parseDouble(view.txtGiaBan.getText().trim()));
            sp.setGiaNhap(Double.parseDouble(view.txtGiaNhap.getText().trim()));
            sp.setThoiGianBaoHanh(Integer.parseInt(view.txtBaoHanh.getText().trim()));

            if (dao.insertOrUpdate(sp)) { 
                JOptionPane.showMessageDialog(view, "Thêm sản phẩm thành công!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm sản phẩm thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Dữ liệu nhập vào không hợp lệ!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage());
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
