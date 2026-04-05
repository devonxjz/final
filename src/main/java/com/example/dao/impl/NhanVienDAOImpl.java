package com.example.dao.impl;

import com.example.config.HibernateConfig;
import com.example.dao.NhanVienDAO;
import com.example.entity.NhanVien;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class NhanVienDAOImpl implements NhanVienDAO {
    @Override
    public List<NhanVien> getAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from NhanVien", NhanVien.class).list();
        }
    }

    @Override
    public NhanVien getById(int id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(NhanVien.class, id);
        }
    }

    @Override
    public void saveOrUpdate(NhanVien nv) {
        Transaction tx = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(nv);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        Transaction tx = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            NhanVien nv = session.get(NhanVien.class, id);
            if (nv != null) session.remove(nv);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
