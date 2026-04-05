package com.example.services.impl;

import com.example.dao.HoaDonBanHangDAO;
import com.example.dao.KhachHangDAO;
import com.example.dao.NhanVienDAO;
import com.example.dao.SanPhamDAO;
import com.example.dto.ChiTietHDBHDTO;
import com.example.dto.HoaDonBanHangDTO;
import com.example.dto.ThanhToanDTO;
import com.example.entity.*;
import com.example.exception.ServiceException;
import com.example.services.HoaDonBanHangService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HoaDonBanHangServiceImpl implements HoaDonBanHangService {
    private final HoaDonBanHangDAO hoadonDAO;
    private final KhachHangDAO khachHangDAO;
    private final SanPhamDAO sanPhamDAO;
    private final NhanVienDAO nhanVienDAO; // BỔ SUNG: Cần NhanVienDAO để lấy thực thể NhanVien

    // BỔ SUNG: Cập nhật Constructor
    public HoaDonBanHangServiceImpl(HoaDonBanHangDAO hoadonDAO, KhachHangDAO khachHangDAO,
                                    SanPhamDAO sanPhamDAO, NhanVienDAO nhanVienDAO) {
        this.hoadonDAO = hoadonDAO;
        this.khachHangDAO = khachHangDAO;
        this.sanPhamDAO = sanPhamDAO;
        this.nhanVienDAO = nhanVienDAO;
    }

    private HoaDonBanHangDTO mapToHoaDonDTO(HoaDonBanHang h) {
        // BỔ SUNG: Lấy dữ liệu KH và NV an toàn
        Integer maKH = h.getKhachHang() != null ? h.getKhachHang().getMaKH() : null;
        String tenKH = h.getKhachHang() != null ? h.getKhachHang().getTenKH() : "Khách vãng lai";
        Integer maNV = h.getNhanVien() != null ? h.getNhanVien().getMaNV() : null;
        String tenNV = h.getNhanVien() != null ? h.getNhanVien().getTenNV() : "Hệ thống";

        return new HoaDonBanHangDTO(
            h.getMaHDBH(), maKH, tenKH, maNV, tenNV, h.getNgayTao(), h.getLoaiHD(),
            h.getTongTien(), h.getTienCoc(), h.getLaiSuat(),
            h.getThoiHanTG(), h.getTienGopHangThang(),
            h.getSoTienConLai(), h.getTrangThai()
        );
    }

    private ChiTietHDBHDTO mapToChiTietDTO(ChiTietHDBH c) {
        int maHDBH = c.getHoaDonBanHang() != null ? c.getHoaDonBanHang().getMaHDBH() : 0;
        int maSP = c.getSanPham() != null ? c.getSanPham().getMaSP() : 0;
        String tenSP = c.getSanPham() != null ? c.getSanPham().getTenSP() : "";
        return new ChiTietHDBHDTO(maHDBH, maSP, tenSP, c.getSoLuong(), c.getTongTien() / c.getSoLuong(), c.getTongTien());
    }

    private ThanhToanDTO mapToThanhToanDTO(ThanhToan t) {
        // SỬA: Đổi t.getHoaDonBanHang() thành t.getHoaDon()
        int maHDBH = t.getHoaDon() != null ? t.getHoaDon().getMaHDBH() : 0;
        int maKH = t.getKhachHang() != null ? t.getKhachHang().getMaKH() : 0;
        String tenKH = t.getKhachHang() != null ? t.getKhachHang().getTenKH() : "";
        return new ThanhToanDTO(t.getMaTT(), maHDBH, maKH, tenKH, t.getNgayTT(), t.getTienThanhToan(), t.getHinhThucTT());
    }

    @Override
    public List<HoaDonBanHangDTO> getAllHoaDon() {
        return hoadonDAO.getAllHDBH().stream().map(this::mapToHoaDonDTO).collect(Collectors.toList());
    }

    @Override
    public List<ChiTietHDBHDTO> getAllChiTiet(int maHD) {
        return hoadonDAO.getAllChiTiet(maHD).stream().map(this::mapToChiTietDTO).collect(Collectors.toList());
    }

    @Override
    public List<ThanhToanDTO> getAllThanhToan(int maHD) {
        return hoadonDAO.getAllThanhToan(maHD).stream().map(this::mapToThanhToanDTO).collect(Collectors.toList());
    }

    @Override
    public List<HoaDonBanHangDTO> searchByDate(Date date) {
        return hoadonDAO.timKiem(date).stream().map(this::mapToHoaDonDTO).collect(Collectors.toList());
    }

    // SỬA: Bổ sung int maNV vào hàm thanh toán
    @Override
    public boolean thanhToanHoaDon(int maKH, int maNV, String loaiHD, double tongTien,
                                   double laiSuat, int thoiHan, List<ChiTietHDBHDTO> gioHang) {

        KhachHang kh = khachHangDAO.getById(maKH);
        NhanVien nv = nhanVienDAO.getById(maNV); // Lấy nhân viên
        if (kh == null || nv == null) {
            throw new ServiceException("Khách hàng hoặc Nhân viên không tồn tại!");
        }

        HoaDonBanHang hd = new HoaDonBanHang();
        hd.setNgayTao(new Date());
        hd.setLoaiHD(loaiHD);
        hd.setTongTien(tongTien);
        hd.setTrangThai("Trả góp".equals(loaiHD) ? "Đang trả góp" : "Đã thanh toán");

        // BỔ SUNG: Gắn Khách và Nhân viên vào Hóa đơn
        hd.setKhachHang(kh);
        hd.setNhanVien(nv);

        if ("Trả góp".equals(loaiHD)) {
            hd.setLaiSuat(laiSuat);
            hd.setThoiHanTG(thoiHan);
            double tienCoc = tongTien * (laiSuat > 0 ? laiSuat / 100.0 : 0.3);
            hd.setTienCoc(tienCoc);
            double conLai = tongTien - tienCoc;
            hd.setSoTienConLai(conLai);
            hd.setTienGopHangThang(thoiHan > 0 ? conLai / thoiHan : 0);
        } else {
            hd.setTienCoc(tongTien);
            hd.setLaiSuat(0.0);
            hd.setThoiHanTG(0);
            hd.setTienGopHangThang(0.0);
            hd.setSoTienConLai(0.0);
        }

        List<ChiTietHDBH> dsCT = new ArrayList<>();
        for (ChiTietHDBHDTO ctDTO : gioHang) {
            SanPham sp = sanPhamDAO.getById(ctDTO.maSP());
            if (sp == null || sp.getSoLuongTrongKho() < ctDTO.soLuong()) {
                throw new ServiceException("Sản phẩm không đủ tồn kho: " + ctDTO.tenSP());
            }
            ChiTietHDBH ct = new ChiTietHDBH();
            ct.setHoaDonBanHang(hd);
            ct.setSanPham(sp);
            ct.setSoLuong(ctDTO.soLuong());
            ct.setTongTien(ctDTO.tongTien());
            dsCT.add(ct);
        }
        hd.setDanhSachChiTiet(dsCT);

        ThanhToan tt = new ThanhToan();
        tt.setHoaDon(hd); // SỬA: Đổi thành setHoaDon cho khớp với Entity ThanhToan
        tt.setKhachHang(kh);
        tt.setNhanVien(nv); // BỔ SUNG: Nhân viên thực hiện thu tiền cọc
        tt.setNgayTT(new Date());
        tt.setTienThanhToan(hd.getTienCoc());
        tt.setHinhThucTT("Tiền mặt");

        List<ThanhToan> dsTT = new ArrayList<>();
        dsTT.add(tt);
        hd.setDanhSachThanhToan(dsTT);

        boolean success = hoadonDAO.saveHoaDonFull(hd);
        if (!success) {
            throw new ServiceException("Lưu hóa đơn thất bại do lỗi CSDL");
        }
        return true;
    }

    @Override
    public boolean themHoaDonVaChiTietVaThanhToan(Date ngayTao, String loaiHD, double tongTien, int maKH, int maNV,
                                                  List<Map<Integer, Integer>> gioHang, String tenKH, String sdt, String diaChi,
                                                  String gioiTinh, String hinhThucTT, String trangThai) {
        return hoadonDAO.themHoaDonVaChiTietVaThanhToan(ngayTao, loaiHD, tongTien, maKH, maNV, gioHang, tenKH, sdt, diaChi, gioiTinh, hinhThucTT, trangThai);
    }
}
