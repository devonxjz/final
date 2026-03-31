package com.example.view;

import com.example.config.HibernateConfig;
import com.example.config.UIThemeConfig;
import com.example.config.AppConfig;
import com.example.controller.*;
import com.example.dto.DashboardStatusDTO;
import com.example.dto.HoaDonBanHangDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.Map;

/**
 * HomeView — SaaS Dashboard Layout.
 * Sidebar(WEST) + Header(NORTH) + Content(CENTER) + Footer(SOUTH).
 */
public class HomeView extends JFrame {

    private JPanel contentPanel;
    private JPanel sidebarPanel;
    private String activeMenu = "Dashboard";

    public HomeView() {
        setTitle("LaptopPU — Laptop Management Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIThemeConfig.BG_DARK);
        getContentPane().setLayout(new BorderLayout());

        // Header
        getContentPane().add(createHeader(), BorderLayout.NORTH);

        // Sidebar
        sidebarPanel = createSidebar();
        getContentPane().add(sidebarPanel, BorderLayout.WEST);

        // Content area
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UIThemeConfig.BG_DARK);
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Footer
        getContentPane().add(createFooter(), BorderLayout.SOUTH);

        showDashboard();
        setVisible(true);
    }

    // ═══════════════════════════════════════════════════════════════════
    // HEADER
    // ═══════════════════════════════════════════════════════════════════
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIThemeConfig.BG_SIDEBAR);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UIThemeConfig.BORDER),
                new EmptyBorder(10, 20, 10, 20)));

        // Logo
        JLabel logo = new JLabel("LaptopPU");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setForeground(UIThemeConfig.ACCENT);

        // Search bar
        JPanel searchWrap = new JPanel(new BorderLayout());
        searchWrap.setOpaque(false);
        searchWrap.setPreferredSize(new Dimension(300, 34));
        JTextField searchField = UIThemeConfig.createTextField();
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIThemeConfig.BORDER, 1),
                new EmptyBorder(5, 12, 5, 12)));
        // Set placeholder text via focus listener
        searchField.setForeground(UIThemeConfig.TEXT_MUTED);
        searchField.setText("Search...");
        searchField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search...")) {
                    searchField.setText("");
                    searchField.setForeground(UIThemeConfig.TEXT_PRIMARY);
                }
            }
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search...");
                    searchField.setForeground(UIThemeConfig.TEXT_MUTED);
                }
            }
        });
        searchWrap.add(searchField, BorderLayout.CENTER);

        // Right section — notification + avatar
        JPanel rightSection = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightSection.setOpaque(false);

        JLabel bellIcon = new JLabel("🔔");
        bellIcon.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        bellIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel avatar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIThemeConfig.ACCENT);
                g2.fillOval(0, 0, 34, 34);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("A", (34 - fm.stringWidth("A")) / 2, (34 + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        avatar.setOpaque(false);
        avatar.setPreferredSize(new Dimension(34, 34));
        avatar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        rightSection.add(bellIcon);
        rightSection.add(avatar);

        header.add(logo, BorderLayout.WEST);
        header.add(searchWrap, BorderLayout.CENTER);
        header.add(rightSection, BorderLayout.EAST);
        return header;
    }

    // ═══════════════════════════════════════════════════════════════════
    // SIDEBAR
    // ═══════════════════════════════════════════════════════════════════
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UIThemeConfig.BG_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, UIThemeConfig.BORDER));
        rebuildSidebarItems(sidebar);
        return sidebar;
    }

    private void rebuildSidebarItems(JPanel sidebar) {
        sidebar.removeAll();
        sidebar.add(Box.createRigidArea(new Dimension(0, 12)));

        String[][] items = {
            {"📊", "Dashboard"},
            {"📦", "Products"},
            {"🏭", "Suppliers"},
            {"👥", "Customers"},
            {"📋", "Orders"},
            {"💳", "Payments"},
            {"📈", "Reports"},
        };

        for (String[] item : items) {
            boolean isActive = item[1].equals(activeMenu);
            JPanel menuItem = UIThemeConfig.createSidebarItem(item[0], item[1], isActive, () -> {
                onSidebarClick(item[1]);
            });
            sidebar.add(menuItem);
            sidebar.add(Box.createRigidArea(new Dimension(0, 2)));
        }

        // Spacer + Exit
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(UIThemeConfig.createDivider());
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));

        JPanel exitItem = UIThemeConfig.createSidebarItem("🚪", "Exit", false, () -> {
            int c = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) {
                HibernateConfig.shutdown();
                System.exit(0);
            }
        });
        sidebar.add(exitItem);
        sidebar.add(Box.createRigidArea(new Dimension(0, 12)));

        sidebar.revalidate();
        sidebar.repaint();
    }

    // ═══════════════════════════════════════════════════════════════════
    // FOOTER
    // ═══════════════════════════════════════════════════════════════════
    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(UIThemeConfig.BG_SIDEBAR);
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, UIThemeConfig.BORDER),
                new EmptyBorder(6, 0, 6, 0)));
        JLabel lbl = new JLabel("© 2026 LaptopPU — All rights reserved.");
        lbl.setFont(UIThemeConfig.FONT_SMALL);
        lbl.setForeground(UIThemeConfig.TEXT_MUTED);
        footer.add(lbl);
        return footer;
    }

    // ═══════════════════════════════════════════════════════════════════
    // DASHBOARD (default content)
    // ═══════════════════════════════════════════════════════════════════
    private void showDashboard() {
        activeMenu = "Dashboard";
        rebuildSidebarItems(sidebarPanel);
        contentPanel.removeAll();

        JPanel dash = new JPanel(new BorderLayout(0, 16));
        dash.setBackground(UIThemeConfig.BG_DARK);
        dash.setBorder(new EmptyBorder(24, 28, 16, 28));

        // Welcome header
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setOpaque(false);
        JLabel lblWelcome = new JLabel("Dashboard");
        lblWelcome.setFont(UIThemeConfig.FONT_TITLE);
        lblWelcome.setForeground(UIThemeConfig.TEXT_PRIMARY);
        JLabel lblSub = new JLabel("Welcome back! Here's what's happening today.");
        lblSub.setFont(UIThemeConfig.FONT_BODY);
        lblSub.setForeground(UIThemeConfig.TEXT_MUTED);
        welcomePanel.add(lblWelcome, BorderLayout.NORTH);
        welcomePanel.add(lblSub, BorderLayout.SOUTH);

        // Stats cards — 4 in a row
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        statsRow.setOpaque(false);

        // Load real data
        DashboardStatusDTO status;
        try {
            status = AppConfig.getDashboardService().getDashboardStatus();
        } catch (Exception e) {
            status = new DashboardStatusDTO(0, 0, 0, 0);
        }

        statsRow.add(UIThemeConfig.createStatCard("REVENUE TODAY",
                String.format("%,.0f VND", status.revenueToday()), "$", UIThemeConfig.ACCENT));
        statsRow.add(UIThemeConfig.createStatCard("TOTAL ORDERS",
                String.valueOf(status.totalOrders()), "#", UIThemeConfig.ACCENT_GREEN));
        statsRow.add(UIThemeConfig.createStatCard("IN STOCK",
                String.valueOf(status.productsInStock()), "📦", UIThemeConfig.ACCENT_YELLOW));
        statsRow.add(UIThemeConfig.createStatCard("CUSTOMERS",
                String.valueOf(status.totalCustomers()), "👥", UIThemeConfig.ACCENT_PURPLE));

        JPanel topSection = new JPanel(new BorderLayout(0, 16));
        topSection.setOpaque(false);
        topSection.add(welcomePanel, BorderLayout.NORTH);
        topSection.add(statsRow, BorderLayout.SOUTH);

        dash.add(topSection, BorderLayout.NORTH);

        // Bottom: Recent Orders + Revenue Chart
        JPanel bottomSection = new JPanel(new GridLayout(1, 2, 16, 0));
        bottomSection.setOpaque(false);

        // Recent Orders
        JPanel ordersPanel = UIThemeConfig.createGlassPanel(new BorderLayout(0, 10));
        ordersPanel.setBorder(new EmptyBorder(18, 18, 18, 18));
        JLabel lblOrders = new JLabel("Recent Orders");
        lblOrders.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblOrders.setForeground(UIThemeConfig.TEXT_PRIMARY);
        ordersPanel.add(lblOrders, BorderLayout.NORTH);

        // Recent orders table
        String[] orderCols = {"Order ID", "Date", "Total", "Status"};
        DefaultTableModel orderModel = new DefaultTableModel(orderCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        try {
            List<HoaDonBanHangDTO> recent = AppConfig.getHoaDonBanHangService().getAllHoaDon();
            int limit = Math.min(recent.size(), 8);
            for (int i = 0; i < limit; i++) {
                HoaDonBanHangDTO hd = recent.get(i);
                orderModel.addRow(new Object[]{
                        hd.maHDBH(), hd.ngayTao(), String.format("%,.0f", hd.tongTien()), hd.trangThai()
                });
            }
        } catch (Exception ignored) {}

        JTable ordersTable = new JTable(orderModel);
        UIThemeConfig.styleTable(ordersTable);
        ordersPanel.add(UIThemeConfig.createScrollPane(ordersTable), BorderLayout.CENTER);

        // Revenue Chart placeholder
        JPanel chartPanel = UIThemeConfig.createGlassPanel(new BorderLayout(0, 10));
        chartPanel.setBorder(new EmptyBorder(18, 18, 18, 18));
        JLabel lblChart = new JLabel("Revenue Trend (7 days)");
        lblChart.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblChart.setForeground(UIThemeConfig.TEXT_PRIMARY);
        chartPanel.add(lblChart, BorderLayout.NORTH);

        // Simple bar chart
        JPanel chartArea = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Map<String, Double> revenueData = null;
                try {
                    java.time.LocalDate now = java.time.LocalDate.now();
                    String to = now.toString();
                    String from = now.minusDays(6).toString();
                    revenueData = AppConfig.getThongKeService().getRevenueByDay(from, to);
                } catch (Exception ignored) {}

                if (revenueData == null || revenueData.isEmpty()) {
                    g2.setColor(UIThemeConfig.TEXT_MUTED);
                    g2.setFont(UIThemeConfig.FONT_BODY);
                    g2.drawString("No revenue data available", 20, getHeight() / 2);
                    g2.dispose();
                    return;
                }

                int w = getWidth() - 40;
                int h = getHeight() - 50;
                int barWidth = Math.max(20, w / revenueData.size() - 10);
                double maxVal = revenueData.values().stream().mapToDouble(Double::doubleValue).max().orElse(1);

                int x = 20;
                for (Map.Entry<String, Double> entry : revenueData.entrySet()) {
                    double val = entry.getValue();
                    int barH = maxVal > 0 ? (int) (val / maxVal * h) : 0;
                    int y = getHeight() - 30 - barH;

                    // Gradient bar
                    GradientPaint gp = new GradientPaint(x, y, UIThemeConfig.ACCENT,
                            x, getHeight() - 30, new Color(63, 132, 229, 60));
                    g2.setPaint(gp);
                    g2.fillRoundRect(x, y, barWidth, barH, 6, 6);

                    // Label
                    g2.setColor(UIThemeConfig.TEXT_MUTED);
                    g2.setFont(UIThemeConfig.FONT_SMALL);
                    String label = entry.getKey().length() > 5 ? entry.getKey().substring(5) : entry.getKey();
                    g2.drawString(label, x + 2, getHeight() - 14);

                    x += barWidth + 10;
                }
                g2.dispose();
            }
        };
        chartArea.setOpaque(false);
        chartPanel.add(chartArea, BorderLayout.CENTER);

        bottomSection.add(ordersPanel);
        bottomSection.add(chartPanel);
        dash.add(bottomSection, BorderLayout.CENTER);

        contentPanel.add(dash, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ═══════════════════════════════════════════════════════════════════
    // SIDEBAR CLICK → Dynamic panel replacement
    // ═══════════════════════════════════════════════════════════════════
    private void onSidebarClick(String name) {
        activeMenu = name;
        rebuildSidebarItems(sidebarPanel);

        switch (name) {
            case "Dashboard" -> showDashboard();
            case "Products" -> {
                SanPhamView view = new SanPhamView();
                new SanPhamController(AppConfig.getSanPhamService(), view);
                showSubView("Products", view);
            }
            case "Suppliers" -> {
                NhaCungCapView view = new NhaCungCapView();
                new NhaCungCapController(AppConfig.getNhaCungCapService(), view);
                showSubView("Suppliers", view);
            }
            case "Customers" -> {
                KhachHangView view = new KhachHangView();
                new KhachHangController(AppConfig.getKhachHangService(), view);
                showSubView("Customers", view);
            }
            case "Orders" -> {
                HoaDonBanHangView listView = new HoaDonBanHangView();
                HoaDonView banHangView = new HoaDonView();
                new HoaDonBanHangController(
                        AppConfig.getHoaDonBanHangService(),
                        AppConfig.getSanPhamService(),
                        AppConfig.getKhachHangService(),
                        listView, banHangView);
                showSubView("Sales Orders", listView);
            }
            case "Payments" -> {
                ThanhToanView view = new ThanhToanView();
                new ThanhToanController(AppConfig.getThanhToanService(), view);
                showSubView("Payments", view);
            }
            case "Reports" -> {
                ThongKeView view = new ThongKeView();
                new ThongKeController(AppConfig.getThongKeService(), view);
                showSubView("Reports", view);
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    // showSubView — replace content, NO top bar (sidebar handles nav)
    // ═══════════════════════════════════════════════════════════════════
    public void showSubView(String title, JPanel panelView) {
        contentPanel.removeAll();
        contentPanel.add(panelView, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * For sub-forms (ThemSanPham, ThemNCC etc.) — adds a "← Back" bar
     */
    public void showSubViewWithBack(String title, JPanel panelView, Runnable onBack) {
        contentPanel.removeAll();

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UIThemeConfig.BG_DARK);

        // Top bar with back button
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UIThemeConfig.BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UIThemeConfig.BORDER),
                new EmptyBorder(9, 18, 9, 18)));

        JButton btnBack = UIThemeConfig.createButton("← Back", UIThemeConfig.BG_INPUT);
        btnBack.setPreferredSize(new Dimension(120, 33));
        btnBack.addActionListener(e -> onBack.run());

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblTitle.setForeground(UIThemeConfig.TEXT_PRIMARY);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(120, 0));

        topBar.add(btnBack, BorderLayout.WEST);
        topBar.add(lblTitle, BorderLayout.CENTER);
        topBar.add(spacer, BorderLayout.EAST);

        wrapper.add(topBar, BorderLayout.NORTH);
        wrapper.add(panelView, BorderLayout.CENTER);

        contentPanel.add(wrapper, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}