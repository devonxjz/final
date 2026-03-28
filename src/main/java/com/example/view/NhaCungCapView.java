package com.example.view;

import com.example.entity.NhaCungCap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NhaCungCapView extends JFrame {
    public JTextField txtMaNCC, txtTenNCC, txtSDT, txtDiaChi, txtTimKiem;
    public JButton btnThem, btnReload, btnSave, btnUpdate, btnDelete;
    public JTable tableNCC;

    public NhaCungCapView() {
        setTitle("Quản lý nhà cung cấp");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JPanel panelMain = new JPanel(null);
        panelMain.setBackground(new Color(0, 204, 204));
        panelMain.setBounds(0, 0, 1000, 700);
        add(panelMain);

        // Tiêu đề
        JLabel lblTitle = new JLabel("QUẢN LÝ NHÀ CUNG CẤP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(173, 240, 255));
        lblTitle.setBounds(350, 10, 300, 40);
        panelMain.add(lblTitle);

        // Nút thêm nhà cung cấp
        btnThem = new JButton("Thêm nhà cung cấp");
        btnThem.setBounds(830, 10, 140, 30);
        btnThem.setForeground(Color.BLUE);
        panelMain.add(btnThem);

        // Panel form nhập liệu
        JPanel panelForm = new JPanel(null);
        panelForm.setBounds(20, 60, 950, 130);
        panelForm.setBackground(new Color(0, 204, 204));
        panelForm.setBorder(BorderFactory.createLineBorder(Color.gray));
        panelMain.add(panelForm);

        int labelWidth = 120, rowHeight = 35;
        int col1X = 20, col2X = 500;
        int y = 10;

        // Mã NCC
        panelForm.add(createLabel("Mã nhà cung cấp:", col1X, y));
        txtMaNCC = createTextField(col1X + labelWidth, y);
        panelForm.add(txtMaNCC);

        // Số điện thoại
        panelForm.add(createLabel("Số điện thoại:", col2X, y));
        txtSDT = createTextField(col2X + labelWidth, y);
        panelForm.add(txtSDT);

        // Tên NCC
        y += rowHeight;
        panelForm.add(createLabel("Tên nhà cung cấp:", col1X, y));
        txtTenNCC = createTextField(col1X + labelWidth, y);
        panelForm.add(txtTenNCC);

        // Địa chỉ
        panelForm.add(createLabel("Địa chỉ:", col2X, y));
        txtDiaChi = createTextField(col2X + labelWidth, y);
        panelForm.add(txtDiaChi);

        // Tìm kiếm
        JLabel lblTimKiem = new JLabel("Tìm kiếm:");
        lblTimKiem.setFont(new Font("Arial", Font.BOLD, 16));
        lblTimKiem.setBounds(20, 210, 100, 30);
        panelMain.add(lblTimKiem);

        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(110, 210, 350, 30);
        panelMain.add(txtTimKiem);

        // Nút reload
        btnReload = new JButton("Reload");
        btnReload.setBounds(880, 210, 90, 30);
        btnReload.setBackground(new Color(255, 255, 204));
        btnReload.setForeground(Color.RED);
        panelMain.add(btnReload);

        // Bảng NCC
        tableNCC = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableNCC);
        scrollPane.setBounds(20, 260, 950, 300);
        panelMain.add(scrollPane);

        // Các nút chức năng
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(700, 580, 90, 30);
        btnUpdate.setBackground(new Color(255, 255, 204));
        btnUpdate.setForeground(Color.RED);
        panelMain.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(800, 580, 90, 30);
        btnDelete.setBackground(new Color(255, 255, 204));
        btnDelete.setForeground(Color.RED);
        panelMain.add(btnDelete);

        btnSave = new JButton("Save");
        btnSave.setBounds(900, 580, 90, 30);
        btnSave.setBackground(new Color(255, 255, 204));
        btnSave.setForeground(Color.RED);
        panelMain.add(btnSave);

        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 120, 25);
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 200, 25);
        return textField;
    }

    public void setEditableFields(boolean editable) {
        txtMaNCC.setEditable(false); // Mã NCC luôn không cho sửa (auto increment)
        txtTenNCC.setEditable(editable);
        txtDiaChi.setEditable(editable);
        txtSDT.setEditable(editable);

        Color bg = editable ? Color.WHITE : new Color(230, 230, 230);
        txtMaNCC.setBackground(new Color(230, 230, 230));
        txtTenNCC.setBackground(bg);
        txtDiaChi.setBackground(bg);
        txtSDT.setBackground(bg);
    }

    // Hiển thị danh sách nhà cung cấp
    public void hienThiDanhSachNCC(List<NhaCungCap> danhSach) {
        String[] columnNames = {"Mã NCC", "Tên NCC", "SĐT", "Địa chỉ"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (NhaCungCap ncc : danhSach) {
            Object[] rowData = {
                    ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSdt(), ncc.getDiaChi()
            };
            model.addRow(rowData);
        }
        tableNCC.setModel(model);
    }
}