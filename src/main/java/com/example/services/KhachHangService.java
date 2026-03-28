package com.example.services;

import com.example.dao.KhachHangDAO;
import com.example.dto.KhachHangDTO;
import com.example.entity.KhachHang;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Layer điều phối logic kinh doanh cho Khách Hành
 */
public class KhachHangService {
    private final KhachHangDAO dao;

    public KhachHangService() {
        this.dao = new KhachHangDAO();
    }

    /**
     * Map Entity to DTO
     */
    private KhachHangDTO mapToDTO(KhachHang entity) {
        return new KhachHangDTO(
                entity.getMaKH(),
                entity.getTenKH(),
                entity.getGioiTinh(),
                entity.getDiaChi(),
                entity.getSdt(),
                entity.getEmail()
        );
    }

    /**
     * Lấy toàn bộ khách hàng
     */
    public List<KhachHangDTO> getAllKhachHang() {
        return dao.getAllKhachHang().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Thêm mới khách hàng
     */
    public boolean addKhachHang(KhachHangDTO dto) {
        KhachHang entity = new KhachHang();
        entity.setTenKH(dto.tenKH());
        entity.setGioiTinh(dto.gioiTinh());
        entity.setDiaChi(dto.diaChi());
        entity.setSdt(dto.sdt());
        entity.setEmail(dto.email());
        return dao.insert(entity);
    }

    /**
     * Cập nhật thông tin khách hàng
     */
    public boolean updateKhachHang(KhachHangDTO dto) {
        KhachHang entity = dao.getById(dto.maKH());
        if (entity != null) {
            entity.setTenKH(dto.tenKH());
            entity.setGioiTinh(dto.gioiTinh());
            entity.setDiaChi(dto.diaChi());
            entity.setSdt(dto.sdt());
            entity.setEmail(dto.email());
            return dao.update(entity);
        }
        return false;
    }

    /**
     * Xóa khách hàng (kiểm tra liên kết khóa ngoại)
     */
    public boolean deleteKhachHang(Integer maKH) {
        if (dao.hasInvoices(maKH)) {
            return false; // Không xóa được do dính khóa ngoại
        }
        return dao.delete(maKH);
    }

    /**
     * Tìm kiếm khách hàng (tên hoặc sđt)
     */
    public List<KhachHangDTO> search(String keyword) {
        return dao.timKiem(keyword).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
