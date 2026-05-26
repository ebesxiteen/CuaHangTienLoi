package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BLL.KhachHangBLL;
import DTO.khachHang;

public class KhachHangView extends javax.swing.JPanel {

    DefaultTableModel model;
    khachHang kh = new khachHang();
    private ArrayList<khachHang> list = new ArrayList<>();
    KhachHangBLL khBll = new KhachHangBLL(this);
    private int count = 0;
    private String matmp, tentmp, sdttmp, emailtmp;
    private boolean updatingFilters;

    public javax.swing.JButton btn_luu;
    public javax.swing.JButton btn_reset;
    public javax.swing.JButton btn_sua;
    public javax.swing.JButton btn_them;
    public javax.swing.JButton btn_xoa;
    public javax.swing.JTextField email_kh;
    public javax.swing.JTextField ma_kh;
    public javax.swing.JTextField sdt_kh;
    public javax.swing.JTable table_kh;
    public javax.swing.JTextField ten_kh;

    private JButton btnSearch;
    private JTextField txtSearch;
    private JComboBox<String> cboMaKHFilter;
    private JComboBox<String> cboTenKHFilter;
    private JComboBox<String> cboSdtFilter;

    public KhachHangView() {
        initComponents();
        UnEditable();
        wireEvents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1015, 690));

        JLabel title = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 26));
        title.setForeground(new Color(0, 102, 102));

        txtSearch = new JTextField();
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(0, 102, 102));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setBorderPainted(false);
        txtSearch.setPreferredSize(new Dimension(240, 28));
        btnSearch.setPreferredSize(new Dimension(100, 28));

        JPanel searchPanel = new JPanel(new BorderLayout(6, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);

        JPanel titlePanel = new JPanel(new BorderLayout(8, 0));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        titlePanel.add(title, BorderLayout.WEST);
        titlePanel.add(searchPanel, BorderLayout.EAST);
        add(titlePanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{
            "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Địa chỉ email"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table_kh = new JTable(model);
        table_kh.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_kh.setRowSelectionAllowed(true);
        table_kh.setCellSelectionEnabled(false);
        table_kh.setRowHeight(28);
        table_kh.getTableHeader().setResizingAllowed(false);
        table_kh.getTableHeader().setReorderingAllowed(false);
        table_kh.setDefaultEditor(Object.class, null);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table_kh.getColumnModel().getColumnCount(); i++) {
            table_kh.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
            table_kh.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        cboMaKHFilter = new JComboBox<>();
        cboTenKHFilter = new JComboBox<>();
        cboSdtFilter = new JComboBox<>();
        JPanel filterPanel = new JPanel(new GridLayout(1, 4, 0, 0));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.add(cboMaKHFilter);
        filterPanel.add(cboTenKHFilter);
        filterPanel.add(cboSdtFilter);
        filterPanel.add(new JLabel(""));

        JPanel tablePanel = new JPanel(new BorderLayout(8, 8));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        tablePanel.add(filterPanel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(table_kh), BorderLayout.CENTER);
        tablePanel.setMinimumSize(new Dimension(590, 0));

        ma_kh = new JTextField();
        ten_kh = new JTextField();
        sdt_kh = new JTextField();
        email_kh = new JTextField();

        btn_them = new JButton("Thêm");
        btn_sua = new JButton("Sửa");
        btn_reset = new JButton("Reset");
        btn_xoa = new JButton("Xóa");
        btn_luu = new JButton("Lưu");
        btn_them.setBackground(new Color(102, 102, 102));
        btn_sua.setBackground(new Color(102, 102, 102));
        btn_reset.setBackground(new Color(102, 102, 102));
        btn_xoa.setBackground(new Color(153, 0, 0));
        btn_luu.setBackground(new Color(0, 102, 0));
        btn_them.setForeground(Color.WHITE);
        btn_sua.setForeground(Color.WHITE);
        btn_reset.setForeground(Color.WHITE);
        btn_xoa.setForeground(Color.WHITE);
        btn_luu.setForeground(Color.WHITE);

        JPanel actionTop = new JPanel(new GridLayout(1, 3, 8, 0));
        actionTop.setBackground(Color.WHITE);
        actionTop.setPreferredSize(new Dimension(0, 38));
        actionTop.add(btn_them);
        actionTop.add(btn_sua);
        actionTop.add(btn_reset);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addFormRow(infoPanel, gbc, 0, "Mã khách hàng:", ma_kh);
        addFormRow(infoPanel, gbc, 1, "Tên khách hàng:", ten_kh);
        addFormRow(infoPanel, gbc, 2, "Số điện thoại:", sdt_kh);
        addFormRow(infoPanel, gbc, 3, "Địa chỉ email:", email_kh);

        JPanel actionBottom = new JPanel(new GridLayout(1, 2, 8, 0));
        actionBottom.setBackground(Color.WHITE);
        actionBottom.setPreferredSize(new Dimension(0, 38));
        actionBottom.add(btn_xoa);
        actionBottom.add(btn_luu);

        JPanel formPanel = new JPanel(new BorderLayout(8, 8));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
        formPanel.setMinimumSize(new Dimension(380, 0));
        formPanel.add(actionTop, BorderLayout.NORTH);
        formPanel.add(infoPanel, BorderLayout.CENTER);
        formPanel.add(actionBottom, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePanel, formPanel);
        splitPane.setResizeWeight(0.62);
        splitPane.setDividerLocation(620);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private void wireEvents() {
        table_kh.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }
                int row = table_kh.getSelectedRow();
                if (row >= 0) {
                    ma_kh.setText(String.valueOf(table_kh.getValueAt(row, 0)));
                    ten_kh.setText(String.valueOf(table_kh.getValueAt(row, 1)));
                    sdt_kh.setText(String.valueOf(table_kh.getValueAt(row, 2)));
                    email_kh.setText(String.valueOf(table_kh.getValueAt(row, 3)));
                }
            }
        });

        DocumentListener searchListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applyFilters();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applyFilters();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applyFilters();
            }
        };
        txtSearch.getDocument().addDocumentListener(searchListener);
        btnSearch.addActionListener(e -> applyFilters());
        cboMaKHFilter.addActionListener(e -> applyFilters());
        cboTenKHFilter.addActionListener(e -> applyFilters());
        cboSdtFilter.addActionListener(e -> applyFilters());

        btn_them.addActionListener(e -> btn_themActionPerformed());
        btn_luu.addActionListener(e -> btn_luuActionPerformed());
        btn_sua.addActionListener(e -> btn_suaActionPerformed());
        btn_xoa.addActionListener(e -> btn_xoaActionPerformed());
        btn_reset.addActionListener(e -> btn_resetActionPerformed());
    }

    public void loadTable(ArrayList<khachHang> list) {
        if (list.isEmpty()) {
            return;
        }
        khachHang kh2 = list.get(list.size() - 1);
        model.addRow(new Object[]{kh2.getMakh(), kh2.getTen(), kh2.getSdt(), kh2.getEmail()});
    }

    public void clearTable() {
        model.setRowCount(0);
    }

    public void getTable() {
        list = khBll.getALL();
        updateFilterCombos();
        applyFilters();
    }

    private void renderTable(ArrayList<khachHang> data) {
        model.setRowCount(0);
        for (khachHang row : data) {
            model.addRow(new Object[]{row.getMakh(), row.getTen(), row.getSdt(), row.getEmail()});
        }
    }

    private void updateFilterCombos() {
        updatingFilters = true;
        Object selectedMa = cboMaKHFilter.getSelectedItem();
        Object selectedTen = cboTenKHFilter.getSelectedItem();
        Object selectedSdt = cboSdtFilter.getSelectedItem();

        cboMaKHFilter.removeAllItems();
        cboTenKHFilter.removeAllItems();
        cboSdtFilter.removeAllItems();
        cboMaKHFilter.addItem("Tất cả mã KH");
        cboTenKHFilter.addItem("Tất cả tên KH");
        cboSdtFilter.addItem("Tất cả SĐT");

        for (khachHang item : list) {
            addUniqueComboItem(cboMaKHFilter, item.getMakh());
            addUniqueComboItem(cboTenKHFilter, item.getTen());
            addUniqueComboItem(cboSdtFilter, item.getSdt());
        }

        restoreComboSelection(cboMaKHFilter, selectedMa, 0);
        restoreComboSelection(cboTenKHFilter, selectedTen, 0);
        restoreComboSelection(cboSdtFilter, selectedSdt, 0);
        updatingFilters = false;
    }

    private void addUniqueComboItem(JComboBox<String> combo, String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (value.equals(combo.getItemAt(i))) {
                return;
            }
        }
        combo.addItem(value);
    }

    private void restoreComboSelection(JComboBox<String> combo, Object selectedValue, int defaultIndex) {
        if (selectedValue != null) {
            for (int i = 0; i < combo.getItemCount(); i++) {
                if (selectedValue.equals(combo.getItemAt(i))) {
                    combo.setSelectedIndex(i);
                    return;
                }
            }
        }
        combo.setSelectedIndex(defaultIndex);
    }

    private void applyFilters() {
        if (updatingFilters) {
            return;
        }
        String selectedMa = String.valueOf(cboMaKHFilter.getSelectedItem());
        String selectedTen = String.valueOf(cboTenKHFilter.getSelectedItem());
        String selectedSdt = String.valueOf(cboSdtFilter.getSelectedItem());
        String keyword = txtSearch.getText().trim().toLowerCase();

        boolean filterMa = selectedMa != null && !"Tất cả mã KH".equals(selectedMa);
        boolean filterTen = selectedTen != null && !"Tất cả tên KH".equals(selectedTen);
        boolean filterSdt = selectedSdt != null && !"Tất cả SĐT".equals(selectedSdt);
        boolean filterKeyword = !keyword.isEmpty();

        ArrayList<khachHang> result = new ArrayList<>();
        for (khachHang item : list) {
            boolean matchesMa = !filterMa || selectedMa.equals(item.getMakh());
            boolean matchesTen = !filterTen || selectedTen.equals(item.getTen());
            boolean matchesSdt = !filterSdt || selectedSdt.equals(item.getSdt());
            boolean matchesKeyword = !filterKeyword
                    || containsKeyword(item.getMakh(), keyword)
                    || containsKeyword(item.getTen(), keyword)
                    || containsKeyword(item.getSdt(), keyword)
                    || containsKeyword(item.getEmail(), keyword);
            if (matchesMa && matchesTen && matchesSdt && matchesKeyword) {
                result.add(item);
            }
        }
        renderTable(result);
    }

    private boolean containsKeyword(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    public void ResetFieldText() {
        ma_kh.setText("");
        ten_kh.setText("");
        sdt_kh.setText("");
        email_kh.setText("");
    }

    public void Editable() {
        btn_luu.setEnabled(true);
        ma_kh.setEditable(false);
        ten_kh.setEditable(true);
        sdt_kh.setEditable(true);
        email_kh.setEditable(true);
    }

    public void UnEditable() {
        btn_luu.setEnabled(false);
        ma_kh.setEditable(false);
        ten_kh.setEditable(false);
        sdt_kh.setEditable(false);
        email_kh.setEditable(false);
    }

    private String generateNextMaKH() {
        list = khBll.getALL();
        int maxNumber = 0;
        for (khachHang item : list) {
            String code = item.getMakh();
            if (code == null || !code.matches("kh\\d{3,}")) {
                continue;
            }
            try {
                maxNumber = Math.max(maxNumber, Integer.parseInt(code.substring(2)));
            } catch (NumberFormatException ex) {
                // Ignore invalid customer codes.
            }
        }
        return "kh" + String.format("%03d", maxNumber + 1);
    }

    private boolean validateInput(String ma, String ten, String sdt, String email) {
        if (ma.isEmpty() || ten.isEmpty() || sdt.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin");
            return false;
        }
        if (!sdt.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ");
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$")) {
            JOptionPane.showMessageDialog(this, "Địa chỉ email không hợp lệ");
            return false;
        }
        return true;
    }

    private boolean isDuplicateCode(String ma, String oldMa) {
        list = khBll.getALL();
        for (khachHang item : list) {
            if (item.getMakh().equals(ma) && (oldMa == null || !item.getMakh().equals(oldMa))) {
                return true;
            }
        }
        return false;
    }

    private void btn_themActionPerformed() {
        ResetFieldText();
        ma_kh.setText(generateNextMaKH());
        Editable();
        count = 1;
        btn_them.setEnabled(false);
    }

    private void btn_luuActionPerformed() {
        String ma = ma_kh.getText().trim();
        String ten = ten_kh.getText().trim();
        String sdt = sdt_kh.getText().trim();
        String email = email_kh.getText().trim();
        if (!validateInput(ma, ten, sdt, email)) {
            return;
        }
        if (count == 1 && isDuplicateCode(ma, null)) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại");
            return;
        }
        if (count == 2 && isDuplicateCode(ma, matmp)) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại");
            return;
        }

        if (count == 1) {
            kh = new khachHang(ma, ten, sdt, email);
            khBll.add(kh);
            btn_them.setEnabled(true);
        } else if (count == 2) {
            if (matmp.equals(ma) && tentmp.equals(ten) && sdttmp.equals(sdt) && emailtmp.equals(email)) {
                JOptionPane.showMessageDialog(this, "Chưa có thông tin nào được sửa đổi");
                return;
            }
            kh = new khachHang(ma, ten, sdt, email);
            khBll.update(kh, matmp);
            btn_sua.setEnabled(true);
        }
        ResetFieldText();
        UnEditable();
        clearTable();
        getTable();
    }

    private void btn_suaActionPerformed() {
        matmp = ma_kh.getText();
        tentmp = ten_kh.getText();
        sdttmp = sdt_kh.getText();
        emailtmp = email_kh.getText();
        if (matmp.isEmpty() && tentmp.isEmpty() && sdttmp.isEmpty() && emailtmp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần sửa");
            return;
        }
        Editable();
        count = 2;
        btn_sua.setEnabled(false);
    }

    private void btn_xoaActionPerformed() {
        String ma = ma_kh.getText();
        String ten = ten_kh.getText();
        String sdt = sdt_kh.getText();
        String email = email_kh.getText();
        if (ma.isEmpty() && ten.isEmpty() && sdt.isEmpty() && email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần xóa");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa ?");
        if (confirm == JOptionPane.YES_OPTION) {
            kh = new khachHang(ma, ten, sdt, email);
            khBll.delete(kh);
            ResetFieldText();
            clearTable();
            getTable();
        }
    }

    private void btn_resetActionPerformed() {
        ResetFieldText();
        txtSearch.setText("");
        if (cboMaKHFilter.getItemCount() > 0) {
            cboMaKHFilter.setSelectedIndex(0);
        }
        if (cboTenKHFilter.getItemCount() > 0) {
            cboTenKHFilter.setSelectedIndex(0);
        }
        if (cboSdtFilter.getItemCount() > 0) {
            cboSdtFilter.setSelectedIndex(0);
        }
        UnEditable();
        btn_them.setEnabled(true);
        btn_sua.setEnabled(true);
        clearTable();
        getTable();
    }
}
