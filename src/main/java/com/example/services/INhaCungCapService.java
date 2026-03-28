package com.example.services;

import com.example.entity.NhaCungCap;
import java.util.List;

/**
 * Interface Service cho Nhà Cung Cấp
 */
public interface INhaCungCapService {
    List<NhaCungCap> getAllNhaCungCap();
    NhaCungCap getById(int maNCC);
    boolean addNhaCungCap(NhaCungCap ncc);
    boolean updateNhaCungCap(NhaCungCap ncc);
    boolean deleteNhaCungCap(int maNCC);
    List<NhaCungCap> search(String keyword);
}
