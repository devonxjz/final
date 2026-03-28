package com.example.services.impl;

import com.example.dao.HoaDonBanHangDAO;
import com.example.dao.impl.HoaDonBanHangDAOImpl;
import com.example.dao.impl.KhachHangDAOImpl;
import com.example.dao.impl.SanPhamDAOImpl;
import com.example.dao.KhachHangDAO;
import com.example.dao.SanPhamDAO;
import com.example.dto.ChiTietHDBHDTO;
import com.example.entity.*;
import com.example.services.IHoaDonBanHangService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Triển khai IHoaDonBanHangService — Xử lý nghiệp vụ Hóa Đơn Bán Hàng
 */
public class HoaDonBanHangServiceImpl implements IHoaDonBanHangService {
    private final HoaDonBanHangDAO hoadonDAO;
    private final KhachHangDAO khachHangDAO;
    private final SanPhamDAO sanPhamDAO;

    public HoaDonBanHangServiceImpl() {
        this.hoadonDAO = new HoaDonBanHangDAOImpl();
        this.khachHangDAO = new KhachHangDAOImpl();
        this.sanPhamDAO = new SanPhamDAOImpl();
    }

    @Override
    public List<HoaDonBanHang> getAllHoaDon() {
        return hoadonDAO.getAllHDBH();
    }

    @Override
    public boolean thanhToanHoaDon(int maKH, String loaiHD, double tongTien,
                                    double laiSuat, int thoiHan, List<ChiTietHDBHDTO> gioHang) {
        KhachHang kh = khachHangDAO.getById(maKH);
        if (kh == null) return false;

        HoaDonBanHang hd = new HoaDonBanHang();
        hd.setNgayTao(new Date());
        hd.setLoaiHD(loaiHD);
        hd.setTongTien(tongTien);
        hd.setTrangThai("Trả góp".equals(loaiHD) ? "Đang trả góp" : "Đã thanh toán");

        if ("Trả góp".equals(loaiHD)) {
            hd.setLaiSuat(laiSuat);
            hd.setThoiHanTG(thoiHan);
            double tienCoc = tongTien * (laiSuat > 0 ? laiSuat / 100.0 : 0.3);
            hd.setTienCoc(tienCoc);
            double conLai = tongTien - tienCoc;
            hd.setSoTienConLai(conLai);
            hd.setTienGopHangThang(thoiHan > 0 ? conLai / thoiHan : 0);
        } else {
            hd.setTienCoc(tongTien); hd.setLaiSuat(0.0);
            hd.setThoiHanTG(0); hd.setTienGopHangThang(0.0); hd.setSoTienConLai(0.0);
        }

        List<ChiTietHDBH> dsCT = new ArrayList<>();
        for (ChiTietHDBHDTO ctDTO : gioHang) {
            SanPham sp = sanPhamDAO.getById(ctDTO.maSP());
            if (sp == null || sp.getSoLuongTrongKho() < ctDTO.soLuong()) {
                throw new IllegalArgumentException("SP không đủ tồn kho: " + ctDTO.tenSP());
            }
            ChiTietHDBH ct = new ChiTietHDBH();
            ct.setHoaDonBanHang(hd); ct.setSanPham(sp);
            ct.setSoLuong(ctDTO.soLuong()); ct.setTongTien(ctDTO.tongTien());
            dsCT.add(ct);
        }
        hd.setDanhSachChiTiet(dsCT);

        ThanhToan tt = new ThanhToan();
        tt.setHoaDonBanHang(hd); tt.setKhachHang(kh);
        tt.setNgayTT(new Date()); tt.setTienThanhToan(hd.getTienCoc());
        tt.setHinhThucTT("Tiền mặt");
        List<ThanhToan> dsTT = new ArrayList<>();
        dsTT.add(tt);
        hd.setDanhSachThanhToan(dsTT);

        return hoadonDAO.saveHoaDonFull(hd);
    }

    @Override
    public List<HoaDonBanHang> searchByDate(Date date) {
        return hoadonDAO.timKiem(date);
    }
}
