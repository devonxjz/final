package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TaiKhoan")
public class TaiKhoan {

    @Id
    @Column(name = "TenDangNhap", length = 50)
    private String tenDangNhap;

    @Column(name = "MatKhau", nullable = false)
    private String matKhau;

    @Column(name = "VaiTro")
    private String vaiTro;

    @OneToOne
    @JoinColumn(name = "MaNV", unique = true)
    private NhanVien nhanVien;

    // Constructors
    public TaiKhoan() {}

    // Getters and Setters
    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }
}
