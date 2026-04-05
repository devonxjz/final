package com.example.dao.impl;

import com.example.config.HibernateConfig;
import com.example.dao.ThanhToanDAO;
import com.example.entity.HoaDonBanHang;
import com.example.entity.KhachHang;
import com.example.entity.NhanVien;
import com.example.entity.ThanhToan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThanhToanDAOImpl implements ThanhToanDAO {

    @Override
    public List<Map<String, Object>> getAllThanhToanAsMap() {
        List<Map<String, Object>> list = new ArrayList<>();
        try (EntityManager em = HibernateConfig.getEntityManager()) {
            // SỬA: Đổi thành LEFT JOIN và lấy thêm h.trangThai
            String jpql = "SELECT t.maTT, h.maHDBH, k.maKH, t.ngayTT, t.tienThanhToan, t.hinhThucTT, k.tenKH, n.tenNV, h.trangThai " +
                "FROM ThanhToan t LEFT JOIN t.khachHang k LEFT JOIN t.hoaDon h LEFT JOIN t.nhanVien n ORDER BY t.ngayTT DESC";
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
                row.put("tenNV", r[7]);
                row.put("trangThai", r[8]); // Bổ sung trạng thái để chặn xóa
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // BỔ SUNG: Hàm sửa thanh toán
    @Override
    public boolean updateThanhToan(int maTT, java.util.Date ngayTT, double tienMoi, String hinhThucTT) {
        EntityManager em = HibernateConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ThanhToan tt = em.find(ThanhToan.class, maTT);
            if (tt == null) return false;

            // Cập nhật thông tin mới
            tt.setNgayTT(ngayTT);
            tt.setTienThanhToan(tienMoi);
            tt.setHinhThucTT(hinhThucTT);
            em.merge(tt);

            // Tự động tính toán lại nợ cho hóa đơn
            HoaDonBanHang hd = tt.getHoaDon();
            double tongDaTra = tinhTongTienDaTra(em, hd.getMaHDBH());
            double conNo = hd.getTongTien() - tongDaTra;

            hd.setTrangThai(conNo <= 0 ? "Đã thanh toán" : "Đang trả góp");
            em.merge(hd);

            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Cập nhật hàm thêm thanh toán có tham số maNV
    public boolean themThanhToan(int maHDBH, int maKH, int maNV, java.util.Date ngayTT, double tienThanhToan, String hinhThucTT) {
        EntityManager em = HibernateConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            HoaDonBanHang hd = em.find(HoaDonBanHang.class, maHDBH);
            KhachHang kh = em.find(KhachHang.class, maKH);
            NhanVien nv = em.find(NhanVien.class, maNV); // Tìm nhân viên thực hiện giao dịch

            if (hd == null || kh == null || nv == null) {
                return false;
            }

            ThanhToan tt = new ThanhToan();
            tt.setHoaDon(hd); // Giữ đúng tên setter trong Entity của bạn
            tt.setKhachHang(kh);
            tt.setNhanVien(nv); // Gán nhân viên vào hóa đơn thanh toán
            tt.setNgayTT(ngayTT);
            tt.setTienThanhToan(tienThanhToan);
            tt.setHinhThucTT(hinhThucTT);

            em.persist(tt);

            // Logic tính toán nợ cũ của bạn
            double tongDaTra = tinhTongTienDaTra(em, maHDBH);
            double conNo = hd.getTongTien() - tongDaTra;

            if (conNo <= 0) {
                hd.setTrangThai("Đã thanh toán");
            } else {
                hd.setTrangThai("Đang trả góp");
            }
            em.merge(hd);

            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(int maTT) {
        EntityManager em = HibernateConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ThanhToan tt = em.find(ThanhToan.class, maTT);
            if (tt != null) {
                em.remove(tt);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    private double tinhTongTienDaTra(EntityManager em, int maHD) {
        String jpql = "SELECT SUM(t.tienThanhToan) FROM ThanhToan t WHERE t.hoaDon.maHDBH = :maHD";
        Double res = em.createQuery(jpql, Double.class)
            .setParameter("maHD", maHD)
            .getSingleResult();
        return res != null ? res : 0.0;
    }

    // Giữ nguyên các hàm cũ cần thiết khác...
    @Override
    public List<Map<String, Object>> getAllHoaDonDangGopAsMap() {
        List<Map<String, Object>> list = new ArrayList<>();
        try (EntityManager em = HibernateConfig.getEntityManager()) {
            String jpql = "SELECT h.maHDBH, k.maKH, k.tenKH, h.tongTien, h.ngayTao " +
                "FROM HoaDonBanHang h JOIN h.khachHang k " +
                "WHERE h.trangThai = 'Đang trả góp'";
            List<Object[]> results = em.createQuery(jpql, Object[].class).getResultList();
            for (Object[] r : results) {
                Map<String, Object> row = new HashMap<>();
                row.put("maHDBH", r[0]);
                row.put("maKH", r[1]);
                row.put("tenKH", r[2]);
                row.put("tongTien", r[3]);
                row.put("ngayTao", r[4]);
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ThanhToan> findByHoaDon(int maHD) {
        try (EntityManager em = HibernateConfig.getEntityManager()) {
            // SỬA LỖI: Dùng JOIN FETCH để lấy luôn thông tin Khách hàng và Hóa đơn trước khi đóng Session
            String jpql = "SELECT t FROM ThanhToan t " +
                "JOIN FETCH t.hoaDon h " +
                "JOIN FETCH t.khachHang k " +
                "WHERE h.maHDBH = :maHD ORDER BY t.ngayTT ASC";

            return em.createQuery(jpql, ThanhToan.class)
                .setParameter("maHD", maHD)
                .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
