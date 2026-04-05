package com.example.services;

import com.example.entity.TaiKhoan;

public interface TaiKhoanService {
    /**
     * Xác thực người dùng dựa trên tên đăng nhập và mật khẩu.
     * @return Đối tượng TaiKhoan nếu thành công, ngược lại trả về null.
     */
    TaiKhoan authenticate(String username, String password);
}
