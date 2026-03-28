package com.example.services;

import java.util.Map;

/**
 * Interface Service cho Thống Kê
 */
public interface IThongKeService {
    double getTongDoanhThu(String tuNgay, String denNgay);
    int getTongDonHang(String tuNgay, String denNgay);
    double getLoiNhuan(String tuNgay, String denNgay);
    Map<String, Integer> getTopSellingProducts(String tuNgay, String denNgay);
    Map<String, Double> getRevenueByDay(String tuNgay, String denNgay);
}
