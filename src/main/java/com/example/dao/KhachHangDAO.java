package com.example.dao;

import com.example.entity.KhachHang;
import com.example.config.DatabaseConnection;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    private Connection conn;

    public KhachHangDAO() {
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Lấy tất cả khách hàng
    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> ds = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setGioiTinh(rs.getString("GioiTinh"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setSdt(rs.getString("SDT"));
                ds.add(kh);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong getAllKhachHang: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Lỗi truy vấn cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return ds;
    }

    // Thêm mới khách hàng
    public boolean insert(KhachHang kh) {
        if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống");
        }
        if (kh.getGioiTinh() == null || kh.getGioiTinh().trim().isEmpty()) {
            throw new IllegalArgumentException("Giới tính không được để trống");
        }
        if (kh.getDiaChi() == null || kh.getDiaChi().trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được để trống");
        }
        if (kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }

        String sql = "INSERT INTO KhachHang (TenKH, GioiTinh, DiaChi, SDT) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, kh.getTenKH());
            stmt.setString(2, kh.getGioiTinh());
            stmt.setString(3, kh.getDiaChi());
            stmt.setString(4, kh.getSdt());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    kh.setMaKH(generatedKeys.getInt(1)); // Cập nhật MaKH tự sinh
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong insert: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Lỗi thêm mới cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Cập nhật khách hàng
    public boolean update(KhachHang kh) {
        if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống");
        }
        if (kh.getGioiTinh() == null || kh.getGioiTinh().trim().isEmpty()) {
            throw new IllegalArgumentException("Giới tính không được để trống");
        }
        if (kh.getDiaChi() == null || kh.getDiaChi().trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được để trống");
        }
        if (kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }

        String sql = "UPDATE KhachHang SET TenKH = ?, GioiTinh = ?, DiaChi = ?, SDT = ? WHERE MaKH = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kh.getTenKH());
            stmt.setString(2, kh.getGioiTinh());
            stmt.setString(3, kh.getDiaChi());
            stmt.setString(4, kh.getSdt());
            stmt.setInt(5, kh.getMaKH());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng với MaKH: " + kh.getMaKH(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong update: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Lỗi cập nhật cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Tìm kiếm theo tên (bắt đầu bằng keyword)
    public List<KhachHang> timKiem(String keyword) {
        List<KhachHang> list = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllKhachHang();
        }

        String sql = "SELECT * FROM KhachHang WHERE TenKH LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setGioiTinh(rs.getString("GioiTinh"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setSdt(rs.getString("SDT"));
                list.add(kh);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong timKiem: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Lỗi truy vấn cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return list;
    }

    // Tìm kiếm theo số điện thoại
    public List<KhachHang> timKiemSDT(String sdt) {
        List<KhachHang> list = new ArrayList<>();
        if (sdt == null || sdt.trim().isEmpty()) {
            return getAllKhachHang();
        }

        String sql = "SELECT * FROM KhachHang WHERE SDT = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sdt);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setGioiTinh(rs.getString("GioiTinh"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setSdt(rs.getString("SDT"));
                list.add(kh);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong timKiemSDT: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Lỗi truy vấn cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return list;
    }
}