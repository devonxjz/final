package com.example.dao;

import com.example.entity.ThanhToan;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ThanhToanDAO {
    List<Map<String, Object>> getAllThanhToanAsMap();
    boolean themThanhToan(int maHDBH, int maKH, Date ngayTT, double tienThanhToan, String hinhThucTT);
    List<Map<String, Object>> getAllHoaDonDangGopAsMap();
    List<ThanhToan> layTatCaThanhToan();
}