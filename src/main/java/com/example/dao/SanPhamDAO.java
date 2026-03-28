package com.example.dao;

import com.example.config.HibernateUtil;
import com.example.entity.SanPham;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Data Access Object cho Sản Phẩm bằng JPA/Hibernate
 */
public class SanPhamDAO {

    /** Lấy tất cả sản phẩm, eager fetch nhà cung cấp */
    public List<SanPham> getAllSanPham() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT s FROM SanPham s LEFT JOIN FETCH s.nhaCungCap", SanPham.class).getResultList();
        }
    }

    /** Tìm sản phẩm theo ID */
    public SanPham getById(int maSP) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.find(SanPham.class, maSP);
        }
    }

    /** Thêm hoặc cập nhật sản phẩm */
    public boolean insertOrUpdate(SanPham sp) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (sp.getMaSP() == null || sp.getMaSP() == 0) {
                em.persist(sp);
            } else {
                em.merge(sp);
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

    /** Xóa sản phẩm theo mã */
    public boolean xoaSanPham(int maSP) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            SanPham sp = em.find(SanPham.class, maSP);
            if (sp != null) {
                em.remove(sp);
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

    /** Tìm kiếm sản phẩm theo tên sử dụng JPQL */
    public List<SanPham> timKiem(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllSanPham();
        }
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String jpql = "SELECT s FROM SanPham s LEFT JOIN FETCH s.nhaCungCap WHERE UPPER(s.tenSP) LIKE UPPER(:keyword)";
            TypedQuery<SanPham> query = em.createQuery(jpql, SanPham.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        }
    }
}