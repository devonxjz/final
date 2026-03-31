package com.example.services;

import com.example.dto.NhaCungCapDTO;
import java.util.List;

/**
 * Interface Service cho Nhà Cung Cấp
 */
public interface NhaCungCapService {
    List<NhaCungCapDTO> getAllNhaCungCap();

    NhaCungCapDTO getById(int maNCC);

    boolean addNhaCungCap(NhaCungCapDTO ncc);

    boolean updateNhaCungCap(NhaCungCapDTO ncc);

    boolean deleteNhaCungCap(int maNCC);

    List<NhaCungCapDTO> search(String keyword);
}
