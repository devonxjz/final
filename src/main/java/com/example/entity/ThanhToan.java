package com.example.entity;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Thực thể Thanh Toán.
 * Lịch sử trả nợ hoặc tất toán đối với từng hóa đơn của khách.
 */
@Entity
@Table(name = "ThanhToan")
public class ThanhToan {

    /** Mã giao dịch thanh toán (Khóa chính) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTT")
    private Integer maTT;

    /** * Hóa đơn liên quan
     * (Đã đổi tên thành hoaDon để khớp với DAO/Service)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaHDBH", nullable = false)
    private HoaDonBanHang hoaDon;

    /** Khách hàng thực hiện thanh toán */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaKH", nullable = false)
    private KhachHang khachHang;

    /** * Nhân viên thực hiện giao dịch (BỔ SUNG)
     * Giúp hệ thống ghi nhận ai là người thu tiền
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV", nullable = false)
    private NhanVien nhanVien;

    /** Ngày thanh toán */
    @Temporal(TemporalType.DATE)
    @Column(name = "NgayTT", nullable = false)
    private Date ngayTT;

    /** Số tiền thanh toán */
    @Column(name = "TienThanhToan", nullable = false)
    private Double tienThanhToan;

    /** Hình thức thanh toán (Tiền mặt, Chuyển khoản...) */
    @Column(name = "HinhThucTT", length = 50)
    private String hinhThucTT;

    // ===== Constructors =====
    public ThanhToan() {}

    // ===== Getters & Setters =====
    public Integer getMaTT() { return maTT; }
    public void setMaTT(Integer maTT) { this.maTT = maTT; }

    public HoaDonBanHang getHoaDon() { return hoaDon; }
    public void setHoaDon(HoaDonBanHang hoaDon) { this.hoaDon = hoaDon; }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }

    public Date getNgayTT() { return ngayTT; }
    public void setNgayTT(Date ngayTT) { this.ngayTT = ngayTT; }

    public Double getTienThanhToan() { return tienThanhToan; }
    public void setTienThanhToan(Double tienThanhToan) { this.tienThanhToan = tienThanhToan; }

    public String getHinhThucTT() { return hinhThucTT; }
    public void setHinhThucTT(String hinhThucTT) { this.hinhThucTT = hinhThucTT; }

    @Override
    public String toString() {
        return "ThanhToan{maTT=" + maTT + ", tienThanhToan=" + tienThanhToan + "}";
    }
}
