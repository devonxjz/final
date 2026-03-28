package com.example.dao;

import com.example.entity.NhaCungCap;
import java.util.List;

public interface NhaCungCapDAO {
    List<NhaCungCap> getAllNhaCungCap();
    boolean themNhaCungCap(String tenNCC, String diaChi, String sdt);
    boolean xoaNhaCungCap(int maNCC);
    boolean capNhatNhaCungCap(int maNCC, String tenNCC, String diaChi, String sdt);
    boolean kiemTraNhaCungCapDuocXoa(String maNCC);
}