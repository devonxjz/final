package com.example.dto;

import java.util.Date;

public record HoaDonBanHangDTO(
        Integer maHDBH,
        Date ngayTao,
        String loaiHD,
        Double tongTien,
        Double tienCoc,
        Double laiSuat,
        Integer thoiHanTG,
        Double tienGopHangThang,
        Double soTienConLai,
        String trangThai
) {}
