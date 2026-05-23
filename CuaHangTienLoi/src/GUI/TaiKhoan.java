package GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BLL.UserBLL;
import DTO.user;

public class TaiKhoan extends JPanel {

    private DefaultTableModel model;
    private JTable table;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JComboBox<String> cbStatus;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;

    private UserBLL userBLL = new UserBLL();

    public TaiKhoan() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(null);

        model = new DefaultTableModel(new Object[]{"Username", "Password", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 20, 760, 380);
        this.add(sp);

        JLabel l1 = new JLabel("Username:");
        l1.setBounds(20, 420, 80, 25);
        this.add(l1);
        tfUsername = new JTextField();
        tfUsername.setBounds(100, 420, 200, 25);
        this.add(tfUsername);

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(320, 420, 80, 25);
        this.add(l2);
        tfPassword = new JTextField();
        tfPassword.setBounds(400, 420, 200, 25);
        this.add(tfPassword);

        JLabel l3 = new JLabel("Status:");
        l3.setBounds(620, 420, 60, 25);
        this.add(l3);
        cbStatus = new JComboBox<>(new String[]{"0","1"});
        cbStatus.setBounds(680, 420, 100, 25);
        this.add(cbStatus);

        btnAdd = new JButton("Thêm");
        btnAdd.setBounds(100, 460, 120, 30);
        this.add(btnAdd);

        btnEdit = new JButton("Sửa");
        btnEdit.setBounds(260, 460, 120, 30);
        this.add(btnEdit);

        btnDelete = new JButton("Xóa");
        btnDelete.setBounds(420, 460, 120, 30);
        this.add(btnDelete);

        // Actions
        btnAdd.addActionListener(e -> {
            String u = tfUsername.getText().trim();
            String p = tfPassword.getText().trim();
            int s = Integer.parseInt((String)cbStatus.getSelectedItem());
            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập username và password");
                return;
            }
            user us = new user(u, p, s);
            userBLL.add(us);
            loadData();
        });

        btnDelete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r < 0) {
                JOptionPane.showMessageDialog(this, "Chọn 1 tài khoản để xóa");
                return;
            }
            String u = (String) model.getValueAt(r, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xóa tài khoản " + u + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                userBLL.delete(u);
                loadData();
            }
        });

        btnEdit.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r < 0) {
                JOptionPane.showMessageDialog(this, "Chọn 1 tài khoản để sửa");
                return;
            }
            String oldU = (String) model.getValueAt(r, 0);
            String u = tfUsername.getText().trim();
            String p = tfPassword.getText().trim();
            int s = Integer.parseInt((String)cbStatus.getSelectedItem());
            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập username và password");
                return;
            }
            user us = new user(u, p, s);
            userBLL.update(us, oldU);
            loadData();
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                if (r >= 0) {
                    tfUsername.setText((String) model.getValueAt(r, 0));
                    tfPassword.setText((String) model.getValueAt(r, 1));
                    cbStatus.setSelectedItem(String.valueOf(model.getValueAt(r, 2)));
                }
            }
        });

        loadData();
    }

    public void loadData() {
        model.setRowCount(0);
        try {
            ArrayList<user> users = userBLL.getALL();
            for (user u : users) {
                model.addRow(new Object[]{u.getUsername(), u.getPassword(), u.getStatus()});
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
