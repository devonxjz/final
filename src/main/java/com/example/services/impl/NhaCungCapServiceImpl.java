package com.example.services.impl;

import com.example.dao.NhaCungCapDAO;
import com.example.entity.NhaCungCap;
import com.example.dto.NhaCungCapDTO;
import com.example.services.NhaCungCapService;
import com.example.exception.ServiceException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Triển khai INhaCungCapService — Xử lý nghiệp vụ Nhà Cung Cấp
 */
public class NhaCungCapServiceImpl implements NhaCungCapService {
    private final NhaCungCapDAO dao;

    public NhaCungCapServiceImpl(NhaCungCapDAO dao) {
        this.dao = dao;
    }

    private NhaCungCapDTO mapToDTO(NhaCungCap e) {
        return new NhaCungCapDTO(e.getMaNCC(), e.getTenNCC(), e.getDiaChi(), e.getSdt());
    }

    @Override
    public List<NhaCungCapDTO> getAllNhaCungCap() {
        return dao.getAllNhaCungCap().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public NhaCungCapDTO getById(int maNCC) {
        List<NhaCungCap> all = dao.getAllNhaCungCap();
        NhaCungCap ncc = all.stream().filter(n -> n.getMaNCC() == maNCC).findFirst().orElse(null);
        return ncc != null ? mapToDTO(ncc) : null;
    }

    @Override
    public boolean addNhaCungCap(NhaCungCapDTO ncc) {
        boolean success = dao.themNhaCungCap(ncc.tenNCC(), ncc.diaChi(), ncc.sdt());
        if (!success) {
            throw new ServiceException("Thêm nhà cung cấp thất bại do lỗi CSDL");
        }
        return true;
    }

    @Override
    public boolean updateNhaCungCap(NhaCungCapDTO ncc) {
        boolean success = dao.capNhatNhaCungCap(ncc.maNCC(), ncc.tenNCC(), ncc.diaChi(), ncc.sdt());
        if (!success) {
            throw new ServiceException("Cập nhật nhà cung cấp thất bại do lỗi CSDL");
        }
        return true;
    }

    @Override
    public boolean deleteNhaCungCap(int maNCC) {
        if (dao.kiemTraNhaCungCapDuocXoa(String.valueOf(maNCC))) {
            throw new ServiceException("Có sản phẩm liên kết với nhà cung cấp này, không thể xóa!");
        }
        boolean success = dao.xoaNhaCungCap(maNCC);
        if (!success) {
            throw new ServiceException("Xóa nhà cung cấp thất bại do lỗi CSDL");
        }
        return true;
    }

    @Override
    public List<NhaCungCapDTO> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllNhaCungCap();
        String kw = keyword.toLowerCase();
        return dao.getAllNhaCungCap().stream()
                .filter(n -> n.getTenNCC().toLowerCase().contains(kw) ||
                             (n.getSdt() != null && n.getSdt().contains(kw)))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
