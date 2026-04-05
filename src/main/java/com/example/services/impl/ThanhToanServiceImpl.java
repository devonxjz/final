package com.example.services.impl;

import com.example.config.HibernateConfig;
import com.example.dao.ThanhToanDAO;
import com.example.dto.ThanhToanDTO;
import com.example.entity.ThanhToan;
import com.example.services.ThanhToanService;
import jakarta.persistence.EntityManager;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ThanhToanServiceImpl implements ThanhToanService {

    // Tiêm DAO thông qua Constructor để AppConfig sử dụng
    private final ThanhToanDAO thanhToanDAO;

    public ThanhToanServiceImpl(ThanhToanDAO thanhToanDAO) {
        this.thanhToanDAO = thanhToanDAO;
    }

    @Override
    public List<Map<String, Object>> getAllThanhToanAsMap() {
        // Trực tiếp gọi hàm đã được bạn viết rất chuẩn ở DAOImpl
        return thanhToanDAO.getAllThanhToanAsMap();
    }

    @Override
    public boolean updateThanhToan(int maTT, Date ngayTT, double tienMoi, String hinhThucTT) {
        return thanhToanDAO.updateThanhToan(maTT, ngayTT, tienMoi, hinhThucTT);
    }

    @Override
    public boolean themThanhToan(int maHDBH, int maKH, int maNV, java.util.Date ngayTT, double tienTT, String hinhThuc) {
        // Logic trừ nợ và chuyển trạng thái hóa đơn đã có sẵn trong DAOImpl
        return thanhToanDAO.themThanhToan(maHDBH, maKH, maNV, ngayTT, tienTT, hinhThuc);
    }

    @Override
    public List<Map<String, Object>> getAllHoaDonDangGop() {
        return thanhToanDAO.getAllHoaDonDangGopAsMap();
    }

    @Override
    public double getSoTienConNo(int maHD) {
        // 1. Lấy lịch sử các lần trả tiền
        List<ThanhToan> lichSu = thanhToanDAO.findByHoaDon(maHD);
        double daTra = lichSu.stream().mapToDouble(ThanhToan::getTienThanhToan).sum();

        // 2. Lấy tổng tiền của hóa đơn (Dùng EntityManager cục bộ vì chưa tiêm HoaDonDAO)
        try (EntityManager em = HibernateConfig.getEntityManager()) {
            Double tongTien = em.createQuery("SELECT h.tongTien FROM HoaDonBanHang h WHERE h.maHDBH = :maHD", Double.class)
                .setParameter("maHD", maHD)
                .getSingleResult();

            double tong = (tongTien != null) ? tongTien : 0.0;
            return Math.max(0, tong - daTra); // Đảm bảo nợ không bị âm
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean xoaThanhToan(int maTT) {
        return thanhToanDAO.delete(maTT);
    }

    @Override
    public List<ThanhToanDTO> getLichSuThanhToanTheoHD(int maHD) {
        // Lấy Entity từ DAO
        List<ThanhToan> list = thanhToanDAO.findByHoaDon(maHD);

        // Chuyển đổi Entity -> DTO bằng Stream API để bảo vệ dữ liệu khi đẩy lên View
        return list.stream().map(t -> new ThanhToanDTO(
            t.getMaTT(),
            t.getHoaDon() != null ? t.getHoaDon().getMaHDBH() : null,
            t.getKhachHang() != null ? t.getKhachHang().getMaKH() : null,
            t.getKhachHang() != null ? t.getKhachHang().getTenKH() : "Khách lẻ",
            t.getNgayTT(),
            t.getTienThanhToan(),
            t.getHinhThucTT()
        )).collect(Collectors.toList());
    }
}
