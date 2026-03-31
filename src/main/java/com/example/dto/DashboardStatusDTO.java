package com.example.dto;

/**
 * DTO tổng hợp thông tin Dashboard.
 * Một lần gọi duy nhất từ DashboardService.getDashboardStatus().
 */
public record DashboardStatusDTO(
        double revenueToday,
        int totalOrders,
        int productsInStock,
        int totalCustomers
) {}
