package com.example.controller;

import com.example.entity.SanPham;
import com.example.dao.SanPhamDAO;
import com.example.view.SanPhamView;
import com.example.view.ThemSanPhamView;
import com.example.util.InputValidator;
import com.example.util.ValidationResult;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Controller điều khiển các tương tác giữa giao diện Sản phẩm và Cơ sở dữ liệu
 * Author: Nông Hoàng Anh
 */
public class SanPhamController {
    private final SanPhamDAO dao;
    private final SanPhamView view;

    public SanPhamController(SanPhamDAO dao, SanPhamView view) {
        this.dao = dao;
        this.view = view;
        initController();
        loadData();
        setInputEnabled(false); // Mặc định khóa các ô nhập liệu khi mới mở
    }

    private void initController() {
        // Sự kiện nút Làm mới
        view.btnReload.addActionListener(e -> loadData());

        // Sự kiện click vào bảng để hiển thị chi tiết sản phẩm
        view.tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.tableSanPham.getSelectedRow();
                if (row >= 0) {
                    displaySelectedRow(row);
                    setInputEnabled(false); // Khóa lại để tránh sửa nhầm khi chưa bấm nút Update
                }
            }
        });

        // Sự kiện tìm kiếm thời gian thực (Real-time search)
        view.txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { search(); }
            @Override
            public void removeUpdate(DocumentEvent e) { search(); }
            @Override
            public void changedUpdate(DocumentEvent e) { search(); }
        });

        // Nút Sửa: Mở khóa các ô nhập liệu
        view.btnUpdate.addActionListener(e -> setInputEnabled(true));

        // Nút Lưu: Thực hiện cập nhật dữ liệu
        view.btnSave.addActionListener(e -> saveSanPham());

        // Nút Xóa sản phẩm
        view.btnDelete.addActionListener(e -> deleteSanPham());

        // Nút Thêm: Mở cửa sổ thêm mới sản phẩm
        view.btnThem.addActionListener(e -> {
            ThemSanPhamView themView = new ThemSanPhamView();
            themView.setVisible(true);
            // Sau khi đóng cửa sổ thêm, tải lại dữ liệu
            themView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    loadData();
                }
            });
        });
    }

    private void loadData() {
        List<SanPham> danhSach = dao.getAllSanPham();
        view.hienThiSanPham(danhSach);
    }

    private void setInputEnabled(boolean enabled) {
        view.txtMaSP.setEnabled(false); // Mã SP luôn không được sửa (Auto Increment)
        view.txtLoaiMay.setEnabled(enabled);
        view.txtTenSP.setEnabled(enabled);
        view.txtCPU.setEnabled(enabled);
        view.txtGPU.setEnabled(enabled);
        view.txtRAM.setEnabled(enabled);
        view.txtOCung.setEnabled(enabled);
        view.txtKTManHinh.setEnabled(enabled);
        view.txtDPGManHinh.setEnabled(enabled);
        view.txtCanNang.setEnabled(enabled);
        view.txtSLTrongKho.setEnabled(enabled);
        view.txtGiaBan.setEnabled(enabled);
        view.txtGiaNhap.setEnabled(enabled);
        view.txtThoiGianBaoHanh.setEnabled(enabled);
    }

    private void search() {
        String keyword = view.txtTimKiem.getText().trim();
        List<SanPham> result = dao.timKiem(keyword);
        view.hienThiSanPham(result);
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

    // Hàm bổ trợ lấy giá trị từ bảng tránh lỗi null
    private String getValue(javax.swing.table.TableModel model, int row, int col) {
        Object val = model.getValueAt(row, col);
        return (val == null) ? "" : val.toString();
    }

    private void saveSanPham() {
        if (view.txtMaSP.getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một sản phẩm để cập nhật!");
            return;
        }

        ValidationResult<String> rLoaiMay = InputValidator.validateRequiredString(view.txtLoaiMay.getText(), "Loại máy", 255);
        if (!rLoaiMay.isValid()) { JOptionPane.showMessageDialog(view, rLoaiMay.getErrorMessage()); return; }
        
        ValidationResult<String> rTenSP = InputValidator.validateRequiredString(view.txtTenSP.getText(), "Tên sản phẩm", 255);
        if (!rTenSP.isValid()) { JOptionPane.showMessageDialog(view, rTenSP.getErrorMessage()); return; }
        
        ValidationResult<Integer> rRAM = InputValidator.parseIntSafe(view.txtRAM.getText(), "RAM");
        if (!rRAM.isValid()) { JOptionPane.showMessageDialog(view, rRAM.getErrorMessage()); return; }
        
        ValidationResult<Float> rKT = InputValidator.parseFloatSafe(view.txtKTManHinh.getText(), "Kích thước màn hình");
        if (!rKT.isValid()) { JOptionPane.showMessageDialog(view, rKT.getErrorMessage()); return; }
        
        ValidationResult<Float> rCanNang = InputValidator.parseFloatSafe(view.txtCanNang.getText(), "Cân nặng");
        if (!rCanNang.isValid()) { JOptionPane.showMessageDialog(view, rCanNang.getErrorMessage()); return; }
        
        ValidationResult<Integer> rSL = InputValidator.parseIntSafe(view.txtSLTrongKho.getText(), "Số lượng tồn kho");
        if (!rSL.isValid()) { JOptionPane.showMessageDialog(view, rSL.getErrorMessage()); return; }
        
        ValidationResult<Double> rGiaBan = InputValidator.parseCurrency(view.txtGiaBan.getText(), "Giá bán");
        if (!rGiaBan.isValid()) { JOptionPane.showMessageDialog(view, rGiaBan.getErrorMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); return; }
        
        ValidationResult<Double> rGiaNhap = InputValidator.parseCurrency(view.txtGiaNhap.getText(), "Giá nhập");
        if (!rGiaNhap.isValid()) { JOptionPane.showMessageDialog(view, rGiaNhap.getErrorMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); return; }
        
        ValidationResult<Integer> rBH = InputValidator.parseIntSafe(view.txtThoiGianBaoHanh.getText(), "Thời gian bảo hành");
        if (!rBH.isValid()) { JOptionPane.showMessageDialog(view, rBH.getErrorMessage()); return; }

        SanPham sp = new SanPham();
        sp.setMaSP(Integer.parseInt(view.txtMaSP.getText()));
        sp.setLoaiMay(rLoaiMay.getValue());
        sp.setTenSP(rTenSP.getValue());
        sp.setCPU(view.txtCPU.getText());
        sp.setGPU(view.txtGPU.getText());
        sp.setRAM(rRAM.getValue());
        sp.setOCung(view.txtOCung.getText());
        sp.setKichThuocMH(rKT.getValue());
        sp.setDoPhanGiaiMH(view.txtDPGManHinh.getText());
        sp.setCanNang(rCanNang.getValue());
        sp.setSoLuongTrongKho(rSL.getValue());
        sp.setGiaBan(rGiaBan.getValue());
        sp.setGiaNhap(rGiaNhap.getValue());
        sp.setThoiGianBaoHanh(rBH.getValue());

        if (dao.insertOrUpdate(sp)) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadData();
            setInputEnabled(false);
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
        }
    }

    private void deleteSanPham() {
        int selectedRow = view.tableSanPham.getSelectedRow();
        if (selectedRow >= 0) {
            int maSP = Integer.parseInt(view.tableSanPham.getModel().getValueAt(selectedRow, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(view,
                    "Bạn có chắc muốn xóa sản phẩm mã '" + maSP + "' không?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.xoaSanPham(maSP)) {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(view, "Không thể xóa sản phẩm này (có thể đã có trong hóa đơn)!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một sản phẩm trong bảng để xóa!");
        }
    }
}