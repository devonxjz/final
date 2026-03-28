package com.example.view;

import javax.swing.*;
import java.awt.*;

public class TrangChuView extends JFrame {

    public TrangChuView() {
        setTitle("Trang chủ");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Sử dụng BorderLayout để căn giữa
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0, 204, 204));

        JLabel lblWelcome = new JLabel("WELCOME!", JLabel.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 36));
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setVerticalAlignment(SwingConstants.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false); // Giữ nền của frame

        centerPanel.add(lblWelcome, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TrangChuView::new);
    }
}