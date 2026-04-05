package com.example.config;

import com.example.dao.*;
import com.example.dao.impl.*;
import com.example.services.*;
import com.example.services.impl.*;
import com.example.entity.TaiKhoan;

/**
 * Dependency Injection Container cập nhật.
 * Cung cấp các instance Singleton của DAOs và Services.
 * Bổ sung quản lý TaiKhoan và phiên đăng nhập (Session).
 */
public class AppConfig {

    // --- Session Management (Quản lý phiên đăng nhập) ---
    private static TaiKhoan currentUser;

    // --- DAOs ---
    private static SanPhamDAO sanPhamDAO;
    private static KhachHangDAO khachHangDAO;
    private static NhaCungCapDAO nhaCungCapDAO;
    private static ThanhToanDAO thanhToanDAO;
    private static HoaDonBanHangDAO hoadonDAO;
    private static ThongKeDAO thongKeDAO;
    private static TaiKhoanDAO taiKhoanDAO; // Bổ sung mới
    private static NhanVienDAO nhanVienDAO; // Bổ sung mới

    // --- Services ---
    private static SanPhamService sanPhamService;
    private static KhachHangService khachHangService;
    private static NhaCungCapService nhaCungCapService;
    private static ThanhToanService thanhToanService;
    private static HoaDonBanHangService hoaDonBanHangService;
    private static ThongKeService thongKeService;
    private static DashboardService dashboardService;
    private static TaiKhoanService taiKhoanService; // Bổ sung mới
    private static NhanVienService nhanVienService; // Bổ sung mới

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
        taiKhoanDAO = new TaiKhoanDAOImpl(); // Khởi tạo DAO Tài khoản
        nhanVienDAO = new NhanVienDAOImpl(); // Khởi tạo DAO Nhân viên

        // Init Services (inject DAOs via constructor — proper DI)
        sanPhamService = new SanPhamServiceImpl(sanPhamDAO);
        khachHangService = new KhachHangServiceImpl(khachHangDAO);
        nhaCungCapService = new NhaCungCapServiceImpl(nhaCungCapDAO);
        thanhToanService = new ThanhToanServiceImpl(thanhToanDAO);
        hoaDonBanHangService = new HoaDonBanHangServiceImpl(hoadonDAO, khachHangDAO, sanPhamDAO, nhanVienDAO);
        thongKeService = new ThongKeServiceImpl(thongKeDAO);
        dashboardService = new DashboardServiceImpl(hoadonDAO, sanPhamDAO, khachHangDAO);

        // Khởi tạo Service Tài khoản và Nhân viên
        taiKhoanService = new TaiKhoanServiceImpl(taiKhoanDAO);
        nhanVienService = new NhanVienServiceImpl(nhanVienDAO);
    }

    // --- Session Getters & Setters ---

    public static void setCurrentUser(TaiKhoan user) {
        currentUser = user;
    }

    public static TaiKhoan getCurrentUser() {
        return currentUser;
    }

    // --- Getters for Services ---

    public static TaiKhoanService getTaiKhoanService() {
        if (taiKhoanService == null) initialize();
        return taiKhoanService;
    }

    public static NhanVienService getNhanVienService() {
        if (nhanVienService == null) initialize();
        return nhanVienService;
    }

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
