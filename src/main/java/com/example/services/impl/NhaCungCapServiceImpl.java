package com.example.services.impl;

import com.example.dao.NhaCungCapDAO;
import com.example.dao.impl.NhaCungCapDAOImpl;
import com.example.entity.NhaCungCap;
import com.example.services.INhaCungCapService;

import java.util.List;

/**
 * Triển khai INhaCungCapService — Xử lý nghiệp vụ Nhà Cung Cấp
 */
public class NhaCungCapServiceImpl implements INhaCungCapService {
    private final NhaCungCapDAO dao;

    public NhaCungCapServiceImpl() {
        this.dao = new NhaCungCapDAOImpl();
    }

    @Override
    public List<NhaCungCap> getAllNhaCungCap() {
        return dao.getAllNhaCungCap();
    }

    @Override
    public NhaCungCap getById(int maNCC) {
        // NhaCungCapDAO interface doesn't have getById, query manually
        List<NhaCungCap> all = dao.getAllNhaCungCap();
        return all.stream().filter(n -> n.getMaNCC() == maNCC).findFirst().orElse(null);
    }

    @Override
    public boolean addNhaCungCap(NhaCungCap ncc) {
        return dao.themNhaCungCap(ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSdt());
    }

    @Override
    public boolean updateNhaCungCap(NhaCungCap ncc) {
        return dao.capNhatNhaCungCap(ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSdt());
    }

    @Override
    public boolean deleteNhaCungCap(int maNCC) {
        if (dao.kiemTraNhaCungCapDuocXoa(String.valueOf(maNCC))) {
            return false; // Có sản phẩm liên kết, không xóa
        }
        return dao.xoaNhaCungCap(maNCC);
    }

    @Override
    public List<NhaCungCap> search(String keyword) {
        // NhaCungCapDAO interface doesn't have search, filter manually
        if (keyword == null || keyword.trim().isEmpty()) return getAllNhaCungCap();
        String kw = keyword.toLowerCase();
        return dao.getAllNhaCungCap().stream()
                .filter(n -> n.getTenNCC().toLowerCase().contains(kw) ||
                             (n.getSdt() != null && n.getSdt().contains(kw)))
                .toList();
    }
}
