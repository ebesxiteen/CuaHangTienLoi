/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BLL.DanhMucBLL;
import BLL.SanPhamBLL;
import DTO.DanhMuc;
import DTO.sanPham;

public class SanPhamView extends javax.swing.JPanel {

    DefaultTableModel model;
    private sanPham sp;
    private ArrayList<sanPham> list = new ArrayList<>();
    private ArrayList<DanhMuc> listDM = new ArrayList<>();
    SanPhamBLL spBLL = new SanPhamBLL(this);
    DanhMucBLL dmBLL = new DanhMucBLL();
    private int count = -1;
    private int soluong;
    private long dongia;

    private String matmp, tentmp, soluongtmp, dongiatmp, imgtmp;
    private File selectFile;
    private Icon icontmp;
    private String selectedImagePath;
    private JComboBox<String> cboMaSPFilter;
    private JComboBox<String> cboTenSPFilter;
    private JComboBox<String> cboMaLoaiFilter;
    private boolean updatingFilters;


    public SanPhamView() {
        initComponents();

        btn_luu.setEnabled(false);

        ma_sp.setEditable(false);
        ten_sp.setEditable(false);
        maloai_sp.setEnabled(false);
        soluong_sp.setEditable(false);
        dongia_sp.setEditable(false);
        btn_chonanh.setEnabled(false);

        model = new DefaultTableModel(new Object[]{
            "Mã sản phẩm", "Tên sản phẩm", "Mã loại", "Số lượng", "Đơn giá", "Ảnh minh họa"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table_sp.setModel(model);
        table_sp.setDefaultEditor(Object.class, null);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table_sp.getColumnModel().getColumnCount(); i++) {
            table_sp.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
            table_sp.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int selectRow = table_sp.getSelectedRow();

                    if (selectRow != -1) {
                        Object data1 = table_sp.getValueAt(selectRow, 0);
                        Object data2 = table_sp.getValueAt(selectRow, 1);
                        Object data3 = table_sp.getValueAt(selectRow, 2);
                        Object data4 = table_sp.getValueAt(selectRow, 3);
                        Object data5 = table_sp.getValueAt(selectRow, 4);
                        Object data6 = table_sp.getValueAt(selectRow, 5);

                        ma_sp.setText(data1.toString());
                        ten_sp.setText(data2.toString());
                        maloai_sp.setSelectedItem(data3.toString());
                        soluong_sp.setText(data4.toString());
                        dongia_sp.setText(data5.toString());
                        String imagePath = data6.toString();
                        selectedImagePath = imagePath;
                        ImageIcon imageIcon = new ImageIcon(imagePath);
                        Image image = imageIcon.getImage().getScaledInstance(image_label.getWidth(), image_label.getHeight(), Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(image);
                        image_label.setIcon(imageIcon);
                    }
                }
            }
        };
        table_sp.addMouseListener(mouseAdapter);
    }

    public void loadTable(ArrayList<sanPham> list) {
        sanPham sp1 = list.get(list.size() - 1);
        model.addRow(new Object[]{
            sp1.getMasp(), sp1.getTensp(), sp1.getMaloaisp(), sp1.getSoluong(), sp1.getDongia(), sp1.getImg()
        });
    }

    public void clearTable() {
        model.setRowCount(0);
    }

    public void getTable() {
        list = spBLL.getALL();
        try {
            updateFilterCombos();
            renderTable(list);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void renderTable(ArrayList<sanPham> data) {
        model.setRowCount(0);
        for (sanPham row : data) {
            model.addRow(new Object[]{
                row.getMasp(), row.getTensp(), row.getMaloaisp(), row.getSoluong(), row.getDongia(), row.getImg()
            });
        }
        table_sp.setModel(model);
    }

    private void updateFilterCombos() {
        if (cboMaSPFilter == null || cboTenSPFilter == null || cboMaLoaiFilter == null) {
            return;
        }
        updatingFilters = true;
        Object selectedMaSP = cboMaSPFilter.getSelectedItem();
        Object selectedTenSP = cboTenSPFilter.getSelectedItem();
        Object selectedMaLoai = cboMaLoaiFilter.getSelectedItem();

        cboMaSPFilter.removeAllItems();
        cboTenSPFilter.removeAllItems();
        cboMaLoaiFilter.removeAllItems();
        cboMaSPFilter.addItem("Tất cả mã SP");
        cboTenSPFilter.addItem("Tất cả tên SP");
        cboMaLoaiFilter.addItem("Tất cả mã loại");

        for (sanPham item : list) {
            addUniqueComboItem(cboMaSPFilter, item.getMasp());
            addUniqueComboItem(cboTenSPFilter, item.getTensp());
            addUniqueComboItem(cboMaLoaiFilter, item.getMaloaisp());
        }

        restoreComboSelection(cboMaSPFilter, selectedMaSP, 0);
        restoreComboSelection(cboTenSPFilter, selectedTenSP, 0);
        restoreComboSelection(cboMaLoaiFilter, selectedMaLoai, 0);
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
        String selectedMaSP = String.valueOf(cboMaSPFilter.getSelectedItem());
        String selectedTenSP = String.valueOf(cboTenSPFilter.getSelectedItem());
        String selectedMaLoai = String.valueOf(cboMaLoaiFilter.getSelectedItem());
        String keyword = jTextField1.getText().trim().toLowerCase();

        boolean filterMaSP = selectedMaSP != null && !"Tất cả mã SP".equals(selectedMaSP);
        boolean filterTenSP = selectedTenSP != null && !"Tất cả tên SP".equals(selectedTenSP);
        boolean filterMaLoai = selectedMaLoai != null && !"Tất cả mã loại".equals(selectedMaLoai);
        boolean filterKeyword = !keyword.isEmpty();

        ArrayList<sanPham> result = new ArrayList<>();
        for (sanPham item : list) {
            boolean matchesMaSP = !filterMaSP || selectedMaSP.equals(item.getMasp());
            boolean matchesTenSP = !filterTenSP || selectedTenSP.equals(item.getTensp());
            boolean matchesMaLoai = !filterMaLoai || selectedMaLoai.equals(item.getMaloaisp());
            boolean matchesKeyword = !filterKeyword
                    || containsKeyword(item.getMasp(), keyword)
                    || containsKeyword(item.getTensp(), keyword)
                    || containsKeyword(item.getMaloaisp(), keyword);
            if (matchesMaSP && matchesTenSP && matchesMaLoai && matchesKeyword) {
                result.add(item);
            }
        }
        renderTable(result);
    }

    private boolean containsKeyword(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    public void ResetFieldText() {
        soluong_sp.setText("");
        ma_sp.setText("");
        ten_sp.setText("");
        if (maloai_sp.getItemCount() > 0) {
            maloai_sp.setSelectedIndex(0);
        }
        soluong_sp.setText("");
        dongia_sp.setText("");
        image_label.setIcon(null);
        selectFile = null;
        selectedImagePath = null;
    }

    public void UnEditable() {
        maloai_sp.setEnabled(false);
        btn_luu.setEnabled(false);
        btn_chonanh.setEnabled(false);
        ma_sp.setEditable(false);
        ten_sp.setEditable(false);
        dongia_sp.setEditable(false);
        soluong_sp.setEditable(false);
    }

    public void Editable() {
        soluong_sp.setEditable(true);
        btn_luu.setEnabled(true);
        maloai_sp.setEnabled(true);
        btn_chonanh.setEnabled(true);
        ma_sp.setEditable(false);
        ten_sp.setEditable(true);
        dongia_sp.setEditable(true);
    }

    public void comboBox() {
        listDM = dmBLL.getALL();
        maloai_sp.removeAllItems();
        for (DanhMuc dataDM : listDM) {
            maloai_sp.addItem(dataDM.getMaloai());
        }
    }

    private boolean isImagePathValid(String imagePath) {
        if (imagePath == null || imagePath.isBlank()) {
            return false;
        }
        String lowerPath = imagePath.toLowerCase();
        return lowerPath.endsWith(".jpg")
                || lowerPath.endsWith(".jpeg")
                || lowerPath.endsWith(".png")
                || lowerPath.endsWith(".gif")
                || lowerPath.endsWith(".bmp");
    }

    private String generateNextMaSP() {
        list = spBLL.getALL();
        String prefix = "SP";
        int maxNumber = 0;

        for (sanPham item : list) {
            String code = item.getMasp();
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

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, JLabel label, java.awt.Component input) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(label, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(input, gbc);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_sp = new javax.swing.JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        image_label = new javax.swing.JLabel();
        btn_chonanh = new javax.swing.JButton();
        ma_sp = new javax.swing.JTextField();
        ten_sp = new javax.swing.JTextField();
        soluong_sp = new javax.swing.JTextField();
        dongia_sp = new javax.swing.JTextField();
        btn_them = new javax.swing.JButton();
        btn_luu = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        maloai_sp = new javax.swing.JComboBox<>();
        btn_reset = new javax.swing.JButton();
        cboMaSPFilter = new JComboBox<>();
        cboTenSPFilter = new JComboBox<>();
        cboMaLoaiFilter = new JComboBox<>();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1015, 690));

        table_sp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table_sp.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table_sp.setRowSelectionAllowed(true);
        table_sp.setCellSelectionEnabled(false);
        table_sp.setRowHeight(28);
        table_sp.getTableHeader().setResizingAllowed(false);
        table_sp.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table_sp);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quản Lý Sản Phẩm");

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Tìm kiếm");
        jButton1.setToolTipText("");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setText("Thông tin sản phẩm:");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
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
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyFilters();
            }
        });
        cboMaSPFilter.addActionListener(e -> applyFilters());
        cboTenSPFilter.addActionListener(e -> applyFilters());
        cboMaLoaiFilter.addActionListener(e -> applyFilters());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Mã sản phẩm:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setText("Tên sản phẩm:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setText("Mã loại sản phẩm:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setText("Số lượng:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setText("Đơn giá:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("Hình ảnh:");

        btn_chonanh.setBackground(new java.awt.Color(204, 204, 204));
        btn_chonanh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_chonanh.setText("Chọn Ảnh");
        btn_chonanh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_chonanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseImage(evt);
            }
        });

        ma_sp.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        ten_sp.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        soluong_sp.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        dongia_sp.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        btn_them.setBackground(new java.awt.Color(102, 102, 102));
        btn_them.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_them.setForeground(new java.awt.Color(255, 255, 255));
        btn_them.setText("Thêm");
        btn_them.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        btn_luu.setBackground(new java.awt.Color(0, 102, 0));
        btn_luu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_luu.setForeground(new java.awt.Color(255, 255, 255));
        btn_luu.setText("Lưu");
        btn_luu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_luu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_luuActionPerformed(evt);
            }
        });

        btn_sua.setBackground(new java.awt.Color(102, 102, 102));
        btn_sua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_sua.setForeground(new java.awt.Color(255, 255, 255));
        btn_sua.setText("Sửa");
        btn_sua.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaActionPerformed(evt);
            }
        });

        btn_xoa.setBackground(new java.awt.Color(153, 0, 0));
        btn_xoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_xoa.setForeground(new java.awt.Color(255, 255, 255));
        btn_xoa.setText("Xóa");
        btn_xoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });

        btn_reset.setBackground(new java.awt.Color(102, 102, 102));
        btn_reset.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_reset.setForeground(new java.awt.Color(255, 255, 255));
        btn_reset.setText("Reset");
        btn_reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel(new BorderLayout(8, 0));
        titlePanel.setBackground(java.awt.Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        titlePanel.add(jLabel1, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new BorderLayout(6, 0));
        searchPanel.setBackground(java.awt.Color.WHITE);
        jTextField1.setPreferredSize(new Dimension(240, 28));
        jButton1.setPreferredSize(new Dimension(100, 28));
        searchPanel.add(jTextField1, BorderLayout.CENTER);
        searchPanel.add(jButton1, BorderLayout.EAST);
        titlePanel.add(searchPanel, BorderLayout.EAST);
        add(titlePanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout(8, 8));
        tablePanel.setBackground(java.awt.Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        JPanel filterPanel = new JPanel(new GridLayout(1, 6, 0, 0));
        filterPanel.setBackground(java.awt.Color.WHITE);
        filterPanel.add(cboMaSPFilter);
        filterPanel.add(cboTenSPFilter);
        filterPanel.add(cboMaLoaiFilter);
        filterPanel.add(new JLabel(""));
        filterPanel.add(new JLabel(""));
        filterPanel.add(new JLabel(""));
        tablePanel.add(filterPanel, BorderLayout.NORTH);
        tablePanel.add(jScrollPane1, BorderLayout.CENTER);
        tablePanel.setMinimumSize(new Dimension(590, 0));

        JPanel formPanel = new JPanel(new BorderLayout(8, 8));
        formPanel.setBackground(java.awt.Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
        formPanel.setMinimumSize(new Dimension(380, 0));

        JPanel actionTop = new JPanel(new GridLayout(1, 3, 8, 0));
        actionTop.setBackground(java.awt.Color.WHITE);
        actionTop.setPreferredSize(new Dimension(0, 38));
        actionTop.add(btn_them);
        actionTop.add(btn_sua);
        actionTop.add(btn_reset);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(java.awt.Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 6, 5, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormRow(infoPanel, gbc, 0, jLabel2, ma_sp);
        addFormRow(infoPanel, gbc, 1, jLabel4, ten_sp);
        addFormRow(infoPanel, gbc, 2, jLabel5, maloai_sp);
        addFormRow(infoPanel, gbc, 3, jLabel6, soluong_sp);
        addFormRow(infoPanel, gbc, 4, jLabel7, dongia_sp);
        addFormRow(infoPanel, gbc, 5, jLabel8, btn_chonanh);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(java.awt.Color.WHITE);
        imagePanel.setBorder(BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        imagePanel.setPreferredSize(new Dimension(0, 220));
        imagePanel.add(image_label, BorderLayout.CENTER);

        JPanel formCenter = new JPanel(new BorderLayout(8, 8));
        formCenter.setBackground(java.awt.Color.WHITE);
        formCenter.add(infoPanel, BorderLayout.NORTH);
        formCenter.add(imagePanel, BorderLayout.CENTER);

        JPanel actionBottom = new JPanel(new GridLayout(1, 2, 8, 0));
        actionBottom.setBackground(java.awt.Color.WHITE);
        actionBottom.setPreferredSize(new Dimension(0, 38));
        actionBottom.add(btn_xoa);
        actionBottom.add(btn_luu);

        formPanel.add(actionTop, BorderLayout.NORTH);
        formPanel.add(formCenter, BorderLayout.CENTER);
        formPanel.add(actionBottom, BorderLayout.SOUTH);

        javax.swing.JSplitPane splitPane = new javax.swing.JSplitPane(javax.swing.JSplitPane.HORIZONTAL_SPLIT, tablePanel, formPanel);
        splitPane.setResizeWeight(0.62);
        splitPane.setDividerLocation(620);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        ResetFieldText();
        ma_sp.setText(generateNextMaSP());
        Editable();
        btn_them.setEnabled(false);
        count = 1;
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_luuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_luuActionPerformed
        switch (count) {
            case 1 -> {
                // Thêm
                String ma = ma_sp.getText().trim();
                String ten = ten_sp.getText().trim();
                String maloai = (String) maloai_sp.getSelectedItem();
                String soluongTxt = soluong_sp.getText().trim();
                String dongiaTxt = dongia_sp.getText().trim();
                String img = selectedImagePath;

                if (img == null || ma.isEmpty() || ten.isEmpty() || soluongTxt.isEmpty() || dongiaTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin");
                    return;
                }

                if (!soluongTxt.isEmpty()) {
                    try {
                        soluong = Integer.parseInt(soluongTxt);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ");
                        return;
                    }
                }

                if (!dongiaTxt.isEmpty()) {
                    try {
                        dongia = Long.parseLong(dongiaTxt);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Đơn giá không hợp lệ");
                        return;
                    }
                }
                if (!isImagePathValid(img)) {
                    JOptionPane.showMessageDialog(this, "File ảnh không hợp lệ");
                    return;
                }

                try {
                    sp = new sanPham(ma, ten, maloai, soluong, dongia, img);
                    list.add(sp);
                    spBLL.add(sp);
                    btn_them.setEnabled(true);
                    UnEditable();
                    ResetFieldText();
                    clearTable();
                    getTable();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            case 2 -> {
                // Sửa
                String ma = ma_sp.getText().trim();
                String ten = ten_sp.getText().trim();
                String maloai = (String) maloai_sp.getSelectedItem();
                String soluongTxt = soluong_sp.getText().trim();
                String dongiaTxt = dongia_sp.getText().trim();
                String img = selectedImagePath;
                
                if (matmp.equals(ma) && tentmp.equals(ten) && soluongtmp.equals(soluongTxt) && dongiatmp.equals(dongiaTxt) && img.equals(imgtmp)) {
                    int confirm = JOptionPane.showConfirmDialog(this, "Chưa có thông tin nào được sửa đổi, bạn có muốn tiếp tục ?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        return;
                    } else {
                        ResetFieldText();
                        UnEditable();
                        btn_sua.setEnabled(true);
                        return;
                    }
                }

                if (!soluongTxt.isEmpty()) {
                    try {
                        soluong = Integer.parseInt(soluongTxt);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ");
                        return;
                    }
                }

                if (!dongiaTxt.isEmpty()) {
                    try {
                        dongia = Long.parseLong(dongiaTxt);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Đơn giá không hợp lệ");
                        return;
                    }
                }
                if (!isImagePathValid(img)) {
                    JOptionPane.showMessageDialog(this, "File ảnh không hợp lệ");
                    return;
                }

                try {
                    sp = new sanPham(ma, ten, maloai, soluong, dongia, img);
                    spBLL.update(sp, matmp);
                    ResetFieldText();
                    UnEditable();
                    clearTable();
                    getTable();
                    btn_sua.setEnabled(true);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
    }//GEN-LAST:event_btn_luuActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        String ma = ma_sp.getText();
        String ten = ten_sp.getText();
        String dongiaTxt = dongia_sp.getText();
        String soluongTxt = soluong_sp.getText();
        matmp = ma_sp.getText();
        tentmp = ten_sp.getText();
        dongiatmp = dongia_sp.getText();
        soluongtmp = soluong_sp.getText();
        icontmp = image_label.getIcon();
        imgtmp = selectedImagePath;
        if (ma.isEmpty() && ten.isEmpty() && dongiaTxt.isEmpty() && icontmp == null && soluongTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần sửa");
        } else {
            Editable();
            count = 2;
            btn_sua.setEnabled(false);
        }
    }//GEN-LAST:event_btn_suaActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        String ma = ma_sp.getText();
        String ten = ten_sp.getText();
        String dongiaTxt = dongia_sp.getText();
        String maloaisp = (String) maloai_sp.getSelectedItem();
        String soluongTxt = soluong_sp.getText();
        if (ma.isEmpty() && ten.isEmpty() && dongiaTxt.isEmpty() && selectedImagePath == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần xóa");
        } else {
            try {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa ?");

                if (confirm == JOptionPane.YES_OPTION) {
                    int soluong = Integer.parseInt(soluongTxt);
                    long dongiasp = Long.parseLong(dongiaTxt);
                    sp = new sanPham(ma, ten, maloaisp, soluong, dongiasp, selectedImagePath);
                    list.remove(sp);
                    spBLL.delete(sp);
                    ResetFieldText();
                    clearTable();
                    getTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Hủy xóa");
                    ResetFieldText();
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void ChooseImage(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChooseImage
        try {
            JFileChooser fchooser = new JFileChooser("D:\\CuaHangTienLoi\\src\\img");
            fchooser.setDialogTitle("Mở file");
            int result = fchooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectFile = fchooser.getSelectedFile();
                String imagePath = selectFile.getAbsolutePath();
                if (!isImagePathValid(imagePath)) {
                    JOptionPane.showMessageDialog(this, "File ảnh không hợp lệ");
                    return;
                }
                selectedImagePath = imagePath;
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage().getScaledInstance(image_label.getWidth(), image_label.getHeight(), Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(image);
                image_label.setIcon(imageIcon);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_ChooseImage

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        ResetFieldText();
        jTextField1.setText("");
        if (cboMaSPFilter != null) {
            cboMaSPFilter.setSelectedIndex(0);
        }
        if (cboTenSPFilter != null) {
            cboTenSPFilter.setSelectedIndex(0);
        }
        if (cboMaLoaiFilter != null) {
            cboMaLoaiFilter.setSelectedIndex(0);
        }
        UnEditable();
        if (btn_luu.isEnabled()) {
            btn_luu.setEnabled(false);
        }
        if (!btn_them.isEnabled()) {
            btn_them.setEnabled(true);
        }
        if (!btn_sua.isEnabled()) {
            btn_sua.setEnabled(true);
        }
        clearTable();
        getTable();
    }//GEN-LAST:event_btn_resetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_chonanh;
    public javax.swing.JButton btn_luu;
    public javax.swing.JButton btn_reset;
    public javax.swing.JButton btn_sua;
    public javax.swing.JButton btn_them;
    public javax.swing.JButton btn_xoa;
    public javax.swing.JTextField dongia_sp;
    private javax.swing.JLabel image_label;
    private javax.swing.JButton jButton1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    public javax.swing.JTextField ma_sp;
    public javax.swing.JComboBox<String> maloai_sp;
    public javax.swing.JTextField soluong_sp;
    public javax.swing.JTable table_sp;
    public javax.swing.JTextField ten_sp;
    // End of variables declaration//GEN-END:variables
}
