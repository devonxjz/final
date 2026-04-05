package com.example.services.impl;

import com.example.dao.TaiKhoanDAO;
import com.example.entity.TaiKhoan;
import com.example.services.TaiKhoanService;
import org.hibernate.Hibernate;

public class TaiKhoanServiceImpl implements TaiKhoanService {

    // Khai báo DAO để gọi logic truy vấn
    private final TaiKhoanDAO taiKhoanDAO;

    // Constructor để AppConfig tiêm DAO vào (Dependency Injection)
    public TaiKhoanServiceImpl(TaiKhoanDAO taiKhoanDAO) {
        this.taiKhoanDAO = taiKhoanDAO;
    }

    @Override
    public TaiKhoan authenticate(String username, String password) {
        try {
            // Bước 1: Gọi DAO để tìm tài khoản theo Username
            TaiKhoan taiKhoan = taiKhoanDAO.findByUsername(username);

            // Bước 2: Kiểm tra mật khẩu (So sánh trực tiếp với mockdata)
            if (taiKhoan != null && taiKhoan.getMatKhau().equals(password)) {

                // Bước 3: Khởi tạo dữ liệu Nhân viên để tránh lỗi LazyInitialization
                // Vì quan hệ OneToOne thường mặc định là Lazy.
                Hibernate.initialize(taiKhoan.getNhanVien());

                return taiKhoan;
            }
        } catch (Exception e) {
            System.err.println("Lỗi xác thực tại Service: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
