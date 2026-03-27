package com.example.dao;

import com.example.entity.NhaCungCap;
import com.example.config.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class NhaCungCapDAO {
    private Connection conn;

    public NhaCungCapDAO() {
        try {
            conn = DatabaseConnection.getConnection(); // Lớp dùng để mở kết nối DB
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách nhà cung cấp
    public List<NhaCungCap> getAllNhaCungCap() {
        List<NhaCungCap> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNCC(rs.getInt("maNCC"));
                ncc.setTenNCC(rs.getString("tenNCC"));
                ncc.setDiaChi(rs.getString("diaChi"));
                ncc.setSdt(rs.getString("sdt"));
                list.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm nhà cung cấp
    public boolean themNhaCungCap(String tenNCC, String diaChi, String sdt) {
        String sql = "INSERT INTO NhaCungCap (tenNCC, diaChi, sdt) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenNCC);
            stmt.setString(2, diaChi);
            stmt.setString(3, sdt);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa nhà cung cấp
    public boolean xoaNhaCungCap(int maNCC) {
        String sql = "DELETE FROM NhaCungCap WHERE maNCC = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maNCC);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật nhà cung cấp
    public boolean capNhatNhaCungCap(int maNCC, String tenNCC, String diaChi, String sdt) {
        String sql = "UPDATE NhaCungCap SET tenNCC = ?, diaChi = ?, sdt = ? WHERE maNCC = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenNCC);
            stmt.setString(2, diaChi);
            stmt.setString(3, sdt);
            stmt.setInt(4, maNCC);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra nhà cung cấp trước khi xóa
    public boolean kiemTraNhaCungCapDuocXoa(String maNCC) {
        String sql = "SELECT COUNT(*) FROM HoaDonNhapHang WHERE MaNCC = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNCC);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}