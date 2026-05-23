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
 
public class DanhMuc extends javax.swing.JPanel {
    DefaultTableModel model;
    DTO.DanhMuc dm = new DTO.DanhMuc();
    private ArrayList<DTO.DanhMuc> list = new ArrayList<>();
    DanhMucBLL dmBll = new DanhMucBLL(this);
    private int count = 0;
    private String matmp, tentmp;
    private Icon icontmp;
    private File selectFile;
    
    public DanhMuc() {
        initComponents();
        btn_luu.setEnabled(false);
        ma_loai.setEditable(false);
        ten_loai.setEditable(false);
        btn_chonanh.setEnabled(false);
        model = (DefaultTableModel) table_danhmuc.getModel();

        model.setColumnIdentifiers(new Object[]{
            "Mã loại", "Tên loại", "Ảnh minh họa"
        });

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
            for (DTO.DanhMuc row : list) {
                model.addRow(new Object[]{
                    row.getMaloai(), row.getTenloai(), row.getImg()
                });
            }
            table_danhmuc.setModel(model);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void ResetFieldText() {
        ma_loai.setText("");
        ten_loai.setText("");
        image_label.setIcon(null);
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
        ma_loai.setEditable(true);
        ten_loai.setEditable(true);
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
        table_danhmuc = new javax.swing.JTable();
        ma_loai = new javax.swing.JTextField();
        ten_loai = new javax.swing.JTextField();
        btn_reset1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btn_chonanh = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        image_label = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Sitka Small", 1, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ DANH MỤC");

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Tìm kiếm");
        jButton1.setToolTipText("");
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jTextField1.setFocusable(false);
        jTextField1.setOpaque(true);

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
        table_danhmuc.setGridColor(new java.awt.Color(255, 255, 255));
        table_danhmuc.setOpaque(false);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 67, Short.MAX_VALUE)
                                .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_reset1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ma_loai))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(btn_chonanh, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addComponent(ten_loai))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_luu, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(image_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_reset1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(ma_loai, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ten_loai, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(btn_chonanh))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(btn_luu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(image_label, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        Editable();
        ResetFieldText();
        count = 1;
        btn_them.setEnabled(false);
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_luuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_luuActionPerformed
        switch (count) {
            case 1 -> {
                // Thêm
                String maloai = ma_loai.getText().trim();
                String tenloai = ten_loai.getText().trim();
                String img;
                Icon icon = image_label.getIcon();
                for (DTO.DanhMuc tmp : list) {
                    if (tmp.getMaloai().equals(maloai)) {
                        JOptionPane.showMessageDialog(this, "Mã loại đã tồn tại");
                        return;
                    }
                }

                if (icon == null || maloai.isEmpty() || tenloai.isEmpty() ) {
                    JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin");
                    return;
                }

                if (icon instanceof ImageIcon) {
                    img = selectFile.getAbsolutePath();
                } else {
                    JOptionPane.showMessageDialog(this, "Đây không phải là ảnh");
                    return;
                }

                try {
                    dm = new DTO.DanhMuc(maloai, tenloai, img);
                    list.add(dm);
                    dmBll.add(dm);
                    ResetFieldText();
                    UnEditable();
                    loadTable(list);
                    btn_them.setEnabled(true);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            case 2 -> {
                // Sửa
                boolean found = false;
                String img;
                String maloai = ma_loai.getText().trim();
                String tenloai = ten_loai.getText().trim();
                Icon icon = image_label.getIcon();
                if (!matmp.equals(maloai)) {
                    for (DTO.DanhMuc tmp : list) {
                        if (tmp.getMaloai().equals(maloai)) {
                            found = true;
                            break;
                        }
                    }
                }
                if (found) {
                    JOptionPane.showMessageDialog(this, "Mã loại đã tồn tại");
                    return;
                }

                if (matmp.equals(maloai) && tentmp.equals(tenloai)  && icontmp.equals(icon)) {
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
                
                if (icon instanceof ImageIcon) {
                    img = selectFile.getAbsolutePath();
                } else {
                    JOptionPane.showMessageDialog(this, "Đây không phải là ảnh");
                    return;
                }

                try {
                    dm = new DTO.DanhMuc(maloai, tenloai, img);
                    dmBll.update(dm, matmp);
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
        icontmp = image_label.getIcon();
        if (maloai.isEmpty() && tenloai.isEmpty()&& icontmp==null) {
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
        Icon icon = image_label.getIcon();
        if (maloai.isEmpty() && tenloai.isEmpty()&& icon==null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần xóa");
        } else {
            try {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa ?");

                if (confirm == JOptionPane.YES_OPTION) {
                    Image image = ((ImageIcon) icon).getImage();
                    String imagePath = image.toString();
                    dm = new DTO.DanhMuc(maloai, tenloai,imagePath);
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
