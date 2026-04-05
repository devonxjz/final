package com.example.dao.impl;

import com.example.config.HibernateConfig;
import com.example.dao.HoaDonBanHangDAO;
import com.example.entity.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

public class HoaDonBanHangDAOImpl implements HoaDonBanHangDAO {

    @Override
    public List<HoaDonBanHang> getAllHDBH() {
        try (EntityManager em = HibernateConfig.getEntityManager()) {
            // SỬA: Thêm LEFT JOIN FETCH để lấy luôn thông tin Khách hàng và Nhân viên (tránh lỗi LazyInitialization)
            String hql = "SELECT hd FROM HoaDonBanHang hd LEFT JOIN FETCH hd.khachHang LEFT JOIN FETCH hd.nhanVien ORDER BY hd.ngayTao DESC";
            return em.createQuery(hql, HoaDonBanHang.class).getResultList();
        }
    }

    @Override
    public HoaDonBanHang getById(int maHD) {
        try (EntityManager em = HibernateConfig.getEntityManager()) {
            return em.find(HoaDonBanHang.class, maHD);
        }
    }

    @Override
    public boolean saveHoaDonFull(HoaDonBanHang hd) {
        EntityManager em = HibernateConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(hd);

            if (hd.getDanhSachChiTiet() != null) {
                for (var ct : hd.getDanhSachChiTiet()) {
                    var sp = ct.getSanPham();
                    sp.setSoLuongTrongKho(sp.getSoLuongTrongKho() - ct.getSoLuong());
                    em.merge(sp);
                }
            }
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public List<HoaDonBanHang> timKiemTheoTrangThai(String keyword) {
        try (EntityManager em = HibernateConfig.getEntityManager()) {
            TypedQuery<HoaDonBanHang> q = em.createQuery("SELECT hd FROM HoaDonBanHang hd LEFT JOIN FETCH hd.khachHang LEFT JOIN FETCH hd.nhanVien WHERE hd.trangThai LIKE :kw", HoaDonBanHang.class);
            q.setParameter("kw", "%" + keyword + "%");
            return q.getResultList();
        }
    }

    @Override
    public List<HoaDonBanHang> timKiem(java.util.Date date) {
        try (EntityManager em = HibernateConfig.getEntityManager()) {
            // SỬA LỖI: Dùng cast(... as date) thay cho FUNC('DATE', ...)
            String hql = "SELECT hd FROM HoaDonBanHang hd " +
                "LEFT JOIN FETCH hd.khachHang " +
                "LEFT JOIN FETCH hd.nhanVien " +
                "WHERE cast(hd.ngayTao as date) = cast(:dt as date)";

            TypedQuery<HoaDonBanHang> q = em.createQuery(hql, HoaDonBanHang.class);
            q.setParameter("dt", date);
            return q.getResultList();
        }
    }

    @Override
    public List<ChiTietHDBH> getAllChiTiet(int maHD) {
        try (EntityManager em = HibernateConfig.getEntityManager()) {
            // Dùng JOIN FETCH để ép Hibernate lấy luôn thông tin Sản Phẩm
            String jpql = "SELECT c FROM ChiTietHDBH c JOIN FETCH c.sanPham WHERE c.hoaDonBanHang.maHDBH = :maHD";
            return em.createQuery(jpql, ChiTietHDBH.class)
                .setParameter("maHD", maHD)
                .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }

    @Override
    public List<ThanhToan> getAllThanhToan(int maHD) {
        try (EntityManager em = HibernateConfig.getEntityManager()) {
            // Dùng JOIN FETCH để ép Hibernate lấy luôn thông tin Khách Hàng
            String jpql = "SELECT t FROM ThanhToan t JOIN FETCH t.khachHang WHERE t.hoaDon.maHDBH = :maHD";
            return em.createQuery(jpql, ThanhToan.class)
                .setParameter("maHD", maHD)
                .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }

    @Override
    public KhachHang timKiemKhachHangTheoSDT(String sdt) {
        try (EntityManager em = HibernateConfig.getEntityManager()) {
            TypedQuery<KhachHang> q = em.createQuery("SELECT k FROM KhachHang k WHERE k.sdt = :sdt", KhachHang.class);
            q.setParameter("sdt", sdt);
            List<KhachHang> list = q.getResultList();
            return list.isEmpty() ? null : list.get(0);
        }
    }

    @Override
    public List<SanPham> getAllSanPham() {
        try (EntityManager em = HibernateConfig.getEntityManager()) {
            return em.createQuery("SELECT sp FROM SanPham sp", SanPham.class).getResultList();
        }
    }

    // SỬA: Bổ sung tham số int maNV vào hàm
    @Override
    public boolean themHoaDonVaChiTietVaThanhToan(java.util.Date ngayTao, String loaiHD, double tongTien, int maKH, int maNV,
                                                  List<Map<Integer, Integer>> gioHang, String tenKH, String sdt, String diaChi,
                                                  String gioiTinh, String hinhThucTT, String trangThai) {

        EntityManager em = HibernateConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 1. Tìm hoặc tạo khách hàng
            KhachHang kh = null;
            if (sdt != null && !sdt.isEmpty()) {
                TypedQuery<KhachHang> q = em.createQuery("SELECT k FROM KhachHang k WHERE k.sdt = :sdt", KhachHang.class);
                q.setParameter("sdt", sdt);
                List<KhachHang> khList = q.getResultList();
                if (!khList.isEmpty()) {
                    kh = khList.get(0);
                    if (tenKH != null && !tenKH.isEmpty()) kh.setTenKH(tenKH);
                    if (diaChi != null && !diaChi.isEmpty()) kh.setDiaChi(diaChi);
                    if (gioiTinh != null && !gioiTinh.isEmpty()) kh.setGioiTinh(gioiTinh);
                    em.merge(kh);
                }
            }
            if (kh == null) {
                kh = new KhachHang();
                kh.setTenKH(tenKH);
                kh.setSdt(sdt);
                kh.setDiaChi(diaChi);
                kh.setGioiTinh(gioiTinh);
                em.persist(kh);
            }

            // BỔ SUNG: Tìm nhân viên lập hóa đơn
            NhanVien nv = em.find(NhanVien.class, maNV);
            if (nv == null) throw new RuntimeException("Không tìm thấy thông tin nhân viên lập đơn!");

            // 2. Tạo hóa đơn bán hàng
            HoaDonBanHang hd = new HoaDonBanHang();
            hd.setNgayTao(ngayTao);
            hd.setLoaiHD(loaiHD);
            hd.setTongTien(tongTien);
            hd.setTrangThai("Trả thẳng".equals(loaiHD) ? "Đã thanh toán" : "Đang trả góp");
            hd.setSoTienConLai("Trả thẳng".equals(loaiHD) ? 0.0 : tongTien);
            hd.setTienCoc(0.0);

            // BỔ SUNG: Gán khách hàng và nhân viên cho Hóa Đơn
            hd.setKhachHang(kh);
            hd.setNhanVien(nv);

            em.persist(hd);
            em.flush(); // Đảm bảo hd có MaHDBH

            // 3. Tạo chi tiết hóa đơn cho từng sản phẩm
            for (Map<Integer, Integer> item : gioHang) {
                for (Map.Entry<Integer, Integer> entry : item.entrySet()) {
                    int maSP = entry.getKey();
                    int soLuong = entry.getValue();

                    SanPham sp = em.find(SanPham.class, maSP);
                    if (sp == null) throw new RuntimeException("Không tìm thấy sản phẩm có mã: " + maSP);
                    if (sp.getSoLuongTrongKho() < soLuong) throw new RuntimeException("Sản phẩm '" + sp.getTenSP() + "' không đủ hàng trong kho!");

                    ChiTietHDBH ct = new ChiTietHDBH();
                    ct.setHoaDonBanHang(hd); // Vẫn giữ nguyên vì entity ChiTietHDBH.java của bạn là hoaDonBanHang
                    ct.setSanPham(sp);
                    ct.setSoLuong(soLuong);
                    ct.setTongTien(sp.getGiaBan() * soLuong);
                    em.persist(ct);

                    sp.setSoLuongTrongKho(sp.getSoLuongTrongKho() - soLuong);
                    em.merge(sp);
                }
            }

            // 4. Tạo bản ghi thanh toán đầu tiên
            ThanhToan tt = new ThanhToan();
            tt.setHoaDon(hd); // SỬA: Đổi từ setHoaDonBanHang thành setHoaDon
            tt.setKhachHang(kh);
            tt.setNhanVien(nv); // BỔ SUNG: Gán nhân viên cho bản ghi thanh toán
            tt.setNgayTT(ngayTao);
            tt.setHinhThucTT(hinhThucTT);

            if ("Trả thẳng".equals(loaiHD)) {
                tt.setTienThanhToan(tongTien);
                hd.setSoTienConLai(0.0);
                hd.setTrangThai("Đã thanh toán");
            } else {
                tt.setTienThanhToan(0.0);
                hd.setSoTienConLai(tongTien);
                hd.setTrangThai("Đang trả góp");
            }
            em.persist(tt);
            em.merge(hd);

            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
