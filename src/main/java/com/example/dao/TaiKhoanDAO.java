package com.example.dao;

import com.example.entity.TaiKhoan;

public interface TaiKhoanDAO {
    TaiKhoan findByUsername(String username);
    void saveOrUpdate(TaiKhoan tk);
}
