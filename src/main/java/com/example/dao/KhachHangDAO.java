package com.example.dao;

import com.example.entity.KhachHang;
import java.util.List;

public interface KhachHangDAO {
    List<KhachHang> getAllKhachHang();
    KhachHang getById(int maKH);
    boolean insert(KhachHang kh);
    boolean update(KhachHang kh);
    boolean delete(int maKH);
    boolean hasInvoices(int maKH);
    List<KhachHang> timKiem(String keyword);
    List<KhachHang> timKiemSDT(String sdt);
}