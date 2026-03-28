package com.example.dto;

public record SanPhamDTO(
        Integer maSP,
        String loaiMay,
        String tenSP,
        String CPU,
        String GPU,
        Integer RAM,
        String oCung,
        Float kichThuocMH,
        String doPhanGiaiMH,
        Float canNang,
        Integer soLuongTrongKho,
        Double giaBan,
        Double giaNhap,
        Integer thoiGianBaoHanh,
        Integer maNCC,
        String tenNCC
) {}
