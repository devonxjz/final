package com.example.dao.impl;

import com.example.config.HibernateUtil;
import com.example.dao.KhachHangDAO;
import com.example.entity.KhachHang;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class KhachHangDAOImpl implements KhachHangDAO {

    @Override
    public List<KhachHang> getAllKhachHang() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT k FROM KhachHang k", KhachHang.class).getResultList();
        }
    }

    @Override
    public KhachHang getById(int maKH) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.find(KhachHang.class, maKH);
        }
    }

    @Override
    public boolean insert(KhachHang kh) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(kh);
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
    public boolean update(KhachHang kh) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(kh);
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
    public boolean delete(int maKH) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            KhachHang kh = em.find(KhachHang.class, maKH);
            if (kh != null) {
                em.remove(kh);
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
    public boolean hasInvoices(int maKH) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String jpql = "SELECT COUNT(t) FROM ThanhToan t WHERE t.khachHang.maKH = :maKH";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("maKH", maKH)
                    .getSingleResult();
            return count > 0;
        }
    }

    @Override
    public List<KhachHang> timKiem(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllKhachHang();
        }
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String jpql = "SELECT k FROM KhachHang k WHERE k.tenKH LIKE :keyword OR k.sdt LIKE :keyword";
            TypedQuery<KhachHang> query = em.createQuery(jpql, KhachHang.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        }
    }

    @Override
    public List<KhachHang> timKiemSDT(String sdt) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String jpql = "SELECT k FROM KhachHang k WHERE k.sdt = :sdt";
            TypedQuery<KhachHang> query = em.createQuery(jpql, KhachHang.class);
            query.setParameter("sdt", sdt);
            return query.getResultList();
        }
    }
}
