package com.example.dao.impl;

import com.example.config.HibernateUtil;
import com.example.dao.SanPhamDAO;
import com.example.entity.SanPham;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class SanPhamDAOImpl implements SanPhamDAO {

    @Override
    public List<SanPham> getAllSanPham() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT s FROM SanPham s LEFT JOIN FETCH s.nhaCungCap", SanPham.class).getResultList();
        }
    }

    @Override
    public SanPham getById(int maSP) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.find(SanPham.class, maSP);
        }
    }

    @Override
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

    @Override
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

    @Override
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
