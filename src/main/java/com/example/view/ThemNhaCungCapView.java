package com.example.view;

import com.example.config.UIThemeConfig;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Form thêm nhà cung cấp — nhúng vào ContentPanel, không mở cửa sổ mới.
 */
public class ThemNhaCungCapView extends JPanel {

    public JTextField txtTenNCC, txtSDT, txtDiaChi;
    public JButton btnThem, btnClear;

    public ThemNhaCungCapView() {
        setLayout(new BorderLayout(0, 0));
        setBackground(UIThemeConfig.BG_DARK);

        // ── HEADER ──
        JPanel pnlHeader = UIThemeConfig.createGlassPanel(new BorderLayout());
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, UIThemeConfig.ACCENT),
                new EmptyBorder(18, 28, 18, 28)));

        JLabel lblTitle = UIThemeConfig.createTitleLabel("THÊM NHÀ CUNG CẤP MỚI");
        lblTitle.setForeground(UIThemeConfig.ACCENT);
        JLabel lblSub = UIThemeConfig.createLabel("Đăng ký nhà cung cấp mới để nhập hàng.");
        lblSub.setForeground(UIThemeConfig.TEXT_MUTED);

        JPanel pnlHeaderText = new JPanel();
        pnlHeaderText.setOpaque(false);
        pnlHeaderText.setLayout(new BoxLayout(pnlHeaderText, BoxLayout.Y_AXIS));
        pnlHeaderText.add(lblTitle);
        pnlHeaderText.add(Box.createRigidArea(new Dimension(0, 4)));
        pnlHeaderText.add(lblSub);
        pnlHeader.add(pnlHeaderText, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        // ── FORM (giữa màn hình, compact) ──
        JPanel pnlWrap = new JPanel(new GridBagLayout());
        pnlWrap.setBackground(UIThemeConfig.BG_DARK);
        pnlWrap.setBorder(new EmptyBorder(40, 0, 20, 0));

        JPanel pnlForm = UIThemeConfig.createGlassPanel(new GridBagLayout());
        pnlForm.setBorder(new EmptyBorder(28, 32, 28, 32));
        pnlForm.setPreferredSize(new Dimension(520, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(9, 8, 9, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtTenNCC = UIThemeConfig.createTextField();
        txtSDT = UIThemeConfig.createTextField();
        txtDiaChi = UIThemeConfig.createTextField();

        String[] labels = { "Tên nhà cung cấp:", "Số điện thoại:", "Địa chỉ:" };
        JTextField[] fields = { txtTenNCC, txtSDT, txtDiaChi };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridy = i;
            gbc.gridx = 0;
            gbc.weightx = 0;
            JLabel lbl = UIThemeConfig.createLabel(labels[i]);
            lbl.setPreferredSize(new Dimension(130, 26));
            lbl.setFont(UIThemeConfig.FONT_BODY);
            pnlForm.add(lbl, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1;
            fields[i].setPreferredSize(new Dimension(280, 32));
            pnlForm.add(fields[i], gbc);
        }

        GridBagConstraints wgbc = new GridBagConstraints();
        pnlWrap.add(pnlForm, wgbc);
        add(pnlWrap, BorderLayout.CENTER);

        // ── BUTTONS ──
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 14));
        pnlBtns.setBackground(UIThemeConfig.BG_DARK);
        pnlBtns.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, UIThemeConfig.BORDER),
                new EmptyBorder(4, 24, 4, 24)));

        btnClear = UIThemeConfig.createButton("Làm mới", UIThemeConfig.ACCENT_YELLOW);
        btnThem = UIThemeConfig.createSuccessButton("Thêm nhà cung cấp");
        btnThem.setPreferredSize(new Dimension(150, 36));

        pnlBtns.add(btnClear);
        pnlBtns.add(btnThem);
        add(pnlBtns, BorderLayout.SOUTH);
    }
}