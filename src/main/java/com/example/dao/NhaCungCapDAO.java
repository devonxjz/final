package com.example.dao;

import com.example.config.HibernateUtil;
import com.example.entity.NhaCungCap;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Data Access Object cho Nhà Cung Cấp sử dụng JPA/Hibernate
 */
public class NhaCungCapDAO {

    /** Lấy danh sách tất cả nhà cung cấp */
    public List<NhaCungCap> getAllNhaCungCap() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT n FROM NhaCungCap n", NhaCungCap.class).getResultList();
        }
    }

    /** Thêm nhà cung cấp mới */
    public boolean themNhaCungCap(String tenNCC, String diaChi, String sdt) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            NhaCungCap ncc = new NhaCungCap();
            ncc.setTenNCC(tenNCC);
            ncc.setDiaChi(diaChi);
            ncc.setSdt(sdt);
            em.persist(ncc);
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

    /** Xóa nhà cung cấp theo mã */
    public boolean xoaNhaCungCap(int maNCC) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            NhaCungCap ncc = em.find(NhaCungCap.class, maNCC);
            if (ncc != null) {
                em.remove(ncc);
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

    /** Cập nhật thông tin nhà cung cấp */
    public boolean capNhatNhaCungCap(int maNCC, String tenNCC, String diaChi, String sdt) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            NhaCungCap ncc = em.find(NhaCungCap.class, maNCC);
            if (ncc != null) {
                ncc.setTenNCC(tenNCC);
                ncc.setDiaChi(diaChi);
                ncc.setSdt(sdt);
                em.merge(ncc);
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

    /** Kiểm tra nhà cung cấp có sản phẩm liên quan không trước khi xóa */
    public boolean kiemTraNhaCungCapDuocXoa(String maNCC) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String jpql = "SELECT COUNT(sp) FROM SanPham sp WHERE sp.nhaCungCap.maNCC = :maNCC";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("maNCC", Integer.parseInt(maNCC))
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}