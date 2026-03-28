package com.example.services;

import com.example.dao.SanPhamDAO;
import com.example.dao.impl.SanPhamDAOImpl;
import com.example.dto.SanPhamDTO;
import com.example.entity.SanPham;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Layer điều phối logic kinh doanh cho Sản Phẩm
 */
public class SanPhamService {
    private final SanPhamDAO dao;

    public SanPhamService() {
        this.dao = new SanPhamDAOImpl();
    }

    private SanPhamDTO mapToDTO(SanPham entity) {
        Integer maNCC = entity.getNhaCungCap() != null ? entity.getNhaCungCap().getMaNCC() : null;
        String tenNCC = entity.getNhaCungCap() != null ? entity.getNhaCungCap().getTenNCC() : "";
        return new SanPhamDTO(
                entity.getMaSP(), entity.getLoaiMay(), entity.getTenSP(),
                entity.getCPU(), entity.getGPU(), entity.getRAM(), entity.getOCung(),
                entity.getKichThuocMH(), entity.getDoPhanGiaiMH(), entity.getCanNang(),
                entity.getSoLuongTrongKho(), entity.getGiaBan(), entity.getGiaNhap(),
                entity.getThoiGianBaoHanh(), maNCC, tenNCC
        );
    }

    public List<SanPhamDTO> getAllSanPham() {
        return dao.getAllSanPham().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
