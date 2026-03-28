package com.example.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Thực thể đại diện cho đối tượng Khách Hàng.
 * Ánh xạ với bảng "KhachHang" trong cơ sở dữ liệu.
 */
@Entity
@Table(name = "KhachHang")
public class KhachHang {

    /** Mã khách hàng (Khóa chính, tự tăng) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKH")
    private Integer maKH;

    /** Tên khách hàng */
    @Column(name = "TenKH", nullable = false)
    private String tenKH;

    /** Giới tính */
    @Column(name = "GioiTinh", length = 10)
    private String gioiTinh;

    /** Địa chỉ liên hệ */
    @Column(name = "DiaChi")
    private String diaChi;

    /** Số điện thoại (Duy nhất) */
    @Column(name = "SDT", length = 20, unique = true)
    private String sdt;

    /** Email liên hệ (Duy nhất) */
    @Column(name = "Email", length = 100, unique = true)
    private String email;

    /** Danh sách các lần thanh toán của khách hàng này (Quan hệ 1-Nhiều) */
    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ThanhToan> danhSachThanhToan = new ArrayList<>();

    // ===== Constructors =====
    public KhachHang() {}

    public KhachHang(Integer maKH, String tenKH, String gioiTinh, String diaChi, String sdt, String email) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
    }

    // ===== Getters & Setters =====
    public Integer getMaKH() { return maKH; }
    public void setMaKH(Integer maKH) { this.maKH = maKH; }

    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<ThanhToan> getDanhSachThanhToan() { return danhSachThanhToan; }
    public void setDanhSachThanhToan(List<ThanhToan> danhSachThanhToan) { this.danhSachThanhToan = danhSachThanhToan; }

    @Override
    public String toString() {
        return "KhachHang{maKH=" + maKH + ", tenKH='" + tenKH + "'}";
    }
}
