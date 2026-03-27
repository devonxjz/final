package com.example.model;

public class SanPham {

    private int maSP;
    private String loaiMay;
    private String tenSP;
    private String CPU;
    private String GPU;
    private int RAM;
    private String oCung;
    private float kichThuocMH;
    private String doPhanGiaiMH;
    private float canNang;
    private int soLuongTrongKho;
    private double giaBan;
    private double giaNhap;
    private int thoiGianBaoHanh;

    // Getter và Setter cho MaSP
    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    // Getter và Setter cho LoaiMay
    public String getLoaiMay() {
        return loaiMay;
    }

    public void setLoaiMay(String loaiMay) {
        this.loaiMay = loaiMay;
    }

    // Getter và Setter cho TenSP
    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    // Getter và Setter cho CPU
    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    // Getter và Setter cho GPU
    public String getGPU() {
        return GPU;
    }

    public void setGPU(String GPU) {
        this.GPU = GPU;
    }

    // Getter và Setter cho RAM
    public int getRAM() {
        return RAM;
    }

    public void setRAM(int RAM) {
        this.RAM = RAM;
    }

    // Getter và Setter cho OCung
    public String getOCung() {
        return oCung;
    }

    public void setOCung(String oCung) {
        this.oCung = oCung;
    }

    // Getter và Setter cho KichThuocMH
    public float getKichThuocMH() {
        return kichThuocMH;
    }

    public void setKichThuocMH(float kichThuocMH) {
        this.kichThuocMH = kichThuocMH;
    }

    // Getter và Setter cho DoPhanGiaiMH
    public String getDoPhanGiaiMH() {
        return doPhanGiaiMH;
    }

    public void setDoPhanGiaiMH(String doPhanGiaiMH) {
        this.doPhanGiaiMH = doPhanGiaiMH;
    }

    // Getter và Setter cho CanNang
    public float getCanNang() {
        return canNang;
    }

    public void setCanNang(float canNang) {
        this.canNang = canNang;
    }

    // Getter và Setter cho SoLuongTrongKho
    public int getSoLuongTrongKho() {
        return soLuongTrongKho;
    }

    public void setSoLuongTrongKho(int soLuongTrongKho) {
        this.soLuongTrongKho = soLuongTrongKho;
    }

    // Getter và Setter cho GiaBan
    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    // Getter và Setter cho GiaNhap
    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    // Getter và Setter cho ThoiGianBaoHanh
    public int getThoiGianBaoHanh() {
        return thoiGianBaoHanh;
    }

    public void setThoiGianBaoHanh(int thoiGianBaoHanh) {
        this.thoiGianBaoHanh = thoiGianBaoHanh;
    }
}