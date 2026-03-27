package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)

public class ChiTietHDBH {
    @NonFinal
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    int maHDBH;
    int maSP;
    int soLuong;
    double tongTien;

}