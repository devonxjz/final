package com.example.services;

import com.example.dto.NhanVienDTO;
import java.util.List;

public interface NhanVienService {
    List<NhanVienDTO> getAllNhanVien();
    NhanVienDTO getById(int id);
    void saveOrUpdate(NhanVienDTO nvDto);
    void deleteNhanVien(int id);
}
