package com.example.controller;

import com.example.dao.ThanhToanDAO;
import com.example.dao.ThanhToanDAO;
import com.example.view.ThemThanhToanView;
import com.example.util.InputValidator;
import com.example.util.ValidationResult;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ThemThanhToanController {
    private final ThanhToanDAO dao;
    private final ThemThanhToanView view;
    private List<Map<String, Object>> hoaDonDangGopList;

    public ThemThanhToanController(ThanhToanDAO dao, ThemThanhToanView view) {
        this.dao = dao;
        this.view = view;
        initController();
        loadData();
    }

    private void initController() {
        // Tải hóa đơn đang trả góp lên bảng
        view.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.table.getSelectedRow();
                if (row >= 0) {
                    fillThongTinToForm(row);
                }
            }
        });

        // Tìm kiếm khách hàng nợ
        view.txtNhapTenKH.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { search(); }
            @Override
            public void removeUpdate(DocumentEvent e) { search(); }
            @Override
            public void changedUpdate(DocumentEvent e) { search(); }
        });

        // Nút Xuất hóa đơn (Lưu thanh toán)
        view.btnXuatHoaDon.addActionListener(e -> thanhToan());
    }

    private void loadData() {
        hoaDonDangGopList = dao.getAllHoaDonDangGopAsMap();
        view.loadData(hoaDonDangGopList);
    }

    private void fillThongTinToForm(int row) {
        var model = view.table.getModel();
        view.txtMaHDBH.setText(model.getValueAt(row, 0).toString());
        view.txtMaKH.setText(model.getValueAt(row, 1).toString());
        view.txtTenKH.setText(model.getValueAt(row, 2).toString());
        view.txtTienThanhToan.setText(model.getValueAt(row, 4).toString()); // Tiền góp tháng mặc định
    }

    private void search() {
        String keyword = view.txtNhapTenKH.getText().trim().toLowerCase();
        if (hoaDonDangGopList == null) return;
        List<Map<String, Object>> result = hoaDonDangGopList.stream()
                .filter(hd -> {
                    String tenKH = hd.get("tenKH") != null ? hd.get("tenKH").toString().toLowerCase() : "";
                    return tenKH.contains(keyword);
                })
                .collect(Collectors.toList());
        view.loadData(result);
    }

    private void thanhToan() {
        if (view.txtMaHDBH.getText().isEmpty() || view.txtMaKH.getText().isEmpty() || view.txtTienThanhToan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn hóa đơn và nhập tiền thanh toán!");
            return;
        }

        ValidationResult<Integer> rMaHDBH = InputValidator.parseIntSafe(view.txtMaHDBH.getText(), "Mã hóa đơn");
        if (!rMaHDBH.isValid()) { JOptionPane.showMessageDialog(view, rMaHDBH.getErrorMessage()); return; }

        ValidationResult<Integer> rMaKH = InputValidator.parseIntSafe(view.txtMaKH.getText(), "Mã khách hàng");
        if (!rMaKH.isValid()) { JOptionPane.showMessageDialog(view, rMaKH.getErrorMessage()); return; }

        ValidationResult<Double> rTienTT = InputValidator.parseCurrency(view.txtTienThanhToan.getText(), "Tiền thanh toán");
        if (!rTienTT.isValid()) { JOptionPane.showMessageDialog(view, rTienTT.getErrorMessage()); return; }

        int maHDBH = rMaHDBH.getValue();
        int maKH = rMaKH.getValue();
        double tienTT = rTienTT.getValue();
        String hinhThucTT = view.cmbHinhThucTT.getSelectedItem().toString();
        Date date = view.dateChooserNgayThanhToan.getDate();

        if (date == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn ngày thanh toán!");
            return;
        }

        if (dao.themThanhToan(maHDBH, maKH, new java.sql.Date(date.getTime()), tienTT, hinhThucTT)) {
            JOptionPane.showMessageDialog(view, "Thanh toán thành công!");
            
            // Hiển thị thông tin lên TextArea
            view.txtAreaThongTin.setText(
                    "THÔNG TIN THANH TOÁN:\n" +
                    "- Mã HĐBH: " + maHDBH + "\n" +
                    "- Khách hàng: " + view.txtTenKH.getText() + " (Mã: " + maKH + ")\n" +
                    "- Tiền thanh toán: " + String.format("%.0f", tienTT) + " đ\n" +
                    "- Hình thức: " + hinhThucTT + "\n" +
                    "- Ngày TT: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(date)
            );
            
            // Đặt lại text field
            view.txtMaHDBH.setText("");
            view.txtMaKH.setText("");
            view.txtTenKH.setText("");
            view.txtTienThanhToan.setText("");
            view.dateChooserNgayThanhToan.setDate(null);
            
            loadData(); // Lưu ý: Tùy thuôc vào DB, nếu tiền còn lại <= 0 thì trạng thái sẽ đổi
        } else {
            JOptionPane.showMessageDialog(view, "Lỗi khi lưu thanh toán!");
        }
    }
}
