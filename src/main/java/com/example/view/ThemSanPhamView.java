package com.example.view;
                    
import com.example.controller.ThemSanPhamController;
import com.example.dao.SanPhamDAO;
import javax.swing.*;
import java.awt.*;

public class ThemSanPhamView extends JFrame {
    // Khai báo các thành phần
    public JTextField txtLoaiMay, txtTenSP, txtCPU, txtGPU, txtRAM, txtOCung, txtCanNang;
    public JTextField txtKTManHinh, txtDPGiaiMH, txtSoLuong, txtGiaBan, txtGiaNhap, txtBaoHanh;
    public JButton btnThem, btnClear;
    private ThemSanPhamController controller;

    public ThemSanPhamView() {
        setTitle("Thêm sản phẩm");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0, 204, 204)); // Màu xanh cyan nhạt

        JLabel lblTitle = new JLabel("THÊM SẢN PHẨM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setBounds(330, 10, 300, 30);
        panel.add(lblTitle);

        // --- CỘT BÊN TRÁI ---
        panel.add(createLabel("Loại máy:", 50, 70));
        txtLoaiMay = createTextField(180, 70, panel);

        panel.add(createLabel("Tên sản phẩm:", 50, 110));
        txtTenSP = createTextField(180, 110, panel);

        panel.add(createLabel("CPU:", 50, 150));
        txtCPU = createTextField(180, 150, panel);

        panel.add(createLabel("GPU:", 50, 190));
        txtGPU = createTextField(180, 190, panel);

        panel.add(createLabel("RAM:", 50, 230));
        txtRAM = createTextField(180, 230, panel);

        panel.add(createLabel("Ổ cứng:", 50, 270));
        txtOCung = createTextField(180, 270, panel);

        panel.add(createLabel("Cân nặng:", 50, 310));
        txtCanNang = createTextField(180, 310, panel);

        // --- CỘT BÊN PHẢI ---
        int labelWidthRight = 150;
        int textFieldXRight = 480 + labelWidthRight + 10;

        panel.add(createLabel("Kích thước MH:", 480, 70, labelWidthRight));
        txtKTManHinh = createTextField(textFieldXRight, 70, panel);

        panel.add(createLabel("Độ phân giải MH:", 480, 110, labelWidthRight));
        txtDPGiaiMH = createTextField(textFieldXRight, 110, panel);

        panel.add(createLabel("Số lượng:", 480, 150, labelWidthRight));
        txtSoLuong = createTextField(textFieldXRight, 150, panel);

        panel.add(createLabel("Giá bán:", 480, 190, labelWidthRight));
        txtGiaBan = createTextField(textFieldXRight, 190, panel);

        panel.add(createLabel("Giá nhập:", 480, 230, labelWidthRight));
        txtGiaNhap = createTextField(textFieldXRight, 230, panel);

        panel.add(createLabel("Thời gian bảo hành:", 480, 270, labelWidthRight));
        txtBaoHanh = createTextField(textFieldXRight, 270, panel);

        // Nút Thêm
        btnThem = new JButton("Thêm sản phẩm");
        btnThem.setForeground(Color.BLUE);
        btnThem.setBounds(710, 20, 150, 35);
        panel.add(btnThem);

        // Nút Clear
        btnClear = new JButton("Clear");
        btnClear.setForeground(Color.RED);
        btnClear.setBounds(740, 380, 100, 35);
        panel.add(btnClear);

        add(panel);

        // Khởi tạo controller để xử lý sự kiện
        controller = new ThemSanPhamController(new SanPhamDAO(), this);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x, y, 130, 25);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        return lbl;
    }

    private JLabel createLabel(String text, int x, int y, int width) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x, y, width, 25);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        return lbl;
    }

    private JTextField createTextField(int x, int y, JPanel panel) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, 150, 25);
        panel.add(txt);
        return txt;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ThemSanPhamView().setVisible(true));
    }
}