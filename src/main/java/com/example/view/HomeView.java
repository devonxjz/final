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

public class HomeView extends JFrame {

    private JPanel contentPanel;

    private static final Color[] CARD_COLORS = {
            new Color(56, 189, 248),
            new Color(52, 211, 153),
            new Color(251, 146, 60),
            new Color(167, 139, 250),
            new Color(248, 113, 113),
            new Color(251, 191, 36),
    };

    private static final String[] ICON_TEXTS = {"SP", "NCC", "KH", "HD", "TT", "TK"};

    public HomeView() {
        setTitle("Laptop Store Management");
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

    private void showDashboard() {
        contentPanel.removeAll();

        JPanel dash = new JPanel(new BorderLayout());
        dash.setBackground(UITheme.BG_DARK);

        // Header
        JPanel header = new JPanel();
        header.setBackground(UITheme.BG_DARK);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(40, 0, 10, 0));

        JLabel t1 = new JLabel("LAPTOP STORE MANAGEMENT");
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

        // Grid cards
        JPanel gridWrapper = new JPanel(new GridBagLayout());
        gridWrapper.setBackground(UITheme.BG_DARK);
        JPanel grid = new JPanel(new GridLayout(2, 3, 20, 20));
        grid.setOpaque(false);

        String[] names = {"Products", "Suppliers", "Customers", "Sales Orders", "Payments", "Reports"};
        String[] descs = {
                "Manage laptops & specs",
                "Import & partners",
                "Customer information",
                "Orders & installments",
                "Payment history",
                "Revenue & charts"
        };

        for (int i = 0; i < names.length; i++) {
            grid.add(createDashCard(ICON_TEXTS[i], names[i], descs[i], CARD_COLORS[i]));
        }
        gridWrapper.add(grid);
        dash.add(gridWrapper, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(UITheme.BG_DARK);
        footer.setBorder(new EmptyBorder(10, 0, 30, 0));
        JButton btnExit = UITheme.createDangerButton("Exit Application");
        btnExit.setPreferredSize(new Dimension(180, 40));
        btnExit.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
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

    private JPanel createDashCard(String iconText, String name, String desc, Color accent) {
        JPanel card = new JPanel() {
            private boolean hover = false;
            {
                setOpaque(false);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                    public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                    public void mouseClicked(MouseEvent e) { onCardClick(name); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? UITheme.BG_HOVER : UITheme.BG_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                g2.setColor(accent);
                g2.fillRoundRect(0, 0, getWidth(), 4, 4, 4);
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

        JPanel iconBadge = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 50));
                g2.fillOval(0, 0, 56, 56);
                g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 120));
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(1, 1, 54, 54);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                g2.setColor(accent);
                FontMetrics fm = g2.getFontMetrics();
                int x = (56 - fm.stringWidth(iconText)) / 2;
                int y = (56 + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(iconText, x, y);
                g2.dispose();
            }
        };
        iconBadge.setOpaque(false);
        iconBadge.setPreferredSize(new Dimension(56, 56));
        iconBadge.setMaximumSize(new Dimension(56, 56));
        iconBadge.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblName = new JLabel(name, SwingConstants.CENTER);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblName.setForeground(UITheme.TEXT_PRIMARY);
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

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

    private void onCardClick(String name) {
        switch (name) {
            case "Products" -> {
                SanPhamView view = new SanPhamView();
                new SanPhamController(new SanPhamDAOImpl(), view);
                showSubView("Products", view);
            }
            case "Suppliers" -> {
                NhaCungCapView view = new NhaCungCapView();
                new NhaCungCapController(new NhaCungCapDAOImpl(), view);
            }
            case "Customers" -> {
                KhachHangView view = new KhachHangView();
                new KhachHangController(new com.example.services.KhachHangService(), view);
                showSubView("Customers", view);
            }
            case "Sales Orders" -> {
                HoaDonBanHangView view = new HoaDonBanHangView();
                new HoaDonBanHangController(new HoaDonBanHangDAOImpl(), view);
            }
            case "Payments" -> {
                ThanhToanView view = new ThanhToanView();
                new ThanhToanController(new ThanhToanDAOImpl(), view);
            }
            case "Reports" -> {
                ThongKeView view = new ThongKeView();
                new ThongKeController(new ThongKeDAOImpl(), view);
                showSubView("Reports", view);
            }
        }
    }

    private void showSubView(String title, JPanel panelView) {
        contentPanel.removeAll();

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UITheme.BG_DARK);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UITheme.BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER),
                new EmptyBorder(8, 15, 8, 15)));

        JButton btnBack = UITheme.createButton("<- Back", UITheme.BG_INPUT);
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