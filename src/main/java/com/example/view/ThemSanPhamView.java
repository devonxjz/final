package com.example.view;

import com.example.config.UIThemeConfig;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Form thêm sản phẩm — hiển thị nhúng vào ContentPanel, không mở cửa sổ mới.
 * Đổi từ JFrame → JPanel.
 */
public class ThemSanPhamView extends JPanel {

    public JTextField txtLoaiMay, txtTenSP, txtCPU, txtGPU, txtRAM, txtOCung, txtCanNang;
    public JTextField txtKTManHinh, txtDPGiaiMH, txtSoLuong, txtGiaBan, txtGiaNhap, txtBaoHanh;
    public JButton btnThem, btnClear;

    public ThemSanPhamView() {
        setLayout(new BorderLayout(0, 0));
        setBackground(UIThemeConfig.BG_DARK);

        // ── HEADER ──
        JPanel pnlHeader = UIThemeConfig.createGlassPanel(new BorderLayout());
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, UIThemeConfig.ACCENT),
                new EmptyBorder(18, 28, 18, 28)));

        JLabel lblTitle = UIThemeConfig.createTitleLabel("THÊM SẢN PHẨM MỚI");
        lblTitle.setForeground(UIThemeConfig.ACCENT);

        JLabel lblSub = UIThemeConfig.createLabel("Điền đầy đủ các thông tin dưới đây để thêm laptop mới vào kho hàng.");
        lblSub.setForeground(UIThemeConfig.TEXT_MUTED);

        JPanel pnlHeaderText = new JPanel();
        pnlHeaderText.setOpaque(false);
        pnlHeaderText.setLayout(new BoxLayout(pnlHeaderText, BoxLayout.Y_AXIS));
        pnlHeaderText.add(lblTitle);
        pnlHeaderText.add(Box.createRigidArea(new Dimension(0, 4)));
        pnlHeaderText.add(lblSub);

        pnlHeader.add(pnlHeaderText, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        // ── FORM ──
        JPanel pnlWrap = new JPanel(new GridBagLayout());
        pnlWrap.setBackground(UIThemeConfig.BG_DARK);
        pnlWrap.setBorder(new EmptyBorder(24, 32, 16, 32));

        JPanel pnlForm = UIThemeConfig.createGlassPanel(new GridBagLayout());
        pnlForm.setBorder(new EmptyBorder(20, 24, 20, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtLoaiMay = UIThemeConfig.createTextField();
        txtTenSP = UIThemeConfig.createTextField();
        txtCPU = UIThemeConfig.createTextField();
        txtGPU = UIThemeConfig.createTextField();
        txtRAM = UIThemeConfig.createTextField();
        txtOCung = UIThemeConfig.createTextField();
        txtCanNang = UIThemeConfig.createTextField();
        txtKTManHinh = UIThemeConfig.createTextField();
        txtDPGiaiMH = UIThemeConfig.createTextField();
        txtSoLuong = UIThemeConfig.createTextField();
        txtGiaBan = UIThemeConfig.createTextField();
        txtGiaNhap = UIThemeConfig.createTextField();
        txtBaoHanh = UIThemeConfig.createTextField();

        // Chia 2 cột, 7 hàng
        Object[][] rows = {
                { "Loại máy:", txtLoaiMay, "Kích thước màn hình:", txtKTManHinh },
                { "Tên sản phẩm:", txtTenSP, "Độ phân giải:", txtDPGiaiMH },
                { "CPU:", txtCPU, "Số lượng tồn:", txtSoLuong },
                { "GPU:", txtGPU, "Giá bán (VND):", txtGiaBan },
                { "RAM (GB):", txtRAM, "Giá nhập:", txtGiaNhap },
                { "Ổ cứng:", txtOCung, "Bảo hành (tháng):", txtBaoHanh },
                { "Trọng lượng (kg):", txtCanNang, null, null },
        };

        for (int r = 0; r < rows.length; r++) {
            // col A — label
            gbc.gridy = r;
            gbc.gridx = 0;
            gbc.weightx = 0;
            JLabel lA = UIThemeConfig.createLabel((String) rows[r][0]);
            lA.setPreferredSize(new Dimension(135, 26));
            pnlForm.add(lA, gbc);
            // col A — field
            gbc.gridx = 1;
            gbc.weightx = 1;
            pnlForm.add((Component) rows[r][1], gbc);

            if (rows[r][2] != null) {
                // col B — label
                gbc.gridx = 2;
                gbc.weightx = 0;
                JLabel lB = UIThemeConfig.createLabel((String) rows[r][2]);
                lB.setPreferredSize(new Dimension(145, 26));
                pnlForm.add(lB, gbc);
                // col B — field
                gbc.gridx = 3;
                gbc.weightx = 1;
                pnlForm.add((Component) rows[r][3], gbc);
            }
        }

        GridBagConstraints wgbc = new GridBagConstraints();
        wgbc.fill = GridBagConstraints.BOTH;
        wgbc.weightx = 1;
        wgbc.weighty = 1;
        pnlWrap.add(pnlForm, wgbc);
        add(pnlWrap, BorderLayout.CENTER);

        // ── BUTTONS ──
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 14));
        pnlBtns.setBackground(UIThemeConfig.BG_DARK);
        pnlBtns.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, UIThemeConfig.BORDER),
                new EmptyBorder(4, 24, 4, 24)));

        btnClear = UIThemeConfig.createButton("Làm mới", UIThemeConfig.ACCENT_YELLOW);
        btnThem = UIThemeConfig.createSuccessButton("Thêm sản phẩm");
        btnThem.setPreferredSize(new Dimension(150, 36));

        pnlBtns.add(btnClear);
        pnlBtns.add(btnThem);
        add(pnlBtns, BorderLayout.SOUTH);
    }
}