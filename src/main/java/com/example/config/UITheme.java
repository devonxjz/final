package com.example.config;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Lớp tiện ích quản lý theme Dark Mode hiện đại cho toàn bộ ứng dụng.
 * Cung cấp các hằng màu, factory method tạo component đã styled sẵn.
 */
public class UITheme {

    // ========== BẢN MÀU CHÍNH (Dark Mode Palette) ==========
    public static final Color BG_DARK       = new Color(17, 24, 39);       // #111827 - nền chính
    public static final Color BG_SIDEBAR    = new Color(15, 23, 42);       // #0F172A - sidebar
    public static final Color BG_CARD       = new Color(30, 41, 59);       // #1E293B - card/panel
    public static final Color BG_INPUT      = new Color(51, 65, 85);       // #334155 - input field
    public static final Color BG_HOVER      = new Color(51, 65, 85);       // #334155 - hover
    public static final Color BG_TABLE_ROW  = new Color(30, 41, 59);       // #1E293B - table row
    public static final Color BG_TABLE_ALT  = new Color(39, 52, 71);       // #273447 - alternate row

    public static final Color ACCENT        = new Color(56, 189, 248);     // #38BDF8 - cyan accent
    public static final Color ACCENT_HOVER  = new Color(14, 165, 233);     // #0EA5E9 - hover
    public static final Color ACCENT_GREEN  = new Color(52, 211, 153);     // #34D399 - success
    public static final Color ACCENT_RED    = new Color(248, 113, 113);    // #F87171 - danger
    public static final Color ACCENT_YELLOW = new Color(251, 191, 36);     // #FBBF24 - warning
    public static final Color ACCENT_PURPLE = new Color(167, 139, 250);    // #A78BFA - purple

    public static final Color TEXT_PRIMARY   = new Color(241, 245, 249);   // #F1F5F9
    public static final Color TEXT_SECONDARY = new Color(148, 163, 184);   // #94A3B8
    public static final Color TEXT_MUTED     = new Color(100, 116, 139);   // #64748B

    public static final Color BORDER        = new Color(51, 65, 85);       // #334155

    // ========== FONT ==========
    public static final Font FONT_TITLE     = new Font("Segoe UI Emoji", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE  = new Font("Segoe UI Emoji", Font.BOLD, 16);
    public static final Font FONT_BODY      = new Font("Segoe UI Emoji", Font.PLAIN, 13);
    public static final Font FONT_SMALL     = new Font("Segoe UI Emoji", Font.PLAIN, 11);
    public static final Font FONT_MENU      = new Font("Segoe UI Emoji", Font.PLAIN, 14);
    public static final Font FONT_TABLE     = new Font("Segoe UI Emoji", Font.PLAIN, 12);
    public static final Font FONT_HEADER    = new Font("Segoe UI Emoji", Font.BOLD, 12);

    // ========== FACTORY METHODS ==========

    /** Áp dụng dark theme cho toàn bộ UIManager (gọi 1 lần trong Main) */
    public static void applyGlobalTheme() {
        UIManager.put("Panel.background", new ColorUIResource(BG_DARK));
        UIManager.put("OptionPane.background", new ColorUIResource(BG_CARD));
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("OptionPane.messageFont", FONT_BODY);
        UIManager.put("OptionPane.buttonFont", FONT_BODY);
        UIManager.put("Button.background", new ColorUIResource(BG_INPUT));
        UIManager.put("Button.foreground", new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("Button.font", FONT_BODY);
        UIManager.put("Button.focus", new ColorUIResource(BG_INPUT));
        UIManager.put("TextField.background", new ColorUIResource(BG_INPUT));
        UIManager.put("TextField.foreground", new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("TextField.caretForeground", new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("ComboBox.background", new ColorUIResource(BG_INPUT));
        UIManager.put("ComboBox.foreground", new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(ACCENT.darker()));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.WHITE));
        UIManager.put("Label.foreground", new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("TitledBorder.titleColor", new ColorUIResource(ACCENT));
        UIManager.put("ScrollPane.background", new ColorUIResource(BG_DARK));
        UIManager.put("Viewport.background", new ColorUIResource(BG_DARK));

        // OptionPane dialog buttons — dark mode
        UIManager.put("OptionPane.buttonAreaBorder", BorderFactory.createEmptyBorder(6, 0, 6, 0));
        UIManager.put("OptionPane.minimumSize", new Dimension(300, 120));

        // List (for ComboBox popup)
        UIManager.put("List.background", new ColorUIResource(BG_CARD));
        UIManager.put("List.foreground", new ColorUIResource(TEXT_PRIMARY));
        UIManager.put("List.selectionBackground", new ColorUIResource(ACCENT.darker()));
        UIManager.put("List.selectionForeground", new ColorUIResource(Color.WHITE));

        // Custom ScrollBar
        UIManager.put("ScrollBarUI", DarkScrollBarUI.class.getName());
        UIManager.put("ScrollBar.width", 12);
    }

    /** Tạo nút bấm với style accent */
    public static JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BODY);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));

        // Rounded corners effect
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setUI(new RoundedButtonUI(bgColor));
        return btn;
    }

    /** Tạo nút accent chính (xanh dương) */
    public static JButton createPrimaryButton(String text) {
        return createButton(text, ACCENT);
    }

    /** Tạo nút nguy hiểm (đỏ) */
    public static JButton createDangerButton(String text) {
        return createButton(text, ACCENT_RED);
    }

    /** Tạo ComboBox dark mode */
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
                if (isSelected) {
                    lbl.setBackground(ACCENT.darker());
                    lbl.setForeground(Color.WHITE);
                } else {
                    lbl.setBackground(BG_CARD);
                    lbl.setForeground(TEXT_PRIMARY);
                }
                lbl.setBorder(new EmptyBorder(4, 8, 4, 8));
                return lbl;
            }
        });
        return cb;
    }

    /** Tạo nút thành công (xanh lá) */
    public static JButton createSuccessButton(String text) {
        return createButton(text, ACCENT_GREEN);
    }

    /** Tạo text field dark mode */
    public static JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(FONT_BODY);
        tf.setForeground(TEXT_PRIMARY);
        tf.setBackground(BG_INPUT);
        tf.setCaretColor(TEXT_PRIMARY);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        return tf;
    }

    /** Style cho JTable dark mode */
    public static void styleTable(JTable table) {
        table.setFont(FONT_TABLE);
        table.setForeground(TEXT_PRIMARY);
        table.setBackground(BG_TABLE_ROW);
        table.setSelectionBackground(ACCENT.darker());
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(BORDER);
        table.setRowHeight(30);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_HEADER);
        header.setForeground(TEXT_PRIMARY);
        header.setBackground(BG_SIDEBAR);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT));
        header.setPreferredSize(new Dimension(0, 35));

        // Alternate row renderer
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? BG_TABLE_ROW : BG_TABLE_ALT);
                    c.setForeground(TEXT_PRIMARY);
                }
                setBorder(new EmptyBorder(0, 8, 0, 8));
                return c;
            }
        });
    }

    /** Tạo panel card với bo góc */
    public static JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        return card;
    }

    /** Tạo label tiêu đề */
    public static JLabel createTitleLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    /** Tạo label phụ */
    public static JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_BODY);
        lbl.setForeground(TEXT_SECONDARY);
        return lbl;
    }

    /** Style cho ScrollPane */
    public static JScrollPane createScrollPane(JTable table) {
        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(BG_DARK);
        sp.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        return sp;
    }

    // ========== INNER CLASS: Rounded Button UI ==========
    private static class RoundedButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        private final Color bgColor;

        public RoundedButtonUI(Color bgColor) {
            this.bgColor = bgColor;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            AbstractButton btn = (AbstractButton) c;
            ButtonModel model = btn.getModel();

            Color bg = bgColor;
            if (model.isPressed()) {
                bg = bgColor.darker();
            } else if (model.isRollover()) {
                bg = bgColor.brighter();
            }

            // Paint rounded background
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10);
            g2.dispose();

            // Delegate text painting to super — handles emoji/surrogate pairs correctly
            super.paint(g, c);
        }
    }

    // ========== INNER CLASS: Dark ScrollBar UI ==========
    public static class DarkScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        
        public static javax.swing.plaf.ComponentUI createUI(JComponent c) {
            return new DarkScrollBarUI();
        }

        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = TEXT_MUTED; // Màu xám nhạt
            this.thumbHighlightColor = TEXT_SECONDARY;
            this.trackColor = BG_DARK;
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            Dimension zeroDim = new Dimension(0, 0);
            button.setPreferredSize(zeroDim);
            button.setMinimumSize(zeroDim);
            button.setMaximumSize(zeroDim);
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) return;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            Color color = isThumbRollover() ? thumbHighlightColor : thumbColor;
            g2.setColor(color);
            int arc = 8;
            g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, 
                             thumbBounds.width - 4, thumbBounds.height - 4, arc, arc);
            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(trackColor);
            g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            g2.dispose();
        }
    }
}
