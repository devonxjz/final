package com.example.services.impl;

import com.example.dao.ThanhToanDAO;
import com.example.dao.impl.ThanhToanDAOImpl;
import com.example.services.IThanhToanService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Triển khai IThanhToanService — Xử lý nghiệp vụ Thanh Toán
 */
public class ThanhToanServiceImpl implements IThanhToanService {
    private final ThanhToanDAO dao;

    public ThanhToanServiceImpl() {
        this.dao = new ThanhToanDAOImpl();
    }

    @Override
    public List<Map<String, Object>> getAllThanhToanAsMap() {
        return dao.getAllThanhToanAsMap();
    }

    @Override
    public boolean themThanhToan(int maHDBH, int maKH, Date ngayTT,
                                  double tienThanhToan, String hinhThucTT) {
        return dao.themThanhToan(maHDBH, maKH, ngayTT, tienThanhToan, hinhThucTT);
    }

    @Override
    public List<Map<String, Object>> getAllHoaDonDangGop() {
        return dao.getAllHoaDonDangGopAsMap();
    }
}
