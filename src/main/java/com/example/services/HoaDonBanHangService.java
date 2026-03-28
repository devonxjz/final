package com.example.services;

import com.example.dao.HoaDonBanHangDAO;
import com.example.dao.KhachHangDAO;
import com.example.dao.SanPhamDAO;
import com.example.dto.ChiTietHDBHDTO;
import com.example.entity.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonBanHangService {
    private final HoaDonBanHangDAO hoadoDao;
    private final KhachHangDAO khachHangDAO;
    private final SanPhamDAO sanPhamDAO;

    public HoaDonBanHangService() {
        this.hoadoDao = new HoaDonBanHangDAO();
        this.khachHangDAO = new KhachHangDAO();
        this.sanPhamDAO = new SanPhamDAO();
    }

    /**
     * Xử lý lưu hóa đơn từ các DTO (Giỏ hàng) chuyển xuống Entity JPA
     */
    public boolean thanhToanHoaDon(int maKH, String loaiHD, double tongTien, double laiSuat, int thoiHan, List<ChiTietHDBHDTO> gioHang) {
        KhachHang kh = khachHangDAO.getById(maKH);
        if (kh == null) return false;

        // Tạo Hóa Đơn Trống
        HoaDonBanHang hd = new HoaDonBanHang();
        hd.setNgayTao(new Date());
        hd.setLoaiHD(loaiHD);
        hd.setTongTien(tongTien);
        hd.setTrangThai("Trả góp".equals(loaiHD) ? "Đang trả góp" : "Đã thanh toán");

        if ("Trả góp".equals(loaiHD)) {
            hd.setLaiSuat(laiSuat);
            hd.setThoiHanTG(thoiHan);
            double tienCoc = tongTien * (laiSuat > 0 ? laiSuat / 100.0 : 0.3); // Ví dụ mặc định cọc 30% nếu không cấu hình
            hd.setTienCoc(tienCoc);
            double conLai = tongTien - tienCoc;
            hd.setSoTienConLai(conLai);
            hd.setTienGopHangThang(thoiHan > 0 ? conLai / thoiHan : 0);
        } else {
            hd.setTienCoc(tongTien);
            hd.setLaiSuat(0.0);
            hd.setThoiHanTG(0);
            hd.setTienGopHangThang(0.0);
            hd.setSoTienConLai(0.0);
        }

        // Tạo danh sách Chi tiết
        List<ChiTietHDBH> dsCT = new ArrayList<>();
        for (ChiTietHDBHDTO ctDTO : gioHang) {
            SanPham sp = sanPhamDAO.getById(ctDTO.maSP());
            if (sp == null || sp.getSoLuongTrongKho() < ctDTO.soLuong()) {
                throw new IllegalArgumentException("Sản phẩm không đủ tồn kho: " + ctDTO.tenSP());
            }
            ChiTietHDBH ct = new ChiTietHDBH();
            ct.setHoaDonBanHang(hd);
            ct.setSanPham(sp);
            ct.setSoLuong(ctDTO.soLuong());
            ct.setTongTien(ctDTO.tongTien());
            dsCT.add(ct);
        }
        hd.setDanhSachChiTiet(dsCT);

        // Tạo 1 dòng thanh toán đầu tiên (Tiền cọc hoặc trả đứt)
        ThanhToan tt = new ThanhToan();
        tt.setHoaDonBanHang(hd);
        tt.setKhachHang(kh);
        tt.setNgayTT(new Date());
        tt.setTienThanhToan(hd.getTienCoc());
        tt.setHinhThucTT("Tiền mặt"); // Có thể binding từ combo box UI sau
        List<ThanhToan> dsTT = new ArrayList<>();
        dsTT.add(tt);
        hd.setDanhSachThanhToan(dsTT);

        // Gọi DAO để save toàn bộ
        return hoadoDao.saveHoaDonFull(hd);
    }
}
