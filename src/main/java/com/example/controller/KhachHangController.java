package com.example.controller;

import com.example.dto.KhachHangDTO;
import com.example.services.KhachHangService;
import com.example.view.KhachHangView;
import com.example.util.InputValidator;
import com.example.util.ValidationResult;
import com.example.util.SwingWorkerUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Controller quản lý thông tin khách hàng.
 * Đã tối ưu: Tất cả DB call chạy async qua SwingWorkerUtils.
 */
public class KhachHangController {
    private final KhachHangService service;
    private final KhachHangView view;

    private static final String[] COLUMN_NAMES = {"Mã KH", "Tên KH", "SĐT", "Địa chỉ", "Email", "Giới tính"};

    public KhachHangController(KhachHangService service, KhachHangView view) {
        this.service = service;
        this.view = view;
        initController();
        loadData();
        setInputEnabled(false);
    }

    private void initController() {
        view.btnReload.addActionListener(e -> loadData());

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

        view.txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { search(); }
            @Override public void removeUpdate(DocumentEvent e) { search(); }
            @Override public void changedUpdate(DocumentEvent e) { search(); }
        });

        view.btnUpdate.addActionListener(e -> setInputEnabled(true));
        view.btnSave.addActionListener(e -> saveKhachHang());
        view.btnDelete.addActionListener(e -> deleteKhachHang());

        view.btnAdd.addActionListener(e -> {
            clearInput();
            setInputEnabled(true);
            view.txtMaKH.setText("");
        });
    }

    // ====== ASYNC: Load toàn bộ khách hàng ======
    private void loadData() {
        SwingWorkerUtils.runAsync(
            view.btnReload,
            () -> service.getAllKhachHang(),
            ds -> view.hienThiKhachHang(ds),
            ex -> JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
        );
    }

    private void setInputEnabled(boolean enabled) {
        view.txtMaKH.setEnabled(false);
        view.txtTenKH.setEnabled(enabled);
        view.txtSDT.setEnabled(enabled);
        view.txtDiaChi.setEnabled(enabled);
        view.txtEmail.setEnabled(enabled);
        if (view.cbGioiTinh != null) view.cbGioiTinh.setEnabled(enabled);
    }

    private void clearInput() {
        view.txtMaKH.setText(""); view.txtTenKH.setText("");
        view.txtSDT.setText(""); view.txtDiaChi.setText("");
        view.txtEmail.setText("");
    }

    // ====== ASYNC: Tìm kiếm ======
    private void search() {
        String keyword = view.txtTimKiem.getText().trim();
        SwingWorkerUtils.runAsync(
            null,
            () -> service.search(keyword),
            result -> view.hienThiKhachHang(result),
            ex -> JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
        );
    }

    private void displaySelectedRow(int row) {
        var model = view.tableKhachHang.getModel();
        view.txtMaKH.setText(getValue(model, row, 0));
        view.txtTenKH.setText(getValue(model, row, 1));
        view.txtSDT.setText(getValue(model, row, 2));
        view.txtDiaChi.setText(getValue(model, row, 3));
        view.txtEmail.setText(getValue(model, row, 4));
        if (view.cbGioiTinh != null) view.cbGioiTinh.setSelectedItem(getValue(model, row, 5));
    }

    private String getValue(javax.swing.table.TableModel model, int row, int col) {
        Object val = model.getValueAt(row, col);
        return (val == null) ? "" : val.toString();
    }

    // ====== ASYNC: Lưu khách hàng ======
    private void saveKhachHang() {
        ValidationResult<String> rTen = InputValidator.normalizeName(view.txtTenKH.getText(), "Tên khách hàng");
        if (!rTen.isValid()) { JOptionPane.showMessageDialog(view, rTen.getErrorMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); return; }

        ValidationResult<String> rSdt = InputValidator.validatePhone(view.txtSDT.getText(), true);
        if (!rSdt.isValid()) { JOptionPane.showMessageDialog(view, rSdt.getErrorMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); return; }

        ValidationResult<String> rDiaChi = InputValidator.normalizeAddress(view.txtDiaChi.getText(), "Địa chỉ");
        if (!rDiaChi.isValid()) { JOptionPane.showMessageDialog(view, rDiaChi.getErrorMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); return; }

        ValidationResult<String> rEmail = InputValidator.validateEmail(view.txtEmail.getText(), false);
        if (!rEmail.isValid()) { JOptionPane.showMessageDialog(view, rEmail.getErrorMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); return; }

        Integer maKH = view.txtMaKH.getText().isEmpty() ? null : Integer.parseInt(view.txtMaKH.getText());
        String gioiTinh = view.cbGioiTinh != null ? view.cbGioiTinh.getSelectedItem().toString() : "";

        KhachHangDTO dto = new KhachHangDTO(maKH, rTen.getValue(), gioiTinh, rDiaChi.getValue(), rSdt.getValue(), rEmail.getValue());

        SwingWorkerUtils.runAsyncVoid(
            view.btnSave,
            () -> {
                if (maKH == null) { service.addKhachHang(dto); }
                else { service.updateKhachHang(dto); }
            },
            () -> {
                JOptionPane.showMessageDialog(view, "Lưu thông tin khách hàng thành công!");
                loadData();
                setInputEnabled(false);
            },
            ex -> JOptionPane.showMessageDialog(view, "Lỗi từ CSDL: " + ex.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE)
        );
    }

    // ====== ASYNC: Xóa khách hàng ======
    private void deleteKhachHang() {
        int row = view.tableKhachHang.getSelectedRow();
        if (row < 0) return;

        int maKH = Integer.parseInt(view.tableKhachHang.getValueAt(row, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(view, "Xóa khách hàng này sẽ ảnh hưởng đến lịch sử hóa đơn. Bạn chắc chứ?");
        if (confirm == JOptionPane.YES_OPTION) {
            SwingWorkerUtils.runAsyncVoid(
                view.btnDelete,
                () -> service.deleteKhachHang(maKH),
                () -> { loadData(); clearInput(); },
                ex -> JOptionPane.showMessageDialog(view, "Lỗi hệ thống khi xóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
            );
        }
    }
}