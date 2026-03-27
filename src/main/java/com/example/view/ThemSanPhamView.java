package com.example.view;

import javax.swing.*;
import java.awt.*;

public class ThemSanPhamView extends JDialog {
    // Tương tự các JTextField ở trên nhưng dành cho Form thêm
    // ... (Khai báo các JTextField cho từng thông số)
    public JButton btnLuuMoi, btnHuy;

    public ThemSanPhamView() {
        setTitle("Thêm sản phẩm mới");
        setModal(true); // Ngăn không cho tương tác với cửa sổ chính khi đang mở form này
        setSize(400, 600);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // Thiết kế giao diện dọc bằng BoxLayout hoặc GridBagLayout
        // Thêm các nút "Lưu mới" và "Hủy"
    }
}