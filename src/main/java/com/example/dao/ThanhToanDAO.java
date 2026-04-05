package com.example.dao;

import com.example.entity.ThanhToan;
import java.util.List;
import java.util.Map;

public interface ThanhToanDAO {
    List<Map<String, Object>> getAllThanhToanAsMap();
    boolean themThanhToan(int maHDBH, int maKH, int maNV, java.util.Date ngayTT, double tienTT, String hinhThuc);
    boolean delete(int maTT);
    List<Map<String, Object>> getAllHoaDonDangGopAsMap();

    List<ThanhToan> findByHoaDon(int maHD);
    // Thêm hàm update
    boolean updateThanhToan(int maTT, java.util.Date ngayTT, double tienMoi, String hinhThucTT);
}
