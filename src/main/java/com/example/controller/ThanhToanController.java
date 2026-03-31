package com.example.controller;

import com.example.services.ThanhToanService;
import com.example.view.ThanhToanView;
import com.example.view.ThemThanhToanView;
import com.example.util.InputValidator;
import com.example.util.ValidationResult;
import com.example.exception.ServiceException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller điều khiển các tương tác giữa giao diện Thanh Toán và Dữ liệu.
 * Đã được gộp chung logic từ ThemThanhToanController và chỉ gọi Service.
 */
public class ThanhToanController {
    
    // Giao tiếp độc quyền qua Service
    private final ThanhToanService service;
    private final ThanhToanView view;
    private List<Map<String, Object>> hoaDonDangGopList;

    public ThanhToanController(ThanhToanService service, ThanhToanView view) {
        this.service = service;
        this.view = view;
        initController();
        loadData(); // Load danh sách chính
    }

    private void initController() {
        view.getBtnReload().addActionListener(e -> loadData());

        view.getBtnThem().addActionListener(e -> showThemThanhToanDialog());
    }

    /** Load toàn bộ danh sách thanh toán vào bảng */
    private void loadData() {
        try {
            List<Map<String, Object>> list = service.getAllThanhToanAsMap();
            var model = view.getTableModel();
            model.setRowCount(0);
            for (Map<String, Object> row : list) {
                model.addRow(new Object[]{
                    row.get("maHDBH"), row.get("maKH"),
                    row.get("ngayTT"), row.get("tienThanhToan"), row.get("hinhThucTT")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu thanh toán: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===========================================
    // LOGIC CỦA THEMTHANHTOANCONTROLLER (GỘP VÀO)
    // ===========================================

    private void showThemThanhToanDialog() {
        ThemThanhToanView themView = new ThemThanhToanView();

        // 1. Khởi tạo dữ liệu bảng Hóa Đơn Trả Góp
        loadDataForThemView(themView);

        // 2. Click vào bảng để lấy thông tin tự động điền
        themView.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = themView.table.getSelectedRow();
                if (row >= 0) {
                    fillThongTinToForm(themView, row);
                }
            }
        });

        // 3. Tìm kiếm real-time
        themView.txtNhapTenKH.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { searchThemView(themView); }
            @Override
            public void removeUpdate(DocumentEvent e) { searchThemView(themView); }
            @Override
            public void changedUpdate(DocumentEvent e) { searchThemView(themView); }
        });

        // 4. Nút Xuất Hóa Đơn (Thanh Toán trả góp)
        themView.btnXuatHoaDon.addActionListener(e -> xuatThanhToan(themView));

        // ThemThanhToanView is now a JPanel — shown inline, no window listener needed
    }

    private void loadDataForThemView(ThemThanhToanView themView) {
        try {
            hoaDonDangGopList = service.getAllHoaDonDangGop();
            themView.loadData(hoaDonDangGopList);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(themView, "Lỗi tải hóa đơn trả góp: " + ex.getMessage());
        }
    }

    private void fillThongTinToForm(ThemThanhToanView themView, int row) {
        var model = themView.table.getModel();
        themView.txtMaHDBH.setText(model.getValueAt(row, 0).toString());
        themView.txtMaKH.setText(model.getValueAt(row, 1).toString());
        themView.txtTenKH.setText(model.getValueAt(row, 2).toString());
        // Tiền góp tháng mặc định
        themView.txtTienThanhToan.setText(model.getValueAt(row, 4).toString()); 
    }

    private void searchThemView(ThemThanhToanView themView) {
        String keyword = themView.txtNhapTenKH.getText().trim().toLowerCase();
        if (hoaDonDangGopList == null) return;

        List<Map<String, Object>> result = hoaDonDangGopList.stream()
                .filter(hd -> {
                    String tenKH = hd.get("tenKH") != null ? hd.get("tenKH").toString().toLowerCase() : "";
                    return tenKH.contains(keyword);
                })
                .collect(Collectors.toList());
        themView.loadData(result);
    }

    private void xuatThanhToan(ThemThanhToanView themView) {
        if (themView.txtMaHDBH.getText().isEmpty() || themView.txtMaKH.getText().isEmpty()
                || themView.txtTienThanhToan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(themView, "Vui lòng chọn hóa đơn và nhập tiền thanh toán!");
            return;
        }

        ValidationResult<Integer> rMaHDBH = InputValidator.parseIntSafe(themView.txtMaHDBH.getText(), "Mã hóa đơn");
        if (!rMaHDBH.isValid()) { JOptionPane.showMessageDialog(themView, rMaHDBH.getErrorMessage()); return; }

        ValidationResult<Integer> rMaKH = InputValidator.parseIntSafe(themView.txtMaKH.getText(), "Mã khách hàng");
        if (!rMaKH.isValid()) { JOptionPane.showMessageDialog(themView, rMaKH.getErrorMessage()); return; }

        ValidationResult<Double> rTienTT = InputValidator.parseCurrency(themView.txtTienThanhToan.getText(), "Tiền thanh toán");
        if (!rTienTT.isValid()) { JOptionPane.showMessageDialog(themView, rTienTT.getErrorMessage()); return; }

        int maHDBH = rMaHDBH.getValue();
        int maKH = rMaKH.getValue();
        double tienTT = rTienTT.getValue();
        String hinhThucTT = themView.cmbHinhThucTT.getSelectedItem().toString();
        Date date = themView.dateChooserNgayThanhToan.getDate();

        if (date == null) {
            JOptionPane.showMessageDialog(themView, "Vui lòng chọn ngày thanh toán!");
            return;
        }

        try {
            boolean success = service.themThanhToan(maHDBH, maKH, new java.sql.Date(date.getTime()), tienTT, hinhThucTT);
            if (success) {
                JOptionPane.showMessageDialog(themView, "Thanh toán thành công!");

                // Hiển thị thông tin lên TextArea
                themView.txtAreaThongTin.setText(
                        "THÔNG TIN THANH TOÁN:\n" +
                                "- Mã HĐBH: " + maHDBH + "\n" +
                                "- Khách hàng: " + themView.txtTenKH.getText() + " (Mã: " + maKH + ")\n" +
                                "- Tiền thanh toán: " + String.format("%.0f", tienTT) + " đ\n" +
                                "- Hình thức: " + hinhThucTT + "\n" +
                                "- Ngày TT: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(date));

                // Đặt lại text field
                themView.txtMaHDBH.setText("");
                themView.txtMaKH.setText("");
                themView.txtTenKH.setText("");
                themView.txtTienThanhToan.setText("");
                themView.dateChooserNgayThanhToan.setDate(null);

                // Reload bảng
                loadDataForThemView(themView);
            } else {
                JOptionPane.showMessageDialog(themView, "Thanh toán thất bại (Có lỗi logic nghiệp vụ)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(themView, "Lỗi từ hệ thống CSDL: " + ex.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(themView, "Lỗi không xác định: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
