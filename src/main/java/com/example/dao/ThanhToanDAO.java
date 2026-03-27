package com.example.dao;

import com.example.entity.ThanhToan;
import com.example.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThanhToanDAO {
    private Connection conn;

    public ThanhToanDAO() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy tất cả thanh toán kèm tên khách hàng
    public List<Map<String, Object>> getAllThanhToanAsMap() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT tt.*, kh.tenKH FROM ThanhToan tt " +
                "JOIN KhachHang kh ON tt.maKH = kh.maKH";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("maTT", rs.getInt("maTT"));
                row.put("maHDBH", rs.getInt("maHDBH"));
                row.put("maKH", rs.getInt("maKH"));
                row.put("ngayTT", rs.getDate("ngayTT"));
                row.put("tienThanhToan", rs.getDouble("tienThanhToan"));
                row.put("hinhThucTT", rs.getString("hinhThucTT"));
                row.put("tenKH", rs.getString("tenKH"));
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm thanh toán mới
    public boolean themThanhToan(int maHDBH, int maKH, Date ngayTT, double tienThanhToan, String hinhThucThanhToan) {
        String sql = "INSERT INTO ThanhToan (maHDBH, maKH, ngayTT, tienThanhToan, hinhThucTT) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maHDBH);
            stmt.setInt(2, maKH);
            stmt.setDate(3, new java.sql.Date(ngayTT.getTime()));
            stmt.setDouble(4, tienThanhToan);
            stmt.setString(5, hinhThucThanhToan);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách hóa đơn đang trả góp
    public List<Map<String, Object>> getAllHoaDonDangGopAsMap() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT hd.MaHDBH, MIN(tt.MaKH) AS MaKH, MIN(kh.TenKH) AS TenKH, " +
                "hd.TongTien, hd.TienCoc, (hd.TongTien - hd.TienCoc) / 6 AS TienGopThang, hd.NgayTao " +
                "FROM HoaDonBanHang hd " +
                "JOIN ThanhToan tt ON hd.MaHDBH = tt.MaHDBH " +
                "JOIN KhachHang kh ON tt.MaKH = kh.MaKH " +
                "WHERE hd.TrangThai = N'Đang trả góp' " +
                "GROUP BY hd.MaHDBH, hd.TongTien, hd.TienCoc, hd.NgayTao";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("maHDBH", rs.getInt("MaHDBH"));
                row.put("maKH", rs.getInt("MaKH"));
                row.put("tenKH", rs.getString("TenKH"));
                row.put("tongTien", rs.getDouble("TongTien"));
                row.put("tienCoc", rs.getDouble("TienCoc"));
                row.put("tienGopThang", rs.getDouble("TienGopThang"));
                row.put("ngayTao", rs.getDate("NgayTao"));
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy danh sách tất cả đối tượng ThanhToan
    public List<ThanhToan> layTatCaThanhToan() {
        List<ThanhToan> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM ThanhToan";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ThanhToan tt = new ThanhToan();
                tt.setMaTT(rs.getInt("maTT"));
                tt.setMaHDBH(rs.getInt("maHDBH"));
                tt.setMaKH(rs.getInt("maKH"));
                tt.setNgayTT(rs.getDate("ngayTT"));
                tt.setTienThanhToan(rs.getDouble("tienThanhToan"));
                tt.setHinhThucTT(rs.getString("hinhThucTT"));
                danhSach.add(tt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
}