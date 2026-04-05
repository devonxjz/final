package com.example.dto;

/**
 * Data Transfer Object cho Nhân viên.
 * Dùng để vận chuyển dữ liệu từ Service lên View mà không làm lộ thông tin nhạy cảm như Lương.
 */
public record NhanVienDTO(
    int maNV,
    String tenNV,
    String sdt,
    String chucVu
) {
    // Bạn có thể thêm các phương thức bổ trợ nếu cần hiển thị đặc biệt
    public String getDisplayName() {
        return maNV + " - " + tenNV;
    }
}
