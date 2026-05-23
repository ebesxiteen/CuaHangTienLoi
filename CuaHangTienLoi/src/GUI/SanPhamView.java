/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
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

    private String matmp, tentmp, soluongtmp, dongiatmp;
    private File selectFile;
    private Icon icontmp;


    public SanPhamView() {
        initComponents();

        btn_luu.setEnabled(false);

        ma_sp.setEditable(false);
        ten_sp.setEditable(false);
        maloai_sp.setEnabled(false);
        soluong_sp.setEditable(false);
        dongia_sp.setEditable(false);
        btn_chonanh.setEnabled(false);

        model = (DefaultTableModel) table_sp.getModel();
        model.setColumnIdentifiers(new Object[]{
            "Mã sản phẩm", "Tên sản phẩm", "Mã loại", "Số lượng", "Đơn giá", "Ảnh minh họa"
        });

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
            for (sanPham row : list) {
                model.addRow(new Object[]{
                    row.getMasp(), row.getTensp(), row.getMaloaisp(), row.getSoluong(), row.getDongia(), row.getImg()
                });
            }
            table_sp.setModel(model);
        } catch (Exception ex) {
            System.out.println(ex);
        }
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
        ma_sp.setEditable(true);
        ten_sp.setEditable(true);
        dongia_sp.setEditable(true);
    }

    public void comboBox() {
        listDM = dmBLL.getALL();
        for (DanhMuc dataDM : listDM) {
            maloai_sp.addItem(dataDM.getMaloai());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_sp = new javax.swing.JTable();
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
        setPreferredSize(new java.awt.Dimension(980, 650));

        table_sp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(soluong_sp)
                                    .addComponent(dongia_sp)
                                    .addComponent(ten_sp)
                                    .addComponent(ma_sp)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(maloai_sp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(186, 206, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_luu, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_xoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_reset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_sua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btn_chonanh, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(image_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(ma_sp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(ten_sp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(maloai_sp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(soluong_sp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(dongia_sp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(btn_chonanh))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btn_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_luu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(image_label, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1))
                .addContainerGap(47, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        Editable();
        btn_them.setEnabled(false);
        count = 1;
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_luuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_luuActionPerformed
        switch (count) {
            case 1 -> {
                // Thêm
                String img;
                String ma = ma_sp.getText().trim();
                String ten = ten_sp.getText().trim();
                String maloai = (String) maloai_sp.getSelectedItem();
                String soluongTxt = soluong_sp.getText().trim();
                String dongiaTxt = dongia_sp.getText().trim();
                Icon icon = image_label.getIcon();

                if (icon == null || ma.isEmpty() || ten.isEmpty() || dongiaTxt.isEmpty()) {
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
                
                if (icon instanceof ImageIcon) {
                    img = selectFile.getAbsolutePath();
                } else {
                    JOptionPane.showMessageDialog(this, "Đây không phải là ảnh");
                    return;
                }

                try {
                    sp = new sanPham(ma, ten, maloai, soluong, dongia, img);
                    list.add(sp);
                    spBLL.add(sp);
                    btn_them.setEnabled(true);
                    UnEditable();
                    ResetFieldText();
                    loadTable(list);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            case 2 -> {
                // Sửa
                boolean found = false;
                String img;
                String ma = ma_sp.getText().trim();
                String ten = ten_sp.getText().trim();
                String maloai = (String) maloai_sp.getSelectedItem();
                String soluongTxt = soluong_sp.getText().trim();
                String dongiaTxt = dongia_sp.getText().trim();
                Icon icon = image_label.getIcon();
                
                if (matmp.equals(ma) && tentmp.equals(ten) && soluongtmp.equals(soluongTxt) && dongiatmp.equals(dongiaTxt) && icontmp.equals(icon)) {
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

                if (!matmp.equals(ma)) {
                    for (sanPham tmp : list) {
                        if (tmp.getMasp().equals(ma)) {
                            found = true;
                            break;
                        }
                    }
                }
                if (found) {
                    JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại");
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
                
                if (icon instanceof ImageIcon) {
                    img = selectFile.getAbsolutePath();
                } else {
                    JOptionPane.showMessageDialog(this, "Đây không phải là ảnh");
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
        Icon icon = image_label.getIcon();
        if (ma.isEmpty() && ten.isEmpty() && dongiaTxt.isEmpty() && icon == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần xóa");
        } else {
            try {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa ?");

                if (confirm == JOptionPane.YES_OPTION) {
                    Image image = ((ImageIcon) icon).getImage();
                    String imagePath = image.toString();
                    int soluong = Integer.parseInt(soluongTxt);
                    long dongiasp = Long.parseLong(dongiaTxt);
                    sp = new sanPham(ma, ten, maloaisp, soluong, dongiasp, imagePath);
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
