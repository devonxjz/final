package com.example.dao;

import com.example.entity.SanPham;
import com.example.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    private Connection conn;

    public SanPhamDAO() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách tất cả sản phẩm
    public List<SanPham> getAllSanPham() {
        List<SanPham> ds = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getInt("MaSP"));
                sp.setLoaiMay(rs.getString("LoaiMay"));
                sp.setTenSP(rs.getString("TenSP"));
                sp.setCPU(rs.getString("CPU"));
                sp.setGPU(rs.getString("GPU"));
                sp.setRAM(rs.getInt("RAM"));
                sp.setOCung(rs.getString("OCung"));
                sp.setKichThuocMH(rs.getFloat("KichThuocMH"));
                sp.setDoPhanGiaiMH(rs.getString("DoPhanGiaiMH"));
                sp.setCanNang(rs.getFloat("CanNang"));
                sp.setSoLuongTrongKho(rs.getInt("SLTrongKho"));
                sp.setGiaBan(rs.getDouble("GiaBan"));
                sp.setGiaNhap(rs.getDouble("GiaNhap"));
                sp.setThoiGianBaoHanh(rs.getInt("ThoiGianBaoHanh"));
                ds.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // Thêm hoặc cập nhật sản phẩm
    public boolean insertOrUpdate(SanPham sp) {
        String checkSql = "SELECT COUNT(*) FROM SanPham WHERE MaSP = ?";
        String insertSql = "INSERT INTO SanPham (LoaiMay, TenSP, ThoiGianBaoHanh, GiaBan, CPU, GPU, RAM, OCung, GiaNhap, KichThuocMH, DoPhanGiaiMH, CanNang, SLTrongKho) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE SanPham SET LoaiMay = ?, TenSP = ?, ThoiGianBaoHanh = ?, GiaBan = ?, CPU = ?, GPU = ?, RAM = ?, OCung = ?, GiaNhap = ?, KichThuocMH = ?, DoPhanGiaiMH = ?, CanNang = ?, SLTrongKho = ? WHERE MaSP = ?";

        try {
            // Kiểm tra sản phẩm đã tồn tại hay chưa
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, sp.getMaSP());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            boolean exists = rs.getInt(1) > 0;
            rs.close();
            checkStmt.close();

            if (exists) {
                // Cập nhật sản phẩm
                PreparedStatement stmt = conn.prepareStatement(updateSql);
                stmt.setString(1, sp.getLoaiMay());
                stmt.setString(2, sp.getTenSP());
                stmt.setInt(3, sp.getThoiGianBaoHanh());
                stmt.setDouble(4, sp.getGiaBan());
                stmt.setString(5, sp.getCPU());
                stmt.setString(6, sp.getGPU());
                stmt.setInt(7, sp.getRAM());
                stmt.setString(8, sp.getOCung());
                stmt.setDouble(9, sp.getGiaNhap());
                stmt.setFloat(10, sp.getKichThuocMH());
                stmt.setString(11, sp.getDoPhanGiaiMH());
                stmt.setFloat(12, sp.getCanNang());
                stmt.setInt(13, sp.getSoLuongTrongKho());
                stmt.setInt(14, sp.getMaSP());
                return stmt.executeUpdate() > 0;
            } else {
                // Thêm mới sản phẩm
                PreparedStatement stmt = conn.prepareStatement(insertSql);
                stmt.setString(1, sp.getLoaiMay());
                stmt.setString(2, sp.getTenSP());
                stmt.setInt(3, sp.getThoiGianBaoHanh());
                stmt.setDouble(4, sp.getGiaBan());
                stmt.setString(5, sp.getCPU());
                stmt.setString(6, sp.getGPU());
                stmt.setInt(7, sp.getRAM());
                stmt.setString(8, sp.getOCung());
                stmt.setDouble(9, sp.getGiaNhap());
                stmt.setFloat(10, sp.getKichThuocMH());
                stmt.setString(11, sp.getDoPhanGiaiMH());
                stmt.setFloat(12, sp.getCanNang());
                stmt.setInt(13, sp.getSoLuongTrongKho());
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa sản phẩm
    public boolean xoaSanPham(int maSP) {
        String sql = "DELETE FROM SanPham WHERE maSP = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maSP);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm sản phẩm theo tên
    public List<SanPham> timKiem(String keyword) {
        List<SanPham> list = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllSanPham();
        }

        String sql = "SELECT * FROM SanPham WHERE UPPER(TenSP) LIKE UPPER(?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getInt("MaSP"));
                sp.setLoaiMay(rs.getString("LoaiMay"));
                sp.setTenSP(rs.getString("TenSP"));
                sp.setCPU(rs.getString("CPU"));
                sp.setGPU(rs.getString("GPU"));
                sp.setRAM(rs.getInt("RAM"));
                sp.setOCung(rs.getString("OCung"));
                sp.setKichThuocMH(rs.getFloat("KichThuocMH"));
                sp.setDoPhanGiaiMH(rs.getString("DoPhanGiaiMH"));
                sp.setCanNang(rs.getFloat("CanNang"));
                sp.setSoLuongTrongKho(rs.getInt("SLTrongKho"));
                sp.setGiaBan(rs.getDouble("GiaBan"));
                sp.setGiaNhap(rs.getDouble("GiaNhap"));
                sp.setThoiGianBaoHanh(rs.getInt("ThoiGianBaoHanh"));
                list.add(sp);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong timKiem: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}