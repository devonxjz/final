package com.example.controller;

import com.example.config.AppConfig; // BỔ SUNG: Import AppConfig để lấy Session người dùng
import com.example.dto.KhachHangDTO;
import com.example.dto.ChiTietHDBHDTO;
import com.example.dto.HoaDonBanHangDTO;
import com.example.dto.SanPhamDTO;
import com.example.dto.ThanhToanDTO;
import com.example.services.HoaDonBanHangService;
import com.example.services.KhachHangService;
import com.example.services.SanPhamService;
import com.example.util.InputValidator;
import com.example.util.ValidationResult;
import com.example.util.SwingWorkerUtils;
import com.example.view.HoaDonBanHangView;
import com.example.view.HoaDonView;
import com.example.view.ThemHoaDonBanView;
import com.example.exception.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller thống nhất xử lý Quản lý HĐ + Bán hàng/Trả góp.
 * Đã tối ưu: Truyền mã nhân viên lập đơn từ phiên đăng nhập hiện tại.
 */
public class HoaDonBanHangController {

    private final HoaDonBanHangService hdService;
    private final SanPhamService spService;
    private final KhachHangService khService;

    private final HoaDonBanHangView dsHoaDonView;
    private final HoaDonView banHangView;

    private final List<ChiTietHDBHDTO> gioHang = new ArrayList<>();

    public HoaDonBanHangController(HoaDonBanHangService hdService,
                                   SanPhamService spService,
                                   KhachHangService khService,
                                   HoaDonBanHangView dsHoaDonView,
                                   HoaDonView banHangView) {
        this.hdService = hdService;
        this.spService = spService;
        this.khService = khService;
        this.dsHoaDonView = dsHoaDonView;
        this.banHangView = banHangView;

        initController();
        loadInitialData();
    }

    private void initController() {
        initDanhSachHoaDonEvents();
        initBanHangEvents();
    }

    private void loadInitialData() {
        loadDsHoaDon();
        loadDsSanPham();
        updateUIPayMode();
    }

    // =========================================================================
    // PHẦN 1: NGHIỆP VỤ QUẢN LÝ DANH SÁCH HÓA ĐƠN
    // =========================================================================

    private void initDanhSachHoaDonEvents() {
        dsHoaDonView.btnReload.addActionListener(e -> loadDsHoaDon());

        dsHoaDonView.btnTimKiem.addActionListener(e -> {
            Date selectedDate = dsHoaDonView.dateChooser.getDate();
            if (selectedDate != null) {
                SwingWorkerUtils.runAsync(
                    dsHoaDonView.btnTimKiem,
                    () -> hdService.searchByDate(selectedDate),
                    this::hienThiDsHDBH,
                    ex -> JOptionPane.showMessageDialog(dsHoaDonView, "Lỗi tìm kiếm: " + ex.getMessage())
                );
            } else {
                loadDsHoaDon();
            }
        });

        dsHoaDonView.btnThem.addActionListener(e -> showThemHoaDonBanDialog());

        dsHoaDonView.tableDsHDBH.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = dsHoaDonView.tableDsHDBH.getSelectedRow();
                if (row >= 0) {
                    int maHDBH = Integer.parseInt(dsHoaDonView.tableDsHDBH.getValueAt(row, 0).toString());
                    loadChiTietHoaDon(maHDBH);
                    loadThanhToanHoaDon(maHDBH);
                }
            }
        });
    }

    // ====== ASYNC: Load danh sách hóa đơn ======
    private void loadDsHoaDon() {
        SwingWorkerUtils.runAsync(
            dsHoaDonView.btnReload,
            () -> hdService.getAllHoaDon(),
            danhSach -> {
                hienThiDsHDBH(danhSach);
                ((DefaultTableModel) dsHoaDonView.tableChiTietHD.getModel()).setRowCount(0);
                ((DefaultTableModel) dsHoaDonView.tableThanhToan.getModel()).setRowCount(0);
            },
            ex -> JOptionPane.showMessageDialog(dsHoaDonView, "Lỗi tải hóa đơn: " + ex.getMessage())
        );
    }

    private void hienThiDsHDBH(List<HoaDonBanHangDTO> danhSach) {
        // SỬA LỖI: Bổ sung cột Khách Hàng và Nhân Viên để hiển thị dữ liệu mới từ DTO
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Mã HĐ", "Khách hàng", "Nhân viên lập", "Ngày tạo", "Loại HĐ", "Tổng tiền", "Trạng thái"}, 0);
        for (HoaDonBanHangDTO hd : danhSach) {
            model.addRow(new Object[]{
                hd.maHDBH(),
                hd.tenKH(),
                hd.tenNV(),
                hd.ngayTao(),
                hd.loaiHD(),
                String.format("%,.0f VNĐ", hd.tongTien()), // Format tiền tệ chuẩn VN
                hd.trangThai()
            });
        }
        dsHoaDonView.tableDsHDBH.setModel(model);
    }

    // ====== ASYNC: Load chi tiết hóa đơn ======
    private void loadChiTietHoaDon(int maHDBH) {
        SwingWorkerUtils.runAsync(
            null,
            () -> hdService.getAllChiTiet(maHDBH),
            ds -> {
                DefaultTableModel model = new DefaultTableModel(new String[]{"Mã SP", "Tên SP", "Số lượng", "Thành tiền"}, 0);
                for (ChiTietHDBHDTO ct : ds) {
                    model.addRow(new Object[]{ct.maSP(), ct.tenSP(), ct.soLuong(), String.format("%,.0f VNĐ", ct.tongTien())});
                }
                dsHoaDonView.tableChiTietHD.setModel(model);
            },
            ex -> {}
        );
    }

    // ====== ASYNC: Load thanh toán của hóa đơn ======
    private void loadThanhToanHoaDon(int maHDBH) {
        SwingWorkerUtils.runAsync(
            null,
            () -> hdService.getAllThanhToan(maHDBH),
            ds -> {
                DefaultTableModel model = new DefaultTableModel(
                    new String[]{"Mã TT", "Khách hàng", "Ngày TT", "Số tiền", "Hình thức TT"}, 0);
                for (ThanhToanDTO tt : ds) {
                    model.addRow(new Object[]{
                        tt.maTT(), tt.tenKH(), tt.ngayTT(),
                        String.format("%,.0f VNĐ", tt.tienThanhToan()), tt.hinhThucTT()
                    });
                }
                dsHoaDonView.tableThanhToan.setModel(model);
            },
            ex -> {}
        );
    }

    // =========================================================================
    // PHẦN 2: NGHIỆP VỤ BÁN HÀNG & GIỎ HÀNG
    // =========================================================================

    private void initBanHangEvents() {
        banHangView.tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) themSanPhamVaoGio();
            }
        });

        banHangView.btnAddToCard.addActionListener(e -> themSanPhamVaoGio());
        banHangView.btnRemoveFromCard.addActionListener(e -> xoaSanPhamKhoiGio());
        banHangView.cbLoaiHD.addActionListener(e -> updateUIPayMode());
        banHangView.btnThanhToan.addActionListener(e -> xuLyThanhToan());
        banHangView.btnHuy.addActionListener(e -> resetFormBanHang());
    }

    private void loadDsSanPham() {
        SwingWorkerUtils.runAsync(
            null,
            () -> spService.getAllSanPham(),
            danhSach -> {
                DefaultTableModel tModel = (DefaultTableModel) banHangView.tableSanPham.getModel();
                tModel.setRowCount(0);
                for (SanPhamDTO dto : danhSach) {
                    tModel.addRow(new Object[]{dto.maSP(), dto.tenSP(), String.format("%,.0f", dto.giaBan()), dto.soLuongTrongKho()});
                }
            },
            ex -> JOptionPane.showMessageDialog(banHangView, "Lỗi tải danh sách sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
        );
    }

    private void updateUIPayMode() {
        boolean isTraGop = "Trả góp".equals(banHangView.cbLoaiHD.getSelectedItem().toString());
        banHangView.panelTraGop.setVisible(isTraGop);
    }

    private void themSanPhamVaoGio() {
        int row = banHangView.tableSanPham.getSelectedRow();
        if (row < 0) return;

        int maSP = Integer.parseInt(banHangView.tableSanPham.getValueAt(row, 0).toString());
        String tenSP = banHangView.tableSanPham.getValueAt(row, 1).toString();
        // Lọc bỏ dấu phẩy do format tiền tệ trước khi parse
        double giaBan = Double.parseDouble(banHangView.tableSanPham.getValueAt(row, 2).toString().replace(",", ""));
        int tonKho = Integer.parseInt(banHangView.tableSanPham.getValueAt(row, 3).toString());

        String inputSL = JOptionPane.showInputDialog(banHangView, "Nhập số lượng cho " + tenSP + ":", "1");
        if (inputSL == null || inputSL.trim().isEmpty()) return;

        ValidationResult<Integer> rSL = InputValidator.parseIntSafe(inputSL, "Số lượng");
        if (!rSL.isValid()) { JOptionPane.showMessageDialog(banHangView, rSL.getErrorMessage()); return; }

        int sl = rSL.getValue();
        if (sl <= 0 || sl > tonKho) {
            JOptionPane.showMessageDialog(banHangView, sl <= 0 ? "Số lượng phải > 0!" : "Số lượng tồn kho không đủ!");
            return;
        }

        capNhatGioHang(maSP, tenSP, giaBan, sl, tonKho);
        updateTableGioHang();
    }

    private void capNhatGioHang(int maSP, String tenSP, double giaBan, int slThem, int tonKho) {
        for (int i = 0; i < gioHang.size(); i++) {
            ChiTietHDBHDTO current = gioHang.get(i);
            if (current.maSP() != null && current.maSP() == maSP) {
                int newSl = current.soLuong() + slThem;
                if (newSl > tonKho) {
                    JOptionPane.showMessageDialog(banHangView, "Tổng số lượng mua vượt tồn kho!");
                    return;
                }
                gioHang.set(i, new ChiTietHDBHDTO(0, maSP, tenSP, newSl, giaBan, newSl * giaBan));
                return;
            }
        }
        gioHang.add(new ChiTietHDBHDTO(0, maSP, tenSP, slThem, giaBan, slThem * giaBan));
    }

    private void updateTableGioHang() {
        double tongTien = 0;
        DefaultTableModel model = (DefaultTableModel) banHangView.tableGioHang.getModel();
        model.setRowCount(0);

        for (ChiTietHDBHDTO ct : gioHang) {
            tongTien += ct.tongTien();
            model.addRow(new Object[]{ct.maSP(), ct.tenSP(), ct.soLuong(), String.format("%,.0f", ct.giaBan()), String.format("%,.0f", ct.tongTien())});
        }
        banHangView.txtTongTien.setText(String.format("%.0f", tongTien));
    }

    private void xoaSanPhamKhoiGio() {
        int row = banHangView.tableGioHang.getSelectedRow();
        if (row >= 0) {
            gioHang.remove(row);
            updateTableGioHang();
        }
    }

    // ====== ASYNC: Xử lý thanh toán ======
    private void xuLyThanhToan() {
        if (gioHang.isEmpty()) {
            JOptionPane.showMessageDialog(banHangView, "Giỏ hàng đang trống!");
            return;
        }

        ValidationResult<Integer> rMaKH = InputValidator.parseIntSafe(banHangView.txtMaKH.getText(), "Mã khách hàng");
        ValidationResult<Double> rTongTien = InputValidator.parseCurrency(banHangView.txtTongTien.getText(), "Tổng tiền");

        if (!rMaKH.isValid()) { JOptionPane.showMessageDialog(banHangView, rMaKH.getErrorMessage()); return; }
        if (!rTongTien.isValid()) { JOptionPane.showMessageDialog(banHangView, rTongTien.getErrorMessage()); return; }

        String loaiHD = banHangView.cbLoaiHD.getSelectedItem().toString();
        double laiSuat = 0;
        int thoiHan = 0;

        if ("Trả góp".equals(loaiHD)) {
            ValidationResult<Float> rLaiSuat = InputValidator.parseFloatSafe(banHangView.txtLaiSuat.getText(), "Lãi suất");
            ValidationResult<Integer> rThoiHan = InputValidator.parseIntSafe(banHangView.txtThoiHan.getText(), "Thời hạn");

            if (!rLaiSuat.isValid()) { JOptionPane.showMessageDialog(banHangView, rLaiSuat.getErrorMessage()); return; }
            if (!rThoiHan.isValid()) { JOptionPane.showMessageDialog(banHangView, rThoiHan.getErrorMessage()); return; }

            laiSuat = rLaiSuat.getValue();
            thoiHan = rThoiHan.getValue();
        }

        final double finalLaiSuat = laiSuat;
        final int finalThoiHan = thoiHan;
        final List<ChiTietHDBHDTO> snapshot = new ArrayList<>(gioHang);

        // BỔ SUNG: Lấy mã nhân viên đang thao tác
        int maNV = AppConfig.getCurrentUser().getNhanVien().getMaNV();

        SwingWorkerUtils.runAsyncVoid(
            banHangView.btnThanhToan,
            // SỬA LỖI: Truyền maNV vào Service
            () -> hdService.thanhToanHoaDon(rMaKH.getValue(), maNV, loaiHD, rTongTien.getValue(), finalLaiSuat, finalThoiHan, snapshot),
            () -> {
                JOptionPane.showMessageDialog(banHangView, "Thanh toán thành công! Người thu tiền: " + AppConfig.getCurrentUser().getNhanVien().getTenNV());
                resetFormBanHang();
                loadDsSanPham();
                loadDsHoaDon();
            },
            ex -> JOptionPane.showMessageDialog(banHangView, "Lỗi khi lưu hóa đơn: " + ex.getMessage(), "Lỗi nghiệp vụ", JOptionPane.ERROR_MESSAGE)
        );
    }

    private void resetFormBanHang() {
        gioHang.clear();
        updateTableGioHang();
        banHangView.txtMaKH.setText("");
        banHangView.txtTenKH.setText("");
        banHangView.txtLaiSuat.setText("");
        banHangView.txtThoiHan.setText("");
        banHangView.cbLoaiHD.setSelectedIndex(0);
    }

    // =========================================================================
    // PHẦN 3: NGHIỆP VỤ TẠO ĐƠN HÀNG MỚI (POPUP)
    // =========================================================================

    private void showThemHoaDonBanDialog() {
        ThemHoaDonBanView themView = new ThemHoaDonBanView();

        JDialog dialog = new JDialog();
        dialog.setTitle("Lập Hóa Đơn Mới");
        dialog.setModal(true);
        dialog.setContentPane(themView);
        dialog.setSize(950, 800);
        dialog.setLocationRelativeTo(dsHoaDonView);

        Map<Integer, Integer> gioHangPopup = new HashMap<>();
        final List<SanPhamDTO> sanPhamList = new ArrayList<>();

        SwingWorkerUtils.runAsync(
            null,
            () -> spService.getAllSanPham(),
            list -> {
                sanPhamList.addAll(list);

                String[] headerSP = { "Mã SP", "Tên SP", "Giá bán", "Tồn kho" };
                themView.tableSanPham.setModel(new DefaultTableModel(headerSP, 0) {
                    @Override public boolean isCellEditable(int row, int column) { return false; }
                });
                String[] headerChon = { "Mã SP", "Tên SP", "Số lượng", "Giá bán", "Thành tiền" };
                themView.tableSanPhamChon.setModel(new DefaultTableModel(headerChon, 0) {
                    @Override public boolean isCellEditable(int row, int column) { return false; }
                });

                DefaultTableModel modelSP = (DefaultTableModel) themView.tableSanPham.getModel();
                for (SanPhamDTO sp : list) {
                    modelSP.addRow(new Object[] { sp.maSP(), sp.tenSP(), String.format("%,.0f", sp.giaBan()), sp.soLuongTrongKho() });
                }
            },
            ex -> JOptionPane.showMessageDialog(themView, "Lỗi khi lấy danh sách sản phẩm!")
        );

        themView.txtSDT.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String sdt = themView.txtSDT.getText().trim();
                if (!sdt.isEmpty()) {
                    SwingWorkerUtils.runAsync(null,
                        () -> khService.search(sdt),
                        listKH -> {
                            if (listKH != null && !listKH.isEmpty()) {
                                themView.txtTenKH.setText(listKH.get(0).tenKH());
                            }
                        },
                        ex -> {}
                    );
                }
            }
        });

        themView.tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = themView.tableSanPham.getSelectedRow();
                if (row >= 0) {
                    themView.txtMaSP.setText(themView.tableSanPham.getValueAt(row, 0).toString());
                    themView.txtTenSP.setText(themView.tableSanPham.getValueAt(row, 1).toString());
                    themView.txtSoLuong.setText("1");
                    themView.txtSoLuong.requestFocus();
                }
            }
        });

        themView.btnThem.addActionListener(e -> {
            if (themView.txtMaSP.getText().isEmpty() || themView.txtSoLuong.getText().isEmpty()) {
                JOptionPane.showMessageDialog(themView, "Vui lòng chọn sản phẩm và nhập số lượng!");
                return;
            }
            try {
                int maSP = Integer.parseInt(themView.txtMaSP.getText());
                int soLuong = Integer.parseInt(themView.txtSoLuong.getText());
                if (soLuong <= 0) { JOptionPane.showMessageDialog(themView, "Số lượng phải lớn hơn 0!"); return; }
                SanPhamDTO found = sanPhamList.stream().filter(sp -> sp.maSP() == maSP).findFirst().orElse(null);
                if (found == null || found.soLuongTrongKho() < soLuong) {
                    JOptionPane.showMessageDialog(themView, "Không đủ hàng trong kho!");
                    return;
                }
                gioHangPopup.put(maSP, gioHangPopup.getOrDefault(maSP, 0) + soLuong);
                updateGioHangPopupUI(themView, gioHangPopup, sanPhamList);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(themView, "Số lượng không hợp lệ!");
            }
        });

        themView.btnHuyChon.addActionListener(e -> {
            int row = themView.tableSanPhamChon.getSelectedRow();
            if (row >= 0) {
                int maSP = Integer.parseInt(themView.tableSanPhamChon.getValueAt(row, 0).toString());
                gioHangPopup.remove(maSP);
                updateGioHangPopupUI(themView, gioHangPopup, sanPhamList);
            } else {
                JOptionPane.showMessageDialog(themView, "Vui lòng chọn sản phẩm trong giỏ để hủy!");
            }
        });

        themView.btnXuatHoaDon.addActionListener(e -> {
            if (gioHangPopup.isEmpty()) {
                JOptionPane.showMessageDialog(themView, "Giỏ hàng trống!");
                return;
            }

            ValidationResult<String> rSdt = InputValidator.validatePhone(themView.txtSDT.getText(), true);
            if (!rSdt.isValid()) { JOptionPane.showMessageDialog(themView, rSdt.getErrorMessage()); return; }

            String sdt = rSdt.getValue();
            String tenKH = themView.txtTenKH.getText().trim();

            Date ngayTao = themView.dateChooser.getDate();
            if (ngayTao == null) ngayTao = new Date();

            String loaiHD = themView.cbLoaiHD.getSelectedItem().toString();
            String hinhThucTT = themView.cbHinhThucTT.getSelectedItem().toString();
            String gioiTinh = themView.cbGioiTinh.getSelectedItem().toString();
            String diaChi = themView.txtDiaChi.getText().trim();

            List<Map<Integer, Integer>> chiTietList = new ArrayList<>();
            double tongTien = 0;
            for (Map.Entry<Integer, Integer> entry : gioHangPopup.entrySet()) {
                Map<Integer, Integer> map = new HashMap<>();
                map.put(entry.getKey(), entry.getValue());
                chiTietList.add(map);
                SanPhamDTO found2 = sanPhamList.stream().filter(s -> s.maSP() == entry.getKey()).findFirst().orElse(null);
                if (found2 != null) tongTien += found2.giaBan() * entry.getValue();
            }

            final double finalTongTien = tongTien;
            final Date finalNgayTao = ngayTao;

            // BỔ SUNG: Lấy mã nhân viên
            int maNV = AppConfig.getCurrentUser().getNhanVien().getMaNV();

            SwingWorkerUtils.runAsyncVoid(
                themView.btnXuatHoaDon,
                () -> {
                    // SỬA LỖI: Truyền maNV (thay cho số 0 lúc trước)
                    boolean success = hdService.themHoaDonVaChiTietVaThanhToan(
                        finalNgayTao, loaiHD, finalTongTien, 0, maNV, chiTietList,
                        tenKH, sdt, diaChi, gioiTinh, hinhThucTT, "Đã thanh toán");
                    if (!success) throw new ServiceException("Lỗi khi xuất hóa đơn!");
                },
                () -> {
                    JOptionPane.showMessageDialog(themView, "Xuất hóa đơn thành công! Người lập: " + AppConfig.getCurrentUser().getNhanVien().getTenNV());
                    dialog.dispose(); // Đóng popup sau khi xuất hóa đơn thành công
                    loadDsHoaDon();
                    loadDsSanPham();
                },
                ex -> JOptionPane.showMessageDialog(themView, "Lỗi nghiệp vụ: " + ex.getMessage())
            );
        });

        dialog.setVisible(true);
    }

    private void updateGioHangPopupUI(ThemHoaDonBanView themView, Map<Integer, Integer> gioHangPopup, List<SanPhamDTO> sanPhamList) {
        DefaultTableModel model = (DefaultTableModel) themView.tableSanPhamChon.getModel();
        model.setRowCount(0);
        for (Map.Entry<Integer, Integer> entry : gioHangPopup.entrySet()) {
            int maSP = entry.getKey();
            int qty = entry.getValue();
            SanPhamDTO sp = sanPhamList.stream().filter(s -> s.maSP() == maSP).findFirst().orElse(null);
            if (sp != null) {
                model.addRow(new Object[] { maSP, sp.tenSP(), qty, String.format("%,.0f", sp.giaBan()), String.format("%,.0f", sp.giaBan() * qty) });
            }
        }
    }
}
