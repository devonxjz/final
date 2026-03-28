package com.example.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Lớp tiện ích quản lý EntityManagerFactory cho Hibernate/JPA.
 * Sử dụng persistence-unit "LaptopPU" được cấu hình trong persistence.xml.
 */
public class HibernateUtil {
    private static final String PERSISTENCE_UNIT_NAME = "LaptopPU";
    private static EntityManagerFactory factory;

    static {
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Throwable ex) {
            System.err.println("Khoi tao EntityManagerFactory that bai: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Tạo một EntityManager mới từ Factory.
     * Người gọi phải tự đóng EntityManager sau khi sử dụng.
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
