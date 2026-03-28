package com.example.dao;

import com.example.config.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class ThongKeDAO {

    public double getTongDoanhThu(String tuNgay, String denNgay) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String jpql = "SELECT SUM(h.tongTien) FROM HoaDonBanHang h WHERE h.trangThai != 'Đã hủy'";
            if (tuNgay != null && denNgay != null && !tuNgay.isEmpty() && !denNgay.isEmpty()) {
                jpql += " AND h.ngayTao BETWEEN :tuNgay AND :denNgay";
            }
            Query query = em.createQuery(jpql);
            if (tuNgay != null && denNgay != null && !tuNgay.isEmpty() && !denNgay.isEmpty()) {
                query.setParameter("tuNgay", java.sql.Date.valueOf(tuNgay));
                query.setParameter("denNgay", java.sql.Date.valueOf(denNgay));
            }
            Double result = (Double) query.getSingleResult();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getTongDonHang(String tuNgay, String denNgay) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String jpql = "SELECT COUNT(h) FROM HoaDonBanHang h WHERE h.trangThai != 'Đã hủy'";
            if (tuNgay != null && denNgay != null && !tuNgay.isEmpty() && !denNgay.isEmpty()) {
                jpql += " AND h.ngayTao BETWEEN :tuNgay AND :denNgay";
            }
            Query query = em.createQuery(jpql);
            if (tuNgay != null && denNgay != null && !tuNgay.isEmpty() && !denNgay.isEmpty()) {
                query.setParameter("tuNgay", java.sql.Date.valueOf(tuNgay));
                query.setParameter("denNgay", java.sql.Date.valueOf(denNgay));
            }
            Long result = (Long) query.getSingleResult();
            return result != null ? result.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public double getLoiNhuan(String tuNgay, String denNgay) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String jpql = "SELECT SUM(c.tongTien - (s.giaNhap * c.soLuong)) " +
                          "FROM ChiTietHDBH c JOIN c.hoaDonBanHang h JOIN c.sanPham s " +
                          "WHERE h.trangThai != 'Đã hủy'";
            if (tuNgay != null && denNgay != null && !tuNgay.isEmpty() && !denNgay.isEmpty()) {
                jpql += " AND h.ngayTao BETWEEN :tuNgay AND :denNgay";
            }
            Query query = em.createQuery(jpql);
            if (tuNgay != null && denNgay != null && !tuNgay.isEmpty() && !denNgay.isEmpty()) {
                query.setParameter("tuNgay", java.sql.Date.valueOf(tuNgay));
                query.setParameter("denNgay", java.sql.Date.valueOf(denNgay));
            }
            Double result = (Double) query.getSingleResult();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Map<String, Integer> getTopSellingProducts(String tuNgay, String denNgay) {
        Map<String, Integer> map = new LinkedHashMap<>();
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String jpql = "SELECT c.sanPham.tenSP, SUM(c.soLuong) as total FROM ChiTietHDBH c JOIN c.hoaDonBanHang h " +
                          "WHERE h.trangThai != 'Đã hủy' ";
            if (tuNgay != null && denNgay != null && !tuNgay.isEmpty() && !denNgay.isEmpty()) {
                jpql += " AND h.ngayTao BETWEEN :tuNgay AND :denNgay ";
            }
            jpql += " GROUP BY c.sanPham.tenSP ORDER BY total DESC";
            
            Query query = em.createQuery(jpql).setMaxResults(5);
            if (tuNgay != null && denNgay != null && !tuNgay.isEmpty() && !denNgay.isEmpty()) {
                query.setParameter("tuNgay", java.sql.Date.valueOf(tuNgay));
                query.setParameter("denNgay", java.sql.Date.valueOf(denNgay));
            }
            
            List<Object[]> results = query.getResultList();
            for (Object[] r : results) {
                map.put((String) r[0], ((Long) r[1]).intValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map<String, Double> getRevenueByDay(String tuNgay, String denNgay) {
        Map<String, Double> map = new LinkedHashMap<>();
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String jpql = "SELECT FUNC('DATE_FORMAT', h.ngayTao, '%Y-%m-%d'), SUM(h.tongTien) FROM HoaDonBanHang h WHERE h.trangThai != 'Đã hủy' ";
            if (tuNgay != null && denNgay != null && !tuNgay.isEmpty() && !denNgay.isEmpty()) {
                jpql += " AND h.ngayTao BETWEEN :tuNgay AND :denNgay ";
            }
            jpql += " GROUP BY FUNC('DATE_FORMAT', h.ngayTao, '%Y-%m-%d') ORDER BY FUNC('DATE_FORMAT', h.ngayTao, '%Y-%m-%d') ASC";
            
            Query query = em.createQuery(jpql);
            if (tuNgay != null && denNgay != null && !tuNgay.isEmpty() && !denNgay.isEmpty()) {
                query.setParameter("tuNgay", java.sql.Date.valueOf(tuNgay));
                query.setParameter("denNgay", java.sql.Date.valueOf(denNgay));
            }
            
            List<Object[]> results = query.getResultList();
            for (Object[] r : results) {
                map.put(r[0].toString(), (Double) r[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
