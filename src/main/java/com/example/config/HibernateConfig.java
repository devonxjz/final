package com.example.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;

/**
 * Lớp tiện ích quản lý EntityManagerFactory và SessionFactory.
 */
public class HibernateConfig {
    private static final String PERSISTENCE_UNIT_NAME = "LaptopPU";
    private static EntityManagerFactory factory;

    static {
        try {
            // Khởi tạo Factory từ persistence.xml
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Throwable ex) {
            System.err.println("Khoi tao EntityManagerFactory that bai: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * BỔ SUNG: Trả về SessionFactory (Dùng cho Hibernate Native DAO)
     * Giúp sửa lỗi "Cannot resolve method 'getSessionFactory'"
     */
    public static SessionFactory getSessionFactory() {
        if (factory == null) return null;
        // Ép kiểu từ EntityManagerFactory sang SessionFactory của Hibernate
        return factory.unwrap(SessionFactory.class);
    }

    /**
     * Tạo một EntityManager mới (Dùng cho JPA DAO)
     */
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    /** Đóng Factory khi ứng dụng kết thúc */
    public static void shutdown() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}
