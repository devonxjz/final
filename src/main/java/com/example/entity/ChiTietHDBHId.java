package com.example.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Lớp khóa phức hợp (Composite Key) cho bảng ChiTietHDBH.
 * Kết hợp MaHDBH và MaSP.
 */
public class ChiTietHDBHId implements Serializable {

    private Integer hoaDonBanHang;
    private Integer sanPham;

    public ChiTietHDBHId() {}

    public ChiTietHDBHId(Integer hoaDonBanHang, Integer sanPham) {
        this.hoaDonBanHang = hoaDonBanHang;
        this.sanPham = sanPham;
    }

    public Integer getHoaDonBanHang() { return hoaDonBanHang; }
    public void setHoaDonBanHang(Integer hoaDonBanHang) { this.hoaDonBanHang = hoaDonBanHang; }

    public Integer getSanPham() { return sanPham; }
    public void setSanPham(Integer sanPham) { this.sanPham = sanPham; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietHDBHId that = (ChiTietHDBHId) o;
        return Objects.equals(hoaDonBanHang, that.hoaDonBanHang) &&
               Objects.equals(sanPham, that.sanPham);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hoaDonBanHang, sanPham);
    }
}
