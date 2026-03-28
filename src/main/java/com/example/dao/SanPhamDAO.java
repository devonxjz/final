package com.example.dao;

import com.example.entity.SanPham;
import java.util.List;

public interface SanPhamDAO {
    List<SanPham> getAllSanPham();
    SanPham getById(int maSP);
    boolean insertOrUpdate(SanPham sp);
    boolean xoaSanPham(int maSP);
    List<SanPham> timKiem(String keyword);
}