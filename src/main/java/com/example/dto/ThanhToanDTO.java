package com.example.dto;

import java.util.Date;

public record ThanhToanDTO(
        Integer maTT,
        Integer maHDBH,
        Integer maKH,
        String tenKH,
        Date ngayTT,
        Double tienThanhToan,
        String hinhThucTT
) {}
