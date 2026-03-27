package com.example.dao;

import com.example.model.HoaDonBanHang;
import com.example.model.ChiTietHDBH;
import com.example.model.ThanhToan;
import com.example.model.KhachHang;
import com.example.model.SanPham;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HoaDonBanHangDAO {
    private Connection conn;

    public HoaDonBanHangDAO() {
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu!");
            }
        } catch (Exception e) {
            System.err.println("Lỗi dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Lấy tất cả hóa đơn bán hàng
    public List<HoaDonBanHang> getAllHDBH() {
        List<HoaDonBanHang> ds = new ArrayList<>();
        if (conn == null) {
            System.err.println("Không có kết nối để lấy danh sách hóa đơn!");
            return ds;
        }
        String sql = "SELECT * FROM HoaDonBanHang";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                HoaDonBanHang hd = new HoaDonBanHang();
                hd.setMaHDBH(rs.getInt("MaHDBH"));
                hd.setNgayTao(rs.getDate("NgayTao"));
                hd.setLoaiHD(rs.getString("LoaiHD"));
                hd.setTongTien(rs.getDouble("TongTien"));
                hd.setTienCoc(rs.getDouble("TienCoc"));
                hd.setLaiSuat(rs.getDouble("LaiSuat"));
                hd.setThoiHanTG(rs.getInt("ThoiHanTG"));
                hd.setTienGopHangThang(rs.getDouble("TienGopHangThang"));
                hd.setSoTienConLai(rs.getDouble("SoTienConLai"));
                hd.setTrangThai(rs.getString("TrangThai"));
                ds.add(hd);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getAllHDBH: " + e.getMessage());
            e.printStackTrace();
        }
        return ds;
    }

    // Lấy chi tiết hóa đơn bán hàng
    public List<ChiTietHDBH> getAllChiTiet(int maHDBH) {
        List<ChiTietHDBH> list = new ArrayList<>();
        if (conn == null) return list;
        String sql = "SELECT MaHDBH, MaSP, SoLuong, TongTien FROM ChiTietHDBH WHERE MaHDBH = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maHDBH);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ChiTietHDBH ct = new ChiTietHDBH();
                ct.setMaHDBH(rs.getInt("MaHDBH"));
                ct.setMaSP(rs.getInt("MaSP"));
                ct.setSoLuong(rs.getInt("SoLuong"));
                ct.setTongTien(rs.getDouble("TongTien"));
                list.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy thông tin thanh toán
    public List<ThanhToan> getAllThanhToan(int maHDBH) {
        List<ThanhToan> list = new ArrayList<>();
        if (conn == null) return list;
        String sql = "SELECT MaTT, MaHDBH, MaKH, NgayTT, TienThanhToan, HinhThucTT FROM ThanhToan WHERE MaHDBH = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maHDBH);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ThanhToan tt = new ThanhToan();
                tt.setMaTT(rs.getInt("MaTT"));
                tt.setMaHDBH(rs.getInt("MaHDBH"));
                tt.setMaKH(rs.getInt("MaKH"));
                tt.setNgayTT(rs.getDate("NgayTT"));
                tt.setTienThanhToan(rs.getDouble("TienThanhToan"));
                tt.setHinhThucTT(rs.getString("HinhThucTT"));
                list.add(tt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tìm kiếm hóa đơn theo ngày
    public List<HoaDonBanHang> timKiem(java.util.Date ngay) {
        List<HoaDonBanHang> list = new ArrayList<>();
        if (conn == null) return list;
        String sql = "SELECT * FROM HoaDonBanHang WHERE DATE(NgayTao) = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(ngay.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HoaDonBanHang hd = new HoaDonBanHang();
                hd.setMaHDBH(rs.getInt("MaHDBH"));
                hd.setNgayTao(rs.getDate("NgayTao"));
                hd.setLoaiHD(rs.getString("LoaiHD"));
                hd.setTongTien(rs.getDouble("TongTien"));
                hd.setTienCoc(rs.getDouble("TienCoc"));
                hd.setLaiSuat(rs.getDouble("LaiSuat"));
                hd.setThoiHanTG(rs.getInt("ThoiHanTG"));
                hd.setTienGopHangThang(rs.getDouble("TienGopHangThang"));
                hd.setSoTienConLai(rs.getDouble("SoTienConLai"));
                hd.setTrangThai(rs.getString("TrangThai"));
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm hóa đơn phức hợp (Transaction)
    public boolean themHoaDonVaChiTietVaThanhToan(java.util.Date ngayTao, String loaiHD,
                                                  double laiSuat, int thoiHanTG,
                                                  List<Map<Integer, Integer>> chiTietList, String tenKH, String gioiTinh,
                                                  String diaChi, String sdt, String hinhThucTT) {
        if (conn == null) return false;
        try {
            conn.setAutoCommit(false);

            if (chiTietList == null || chiTietList.isEmpty()) {
                System.err.println("Danh sách sản phẩm trống!");
                return false;
            }

            // Kiểm tra/Thêm khách hàng
            KhachHang khachHang = null;
            String sqlCheckKH = "SELECT * FROM KhachHang WHERE SDT = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlCheckKH)) {
                stmt.setString(1, sdt);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    khachHang = new KhachHang();
                    khachHang.setMaKH(rs.getInt("MaKH"));
                }
            }

            if (khachHang == null) {
                String sqlInsKH = "INSERT INTO KhachHang (TenKH, GioiTinh, DiaChi, SDT) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sqlInsKH, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, tenKH);
                    stmt.setString(2, gioiTinh);
                    stmt.setString(3, diaChi);
                    stmt.setString(4, sdt);
                    stmt.executeUpdate();
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        khachHang = new KhachHang();
                        khachHang.setMaKH(rs.getInt(1));
                    }
                }
            }

            // Xử lý danh sách sản phẩm
            List<Integer> maSPs = new ArrayList<>();
            for (Map<Integer, Integer> map : chiTietList) {
                maSPs.add(map.keySet().iterator().next());
            }

            List<SanPham> sanPhams = new ArrayList<>();
            if (!maSPs.isEmpty()) {
                StringBuilder sqlSP = new StringBuilder("SELECT * FROM SanPham WHERE MaSP IN (");
                for (int i = 0; i < maSPs.size(); i++) {
                    sqlSP.append("?");
                    if (i < maSPs.size() - 1) sqlSP.append(",");
                }
                sqlSP.append(")");
                try (PreparedStatement stmt = conn.prepareStatement(sqlSP.toString())) {
                    for (int i = 0; i < maSPs.size(); i++) {
                        stmt.setInt(i + 1, maSPs.get(i));
                    }
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        SanPham sp = new SanPham();
                        sp.setMaSP(rs.getInt("MaSP"));
                        sp.setGiaBan(rs.getDouble("GiaBan"));
                        sp.setSoLuongTrongKho(rs.getInt("SLTrongKho"));
                        sanPhams.add(sp);
                    }
                }
            }

            // Tính tổng tiền
            double tongTien = 0;
            for (Map<Integer, Integer> ct : chiTietList) {
                int id = ct.keySet().iterator().next();
                int qty = ct.get(id);
                SanPham spFound = sanPhams.stream().filter(s -> s.getMaSP() == id).findFirst()
                        .orElseThrow(() -> new SQLException("Không tìm thấy sản phẩm ID: " + id));
                tongTien += spFound.getGiaBan() * qty;
            }

            // Tính toán tài chính
            double tienCoc, tienGop, conLai;
            String status;
            if ("Trả góp".equals(loaiHD)) {
                if (thoiHanTG <= 0) throw new SQLException("Thời hạn trả góp không hợp lệ");
                tienCoc = laiSuat * tongTien;
                conLai = tongTien - tienCoc;
                tienGop = conLai / thoiHanTG;
                status = "Đang trả góp";
            } else {
                tienCoc = tongTien;
                tienGop = 0;
                conLai = 0;
                status = "Đã thanh toán";
            }

            // Insert Hóa đơn
            String sqlInsHD = "INSERT INTO HoaDonBanHang (NgayTao, LoaiHD, TongTien, TienCoc, LaiSuat, ThoiHanTG, TienGopHangThang, SoTienConLai, TrangThai) VALUES (?,?,?,?,?,?,?,?,?)";
            int maHDBH;
            try (PreparedStatement stmt = conn.prepareStatement(sqlInsHD, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setDate(1, new java.sql.Date(ngayTao.getTime()));
                stmt.setString(2, loaiHD);
                stmt.setDouble(3, tongTien);
                stmt.setDouble(4, tienCoc);
                stmt.setDouble(5, laiSuat);
                stmt.setInt(6, thoiHanTG);
                stmt.setDouble(7, tienGop);
                stmt.setDouble(8, conLai);
                stmt.setString(9, status);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) maHDBH = rs.getInt(1);
                else throw new SQLException("Lỗi tạo hóa đơn");
            }

            // Chi tiết hóa đơn và Cập nhật kho
            String sqlInsCT = "INSERT INTO ChiTietHDBH (MaHDBH, MaSP, SoLuong, TongTien) VALUES (?,?,?,?)";
            String sqlUpdStock = "UPDATE SanPham SET SLTrongKho = ? WHERE MaSP = ?";
            for (Map<Integer, Integer> ct : chiTietList) {
                int id = ct.keySet().iterator().next();
                int qty = ct.get(id);
                SanPham sp = sanPhams.stream().filter(s -> s.getMaSP() == id).findFirst().get();

                try (PreparedStatement stmt = conn.prepareStatement(sqlInsCT)) {
                    stmt.setInt(1, maHDBH);
                    stmt.setInt(2, id);
                    stmt.setInt(3, qty);
                    stmt.setDouble(4, sp.getGiaBan() * qty);
                    stmt.executeUpdate();
                }

                int newStock = sp.getSoLuongTrongKho() - qty;
                if (newStock < 0) throw new SQLException("Kho không đủ hàng cho sản phẩm: " + id);
                try (PreparedStatement stmt = conn.prepareStatement(sqlUpdStock)) {
                    stmt.setInt(1, newStock);
                    stmt.setInt(2, id);
                    stmt.executeUpdate();
                }
            }

            // Thanh toán
            String sqlInsTT = "INSERT INTO ThanhToan (MaHDBH, MaKH, NgayTT, TienThanhToan, HinhThucTT) VALUES (?,?,?,?,?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlInsTT)) {
                stmt.setInt(1, maHDBH);
                stmt.setInt(2, khachHang.getMaKH());
                stmt.setDate(3, new java.sql.Date(ngayTao.getTime()));
                stmt.setDouble(4, tienCoc);
                stmt.setString(5, hinhThucTT);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public List<SanPham> getAllSanPham() {
        List<SanPham> list = new ArrayList<>();
        if (conn == null) return list;
        String sql = "SELECT * FROM SanPham";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getInt("MaSP"));
                sp.setTenSP(rs.getString("TenSP"));
                sp.setGiaBan(rs.getDouble("GiaBan"));
                sp.setSoLuongTrongKho(rs.getInt("SLTrongKho"));
                list.add(sp);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<SanPham> timKiemSanPham(String keyword) {
        List<SanPham> list = new ArrayList<>();
        if (conn == null) return list;
        String sql = "SELECT * FROM SanPham WHERE TenSP LIKE ? OR MaSP LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getInt("MaSP"));
                sp.setTenSP(rs.getString("TenSP"));
                sp.setGiaBan(rs.getDouble("GiaBan"));
                list.add(sp);
            }
        } catch (SQLException e) { e.printStackTrace(); }git
        return list;
    }

    public List<KhachHang> timKiemKhachHangTheoSDT(String sdt) {
        List<KhachHang> list = new ArrayList<>();
        if (conn == null) return list;
        String sql = "SELECT * FROM KhachHang WHERE SDT = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sdt);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setSdt(rs.getString("SDT"));
                list.add(kh);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}