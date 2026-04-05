USE QLNVBCHLapTop;

-- =============================================
-- 1. NHÂN VIÊN (Cần nạp trước để gắn vào Hóa đơn)
-- =============================================
INSERT INTO NhanVien (TenNV, NgaySinh, GioiTinh, DiaChi, SDT, ChucVu, Luong) VALUES
                                                                                 ('Nông Hoàng Anh', '2004-05-20', 'Nam', 'Thủ Đức, TP.HCM', '0988123456', 'Quản lý', 20000000),
                                                                                 ('Lê Minh Tâm', '2003-10-15', 'Nam', 'Q9, TP.HCM', '0988654321', 'Nhân viên bán hàng', 10000000),
                                                                                 ('Nguyễn Thu Hà', '2005-01-12', 'Nữ', 'Bình Thạnh, TP.HCM', '0988000111', 'Nhân viên kho', 9000000);

-- =============================================
-- 2. TÀI KHOẢN (Liên kết với Nhân viên)
-- =============================================
INSERT INTO TaiKhoan (TenDangNhap, MatKhau, VaiTro, MaNV) VALUES
                                                              ('admin', '12345', 'Admin', 1),
                                                              ('nv_tam', '12345', 'NhanVien', 2),
                                                              ('nv_ha', '12345', 'NhanVien', 3);

-- =============================================
-- 3. NHÀ CUNG CẤP
-- =============================================
INSERT INTO NhaCungCap (TenNCC, DiaChi, SDT) VALUES
                                                 ('Asus Việt Nam', 'Q1, TP.HCM', '02839111222'),
                                                 ('Dell Technologies', 'Q7, TP.HCM', '02839222333'),
                                                 ('Lenovo Việt Nam', 'Cầu Giấy, Hà Nội', '02438333444'),
                                                 ('HP Inc Vietnam', 'Q3, TP.HCM', '02839444555'),
                                                 ('Apple Vietnam', 'Q2, TP.HCM', '02839555666');

-- =============================================
-- 4. KHÁCH HÀNG
-- =============================================
INSERT INTO KhachHang (TenKH, GioiTinh, DiaChi, SDT, Email) VALUES
                                                                ('Nguyễn Văn An', 'Nam', 'Thủ Đức, TP.HCM', '0901000001', 'an.nv@gmail.com'),
                                                                ('Trần Thị Bích', 'Nữ', 'Gò Vấp, TP.HCM', '0901000002', 'bich.tt@gmail.com'),
                                                                ('Đặng Quốc Gia', 'Nam', 'Cầu Giấy, Hà Nội', '0901000007', 'gia.dq@gmail.com');

-- =============================================
-- 5. SẢN PHẨM
-- =============================================
INSERT INTO SanPham (LoaiMay, TenSP, CPU, GPU, RAM, OCung, KichThuocMH, DoPhanGiaiMH, CanNang, SLTrongKho, GiaBan, GiaNhap, ThoiGianBaoHanh, MaNCC) VALUES
                                                                                                                                                        ('Gaming', 'Asus ROG Strix G16', 'Intel i7-13650HX', 'RTX 4060 8GB', 16, 'SSD 512GB', 16.0, '1920x1200', 2.5, 15, 32990000, 28000000, 24, 1),
                                                                                                                                                        ('Ultrabook', 'MacBook Air M2', 'Apple M2', 'Apple GPU 8-core', 8, 'SSD 256GB', 13.6, '2560x1664', 1.2, 10, 27990000, 24000000, 12, 5),
                                                                                                                                                        ('Văn phòng', 'Dell Inspiron 15 3520', 'Intel i5-1235U', 'Intel UHD', 8, 'SSD 256GB', 15.6, '1920x1080', 1.7, 25, 14990000, 12000000, 12, 2);

-- =============================================
-- 6. HÓA ĐƠN NHẬP HÀNG
-- =============================================
INSERT INTO HoaDonNhapHang (NgayNH, TongTienNH, MaNCC, MaNV) VALUES
                                                                 ('2026-03-01', 280000000, 1, 3),
                                                                 ('2026-03-10', 120000000, 2, 3);

-- =============================================
-- 7. CHI TIẾT HÓA ĐƠN NHẬP HÀNG
-- =============================================
INSERT INTO ChiTietHDNH (MaHDNH, MaSP, SoLuong, GiaNhapTaiThoiDiem, TongTien) VALUES
                                                                                  (1, 1, 10, 28000000, 280000000),
                                                                                  (2, 3, 10, 12000000, 120000000);

-- =============================================
-- 8. HÓA ĐƠN BÁN HÀNG (SỬA LỖI: Đã bổ sung cột MaKH)
-- =============================================
INSERT INTO HoaDonBanHang (NgayTao, LoaiHD, TongTien, TienCoc, LaiSuat, ThoiHanTG, TienGopHangThang, SoTienConLai, TrangThai, MaKH, MaNV) VALUES
                                                                                                                                              ('2026-03-15', 'Trả thẳng', 32990000, 32990000, 0, 0, 0, 0, 'Đã thanh toán', 1, 2),
                                                                                                                                              ('2026-03-20', 'Trả góp', 27990000, 8397000, 0, 6, 3265500, 19593000, 'Đang trả góp', 2, 2);

-- =============================================
-- 9. CHI TIẾT HÓA ĐƠN BÁN HÀNG
-- =============================================
INSERT INTO ChiTietHDBH (MaHDBH, MaSP, SoLuong, TongTien) VALUES
                                                              (1, 1, 1, 32990000),
                                                              (2, 2, 1, 27990000);

-- =============================================
-- 10. THANH TOÁN
-- =============================================
INSERT INTO ThanhToan (MaHDBH, MaKH, MaNV, NgayTT, TienThanhToan, HinhThucTT) VALUES
                                                                                  (1, 1, 2, '2026-03-15', 32990000, 'Tiền mặt'),
                                                                                  (2, 2, 2, '2026-03-20', 8397000, 'Chuyển khoản');
