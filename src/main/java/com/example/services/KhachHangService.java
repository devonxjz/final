package com.example.services;

import com.example.dto.KhachHangDTO;
import java.util.List;

/**
 * Interface Service cho Khách Hàng
 * Định nghĩa các phương thức nghiệp vụ CRUD cho Khách Hàng
 */
public interface KhachHangService {
    /** Lấy toàn bộ danh sách khách hàng */
    List<KhachHangDTO> getAllKhachHang();

    /** Thêm mới khách hàng */
    boolean addKhachHang(KhachHangDTO dto);

    /** Cập nhật thông tin khách hàng */
    boolean updateKhachHang(KhachHangDTO dto);

    /** Xóa khách hàng theo mã */
    boolean deleteKhachHang(Integer maKH);

    /** Tìm kiếm khách hàng theo tên hoặc SĐT */
    List<KhachHangDTO> search(String keyword);
}
