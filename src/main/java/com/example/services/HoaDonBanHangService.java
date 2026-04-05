package com.example.services;

import com.example.dto.ChiTietHDBHDTO;
import com.example.dto.HoaDonBanHangDTO;
import com.example.dto.ThanhToanDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Interface Service cho Hóa Đơn Bán Hàng
 * Trả về DTO thay vì Entity
 */
public interface HoaDonBanHangService {
    /** Lấy toàn bộ hóa đơn */
    List<HoaDonBanHangDTO> getAllHoaDon();

    /** Lấy chi tiết hóa đơn theo mã hóa đơn */
    List<ChiTietHDBHDTO> getAllChiTiet(int maHD);

    /** Lấy toàn bộ thanh toán theo mã hóa đơn */
    List<ThanhToanDTO> getAllThanhToan(int maHD);

    /** Thanh toán trực tiếp */
    boolean thanhToanHoaDon(int maHD, int maNV, String loaiHD, double tongTien, double laiSuat, int thoiHan, List<ChiTietHDBHDTO> gioHang);

    /** Lưu hóa đơn và chi tiết vào CSDL */
    boolean themHoaDonVaChiTietVaThanhToan(Date ngayTao, String loaiHD, double tongTien, int maNV, int maKH,
            List<Map<Integer, Integer>> gioHang, String tenKH, String sdt, String diaChi,
            String gioiTinh, String hinhThucTT, String trangThai);

    /** Tìm hóa đơn theo ngày */
    List<HoaDonBanHangDTO> searchByDate(Date date);
}
