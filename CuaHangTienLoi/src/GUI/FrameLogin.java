
package GUI;

import javax.swing.JOptionPane;

import BLL.LoginoutBLL;

public class FrameLogin extends javax.swing.JFrame {
    LoginoutBLL logbll = new LoginoutBLL(this);
  
    public FrameLogin() {
        initComponents();
        
    }
    
    public void FrameLogin(){
       username_dn.grabFocus();
   }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        xemPass = new javax.swing.JCheckBox();
        btn_dn = new javax.swing.JButton();
        pass_dn = new javax.swing.JPasswordField();
        username_dn = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cửa Hàng 284");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(117, 253, 206));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Đăng Nhập");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Tên đăng nhập:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Mật khẩu:");

        xemPass.setBackground(new java.awt.Color(255, 255, 255));
        xemPass.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xemPass.setText("Xem mật khẩu");
        xemPass.setBorder(null);
        xemPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xemPassActionPerformed(evt);
            }
        });

        btn_dn.setBackground(new java.awt.Color(153, 255, 153));
        btn_dn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_dn.setText("Đăng Nhập");
        btn_dn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dnActionPerformed(evt);
            }
        });

        pass_dn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pass_dn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        username_dn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        username_dn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addGap(55, 55, 55))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_dn, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(xemPass, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pass_dn)
                    .addComponent(username_dn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(27, 27, 27)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(username_dn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pass_dn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(xemPass, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_dn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, -1, 410));

        java.net.URL bgUrl = getClass().getResource("/img/bgDKDN.jpg");
        if (bgUrl != null) {
            jLabel1.setIcon(new javax.swing.ImageIcon(bgUrl)); // NOI18N
        } else {
            // fallback: no image available
            jLabel1.setText("");
        }
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void xemPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xemPassActionPerformed
        if(xemPass.isSelected()) {
            pass_dn.setEchoChar((char)0);
        } else {
            pass_dn.setEchoChar('\u25cf');
        }
    }//GEN-LAST:event_xemPassActionPerformed

    private void btn_dnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dnActionPerformed
        String username = username_dn.getText().trim();
            String password = pass_dn.getText().trim();
            if(username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin");
            }
            else {
                int status = logbll.CheckStatusBLL(username, password);
                switch (status) {
                    case 0 -> {
                        TrangChu tc = new TrangChu(username);
                        username_dn.setText("");
                        pass_dn.setText("");
                        this.dispose();
                        tc.setVisible(true);
                    }
                    case 1 -> {
                        TrangChu tc = new TrangChu(username);
                        username_dn.setText("");
                        pass_dn.setText("");
                        this.dispose();
                        tc.setVisible(true);
                    }
                    default -> JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không chính xác !!!");
                }
            }
    }//GEN-LAST:event_btn_dnActionPerformed

//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FrameDN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FrameDN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FrameDN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FrameDN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new FrameDN().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_dn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JPasswordField pass_dn;
    public javax.swing.JTextField username_dn;
    private javax.swing.JCheckBox xemPass;
    // End of variables declaration//GEN-END:variables
}
