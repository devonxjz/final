package com.example.controller;

import com.example.dto.NhaCungCapDTO;
import com.example.services.NhaCungCapService;
import com.example.view.NhaCungCapView;
import com.example.util.InputValidator;
import com.example.util.ValidationResult;
import com.example.util.SwingWorkerUtils;
import com.example.exception.ServiceException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Controller Nhà Cung Cấp — Async optimized.
 */
public class NhaCungCapController {
    
    private final NhaCungCapService service;
    private final NhaCungCapView view;

    private static final String[] COLUMN_NAMES = {"Mã NCC", "Tên NCC", "SĐT", "Địa chỉ"};

    public NhaCungCapController(NhaCungCapService service, NhaCungCapView view) {
        this.service = service;
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
            @Override public void insertUpdate(DocumentEvent e) { search(); }
            @Override public void removeUpdate(DocumentEvent e) { search(); }
            @Override public void changedUpdate(DocumentEvent e) { search(); }
        });

        view.btnUpdate.addActionListener(e -> view.setEditableFields(true));
        view.btnSave.addActionListener(e -> saveNhaCungCap());
        view.btnDelete.addActionListener(e -> deleteNhaCungCap());

        view.btnThem.addActionListener(e -> {
            clearInput();
            view.setEditableFields(true);
        });
    }

    private void clearInput() {
        view.txtMaNCC.setText(""); view.txtTenNCC.setText("");
        view.txtSDT.setText(""); view.txtDiaChi.setText("");
    }

    // ====== ASYNC: Load toàn bộ NCC ======
    private void loadData() {
        SwingWorkerUtils.runAsync(
            view.btnReload,
            () -> service.getAllNhaCungCap(),
            listDTO -> {
                DefaultTableModel newModel = new DefaultTableModel(COLUMN_NAMES, 0) {
                    @Override public boolean isCellEditable(int r, int c) { return false; }
                };
                for (NhaCungCapDTO dto : listDTO) {
                    newModel.addRow(new Object[]{ dto.maNCC(), dto.tenNCC(), dto.sdt(), dto.diaChi() });
                }
                view.tableNCC.setModel(newModel);
            },
            ex -> JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
        );
    }

    // ====== ASYNC: Tìm kiếm ======
    private void search() {
        String keyword = view.txtTimKiem.getText().trim();
        SwingWorkerUtils.runAsync(
            null,
            () -> service.search(keyword),
            result -> {
                DefaultTableModel newModel = new DefaultTableModel(COLUMN_NAMES, 0) {
                    @Override public boolean isCellEditable(int r, int c) { return false; }
                };
                for (NhaCungCapDTO dto : result) {
                    newModel.addRow(new Object[]{ dto.maNCC(), dto.tenNCC(), dto.sdt(), dto.diaChi() });
                }
                view.tableNCC.setModel(newModel);
            },
            ex -> JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
        );
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

    // ====== ASYNC: Lưu NCC ======
    private void saveNhaCungCap() {
        ValidationResult<String> rTen = InputValidator.normalizeName(view.txtTenNCC.getText(), "Tên nhà cung cấp");
        if (!rTen.isValid()) { JOptionPane.showMessageDialog(view, rTen.getErrorMessage()); return; }

        ValidationResult<String> rSdt = InputValidator.validatePhone(view.txtSDT.getText(), true);
        if (!rSdt.isValid()) { JOptionPane.showMessageDialog(view, rSdt.getErrorMessage()); return; }

        ValidationResult<String> rDiaChi = InputValidator.normalizeAddress(view.txtDiaChi.getText(), "Địa chỉ");
        if (!rDiaChi.isValid()) { JOptionPane.showMessageDialog(view, rDiaChi.getErrorMessage()); return; }

        SwingWorkerUtils.runAsyncVoid(
            view.btnSave,
            () -> {
                if (view.txtMaNCC.getText().isEmpty()) {
                    NhaCungCapDTO newDto = new NhaCungCapDTO(null, rTen.getValue(), rDiaChi.getValue(), rSdt.getValue());
                    service.addNhaCungCap(newDto);
                } else {
                    int maNCC = Integer.parseInt(view.txtMaNCC.getText());
                    NhaCungCapDTO updateDto = new NhaCungCapDTO(maNCC, rTen.getValue(), rDiaChi.getValue(), rSdt.getValue());
                    service.updateNhaCungCap(updateDto);
                }
            },
            () -> {
                JOptionPane.showMessageDialog(view, "Lưu thông tin thành công!");
                loadData();
                view.setEditableFields(false);
            },
            ex -> JOptionPane.showMessageDialog(view, "Lỗi từ CSDL: " + ex.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE)
        );
    }

    // ====== ASYNC: Xóa NCC ======
    private void deleteNhaCungCap() {
        int row = view.tableNCC.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một nhà cung cấp trong bảng để xóa!");
            return;
        }

        int maNCC = Integer.parseInt(view.tableNCC.getValueAt(row, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa nhà cung cấp này?");
        if (confirm == JOptionPane.YES_OPTION) {
            SwingWorkerUtils.runAsyncVoid(
                view.btnDelete,
                () -> service.deleteNhaCungCap(maNCC),
                () -> {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    loadData();
                    clearInput();
                },
                ex -> JOptionPane.showMessageDialog(view, "Lỗi hệ thống khi xóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
            );
        }
    }
}
