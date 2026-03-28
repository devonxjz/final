package com.example.controller;

import com.example.dao.NhaCungCapDAO;
import com.example.entity.NhaCungCap;
import com.example.view.NhaCungCapView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class NhaCungCapController {
    private final NhaCungCapDAO dao;
    private final NhaCungCapView view;
    private List<NhaCungCap> allNhaCungCap;

    public NhaCungCapController(NhaCungCapDAO dao, NhaCungCapView view) {
        this.dao = dao;
        this.view = view;
        initController();
        loadData();
        view.setEditableFields(false);
    }

    private void initController() {
        view.btnReload.addActionListener(e -> loadData());

        view.tableNCC.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.tableNCC.getSelectedRow();
                if (row >= 0) {
                    displaySelectedRow(row);
                    view.setEditableFields(false);
                }
            }
        });

        view.txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { search(); }
            @Override
            public void removeUpdate(DocumentEvent e) { search(); }
            @Override
            public void changedUpdate(DocumentEvent e) { search(); }
        });

        view.btnUpdate.addActionListener(e -> view.setEditableFields(true));

        view.btnSave.addActionListener(e -> saveNhaCungCap());

        view.btnDelete.addActionListener(e -> deleteNhaCungCap());

        view.btnThem.addActionListener(e -> {
            clearInput();
            view.setEditableFields(true);
            view.txtMaNCC.setText("");
        });
    }

    private void loadData() {
        allNhaCungCap = dao.getAllNhaCungCap();
        view.hienThiDanhSachNCC(allNhaCungCap);
    }

    private void clearInput() {
        view.txtMaNCC.setText("");
        view.txtTenNCC.setText("");
        view.txtSDT.setText("");
        view.txtDiaChi.setText("");
    }

    private void search() {
        String keyword = view.txtTimKiem.getText().trim().toLowerCase();
        if (allNhaCungCap == null) return;
        List<NhaCungCap> result = allNhaCungCap.stream()
                .filter(ncc -> ncc.getTenNCC().toLowerCase().contains(keyword) || 
                               (ncc.getSdt() != null && ncc.getSdt().contains(keyword)))
                .collect(Collectors.toList());
        view.hienThiDanhSachNCC(result);
    }

    private void displaySelectedRow(int row) {
        var model = view.tableNCC.getModel();
        view.txtMaNCC.setText(getValue(model, row, 0));
        view.txtTenNCC.setText(getValue(model, row, 1));
        view.txtSDT.setText(getValue(model, row, 2));
        view.txtDiaChi.setText(getValue(model, row, 3));
    }

    private String getValue(javax.swing.table.TableModel model, int row, int col) {
        Object val = model.getValueAt(row, col);
        return (val == null) ? "" : val.toString();
    }

    private void saveNhaCungCap() {
        if (view.txtTenNCC.getText().isEmpty() || view.txtSDT.getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tên và Số điện thoại không được để trống!");
            return;
        }

        String tenNCC = view.txtTenNCC.getText();
        String sdt = view.txtSDT.getText();
        String diaChi = view.txtDiaChi.getText();

        boolean success;
        if (!view.txtMaNCC.getText().isEmpty()) {
            // Update
            int maNCC = Integer.parseInt(view.txtMaNCC.getText());
            success = dao.capNhatNhaCungCap(maNCC, tenNCC, diaChi, sdt);
        } else {
            // Insert
            success = dao.themNhaCungCap(tenNCC, diaChi, sdt);
        }

        if (success) {
            JOptionPane.showMessageDialog(view, "Lưu thông tin thành công!");
            loadData();
            view.setEditableFields(false);
        } else {
            JOptionPane.showMessageDialog(view, "Thao tác thất bại!");
        }
    }

    private void deleteNhaCungCap() {
        int row = view.tableNCC.getSelectedRow();
        if (row >= 0) {
            String maNCCStr = view.tableNCC.getValueAt(row, 0).toString();
            int maNCC = Integer.parseInt(maNCCStr);
            if (dao.kiemTraNhaCungCapDuocXoa(maNCCStr)) {
                JOptionPane.showMessageDialog(view, "Không thể xóa nhà cung cấp đã có hóa đơn nhập!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa nhà cung cấp này?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.xoaNhaCungCap(maNCC)) {
                    loadData();
                    clearInput();
                } else {
                    JOptionPane.showMessageDialog(view, "Không thể xóa nhà cung cấp!");
                }
            }
        }
    }
}
