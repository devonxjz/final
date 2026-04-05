package com.example.dao;

import com.example.entity.NhanVien;
import java.util.List;

public interface NhanVienDAO {
    List<NhanVien> getAll();
    NhanVien getById(int id);
    void saveOrUpdate(NhanVien nv);
    void delete(int id);
}
