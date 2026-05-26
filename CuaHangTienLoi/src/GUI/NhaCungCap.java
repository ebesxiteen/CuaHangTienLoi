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

import BLL.NhaCungCapBLL;
import DTO.nhaCungCap;

public class NhaCungCap extends javax.swing.JPanel {
    DefaultTableModel model;
    nhaCungCap ncc = new nhaCungCap();
    private ArrayList<nhaCungCap> list = new ArrayList<>();
    NhaCungCapBLL khBll = new NhaCungCapBLL(this);
    private int count = 0;
    private String matmp, tentmp, tennddtmp, sdttmp, dchitmp;
    private boolean updatingFilters;

    public javax.swing.JButton btn_luu;
    public javax.swing.JButton btn_reset;
    public javax.swing.JButton btn_sua;
    public javax.swing.JButton btn_them;
    public javax.swing.JButton btn_xoa;
    public javax.swing.JTextField dchi_ncc;
    public javax.swing.JTextField ma_ncc;
    public javax.swing.JTextField sdt_ncc;
    public javax.swing.JTable table_ncc;
    public javax.swing.JTextField ten_ncc;
    public javax.swing.JTextField ten_ndd;

    private JButton btnSearch;
    private JTextField txtSearch;
    private JComboBox<String> cboMaNCCFilter;
    private JComboBox<String> cboTenNCCFilter;
    private JComboBox<String> cboSdtFilter;

    public NhaCungCap() {
        initComponents();
        btn_luu.setEnabled(false);
        UnEditable();
        wireEvents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1015, 690));

        JLabel title = new JLabel("QUẢN LÝ NHÀ CUNG CẤP");
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
            "Mã nhà cung cấp", "Tên nhà cung cấp", "Tên người đại diện", "Số điện thoại", "Địa chỉ"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table_ncc = new JTable(model);
        table_ncc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_ncc.setRowSelectionAllowed(true);
        table_ncc.setCellSelectionEnabled(false);
        table_ncc.setRowHeight(28);
        table_ncc.getTableHeader().setResizingAllowed(false);
        table_ncc.getTableHeader().setReorderingAllowed(false);
        table_ncc.setDefaultEditor(Object.class, null);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table_ncc.getColumnModel().getColumnCount(); i++) {
            table_ncc.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
            table_ncc.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        cboMaNCCFilter = new JComboBox<>();
        cboTenNCCFilter = new JComboBox<>();
        cboSdtFilter = new JComboBox<>();
        JPanel filterPanel = new JPanel(new GridLayout(1, 5, 0, 0));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.add(cboMaNCCFilter);
        filterPanel.add(cboTenNCCFilter);
        filterPanel.add(cboSdtFilter);
        filterPanel.add(new JLabel(""));
        filterPanel.add(new JLabel(""));

        JPanel tablePanel = new JPanel(new BorderLayout(8, 8));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        tablePanel.add(filterPanel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(table_ncc), BorderLayout.CENTER);
        tablePanel.setMinimumSize(new Dimension(590, 0));

        ma_ncc = new JTextField();
        ten_ncc = new JTextField();
        ten_ndd = new JTextField();
        sdt_ncc = new JTextField();
        dchi_ncc = new JTextField();

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
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin nhà cung cấp"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addFormRow(infoPanel, gbc, 0, "Mã nhà cung cấp:", ma_ncc);
        addFormRow(infoPanel, gbc, 1, "Tên nhà cung cấp:", ten_ncc);
        addFormRow(infoPanel, gbc, 2, "Tên người đại diện:", ten_ndd);
        addFormRow(infoPanel, gbc, 3, "Số điện thoại:", sdt_ncc);
        addFormRow(infoPanel, gbc, 4, "Địa chỉ:", dchi_ncc);

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
        table_ncc.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }
                int row = table_ncc.getSelectedRow();
                if (row >= 0) {
                    ma_ncc.setText(String.valueOf(table_ncc.getValueAt(row, 0)));
                    ten_ncc.setText(String.valueOf(table_ncc.getValueAt(row, 1)));
                    ten_ndd.setText(String.valueOf(table_ncc.getValueAt(row, 2)));
                    sdt_ncc.setText(String.valueOf(table_ncc.getValueAt(row, 3)));
                    dchi_ncc.setText(String.valueOf(table_ncc.getValueAt(row, 4)));
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
        cboMaNCCFilter.addActionListener(e -> applyFilters());
        cboTenNCCFilter.addActionListener(e -> applyFilters());
        cboSdtFilter.addActionListener(e -> applyFilters());

        btn_them.addActionListener(e -> btn_themActionPerformed());
        btn_luu.addActionListener(e -> btn_luuActionPerformed());
        btn_sua.addActionListener(e -> btn_suaActionPerformed());
        btn_xoa.addActionListener(e -> btn_xoaActionPerformed());
        btn_reset.addActionListener(e -> btn_resetActionPerformed());
    }

    public void loadTable(ArrayList<nhaCungCap> list) {
        if (list.isEmpty()) {
            return;
        }
        nhaCungCap ncc2 = list.get(list.size() - 1);
        model.addRow(new Object[]{ncc2.getMaNCC(), ncc2.getTenNCC(), ncc2.getTenNDD(), ncc2.getSdt(), ncc2.getDiachi()});
    }

    public void clearTable() {
        model.setRowCount(0);
    }

    public void getTable() {
        list = khBll.getALL();
        updateFilterCombos();
        applyFilters();
    }

    private void renderTable(ArrayList<nhaCungCap> data) {
        model.setRowCount(0);
        for (nhaCungCap row : data) {
            model.addRow(new Object[]{row.getMaNCC(), row.getTenNCC(), row.getTenNDD(), row.getSdt(), row.getDiachi()});
        }
    }

    private void updateFilterCombos() {
        updatingFilters = true;
        Object selectedMa = cboMaNCCFilter.getSelectedItem();
        Object selectedTen = cboTenNCCFilter.getSelectedItem();
        Object selectedSdt = cboSdtFilter.getSelectedItem();

        cboMaNCCFilter.removeAllItems();
        cboTenNCCFilter.removeAllItems();
        cboSdtFilter.removeAllItems();
        cboMaNCCFilter.addItem("Tất cả mã NCC");
        cboTenNCCFilter.addItem("Tất cả tên NCC");
        cboSdtFilter.addItem("Tất cả SĐT");

        for (nhaCungCap item : list) {
            addUniqueComboItem(cboMaNCCFilter, item.getMaNCC());
            addUniqueComboItem(cboTenNCCFilter, item.getTenNCC());
            addUniqueComboItem(cboSdtFilter, item.getSdt());
        }

        restoreComboSelection(cboMaNCCFilter, selectedMa, 0);
        restoreComboSelection(cboTenNCCFilter, selectedTen, 0);
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
        String selectedMa = String.valueOf(cboMaNCCFilter.getSelectedItem());
        String selectedTen = String.valueOf(cboTenNCCFilter.getSelectedItem());
        String selectedSdt = String.valueOf(cboSdtFilter.getSelectedItem());
        String keyword = txtSearch.getText().trim().toLowerCase();

        boolean filterMa = selectedMa != null && !"Tất cả mã NCC".equals(selectedMa);
        boolean filterTen = selectedTen != null && !"Tất cả tên NCC".equals(selectedTen);
        boolean filterSdt = selectedSdt != null && !"Tất cả SĐT".equals(selectedSdt);
        boolean filterKeyword = !keyword.isEmpty();

        ArrayList<nhaCungCap> result = new ArrayList<>();
        for (nhaCungCap item : list) {
            boolean matchesMa = !filterMa || selectedMa.equals(item.getMaNCC());
            boolean matchesTen = !filterTen || selectedTen.equals(item.getTenNCC());
            boolean matchesSdt = !filterSdt || selectedSdt.equals(item.getSdt());
            boolean matchesKeyword = !filterKeyword
                    || containsKeyword(item.getMaNCC(), keyword)
                    || containsKeyword(item.getTenNCC(), keyword)
                    || containsKeyword(item.getTenNDD(), keyword)
                    || containsKeyword(item.getSdt(), keyword)
                    || containsKeyword(item.getDiachi(), keyword);
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
        ma_ncc.setText("");
        ten_ncc.setText("");
        ten_ndd.setText("");
        sdt_ncc.setText("");
        dchi_ncc.setText("");
    }

    public void Editable() {
        btn_luu.setEnabled(true);
        ma_ncc.setEditable(false);
        ten_ncc.setEditable(true);
        ten_ndd.setEditable(true);
        sdt_ncc.setEditable(true);
        dchi_ncc.setEditable(true);
    }

    public void UnEditable() {
        btn_luu.setEnabled(false);
        ma_ncc.setEditable(false);
        ten_ncc.setEditable(false);
        ten_ndd.setEditable(false);
        sdt_ncc.setEditable(false);
        dchi_ncc.setEditable(false);
    }

    private String generateNextMaNCC() {
        list = khBll.getALL();
        String prefix = "ncc";
        int maxNumber = 0;
        for (nhaCungCap item : list) {
            String code = item.getMaNCC();
            if (code == null || code.isBlank()) {
                continue;
            }
            int splitIndex = code.length();
            while (splitIndex > 0 && Character.isDigit(code.charAt(splitIndex - 1))) {
                splitIndex--;
            }
            String numberPart = code.substring(splitIndex);
            if (numberPart.isEmpty()) {
                continue;
            }
            if (splitIndex > 0) {
                prefix = code.substring(0, splitIndex);
            }
            try {
                maxNumber = Math.max(maxNumber, Integer.parseInt(numberPart));
            } catch (NumberFormatException ex) {
                // Ignore codes without numeric suffix.
            }
        }
        return prefix + String.format("%03d", maxNumber + 1);
    }

    private boolean validateInput(String ma, String tenncc, String tenndd, String sdt, String diachi) {
        if (ma.isEmpty() || tenncc.isEmpty() || tenndd.isEmpty() || sdt.isEmpty() || diachi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin");
            return false;
        }
        if (!sdt.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ");
            return false;
        }
        return true;
    }

    private void btn_themActionPerformed() {
        ResetFieldText();
        ma_ncc.setText(generateNextMaNCC());
        Editable();
        count = 1;
        btn_them.setEnabled(false);
    }

    private void btn_luuActionPerformed() {
        String ma = ma_ncc.getText().trim();
        String tenncc = ten_ncc.getText().trim();
        String tenndd = ten_ndd.getText().trim();
        String sdt = sdt_ncc.getText().trim();
        String diachi = dchi_ncc.getText().trim();
        if (!validateInput(ma, tenncc, tenndd, sdt, diachi)) {
            return;
        }

        if (count == 1) {
            ncc = new nhaCungCap(ma, tenncc, tenndd, sdt, diachi);
            khBll.add(ncc);
            ResetFieldText();
            UnEditable();
            btn_them.setEnabled(true);
            clearTable();
            getTable();
            return;
        }

        if (count == 2) {
            if (matmp.equals(ma) && tennddtmp.equals(tenndd) && tentmp.equals(tenncc) && sdttmp.equals(sdt) && dchitmp.equals(diachi)) {
                JOptionPane.showMessageDialog(this, "Chưa có thông tin nào được sửa đổi");
                return;
            }
            ncc = new nhaCungCap(ma, tenncc, tenndd, sdt, diachi);
            khBll.update(ncc, matmp);
            ResetFieldText();
            UnEditable();
            btn_sua.setEnabled(true);
            clearTable();
            getTable();
        }
    }

    private void btn_suaActionPerformed() {
        matmp = ma_ncc.getText();
        tentmp = ten_ncc.getText();
        tennddtmp = ten_ndd.getText();
        sdttmp = sdt_ncc.getText();
        dchitmp = dchi_ncc.getText();
        if (matmp.isEmpty() && tentmp.isEmpty() && tennddtmp.isEmpty() && sdttmp.isEmpty() && dchitmp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần sửa");
            return;
        }
        Editable();
        count = 2;
        btn_sua.setEnabled(false);
    }

    private void btn_xoaActionPerformed() {
        String ma = ma_ncc.getText();
        String tenncc = ten_ncc.getText();
        String tenndd = ten_ndd.getText();
        String sdt = sdt_ncc.getText();
        String diachi = dchi_ncc.getText();
        if (ma.isEmpty() && tenncc.isEmpty() && tenndd.isEmpty() && sdt.isEmpty() && diachi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần xóa");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa ?");
        if (confirm == JOptionPane.YES_OPTION) {
            ncc = new nhaCungCap(ma, tenncc, tenndd, sdt, diachi);
            khBll.delete(ncc);
            ResetFieldText();
            clearTable();
            getTable();
        }
    }

    private void btn_resetActionPerformed() {
        ResetFieldText();
        txtSearch.setText("");
        if (cboMaNCCFilter.getItemCount() > 0) {
            cboMaNCCFilter.setSelectedIndex(0);
        }
        if (cboTenNCCFilter.getItemCount() > 0) {
            cboTenNCCFilter.setSelectedIndex(0);
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
