package com.example.controller;

import com.example.config.AppConfig;
import com.example.services.ThanhToanService;
import com.example.view.ThanhToanView;
import com.example.view.ThemThanhToanView;
import com.example.util.InputValidator;
import com.example.util.ValidationResult;
import com.example.util.SwingWorkerUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        view.getBtnXoa().addActionListener(e -> xuLyXoaThanhToan());
        view.getBtnSua().addActionListener(e -> xuLySuaThanhToan());
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
                        row.get("maTT"), row.get("maHDBH"), row.get("tenKH"), row.get("tenNV"),
                        String.format("%,.0f VNĐ", row.get("tienThanhToan")), row.get("ngayTT"),
                        row.get("hinhThucTT"),
                        row.get("trangThai") // Bổ sung cột thứ 8
                    });
                }
            },
            ex -> JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu: " + ex.getMessage())
        );
    }
    private void xuLyXoaThanhToan() {
        int row = view.getTableThanhToan().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một giao dịch để xóa!");
            return;
        }

        // BỔ SUNG: Kiểm tra trạng thái hóa đơn
        String trangThai = view.getTableThanhToan().getValueAt(row, 7).toString();
        if ("Đã thanh toán".equals(trangThai)) {
            JOptionPane.showMessageDialog(view, "Không thể xóa thanh toán vì hóa đơn này đã hoàn tất!", "Bảo vệ dữ liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maTT = Integer.parseInt(view.getTableThanhToan().getValueAt(row, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa giao dịch này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            SwingWorkerUtils.runAsync(view.getBtnXoa(), () -> service.xoaThanhToan(maTT),
                success -> {
                    if (success) { JOptionPane.showMessageDialog(view, "Xóa thành công!"); loadData(); }
                }, ex -> {}
            );
        }
    }

    // BỔ SUNG: Hàm xử lý Sửa
    private void xuLySuaThanhToan() {
        int row = view.getTableThanhToan().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn giao dịch cần sửa!");
            return;
        }

        // Chặn sửa nếu HĐ đã đóng
        String trangThai = view.getTableThanhToan().getValueAt(row, 7).toString();
        if ("Đã thanh toán".equals(trangThai)) {
            JOptionPane.showMessageDialog(view, "Không thể sửa thanh toán vì hóa đơn này đã hoàn tất!", "Bảo vệ dữ liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maTT = Integer.parseInt(view.getTableThanhToan().getValueAt(row, 0).toString());
        String tienStr = view.getTableThanhToan().getValueAt(row, 4).toString().replaceAll("[^\\d]", "");
        Date ngayHienTai = (Date) view.getTableThanhToan().getValueAt(row, 5);
        String hinhThucHienTai = view.getTableThanhToan().getValueAt(row, 6).toString();

        // Tạo Dialog Sửa nhanh
        JDialog dlg = new JDialog();
        dlg.setTitle("Sửa Giao Dịch " + maTT);
        dlg.setModal(true);
        dlg.setSize(400, 250);
        dlg.setLocationRelativeTo(view);
        dlg.setLayout(new java.awt.GridLayout(4, 2, 10, 10));
        dlg.getContentPane().setBackground(com.example.config.UIThemeConfig.BG_DARK);

        JTextField txtTienMoi = com.example.config.UIThemeConfig.createTextField();
        txtTienMoi.setText(tienStr);

        com.toedter.calendar.JDateChooser dateChooser = new com.toedter.calendar.JDateChooser(ngayHienTai);

        JComboBox<String> cmbHinhThuc = com.example.config.UIThemeConfig.createComboBox(new String[]{"Tiền mặt", "Chuyển khoản"});
        cmbHinhThuc.setSelectedItem(hinhThucHienTai);

        JButton btnLuu = com.example.config.UIThemeConfig.createSuccessButton("Lưu Thay Đổi");

        dlg.add(com.example.config.UIThemeConfig.createLabel("Số tiền mới:")); dlg.add(txtTienMoi);
        dlg.add(com.example.config.UIThemeConfig.createLabel("Ngày TT:")); dlg.add(dateChooser);
        dlg.add(com.example.config.UIThemeConfig.createLabel("Phương thức:")); dlg.add(cmbHinhThuc);
        dlg.add(new JLabel()); dlg.add(btnLuu);

        btnLuu.addActionListener(ev -> {
            ValidationResult<Double> rTien = InputValidator.parseCurrency(txtTienMoi.getText(), "Số tiền");
            if (!rTien.isValid()) { JOptionPane.showMessageDialog(dlg, rTien.getErrorMessage()); return; }

            SwingWorkerUtils.runAsync(btnLuu,
                () -> service.updateThanhToan(maTT, dateChooser.getDate(), rTien.getValue(), cmbHinhThuc.getSelectedItem().toString()),
                success -> {
                    if (success) {
                        JOptionPane.showMessageDialog(dlg, "Cập nhật thành công!");
                        dlg.dispose();
                        loadData(); // Tải lại bảng để thấy kết quả
                    }
                }, ex -> {}
            );
        });
        dlg.setVisible(true);
    }

    private void showThemThanhToanDialog() {
        ThemThanhToanView themView = new ThemThanhToanView();

        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm Giao Dịch Thanh Toán");
        dialog.setModal(true);
        dialog.setContentPane(themView);
        dialog.setSize(950, 800);
        dialog.setLocationRelativeTo(view);

        loadDataForThemView(themView);

        themView.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = themView.table.getSelectedRow();
                if (row >= 0) fillThongTinToForm(themView, row);
            }
        });

        // Nút TÌM KIẾM
        themView.btnTimKiem.addActionListener(e -> searchThemView(themView));

        // Nút XÁC NHẬN (Truyền thêm biến dialog vào để có thể đóng form)
        themView.btnXacNhanThanhToan.addActionListener(e -> xuatThanhToan(themView, dialog));

        dialog.setVisible(true);
    }

    private void loadDataForThemView(ThemThanhToanView themView) {
        SwingWorkerUtils.runAsync(null,
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
        int maHD = Integer.parseInt(model.getValueAt(row, 0).toString());

        themView.txtMaHDBH.setText(String.valueOf(maHD));
        themView.txtMaKH.setText(model.getValueAt(row, 1).toString());
        themView.txtTenKH.setText(model.getValueAt(row, 2).toString());

        double conNo = service.getSoTienConNo(maHD);
        themView.txtTienThanhToan.setText(String.format("%.0f", conNo));

        // =========================================================
        // BỔ SUNG: TẢI VÀ HIỂN THỊ LỊCH SỬ THANH TOÁN XUỐNG Ô BÊN DƯỚI
        // =========================================================
        themView.txtAreaThongTin.setText("Đang tải dữ liệu...");

        SwingWorkerUtils.runAsync(
            null,
            () -> service.getLichSuThanhToanTheoHD(maHD),
            lichSu -> {
                if (lichSu == null || lichSu.isEmpty()) {
                    themView.txtAreaThongTin.setText("Chưa có lịch sử đóng tiền nào cho đơn hàng này.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("LỊCH SỬ THANH TOÁN (Mã HĐ: ").append(maHD).append(")\n");
                    sb.append("----------------------------------------------------------------------\n");

                    for (int i = 0; i < lichSu.size(); i++) {
                        // Gọi DTO với đường dẫn đầy đủ để tránh phải kéo lên import
                        com.example.dto.ThanhToanDTO tt = lichSu.get(i);
                        sb.append(String.format("Lần %d: Ngày %s | Đã đóng: %,.0f VNĐ | Phương thức: %s\n",
                            i + 1,
                            new java.text.SimpleDateFormat("dd/MM/yyyy").format(tt.ngayTT()),
                            tt.tienThanhToan(),
                            tt.hinhThucTT()
                        ));
                    }
                    sb.append("----------------------------------------------------------------------\n");
                    sb.append(String.format("=> TỔNG CÒN NỢ HIỆN TẠI: %,.0f VNĐ", conNo));

                    themView.txtAreaThongTin.setText(sb.toString());
                }
            },
            ex -> themView.txtAreaThongTin.setText("Không thể tải lịch sử: " + ex.getMessage())
        );
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

        // Kiểm tra nếu rỗng thì báo lỗi
        if (result.isEmpty() && !keyword.isEmpty()) {
            JOptionPane.showMessageDialog(themView, "Không tìm thấy hóa đơn trả góp nào của khách hàng: " + keyword, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        themView.loadData(result);
    }

    // Đã thêm tham số JDialog để đóng form sau khi thành công
    private void xuatThanhToan(ThemThanhToanView themView, JDialog dialog) {
        if (themView.txtMaHDBH.getText().isEmpty() || themView.txtMaKH.getText().isEmpty()
            || themView.txtTienThanhToan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(themView, "Vui lòng chọn hóa đơn và nhập tiền thanh toán!");
            return;
        }

        ValidationResult<Integer> rMaHDBH = InputValidator.parseIntSafe(themView.txtMaHDBH.getText(), "Mã hóa đơn");
        ValidationResult<Integer> rMaKH = InputValidator.parseIntSafe(themView.txtMaKH.getText(), "Mã khách hàng");
        ValidationResult<Double> rTienTT = InputValidator.parseCurrency(themView.txtTienThanhToan.getText(), "Tiền thanh toán");

        if (!rMaHDBH.isValid()) { JOptionPane.showMessageDialog(themView, rMaHDBH.getErrorMessage()); return; }
        if (!rMaKH.isValid()) { JOptionPane.showMessageDialog(themView, rMaKH.getErrorMessage()); return; }
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

        int maNV = AppConfig.getCurrentUser().getNhanVien().getMaNV();

        SwingWorkerUtils.runAsync(
            themView.btnXacNhanThanhToan,
            () -> service.themThanhToan(maHDBH, maKH, maNV, new java.sql.Date(date.getTime()), tienTT, hinhThucTT),
            success -> {
                if (success) {
                    JOptionPane.showMessageDialog(themView, "Thanh toán thành công! Đã trừ nợ cho khách hàng.");
                    dialog.dispose(); // ĐÓNG FORM SAU KHI THÀNH CÔNG
                    loadData(); // Tải lại bảng chính
                } else {
                    JOptionPane.showMessageDialog(themView, "Lưu thất bại, vui lòng kiểm tra lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            },
            ex -> JOptionPane.showMessageDialog(themView, "Lỗi từ hệ thống CSDL: " + ex.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE)
        );
    }
}
