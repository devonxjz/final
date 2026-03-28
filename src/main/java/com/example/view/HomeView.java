package com.example.view;

import com.example.config.UITheme;
import com.example.controller.*;
import com.example.dao.*;
import com.example.dao.impl.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Giao diện chính – Dashboard Dark Mode.
 * Các nút chức năng hiển thị dạng lưới card ở giữa màn hình.
 */
public class HomeView extends JFrame {

    private JPanel contentPanel;

    private static final Color[] CARD_COLORS = {
        new Color(56, 189, 248),   // Cyan
        new Color(52, 211, 153),   // Green
        new Color(251, 146, 60),   // Orange
        new Color(167, 139, 250),  // Purple
        new Color(248, 113, 113),  // Red
        new Color(251, 191, 36),   // Yellow
    };

    public HomeView() {
        setTitle("Quản lí nhập và bán cửa hàng Laptop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        getContentPane().setBackground(UITheme.BG_DARK);
        getContentPane().setLayout(new BorderLayout());

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UITheme.BG_DARK);
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        showDashboard();
        setVisible(true);
    }

    // ========== DASHBOARD ==========
    private void showDashboard() {
        contentPanel.removeAll();

        JPanel dash = new JPanel(new BorderLayout());
        dash.setBackground(UITheme.BG_DARK);

        // --- Header ---
        JPanel header = new JPanel();
        header.setBackground(UITheme.BG_DARK);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(40, 0, 10, 0));

        JLabel t1 = new JLabel("\uD83D\uDCBB  QUẢN LÝ CỬA HÀNG LAPTOP");
        t1.setFont(new Font("Segoe UI", Font.BOLD, 32));
        t1.setForeground(UITheme.ACCENT);
        t1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel t2 = new JLabel("MVC  |  JPA / Hibernate  |  Java Swing");
        t2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        t2.setForeground(UITheme.TEXT_MUTED);
        t2.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(t1);
        header.add(Box.createRigidArea(new Dimension(0, 6)));
        header.add(t2);

        dash.add(header, BorderLayout.NORTH);

        // --- Grid các nút ---
        JPanel gridWrapper = new JPanel(new GridBagLayout());
        gridWrapper.setBackground(UITheme.BG_DARK);

        JPanel grid = new JPanel(new GridLayout(2, 3, 20, 20));
        grid.setOpaque(false);

        String[] names = {"Sản phẩm", "Nhà cung cấp", "Khách hàng",
                           "Hoá đơn bán hàng", "Thanh toán", "Thống kê"};
        // Unicode emoji icons
        String[] icons = {"\uD83D\uDCE6", "\uD83C\uDFED", "\uD83D\uDC64", "\uD83D\uDCCB", "\uD83D\uDCB3", "\uD83D\uDCCA"};
        String[] descs = {"Quản lý laptop, cấu hình", "Nhập hàng, đối tác",
                          "Thông tin khách mua", "Hoá đơn trả thẳng/góp",
                          "Lịch sử thu ngân", "Doanh thu, biểu đồ"};

        for (int i = 0; i < names.length; i++) {
            grid.add(createDashCard(icons[i], names[i], descs[i], CARD_COLORS[i]));
        }

        gridWrapper.add(grid);
        dash.add(gridWrapper, BorderLayout.CENTER);

        // --- Footer ---
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(UITheme.BG_DARK);
        footer.setBorder(new EmptyBorder(10, 0, 30, 0));

        JButton btnExit = UITheme.createDangerButton("Thoát ứng dụng");
        btnExit.setPreferredSize(new Dimension(180, 40));
        btnExit.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) {
                com.example.config.HibernateUtil.shutdown();
                System.exit(0);
            }
        });
        footer.add(btnExit);

        dash.add(footer, BorderLayout.SOUTH);

        contentPanel.add(dash, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ========== CARD ==========
    private JPanel createDashCard(String iconEmoji, String name, String desc, Color accent) {
        JPanel card = new JPanel() {
            private boolean hover = false;
            {
                setOpaque(false);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                    public void mouseExited(MouseEvent e) { hover = false; repaint(); }
                    public void mouseClicked(MouseEvent e) { onCardClick(name); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bg = hover ? UITheme.BG_HOVER : UITheme.BG_CARD;
                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));

                // Accent bar top
                g2.setColor(accent);
                g2.fillRoundRect(0, 0, getWidth(), 4, 4, 4);

                // Glow khi hover
                if (hover) {
                    g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 25));
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                }

                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(20, 25, 20, 25));
        card.setPreferredSize(new Dimension(220, 170));

        // Emoji icon — rendered as large text in a circle
        JPanel iconBadge = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Background circle
                g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 40));
                g2.fillOval(0, 0, 56, 56);
                // Emoji text
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
                FontMetrics fm = g2.getFontMetrics();
                int x = (56 - fm.stringWidth(iconEmoji)) / 2;
                int y = (56 + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(iconEmoji, x, y);
                g2.dispose();
            }
        };
        iconBadge.setOpaque(false);
        iconBadge.setPreferredSize(new Dimension(56, 56));
        iconBadge.setMaximumSize(new Dimension(56, 56));
        iconBadge.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tên
        JLabel lblName = new JLabel(name, SwingConstants.CENTER);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblName.setForeground(UITheme.TEXT_PRIMARY);
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Mô tả
        JLabel lblDesc = new JLabel(desc, SwingConstants.CENTER);
        lblDesc.setFont(UITheme.FONT_SMALL);
        lblDesc.setForeground(UITheme.TEXT_MUTED);
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(iconBadge);
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(lblName);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(lblDesc);
        card.add(Box.createVerticalGlue());

        return card;
    }

    // ========== CLICK ==========
    private void onCardClick(String name) {
        switch (name) {
            case "Sản phẩm" -> {
                SanPhamView view = new SanPhamView();
                new SanPhamController(new SanPhamDAOImpl(), view);
                showSubView("Sản Phẩm", view);
            }
            case "Nhà cung cấp" -> {
                NhaCungCapView view = new NhaCungCapView();
                new NhaCungCapController(new NhaCungCapDAOImpl(), view);
            }
            case "Khách hàng" -> {
                KhachHangView view = new KhachHangView();
                new KhachHangController(new com.example.services.KhachHangService(), view);
                showSubView("Khách Hàng", view);
            }
            case "Hoá đơn bán hàng" -> {
                HoaDonBanHangView view = new HoaDonBanHangView();
                new HoaDonBanHangController(new HoaDonBanHangDAOImpl(), view);
            }
            case "Thanh toán" -> {
                ThanhToanView view = new ThanhToanView();
                new ThanhToanController(new ThanhToanDAOImpl(), view);
            }
            case "Thống kê" -> {
                ThongKeView view = new ThongKeView();
                new ThongKeController(new ThongKeDAOImpl(), view);
                showSubView("Thống Kê", view);
            }
        }
    }

    // ========== SUB VIEW ==========
    private void showSubView(String title, JPanel panelView) {
        contentPanel.removeAll();

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UITheme.BG_DARK);

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UITheme.BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER),
                new EmptyBorder(8, 15, 8, 15)
        ));

        JButton btnBack = UITheme.createButton("\u2190  Quay lại", UITheme.BG_INPUT);
        btnBack.setPreferredSize(new Dimension(130, 34));
        btnBack.addActionListener(e -> showDashboard());

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(UITheme.FONT_SUBTITLE);
        lblTitle.setForeground(UITheme.TEXT_PRIMARY);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        topBar.add(btnBack, BorderLayout.WEST);
        topBar.add(lblTitle, BorderLayout.CENTER);
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(130, 0));
        topBar.add(spacer, BorderLayout.EAST);

        wrapper.add(topBar, BorderLayout.NORTH);
        wrapper.add(panelView, BorderLayout.CENTER);

        contentPanel.add(wrapper, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}