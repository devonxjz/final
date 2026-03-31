package com.example.dao.impl;

import com.example.config.HibernateConfig;
import com.example.dao.NhaCungCapDAO;
import com.example.entity.NhaCungCap;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class NhaCungCapDAOImpl implements NhaCungCapDAO {

    @Override
    public List<NhaCungCap> getAllNhaCungCap() {
        try (EntityManager em = HibernateConfig.getEntityManager()) {
            return em.createQuery("SELECT n FROM NhaCungCap n", NhaCungCap.class).getResultList();
        }
    }

    @Override
    public boolean themNhaCungCap(String tenNCC, String diaChi, String sdt) {
        EntityManager em = HibernateConfig.getEntityManager();
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

    @Override
    public boolean xoaNhaCungCap(int maNCC) {
        EntityManager em = HibernateConfig.getEntityManager();
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

    @Override
    public boolean capNhatNhaCungCap(int maNCC, String tenNCC, String diaChi, String sdt) {
        EntityManager em = HibernateConfig.getEntityManager();
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

    @Override
    public boolean kiemTraNhaCungCapDuocXoa(String maNCC) {
        try (EntityManager em = HibernateConfig.getEntityManager()) {
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
