package com.example.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Thực thể Hóa Đơn Bán Hàng.
 * Chứa thông tin hóa đơn khi bán laptop.
 */
@Entity
@Table(name = "HoaDonBanHang")
public class HoaDonBanHang {

    /** Mã hóa đơn (Khóa chính, tự tăng) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHDBH")
    private Integer maHDBH;

    /** Ngày tạo hóa đơn */
    @Temporal(TemporalType.DATE)
    @Column(name = "NgayTao", nullable = false)
    private Date ngayTao;

    /** Loại hóa đơn (Bán đứt / Trả góp) */
    @Column(name = "LoaiHD", nullable = false)
    private String loaiHD;

    /** Tổng tiền */
    @Column(name = "TongTien", nullable = false)
    private Double tongTien;

    /** Tiền đặt cọc */
    @Column(name = "TienCoc")
    private Double tienCoc;

    /** Lãi suất trả góp (%) */
    @Column(name = "LaiSuat")
    private Double laiSuat;

    /** Thời hạn trả góp (tháng) */
    @Column(name = "ThoiHanTG")
    private Integer thoiHanTG;

    /** Tiền góp hàng tháng */
    @Column(name = "TienGopHangThang")
    private Double tienGopHangThang;

    /** Số tiền còn lại */
    @Column(name = "SoTienConLai")
    private Double soTienConLai;

    /** Trạng thái hóa đơn */
    @Column(name = "TrangThai")
    private String trangThai;

    /** Danh sách chi tiết hóa đơn */
    @OneToMany(mappedBy = "hoaDonBanHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChiTietHDBH> danhSachChiTiet = new ArrayList<>();

    /** Danh sách thanh toán */
    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ThanhToan> danhSachThanhToan = new ArrayList<>();

    /** Khách hàng mua đơn hàng này */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaKH", nullable = false)
    private KhachHang khachHang;

    /** Nhân viên lập hóa đơn */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV", nullable = false)
    private NhanVien nhanVien;

    // ===== Constructors =====
    public HoaDonBanHang() {}

    // ===== Getters & Setters =====
    public Integer getMaHDBH() { return maHDBH; }
    public void setMaHDBH(Integer maHDBH) { this.maHDBH = maHDBH; }

    public Date getNgayTao() { return ngayTao; }
    public void setNgayTao(Date ngayTao) { this.ngayTao = ngayTao; }

    public String getLoaiHD() { return loaiHD; }
    public void setLoaiHD(String loaiHD) { this.loaiHD = loaiHD; }

    public Double getTongTien() { return tongTien; }
    public void setTongTien(Double tongTien) { this.tongTien = tongTien; }

    public Double getTienCoc() { return tienCoc; }
    public void setTienCoc(Double tienCoc) { this.tienCoc = tienCoc; }

    public Double getLaiSuat() { return laiSuat; }
    public void setLaiSuat(Double laiSuat) { this.laiSuat = laiSuat; }

    public Integer getThoiHanTG() { return thoiHanTG; }
    public void setThoiHanTG(Integer thoiHanTG) { this.thoiHanTG = thoiHanTG; }

    public Double getTienGopHangThang() { return tienGopHangThang; }
    public void setTienGopHangThang(Double tienGopHangThang) { this.tienGopHangThang = tienGopHangThang; }

    public Double getSoTienConLai() { return soTienConLai; }
    public void setSoTienConLai(Double soTienConLai) { this.soTienConLai = soTienConLai; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public List<ChiTietHDBH> getDanhSachChiTiet() { return danhSachChiTiet; }
    public void setDanhSachChiTiet(List<ChiTietHDBH> danhSachChiTiet) { this.danhSachChiTiet = danhSachChiTiet; }

    public List<ThanhToan> getDanhSachThanhToan() { return danhSachThanhToan; }
    public void setDanhSachThanhToan(List<ThanhToan> danhSachThanhToan) { this.danhSachThanhToan = danhSachThanhToan; }

    @Override
    public String toString() {
        return "HoaDonBanHang{maHDBH=" + maHDBH + ", tongTien=" + tongTien + "}";
    }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }
}
