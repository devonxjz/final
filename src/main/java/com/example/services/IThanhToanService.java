package com.example.services;

import java.util.List;
import java.util.Map;

/**
 * Interface Service cho Thanh Toán
 */
public interface IThanhToanService {
    /** Lấy toàn bộ thanh toán dạng Map */
    List<Map<String, Object>> getAllThanhToanAsMap();

    /** Thêm thanh toán mới */
    boolean themThanhToan(int maHDBH, int maKH, java.util.Date ngayTT,
                          double tienThanhToan, String hinhThucTT);

    /** Lấy hóa đơn đang trả góp */
    List<Map<String, Object>> getAllHoaDonDangGop();
}
