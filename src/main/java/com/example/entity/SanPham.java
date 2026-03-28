package com.example.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Thực thể Sản Phẩm (Laptop).
 * Lưu thông tin đầy đủ về một dòng máy tính xách tay bao gồm mã, cấu hình, giá.
 */
@Entity
@Table(name = "SanPham")
public class SanPham {

    /** Mã sản phẩm (Khóa chính, tự tăng) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaSP")
    private Integer maSP;

    /** Loại máy */
    @Column(name = "LoaiMay")
    private String loaiMay;

    /** Tên đầy đủ của sản phẩm */
    @Column(name = "TenSP", nullable = false)
    private String tenSP;

    /** Vi xử lý trung tâm (CPU) */
    @Column(name = "CPU")
    private String CPU;

    /** Card đồ họa (GPU) */
    @Column(name = "GPU")
    private String GPU;

    /** Bộ nhớ RAM (GB) */
    @Column(name = "RAM")
    private Integer RAM;

    /** Ổ cứng SSD/HDD */
    @Column(name = "OCung")
    private String oCung;

    /** Kích thước màn hình (inch) */
    @Column(name = "KichThuocMH")
    private Float kichThuocMH;

    /** Độ phân giải màn hình */
    @Column(name = "DoPhanGiaiMH")
    private String doPhanGiaiMH;

    /** Cân nặng (kg) */
    @Column(name = "CanNang")
    private Float canNang;

    /** Số lượng tồn kho */
    @Column(name = "SLTrongKho", nullable = false)
    private Integer soLuongTrongKho;

    /** Giá bán (VND) */
    @Column(name = "GiaBan", nullable = false)
    private Double giaBan;

    /** Giá nhập (VND) */
    @Column(name = "GiaNhap")
    private Double giaNhap;

    /** Thời gian bảo hành (tháng) */
    @Column(name = "ThoiGianBaoHanh")
    private Integer thoiGianBaoHanh;

    /** Liên kết tới nhà cung cấp (Quan hệ Nhiều-1) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNCC")
    private NhaCungCap nhaCungCap;

    /** Danh sách chi tiết hóa đơn chứa sản phẩm này */
    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChiTietHDBH> danhSachChiTietHoaDon = new ArrayList<>();

    // ===== Constructors =====
    public SanPham() {}

    // ===== Getters & Setters =====
    public Integer getMaSP() { return maSP; }
    public void setMaSP(Integer maSP) { this.maSP = maSP; }

    public String getLoaiMay() { return loaiMay; }
    public void setLoaiMay(String loaiMay) { this.loaiMay = loaiMay; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public String getCPU() { return CPU; }
    public void setCPU(String CPU) { this.CPU = CPU; }

    public String getGPU() { return GPU; }
    public void setGPU(String GPU) { this.GPU = GPU; }

    public Integer getRAM() { return RAM; }
    public void setRAM(Integer RAM) { this.RAM = RAM; }

    public String getOCung() { return oCung; }
    public void setOCung(String oCung) { this.oCung = oCung; }

    public Float getKichThuocMH() { return kichThuocMH; }
    public void setKichThuocMH(Float kichThuocMH) { this.kichThuocMH = kichThuocMH; }

    public String getDoPhanGiaiMH() { return doPhanGiaiMH; }
    public void setDoPhanGiaiMH(String doPhanGiaiMH) { this.doPhanGiaiMH = doPhanGiaiMH; }

    public Float getCanNang() { return canNang; }
    public void setCanNang(Float canNang) { this.canNang = canNang; }

    public Integer getSoLuongTrongKho() { return soLuongTrongKho; }
    public void setSoLuongTrongKho(Integer soLuongTrongKho) { this.soLuongTrongKho = soLuongTrongKho; }

    public Double getGiaBan() { return giaBan; }
    public void setGiaBan(Double giaBan) { this.giaBan = giaBan; }

    public Double getGiaNhap() { return giaNhap; }
    public void setGiaNhap(Double giaNhap) { this.giaNhap = giaNhap; }

    public Integer getThoiGianBaoHanh() { return thoiGianBaoHanh; }
    public void setThoiGianBaoHanh(Integer thoiGianBaoHanh) { this.thoiGianBaoHanh = thoiGianBaoHanh; }

    public NhaCungCap getNhaCungCap() { return nhaCungCap; }
    public void setNhaCungCap(NhaCungCap nhaCungCap) { this.nhaCungCap = nhaCungCap; }

    public List<ChiTietHDBH> getDanhSachChiTietHoaDon() { return danhSachChiTietHoaDon; }
    public void setDanhSachChiTietHoaDon(List<ChiTietHDBH> danhSachChiTietHoaDon) { this.danhSachChiTietHoaDon = danhSachChiTietHoaDon; }

    @Override
    public String toString() {
        return "SanPham{maSP=" + maSP + ", tenSP='" + tenSP + "'}";
    }
}
