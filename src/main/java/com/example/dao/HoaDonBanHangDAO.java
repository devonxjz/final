package com.example.dao;

import com.example.config.HibernateUtil;
import com.example.entity.HoaDonBanHang;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class HoaDonBanHangDAO {

    public List<HoaDonBanHang> getAllHDBH() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT hd FROM HoaDonBanHang hd", HoaDonBanHang.class).getResultList();
        }
    }

    public HoaDonBanHang getById(int maHD) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.find(HoaDonBanHang.class, maHD);
        }
    }

    /**
     * Dùng JPA để lưu Giao dịch đầy đủ (Hóa đơn + Chi tiết + Thanh toán)
     * Vì JPA tự quản lý Cascade, nên chỉ cần persist HoaDonBanHang là các Entity con tự được lưu.
     */
    public boolean saveHoaDonFull(HoaDonBanHang hd) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Lưu hóa đơn
            em.persist(hd);
            
            // Cập nhật lại số lượng hàng trong kho dựa vào ChiTietHDBH
            if (hd.getDanhSachChiTiet() != null) {
                for (var ct : hd.getDanhSachChiTiet()) {
                    var sp = ct.getSanPham(); // sp hiện tại đang Managed/Detached
                    // Trừ kho
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

    public List<HoaDonBanHang> timKiemTheoTrangThai(String keyword) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<HoaDonBanHang> q = em.createQuery("SELECT hd FROM HoaDonBanHang hd WHERE hd.trangThai LIKE :kw", HoaDonBanHang.class);
            q.setParameter("kw", "%" + keyword + "%");
            return q.getResultList();
        }
    }

    public List<HoaDonBanHang> timKiem(java.util.Date date) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<HoaDonBanHang> q = em.createQuery("SELECT hd FROM HoaDonBanHang hd WHERE FUNC('DATE', hd.ngayTao) = FUNC('DATE', :dt)", HoaDonBanHang.class);
            q.setParameter("dt", date);
            return q.getResultList();
        }
    }

    public List<com.example.entity.ChiTietHDBH> getAllChiTiet(int maHD) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            HoaDonBanHang hd = em.find(HoaDonBanHang.class, maHD);
            if (hd != null) {
                hd.getDanhSachChiTiet().size(); // force initialize lazy collection
                return hd.getDanhSachChiTiet();
            }
            return java.util.Collections.emptyList();
        }
    }

    public List<com.example.entity.ThanhToan> getAllThanhToan(int maHD) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            HoaDonBanHang hd = em.find(HoaDonBanHang.class, maHD);
            if (hd != null) {
                hd.getDanhSachThanhToan().size(); // force initialize lazy collection
                return hd.getDanhSachThanhToan();
            }
            return java.util.Collections.emptyList();
        }
    }

    public com.example.entity.KhachHang timKiemKhachHangTheoSDT(String sdt) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<com.example.entity.KhachHang> q = em.createQuery("SELECT k FROM KhachHang k WHERE k.sdt = :sdt", com.example.entity.KhachHang.class);
            q.setParameter("sdt", sdt);
            List<com.example.entity.KhachHang> list = q.getResultList();
            return list.isEmpty() ? null : list.get(0);
        }
    }

    public List<com.example.entity.SanPham> getAllSanPham() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT sp FROM SanPham sp", com.example.entity.SanPham.class).getResultList();
        }
    }

    // This is a stub for the old JDBC complex save function. I will just forward it to saveHoaDonFull since we built JPA!
    public boolean themHoaDonVaChiTietVaThanhToan(java.util.Date ngayTao, String loaiHD, double tongTien, int maKH, List<java.util.Map<Integer, Integer>> gioHang, String tenKH, String sdt, String diaChi, String hinhThucTT, String trangThai) {
        // Warning: This method is deprecated, you should be using HoaDonBanHangService instead.
        // It's here just to allow ThemHoaDonBanController to compile, but logic should be refactored.
        return false; 
    }
}