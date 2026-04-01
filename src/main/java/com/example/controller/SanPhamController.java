package com.example.controller;

import com.example.dto.SanPhamDTO;
import com.example.services.SanPhamService;
import com.example.view.SanPhamView;
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
 * Controller điều khiển các tương tác giữa giao diện Sản phẩm và CSDL.
 * Đã tối ưu: Tất cả DB call chạy async qua SwingWorkerUtils (không block EDT).
 */
public class SanPhamController {
    
    private final SanPhamService service;
    private final SanPhamView view;

    // Column names cho bảng sản phẩm
    private static final String[] COLUMN_NAMES = {
        "Mã SP", "Loại máy", "Tên SP", "CPU", "GPU", "RAM", "Ổ cứng",
        "KT Màn hình", "Độ phân giải", "Cân nặng", "SL Kho", "Giá bán", "Giá nhập", "Bảo hành"
    };

    public SanPhamController(SanPhamService service, SanPhamView view) {
        this.service = service;
        this.view = view;
        initController();
        loadData();
        setInputEnabled(false);
    }

    private void initController() {
        view.btnReload.addActionListener(e -> loadData());

        view.tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.tableSanPham.getSelectedRow();
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
        view.btnSave.addActionListener(e -> saveSanPham());
        view.btnDelete.addActionListener(e -> deleteSanPham());

        view.btnThem.addActionListener(e -> {
            clearInput();
            setInputEnabled(true);
        });
    }

    private void clearInput() {
        view.txtMaSP.setText(""); view.txtLoaiMay.setText(""); view.txtTenSP.setText("");
        view.txtCPU.setText(""); view.txtGPU.setText(""); view.txtRAM.setText("");
        view.txtOCung.setText(""); view.txtKTManHinh.setText(""); view.txtDPGManHinh.setText("");
        view.txtCanNang.setText(""); view.txtSLTrongKho.setText(""); view.txtGiaBan.setText("");
        view.txtGiaNhap.setText(""); view.txtThoiGianBaoHanh.setText("");
    }

    // ====== ASYNC: Load toàn bộ sản phẩm ======
    private void loadData() {
        SwingWorkerUtils.runAsync(
            view.btnReload,
            () -> service.getAllSanPham(),
            danhSach -> {
                DefaultTableModel newModel = new DefaultTableModel(COLUMN_NAMES, 0) {
                    @Override public boolean isCellEditable(int r, int c) { return false; }
                };
                for (SanPhamDTO dto : danhSach) {
                    newModel.addRow(new Object[]{
                        dto.maSP(), dto.loaiMay(), dto.tenSP(), dto.CPU(), dto.GPU(),
                        dto.RAM(), dto.oCung(), dto.kichThuocMH(), dto.doPhanGiaiMH(),
                        dto.canNang(), dto.soLuongTrongKho(), dto.giaBan(), dto.giaNhap(), dto.thoiGianBaoHanh()
                    });
                }
                view.tableSanPham.setModel(newModel); // Chỉ vẽ lại UI 1 lần
            },
            ex -> JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
        );
    }

    private void setInputEnabled(boolean enabled) {
        view.txtMaSP.setEnabled(false);
        view.txtLoaiMay.setEnabled(enabled); view.txtTenSP.setEnabled(enabled);
        view.txtCPU.setEnabled(enabled); view.txtGPU.setEnabled(enabled);
        view.txtRAM.setEnabled(enabled); view.txtOCung.setEnabled(enabled);
        view.txtKTManHinh.setEnabled(enabled); view.txtDPGManHinh.setEnabled(enabled);
        view.txtCanNang.setEnabled(enabled); view.txtSLTrongKho.setEnabled(enabled);
        view.txtGiaBan.setEnabled(enabled); view.txtGiaNhap.setEnabled(enabled);
        view.txtThoiGianBaoHanh.setEnabled(enabled);
    }

    // ====== ASYNC: Tìm kiếm sản phẩm ======
    private void search() {
        String keyword = view.txtTimKiem.getText().trim();
        SwingWorkerUtils.runAsync(
            null, // Không lock component nào khi search real-time
            () -> service.search(keyword),
            result -> {
                DefaultTableModel newModel = new DefaultTableModel(COLUMN_NAMES, 0) {
                    @Override public boolean isCellEditable(int r, int c) { return false; }
                };
                for (SanPhamDTO dto : result) {
                    newModel.addRow(new Object[]{
                        dto.maSP(), dto.loaiMay(), dto.tenSP(), dto.CPU(), dto.GPU(),
                        dto.RAM(), dto.oCung(), dto.kichThuocMH(), dto.doPhanGiaiMH(),
                        dto.canNang(), dto.soLuongTrongKho(), dto.giaBan(), dto.giaNhap(), dto.thoiGianBaoHanh()
                    });
                }
                view.tableSanPham.setModel(newModel);
            },
            ex -> JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
        );
    }

    private void displaySelectedRow(int row) {
        var model = view.tableSanPham.getModel();
        view.txtMaSP.setText(getValue(model, row, 0));
        view.txtLoaiMay.setText(getValue(model, row, 1));
        view.txtTenSP.setText(getValue(model, row, 2));
        view.txtCPU.setText(getValue(model, row, 3));
        view.txtGPU.setText(getValue(model, row, 4));
        view.txtRAM.setText(getValue(model, row, 5));
        view.txtOCung.setText(getValue(model, row, 6));
        view.txtKTManHinh.setText(getValue(model, row, 7));
        view.txtDPGManHinh.setText(getValue(model, row, 8));
        view.txtCanNang.setText(getValue(model, row, 9));
        view.txtSLTrongKho.setText(getValue(model, row, 10));
        view.txtGiaBan.setText(getValue(model, row, 11).replace(",", ""));
        view.txtGiaNhap.setText(getValue(model, row, 12).replace(",", ""));
        view.txtThoiGianBaoHanh.setText(getValue(model, row, 13));
    }

    private String getValue(javax.swing.table.TableModel model, int row, int col) {
        Object val = model.getValueAt(row, col);
        return (val == null) ? "" : val.toString();
    }

    // ====== ASYNC: Lưu sản phẩm (thêm/sửa) ======
    private void saveSanPham() {
        Integer maSP = view.txtMaSP.getText().isEmpty() ? null : Integer.parseInt(view.txtMaSP.getText());

        SanPhamDTO dto = validateAndCreateDTO(view.txtLoaiMay, view.txtTenSP, view.txtCPU, view.txtGPU,
                view.txtRAM, view.txtOCung, view.txtKTManHinh, view.txtDPGManHinh, view.txtCanNang,
                view.txtSLTrongKho, view.txtGiaBan, view.txtGiaNhap, view.txtThoiGianBaoHanh, maSP);

        if (dto == null) return; // Validation failed

        SwingWorkerUtils.runAsyncVoid(
            view.btnSave,
            () -> {
                if (maSP == null) {
                    service.addSanPham(dto);
                } else {
                    service.updateSanPham(dto);
                }
            },
            () -> {
                JOptionPane.showMessageDialog(view, "Lưu thông tin sản phẩm thành công!");
                loadData();
                setInputEnabled(false);
            },
            ex -> JOptionPane.showMessageDialog(view, "Lỗi từ CSDL: " + ex.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE)
        );
    }

    // ====== ASYNC: Xóa sản phẩm ======
    private void deleteSanPham() {
        int selectedRow = view.tableSanPham.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một sản phẩm trong bảng để xóa!");
            return;
        }

        int maSP = Integer.parseInt(view.tableSanPham.getModel().getValueAt(selectedRow, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(view,
                "Bạn có chắc muốn xóa sản phẩm mã '" + maSP + "' không?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            SwingWorkerUtils.runAsyncVoid(
                view.btnDelete,
                () -> service.deleteSanPham(maSP),
                () -> {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    loadData();
                },
                ex -> JOptionPane.showMessageDialog(view, "Lỗi hệ thống khi xóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
            );
        }
    }

    private SanPhamDTO validateAndCreateDTO(
            JTextField txtLoaiMay, JTextField txtTenSP, JTextField txtCPU, JTextField txtGPU,
            JTextField txtRAM, JTextField txtOCung, JTextField txtKTManHinh, JTextField txtDPGManHinh,
            JTextField txtCanNang, JTextField txtSLTrongKho, JTextField txtGiaBan, JTextField txtGiaNhap,
            JTextField txtThoiGianBaoHanh, Integer maSP) {

        ValidationResult<String> rLoaiMay = InputValidator.validateRequiredString(txtLoaiMay.getText(), "Loại máy", 255);
        if (!rLoaiMay.isValid()) { JOptionPane.showMessageDialog(view, rLoaiMay.getErrorMessage(), "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE); txtLoaiMay.requestFocus(); return null; }
        
        ValidationResult<String> rTenSP = InputValidator.validateRequiredString(txtTenSP.getText(), "Tên sản phẩm", 255);
        if (!rTenSP.isValid()) { JOptionPane.showMessageDialog(view, rTenSP.getErrorMessage(), "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE); txtTenSP.requestFocus(); return null; }
        
        ValidationResult<Integer> rRAM = InputValidator.parseIntSafe(txtRAM.getText(), "RAM");
        if (!rRAM.isValid()) { JOptionPane.showMessageDialog(view, "RAM không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE); txtRAM.requestFocus(); return null; }
        
        ValidationResult<Float> rKT = InputValidator.parseFloatSafe(txtKTManHinh.getText(), "Kích thước màn hình");
        if (!rKT.isValid()) { JOptionPane.showMessageDialog(view, "Kích thước màn hình không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE); txtKTManHinh.requestFocus(); return null; }
        
        ValidationResult<Float> rCanNang = InputValidator.parseFloatSafe(txtCanNang.getText(), "Cân nặng");
        if (!rCanNang.isValid()) { JOptionPane.showMessageDialog(view, "Cân nặng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE); txtCanNang.requestFocus(); return null; }
        
        ValidationResult<Integer> rSL = InputValidator.parseIntSafe(txtSLTrongKho.getText(), "Số lượng tồn kho");
        if (!rSL.isValid()) { JOptionPane.showMessageDialog(view, rSL.getErrorMessage()); txtSLTrongKho.requestFocus(); return null; }
        
        ValidationResult<Double> rGiaBan = InputValidator.parseCurrency(txtGiaBan.getText(), "Giá bán");
        if (!rGiaBan.isValid()) { JOptionPane.showMessageDialog(view, rGiaBan.getErrorMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); txtGiaBan.requestFocus(); return null; }
        
        ValidationResult<Double> rGiaNhap = InputValidator.parseCurrency(txtGiaNhap.getText(), "Giá nhập");
        if (!rGiaNhap.isValid()) { JOptionPane.showMessageDialog(view, rGiaNhap.getErrorMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); txtGiaNhap.requestFocus(); return null; }
        
        ValidationResult<Integer> rBH = InputValidator.parseIntSafe(txtThoiGianBaoHanh.getText(), "Thời gian bảo hành");
        if (!rBH.isValid()) { JOptionPane.showMessageDialog(view, "Thời gian bảo hành không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE); txtThoiGianBaoHanh.requestFocus(); return null; }

        return new SanPhamDTO(
            maSP, rLoaiMay.getValue(), rTenSP.getValue(), txtCPU.getText(), txtGPU.getText(),
            rRAM.getValue(), txtOCung.getText(), rKT.getValue(), txtDPGManHinh.getText(),
            rCanNang.getValue(), rSL.getValue(), rGiaBan.getValue(), rGiaNhap.getValue(),
            rBH.getValue(), null, ""
        );
    }
}