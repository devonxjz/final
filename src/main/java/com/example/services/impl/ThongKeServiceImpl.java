package com.example.services.impl;

import com.example.dao.ThongKeDAO;
import com.example.dao.impl.ThongKeDAOImpl;
import com.example.services.ThongKeService;

import java.util.Map;

/**
 * Triển khai IThongKeService — Xử lý nghiệp vụ Thống Kê
 */
public class ThongKeServiceImpl implements ThongKeService {
    private final ThongKeDAO dao;

    public ThongKeServiceImpl() {
        this.dao = new ThongKeDAOImpl();
    }

    @Override
    public double getTongDoanhThu(String tuNgay, String denNgay) {
        return dao.getTongDoanhThu(tuNgay, denNgay);
    }

    @Override
    public int getTongDonHang(String tuNgay, String denNgay) {
        return dao.getTongDonHang(tuNgay, denNgay);
    }

    @Override
    public double getLoiNhuan(String tuNgay, String denNgay) {
        return dao.getLoiNhuan(tuNgay, denNgay);
    }

    @Override
    public Map<String, Integer> getTopSellingProducts(String tuNgay, String denNgay) {
        return dao.getTopSellingProducts(tuNgay, denNgay);
    }

    @Override
    public Map<String, Double> getRevenueByDay(String tuNgay, String denNgay) {
        return dao.getRevenueByDay(tuNgay, denNgay);
    }
}
