package com.example.services.impl;

import com.example.dao.NhanVienDAO;
import com.example.dto.NhanVienDTO;
import com.example.entity.NhanVien;
import com.example.services.NhanVienService;
import java.util.List;
import java.util.stream.Collectors;

public class NhanVienServiceImpl implements NhanVienService {

    private final NhanVienDAO nhanVienDAO;

    // Tiêm DAO thông qua Constructor để AppConfig sử dụng
    public NhanVienServiceImpl(NhanVienDAO nhanVienDAO) {
        this.nhanVienDAO = nhanVienDAO;
    }

    @Override
    public List<NhanVienDTO> getAllNhanVien() {
        // Chuyển đổi List Entity sang List DTO bằng Stream API
        return nhanVienDAO.getAll().stream()
            .map(nv -> new NhanVienDTO(nv.getMaNV(), nv.getTenNV(), nv.getSDT(), nv.getChucVu()))
            .collect(Collectors.toList());
    }

    @Override
    public NhanVienDTO getById(int id) {
        NhanVien nv = nhanVienDAO.getById(id);
        if (nv != null) {
            return new NhanVienDTO(nv.getMaNV(), nv.getTenNV(), nv.getSDT(), nv.getChucVu());
        }
        return null;
    }

    @Override
    public void saveOrUpdate(NhanVienDTO dto) {
        NhanVien nv = new NhanVien();
        if (dto.maNV() > 0) {
            nv = nhanVienDAO.getById(dto.maNV());
        }
        nv.setTenNV(dto.tenNV());
        nv.setSDT(dto.sdt());
        nv.setChucVu(dto.chucVu());

        nhanVienDAO.saveOrUpdate(nv);
    }

    @Override
    public void deleteNhanVien(int id) {
        nhanVienDAO.delete(id);
    }
}
