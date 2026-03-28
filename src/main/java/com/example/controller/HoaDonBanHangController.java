package com.example.controller;

import com.example.dao.HoaDonBanHangDAO;
import com.example.entity.ChiTietHDBH;
import com.example.entity.HoaDonBanHang;
import com.example.entity.ThanhToan;
import com.example.view.HoaDonBanHangView;
import com.example.view.ThemHoaDonBanView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

public class HoaDonBanHangController {
    private final HoaDonBanHangDAO dao;
    private final HoaDonBanHangView view;

    public HoaDonBanHangController(HoaDonBanHangDAO dao, HoaDonBanHangView view) {
        this.dao = dao;
        this.view = view;
        initController();
        loadData();
    }

    private void initController() {
        view.btnReload.addActionListener(e -> loadData());

        view.btnTimKiem.addActionListener(e -> {
            Date selectedDate = view.dateChooser.getDate();
            if (selectedDate != null) {
                List<HoaDonBanHang> result = dao.timKiem(selectedDate);
                hienThiDsHDBH(result);
            } else {
                loadData();
            }
        });

        view.btnThem.addActionListener(e -> {
            ThemHoaDonBanView themView = new ThemHoaDonBanView();
            ThemHoaDonBanController themController = new ThemHoaDonBanController(dao, themView);
        });

        view.tableDsHDBH.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.tableDsHDBH.getSelectedRow();
                if (row >= 0) {
                    int maHDBH = Integer.parseInt(view.tableDsHDBH.getValueAt(row, 0).toString());
                    loadChiTiet(maHDBH);
                    loadThanhToan(maHDBH);
                }
            }
        });
    }

    private void loadData() {
        List<HoaDonBanHang> ds = dao.getAllHDBH();
        hienThiDsHDBH(ds);
        
        // Clear chi tiết và thanh toán
        ((DefaultTableModel) view.tableChiTietHD.getModel()).setRowCount(0);
        ((DefaultTableModel) view.tableThanhToan.getModel()).setRowCount(0);
    }

    private void hienThiDsHDBH(List<HoaDonBanHang> danhSach) {
        String[] columnNames = {"Mã HĐBH", "Ngày tạo", "Loại HĐ", "Tổng tiền", "Tiền cọc", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (HoaDonBanHang hd : danhSach) {
            model.addRow(new Object[]{
                    hd.getMaHDBH(), hd.getNgayTao(), hd.getLoaiHD(),
                    hd.getTongTien(), hd.getTienCoc(), hd.getTrangThai()
            });
        }
        view.tableDsHDBH.setModel(model);
    }

    private void loadChiTiet(int maHDBH) {
        List<ChiTietHDBH> dsCT = dao.getAllChiTiet(maHDBH);
        String[] columns = {"Mã SP", "Số lượng", "Tổng tiền"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (ChiTietHDBH ct : dsCT) {
            int maSP = ct.getSanPham() != null ? ct.getSanPham().getMaSP() : 0;
            model.addRow(new Object[]{maSP, ct.getSoLuong(), ct.getTongTien()});
        }
        view.tableChiTietHD.setModel(model);
    }

    private void loadThanhToan(int maHDBH) {
        List<ThanhToan> dsTT = dao.getAllThanhToan(maHDBH);
        String[] columns = {"Mã TT", "Mã KH", "Ngày TT", "Tiền TT", "Hình thức TT"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (ThanhToan tt : dsTT) {
            int maKH = tt.getKhachHang() != null ? tt.getKhachHang().getMaKH() : 0;
            model.addRow(new Object[]{
                    tt.getMaTT(), maKH, tt.getNgayTT(),
                    tt.getTienThanhToan(), tt.getHinhThucTT()
            });
        }
        view.tableThanhToan.setModel(model);
    }
}
