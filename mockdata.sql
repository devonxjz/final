USE QLNVBCHLapTop;

-- Xóa dữ liệu cũ
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE ThanhToan;
TRUNCATE TABLE ChiTietHDBH;
TRUNCATE TABLE HoaDonBanHang;
TRUNCATE TABLE SanPham;
TRUNCATE TABLE KhachHang;
TRUNCATE TABLE NhaCungCap;
SET FOREIGN_KEY_CHECKS = 1;

-- =============================================
-- 1. NHÀ CUNG CẤP (20 records)
-- =============================================
INSERT INTO NhaCungCap (TenNCC, DiaChi, SDT) VALUES
('Asus Việt Nam', 'Q1, TP.HCM', '02839111222'),
('Dell Technologies', 'Q7, TP.HCM', '02839222333'),
('Lenovo Việt Nam', 'Cầu Giấy, Hà Nội', '02438333444'),
('HP Inc Vietnam', 'Q3, TP.HCM', '02839444555'),
('Apple Vietnam', 'Q2, TP.HCM', '02839555666'),
('MSI Computer', 'Tân Bình, TP.HCM', '02839666777'),
('Acer Vietnam', 'Đống Đa, Hà Nội', '02438777888'),
('Samsung Electronics', 'Bắc Ninh', '02223888999'),
('LG Electronics', 'Hải Phòng', '02253999000'),
('Gigabyte Technology', 'Q10, TP.HCM', '02839000111'),
('Razer Inc', 'Bình Thạnh, TP.HCM', '02839112233'),
('Huawei Vietnam', 'Nam Từ Liêm, Hà Nội', '02438223344'),
('Xiaomi Vietnam', 'Thanh Xuân, Hà Nội', '02438334455'),
('Microsoft Vietnam', 'Q1, TP.HCM', '02839445566'),
('Toshiba Vietnam', 'Biên Hòa, Đồng Nai', '02513556677'),
('Fujitsu Vietnam', 'KCN Thăng Long, Hà Nội', '02438667788'),
('Panasonic Vietnam', 'Bình Dương', '02743778899'),
('Sony Vietnam', 'Q4, TP.HCM', '02839889900'),
('Intel Vietnam', 'KCN SHTP, Q9, TP.HCM', '02839990011'),
('AMD Vietnam', 'Thủ Đức, TP.HCM', '02839001122');

-- =============================================
-- 2. KHÁCH HÀNG (30 records)
-- =============================================
INSERT INTO KhachHang (TenKH, GioiTinh, DiaChi, SDT, Email) VALUES
('Nguyễn Văn An', 'Nam', 'Thủ Đức, TP.HCM', '0901000001', 'an.nv@gmail.com'),
('Trần Thị Bích', 'Nữ', 'Gò Vấp, TP.HCM', '0901000002', 'bich.tt@gmail.com'),
('Lê Hoàng Cường', 'Nam', 'Q1, TP.HCM', '0901000003', 'cuong.lh@gmail.com'),
('Phạm Thị Dung', 'Nữ', 'Bình Thạnh, TP.HCM', '0901000004', 'dung.pt@gmail.com'),
('Hoàng Văn Em', 'Nam', 'Tân Phú, TP.HCM', '0901000005', 'em.hv@gmail.com'),
('Ngô Thị Phương', 'Nữ', 'Q7, TP.HCM', '0901000006', 'phuong.nt@gmail.com'),
('Đặng Quốc Gia', 'Nam', 'Cầu Giấy, Hà Nội', '0901000007', 'gia.dq@gmail.com'),
('Bùi Thị Hạnh', 'Nữ', 'Đống Đa, Hà Nội', '0901000008', 'hanh.bt@gmail.com'),
('Vũ Minh Inh', 'Nam', 'Hai Bà Trưng, Hà Nội', '0901000009', 'inh.vm@gmail.com'),
('Lý Thị Kim', 'Nữ', 'Long Biên, Hà Nội', '0901000010', 'kim.lt@gmail.com'),
('Trương Văn Long', 'Nam', 'Biên Hòa, Đồng Nai', '0901000011', 'long.tv@gmail.com'),
('Phan Thị Mai', 'Nữ', 'Thủ Dầu Một, Bình Dương', '0901000012', 'mai.pt@gmail.com'),
('Cao Hoàng Nam', 'Nam', 'Nha Trang, Khánh Hòa', '0901000013', 'nam.ch@gmail.com'),
('Đinh Thị Oanh', 'Nữ', 'Huế, TT-Huế', '0901000014', 'oanh.dt@gmail.com'),
('Hồ Văn Phúc', 'Nam', 'Đà Nẵng', '0901000015', 'phuc.hv@gmail.com'),
('Nguyễn Thị Quỳnh', 'Nữ', 'Cần Thơ', '0901000016', 'quynh.nt@gmail.com'),
('Lê Văn Rạng', 'Nam', 'Vũng Tàu, BR-VT', '0901000017', 'rang.lv@gmail.com'),
('Trần Thị Sương', 'Nữ', 'Q12, TP.HCM', '0901000018', 'suong.tt@gmail.com'),
('Phạm Văn Tài', 'Nam', 'Bình Tân, TP.HCM', '0901000019', 'tai.pv@gmail.com'),
('Hoàng Thị Uyên', 'Nữ', 'Q5, TP.HCM', '0901000020', 'uyen.ht@gmail.com'),
('Đỗ Văn Vinh', 'Nam', 'Hóc Môn, TP.HCM', '0901000021', 'vinh.dv@gmail.com'),
('Mai Thị Xuyến', 'Nữ', 'Củ Chi, TP.HCM', '0901000022', 'xuyen.mt@gmail.com'),
('Nguyễn Đức Yên', 'Nam', 'Q9, TP.HCM', '0901000023', 'yen.nd@gmail.com'),
('Trần Ánh Nguyệt', 'Nữ', 'Tân Bình, TP.HCM', '0901000024', 'nguyet.ta@gmail.com'),
('Lê Quang Đạt', 'Nam', 'Phú Nhuận, TP.HCM', '0901000025', 'dat.lq@gmail.com'),
('Võ Thị Hoa', 'Nữ', 'Q3, TP.HCM', '0901000026', 'hoa.vt@gmail.com'),
('Bùi Thanh Lâm', 'Nam', 'Thanh Khê, Đà Nẵng', '0901000027', 'lam.bt@gmail.com'),
('Dương Thị Nhung', 'Nữ', 'Sơn Trà, Đà Nẵng', '0901000028', 'nhung.dt@gmail.com'),
('Tô Văn Quân', 'Nam', 'Hải Châu, Đà Nẵng', '0901000029', 'quan.tv@gmail.com'),
('Lương Thị Trang', 'Nữ', 'Ninh Kiều, Cần Thơ', '0901000030', 'trang.lt@gmail.com');

-- =============================================
-- 3. SẢN PHẨM (30 records) - đầy đủ cấu hình
-- =============================================
INSERT INTO SanPham (LoaiMay, TenSP, CPU, GPU, RAM, OCung, KichThuocMH, DoPhanGiaiMH, CanNang, SLTrongKho, GiaBan, GiaNhap, ThoiGianBaoHanh, MaNCC) VALUES
('Gaming', 'Asus ROG Strix G16', 'Intel i7-13650HX', 'RTX 4060 8GB', 16, 'SSD 512GB', 16.0, '1920x1200', 2.5, 15, 32990000, 28000000, 24, 1),
('Gaming', 'Asus TUF Gaming F15', 'Intel i5-12500H', 'RTX 4050 6GB', 16, 'SSD 512GB', 15.6, '1920x1080', 2.3, 20, 24990000, 21000000, 24, 1),
('Ultrabook', 'Asus ZenBook 14 OLED', 'Intel i7-1360P', 'Intel Iris Xe', 16, 'SSD 512GB', 14.0, '2880x1800', 1.4, 12, 27990000, 24000000, 24, 1),
('Văn phòng', 'Asus Vivobook 15', 'Intel i5-1235U', 'Intel UHD', 8, 'SSD 256GB', 15.6, '1920x1080', 1.8, 30, 13990000, 11000000, 12, 1),
('Gaming', 'Dell G15 5530', 'Intel i7-13650HX', 'RTX 4060 8GB', 16, 'SSD 512GB', 15.6, '1920x1080', 2.8, 10, 29990000, 25000000, 12, 2),
('Ultrabook', 'Dell XPS 15 9530', 'Intel i7-13700H', 'RTX 4050 6GB', 16, 'SSD 512GB', 15.6, '3456x2160', 1.9, 8, 42990000, 37000000, 24, 2),
('Văn phòng', 'Dell Inspiron 15 3520', 'Intel i5-1235U', 'Intel UHD', 8, 'SSD 256GB', 15.6, '1920x1080', 1.7, 25, 14990000, 12000000, 12, 2),
('Workstation', 'Dell Precision 5570', 'Intel i9-12900H', 'RTX A2000', 32, 'SSD 1TB', 15.6, '3840x2400', 1.8, 5, 65990000, 58000000, 36, 2),
('Gaming', 'Lenovo Legion 5 Pro', 'AMD R7-7745HX', 'RTX 4070 8GB', 16, 'SSD 1TB', 16.0, '2560x1600', 2.5, 12, 37990000, 32000000, 24, 3),
('Ultrabook', 'Lenovo Yoga Slim 7', 'AMD R7-7730U', 'AMD Radeon', 16, 'SSD 512GB', 14.0, '2880x1800', 1.4, 18, 23990000, 20000000, 24, 3),
('Văn phòng', 'Lenovo IdeaPad 3', 'Intel i3-1215U', 'Intel UHD', 8, 'SSD 256GB', 15.6, '1920x1080', 1.6, 35, 10990000, 9000000, 12, 3),
('Văn phòng', 'Lenovo ThinkPad E14', 'Intel i5-1335U', 'Intel Iris Xe', 16, 'SSD 512GB', 14.0, '1920x1080', 1.6, 20, 18990000, 16000000, 36, 3),
('Ultrabook', 'HP Envy x360 15', 'AMD R7-7730U', 'AMD Radeon', 16, 'SSD 512GB', 15.6, '1920x1080', 1.7, 14, 24990000, 21000000, 24, 4),
('Gaming', 'HP Victus 16', 'Intel i5-13500H', 'RTX 4050 6GB', 16, 'SSD 512GB', 16.1, '1920x1080', 2.3, 22, 22990000, 19000000, 12, 4),
('Văn phòng', 'HP Pavilion 15', 'Intel i5-1335U', 'Intel Iris Xe', 8, 'SSD 256GB', 15.6, '1920x1080', 1.7, 28, 15990000, 13000000, 12, 4),
('Ultrabook', 'MacBook Air M2', 'Apple M2', 'Apple GPU 8-core', 8, 'SSD 256GB', 13.6, '2560x1664', 1.2, 10, 27990000, 24000000, 12, 5),
('Ultrabook', 'MacBook Pro 14 M3', 'Apple M3 Pro', 'Apple GPU 14-core', 18, 'SSD 512GB', 14.2, '3024x1964', 1.6, 6, 49990000, 44000000, 12, 5),
('Ultrabook', 'MacBook Air M3 15', 'Apple M3', 'Apple GPU 10-core', 16, 'SSD 512GB', 15.3, '2880x1864', 1.5, 8, 37990000, 33000000, 12, 5),
('Gaming', 'MSI Katana 15', 'Intel i7-13620H', 'RTX 4050 6GB', 16, 'SSD 512GB', 15.6, '1920x1080', 2.3, 18, 23990000, 20000000, 24, 6),
('Gaming', 'MSI Raider GE78 HX', 'Intel i9-13980HX', 'RTX 4090 16GB', 32, 'SSD 2TB', 17.0, '2560x1600', 3.1, 3, 89990000, 78000000, 24, 6),
('Văn phòng', 'MSI Modern 14', 'Intel i5-1335U', 'Intel Iris Xe', 8, 'SSD 512GB', 14.0, '1920x1080', 1.4, 20, 14990000, 12000000, 24, 6),
('Văn phòng', 'Acer Aspire 5', 'Intel i5-1335U', 'Intel Iris Xe', 8, 'SSD 256GB', 15.6, '1920x1080', 1.8, 25, 13990000, 11000000, 12, 7),
('Gaming', 'Acer Nitro 5 AN515', 'AMD R5-7535HS', 'RTX 4050 6GB', 16, 'SSD 512GB', 15.6, '1920x1080', 2.5, 16, 22990000, 19000000, 12, 7),
('Ultrabook', 'Acer Swift 3 OLED', 'Intel i7-1360P', 'Intel Iris Xe', 16, 'SSD 512GB', 14.0, '2880x1800', 1.3, 10, 22990000, 19000000, 24, 7),
('Ultrabook', 'Samsung Galaxy Book3', 'Intel i7-1360P', 'Intel Iris Xe', 16, 'SSD 512GB', 15.6, '1920x1080', 1.6, 12, 25990000, 22000000, 12, 8),
('Ultrabook', 'LG Gram 16 2023', 'Intel i7-1360P', 'Intel Iris Xe', 16, 'SSD 512GB', 16.0, '2560x1600', 1.2, 7, 33990000, 29000000, 12, 9),
('Gaming', 'Gigabyte G5 MF', 'Intel i5-12500H', 'RTX 4050 6GB', 16, 'SSD 512GB', 15.6, '1920x1080', 2.1, 14, 20990000, 17000000, 24, 10),
('Gaming', 'Razer Blade 15', 'Intel i7-13800H', 'RTX 4070 8GB', 16, 'SSD 1TB', 15.6, '2560x1440', 2.0, 4, 59990000, 52000000, 24, 11),
('Ultrabook', 'Huawei MateBook 14s', 'Intel i7-13700H', 'Intel Iris Xe', 16, 'SSD 512GB', 14.2, '2520x1680', 1.4, 9, 28990000, 25000000, 24, 12),
('Ultrabook', 'Xiaomi Book Pro 14', 'Intel i5-1240P', 'Intel Iris Xe', 16, 'SSD 512GB', 14.0, '2880x1800', 1.4, 11, 19990000, 16000000, 12, 13);

-- =============================================
-- 4. HÓA ĐƠN BÁN HÀNG (25 records)
-- =============================================
INSERT INTO HoaDonBanHang (NgayTao, LoaiHD, TongTien, TienCoc, LaiSuat, ThoiHanTG, TienGopHangThang, SoTienConLai, TrangThai) VALUES
('2025-10-05', 'Trả thẳng', 32990000, 32990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2025-10-10', 'Trả góp', 42990000, 12897000, 0, 12, 2507750, 30093000, 'Đang trả góp'),
('2025-10-18', 'Trả thẳng', 24990000, 24990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2025-11-02', 'Trả thẳng', 27990000, 27990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2025-11-08', 'Trả góp', 37990000, 11397000, 0, 6, 4432167, 26593000, 'Đang trả góp'),
('2025-11-15', 'Trả thẳng', 13990000, 13990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2025-11-22', 'Trả thẳng', 49990000, 49990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2025-12-01', 'Trả góp', 89990000, 26997000, 0, 12, 5249417, 62993000, 'Đang trả góp'),
('2025-12-10', 'Trả thẳng', 23990000, 23990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2025-12-18', 'Trả thẳng', 14990000, 14990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2026-01-05', 'Trả thẳng', 22990000, 22990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2026-01-12', 'Trả góp', 59990000, 17997000, 0, 12, 3499417, 41993000, 'Đang trả góp'),
('2026-01-20', 'Trả thẳng', 10990000, 10990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2026-01-28', 'Trả thẳng', 33990000, 33990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2026-02-03', 'Trả góp', 65990000, 19797000, 0, 12, 3849417, 46193000, 'Đang trả góp'),
('2026-02-10', 'Trả thẳng', 15990000, 15990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2026-02-18', 'Trả thẳng', 25990000, 25990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2026-02-25', 'Trả thẳng', 37990000, 37990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2026-03-01', 'Trả góp', 20990000, 6297000, 0, 6, 2448833, 14693000, 'Đang trả góp'),
('2026-03-05', 'Trả thẳng', 28990000, 28990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2026-03-10', 'Trả thẳng', 18990000, 18990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2026-03-12', 'Trả thẳng', 19990000, 19990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2026-03-15', 'Trả góp', 45980000, 13794000, 0, 6, 5364333, 32186000, 'Đang trả góp'),
('2026-03-20', 'Trả thẳng', 22990000, 22990000, 0, 0, 0, 0, 'Đã thanh toán'),
('2026-03-25', 'Trả thẳng', 13990000, 13990000, 0, 0, 0, 0, 'Đã thanh toán');

-- =============================================
-- 5. CHI TIẾT HÓA ĐƠN (30 records)
-- =============================================
INSERT INTO ChiTietHDBH (MaHDBH, MaSP, SoLuong, TongTien) VALUES
(1, 1, 1, 32990000),
(2, 6, 1, 42990000),
(3, 2, 1, 24990000),
(4, 3, 1, 27990000),
(5, 9, 1, 37990000),
(6, 4, 1, 13990000),
(7, 17, 1, 49990000),
(8, 20, 1, 89990000),
(9, 14, 1, 23990000),
(10, 7, 1, 14990000),
(11, 19, 1, 22990000),
(12, 28, 1, 59990000),
(13, 11, 1, 10990000),
(14, 26, 1, 33990000),
(15, 8, 1, 65990000),
(16, 15, 1, 15990000),
(17, 25, 1, 25990000),
(18, 18, 1, 37990000),
(19, 27, 1, 20990000),
(20, 29, 1, 28990000),
(21, 12, 1, 18990000),
(22, 30, 1, 19990000),
(23, 16, 1, 27990000),
(23, 5, 1, 17990000),
(24, 23, 1, 22990000),
(25, 22, 1, 13990000);

-- =============================================
-- 6. THANH TOÁN (30 records)
-- =============================================
INSERT INTO ThanhToan (MaHDBH, MaKH, NgayTT, TienThanhToan, HinhThucTT) VALUES
(1, 1, '2025-10-05', 32990000, 'Tiền mặt'),
(2, 2, '2025-10-10', 12897000, 'Chuyển khoản'),
(3, 3, '2025-10-18', 24990000, 'Tiền mặt'),
(4, 4, '2025-11-02', 27990000, 'Chuyển khoản'),
(5, 5, '2025-11-08', 11397000, 'Tiền mặt'),
(6, 6, '2025-11-15', 13990000, 'Tiền mặt'),
(7, 7, '2025-11-22', 49990000, 'Chuyển khoản'),
(8, 8, '2025-12-01', 26997000, 'Chuyển khoản'),
(9, 9, '2025-12-10', 23990000, 'Tiền mặt'),
(10, 10, '2025-12-18', 14990000, 'Tiền mặt'),
(11, 11, '2026-01-05', 22990000, 'Chuyển khoản'),
(12, 12, '2026-01-12', 17997000, 'Tiền mặt'),
(13, 13, '2026-01-20', 10990000, 'Tiền mặt'),
(14, 14, '2026-01-28', 33990000, 'Chuyển khoản'),
(15, 15, '2026-02-03', 19797000, 'Chuyển khoản'),
(16, 16, '2026-02-10', 15990000, 'Tiền mặt'),
(17, 17, '2026-02-18', 25990000, 'Chuyển khoản'),
(18, 18, '2026-02-25', 37990000, 'Tiền mặt'),
(19, 19, '2026-03-01', 6297000, 'Tiền mặt'),
(20, 20, '2026-03-05', 28990000, 'Chuyển khoản'),
(21, 21, '2026-03-10', 18990000, 'Tiền mặt'),
(22, 22, '2026-03-12', 19990000, 'Tiền mặt'),
(23, 23, '2026-03-15', 13794000, 'Chuyển khoản'),
(24, 24, '2026-03-20', 22990000, 'Tiền mặt'),
(25, 25, '2026-03-25', 13990000, 'Chuyển khoản'),
-- Thanh toán tiếp cho các hóa đơn trả góp
(2, 2, '2025-11-10', 2507750, 'Chuyển khoản'),
(5, 5, '2025-12-08', 4432167, 'Tiền mặt'),
(8, 8, '2026-01-01', 5249417, 'Chuyển khoản'),
(12, 12, '2026-02-12', 3499417, 'Tiền mặt'),
(15, 15, '2026-03-03', 3849417, 'Chuyển khoản');
