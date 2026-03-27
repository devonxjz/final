package com.example.model;

public class NhaCungCap {
    private int maNCC;
    private String tenNCC;
    private String diaChi;
    private String sdt;

    // Getter cho MaNCC
    public int getMaNCC() {
        return maNCC;
    }

    // Setter cho MaNCC
    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    // Getter cho TenNCC
    public String getTenNCC() {
        return tenNCC;
    }

    // Setter cho TenNCC
    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    // Getter cho DiaChi
    public String getDiaChi() {
        return diaChi;
    }

    // Setter cho DiaChi
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    // Getter cho Sdt
    public String getSdt() {
        return sdt;
    }

    // Setter cho Sdt
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}