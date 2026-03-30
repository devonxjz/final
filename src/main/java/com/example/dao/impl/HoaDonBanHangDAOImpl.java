package com.example.dao.impl;

import com.example.config.HibernateUtil;
import com.example.dao.HoaDonBanHangDAO;
import com.example.entity.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

public class HoaDonBanHangDAOImpl implements HoaDonBanHangDAO {

    @Override
    public List<HoaDonBanHang> getAllHDBH() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT hd FROM HoaDonBanHang hd", HoaDonBanHang.class).getResultList();
        }
    }

    @Override
    public HoaDonBanHang getById(int maHD) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.find(HoaDonBanHang.class, maHD);
        }
    }

    @Override
    public boolean saveHoaDonFull(HoaDonBanHang hd) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(hd);
            
            if (hd.getDanhSachChiTiet() != null) {
                for (var ct : hd.getDanhSachChiTiet()) {
                    var sp = ct.getSanPham();
                    sp.setSoLuongTrongKho(sp.getSoLuongTrongKho() - ct.getSoLuong());
                    em.merge(sp);
                }
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
    public List<HoaDonBanHang> timKiemTheoTrangThai(String keyword) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<HoaDonBanHang> q = em.createQuery("SELECT hd FROM HoaDonBanHang hd WHERE hd.trangThai LIKE :kw", HoaDonBanHang.class);
            q.setParameter("kw", "%" + keyword + "%");
            return q.getResultList();
        }
    }

    @Override
    public List<HoaDonBanHang> timKiem(java.util.Date date) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<HoaDonBanHang> q = em.createQuery("SELECT hd FROM HoaDonBanHang hd WHERE FUNC('DATE', hd.ngayTao) = FUNC('DATE', :dt)", HoaDonBanHang.class);
            q.setParameter("dt", date);
            return q.getResultList();
        }
    }

    @Override
    public List<ChiTietHDBH> getAllChiTiet(int maHD) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            HoaDonBanHang hd = em.find(HoaDonBanHang.class, maHD);
            if (hd != null) {
                hd.getDanhSachChiTiet().size();
                return hd.getDanhSachChiTiet();
            }
            return java.util.Collections.emptyList();
        }
    }

    @Override
    public List<ThanhToan> getAllThanhToan(int maHD) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            HoaDonBanHang hd = em.find(HoaDonBanHang.class, maHD);
            if (hd != null) {
                hd.getDanhSachThanhToan().size();
                return hd.getDanhSachThanhToan();
            }
            return java.util.Collections.emptyList();
        }
    }

    @Override
    public KhachHang timKiemKhachHangTheoSDT(String sdt) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<KhachHang> q = em.createQuery("SELECT k FROM KhachHang k WHERE k.sdt = :sdt", KhachHang.class);
            q.setParameter("sdt", sdt);
            List<KhachHang> list = q.getResultList();
            return list.isEmpty() ? null : list.get(0);
        }
    }

    @Override
    public List<SanPham> getAllSanPham() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT sp FROM SanPham sp", SanPham.class).getResultList();
        }
    }

    @Override
    public boolean themHoaDonVaChiTietVaThanhToan(java.util.Date ngayTao, String loaiHD, double tongTien, int maKH,
            List<Map<Integer, Integer>> gioHang, String tenKH, String sdt, String diaChi,
            String gioiTinh, String hinhThucTT, String trangThai) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 1. Tìm hoặc tạo khách hàng
            KhachHang kh = null;
            if (sdt != null && !sdt.isEmpty()) {
                TypedQuery<KhachHang> q = em.createQuery("SELECT k FROM KhachHang k WHERE k.sdt = :sdt", KhachHang.class);
                q.setParameter("sdt", sdt);
                List<KhachHang> khList = q.getResultList();
                if (!khList.isEmpty()) {
                    kh = khList.get(0);
                    // Cập nhật thông tin nếu cần
                    if (tenKH != null && !tenKH.isEmpty()) kh.setTenKH(tenKH);
                    if (diaChi != null && !diaChi.isEmpty()) kh.setDiaChi(diaChi);
                    if (gioiTinh != null && !gioiTinh.isEmpty()) kh.setGioiTinh(gioiTinh);
                    em.merge(kh);
                }
            }
            if (kh == null) {
                kh = new KhachHang();
                kh.setTenKH(tenKH);
                kh.setSdt(sdt);
                kh.setDiaChi(diaChi);
                kh.setGioiTinh(gioiTinh);
                em.persist(kh);
            }

            // 2. Tạo hóa đơn bán hàng
            HoaDonBanHang hd = new HoaDonBanHang();
            hd.setNgayTao(ngayTao);
            hd.setLoaiHD(loaiHD);
            hd.setTongTien(tongTien);
            hd.setTrangThai("Trả thẳng".equals(loaiHD) ? "Đã thanh toán" : "Đang trả góp");
            hd.setSoTienConLai("Trả thẳng".equals(loaiHD) ? 0.0 : tongTien);
            hd.setTienCoc(0.0);
            em.persist(hd);
            em.flush(); // Đảm bảo hd có MaHDBH

            // 3. Tạo chi tiết hóa đơn cho từng sản phẩm trong giỏ hàng
            for (Map<Integer, Integer> item : gioHang) {
                for (Map.Entry<Integer, Integer> entry : item.entrySet()) {
                    int maSP = entry.getKey();
                    int soLuong = entry.getValue();

                    SanPham sp = em.find(SanPham.class, maSP);
                    if (sp == null) {
                        throw new RuntimeException("Không tìm thấy sản phẩm có mã: " + maSP);
                    }

                    // Kiểm tra tồn kho
                    if (sp.getSoLuongTrongKho() < soLuong) {
                        throw new RuntimeException("Sản phẩm '" + sp.getTenSP() + "' không đủ hàng trong kho!");
                    }

                    // Tạo chi tiết
                    ChiTietHDBH ct = new ChiTietHDBH();
                    ct.setHoaDonBanHang(hd);
                    ct.setSanPham(sp);
                    ct.setSoLuong(soLuong);
                    ct.setTongTien(sp.getGiaBan() * soLuong);
                    em.persist(ct);

                    // Trừ kho
                    sp.setSoLuongTrongKho(sp.getSoLuongTrongKho() - soLuong);
                    em.merge(sp);
                }
            }

            // 4. Tạo bản ghi thanh toán đầu tiên
            ThanhToan tt = new ThanhToan();
            tt.setHoaDonBanHang(hd);
            tt.setKhachHang(kh);
            tt.setNgayTT(ngayTao);
            tt.setHinhThucTT(hinhThucTT);

            if ("Trả thẳng".equals(loaiHD)) {
                tt.setTienThanhToan(tongTien);
                hd.setSoTienConLai(0.0);
                hd.setTrangThai("Đã thanh toán");
            } else {
                // Trả góp - thanh toán lần đầu = 0 (hoặc tiền cọc nếu có)
                tt.setTienThanhToan(0.0);
                hd.setSoTienConLai(tongTien);
                hd.setTrangThai("Đang trả góp");
            }
            em.persist(tt);
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
}
