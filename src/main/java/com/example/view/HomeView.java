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
import java.util.List;
import java.util.Map;

/**
 * HomeView — SaaS Dashboard Layout.
 * Sidebar(WEST) + Header(NORTH) + Content(CENTER) + Footer(SOUTH).
 */
public class HomeView extends JFrame {

    private JPanel contentPanel;
    private JPanel sidebarPanel;
    private String activeMenu = "Bảng điều khiển";

    public HomeView() {
        setTitle("LaptopPU — Hệ Thống Quản Lý Laptop");
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

        // Right section — notification + avatar
        JPanel rightSection = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightSection.setOpaque(false);

        ImageIcon bellImg = UIThemeConfig.loadScaledIcon("/icons/notification.png", 22, 22);
        JLabel bellIcon = new JLabel();
        if (bellImg != null) {
            bellIcon.setIcon(bellImg);
        } else {
            bellIcon.setText("🔔");
            bellIcon.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        }
        bellIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ImageIcon profileImg = UIThemeConfig.loadScaledIcon("/icons/profile.png", 34, 34);
        JLabel avatar = new JLabel();
        if (profileImg != null) {
            avatar.setIcon(profileImg);
        } else {
            avatar.setText("A");
            avatar.setFont(new Font("Segoe UI", Font.BOLD, 14));
            avatar.setForeground(UIThemeConfig.ACCENT);
        }
        avatar.setPreferredSize(new Dimension(34, 34));
        avatar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        rightSection.add(bellIcon);
        rightSection.add(avatar);

        header.add(logo, BorderLayout.WEST);
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
                { "/icons/dashboard.png", "Bảng điều khiển", "📊" },
                { "/icons/box.png", "Sản phẩm", "📦" },
                { "/icons/factory.png", "Nhà cung cấp", "🏭" },
                { "/icons/team.png", "Khách hàng", "👥" },
                { "/icons/checklist.png", "Đơn hàng", "📋" },
                { "/icons/atm-card.png", "Thanh toán", "💳" },
                { "/icons/bar-chart.png", "Báo cáo", "📈" },
        };

        for (String[] item : items) {
            boolean isActive = item[1].equals(activeMenu);

            // 1. Thử load ảnh với đường dẫn classpath (VD: /icons/box.png)
            ImageIcon icon = UIThemeConfig.loadScaledIcon(item[0], 24, 24);
            JPanel menuItem;

            if (icon != null) {
                // 2a. Load ảnh thành công -> Dùng hàm tạo ImageIcon
                menuItem = UIThemeConfig.createSidebarItem(icon, item[1], isActive, () -> {
                    onSidebarClick(item[1]);
                });
            } else {
                // 2b. Không tìm thấy ảnh -> Fallback về Emoji truyền thống
                menuItem = UIThemeConfig.createSidebarItem(item[2], item[1], isActive, () -> {
                    onSidebarClick(item[1]);
                });
            }

            sidebar.add(menuItem);
            sidebar.add(Box.createRigidArea(new Dimension(0, 2)));
        }

        // Spacer + Exit
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(UIThemeConfig.createDivider());
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));

        JPanel exitItem = UIThemeConfig.createSidebarItem("🚪", "Thoát", false, () -> {
            int c = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn thoát không?", "Xác nhận thoát", JOptionPane.YES_NO_OPTION);
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
        JLabel lbl = new JLabel("© 2026 LaptopPU — Bảo lưu mọi quyền.");
        lbl.setFont(UIThemeConfig.FONT_SMALL);
        lbl.setForeground(UIThemeConfig.TEXT_MUTED);
        footer.add(lbl);
        return footer;
    }

    // ═══════════════════════════════════════════════════════════════════
    // DASHBOARD (default content)
    // ═══════════════════════════════════════════════════════════════════
    private void showDashboard() {
        activeMenu = "Bảng điều khiển";
        rebuildSidebarItems(sidebarPanel);
        contentPanel.removeAll();

        JPanel dash = new JPanel(new BorderLayout(0, 16));
        dash.setBackground(UIThemeConfig.BG_DARK);
        dash.setBorder(new EmptyBorder(24, 28, 16, 28));

        // Welcome header
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setOpaque(false);
        JLabel lblWelcome = new JLabel("Bảng điều khiển");
        lblWelcome.setFont(UIThemeConfig.FONT_TITLE);
        lblWelcome.setForeground(UIThemeConfig.TEXT_PRIMARY);
        JLabel lblSub = new JLabel("Đang tải dữ liệu...");
        lblSub.setFont(UIThemeConfig.FONT_BODY);
        lblSub.setForeground(UIThemeConfig.TEXT_MUTED);
        welcomePanel.add(lblWelcome, BorderLayout.NORTH);
        welcomePanel.add(lblSub, BorderLayout.SOUTH);

        // Stats cards — 4 in a row (placeholder)
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        statsRow.setOpaque(false);
        statsRow.add(UIThemeConfig.createStatCard("DOANH THU HÔM NAY", "...", "$", UIThemeConfig.ACCENT));
        statsRow.add(UIThemeConfig.createStatCard("TỔNG ĐƠN HÀNG", "...", "#", UIThemeConfig.ACCENT_GREEN));
        statsRow.add(UIThemeConfig.createStatCard("SẢN PHẨM TRONG KHO", "...", "📦", UIThemeConfig.ACCENT_YELLOW));
        statsRow.add(UIThemeConfig.createStatCard("KHÁCH HÀNG", "...", "👥", UIThemeConfig.ACCENT_PURPLE));

        JPanel topSection = new JPanel(new BorderLayout(0, 16));
        topSection.setOpaque(false);
        topSection.add(welcomePanel, BorderLayout.NORTH);
        topSection.add(statsRow, BorderLayout.SOUTH);

        dash.add(topSection, BorderLayout.NORTH);

        // Bottom: Recent Orders + Revenue Chart
        JPanel bottomSection = new JPanel(new GridLayout(1, 2, 16, 0));
        bottomSection.setOpaque(false);

        // Recent Orders table (empty initially)
        JPanel ordersPanel = UIThemeConfig.createGlassPanel(new BorderLayout(0, 10));
        ordersPanel.setBorder(new EmptyBorder(18, 18, 18, 18));
        JLabel lblOrders = new JLabel("Đơn hàng gần đây");
        lblOrders.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblOrders.setForeground(UIThemeConfig.TEXT_PRIMARY);
        ordersPanel.add(lblOrders, BorderLayout.NORTH);

        String[] orderCols = { "Mã đơn hàng", "Ngày", "Tổng tiền", "Trạng thái" };
        DefaultTableModel orderModel = new DefaultTableModel(orderCols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        JTable ordersTable = new JTable(orderModel);
        UIThemeConfig.styleTable(ordersTable);
        ordersPanel.add(UIThemeConfig.createScrollPane(ordersTable), BorderLayout.CENTER);

        // Revenue Chart placeholder
        JPanel chartPanel = UIThemeConfig.createGlassPanel(new BorderLayout(0, 10));
        chartPanel.setBorder(new EmptyBorder(18, 18, 18, 18));
        JLabel lblChart = new JLabel("Xu hướng doanh thu (7 ngày)");
        lblChart.setFont(UIThemeConfig.FONT_SUBTITLE);
        lblChart.setForeground(UIThemeConfig.TEXT_PRIMARY);
        chartPanel.add(lblChart, BorderLayout.NORTH);

        JPanel chartArea = new JPanel() {
            private Map<String, Double> chartRevenueData = null;

            public void setRevenueData(Map<String, Double> data) {
                this.chartRevenueData = data;
                repaint();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(UIThemeConfig.BG_CARD);
                    g2.fillRect(0, 0, getWidth(), getHeight());

                    if (chartRevenueData == null || chartRevenueData.isEmpty()) {
                        g2.setColor(UIThemeConfig.TEXT_MUTED);
                        g2.setFont(UIThemeConfig.FONT_BODY);
                        g2.drawString("Đang tải dữ liệu...", 20, getHeight() / 2);
                        return;
                    }

                    int w = getWidth() - 40;
                    int h = getHeight() - 50;
                    int barWidth = Math.max(20, w / chartRevenueData.size() - 10);
                    double maxVal = chartRevenueData.values().stream().mapToDouble(Double::doubleValue).max().orElse(1);

                    int x = 20;
                    for (Map.Entry<String, Double> entry : chartRevenueData.entrySet()) {
                        double val = entry.getValue();
                        int barH = maxVal > 0 ? (int) (val / maxVal * h) : 0;
                        int y = getHeight() - 30 - barH;

                        GradientPaint gp = new GradientPaint(x, y, UIThemeConfig.ACCENT,
                                x, getHeight() - 30, new Color(63, 132, 229, 60));
                        g2.setPaint(gp);
                        g2.fillRoundRect(x, y, barWidth, barH, 6, 6);

                        g2.setColor(UIThemeConfig.TEXT_MUTED);
                        g2.setFont(UIThemeConfig.FONT_SMALL);
                        String label = entry.getKey().length() > 5 ? entry.getKey().substring(5) : entry.getKey();
                        g2.drawString(label, x + 2, getHeight() - 14);

                        x += barWidth + 10;
                    }
                } finally {
                    g2.dispose();
                }
            }
        };
        chartArea.setBackground(UIThemeConfig.BG_CARD);
        chartArea.setOpaque(false);
        chartPanel.add(chartArea, BorderLayout.CENTER);

        bottomSection.add(ordersPanel);
        bottomSection.add(chartPanel);
        dash.add(bottomSection, BorderLayout.CENTER);

        contentPanel.add(dash, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();

        // ====== ASYNC: Load tất cả dữ liệu Dashboard trên Background Thread ======
        com.example.util.SwingWorkerUtils.runAsync(
                null,
                () -> {
                    DashboardStatusDTO status;
                    try {
                        status = AppConfig.getDashboardService().getDashboardStatus();
                    } catch (Exception e) {
                        status = new DashboardStatusDTO(0, 0, 0, 0);
                    }

                    List<HoaDonBanHangDTO> recent;
                    try {
                        recent = AppConfig.getHoaDonBanHangService().getAllHoaDon();
                    } catch (Exception e) {
                        recent = new java.util.ArrayList<>();
                    }

                    Map<String, Double> revenueData;
                    try {
                        java.time.LocalDate now = java.time.LocalDate.now();
                        revenueData = AppConfig.getThongKeService().getRevenueByDay(now.minusDays(6).toString(),
                                now.toString());
                    } catch (Exception e) {
                        revenueData = null;
                    }

                    return new Object[] { status, recent, revenueData };
                },
                result -> {
                    // Cập nhật UI trên EDT
                    DashboardStatusDTO status = (DashboardStatusDTO) result[0];
                    @SuppressWarnings("unchecked")
                    List<HoaDonBanHangDTO> recent = (List<HoaDonBanHangDTO>) result[1];
                    @SuppressWarnings("unchecked")
                    Map<String, Double> revenueData = (Map<String, Double>) result[2];

                    lblSub.setText("Chào mừng bạn trở lại! Đây là tình hình hôm nay.");

                    // Rebuild stats cards with real data
                    statsRow.removeAll();
                    statsRow.add(UIThemeConfig.createStatCard("DOANH THU HÔM NAY",
                            String.format("%,.0f VND", status.revenueToday()), "$", UIThemeConfig.ACCENT));
                    statsRow.add(UIThemeConfig.createStatCard("TỔNG ĐƠN HÀNG",
                            String.valueOf(status.totalOrders()), "#", UIThemeConfig.ACCENT_GREEN));
                    statsRow.add(UIThemeConfig.createStatCard("SẢN PHẨM TRONG KHO",
                            String.valueOf(status.productsInStock()), "📦", UIThemeConfig.ACCENT_YELLOW));
                    statsRow.add(UIThemeConfig.createStatCard("KHÁCH HÀNG",
                            String.valueOf(status.totalCustomers()), "👥", UIThemeConfig.ACCENT_PURPLE));
                    statsRow.revalidate();
                    statsRow.repaint();

                    // Recent orders
                    int limit = Math.min(recent.size(), 8);
                    for (int i = 0; i < limit; i++) {
                        HoaDonBanHangDTO hd = recent.get(i);
                        orderModel.addRow(new Object[] {
                                hd.maHDBH(), hd.ngayTao(), String.format("%,.0f", hd.tongTien()), hd.trangThai()
                        });
                    }

                    // Revenue chart — use reflection-free approach: just set data and repaint
                    if (revenueData != null) {
                        try {
                            var setMethod = chartArea.getClass().getMethod("setRevenueData", Map.class);
                            setMethod.invoke(chartArea, revenueData);
                        } catch (Exception ignored) {
                            chartArea.repaint();
                        }
                    }
                },
                ex -> {
                    lblSub.setText("Lỗi tải dữ liệu dashboard.");
                });
    }

    // ═══════════════════════════════════════════════════════════════════
    // SIDEBAR CLICK → Dynamic panel replacement
    // ═══════════════════════════════════════════════════════════════════
    private void onSidebarClick(String name) {
        activeMenu = name;
        rebuildSidebarItems(sidebarPanel);

        switch (name) {
            case "Bảng điều khiển" -> showDashboard();
            case "Sản phẩm" -> {
                SanPhamView view = new SanPhamView();
                new SanPhamController(AppConfig.getSanPhamService(), view);
                showSubView("Sản phẩm", view);
            }
            case "Nhà cung cấp" -> {
                NhaCungCapView view = new NhaCungCapView();
                new NhaCungCapController(AppConfig.getNhaCungCapService(), view);
                showSubView("Nhà cung cấp", view);
            }
            case "Khách hàng" -> {
                KhachHangView view = new KhachHangView();
                new KhachHangController(AppConfig.getKhachHangService(), view);
                showSubView("Khách hàng", view);
            }
            case "Đơn hàng" -> {
                HoaDonBanHangView listView = new HoaDonBanHangView();
                HoaDonView banHangView = new HoaDonView();
                new HoaDonBanHangController(
                        AppConfig.getHoaDonBanHangService(),
                        AppConfig.getSanPhamService(),
                        AppConfig.getKhachHangService(),
                        listView, banHangView);
                showSubView("Đơn hàng", listView);
            }
            case "Thanh toán" -> {
                ThanhToanView view = new ThanhToanView();
                new ThanhToanController(AppConfig.getThanhToanService(), view);
                showSubView("Thanh toán", view);
            }
            case "Báo cáo" -> {
                ThongKeView view = new ThongKeView();
                new ThongKeController(AppConfig.getThongKeService(), view);
                showSubView("Báo cáo", view);
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

        JButton btnBack = UIThemeConfig.createButton("← Quay lại", UIThemeConfig.BG_INPUT);
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