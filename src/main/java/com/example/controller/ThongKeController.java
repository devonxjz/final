package com.example.controller;

import com.example.services.ThongKeService;
import com.example.view.ThongKeView;
import com.example.util.SwingWorkerUtils;

import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Controller báo cáo/thống kê — Async optimized.
 */
public class ThongKeController {
    private final ThongKeService service;
    private final ThongKeView view;

    public ThongKeController(ThongKeService service, ThongKeView view) {
        this.service = service;
        this.view = view;
        initController();
        loadDefaultStatistics();
    }

    private void initController() {
        view.btnSubmit.addActionListener(e -> updateStatistics());

        if (view.btnExportExcel != null) {
            view.btnExportExcel.addActionListener(e -> exportToExcel());
        }
    }

    private void loadDefaultStatistics() {
        updateStatistics();
    }

    // ====== ASYNC: Load toàn bộ thống kê ======
    private void updateStatistics() {
        String tuNgay = view.getTuNgay();
        String denNgay = view.getDenNgay();

        SwingWorkerUtils.runAsync(
            view.btnSubmit,
            () -> {
                // Chạy tất cả DB calls trên background thread
                double tongDoanhThu = service.getTongDoanhThu(tuNgay, denNgay);
                int tongDonHang = service.getTongDonHang(tuNgay, denNgay);
                double loiNhuan = service.getLoiNhuan(tuNgay, denNgay);
                Map<String, Integer> topSelling = service.getTopSellingProducts(tuNgay, denNgay);
                Map<String, Double> chartData = service.getRevenueByDay(tuNgay, denNgay);

                // Đóng gói kết quả
                return new Object[]{tongDoanhThu, tongDonHang, loiNhuan, topSelling, chartData};
            },
            result -> {
                // Cập nhật UI trên EDT
                double tongDoanhThu = (double) result[0];
                int tongDonHang = (int) result[1];
                double loiNhuan = (double) result[2];
                @SuppressWarnings("unchecked")
                Map<String, Integer> topSelling = (Map<String, Integer>) result[3];
                @SuppressWarnings("unchecked")
                Map<String, Double> chartData = (Map<String, Double>) result[4];

                view.lblTongDoanhThu.setText(String.format("%,.0f VNĐ", tongDoanhThu));
                view.lblTongDonHang.setText(String.valueOf(tongDonHang));
                view.lblLoiNhuan.setText(String.format("%,.0f VNĐ", loiNhuan));

                // Bảng sản phẩm bán chạy
                DefaultTableModel model = (DefaultTableModel) view.tableBestSeller.getModel();
                model.setRowCount(0);
                for (Map.Entry<String, Integer> entry : topSelling.entrySet()) {
                    model.addRow(new Object[]{ entry.getKey(), entry.getValue() });
                }

                // Biểu đồ doanh thu
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (Map.Entry<String, Double> entry : chartData.entrySet()) {
                    dataset.addValue(entry.getValue(), "Doanh thu", entry.getKey());
                }
                JFreeChart lineChart = ChartFactory.createLineChart(
                        "BIỂU ĐỒ DOANH THU", "Ngày", "Số tiền (VNĐ)", dataset);
                ChartPanel chartPanel = new ChartPanel(lineChart);
                view.panelChart.removeAll();
                view.panelChart.add(chartPanel);
                view.panelChart.validate();
            },
            ex -> JOptionPane.showMessageDialog(view, "Lỗi tải thống kê: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
        );
    }

    private void exportToExcel() {
        System.out.println("Đang xuất báo cáo Excel...");
    }
}