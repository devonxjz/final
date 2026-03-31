package com.example.controller;

import com.example.dto.NhaCungCapDTO;
import com.example.services.NhaCungCapService;
import com.example.view.NhaCungCapView;
import com.example.util.InputValidator;
import com.example.util.ValidationResult;
import com.example.exception.ServiceException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Controller điều khiển các tương tác giữa giao diện Nhà Cung Cấp và Cơ sở dữ liệu.
 * Đã được refactor tuân thủ Clean Architecture, giao tiếp độc quyền qua Service & DTO.
 */
public class NhaCungCapController {
    
    private final NhaCungCapService service;
    private final NhaCungCapView view;

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

        // Nút Thêm: Xóa form và mở khóa để nhập mới
        view.btnThem.addActionListener(e -> {
            clearInput();
            view.setEditableFields(true);
        });
    }

    private void clearInput() {
        view.txtMaNCC.setText("");
        view.txtTenNCC.setText("");
        view.txtSDT.setText("");
        view.txtDiaChi.setText("");
    }

    private void loadData() {
        try {
            List<NhaCungCapDTO> listDTO = service.getAllNhaCungCap();
            // Hàm hiển thị bên View cần được điều chỉnh nếu trước đây nó nhận Entity,
            // ta sẽ map lại sang dạng phù hợp hoặc gán trực tiếp lên TableModel
            var model = (javax.swing.table.DefaultTableModel) view.tableNCC.getModel();
            model.setRowCount(0);
            for (NhaCungCapDTO dto : listDTO) {
                model.addRow(new Object[]{ dto.maNCC(), dto.tenNCC(), dto.sdt(), dto.diaChi() });
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void search() {
        String keyword = view.txtTimKiem.getText().trim();
        try {
            List<NhaCungCapDTO> result = service.search(keyword);
            var model = (javax.swing.table.DefaultTableModel) view.tableNCC.getModel();
            model.setRowCount(0);
            for (NhaCungCapDTO dto : result) {
                model.addRow(new Object[]{ dto.maNCC(), dto.tenNCC(), dto.sdt(), dto.diaChi() });
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
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
        ValidationResult<String> rTen = InputValidator.normalizeName(view.txtTenNCC.getText(), "Tên nhà cung cấp");
        if (!rTen.isValid()) { JOptionPane.showMessageDialog(view, rTen.getErrorMessage()); return; }

        ValidationResult<String> rSdt = InputValidator.validatePhone(view.txtSDT.getText(), true);
        if (!rSdt.isValid()) { JOptionPane.showMessageDialog(view, rSdt.getErrorMessage()); return; }

        ValidationResult<String> rDiaChi = InputValidator.normalizeAddress(view.txtDiaChi.getText(), "Địa chỉ");
        if (!rDiaChi.isValid()) { JOptionPane.showMessageDialog(view, rDiaChi.getErrorMessage()); return; }

        String tenNCC = rTen.getValue();
        String sdt = rSdt.getValue();
        String diaChi = rDiaChi.getValue();

        try {
            boolean success = false;
            // Nếu txtMaNCC trống -> Insert
            if (view.txtMaNCC.getText().isEmpty()) {
                NhaCungCapDTO newDto = new NhaCungCapDTO(null, tenNCC, diaChi, sdt);
                success = service.addNhaCungCap(newDto);
            } else {
                // Update
                int maNCC = Integer.parseInt(view.txtMaNCC.getText());
                NhaCungCapDTO updateDto = new NhaCungCapDTO(maNCC, tenNCC, diaChi, sdt);
                success = service.updateNhaCungCap(updateDto);
            }

            if (success) {
                JOptionPane.showMessageDialog(view, "Lưu thông tin thành công!");
                loadData();
                view.setEditableFields(false);
            } else {
                JOptionPane.showMessageDialog(view, "Thao tác luồng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi từ CSDL: " + ex.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteNhaCungCap() {
        int row = view.tableNCC.getSelectedRow();
        if (row >= 0) {
            String maNCCStr = view.tableNCC.getValueAt(row, 0).toString();
            int maNCC = Integer.parseInt(maNCCStr);
            
            int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa nhà cung cấp này?");
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (service.deleteNhaCungCap(maNCC)) {
                        JOptionPane.showMessageDialog(view, "Xóa thành công!");
                        loadData();
                        clearInput();
                    }
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(view, "Lỗi hệ thống khi xóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một nhà cung cấp trong bảng để xóa!");
        }
    }
}
