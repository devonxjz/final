package com.example.services.impl;

import com.example.dao.SanPhamDAO;
import com.example.dao.impl.SanPhamDAOImpl;
import com.example.dto.SanPhamDTO;
import com.example.entity.SanPham;
import com.example.services.ISanPhamService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Triển khai ISanPhamService — Xử lý nghiệp vụ Sản Phẩm
 */
public class SanPhamServiceImpl implements ISanPhamService {
    private final SanPhamDAO dao;

    public SanPhamServiceImpl() {
        this.dao = new SanPhamDAOImpl();
    }

    private SanPhamDTO mapToDTO(SanPham e) {
        Integer maNCC = e.getNhaCungCap() != null ? e.getNhaCungCap().getMaNCC() : null;
        String tenNCC = e.getNhaCungCap() != null ? e.getNhaCungCap().getTenNCC() : "";
        return new SanPhamDTO(
                e.getMaSP(), e.getLoaiMay(), e.getTenSP(), e.getCPU(), e.getGPU(),
                e.getRAM(), e.getOCung(), e.getKichThuocMH(), e.getDoPhanGiaiMH(),
                e.getCanNang(), e.getSoLuongTrongKho(), e.getGiaBan(), e.getGiaNhap(),
                e.getThoiGianBaoHanh(), maNCC, tenNCC
        );
    }

    @Override
    public List<SanPhamDTO> getAllSanPham() {
        return dao.getAllSanPham().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public boolean addSanPham(SanPhamDTO dto) {
        SanPham sp = new SanPham();
        sp.setLoaiMay(dto.loaiMay()); sp.setTenSP(dto.tenSP());
        sp.setCPU(dto.CPU()); sp.setGPU(dto.GPU());
        sp.setRAM(dto.RAM()); sp.setOCung(dto.oCung());
        sp.setKichThuocMH(dto.kichThuocMH()); sp.setDoPhanGiaiMH(dto.doPhanGiaiMH());
        sp.setCanNang(dto.canNang()); sp.setSoLuongTrongKho(dto.soLuongTrongKho());
        sp.setGiaBan(dto.giaBan()); sp.setGiaNhap(dto.giaNhap());
        sp.setThoiGianBaoHanh(dto.thoiGianBaoHanh());
        return dao.insertOrUpdate(sp);
    }

    @Override
    public boolean updateSanPham(SanPhamDTO dto) {
        SanPham sp = dao.getById(dto.maSP());
        if (sp == null) return false;
        sp.setLoaiMay(dto.loaiMay()); sp.setTenSP(dto.tenSP());
        sp.setCPU(dto.CPU()); sp.setGPU(dto.GPU());
        sp.setRAM(dto.RAM()); sp.setOCung(dto.oCung());
        sp.setKichThuocMH(dto.kichThuocMH()); sp.setDoPhanGiaiMH(dto.doPhanGiaiMH());
        sp.setCanNang(dto.canNang()); sp.setSoLuongTrongKho(dto.soLuongTrongKho());
        sp.setGiaBan(dto.giaBan()); sp.setGiaNhap(dto.giaNhap());
        sp.setThoiGianBaoHanh(dto.thoiGianBaoHanh());
        return dao.insertOrUpdate(sp);
    }

    @Override
    public boolean deleteSanPham(Integer maSP) {
        return dao.xoaSanPham(maSP);
    }

    @Override
    public List<SanPhamDTO> search(String keyword) {
        return dao.timKiem(keyword).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
