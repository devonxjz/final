package com.example.services;

import com.example.dto.SanPhamDTO;
import java.util.List;

/**
 * Interface Service cho Sản Phẩm
 */
public interface ISanPhamService {
    /** Lấy toàn bộ sản phẩm */
    List<SanPhamDTO> getAllSanPham();

    /** Thêm sản phẩm mới */
    boolean addSanPham(SanPhamDTO dto);

    /** Cập nhật sản phẩm */
    boolean updateSanPham(SanPhamDTO dto);

    /** Xóa sản phẩm theo mã */
    boolean deleteSanPham(Integer maSP);

    /** Tìm kiếm sản phẩm theo tên */
    List<SanPhamDTO> search(String keyword);
}
