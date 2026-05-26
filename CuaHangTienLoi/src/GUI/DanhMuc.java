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
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BLL.DanhMucBLL;
 
public class DanhMuc extends javax.swing.JPanel {
    DefaultTableModel model;
    DTO.DanhMuc dm = new DTO.DanhMuc();
    private ArrayList<DTO.DanhMuc> list = new ArrayList<>();
    DanhMucBLL dmBll = new DanhMucBLL(this);
    private int count = 0;
    private String matmp, tentmp, imgtmp;
    private File selectFile;
    private String selectedImagePath;
    private JComboBox<String> cboMaLoaiFilter;
    private JComboBox<String> cboTenLoaiFilter;
    private boolean updatingFilters;
    
    public DanhMuc() {
        initComponents();
        btn_luu.setEnabled(false);
        ma_loai.setEditable(false);
        ten_loai.setEditable(false);
        btn_chonanh.setEnabled(false);
        model = new DefaultTableModel(new Object[]{"Mã loại", "Tên loại", "Ảnh minh họa"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table_danhmuc.setModel(model);
        table_danhmuc.setDefaultEditor(Object.class, null);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table_danhmuc.getColumnModel().getColumnCount(); i++) {
            table_danhmuc.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
            table_danhmuc.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int selectRow = table_danhmuc.getSelectedRow();

                    if (selectRow != -1) {
                        Object data1 = table_danhmuc.getValueAt(selectRow, 0);
                        Object data2 = table_danhmuc.getValueAt(selectRow, 1);
                        Object data3 = table_danhmuc.getValueAt(selectRow, 2);


                        ma_loai.setText(data1.toString());
                        ten_loai.setText(data2.toString());
                        String imagePath = data3.toString();
                        selectedImagePath = imagePath;
                        ImageIcon imageIcon = new ImageIcon(imagePath);
                        Image image = imageIcon.getImage().getScaledInstance(image_label.getWidth(), image_label.getHeight(), Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(image);
                        image_label.setIcon(imageIcon);
                    }
                }
            }
        };

        table_danhmuc.addMouseListener(mouseAdapter);
    }

    public void loadTable(ArrayList<DTO.DanhMuc> list) {
        DTO.DanhMuc dm1 = list.get(list.size() - 1);
        model.addRow(new Object[]{
            dm1.getMaloai(), dm1.getTenloai(),dm1.getImg()
        });
    }

    public void clearTable() {
        model.setRowCount(0);
    }

    public void getTable() {
        list = dmBll.getALL();
        try {
            updateFilterCombos();
            renderTable(list);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void renderTable(ArrayList<DTO.DanhMuc> data) {
        model.setRowCount(0);
        for (DTO.DanhMuc row : data) {
            model.addRow(new Object[]{
                row.getMaloai(), row.getTenloai(), row.getImg()
            });
        }
        table_danhmuc.setModel(model);
    }

    private void updateFilterCombos() {
        if (cboMaLoaiFilter == null || cboTenLoaiFilter == null) {
            return;
        }

        updatingFilters = true;
        Object selectedMa = cboMaLoaiFilter.getSelectedItem();
        Object selectedTen = cboTenLoaiFilter.getSelectedItem();

        cboMaLoaiFilter.removeAllItems();
        cboTenLoaiFilter.removeAllItems();
        cboMaLoaiFilter.addItem("Tất cả mã loại");
        cboTenLoaiFilter.addItem("Tất cả tên loại");

        for (DTO.DanhMuc item : list) {
            if (item.getMaloai() != null) {
                cboMaLoaiFilter.addItem(item.getMaloai());
            }
            if (item.getTenloai() != null) {
                cboTenLoaiFilter.addItem(item.getTenloai());
            }
        }

        restoreComboSelection(cboMaLoaiFilter, selectedMa, 0);
        restoreComboSelection(cboTenLoaiFilter, selectedTen, 0);
        updatingFilters = false;
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

    private void applyDanhMucFilters() {
        if (updatingFilters) {
            return;
        }

        String selectedMa = String.valueOf(cboMaLoaiFilter.getSelectedItem());
        String selectedTen = String.valueOf(cboTenLoaiFilter.getSelectedItem());
        String keyword = jTextField1 != null ? jTextField1.getText().trim().toLowerCase() : "";
        boolean filterMa = selectedMa != null && !"Tất cả mã loại".equals(selectedMa);
        boolean filterTen = selectedTen != null && !"Tất cả tên loại".equals(selectedTen);
        boolean filterKeyword = !keyword.isEmpty();

        ArrayList<DTO.DanhMuc> result = new ArrayList<>();
        for (DTO.DanhMuc item : list) {
            boolean matchesMa = !filterMa || selectedMa.equals(item.getMaloai());
            boolean matchesTen = !filterTen || selectedTen.equals(item.getTenloai());
            boolean matchesKeyword = !filterKeyword
                    || (item.getMaloai() != null && item.getMaloai().toLowerCase().contains(keyword))
                    || (item.getTenloai() != null && item.getTenloai().toLowerCase().contains(keyword));
            if (matchesMa && matchesTen && matchesKeyword) {
                result.add(item);
            }
        }

        renderTable(result);
    }

    public void ResetFieldText() {
        ma_loai.setText("");
        ten_loai.setText("");
        image_label.setIcon(null);
        selectFile = null;
        selectedImagePath = null;
    }

    public void UnEditable() {
        btn_luu.setEnabled(false);
        btn_chonanh.setEnabled(false);
        ma_loai.setEditable(false);
        ten_loai.setEditable(false);
    }

    public void Editable() {
        btn_luu.setEnabled(true);
        btn_chonanh.setEnabled(true);
        ma_loai.setEditable(false);
        ten_loai.setEditable(true);
    }

    private boolean validateInput(String maloai, String tenloai, String imagePath) {
        if (maloai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tạo mã loại tự động");
            return false;
        }
        if (!maloai.matches("[A-Za-z0-9_-]+")) {
            JOptionPane.showMessageDialog(this, "Mã loại chỉ được chứa chữ không dấu, số, dấu gạch dưới hoặc gạch ngang");
            ma_loai.requestFocus();
            return false;
        }
        if (tenloai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên loại");
            ten_loai.requestFocus();
            return false;
        }
        if (tenloai.length() < 2) {
            JOptionPane.showMessageDialog(this, "Tên loại phải có ít nhất 2 ký tự");
            ten_loai.requestFocus();
            return false;
        }
        if (imagePath == null || imagePath.isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ảnh minh họa");
            return false;
        }
        return true;
    }

    private String generateNextMaLoai() {
        list = dmBll.getALL();
        String prefix = "DM";
        int maxNumber = 0;

        for (DTO.DanhMuc item : list) {
            String code = item.getMaloai();
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
                // Ignore codes without a numeric suffix.
            }
        }

        return prefix + String.format("%03d", maxNumber + 1);
    }

    private boolean isDuplicateCode(String maloai, String oldCode) {
        for (DTO.DanhMuc tmp : list) {
            if (tmp.getMaloai().equalsIgnoreCase(maloai)
                    && (oldCode == null || !tmp.getMaloai().equalsIgnoreCase(oldCode))) {
                return true;
            }
        }
        return false;
    }

    private boolean isImagePathValid(String imagePath) {
        String lowerPath = imagePath.toLowerCase();
        return lowerPath.endsWith(".jpg")
                || lowerPath.endsWith(".jpeg")
                || lowerPath.endsWith(".png")
                || lowerPath.endsWith(".gif")
                || lowerPath.endsWith(".bmp");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btn_them = new javax.swing.JButton();
        btn_luu = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_danhmuc = new javax.swing.JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        ma_loai = new javax.swing.JTextField();
        ten_loai = new javax.swing.JTextField();
        btn_reset1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btn_chonanh = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        image_label = new javax.swing.JLabel();
        cboMaLoaiFilter = new JComboBox<>();
        cboTenLoaiFilter = new JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1015, 690));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("QUẢN LÝ DANH MỤC");

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Tìm kiếm");
        jButton1.setToolTipText("");
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyDanhMucFilters();
            }
        });

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jTextField1.setFocusable(true);
        jTextField1.setOpaque(true);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyDanhMucFilters();
            }
        });
        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applyDanhMucFilters();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applyDanhMucFilters();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applyDanhMucFilters();
            }
        });

        cboMaLoaiFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyDanhMucFilters();
            }
        });
        cboTenLoaiFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyDanhMucFilters();
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Mã loại:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setText("Thêm ảnh:");

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

        table_danhmuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table_danhmuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        table_danhmuc.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table_danhmuc.setRowSelectionAllowed(true);
        table_danhmuc.setCellSelectionEnabled(false);
        table_danhmuc.setGridColor(new java.awt.Color(255, 255, 255));
        table_danhmuc.setOpaque(false);
        table_danhmuc.setRowHeight(28);
        table_danhmuc.getTableHeader().setResizingAllowed(false);
        table_danhmuc.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(table_danhmuc);

        ma_loai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ma_loai.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        ma_loai.setCaretColor(new java.awt.Color(153, 153, 153));
        ma_loai.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        ma_loai.setDoubleBuffered(true);
        ma_loai.setDragEnabled(true);
        ma_loai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ma_loaiActionPerformed(evt);
            }
        });

        ten_loai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ten_loai.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        btn_reset1.setBackground(new java.awt.Color(102, 102, 102));
        btn_reset1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_reset1.setForeground(new java.awt.Color(255, 255, 255));
        btn_reset1.setText("Reset");
        btn_reset1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_reset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reset1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setText("Tên loại:");

        btn_chonanh.setBackground(new java.awt.Color(204, 204, 204));
        btn_chonanh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_chonanh.setText("Chọn Ảnh");
        btn_chonanh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_chonanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chonanhChooseImage(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Thông tin danh mục: ");

        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel(new BorderLayout(8, 0));
        titlePanel.setBackground(java.awt.Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
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
        JPanel filterPanel = new JPanel(new GridLayout(1, 3, 0, 0));
        filterPanel.setBackground(java.awt.Color.WHITE);
        filterPanel.add(cboMaLoaiFilter);
        filterPanel.add(cboTenLoaiFilter);
        filterPanel.add(new JLabel(""));
        tablePanel.add(filterPanel, BorderLayout.NORTH);
        tablePanel.add(jScrollPane2, BorderLayout.CENTER);
        tablePanel.setMinimumSize(new Dimension(560, 0));

        JPanel formPanel = new JPanel(new BorderLayout(8, 8));
        formPanel.setBackground(java.awt.Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
        formPanel.setMinimumSize(new Dimension(360, 0));

        JPanel actionTop = new JPanel(new GridLayout(1, 3, 8, 0));
        actionTop.setBackground(java.awt.Color.WHITE);
        actionTop.setPreferredSize(new Dimension(0, 38));
        actionTop.add(btn_them);
        actionTop.add(btn_sua);
        actionTop.add(btn_reset1);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(java.awt.Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin danh mục"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0;
        infoPanel.add(jLabel2, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        infoPanel.add(ma_loai, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0;
        infoPanel.add(jLabel7, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        infoPanel.add(ten_loai, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weightx = 0;
        infoPanel.add(jLabel5, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        infoPanel.add(btn_chonanh, gbc);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(java.awt.Color.WHITE);
        imagePanel.setBorder(BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        imagePanel.setPreferredSize(new Dimension(0, 230));
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
        ma_loai.setText(generateNextMaLoai());
        Editable();
        count = 1;
        btn_them.setEnabled(false);
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_luuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_luuActionPerformed
        switch (count) {
            case 1 -> {
                // Thêm
                String maloai = ma_loai.getText().trim();
                String tenloai = ten_loai.getText().trim();
                String img = selectedImagePath;
                if (!validateInput(maloai, tenloai, img)) {
                    return;
                }
                if (isDuplicateCode(maloai, null)) {
                    JOptionPane.showMessageDialog(this, "Mã loại đã tồn tại");
                    ma_loai.requestFocus();
                    return;
                }
                if (!isImagePathValid(img)) {
                    JOptionPane.showMessageDialog(this, "File ảnh không hợp lệ");
                    return;
                }

                try {
                    dm = new DTO.DanhMuc(maloai, tenloai, img);
                    list.add(dm);
                    dmBll.add(dm);
                    ResetFieldText();
                    UnEditable();
                    clearTable();
                    getTable();
                    btn_them.setEnabled(true);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            case 2 -> {
                // Sửa
                String maloai = ma_loai.getText().trim();
                String tenloai = ten_loai.getText().trim();
                String img = selectedImagePath;
                if (!validateInput(maloai, tenloai, img)) {
                    return;
                }
                if (!isImagePathValid(img)) {
                    JOptionPane.showMessageDialog(this, "File ảnh không hợp lệ");
                    return;
                }
                if (matmp.equals(maloai) && tentmp.equals(tenloai) && img.equals(imgtmp)) {
                    JOptionPane.showMessageDialog(this, "Chưa có thông tin nào được sửa đổi");
                    return;
                }

                try {
                    dm = new DTO.DanhMuc(maloai, tenloai, img);
                    dmBll.update(dm);
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
        String maloai = ma_loai.getText();
        matmp = ma_loai.getText();
        String tenloai = ten_loai.getText();
        tentmp = ten_loai.getText();
        imgtmp = selectedImagePath;
        if (maloai.isEmpty() && tenloai.isEmpty() && imgtmp == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần sửa");
        } else {
            Editable();
            count = 2;
            btn_sua.setEnabled(false);
        }
    }//GEN-LAST:event_btn_suaActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        String maloai = ma_loai.getText();
        String tenloai = ten_loai.getText();
        if (maloai.isEmpty() && tenloai.isEmpty() && selectedImagePath == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần xóa");
        } else {
            try {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa ?");

                if (confirm == JOptionPane.YES_OPTION) {
                    dm = new DTO.DanhMuc(maloai, tenloai, selectedImagePath);
                    list.remove(dm);
                    dmBll.delete(dm);
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

    private void ma_loaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ma_loaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ma_loaiActionPerformed

    private void btn_reset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reset1ActionPerformed
        ResetFieldText();
        if (jTextField1 != null) {
            jTextField1.setText("");
        }
        if (cboMaLoaiFilter != null) {
            cboMaLoaiFilter.setSelectedIndex(0);
        }
        if (cboTenLoaiFilter != null) {
            cboTenLoaiFilter.setSelectedIndex(0);
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
    }//GEN-LAST:event_btn_reset1ActionPerformed

    private void btn_chonanhChooseImage(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chonanhChooseImage
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
    }//GEN-LAST:event_btn_chonanhChooseImage


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_chonanh;
    public javax.swing.JButton btn_luu;
    public javax.swing.JButton btn_reset1;
    public javax.swing.JButton btn_sua;
    public javax.swing.JButton btn_them;
    public javax.swing.JButton btn_xoa;
    private javax.swing.JLabel image_label;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    public javax.swing.JTextField ma_loai;
    public javax.swing.JTable table_danhmuc;
    public javax.swing.JTextField ten_loai;
    // End of variables declaration//GEN-END:variables
}
