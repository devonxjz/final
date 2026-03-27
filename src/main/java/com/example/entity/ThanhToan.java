package com.example.model;

import java.sql.Date;

public class ThanhToan {
    private int maTT;
    private int maHDBH;
    private int maKH;
    private Date ngayTT;
    private double tienThanhToan;
    private String hinhThucTT;

    // Getter và Setter cho MaTT
    public int getMaTT() {
        return maTT;
    }

    public void setMaTT(int maTT) {
        this.maTT = maTT;
    }

    // Getter và Setter cho MaHDBH
    public int getMaHDBH() {
        return maHDBH;
    }

    public void setMaHDBH(int maHDBH) {
        this.maHDBH = maHDBH;
    }

    // Getter và Setter cho MaKH
    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    // Getter và Setter cho NgayTT
    public Date getNgayTT() {
        return ngayTT;
    }

    public void setNgayTT(Date ngayTT) {
        this.ngayTT = ngayTT;
    }

    // Getter và Setter cho TienThanhToan
    public double getTienThanhToan() {
        return tienThanhToan;
    }

    public void setTienThanhToan(double tienThanhToan) {
        this.tienThanhToan = tienThanhToan;
    }

    // Getter và Setter cho HinhThucTT
    public String getHinhThucTT() {
        return hinhThucTT;
    }

    public void setHinhThucTT(String hinhThucTT) {
        this.hinhThucTT = hinhThucTT;
    }
}