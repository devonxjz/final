package com.example.entity;

import jakarta.persistence.*;

/**
 * Thực thể Chi Tiết Hóa Đơn Bán Hàng.
 * Bảng trung gian Nhiều-Nhiều giữa HoaDonBanHang và SanPham.
 */
@Entity
@Table(name = "ChiTietHDBH")
@IdClass(ChiTietHDBHId.class)
public class ChiTietHDBH {

    /** Liên kết đến Hóa đơn (phần khóa phức hợp) */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaHDBH", nullable = false)
    private HoaDonBanHang hoaDonBanHang;

    /** Liên kết đến Sản phẩm (phần khóa phức hợp) */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSP", nullable = false)
    private SanPham sanPham;

    /** Số lượng mua */
    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong;

    /** Tổng tiền cho dòng này */
    @Column(name = "TongTien", nullable = false)
    private Double tongTien;

    // ===== Constructors =====
    public ChiTietHDBH() {}

    // ===== Getters & Setters =====
    public HoaDonBanHang getHoaDonBanHang() { return hoaDonBanHang; }
    public void setHoaDonBanHang(HoaDonBanHang hoaDonBanHang) { this.hoaDonBanHang = hoaDonBanHang; }

    public SanPham getSanPham() { return sanPham; }
    public void setSanPham(SanPham sanPham) { this.sanPham = sanPham; }

    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }

    public Double getTongTien() { return tongTien; }
    public void setTongTien(Double tongTien) { this.tongTien = tongTien; }
}
