package com.example.services;

import com.example.dto.ThanhToanDTO;
import java.util.List;
import java.util.Map;

/**
 * Interface Service cho Thanh Toán.
 * Quản lý các giao dịch thu tiền, trả góp và đối soát nhân viên.
 */
public interface ThanhToanService {

    /** * Lấy toàn bộ lịch sử thanh toán để hiển thị lên bảng.
     * Trả về List Map để linh hoạt hiển thị thông tin kèm tên Khách hàng/Nhân viên.
     */
    List<Map<String, Object>> getAllThanhToanAsMap();

    /** * Thêm một giao dịch thanh toán mới.
     * Cần maNV để biết nhân viên nào thực hiện thu tiền.
     */
    boolean themThanhToan(int maHDBH, int maKH, int maNV, java.util.Date ngayTT,
                          double tienThanhToan, String hinhThucTT);

    /** * Lấy danh sách các hóa đơn hiện đang trong tình trạng "Đang trả góp".
     */
    List<Map<String, Object>> getAllHoaDonDangGop();

    /** * Tính toán số tiền còn nợ của một hóa đơn cụ thể.
     * Phục vụ logic hiển thị số tiền cần thanh toán tiếp theo.
     */
    double getSoTienConNo(int maHD);

    /** * Xóa một bản ghi thanh toán (Dùng trong trường hợp nhập sai).
     * @param maTT Mã thanh toán cần xóa.
     */
    boolean xoaThanhToan(int maTT);

    /** * Lấy lịch sử các đợt thanh toán của một hóa đơn nhất định.
     */
    List<ThanhToanDTO> getLichSuThanhToanTheoHD(int maHD);

    boolean updateThanhToan(int maTT, java.util.Date ngayTT, double tienMoi, String hinhThucTT);
}
