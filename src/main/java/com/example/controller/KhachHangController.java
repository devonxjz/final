package org.example.controller;

import com.example.model.KhachHang;
import com.example.dao.KhachHangDAO;
import com.example.view.KhachHangView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Controller quản lý thông tin khách hàng
 * Tương ứng với logic trong tài liệu đồ án HUTECH
 */
public class KhachHangController {
    private final KhachHangDAO dao;
    private final KhachHangView view;

    public KhachHangController(KhachHangDAO dao, KhachHangView view) {
        this.dao = dao;
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

        // Nút Sửa: Mở khóa để chỉnh sửa thông tin khách hàng hiện tại
        view.btnUpdate.addActionListener(e -> setInputEnabled(true));

        // Nút Lưu: Thực hiện thêm mới hoặc cập nhật
        view.btnSave.addActionListener(e -> saveKhachHang());

        // Nút Xóa khách hàng
        view.btnDelete.addActionListener(e -> deleteKhachHang());

        // Nút Thêm mới: Xóa trắng form để nhập liệu mới
        view.btnAdd.addActionListener(e -> {
            clearInput();
            setInputEnabled(true);
            view.txtMaKH.setText(""); // Để trống để DAO tự tạo ID nếu là Auto Increment
        });
    }

    private void loadData() {
        List<KhachHang> ds = dao.getAllKhachHang();
        view.hienThiKhachHang(ds);
    }

    private void setInputEnabled(boolean enabled) {
        view.txtMaKH.setEnabled(false); // Không cho sửa mã khách hàng
        view.txtTenKH.setEnabled(enabled);
        view.txtSDT.setEnabled(enabled);
        view.txtDiaChi.setEnabled(enabled);
        view.txtEmail.setEnabled(enabled);
        if (view.cbGioiTinh != null) view.cbGioiTinh.setEnabled(enabled);
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
        List<KhachHang> result = dao.timKiem(keyword);
        view.hienThiKhachHang(result);
    }

    private void displaySelectedRow(int row) {
        var model = view.tableKhachHang.getModel();
        view.txtMaKH.setText(getValue(model, row, 0));
        view.txtTenKH.setText(getValue(model, row, 1));
        view.txtSDT.setText(getValue(model, row, 2));
        view.txtDiaChi.setText(getValue(model, row, 3));
        view.txtEmail.setText(getValue(model, row, 4));

        // Xử lý ComboBox giới tính nếu có
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

        KhachHang kh = new KhachHang();
        // Kiểm tra nếu MaKH có giá trị thì là Update, ngược lại là Insert
        if (!view.txtMaKH.getText().isEmpty()) {
            kh.setMaKH(Integer.parseInt(view.txtMaKH.getText()));
        }

        kh.setTenKH(view.txtTenKH.getText());
        kh.setSdt(view.txtSDT.getText());
        kh.setDiaChi(view.txtDiaChi.getText());
        kh.setEmail(view.txtEmail.getText());
        if (view.cbGioiTinh != null) kh.setGioiTinh(view.cbGioiTinh.getSelectedItem().toString());

        if (dao.insertOrUpdate(kh)) {
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
            int confirm = JOptionPane.showConfirmDialog(view, "Xóa khách hàng này sẽ ảnh hưởng đến lịch sử hóa đơn. Bạn chắc chứ?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.xoaKhachHang(maKH)) {
                    loadData();
                    clearInput();
                } else {
                    JOptionPane.showMessageDialog(view, "Không thể xóa khách hàng đã có hóa đơn!");
                }
            }
        }
    }
}