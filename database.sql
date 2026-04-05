CREATE DATABASE IF NOT EXISTS QLNVBCHLapTop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE QLNVBCHLapTop;

-- ==============================================================================
-- PHẦN 1: TẠO CẤU TRÚC CÁC BẢNG (SCHEMA)
-- ==============================================================================

-- 1. Bảng Nhà Cung Cấp
CREATE TABLE IF NOT EXISTS NhaCungCap (
                                          MaNCC INT AUTO_INCREMENT PRIMARY KEY,
                                          TenNCC VARCHAR(255) NOT NULL,
    DiaChi VARCHAR(255),
    SDT VARCHAR(20)
    );

-- 2. Bảng Nhân Viên
CREATE TABLE IF NOT EXISTS NhanVien (
                                        MaNV INT AUTO_INCREMENT PRIMARY KEY,
                                        TenNV VARCHAR(255) NOT NULL,
    NgaySinh DATE,
    GioiTinh VARCHAR(10),
    DiaChi VARCHAR(255),
    SDT VARCHAR(20) UNIQUE,
    ChucVu VARCHAR(50),
    Luong DOUBLE
    );

-- 3. Bảng Tài Khoản (Liên kết 1:1 với NhanVien)
CREATE TABLE IF NOT EXISTS TaiKhoan (
                                        TenDangNhap VARCHAR(50) PRIMARY KEY,
    MatKhau VARCHAR(255) NOT NULL,
    VaiTro VARCHAR(20) DEFAULT 'NhanVien', -- Admin, NhanVien
    MaNV INT UNIQUE,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV) ON DELETE CASCADE
    );

-- 4. Bảng Khách Hàng
CREATE TABLE IF NOT EXISTS KhachHang (
                                         MaKH INT AUTO_INCREMENT PRIMARY KEY,
                                         TenKH VARCHAR(255) NOT NULL,
    GioiTinh VARCHAR(10),
    DiaChi VARCHAR(255),
    SDT VARCHAR(20) UNIQUE,
    Email VARCHAR(100) UNIQUE
    );

-- 5. Bảng Sản Phẩm
CREATE TABLE IF NOT EXISTS SanPham (
                                       MaSP INT AUTO_INCREMENT PRIMARY KEY,
                                       LoaiMay VARCHAR(100),
    TenSP VARCHAR(255) NOT NULL,
    CPU VARCHAR(100),
    GPU VARCHAR(100),
    RAM INT,
    OCung VARCHAR(100),
    KichThuocMH FLOAT,
    DoPhanGiaiMH VARCHAR(50),
    CanNang FLOAT,
    SLTrongKho INT NOT NULL DEFAULT 0,
    GiaBan DOUBLE NOT NULL,
    GiaNhap DOUBLE,
    ThoiGianBaoHanh INT,
    MaNCC INT,
    FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC) ON DELETE SET NULL
    );

-- 6. Bảng Hóa Đơn Nhập Hàng
CREATE TABLE IF NOT EXISTS HoaDonNhapHang (
                                              MaHDNH INT AUTO_INCREMENT PRIMARY KEY,
                                              NgayNH DATE NOT NULL,
                                              TongTienNH DOUBLE NOT NULL DEFAULT 0,
                                              MaNCC INT,
                                              MaNV INT,
                                              FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
    );

-- 7. Bảng Chi Tiết Hóa Đơn Nhập Hàng
CREATE TABLE IF NOT EXISTS ChiTietHDNH (
                                           MaHDNH INT,
                                           MaSP INT,
                                           SoLuong INT NOT NULL,
                                           GiaNhapTaiThoiDiem DOUBLE NOT NULL,
                                           TongTien DOUBLE NOT NULL,
                                           PRIMARY KEY (MaHDNH, MaSP),
    FOREIGN KEY (MaHDNH) REFERENCES HoaDonNhapHang(MaHDNH) ON DELETE CASCADE,
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP) ON DELETE CASCADE
    );

-- 8. Bảng Hóa Đơn Bán Hàng (ĐÃ SỬA LỖI: BỔ SUNG MaKH)
CREATE TABLE IF NOT EXISTS HoaDonBanHang (
                                             MaHDBH INT AUTO_INCREMENT PRIMARY KEY,
                                             NgayTao DATE NOT NULL,
                                             LoaiHD VARCHAR(50) NOT NULL, -- Trực tiếp / Trả góp
    TongTien DOUBLE NOT NULL,
    TienCoc DOUBLE DEFAULT 0,
    LaiSuat DOUBLE DEFAULT 0,
    ThoiHanTG INT DEFAULT 0,
    TienGopHangThang DOUBLE DEFAULT 0,
    SoTienConLai DOUBLE DEFAULT 0,
    TrangThai VARCHAR(50),
    MaKH INT NOT NULL, -- BỔ SUNG: Khách hàng mua
    MaNV INT NOT NULL, -- Nhân viên lập hóa đơn
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
    );

-- 9. Bảng Chi Tiết Hóa Đơn Bán Hàng
CREATE TABLE IF NOT EXISTS ChiTietHDBH (
                                           MaHDBH INT,
                                           MaSP INT,
                                           SoLuong INT NOT NULL,
                                           TongTien DOUBLE NOT NULL,
                                           PRIMARY KEY (MaHDBH, MaSP),
    FOREIGN KEY (MaHDBH) REFERENCES HoaDonBanHang(MaHDBH) ON DELETE CASCADE,
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP) ON DELETE CASCADE
    );

-- 10. Bảng Thanh Toán
CREATE TABLE IF NOT EXISTS ThanhToan (
                                         MaTT INT AUTO_INCREMENT PRIMARY KEY,
                                         MaHDBH INT NOT NULL,
                                         MaKH INT NOT NULL,
                                         MaNV INT NOT NULL,
                                         NgayTT DATE NOT NULL,
                                         TienThanhToan DOUBLE NOT NULL,
                                         HinhThucTT VARCHAR(50),
    FOREIGN KEY (MaHDBH) REFERENCES HoaDonBanHang(MaHDBH) ON DELETE CASCADE,
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH) ON DELETE CASCADE,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
    );
