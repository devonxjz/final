package com.example.services;

import com.example.dto.ChiTietHDBHDTO;
import com.example.entity.HoaDonBanHang;
import java.util.List;
import java.util.Map;

/**
 * Interface Service cho Hóa Đơn Bán Hàng
 */
public interface IHoaDonBanHangService {
    /** Lấy toàn bộ hóa đơn */
    List<HoaDonBanHang> getAllHoaDon();

    /** Thanh toán hóa đơn mới (từ giỏ hàng DTO) */
    boolean thanhToanHoaDon(int maKH, String loaiHD, double tongTien,
                            double laiSuat, int thoiHan, List<ChiTietHDBHDTO> gioHang);

    /** Tìm hóa đơn theo ngày */
    List<HoaDonBanHang> searchByDate(java.util.Date date);
}
