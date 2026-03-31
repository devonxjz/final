package com.example.services;

import com.example.dto.DashboardStatusDTO;

/**
 * Service duy nhất cho toàn bộ dữ liệu Dashboard.
 */
public interface DashboardService {
    DashboardStatusDTO getDashboardStatus();
}
