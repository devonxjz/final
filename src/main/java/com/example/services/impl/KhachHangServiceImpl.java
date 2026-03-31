package com.example.services.impl;

import com.example.dao.KhachHangDAO;
import com.example.dao.impl.KhachHangDAOImpl;
import com.example.dto.KhachHangDTO;
import com.example.entity.KhachHang;
import com.example.services.KhachHangService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Triển khai IKhachHangService — Xử lý nghiệp vụ Khách Hàng
 */
public class KhachHangServiceImpl implements KhachHangService {
    private final KhachHangDAO dao;

    public KhachHangServiceImpl() {
        this.dao = new KhachHangDAOImpl();
    }

    private KhachHangDTO mapToDTO(KhachHang entity) {
        return new KhachHangDTO(
                entity.getMaKH(), entity.getTenKH(), entity.getGioiTinh(),
                entity.getDiaChi(), entity.getSdt(), entity.getEmail()
        );
    }

    @Override
    public List<KhachHangDTO> getAllKhachHang() {
        return dao.getAllKhachHang().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public boolean addKhachHang(KhachHangDTO dto) {
        KhachHang entity = new KhachHang();
        entity.setTenKH(dto.tenKH()); entity.setGioiTinh(dto.gioiTinh());
        entity.setDiaChi(dto.diaChi()); entity.setSdt(dto.sdt()); entity.setEmail(dto.email());
        boolean success = dao.insert(entity);
        if (!success) throw new com.example.exception.ServiceException("Thêm khách hàng thất bại (Lỗi CSDL)");
        return true;
    }

    @Override
    public boolean updateKhachHang(KhachHangDTO dto) {
        KhachHang entity = dao.getById(dto.maKH());
        if (entity == null) return false;
        entity.setTenKH(dto.tenKH()); entity.setGioiTinh(dto.gioiTinh());
        entity.setDiaChi(dto.diaChi()); entity.setSdt(dto.sdt()); entity.setEmail(dto.email());
        boolean success = dao.update(entity);
        if (!success) throw new com.example.exception.ServiceException("Cập nhật khách hàng thất bại (Lỗi CSDL)");
        return true;
    }

    @Override
    public boolean deleteKhachHang(Integer maKH) {
        if (dao.hasInvoices(maKH)) throw new com.example.exception.ServiceException("Không thể xóa khách hàng đã có lịch sử đơn hàng!");
        boolean success = dao.delete(maKH);
        if (!success) throw new com.example.exception.ServiceException("Xóa khách hàng thất bại (Lỗi CSDL)");
        return true;
    }

    @Override
    public List<KhachHangDTO> search(String keyword) {
        return dao.timKiem(keyword).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
