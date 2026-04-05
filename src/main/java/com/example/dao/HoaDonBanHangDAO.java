package com.example.dao;

import com.example.entity.ChiTietHDBH;
import com.example.entity.HoaDonBanHang;
import com.example.entity.KhachHang;
import com.example.entity.SanPham;
import com.example.entity.ThanhToan;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface HoaDonBanHangDAO {
    List<HoaDonBanHang> getAllHDBH();
    HoaDonBanHang getById(int maHD);
    boolean saveHoaDonFull(HoaDonBanHang hd);
    List<HoaDonBanHang> timKiemTheoTrangThai(String keyword);
    List<HoaDonBanHang> timKiem(Date date);
    List<ChiTietHDBH> getAllChiTiet(int maHD);
    List<ThanhToan> getAllThanhToan(int maHD);
    KhachHang timKiemKhachHangTheoSDT(String sdt);
    List<SanPham> getAllSanPham();

    // SỬA: Bổ sung tham số int maNV để biết nhân viên nào lập đơn hàng này
    boolean themHoaDonVaChiTietVaThanhToan(Date ngayTao, String loaiHD, double tongTien,
                                           int maKH, int maNV,
                                           List<Map<Integer, Integer>> gioHang, String tenKH, String sdt, String diaChi,
                                           String gioiTinh, String hinhThucTT, String trangThai);
}
