package com.example.dto;

import java.util.Date;

public record HoaDonBanHangDTO(
    Integer maHDBH,
    Integer maKH,       // Bổ sung: Mã khách hàng
    String tenKH,       // Bổ sung: Tên khách hàng
    Integer maNV,       // Bổ sung: Mã nhân viên
    String tenNV,       // Bổ sung: Tên nhân viên
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
