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
import java.sql.Date;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class ThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    int maTT;
    int maHDBH;
    int maKH;
    Date ngayTT;
    double tienThanhToan;
    String hinhThucTT;

}