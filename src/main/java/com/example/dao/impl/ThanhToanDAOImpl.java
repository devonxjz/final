package com.example.dao.impl;

import com.example.config.HibernateUtil;
import com.example.dao.ThanhToanDAO;
import com.example.entity.HoaDonBanHang;
import com.example.entity.KhachHang;
import com.example.entity.ThanhToan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThanhToanDAOImpl implements ThanhToanDAO {

    @Override
    public List<Map<String, Object>> getAllThanhToanAsMap() {
        List<Map<String, Object>> list = new ArrayList<>();
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String jpql = "SELECT t.maTT, h.maHDBH, k.maKH, t.ngayTT, t.tienThanhToan, t.hinhThucTT, k.tenKH " +
                          "FROM ThanhToan t JOIN t.khachHang k JOIN t.hoaDonBanHang h";
            List<Object[]> results = em.createQuery(jpql, Object[].class).getResultList();
            for (Object[] r : results) {
                Map<String, Object> row = new HashMap<>();
                row.put("maTT", r[0]);
                row.put("maHDBH", r[1]);
                row.put("maKH", r[2]);
                row.put("ngayTT", r[3]);
                row.put("tienThanhToan", r[4]);
                row.put("hinhThucTT", r[5]);
                row.put("tenKH", r[6]);
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean themThanhToan(int maHDBH, int maKH, java.util.Date ngayTT, double tienThanhToan, String hinhThucTT) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            HoaDonBanHang hd = em.find(HoaDonBanHang.class, maHDBH);
            KhachHang kh = em.find(KhachHang.class, maKH);
            if (hd == null || kh == null) {
                return false;
            }
            ThanhToan tt = new ThanhToan();
            tt.setHoaDonBanHang(hd);
            tt.setKhachHang(kh);
            tt.setNgayTT(ngayTT);
            tt.setTienThanhToan(tienThanhToan);
            tt.setHinhThucTT(hinhThucTT);
            em.persist(tt);
            
            // Cập nhật lại số tiền nợ
            double nợCòn = hd.getSoTienConLai() - tienThanhToan;
            if (nợCòn <= 0) {
                hd.setSoTienConLai(0.0);
                hd.setTrangThai("Đã thanh toán");
            } else {
                hd.setSoTienConLai(nợCòn);
            }
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

    @Override
    public List<Map<String, Object>> getAllHoaDonDangGopAsMap() {
        List<Map<String, Object>> list = new ArrayList<>();
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String jpql = "SELECT h.maHDBH, MIN(k.maKH), MIN(k.tenKH), h.tongTien, h.tienCoc, h.tienGopHangThang, h.ngayTao " +
                          "FROM ThanhToan t JOIN t.hoaDonBanHang h JOIN t.khachHang k " +
                          "WHERE h.trangThai = 'Đang trả góp' " +
                          "GROUP BY h.maHDBH, h.tongTien, h.tienCoc, h.tienGopHangThang, h.ngayTao";
            List<Object[]> results = em.createQuery(jpql, Object[].class).getResultList();
            for (Object[] r : results) {
                Map<String, Object> row = new HashMap<>();
                row.put("maHDBH", r[0]);
                row.put("maKH", r[1]);
                row.put("tenKH", r[2]);
                row.put("tongTien", r[3]);
                row.put("tienCoc", r[4]);
                row.put("tienGopThang", r[5]);
                row.put("ngayTao", r[6]);
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ThanhToan> layTatCaThanhToan() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT t FROM ThanhToan t JOIN FETCH t.hoaDonBanHang h JOIN FETCH t.khachHang k", ThanhToan.class).getResultList();
        }
    }
}
