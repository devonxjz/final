package com.example.services.impl;

import com.example.dao.HoaDonBanHangDAO;
import com.example.dao.KhachHangDAO;
import com.example.dao.SanPhamDAO;
import com.example.dto.DashboardStatusDTO;
import com.example.entity.HoaDonBanHang;
import com.example.entity.SanPham;
import com.example.services.DashboardService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Tổng hợp dữ liệu Dashboard từ 3 DAO hiện có.
 * Không thêm method mới vào DAO — chỉ filter in-memory.
 */
public class DashboardServiceImpl implements DashboardService {

    private final HoaDonBanHangDAO hoadonDAO;
    private final SanPhamDAO sanPhamDAO;
    private final KhachHangDAO khachHangDAO;

    public DashboardServiceImpl(HoaDonBanHangDAO hoadonDAO, SanPhamDAO sanPhamDAO, KhachHangDAO khachHangDAO) {
        this.hoadonDAO = hoadonDAO;
        this.sanPhamDAO = sanPhamDAO;
        this.khachHangDAO = khachHangDAO;
    }

    @Override
    public DashboardStatusDTO getDashboardStatus() {
        // 1. Revenue Today — filter hóa đơn theo ngày hôm nay
        List<HoaDonBanHang> allHD = hoadonDAO.getAllHDBH();
        Date today = stripTime(new Date());
        double revenueToday = allHD.stream()
                .filter(hd -> hd.getNgayTao() != null && stripTime(hd.getNgayTao()).equals(today))
                .mapToDouble(hd -> hd.getTongTien() != null ? hd.getTongTien() : 0)
                .sum();

        // 2. Total Orders — tổng số hóa đơn
        int totalOrders = allHD.size();

        // 3. Products In Stock — tổng tồn kho
        List<SanPham> allSP = sanPhamDAO.getAllSanPham();
        int productsInStock = allSP.stream()
                .mapToInt(sp -> sp.getSoLuongTrongKho() != null ? sp.getSoLuongTrongKho() : 0)
                .sum();

        // 4. Total Customers — tổng khách hàng (KhachHang không có ngayTao)
        int totalCustomers = khachHangDAO.getAllKhachHang().size();

        return new DashboardStatusDTO(revenueToday, totalOrders, productsInStock, totalCustomers);
    }

    /** Bỏ giờ/phút/giây để so sánh ngày thuần túy */
    private Date stripTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
