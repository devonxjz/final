package com.example.controller;

import com.example.dao.ThanhToanDAO;
import com.example.entity.ThanhToan;
import com.example.view.ThanhToanView;
import com.example.view.ThemThanhToanView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ThanhToanController {
    private final ThanhToanDAO dao;
    private final ThanhToanView view;

    public ThanhToanController(ThanhToanDAO dao, ThanhToanView view) {
        this.dao = dao;
        this.view = view;
        initController();
        view.taiLaiDuLieu();
    }

    private void initController() {
        view.getBtnReload().addActionListener(e -> view.taiLaiDuLieu());

        view.getBtnThem().addActionListener(e -> {
            ThemThanhToanView themView = new ThemThanhToanView();
            ThemThanhToanController themController = new ThemThanhToanController(dao, themView);

            // Listen for closure to refresh main list
            themView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent windowEvent) {
                    view.taiLaiDuLieu();
                }
            });
        });
        
        // Cập nhật tìm kiếm nếu muốn bằng txtTenKhachHang
        // Hiện tại ThanhToanView dùng tableModel và có thể filter bằng tay.
    }
}
