package com.example.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Thực thể đại diện cho đối tượng Nhà Cung Cấp.
 * Ánh xạ với bảng "NhaCungCap" trong CSDL.
 * Quan hệ 1-Nhiều với SanPham (một NCC có thể cung cấp nhiều sản phẩm).
 */
@Entity
@Table(name = "NhaCungCap")
public class NhaCungCap {

    /** Mã nhà cung cấp (Khóa chính, tự tăng) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNCC")
    private Integer maNCC;

    /** Tên nhà cung cấp */
    @Column(name = "TenNCC", nullable = false, length = 255)
    private String tenNCC;

    /** Địa chỉ trụ sở */
    @Column(name = "DiaChi", length = 255)
    private String diaChi;

    /** Số điện thoại liên hệ */
    @Column(name = "SDT", length = 20)
    private String sdt;

    /**
     * Danh sách sản phẩm do nhà cung cấp này cung cấp.
     * Quan hệ 1-Nhiều, cascade ALL: xóa NCC sẽ xóa luôn sản phẩm liên quan.
     * mappedBy trỏ đến field "nhaCungCap" trong SanPham.
     */
    @OneToMany(mappedBy = "nhaCungCap", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SanPham> danhSachSanPham = new ArrayList<>();

    // ===== Constructors =====

    public NhaCungCap() {}

    public NhaCungCap(Integer maNCC, String tenNCC, String diaChi, String sdt) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }

    // ===== Getters & Setters =====

    public Integer getMaNCC() { return maNCC; }
    public void setMaNCC(Integer maNCC) { this.maNCC = maNCC; }

    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public List<SanPham> getDanhSachSanPham() { return danhSachSanPham; }
    public void setDanhSachSanPham(List<SanPham> danhSachSanPham) { this.danhSachSanPham = danhSachSanPham; }

    @Override
    public String toString() {
        return "NhaCungCap{maNCC=" + maNCC + ", tenNCC='" + tenNCC + "'}";
    }
}
