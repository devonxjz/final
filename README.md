# 💻 Hệ Thống Quản Lý Cửa Hàng Laptop (QLNVBCHLapTop)

> Ứng dụng desktop quản lý bán hàng laptop xây dựng bằng **Java Swing** với kiến trúc **MVC nhiều lớp**, sử dụng **JPA/Hibernate** làm ORM và **MySQL** làm hệ quản trị cơ sở dữ liệu.

---

## 📋 Mục Lục

- [1. Tổng Quan Dự Án](#1-tổng-quan-dự-án)
- [2. Công Nghệ Sử Dụng](#2-công-nghệ-sử-dụng)
- [3. Kiến Trúc Hệ Thống](#3-kiến-trúc-hệ-thống)
- [4. Cấu Trúc Thư Mục](#4-cấu-trúc-thư-mục)
- [5. Cơ Sở Dữ Liệu](#5-cơ-sở-dữ-liệu)
- [6. Tầng Entity (Thực Thể)](#6-tầng-entity-thực-thể)
- [7. Tầng DAO (Data Access Object)](#7-tầng-dao-data-access-object)
- [8. Tầng Service (Nghiệp Vụ)](#8-tầng-service-nghiệp-vụ)
- [9. Tầng Controller (Điều Khiển)](#9-tầng-controller-điều-khiển)
- [10. Tầng View (Giao Diện)](#10-tầng-view-giao-diện)
- [11. Tiện Ích & Cấu Hình](#11-tiện-ích--cấu-hình)
- [12. Chức Năng Chi Tiết](#12-chức-năng-chi-tiết)
- [13. Hướng Dẫn Cài Đặt & Chạy](#13-hướng-dẫn-cài-đặt--chạy)
- [14. Dữ Liệu Mẫu](#14-dữ-liệu-mẫu)
- [15. Luồng Hoạt Động Chính](#15-luồng-hoạt-động-chính)
- [16. Lưu Ý Quan Trọng](#16-lưu-ý-quan-trọng)
- [17. Định Hướng Phát Triển](#17-định-hướng-phát-triển)

---

## 1. Tổng Quan Dự Án

| Thông tin | Chi tiết |
|---|---|
| **Tên dự án** | Quản Lý Nhân Viên Bán Cửa Hàng Laptop (QLNVBCHLapTop) |
| **Loại ứng dụng** | Desktop Application |
| **Ngôn ngữ** | Java 17 |
| **Build tool** | Apache Maven |
| **Giao diện** | Java Swing (Dark Mode) |
| **ORM** | Jakarta Persistence 3.1 + Hibernate ORM 6.4 |
| **Cơ sở dữ liệu** | MySQL 8+ |
| **Main class** | `com.example.Main` |
| **Persistence Unit** | `LaptopPU` |

### Mục tiêu chính

Ứng dụng cung cấp hệ thống quản lý toàn diện cho một cửa hàng bán laptop, bao gồm:

- 📦 **Quản lý sản phẩm** (Laptop) với đầy đủ cấu hình phần cứng
- 🏢 **Quản lý nhà cung cấp** (đối tác nhập hàng)
- 👥 **Quản lý khách hàng** (thông tin liên hệ)
- 🧾 **Quản lý hóa đơn bán hàng** (bán đứt & trả góp)
- 💳 **Quản lý thanh toán** (tiền mặt & chuyển khoản)
- 📊 **Thống kê & báo cáo** (doanh thu, lợi nhuận, biểu đồ)

---

## 2. Công Nghệ Sử Dụng

### Core

| Thư viện | Phiên bản | Mục đích |
|---|---|---|
| Java JDK | 17 | Ngôn ngữ lập trình chính |
| Maven | 3.9+ | Quản lý dependency & build |

### Persistence & Database

| Thư viện | Phiên bản | Mục đích |
|---|---|---|
| `jakarta.persistence-api` | 3.1.0 | JPA API (Jakarta Persistence) |
| `hibernate-core` | 6.4.4.Final | JPA Provider (Hibernate ORM 6) |
| `mysql-connector-j` | 8.3.0 | JDBC Driver cho MySQL |

### UI & Charting

| Thư viện | Phiên bản | Mục đích |
|---|---|---|
| Java Swing | (built-in) | Framework giao diện desktop |
| `jcalendar` | 1.4 | Date picker component cho Swing |
| `jfreechart` | 1.5.4 | Vẽ biểu đồ thống kê doanh thu |

### Logging

| Thư viện | Phiên bản | Mục đích |
|---|---|---|
| `slf4j-api` | 2.0.12 | API logging chuẩn |
| `logback-classic` | 1.5.3 | Implementation SLF4J |

---

## 3. Kiến Trúc Hệ Thống

Ứng dụng tuân theo kiến trúc **MVC nhiều lớp (Layered MVC)** với luồng dữ liệu rõ ràng:

```
┌───────────────────────────────────────────────────────────┐
│                    VIEW (Java Swing)                      │
│  HomeView, SanPhamView, KhachHangView, ThongKeView ...    │
│  • Hiển thị giao diện người dùng                          │
│  • Nhận sự kiện từ người dùng                             │
│  • Dark Mode theme (UITheme)                              │
└────────────────────────┬──────────────────────────────────┘
                         │ (sự kiện / dữ liệu UI)
                         ▼
┌───────────────────────────────────────────────────────────┐
│                    CONTROLLER                             │
│  SanPhamController, KhachHangController, ...              │
│  • Parse & validate dữ liệu từ View (qua InputValidator) │
│  • Gọi Service xử lý nghiệp vụ                           │
│  • Trả kết quả / lỗi về View                             │
└────────────────────────┬──────────────────────────────────┘
                         │ (DTO / Entity)
                         ▼
┌───────────────────────────────────────────────────────────┐
│                  SERVICE (Nghiệp vụ)                      │
│  Interface: ISanPhamService, IKhachHangService, ...       │
│  Impl: SanPhamServiceImpl, KhachHangServiceImpl, ...      │
│  • Chứa logic nghiệp vụ                                  │
│  • Validate dữ liệu trước khi lưu                        │
│  • Tính toán (tổng tiền, trả góp, ...)                    │
└────────────────────────┬──────────────────────────────────┘
                         │ (Entity)
                         ▼
┌───────────────────────────────────────────────────────────┐
│                    DAO (Data Access)                       │
│  Interface: SanPhamDAO, KhachHangDAO, ...                 │
│  Impl: SanPhamDAOImpl, KhachHangDAOImpl, ...              │
│  • CRUD operations                                        │
│  • JPQL queries                                           │
│  • Transaction management                                 │
└────────────────────────┬──────────────────────────────────┘
                         │ (SQL via Hibernate)
                         ▼
┌───────────────────────────────────────────────────────────┐
│                    DATABASE (MySQL)                        │
│  Schema: QLNVBCHLapTop                                    │
│  Tables: SanPham, KhachHang, NhaCungCap,                  │
│          HoaDonBanHang, ChiTietHDBH, ThanhToan            │
└───────────────────────────────────────────────────────────┘
```

---

## 4. Cấu Trúc Thư Mục

```
final/
├── pom.xml                          # Maven build config
├── database.sql                     # Script tạo database & bảng
├── mockdata.sql                     # Dữ liệu mẫu (20 NCC, 30 KH, 30 SP, 25 HĐ, ...)
├── README.md                        # Tài liệu dự án (file này)
│
└── src/main/
    ├── java/com/example/
    │   ├── Main.java                # 🚀 Entry point - khởi động ứng dụng
    │   │
    │   ├── config/                  # ⚙️ Cấu hình hệ thống
    │   │   ├── DatabaseConnection.java   # JDBC connection trực tiếp
    │   │   ├── HibernateUtil.java        # EntityManagerFactory (chính)
    │   │   ├── JpaConfig.java            # EntityManagerFactory (dự phòng)
    │   │   └── UITheme.java              # 🎨 Dark Mode theme & component factory
    │   │
    │   ├── entity/                  # 📦 JPA Entities (ánh xạ bảng CSDL)
    │   │   ├── SanPham.java              # Sản phẩm (Laptop)
    │   │   ├── NhaCungCap.java           # Nhà cung cấp
    │   │   ├── KhachHang.java            # Khách hàng
    │   │   ├── HoaDonBanHang.java        # Hóa đơn bán hàng
    │   │   ├── ChiTietHDBH.java          # Chi tiết hóa đơn (bảng trung gian)
    │   │   ├── ChiTietHDBHId.java        # Composite key cho ChiTietHDBH
    │   │   └── ThanhToan.java            # Thanh toán
    │   │
    │   ├── dto/                     # 📋 Data Transfer Objects
    │   │   ├── SanPhamDTO.java
    │   │   ├── NhaCungCapDTO.java
    │   │   ├── KhachHangDTO.java
    │   │   ├── HoaDonBanHangDTO.java
    │   │   ├── ChiTietHDBHDTO.java
    │   │   └── ThanhToanDTO.java
    │   │
    │   ├── dao/                     # 🗄️ Data Access Object (Interface)
    │   │   ├── SanPhamDAO.java
    │   │   ├── NhaCungCapDAO.java
    │   │   ├── KhachHangDAO.java
    │   │   ├── HoaDonBanHangDAO.java
    │   │   ├── ThanhToanDAO.java
    │   │   ├── ThongKeDAO.java           # Thống kê/Báo cáo
    │   │   └── impl/               # Triển khai DAO
    │   │       ├── SanPhamDAOImpl.java
    │   │       ├── NhaCungCapDAOImpl.java
    │   │       ├── KhachHangDAOImpl.java
    │   │       ├── HoaDonBanHangDAOImpl.java
    │   │       ├── ThanhToanDAOImpl.java
    │   │       └── ThongKeDAOImpl.java
    │   │
    │   ├── services/                # 🔧 Service Layer (Interface + Impl)
    │   │   ├── ISanPhamService.java
    │   │   ├── SanPhamService.java
    │   │   ├── INhaCungCapService.java
    │   │   ├── IKhachHangService.java
    │   │   ├── KhachHangService.java
    │   │   ├── IHoaDonBanHangService.java
    │   │   ├── HoaDonBanHangService.java
    │   │   ├── IThanhToanService.java
    │   │   ├── IThongKeService.java
    │   │   └── impl/               # Triển khai Service
    │   │       ├── SanPhamServiceImpl.java
    │   │       ├── NhaCungCapServiceImpl.java
    │   │       ├── KhachHangServiceImpl.java
    │   │       ├── HoaDonBanHangServiceImpl.java
    │   │       ├── ThanhToanServiceImpl.java
    │   │       └── ThongKeServiceImpl.java
    │   │
    │   ├── controller/              # 🎮 Controllers (điều khiển luồng)
    │   │   ├── SanPhamController.java         # CRUD sản phẩm
    │   │   ├── ThemSanPhamController.java     # Thêm sản phẩm mới
    │   │   ├── NhaCungCapController.java      # CRUD nhà cung cấp
    │   │   ├── ThemNhaCungCapController.java  # Thêm NCC mới
    │   │   ├── KhachHangController.java       # CRUD khách hàng
    │   │   ├── HoaDonBanHangController.java   # Quản lý hóa đơn
    │   │   ├── HoaDonController.java          # Xử lý hóa đơn chi tiết
    │   │   ├── ThemHoaDonBanController.java   # Tạo hóa đơn mới
    │   │   ├── ThanhToanController.java       # Quản lý thanh toán
    │   │   ├── ThemThanhToanController.java   # Thêm thanh toán mới
    │   │   └── ThongKeController.java         # Thống kê & báo cáo
    │   │
    │   ├── view/                    # 🖥️ Giao diện Swing
    │   │   ├── HomeView.java              # Trang chủ Dashboard
    │   │   ├── TrangChuView.java          # Trang chủ phụ
    │   │   ├── SanPhamView.java           # Quản lý sản phẩm
    │   │   ├── ThemSanPhamView.java       # Form thêm sản phẩm
    │   │   ├── NhaCungCapView.java        # Quản lý nhà cung cấp
    │   │   ├── ThemNhaCungCapView.java    # Form thêm NCC
    │   │   ├── KhachHangView.java         # Quản lý khách hàng
    │   │   ├── HoaDonBanHangView.java     # Danh sách hóa đơn
    │   │   ├── HoaDonView.java            # Chi tiết hóa đơn
    │   │   ├── ThemHoaDonBanView.java     # Form tạo hóa đơn
    │   │   ├── XacNhanHoaDonView.java     # Xác nhận hóa đơn
    │   │   ├── ThanhToanView.java         # Quản lý thanh toán
    │   │   ├── ThemThanhToanView.java     # Form thanh toán
    │   │   └── ThongKeView.java           # Màn hình thống kê
    │   │
    │   └── util/                    # 🛠️ Tiện ích
    │       ├── InputValidator.java        # Chuẩn hóa & validate input
    │       └── ValidationResult.java      # Kết quả validation (generic)
    │
    └── resources/
        └── META-INF/
            └── persistence.xml      # Cấu hình JPA/Hibernate
```

---

## 5. Cơ Sở Dữ Liệu

### 5.1 Thông tin kết nối

| Thông số | Giá trị |
|---|---|
| **DBMS** | MySQL 8+ |
| **Schema** | `QLNVBCHLapTop` |
| **Character Set** | `utf8mb4` / `utf8mb4_unicode_ci` |
| **JDBC URL** | `jdbc:mysql://localhost:3306/QLNVBCHLapTop` |
| **User** | `root` |
| **Password** | `12345` |
| **Hibernate DDL** | `update` (tự tạo/cập nhật schema) |

### 5.2 Sơ đồ quan hệ (ERD)

```
┌──────────────┐       ┌──────────────────┐       ┌──────────────────┐
│  NhaCungCap  │ 1───n │     SanPham      │ n───n │  HoaDonBanHang   │
│──────────────│       │──────────────────│       │──────────────────│
│ MaNCC (PK)   │       │ MaSP (PK)        │       │ MaHDBH (PK)      │
│ TenNCC       │       │ LoaiMay          │       │ NgayTao          │
│ DiaChi       │       │ TenSP            │       │ LoaiHD           │
│ SDT          │       │ CPU, GPU, RAM    │       │ TongTien         │
└──────────────┘       │ OCung            │       │ TienCoc          │
                       │ KichThuocMH      │       │ LaiSuat          │
                       │ DoPhanGiaiMH     │       │ ThoiHanTG        │
                       │ CanNang          │       │ TienGopHangThang │
                       │ SLTrongKho       │       │ SoTienConLai     │
                       │ GiaBan, GiaNhap  │       │ TrangThai        │
                       │ ThoiGianBaoHanh  │       └────────┬─────────┘
                       │ MaNCC (FK)       │                │
                       └────────┬─────────┘                │
                                │                          │
                       ┌────────┴──────────┐               │
                       │   ChiTietHDBH     │               │
                       │──────────────────│               │
                       │ MaHDBH (PK, FK)  │───────────────┘
                       │ MaSP   (PK, FK)  │
                       │ SoLuong           │
                       │ TongTien          │
                       └──────────────────┘

┌──────────────┐       ┌──────────────────┐
│  KhachHang   │ 1───n │    ThanhToan     │
│──────────────│       │──────────────────│
│ MaKH (PK)    │       │ MaTT (PK)        │
│ TenKH        │       │ MaHDBH (FK) ─────────→ HoaDonBanHang
│ GioiTinh     │       │ MaKH   (FK) ─────────→ KhachHang
│ DiaChi       │       │ NgayTT           │
│ SDT (UNIQUE) │       │ TienThanhToan    │
│ Email(UNIQUE)│       │ HinhThucTT       │
└──────────────┘       └──────────────────┘
```

### 5.3 Chi tiết các bảng

#### Bảng `NhaCungCap` (Nhà Cung Cấp)

| Cột | Kiểu | Ràng buộc | Mô tả |
|---|---|---|---|
| `MaNCC` | INT | PK, AUTO_INCREMENT | Mã nhà cung cấp |
| `TenNCC` | VARCHAR(255) | NOT NULL | Tên nhà cung cấp |
| `DiaChi` | VARCHAR(255) | | Địa chỉ trụ sở |
| `SDT` | VARCHAR(20) | | Số điện thoại |

#### Bảng `KhachHang` (Khách Hàng)

| Cột | Kiểu | Ràng buộc | Mô tả |
|---|---|---|---|
| `MaKH` | INT | PK, AUTO_INCREMENT | Mã khách hàng |
| `TenKH` | VARCHAR(255) | NOT NULL | Tên khách hàng |
| `GioiTinh` | VARCHAR(10) | | Giới tính (Nam/Nữ) |
| `DiaChi` | VARCHAR(255) | | Địa chỉ |
| `SDT` | VARCHAR(20) | UNIQUE | Số điện thoại |
| `Email` | VARCHAR(100) | UNIQUE | Email liên hệ |

#### Bảng `SanPham` (Sản Phẩm - Laptop)

| Cột | Kiểu | Ràng buộc | Mô tả |
|---|---|---|---|
| `MaSP` | INT | PK, AUTO_INCREMENT | Mã sản phẩm |
| `LoaiMay` | VARCHAR(100) | | Loại máy (Gaming, Ultrabook, Văn phòng, Workstation) |
| `TenSP` | VARCHAR(255) | NOT NULL | Tên sản phẩm đầy đủ |
| `CPU` | VARCHAR(100) | | Vi xử lý (Intel i7-13650HX, AMD R7-7745HX, Apple M3...) |
| `GPU` | VARCHAR(100) | | Card đồ họa (RTX 4060, Intel Iris Xe...) |
| `RAM` | INT | | Bộ nhớ RAM (GB) |
| `OCung` | VARCHAR(100) | | Ổ cứng (SSD 512GB, SSD 1TB...) |
| `KichThuocMH` | FLOAT | | Kích thước màn hình (inch) |
| `DoPhanGiaiMH` | VARCHAR(50) | | Độ phân giải (1920x1080, 2880x1800...) |
| `CanNang` | FLOAT | | Cân nặng (kg) |
| `SLTrongKho` | INT | NOT NULL | Số lượng tồn kho |
| `GiaBan` | DOUBLE | NOT NULL | Giá bán lẻ (VND) |
| `GiaNhap` | DOUBLE | | Giá nhập (VND) |
| `ThoiGianBaoHanh` | INT | | Thời gian bảo hành (tháng) |
| `MaNCC` | INT | FK → NhaCungCap | Mã nhà cung cấp |

#### Bảng `HoaDonBanHang` (Hóa Đơn Bán Hàng)

| Cột | Kiểu | Ràng buộc | Mô tả |
|---|---|---|---|
| `MaHDBH` | INT | PK, AUTO_INCREMENT | Mã hóa đơn |
| `NgayTao` | DATE | NOT NULL | Ngày lập hóa đơn |
| `LoaiHD` | VARCHAR(50) | NOT NULL | Loại hóa đơn (Trả thẳng / Trả góp) |
| `TongTien` | DOUBLE | NOT NULL | Tổng giá trị hóa đơn |
| `TienCoc` | DOUBLE | | Tiền đặt cọc ban đầu |
| `LaiSuat` | DOUBLE | DEFAULT 0 | Lãi suất trả góp (%) |
| `ThoiHanTG` | INT | DEFAULT 0 | Thời hạn trả góp (tháng) |
| `TienGopHangThang` | DOUBLE | DEFAULT 0 | Số tiền góp mỗi tháng |
| `SoTienConLai` | DOUBLE | DEFAULT 0 | Số tiền còn phải trả |
| `TrangThai` | VARCHAR(50) | | Trạng thái (Đã thanh toán / Đang trả góp) |

#### Bảng `ChiTietHDBH` (Chi Tiết Hóa Đơn)

| Cột | Kiểu | Ràng buộc | Mô tả |
|---|---|---|---|
| `MaHDBH` | INT | PK, FK → HoaDonBanHang | Mã hóa đơn |
| `MaSP` | INT | PK, FK → SanPham | Mã sản phẩm |
| `SoLuong` | INT | NOT NULL | Số lượng mua |
| `TongTien` | DOUBLE | NOT NULL | Thành tiền (= SoLuong × GiaBan) |

#### Bảng `ThanhToan` (Thanh Toán)

| Cột | Kiểu | Ràng buộc | Mô tả |
|---|---|---|---|
| `MaTT` | INT | PK, AUTO_INCREMENT | Mã giao dịch thanh toán |
| `MaHDBH` | INT | FK → HoaDonBanHang | Hóa đơn tương ứng |
| `MaKH` | INT | FK → KhachHang | Khách hàng thanh toán |
| `NgayTT` | DATE | NOT NULL | Ngày thanh toán |
| `TienThanhToan` | DOUBLE | NOT NULL | Số tiền thanh toán |
| `HinhThucTT` | VARCHAR(50) | | Hình thức (Tiền mặt / Chuyển khoản) |

---

## 6. Tầng Entity (Thực Thể)

Các entity class nằm trong package `com.example.entity`, ánh xạ trực tiếp đến các bảng trong CSDL thông qua JPA annotations.

### 6.1 Quan hệ giữa các Entity

```
NhaCungCap ──(1:N)──→ SanPham
SanPham    ──(N:M)──→ HoaDonBanHang   (thông qua ChiTietHDBH)
HoaDonBanHang ──(1:N)──→ ChiTietHDBH
HoaDonBanHang ──(1:N)──→ ThanhToan
KhachHang  ──(1:N)──→ ThanhToan
```

### 6.2 Chi tiết từng Entity

| Entity | Bảng DB | Khóa chính | Quan hệ đáng chú ý |
|---|---|---|---|
| `SanPham` | `SanPham` | `MaSP` (AUTO) | `@ManyToOne` → NhaCungCap, `@OneToMany` → ChiTietHDBH |
| `NhaCungCap` | `NhaCungCap` | `MaNCC` (AUTO) | `@OneToMany(cascade=ALL)` → SanPham |
| `KhachHang` | `KhachHang` | `MaKH` (AUTO) | `@OneToMany(cascade=ALL)` → ThanhToan |
| `HoaDonBanHang` | `HoaDonBanHang` | `MaHDBH` (AUTO) | `@OneToMany(cascade=ALL)` → ChiTietHDBH & ThanhToan |
| `ChiTietHDBH` | `ChiTietHDBH` | Composite (`MaHDBH` + `MaSP`) | `@IdClass(ChiTietHDBHId)`, `@ManyToOne` → HoaDonBanHang & SanPham |
| `ThanhToan` | `ThanhToan` | `MaTT` (AUTO) | `@ManyToOne` → HoaDonBanHang & KhachHang |

### 6.3 Composite Key

`ChiTietHDBHId` là lớp khóa phức hợp (implements `Serializable`) kết hợp `hoaDonBanHang` (Integer) và `sanPham` (Integer), kèm theo `equals()` và `hashCode()` đúng chuẩn JPA.

---

## 7. Tầng DAO (Data Access Object)

Package `com.example.dao` chứa các interface DAO, còn `com.example.dao.impl` chứa triển khai cụ thể sử dụng `EntityManager` từ `HibernateUtil`.

### 7.1 Danh sách DAO

| Interface | Implementation | Chức năng chính |
|---|---|---|
| `SanPhamDAO` | `SanPhamDAOImpl` | CRUD sản phẩm, tìm kiếm |
| `NhaCungCapDAO` | `NhaCungCapDAOImpl` | CRUD nhà cung cấp |
| `KhachHangDAO` | `KhachHangDAOImpl` | CRUD khách hàng, tìm theo SĐT/Email |
| `HoaDonBanHangDAO` | `HoaDonBanHangDAOImpl` | CRUD hóa đơn, tìm kiếm, lọc theo trạng thái |
| `ThanhToanDAO` | `ThanhToanDAOImpl` | CRUD thanh toán, lọc theo hóa đơn/khách hàng |
| `ThongKeDAO` | `ThongKeDAOImpl` | Truy vấn thống kê: doanh thu, lợi nhuận, top bán chạy, doanh thu theo ngày |

### 7.2 Các chức năng ThongKeDAO

```java
public interface ThongKeDAO {
    double getTongDoanhThu(String tuNgay, String denNgay);        // Tổng doanh thu theo khoảng thời gian
    int    getTongDonHang(String tuNgay, String denNgay);         // Tổng số đơn hàng
    double getLoiNhuan(String tuNgay, String denNgay);            // Lợi nhuận (= Doanh thu - Giá nhập)
    Map<String, Integer> getTopSellingProducts(String, String);   // Top sản phẩm bán chạy
    Map<String, Double>  getRevenueByDay(String, String);         // Doanh thu theo ngày (cho biểu đồ)
}
```

---

## 8. Tầng Service (Nghiệp Vụ)

Package `com.example.services` chứa cả interface lẫn implementation cho tầng nghiệp vụ.

### 8.1 Danh sách Service

| Interface | Implementation | Chức năng |
|---|---|---|
| `ISanPhamService` | `SanPhamServiceImpl` | Validate cấu hình laptop, kiểm tra tồn kho |
| `INhaCungCapService` | `NhaCungCapServiceImpl` | Validate thông tin NCC |
| `IKhachHangService` | `KhachHangServiceImpl` | Validate SĐT/Email trùng, chuẩn hóa tên |
| `IHoaDonBanHangService` | `HoaDonBanHangServiceImpl` | Tính toán trả góp, validate chi tiết HĐ |
| `IThanhToanService` | `ThanhToanServiceImpl` | Validate thanh toán, kiểm tra số tiền |
| `IThongKeService` | `ThongKeServiceImpl` | Xử lý dữ liệu thống kê cho Controller |

### 8.2 Các Service bổ sung (không có interface)

- `SanPhamService` — Xử lý nghiệp vụ sản phẩm trực tiếp
- `KhachHangService` — Xử lý nghiệp vụ khách hàng trực tiếp  
- `HoaDonBanHangService` — Xử lý nghiệp vụ hóa đơn trực tiếp

---

## 9. Tầng Controller (Điều Khiển)

Package `com.example.controller` chứa các controller làm cầu nối giữa View và Service.

### 9.1 Danh sách Controller

| Controller | View tương ứng | Chức năng |
|---|---|---|
| `SanPhamController` | `SanPhamView` | CRUD sản phẩm, nạp bảng, tìm kiếm, sắp xếp |
| `ThemSanPhamController` | `ThemSanPhamView` | Parse form thêm SP mới, validate input |
| `NhaCungCapController` | `NhaCungCapView` | CRUD nhà cung cấp |
| `ThemNhaCungCapController` | `ThemNhaCungCapView` | Form thêm NCC mới |
| `KhachHangController` | `KhachHangView` | CRUD khách hàng |
| `HoaDonBanHangController` | `HoaDonBanHangView` | Danh sách hóa đơn, lọc, tìm kiếm |
| `HoaDonController` | `HoaDonView` | Xử lý chi tiết hóa đơn |
| `ThemHoaDonBanController` | `ThemHoaDonBanView` | Tạo hóa đơn mới (bán đứt/trả góp) |
| `ThanhToanController` | `ThanhToanView` | Danh sách thanh toán |
| `ThemThanhToanController` | `ThemThanhToanView` | Thêm giao dịch thanh toán mới |
| `ThongKeController` | `ThongKeView` | Tạo báo cáo, vẽ biểu đồ JFreeChart |

### 9.2 Trách nhiệm chung của Controller

1. **Parse dữ liệu** từ UI fields (text → number, text → date, ...)
2. **Gọi `InputValidator`** để chuẩn hóa & validate
3. **Gọi Service/DAO** để thực thi nghiệp vụ
4. **Cập nhật View** (nạp lại bảng, hiện thông báo lỗi/thành công)
5. **Đăng ký sự kiện** (ActionListener trên các nút bấm)

---

## 10. Tầng View (Giao Diện)

### 10.1 Giao diện Dark Mode

Ứng dụng sử dụng theme **Dark Mode hiện đại** được quản lý tập trung bởi `UITheme.java`:

#### Bảng màu chính

| Vai trò | Mã màu | Preview |
|---|---|---|
| Nền chính | `#111827` | 🟫 Xám đậm |
| Sidebar | `#0F172A` | 🟫 Xanh đen |
| Card/Panel | `#1E293B` | 🟦 Xám xanh |
| Input field | `#334155` | 🟦 Xám trung |
| Accent (chính) | `#38BDF8` | 🔵 Cyan |
| Success | `#34D399` | 🟢 Xanh lá |
| Danger | `#F87171` | 🔴 Đỏ nhạt |
| Warning | `#FBBF24` | 🟡 Vàng |
| Purple | `#A78BFA` | 🟣 Tím |
| Text chính | `#F1F5F9` | ⬜ Trắng nhạt |
| Text phụ | `#94A3B8` | ⬛ Xám |

#### Đặc điểm UI

- **Font chính**: Segoe UI (hỗ trợ tiếng Việt Unicode)
- **Rounded buttons**: Bo góc 10px với hover/press effect
- **Custom ScrollBar**: Dark scrollbar tối giản
- **Striped table**: Xen kẽ màu hàng sáng/tối
- **Factory pattern**: Tạo components styled sẵn qua `UITheme.createButton()`, `createTextField()`, `styleTable()`, ...

### 10.2 Danh sách màn hình

| View | Loại | Mô tả |
|---|---|---|
| `HomeView` | `JFrame` | 🏠 **Dashboard chính** — 6 card điều hướng (Products, Suppliers, Customers, Sales Orders, Payments, Reports) với hover animation |
| `SanPhamView` | `JPanel` | 📦 Bảng sản phẩm + tìm kiếm + nút CRUD |
| `ThemSanPhamView` | `JPanel/Dialog` | Form nhập thông tin cấu hình laptop mới |
| `NhaCungCapView` | `JPanel` | Bảng nhà cung cấp + CRUD |
| `ThemNhaCungCapView` | `JPanel/Dialog` | Form thêm NCC |
| `KhachHangView` | `JPanel` | Bảng khách hàng + CRUD |
| `HoaDonBanHangView` | `JPanel` | Danh sách hóa đơn bán hàng |
| `HoaDonView` | `JPanel` | Chi tiết hóa đơn + sản phẩm trong đơn |
| `ThemHoaDonBanView` | `JPanel/Dialog` | Form tạo hóa đơn mới (chọn SP, nhập SL, chọn loại bán đứt/trả góp) |
| `XacNhanHoaDonView` | `JPanel/Dialog` | Màn hình xác nhận trước khi lưu hóa đơn |
| `ThanhToanView` | `JPanel` | Bảng lịch sử thanh toán |
| `ThemThanhToanView` | `JPanel/Dialog` | Form thêm giao dịch thanh toán |
| `ThongKeView` | `JPanel` | 📊 **Báo cáo thống kê** — lọc theo ngày, 3 card tổng hợp (Doanh thu, Số đơn, Lợi nhuận), bảng Top 5 bán chạy, biểu đồ doanh thu |
| `TrangChuView` | `JPanel` | Trang chủ phụ |

### 10.3 Luồng điều hướng

```
HomeView (Dashboard)
    ├── "Products"     → SanPhamView     → ThemSanPhamView
    ├── "Suppliers"    → NhaCungCapView  → ThemNhaCungCapView
    ├── "Customers"    → KhachHangView
    ├── "Sales Orders" → HoaDonBanHangView → ThemHoaDonBanView → XacNhanHoaDonView
    ├── "Payments"     → ThanhToanView    → ThemThanhToanView
    └── "Reports"      → ThongKeView (biểu đồ JFreeChart)
```

Navigation sử dụng cơ chế **content swapping**: `HomeView` là `JFrame` chính, chứa `contentPanel` có thể thay đổi nội dung. Khi click vào card, `contentPanel` được swap sang View tương ứng kèm nút "← Back" quay về Dashboard.

---

## 11. Tiện Ích & Cấu Hình

### 11.1 InputValidator (`com.example.util`)

Lớp tiện ích trung tâm cho việc chuẩn hóa và validate dữ liệu đầu vào:

| Method | Chức năng |
|---|---|
| `cleanNumeric(String)` | Làm sạch ký tự không phải số, hỗ trợ format VN (1.500.000,50) và quốc tế (1,500,000.50) |
| `parseIntSafe(String, String)` | Parse chuỗi → Integer an toàn |
| `parseDoubleSafe(String, String)` | Parse chuỗi → Double an toàn |
| `parseFloatSafe(String, String)` | Parse chuỗi → Float an toàn |
| `parseCurrency(String, String)` | Parse tiền tệ VND, loại bỏ đơn vị "đ"/"VND" |
| `normalizeName(String, String)` | Chuẩn hóa tên → Title Case, loại khoảng trắng thừa |
| `normalizeAddress(String, String)` | Chuẩn hóa địa chỉ |
| `validatePhone(String, boolean)` | Kiểm tra SĐT Việt Nam (10 số, đầu 03/05/07/08/09) |
| `validateEmail(String, boolean)` | Kiểm tra format email |
| `validateRequiredString(String, String, int)` | Kiểm tra chuỗi bắt buộc + giới hạn độ dài |

### 11.2 ValidationResult (`com.example.util`)

Generic wrapper cho kết quả validation:

```java
ValidationResult<T>
├── success(T value)     // Thành công, chứa giá trị đã chuẩn hóa
├── fail(String message) // Thất bại, chứa thông báo lỗi
├── isValid()            // Kiểm tra kết quả
├── getValue()           // Lấy giá trị (nếu thành công)
└── getMessage()         // Lấy thông báo lỗi (nếu thất bại)
```

### 11.3 Cấu hình kết nối Database

Dự án có **2 cách kết nối** database:

1. **JPA/Hibernate** (chính) — qua `HibernateUtil.java` + `persistence.xml`
   - Persistence Unit: `LaptopPU`
   - DDL mode: `update` (tự tạo bảng nếu chưa có)
   
2. **JDBC trực tiếp** (dự phòng) — qua `DatabaseConnection.java`
   - URL: `jdbc:mysql://localhost:3306/QLNVBCHLapTop`
   - Password: `12345@` (lưu ý khác password ở persistence.xml)

### 11.4 UITheme (`com.example.config`)

Lớp quản lý theme toàn cục, cung cấp:

- **Hằng màu** (`BG_DARK`, `ACCENT`, `TEXT_PRIMARY`, ...)
- **Hằng font** (`FONT_TITLE`, `FONT_BODY`, `FONT_TABLE`, ...)
- **Factory methods**:
  - `createButton(text, color)` — Nút bo góc
  - `createPrimaryButton(text)` — Nút accent xanh
  - `createDangerButton(text)` — Nút đỏ
  - `createSuccessButton(text)` — Nút xanh lá
  - `createTextField()` — Text field dark mode
  - `createComboBox(items)` — ComboBox styled
  - `createCard()` — Panel card bo góc
  - `createTitleLabel(text)` / `createLabel(text)` — Label styled
  - `createScrollPane(table)` — ScrollPane dark
  - `styleTable(table)` — Áp dụng dark theme cho JTable
  - `applyGlobalTheme()` — Gọi 1 lần trong `Main` để set theme toàn cục

---

## 12. Chức Năng Chi Tiết

### 12.1 Quản Lý Sản Phẩm (Laptop)

- ✅ Xem danh sách sản phẩm dạng bảng (đầy đủ thông số)
- ✅ Thêm sản phẩm mới với đầy đủ cấu hình (CPU, GPU, RAM, ổ cứng, màn hình, ...)
- ✅ Sửa thông tin sản phẩm
- ✅ Xóa sản phẩm
- ✅ Tìm kiếm sản phẩm
- ✅ Liên kết với nhà cung cấp

### 12.2 Quản Lý Nhà Cung Cấp

- ✅ Xem danh sách NCC
- ✅ Thêm / Sửa / Xóa NCC
- ✅ Hiển thị danh sách sản phẩm của NCC

### 12.3 Quản Lý Khách Hàng

- ✅ Xem danh sách khách hàng
- ✅ Thêm / Sửa / Xóa khách hàng
- ✅ Validate SĐT (format VN 10 số), Email
- ✅ Kiểm tra trùng SĐT/Email (UNIQUE constraint)

### 12.4 Quản Lý Hóa Đơn Bán Hàng

- ✅ Tạo hóa đơn mới (chọn sản phẩm, số lượng)
- ✅ Hỗ trợ 2 loại: **Bán đứt** và **Trả góp**
- ✅ Tính toán tự động:
  - Tổng tiền = Σ (SoLuong × GiaBan)
  - Tiền cọc (trả góp)
  - Tiền góp hàng tháng
  - Số tiền còn lại
- ✅ Xác nhận hóa đơn trước khi lưu
- ✅ Quản lý trạng thái (Đã thanh toán / Đang trả góp)
- ✅ Xem chi tiết hóa đơn

### 12.5 Quản Lý Thanh Toán

- ✅ Ghi nhận thanh toán theo hóa đơn + khách hàng
- ✅ Hỗ trợ hình thức: Tiền mặt / Chuyển khoản
- ✅ Lịch sử thanh toán
- ✅ Thanh toán nhiều lần cho hóa đơn trả góp

### 12.6 Thống Kê & Báo Cáo

- ✅ Lọc theo khoảng thời gian (Date Picker)
- ✅ 3 card tổng hợp: **Tổng doanh thu**, **Tổng đơn hàng**, **Lợi nhuận**
- ✅ Bảng **Top 5 sản phẩm bán chạy**
- ✅ **Biểu đồ doanh thu** theo ngày (JFreeChart)
- 🔲 Export Excel (đã có nút, chưa triển khai)

---

## 13. Hướng Dẫn Cài Đặt & Chạy

### 13.1 Điều kiện tiên quyết

| Yêu cầu | Phiên bản tối thiểu |
|---|---|
| Java JDK | 17+ |
| Apache Maven | 3.9+ |
| MySQL Server | 8.0+ |
| IDE (khuyến nghị) | IntelliJ IDEA / Eclipse |

### 13.2 Thiết lập Database

**Bước 1:** Tạo database và các bảng:

```bash
mysql -u root -p < database.sql
```

**Bước 2:** Nạp dữ liệu mẫu (tùy chọn):

```bash
mysql -u root -p < mockdata.sql
```

**Bước 3:** Kiểm tra/chỉnh sửa thông tin kết nối trong `src/main/resources/META-INF/persistence.xml`:

```xml
<property name="jakarta.persistence.jdbc.url" 
          value="jdbc:mysql://localhost:3306/QLNVBCHLapTop"/>
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="12345"/>
```

> ⚠️ **Lưu ý:** `hbm2ddl.auto=update` sẽ tự động tạo/cập nhật bảng khi khởi động. Tuy nhiên, nên chạy `database.sql` trước để đảm bảo schema chính xác.

### 13.3 Build dự án

```bash
mvn clean compile
```

### 13.4 Chạy ứng dụng

**Cách 1 — Maven:**

```bash
mvn exec:java -Dexec.mainClass="com.example.Main"
```

**Cách 2 — IDE:**

Mở project bằng IntelliJ IDEA hoặc Eclipse, tìm và chạy `com.example.Main`.

**Cách 3 — Build JAR rồi chạy:**

```bash
mvn clean package
java -jar target/final-1.0-SNAPSHOT.jar
```

### 13.5 Xác nhận chạy thành công

Khi khởi động, console sẽ hiển thị:

```
Starting Laptop Store Management App...
Connecting to Database via Hibernate JPA...
Database connection successful!
```

Và giao diện Dashboard Dark Mode sẽ xuất hiện.

---

## 14. Dữ Liệu Mẫu

File `mockdata.sql` cung cấp dữ liệu mẫu phong phú để test:

| Bảng | Số bản ghi | Ghi chú |
|---|---|---|
| `NhaCungCap` | 20 | Asus, Dell, Lenovo, HP, Apple, MSI, Acer, Samsung, LG, ... |
| `KhachHang` | 30 | Khách hàng từ các tỉnh thành Việt Nam |
| `SanPham` | 30 | Laptop đa dạng loại: Gaming, Ultrabook, Văn phòng, Workstation |
| `HoaDonBanHang` | 25 | Hóa đơn từ 10/2025 → 03/2026 (cả bán đứt lẫn trả góp) |
| `ChiTietHDBH` | 26 | Chi tiết đơn hàng |
| `ThanhToan` | 30 | Bao gồm cả thanh toán đợt đầu và trả góp theo tháng |

### Phân loại sản phẩm mẫu

- **Gaming** (10 SP): ROG Strix, TUF Gaming, Dell G15, Legion 5 Pro, Nitro 5, Katana, Raider, Victus, Gigabyte G5, Razer Blade
- **Ultrabook** (12 SP): ZenBook OLED, XPS 15, Yoga Slim, MacBook Air/Pro, Swift 3, Galaxy Book, LG Gram, MateBook, Xiaomi Book Pro
- **Văn phòng** (7 SP): Vivobook, Inspiron, IdeaPad, ThinkPad, Pavilion, Modern, Aspire
- **Workstation** (1 SP): Dell Precision 5570

---

## 15. Luồng Hoạt Động Chính

### 15.1 Khởi động ứng dụng

```
Main.main()
  ├── System.setProperty("file.encoding", "UTF-8")     # Force UTF-8
  ├── UIManager.setLookAndFeel(CrossPlatform)           # Look & Feel
  ├── setGlobalFont("Segoe UI", 13)                     # Font toàn cục
  ├── UITheme.applyGlobalTheme()                        # Dark Mode
  ├── HibernateUtil.getEntityManager().close()           # Test DB connection
  └── new HomeView()                                     # Mở Dashboard
```

### 15.2 Tạo hóa đơn mới

```
HomeView → click "Sales Orders"
  → HoaDonBanHangView (danh sách)
    → click "Thêm" → ThemHoaDonBanView
      → Chọn sản phẩm từ danh sách
      → Nhập số lượng
      → Chọn loại (Trả thẳng / Trả góp)
      → [Nếu trả góp] Nhập tiền cọc, thời hạn
      → Controller tính toán tự động
      → XacNhanHoaDonView (xem lại)
      → Xác nhận → Lưu vào DB
```

### 15.3 Xem báo cáo

```
HomeView → click "Reports"
  → ThongKeView
    → Chọn khoảng ngày (DatePicker)
    → click "Generate Report"
    → ThongKeController gọi ThongKeDAO
    → Hiển thị: Tổng doanh thu, Số đơn, Lợi nhuận
    → Bảng Top 5 bán chạy
    → Biểu đồ doanh thu theo ngày (JFreeChart)
```

---

## 16. Lưu Ý Quan Trọng

### ⚠️ Cấu hình

- **Password DB** khác nhau giữa `persistence.xml` (`12345`) và `DatabaseConnection.java` (`12345@`). Cần đồng bộ theo môi trường thực tế.
- **`hbm2ddl.auto=update`**: Hibernate sẽ tự tạo/cập nhật bảng. Trong production nên đổi sang `validate` hoặc `none`.
- Thông tin kết nối đang **hardcode** trong file config.

### ⚠️ Kỹ thuật

- **Encoding**: Ứng dụng force UTF-8 cho toàn bộ JVM để hỗ trợ tiếng Việt đầy đủ.
- **Font**: Sử dụng Segoe UI — cần đảm bảo font này đã cài trên hệ thống (mặc định có trên Windows).
- **Cascade**: `NhaCungCap` có `cascade=ALL` với `SanPham` — xóa NCC sẽ cascade xóa tất cả SP liên quan.
- **Lazy Loading**: Tất cả quan hệ đều dùng `FetchType.LAZY` — cần chú ý `LazyInitializationException` khi truy cập ngoài transaction.

### ⚠️ Chưa triển khai

- Export Excel (đã có nút nhưng chưa có logic)
- Cập nhật `SLTrongKho` khi tạo/xóa hóa đơn
- Unit test / Integration test
- Quản lý phiên đăng nhập (không có authentication)

---

## 17. Định Hướng Phát Triển

### Ngắn hạn

- [ ] Tự động cập nhật số lượng tồn kho khi bán hàng
- [ ] Triển khai Export Excel cho báo cáo
- [ ] Thêm tính năng đăng nhập / phân quyền
- [ ] Chuẩn hóa password DB qua biến môi trường hoặc file `.env`

### Trung hạn

- [ ] Thêm Unit Test (JUnit 5) cho Service và DAO
- [ ] Áp dụng DB migration (Flyway hoặc Liquibase)
- [ ] Thêm tính năng in hóa đơn (PDF export)
- [ ] Bổ sung quản lý bảo hành

### Dài hạn

- [ ] Chuyển đổi sang Spring Boot + Thymeleaf/React
- [ ] Thêm REST API cho mobile app
- [ ] Tích hợp thanh toán online (VNPay, MoMo)
- [ ] Deploy cloud (Docker + MySQL trên AWS/GCP)

---

> **Phiên bản tài liệu**: 2.0  
> **Cập nhật lần cuối**: 30/03/2026  
> **Tác giả**: Team Development
