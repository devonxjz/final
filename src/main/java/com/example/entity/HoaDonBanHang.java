package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.sql.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)

public class HoaDonBanHang {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    int maHDBH;
    Date ngayTao;
    String loaiHD;
    double tongTien;
    double tienCoc;
    double laiSuat;
    int thoiHanTG;
    double tienGopHangThang;
    double soTienConLai;
    String trangThai;

}