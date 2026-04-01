package com.example.config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * UIThemeConfig — Design System cho LaptopPU.
 * Glassmorphism dark-mode · Segoe UI · #0F1A20 / #3F84E5
 */
public class UIThemeConfig {

    // ========== PALETTE ==========
    public static final Color BG_DARK        = new Color(15, 26, 32);
    public static final Color BG_SIDEBAR     = new Color(10, 18, 22);
    public static final Color BG_CARD        = new Color(22, 38, 48);
    public static final Color BG_INPUT       = new Color(30, 52, 65);
    public static final Color BG_HOVER       = new Color(35, 60, 75);
    public static final Color BG_TABLE_ROW   = new Color(22, 38, 48);
    public static final Color BG_TABLE_ALT   = new Color(28, 46, 58);

    // Glassmorphism
    public static final Color BG_GLASS       = new Color(22, 38, 48, 180);
    public static final Color GLOW_BLUE      = new Color(63, 132, 229, 30);
    public static final Color SIDEBAR_ACTIVE = new Color(63, 132, 229, 25);

    // Accents
    public static final Color ACCENT         = new Color(63, 132, 229);
    public static final Color ACCENT_HOVER   = new Color(50, 110, 200);
    public static final Color ACCENT_GREEN   = new Color(40, 160, 100);
    public static final Color ACCENT_RED     = new Color(244, 44, 4);
    public static final Color ACCENT_YELLOW  = new Color(226, 133, 110);
    public static final Color ACCENT_PURPLE  = new Color(130, 170, 230);

    // Text
    public static final Color TEXT_PRIMARY   = new Color(247, 255, 247);
    public static final Color TEXT_SECONDARY = new Color(180, 210, 200);
    public static final Color TEXT_MUTED     = new Color(100, 140, 155);

    // Border
    public static final Color BORDER         = new Color(40, 65, 82);
    public static final Color BORDER_ACCENT  = new Color(63, 132, 229);

    // ========== FONTS ==========
    public static final Font FONT_TITLE    = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BODY     = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL    = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_MENU     = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_TABLE    = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_HEADER   = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FONT_STAT     = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font FONT_STAT_LBL = new Font("Segoe UI", Font.PLAIN, 12);

    // ========== GLOBAL THEME ==========
    public static void applyGlobalTheme() {
        UIManager.put("Panel.background",             new ColorUIResource(BG_DARK));
        UIManager.put("OptionPane.background",        new ColorUIResource(BG_CARD));
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("OptionPane.messageFont",       FONT_BODY);
        UIManager.put("OptionPane.buttonFont",        FONT_BODY);
        UIManager.put("Button.background",            new ColorUIResource(BG_INPUT));
        UIManager.put("Button.foreground",            new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("Button.font",                  FONT_BODY);
        UIManager.put("Button.focus",                 new ColorUIResource(BG_INPUT));
        UIManager.put("TextField.background",         new ColorUIResource(BG_INPUT));
        UIManager.put("TextField.foreground",         new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("TextField.caretForeground",    new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("ComboBox.background",          new ColorUIResource(BG_INPUT));
        UIManager.put("ComboBox.foreground",          new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(ACCENT));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.WHITE));
        UIManager.put("Label.foreground",             new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("TitledBorder.titleColor",      new ColorUIResource(ACCENT));
        UIManager.put("ScrollPane.background",        new ColorUIResource(BG_DARK));
        UIManager.put("Viewport.background",          new ColorUIResource(BG_DARK));
        UIManager.put("OptionPane.buttonAreaBorder",  BorderFactory.createEmptyBorder(6, 0, 6, 0));
        UIManager.put("OptionPane.minimumSize",       new Dimension(300, 120));
        UIManager.put("List.background",              new ColorUIResource(BG_CARD));
        UIManager.put("List.foreground",              new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("List.selectionBackground",     new ColorUIResource(ACCENT));
        UIManager.put("List.selectionForeground",     new ColorUIResource(Color.WHITE));
        UIManager.put("ScrollBarUI",                  DarkScrollBarUI.class.getName());
        UIManager.put("ScrollBar.width",              10);
    }

    // ========== GLASS PANELS ==========

    /** Tạo panel glass — nền translucent + round border (không blur thật, Swing không hỗ trợ) */
    public static JPanel createGlassPanel() {
        return new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_GLASS);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                g2.setColor(BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth() - 1, getHeight() - 1, 16, 16));
                g2.dispose();
            }

            @Override public boolean isOpaque() { return false; }
        };
    }

    /** Tạo panel glass với layout */
    public static JPanel createGlassPanel(LayoutManager layout) {
        JPanel p = createGlassPanel();
        p.setLayout(layout);
        return p;
    }

    // ========== STAT CARD ==========

    /** Card thống kê trên dashboard — glassmorphism với accent glow */
    public static JPanel createStatCard(String title, String value, String icon, Color accent) {
        JPanel card = new JPanel() {
            private boolean hover = false;
            {
                setOpaque(false);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                    public void mouseExited(MouseEvent e) { hover = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Glass bg
                g2.setColor(hover ? BG_HOVER : BG_GLASS);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                // Accent top bar
                g2.setColor(accent);
                g2.fillRoundRect(0, 0, getWidth(), 3, 4, 4);
                // Hover glow
                if (hover) {
                    g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 20));
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                }
                // Border
                g2.setColor(hover ? accent : BORDER);
                g2.setStroke(new BasicStroke(hover ? 1.5f : 1f));
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth() - 1, getHeight() - 1, 16, 16));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setLayout(new BorderLayout(10, 5));
        card.setBorder(new EmptyBorder(18, 22, 18, 22));

        // Icon circle
        JPanel iconPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 40));
                g2.fillOval(0, 0, 44, 44);
                g2.setColor(accent);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(icon, (44 - fm.stringWidth(icon)) / 2, (44 + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(44, 44));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(FONT_STAT_LBL);
        lblTitle.setForeground(TEXT_MUTED);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(FONT_STAT);
        lblValue.setForeground(TEXT_PRIMARY);

        textPanel.add(lblTitle);
        textPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        textPanel.add(lblValue);

        card.add(iconPanel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    // ========== STATUS BADGE ==========

    /** Badge trạng thái cho đơn hàng — pill colored */
    public static JLabel createStatusBadge(String text, String type) {
        Color bg, fg;
        switch (type.toLowerCase()) {
            case "done", "completed", "da thanh toan" -> { bg = new Color(40, 160, 100, 50); fg = ACCENT_GREEN; }
            case "pending", "chua thanh toan"         -> { bg = new Color(226, 133, 110, 50); fg = ACCENT_YELLOW; }
            case "cancelled", "da huy"                -> { bg = new Color(244, 44, 4, 50); fg = ACCENT_RED; }
            default                                   -> { bg = new Color(100, 140, 155, 50); fg = TEXT_MUTED; }
        };
        JLabel badge = new JLabel(text, SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setOpaque(false);
        badge.setForeground(fg);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setBorder(new EmptyBorder(3, 10, 3, 10));
        return badge;
    }

    // ========== SIDEBAR ITEM ==========

    // ========== IMAGE ICON HELPER ==========

    /** Load ảnh từ resources và scale lại kích thước */
    public static ImageIcon loadScaledIcon(String path, int width, int height) {
        try {
            java.net.URL imgURL = UIThemeConfig.class.getResource(path);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                Image scaled = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        } catch (Exception e) {
            System.err.println("Lỗi load icon: " + path);
        }
        return null; // Trả về null để UI tự fallback nếu không có
    }

    // ========== SIDEBAR ITEM ==========

    /** Menu item cho sidebar — hover effect + active glow (dùng Emoji String) */
    public static JPanel createSidebarItem(String icon, String text, boolean active, Runnable onClick) {
        return createSidebarItemBase(new JLabel(icon), text, active, onClick, true);
    }

    /** Menu item cho sidebar — Hover effect + active glow (Dùng ImageIcon) */
    public static JPanel createSidebarItem(Icon icon, String text, boolean active, Runnable onClick) {
        JLabel lblIcon = new JLabel();
        if (icon != null) {
            lblIcon.setIcon(icon);
        } else {
            lblIcon.setText("🔹"); // Fallback nếu chết icon
        }
        return createSidebarItemBase(lblIcon, text, active, onClick, false);
    }

    private static JPanel createSidebarItemBase(JLabel lblIcon, String text, boolean active, Runnable onClick, boolean isTextIcon) {
        JPanel item = new JPanel(new BorderLayout(10, 0)) {
            private boolean hover = false;
            {
                setOpaque(false);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                    public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                    public void mouseClicked(MouseEvent e)  { onClick.run(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (active) {
                    g2.setColor(SIDEBAR_ACTIVE);
                    g2.fillRoundRect(4, 0, getWidth() - 8, getHeight(), 8, 8);
                    // Left accent bar
                    g2.setColor(ACCENT);
                    g2.fillRoundRect(0, 4, 3, getHeight() - 8, 3, 3);
                } else if (hover) {
                    g2.setColor(new Color(255, 255, 255, 8));
                    g2.fillRoundRect(4, 0, getWidth() - 8, getHeight(), 8, 8);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        item.setBorder(new EmptyBorder(10, 16, 10, 16));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

        if (isTextIcon) {
            lblIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            lblIcon.setForeground(active ? ACCENT : TEXT_MUTED);
        }
        lblIcon.setPreferredSize(new Dimension(24, 24));
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblText = new JLabel(text);
        lblText.setFont(FONT_MENU);
        lblText.setForeground(active ? TEXT_PRIMARY : TEXT_SECONDARY);

        item.add(lblIcon, BorderLayout.WEST);
        item.add(lblText, BorderLayout.CENTER);
        return item;
    }

    // ========== FACTORY: BUTTONS ==========

    public static JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BODY);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 34));
        btn.setBorder(new EmptyBorder(7, 16, 7, 16));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setUI(new RoundedButtonUI(bgColor));
        return btn;
    }

    public static JButton createPrimaryButton(String text) { return createButton(text, ACCENT); }
    public static JButton createDangerButton(String text)  { return createButton(text, ACCENT_RED); }
    public static JButton createSuccessButton(String text) { return createButton(text, ACCENT_GREEN); }

    // ========== FACTORY: INPUTS ==========

    public static JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(FONT_BODY);
        tf.setForeground(TEXT_PRIMARY);
        tf.setBackground(BG_INPUT);
        tf.setCaretColor(TEXT_PRIMARY);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(5, 10, 5, 10)));
        return tf;
    }

    public static JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_BODY);
        cb.setBackground(BG_INPUT);
        cb.setForeground(TEXT_PRIMARY);
        cb.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setFont(FONT_BODY);
                lbl.setBackground(isSelected ? ACCENT : BG_CARD);
                lbl.setForeground(isSelected ? Color.WHITE : TEXT_PRIMARY);
                lbl.setBorder(new EmptyBorder(4, 10, 4, 10));
                return lbl;
            }
        });
        return cb;
    }

    // ========== FACTORY: PANELS & LABELS ==========

    public static JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(15, 15, 15, 15)));
        return card;
    }

    public static JPanel createAccentCard() {
        JPanel card = new JPanel();
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT, 1),
                new EmptyBorder(15, 15, 15, 15)));
        return card;
    }

    public static JLabel createTitleLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    public static JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_BODY);
        lbl.setForeground(TEXT_SECONDARY);
        return lbl;
    }

    // ========== FACTORY: TABLE ==========

    public static void styleTable(JTable table) {
        table.setFont(FONT_TABLE);
        table.setForeground(TEXT_PRIMARY);
        table.setBackground(BG_TABLE_ROW);
        table.setSelectionBackground(ACCENT);
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(BORDER);
        table.setRowHeight(30);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_HEADER);
        header.setForeground(TEXT_PRIMARY);
        header.setBackground(BG_SIDEBAR);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT));
        header.setPreferredSize(new Dimension(0, 36));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? BG_TABLE_ROW : BG_TABLE_ALT);
                    c.setForeground(TEXT_PRIMARY);
                }
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
    }

    public static JScrollPane createScrollPane(JTable table) {
        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(BG_DARK);
        sp.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        sp.getVerticalScrollBar().setBackground(BG_DARK);
        sp.getHorizontalScrollBar().setBackground(BG_DARK);
        return sp;
    }

    // ========== DIVIDER ==========
    public static JSeparator createDivider() {
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        sep.setBackground(BG_DARK);
        return sep;
    }

    // ========== INNER: RoundedButtonUI ==========
    private static class RoundedButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        private final Color bgColor;
        RoundedButtonUI(Color bgColor) { this.bgColor = bgColor; }

        @Override public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            AbstractButton btn = (AbstractButton) c;
            ButtonModel model = btn.getModel();
            Color bg = model.isPressed() ? bgColor.darker().darker()
                    : model.isRollover() ? bgColor.brighter() : bgColor;
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 8, 8);
            g2.dispose();
            super.paint(g, c);
        }
    }

    // ========== INNER: DarkScrollBarUI ==========
    public static class DarkScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        public static javax.swing.plaf.ComponentUI createUI(JComponent c) { return new DarkScrollBarUI(); }

        @Override protected void configureScrollBarColors() {
            this.thumbColor = new Color(63, 132, 229, 130);
            this.thumbHighlightColor = ACCENT;
            this.trackColor = BG_DARK;
        }

        @Override protected JButton createDecreaseButton(int o) { return zeroBtn(); }
        @Override protected JButton createIncreaseButton(int o) { return zeroBtn(); }

        private JButton zeroBtn() {
            JButton b = new JButton();
            Dimension d = new Dimension(0, 0);
            b.setPreferredSize(d); b.setMinimumSize(d); b.setMaximumSize(d);
            return b;
        }

        @Override protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
            if (r.isEmpty() || !scrollbar.isEnabled()) return;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(isThumbRollover() ? thumbHighlightColor : thumbColor);
            g2.fillRoundRect(r.x + 2, r.y + 2, r.width - 4, r.height - 4, 8, 8);
            g2.dispose();
        }

        @Override protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(trackColor);
            g2.fillRect(r.x, r.y, r.width, r.height);
            g2.dispose();
        }
    }
}