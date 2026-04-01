package com.example.controller;

import com.example.services.ThanhToanService;
import com.example.view.ThanhToanView;
import com.example.view.ThemThanhToanView;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller Thanh Toán — Async optimized.
 */
public class ThanhToanController {
    
    private final ThanhToanService service;
    private final ThanhToanView view;
    private List<Map<String, Object>> hoaDonDangGopList;

    public ThanhToanController(ThanhToanService service, ThanhToanView view) {
        this.service = service;
        this.view = view;
        initController();
        loadData();
    }

    private void initController() {
        view.getBtnReload().addActionListener(e -> loadData());
        view.getBtnThem().addActionListener(e -> showThemThanhToanDialog());
    }

    // ====== ASYNC: Load danh sách thanh toán ======
    private void loadData() {
        SwingWorkerUtils.runAsync(
            (JComponent) view.getBtnReload(),
            () -> service.getAllThanhToanAsMap(),
            list -> {
                var model = view.getTableModel();
                model.setRowCount(0);
                for (Map<String, Object> row : list) {
                    model.addRow(new Object[]{
                        row.get("maHDBH"), row.get("maKH"),
                        row.get("ngayTT"), row.get("tienThanhToan"), row.get("hinhThucTT")
                    });
                }
            },
            ex -> JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu thanh toán: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
        );
    }

    private void showThemThanhToanDialog() {
        ThemThanhToanView themView = new ThemThanhToanView();
        loadDataForThemView(themView);

        themView.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = themView.table.getSelectedRow();
                if (row >= 0) fillThongTinToForm(themView, row);
            }
        });

        themView.txtNhapTenKH.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { searchThemView(themView); }
            @Override public void removeUpdate(DocumentEvent e) { searchThemView(themView); }
            @Override public void changedUpdate(DocumentEvent e) { searchThemView(themView); }
        });

        themView.btnXuatHoaDon.addActionListener(e -> xuatThanhToan(themView));
    }

    // ====== ASYNC: Load dữ liệu cho form Thêm thanh toán ======
    private void loadDataForThemView(ThemThanhToanView themView) {
        SwingWorkerUtils.runAsync(
            null,
            () -> service.getAllHoaDonDangGop(),
            list -> {
                hoaDonDangGopList = list;
                themView.loadData(list);
            },
            ex -> JOptionPane.showMessageDialog(themView, "Lỗi tải hóa đơn trả góp: " + ex.getMessage())
        );
    }

    private void fillThongTinToForm(ThemThanhToanView themView, int row) {
        var model = themView.table.getModel();
        themView.txtMaHDBH.setText(model.getValueAt(row, 0).toString());
        themView.txtMaKH.setText(model.getValueAt(row, 1).toString());
        themView.txtTenKH.setText(model.getValueAt(row, 2).toString());
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

    // ====== ASYNC: Xuất thanh toán ======
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

        SwingWorkerUtils.runAsyncVoid(
            themView.btnXuatHoaDon,
            () -> service.themThanhToan(maHDBH, maKH, new java.sql.Date(date.getTime()), tienTT, hinhThucTT),
            () -> {
                JOptionPane.showMessageDialog(themView, "Thanh toán thành công!");
                themView.txtAreaThongTin.setText(
                    "THÔNG TIN THANH TOÁN:\n" +
                    "- Mã HĐBH: " + maHDBH + "\n" +
                    "- Khách hàng: " + themView.txtTenKH.getText() + " (Mã: " + maKH + ")\n" +
                    "- Tiền thanh toán: " + String.format("%.0f", tienTT) + " đ\n" +
                    "- Hình thức: " + hinhThucTT + "\n" +
                    "- Ngày TT: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(date));

                themView.txtMaHDBH.setText(""); themView.txtMaKH.setText("");
                themView.txtTenKH.setText(""); themView.txtTienThanhToan.setText("");
                themView.dateChooserNgayThanhToan.setDate(null);

                loadDataForThemView(themView);
            },
            ex -> JOptionPane.showMessageDialog(themView, "Lỗi từ hệ thống CSDL: " + ex.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE)
        );
    }
}
