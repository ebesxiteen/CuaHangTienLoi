package GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import BLL.UserBLL;
import DTO.user;

public class TaiKhoan extends JPanel {

    private DefaultTableModel model;
    private JTable table;
    private JTextField tfSearch;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JComboBox<String> cbStatus;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private ArrayList<user> accountList = new ArrayList<>();

    private UserBLL userBLL = new UserBLL();

    public TaiKhoan() {
        buildUI();
        loadData();
    }

    private void onAdd() {
        String u = tfUsername.getText().trim();
        String p = tfPassword.getText().trim();
        int s = Integer.parseInt((String) cbStatus.getSelectedItem());
        if (u.isEmpty() || p.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập username và password");
            return;
        }
        user us = new user(u, p, s);
        userBLL.add(us);
        loadData();
    }

    private void onDelete() {
        int r = table.getSelectedRow();
        if (r < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Chọn 1 tài khoản để xóa");
            return;
        }
        String u = (String) model.getValueAt(r, 0);
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Xóa tài khoản " + u + " ?", "Xác nhận", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            userBLL.delete(u);
            loadData();
        }
    }

    private void onEdit() {
        int r = table.getSelectedRow();
        if (r < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Chọn 1 tài khoản để sửa");
            return;
        }
        String oldU = (String) model.getValueAt(r, 0);
        String u = tfUsername.getText().trim();
        String p = tfPassword.getText().trim();
        int s = Integer.parseInt((String) cbStatus.getSelectedItem());
        if (u.isEmpty() || p.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập username và password");
            return;
        }
        user us = new user(u, p, s);
        userBLL.update(us, oldU);
        loadData();
    }
    private void buildUI() {
        setLayout(new java.awt.BorderLayout(10, 10));
        setBackground(java.awt.Color.WHITE);
        setPreferredSize(new java.awt.Dimension(980, 650));

        javax.swing.JLabel title = new javax.swing.JLabel("TÀI KHOẢN", javax.swing.SwingConstants.LEFT);
        title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 26));
        title.setForeground(new java.awt.Color(0, 102, 102));
        title.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 0, 10));

        javax.swing.JPanel titlePanel = new javax.swing.JPanel(new java.awt.BorderLayout(8, 0));
        titlePanel.setBackground(java.awt.Color.WHITE);
        titlePanel.add(title, java.awt.BorderLayout.WEST);

        // search
        tfSearch = new JTextField();
        tfSearch.setPreferredSize(new java.awt.Dimension(240, 28));
        javax.swing.JButton btnSearch = new javax.swing.JButton("Tìm kiếm");
        btnSearch.setBackground(new java.awt.Color(0, 102, 102));
        btnSearch.setForeground(java.awt.Color.WHITE);
        btnSearch.setBorderPainted(false);
        btnSearch.setPreferredSize(new java.awt.Dimension(100, 28));

        javax.swing.JPanel searchPanel = new javax.swing.JPanel(new java.awt.BorderLayout(6, 0));
        searchPanel.setBackground(java.awt.Color.WHITE);
        searchPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 10));
        searchPanel.add(tfSearch, java.awt.BorderLayout.CENTER);
        searchPanel.add(btnSearch, java.awt.BorderLayout.EAST);
        titlePanel.add(searchPanel, java.awt.BorderLayout.EAST);

        add(titlePanel, java.awt.BorderLayout.NORTH);

        // table
        model = new DefaultTableModel(new Object[]{"Username", "Password", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setRowHeight(24);
        javax.swing.JScrollPane sp = new javax.swing.JScrollPane(table);
        add(sp, java.awt.BorderLayout.CENTER);

        // form + actions
        javax.swing.JPanel form = new javax.swing.JPanel();
        form.setBackground(java.awt.Color.WHITE);
        java.awt.GridBagLayout gbl = new java.awt.GridBagLayout();
        form.setLayout(gbl);
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(6, 8, 6, 8);

        javax.swing.JLabel lUser = new javax.swing.JLabel("Username:");
        lUser.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = java.awt.GridBagConstraints.EAST;
        form.add(lUser, gbc);

        tfUsername = new JTextField();
        tfUsername.setPreferredSize(new java.awt.Dimension(220, 28));
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = java.awt.GridBagConstraints.WEST;
        form.add(tfUsername, gbc);

        javax.swing.JLabel lPass = new javax.swing.JLabel("Password:");
        lPass.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        gbc.gridx = 2; gbc.gridy = 0; gbc.anchor = java.awt.GridBagConstraints.EAST;
        form.add(lPass, gbc);

        tfPassword = new JTextField();
        tfPassword.setPreferredSize(new java.awt.Dimension(220, 28));
        gbc.gridx = 3; gbc.gridy = 0; gbc.anchor = java.awt.GridBagConstraints.WEST;
        form.add(tfPassword, gbc);

        javax.swing.JLabel lStatus = new javax.swing.JLabel("Status:");
        lStatus.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        gbc.gridx = 4; gbc.gridy = 0; gbc.anchor = java.awt.GridBagConstraints.EAST;
        form.add(lStatus, gbc);

        cbStatus = new JComboBox<>(new String[]{"0","1"});
        cbStatus.setPreferredSize(new java.awt.Dimension(80, 28));
        gbc.gridx = 5; gbc.gridy = 0; gbc.anchor = java.awt.GridBagConstraints.WEST;
        form.add(cbStatus, gbc);

        btnAdd = new JButton("Thêm");
        btnAdd.setBackground(new java.awt.Color(102, 102, 102));
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnAdd.setPreferredSize(new java.awt.Dimension(100, 32));
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = java.awt.GridBagConstraints.WEST;
        form.add(btnAdd, gbc);

        btnEdit = new JButton("Sửa");
        btnEdit.setBackground(new java.awt.Color(0, 102, 102));
        btnEdit.setForeground(java.awt.Color.WHITE);
        btnEdit.setPreferredSize(new java.awt.Dimension(100, 32));
        gbc.gridx = 2; gbc.gridy = 1; gbc.anchor = java.awt.GridBagConstraints.WEST;
        form.add(btnEdit, gbc);

        btnDelete = new JButton("Xóa");
        btnDelete.setBackground(new java.awt.Color(153, 0, 0));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setPreferredSize(new java.awt.Dimension(100, 32));
        gbc.gridx = 3; gbc.gridy = 1; gbc.anchor = java.awt.GridBagConstraints.WEST;
        form.add(btnDelete, gbc);

        add(form, java.awt.BorderLayout.SOUTH);

        // Wire actions
        btnAdd.addActionListener(e -> onAdd());
        btnDelete.addActionListener(e -> onDelete());
        btnEdit.addActionListener(e -> onEdit());
        btnSearch.addActionListener(e -> applyKeywordFilter());
        tfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applyKeywordFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applyKeywordFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applyKeywordFilter();
            }
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
    }

    public void loadData() {
        try {
            accountList = userBLL.getALL();
            applyKeywordFilter();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void applyKeywordFilter() {
        String keyword = tfSearch == null ? "" : tfSearch.getText().trim().toLowerCase();
        model.setRowCount(0);
        for (user u : accountList) {
            boolean matches = keyword.isEmpty()
                    || containsKeyword(u.getUsername(), keyword)
                    || containsKeyword(u.getPassword(), keyword)
                    || containsKeyword(String.valueOf(u.getStatus()), keyword);
            if (matches) {
                model.addRow(new Object[]{u.getUsername(), u.getPassword(), u.getStatus()});
            }
        }
    }

    private boolean containsKeyword(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }
}
