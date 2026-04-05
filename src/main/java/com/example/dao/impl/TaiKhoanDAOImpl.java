package com.example.dao.impl;

import com.example.config.HibernateConfig;
import com.example.dao.TaiKhoanDAO;
import com.example.entity.TaiKhoan;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TaiKhoanDAOImpl implements TaiKhoanDAO {
    @Override
    public TaiKhoan findByUsername(String username) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from TaiKhoan t where t.tenDangNhap = :user", TaiKhoan.class)
                .setParameter("user", username)
                .uniqueResult();
        }
    }

    @Override
    public void saveOrUpdate(TaiKhoan tk) {
        Transaction tx = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(tk);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
