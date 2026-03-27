package com.example.model;

import java.sql.Date;

public class HoaDonBanHang {
    private int maHDBH;
    private Date ngayTao;
    private String loaiHD;
    private double tongTien;
    private double tienCoc;
    private double laiSuat;
    private int thoiHanTG;
    private double tienGopHangThang;
    private double soTienConLai;
    private String trangThai;

    // Getter và Setter cho MaHDBH
    public int getMaHDBH() {
        return maHDBH;
    }

    public void setMaHDBH(int maHDBH) {
        this.maHDBH = maHDBH;
    }

    // Getter và Setter cho NgayTao
    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    // Getter và Setter cho LoaiHD
    public String getLoaiHD() {
        return loaiHD;
    }

    public void setLoaiHD(String loaiHD) {
        this.loaiHD = loaiHD;
    }

    // Getter và Setter cho TongTien
    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    // Getter và Setter cho TienCoc
    public double getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(double tienCoc) {
        this.tienCoc = tienCoc;
    }

    // Getter và Setter cho LaiSuat
    public double getLaiSuat() {
        return laiSuat;
    }

    public void setLaiSuat(double laiSuat) {
        this.laiSuat = laiSuat;
    }

    // Getter và Setter cho ThoiHanTG
    public int getThoiHanTG() {
        return thoiHanTG;
    }

    public void setThoiHanTG(int thoiHanTG) {
        this.thoiHanTG = thoiHanTG;
    }

    // Getter và Setter cho TienGopHangThang
    public double getTienGopHangThang() {
        return tienGopHangThang;
    }

    public void setTienGopHangThang(double tienGopHangThang) {
        this.tienGopHangThang = tienGopHangThang;
    }

    // Getter và Setter cho SoTienConLai
    public double getSoTienConLai() {
        return soTienConLai;
    }

    public void setSoTienConLai(double soTienConLai) {
        this.soTienConLai = soTienConLai;
    }

    // Getter và Setter cho TrangThai
    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}