package com.example.config;

import com.example.dao.*;
import com.example.dao.impl.*;
import com.example.services.*;
import com.example.services.impl.*;

/**
 * Dependency Injection Container.
 * Cung cấp các instance Singleton của DAOs và Services.
 * Giải phóng Controller và View khỏi việc tự tạo instance (new DAOImpl()).
 */
public class AppConfig {
    
    // --- DAOs ---
    private static SanPhamDAO sanPhamDAO;
    private static KhachHangDAO khachHangDAO;
    private static NhaCungCapDAO nhaCungCapDAO;
    private static ThanhToanDAO thanhToanDAO;
    private static HoaDonBanHangDAO hoadonDAO;
    private static ThongKeDAO thongKeDAO;

    // --- Services ---
    private static SanPhamService sanPhamService;
    private static KhachHangService khachHangService;
    private static NhaCungCapService nhaCungCapService;
    private static ThanhToanService thanhToanService;
    private static HoaDonBanHangService hoaDonBanHangService;
    private static ThongKeService thongKeService;
    private static DashboardService dashboardService;

    /**
     * Khởi tạo toàn bộ dependency tĩnh một lần.
     */
    public static void initialize() {
        // Init DAOs
        sanPhamDAO = new SanPhamDAOImpl();
        khachHangDAO = new KhachHangDAOImpl();
        nhaCungCapDAO = new NhaCungCapDAOImpl();
        thanhToanDAO = new ThanhToanDAOImpl();
        hoadonDAO = new HoaDonBanHangDAOImpl();
        thongKeDAO = new ThongKeDAOImpl();

        // Init Services (inject DAOs via constructor — proper DI)
        sanPhamService = new SanPhamServiceImpl(sanPhamDAO);
        khachHangService = new KhachHangServiceImpl(khachHangDAO);
        nhaCungCapService = new NhaCungCapServiceImpl(nhaCungCapDAO);
        thanhToanService = new ThanhToanServiceImpl(thanhToanDAO);
        hoaDonBanHangService = new HoaDonBanHangServiceImpl(hoadonDAO, khachHangDAO, sanPhamDAO);
        thongKeService = new ThongKeServiceImpl(thongKeDAO);
        dashboardService = new DashboardServiceImpl(hoadonDAO, sanPhamDAO, khachHangDAO);
    }

    // --- Getters for Services ---
    
    public static SanPhamService getSanPhamService() {
        if (sanPhamService == null) initialize();
        return sanPhamService;
    }

    public static KhachHangService getKhachHangService() {
        if (khachHangService == null) initialize();
        return khachHangService;
    }

    public static NhaCungCapService getNhaCungCapService() {
        if (nhaCungCapService == null) initialize();
        return nhaCungCapService;
    }

    public static ThanhToanService getThanhToanService() {
        if (thanhToanService == null) initialize();
        return thanhToanService;
    }

    public static HoaDonBanHangService getHoaDonBanHangService() {
        if (hoaDonBanHangService == null) initialize();
        return hoaDonBanHangService;
    }

    public static ThongKeService getThongKeService() {
        if (thongKeService == null) initialize();
        return thongKeService;
    }

    public static DashboardService getDashboardService() {
        if (dashboardService == null) initialize();
        return dashboardService;
    }
}
