package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    int maSP;
    String loaiMay;
    String tenSP;
    String CPU;
    String GPU;
    int RAM;
    String oCung;
    float kichThuocMH;
    String doPhanGiaiMH;
    float canNang;
    int soLuongTrongKho;
    double giaBan;
    double giaNhap;
    int thoiGianBaoHanh;
}