package com.example.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NhanVien")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNV")
    private int maNV;

    @Column(name = "TenNV", nullable = false)
    private String tenNV;

    @Column(name = "NgaySinh")
    @Temporal(TemporalType.DATE)
    private Date ngaySinh;

    @Column(name = "GioiTinh")
    private String gioiTinh;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "SDT", unique = true)
    private String sdt;

    @Column(name = "ChucVu")
    private String chucVu;

    @Column(name = "Luong")
    private Double luong;

    // Constructors
    public NhanVien() {}

    // Getters and Setters
    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }

    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }

    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSDT() { return sdt; }
    public void setSDT(String sdt) { this.sdt = sdt; }

    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }

    public Double getLuong() { return luong; }
    public void setLuong(Double luong) { this.luong = luong; }
}
