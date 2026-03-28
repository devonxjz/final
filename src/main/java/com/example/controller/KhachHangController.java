package com.example.controller;

import com.example.dto.KhachHangDTO;
import com.example.services.KhachHangService;
import com.example.view.KhachHangView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Controller quản lý thông tin khách hàng
 * Giao tiếp qua Service (KhachHangService) và DTO
 */
public class KhachHangController {
    private final KhachHangService service;
    private final KhachHangView view;

    public KhachHangController(KhachHangService service, KhachHangView view) {
        this.service = service;
        this.view = view;
        initController();
        loadData();
        setInputEnabled(false);
    }

    private void initController() {
        // Nút Làm mới danh sách
        view.btnReload.addActionListener(e -> loadData());

        // Chọn khách hàng từ bảng để hiển thị lên Form
        view.tableKhachHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.tableKhachHang.getSelectedRow();
                if (row >= 0) {
                    displaySelectedRow(row);
                    setInputEnabled(false);
                }
            }
        });

        // Tìm kiếm khách hàng theo tên hoặc số điện thoại
        view.txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { search(); }
            @Override
            public void removeUpdate(DocumentEvent e) { search(); }
            @Override
            public void changedUpdate(DocumentEvent e) { search(); }
        });

        // Nút Sửa
        view.btnUpdate.addActionListener(e -> setInputEnabled(true));

        // Nút Lưu: Thực hiện thêm mới hoặc cập nhật
        view.btnSave.addActionListener(e -> saveKhachHang());

        // Nút Xóa khách hàng
        view.btnDelete.addActionListener(e -> deleteKhachHang());

        // Nút Thêm mới
        view.btnAdd.addActionListener(e -> {
            clearInput();
            setInputEnabled(true);
            view.txtMaKH.setText(""); 
        });
    }

    private void loadData() {
        List<KhachHangDTO> ds = service.getAllKhachHang();
        view.hienThiKhachHang(ds);
    }

    private void setInputEnabled(boolean enabled) {
        view.txtMaKH.setEnabled(false);
        view.txtTenKH.setEnabled(enabled);
        view.txtSDT.setEnabled(enabled);
        view.txtDiaChi.setEnabled(enabled);
        view.txtEmail.setEnabled(enabled);
        if (view.cbGioiTinh != null)
            view.cbGioiTinh.setEnabled(enabled);
    }

    private void clearInput() {
        view.txtMaKH.setText("");
        view.txtTenKH.setText("");
        view.txtSDT.setText("");
        view.txtDiaChi.setText("");
        view.txtEmail.setText("");
    }

    private void search() {
        String keyword = view.txtTimKiem.getText().trim();
        List<KhachHangDTO> result = service.search(keyword);
        view.hienThiKhachHang(result);
    }

    private void displaySelectedRow(int row) {
        var model = view.tableKhachHang.getModel();
        view.txtMaKH.setText(getValue(model, row, 0));
        view.txtTenKH.setText(getValue(model, row, 1));
        view.txtSDT.setText(getValue(model, row, 2));
        view.txtDiaChi.setText(getValue(model, row, 3));
        view.txtEmail.setText(getValue(model, row, 4));

        if (view.cbGioiTinh != null) {
            view.cbGioiTinh.setSelectedItem(getValue(model, row, 5));
        }
    }

    private String getValue(javax.swing.table.TableModel model, int row, int col) {
        Object val = model.getValueAt(row, col);
        return (val == null) ? "" : val.toString();
    }

    private void saveKhachHang() {
        if (view.txtTenKH.getText().isEmpty() || view.txtSDT.getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tên và Số điện thoại không được để trống!");
            return;
        }

        Integer maKH = view.txtMaKH.getText().isEmpty() ? null : Integer.parseInt(view.txtMaKH.getText());
        String gioiTinh = view.cbGioiTinh != null ? view.cbGioiTinh.getSelectedItem().toString() : "";

        // Tạo DTO
        KhachHangDTO dto = new KhachHangDTO(
                maKH,
                view.txtTenKH.getText(),
                gioiTinh,
                view.txtDiaChi.getText(),
                view.txtSDT.getText(),
                view.txtEmail.getText()
        );

        boolean success;
        if (maKH == null) {
            success = service.addKhachHang(dto);
        } else {
            success = service.updateKhachHang(dto);
        }

        if (success) {
            JOptionPane.showMessageDialog(view, "Lưu thông tin khách hàng thành công!");
            loadData();
            setInputEnabled(false);
        } else {
            JOptionPane.showMessageDialog(view, "Thao tác thất bại!");
        }
    }

    private void deleteKhachHang() {
        int row = view.tableKhachHang.getSelectedRow();
        if (row >= 0) {
            int maKH = Integer.parseInt(view.tableKhachHang.getValueAt(row, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(view,
                    "Xóa khách hàng này sẽ ảnh hưởng đến lịch sử hóa đơn. Bạn chắc chứ?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (service.deleteKhachHang(maKH)) {
                    loadData();
                    clearInput();
                } else {
                    JOptionPane.showMessageDialog(view, "Không thể xóa khách hàng đã có hóa đơn hoặc lỗi DB!");
                }
            }
        }
    }
}