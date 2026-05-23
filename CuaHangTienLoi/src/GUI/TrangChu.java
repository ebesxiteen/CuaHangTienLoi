package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JFrame;

public class TrangChu extends JFrame{
    CardLayout cardlayout;
    private boolean isMenuVisible;
    NhanVienView nvView = new NhanVienView();
    SanPhamView spView = new SanPhamView();
    KhachHangView khView = new KhachHangView();
    NhaCungCap nccView = new NhaCungCap();
    DanhMuc dmView = new DanhMuc(); 
    phieuNhap pnView = new phieuNhap();
    HoaDon hdView = new HoaDon();
    CTHoaDon cthdView = new CTHoaDon();
    TaiKhoan tkView = new TaiKhoan();
    
    public TrangChu(String username) {
        initComponents();
    }
    
    public void ClearTableALL() {
        nvView.clearTable();
        spView.clearTable();
        khView.clearTable();
        nccView.clearTable();
        dmView.clearTable();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        MenuPanel = new javax.swing.JPanel();
        Pane_cover = new javax.swing.JPanel();
        Pane_content = new javax.swing.JPanel();
        pHoaDon = new javax.swing.JPanel();
        lHoaDon = new javax.swing.JLabel();
        pChiTietHD = new javax.swing.JPanel();
        lChiTietHD = new javax.swing.JLabel();
        pDanhMuc = new javax.swing.JPanel();
        lDanhMuc = new javax.swing.JLabel();
        pSanPham = new javax.swing.JPanel();
        lSanPham = new javax.swing.JLabel();
        pDoanhThu = new javax.swing.JPanel();
        lDoanhThu = new javax.swing.JLabel();
        pNhanVien = new javax.swing.JPanel();
        lNhanVien = new javax.swing.JLabel();
        pQuanLy = new javax.swing.JPanel();
        lQuanLy = new javax.swing.JLabel();
        pNhaCC = new javax.swing.JPanel();
        lNhaCC = new javax.swing.JLabel();
        pDangXuat = new javax.swing.JPanel();
        lDangXuat = new javax.swing.JLabel();
        pTaiKhoan = new javax.swing.JPanel();
        lTaiKhoan = new javax.swing.JLabel();
        
        
        cardlayout = new CardLayout();
        Pane_content.setLayout(cardlayout);
        
        Pane_content.add(nvView,"QL Nhân Viên");
        Pane_content.add(spView,"QL Sản Phẩm");
        Pane_content.add(khView,"QL Khách Hàng");
        Pane_content.add(pnView,"Phiếu Nhập");
        Pane_content.add(nccView,"Nhà Cung Cấp");
        Pane_content.add(dmView,"Danh Mục");
        // Add invoice views
        Pane_content.add(hdView, "Hóa Đơn");
        Pane_content.add(cthdView, "Chi Tiết HĐ");
        Pane_content.add(tkView, "Tài Khoản");
       
        isMenuVisible = false;
        Pane_content.setVisible(isMenuVisible);
        
        
        
        
   
        
        this.setSize(1240,690);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        
        
        MenuPanel.setLayout(null);

        MenuPanel.setBackground(new java.awt.Color(29, 117, 109));

        
        Pane_cover.setSize(1240,690);
        Pane_cover.setBackground(Color.white);
        Pane_content.setBackground(Color.white);
        MenuPanel.setBounds(0, 0, 200, 690);
        Pane_content.setBounds(205,0,1015,690);
        
        Pane_cover.setLayout(null);
        this.setLayout(null);
        
        pHoaDon.setLayout(null);
        pHoaDon.setBackground(new java.awt.Color(29, 117, 109));
        pHoaDon.setBounds(0, 75, 200, 50);
        pHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Hiển thị panel Hóa Đơn
                ClearTableALL();
                isMenuVisible = true;
                Pane_content.setVisible(isMenuVisible);
                cardlayout.show(Pane_content, "Hóa Đơn");
                // (Optional) later: load data into hdView from BLL/DAL
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pHoaDon.setBackground(new Color(64, 164, 156));
                pHoaDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pHoaDon.setBackground(new Color(29, 117, 109));
            }
        });

        lHoaDon.setFont(new java.awt.Font("Sitka Text", 0, 16)); // NOI18N
        lHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        //lHoaDon.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        lHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lHoaDon.setText("Hóa Đơn");
        int labelX = (pHoaDon.getWidth() - lHoaDon.getPreferredSize().width) / 2;
        int labelY = (pHoaDon.getHeight() - lHoaDon.getPreferredSize().height) / 2;
        lHoaDon.setBounds(labelX, labelY, lHoaDon.getPreferredSize().width, lHoaDon.getPreferredSize().height);

        pChiTietHD.setLayout(null);
        pChiTietHD.setBackground(new java.awt.Color(29, 117, 109));
        pChiTietHD.setBounds(0, 125, 200, 50);
        pChiTietHD.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Hiển thị panel Chi Tiết Hóa Đơn
                ClearTableALL();
                isMenuVisible = true;
                Pane_content.setVisible(isMenuVisible);
                cardlayout.show(Pane_content, "Chi Tiết HĐ");
                // (Optional) later: load detail data for selected invoice
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pChiTietHD.setBackground(new Color(64, 164, 156));
                pChiTietHD.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pChiTietHD.setBackground(new Color(29, 117, 109));
            }
        });

        lChiTietHD.setFont(new java.awt.Font("Sitka Text", 0, 16)); // NOI18N
        lChiTietHD.setForeground(new java.awt.Color(255, 255, 255));
        lChiTietHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lChiTietHD.setText("Chi Tiết HĐ");
        labelX = (pHoaDon.getWidth() - lChiTietHD.getPreferredSize().width) / 2;
        labelY = (pHoaDon.getHeight() - lChiTietHD.getPreferredSize().height) / 2;
        lChiTietHD.setBounds(labelX, labelY, lChiTietHD.getPreferredSize().width, lChiTietHD.getPreferredSize().height);


        pDanhMuc.setLayout(null);
        pDanhMuc.setBackground(new java.awt.Color(29, 117, 109));
        pDanhMuc.setBounds(0, 175, 200, 50);
        pDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClearTableALL();
                isMenuVisible = true;
                Pane_content.setVisible(isMenuVisible);
                cardlayout.show(Pane_content, "Danh Mục");
                dmView.getTable();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pDanhMuc.setBackground(new Color(64, 164, 156));
                pDanhMuc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pDanhMuc.setBackground(new Color(29, 117, 109));
            }
        });

        lDanhMuc.setFont(new java.awt.Font("Sitka Text", 0, 16)); // NOI18N
        lDanhMuc.setForeground(new java.awt.Color(255, 255, 255));
        lDanhMuc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lDanhMuc.setText("Danh Mục");
        labelX = (pHoaDon.getWidth() - lDanhMuc.getPreferredSize().width) / 2;
        labelY = (pHoaDon.getHeight() - lDanhMuc.getPreferredSize().height) / 2;
        lDanhMuc.setBounds(labelX, labelY, lDanhMuc.getPreferredSize().width, lDanhMuc.getPreferredSize().height);
        

        pSanPham.setLayout(null);
        pSanPham.setBackground(new java.awt.Color(29, 117, 109));
        pSanPham.setBounds(0, 225, 200, 50);
        
        // Nút hiện QL Sản Phẩm
        pSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClearTableALL();
                isMenuVisible = true;
                Pane_content.setVisible(isMenuVisible);
                cardlayout.show(Pane_content, "QL Sản Phẩm");
                spView.getTable();
                spView.comboBox();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pSanPham.setBackground(new Color(64, 164, 156));
                pSanPham.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pSanPham.setBackground(new Color(29, 117, 109));
            }
        });

        lSanPham.setFont(new java.awt.Font("Sitka Text", 0, 16)); // NOI18N
        lSanPham.setForeground(new java.awt.Color(255, 255, 255));
        lSanPham.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lSanPham.setText("QL Sản Phẩm");
        labelX = (pHoaDon.getWidth() - lSanPham.getPreferredSize().width) / 2;
        labelY = (pHoaDon.getHeight() - lSanPham.getPreferredSize().height) / 2;
        lSanPham.setBounds(labelX, labelY, lSanPham.getPreferredSize().width, lSanPham.getPreferredSize().height);
        

        pDoanhThu.setLayout(null);
        pDoanhThu.setBackground(new java.awt.Color(29, 117, 109));
        pDoanhThu.setBounds(0, 275, 200, 50);
        pDoanhThu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Minimal: hiển thị hộp thông báo hoặc chuyển tới Hóa Đơn (có thể implement báo cáo sau)
                ClearTableALL();
                isMenuVisible = true;
                Pane_content.setVisible(isMenuVisible);
                // For now show Hóa Đơn as base for doanh thu overview
                cardlayout.show(Pane_content, "Hóa Đơn");
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pDoanhThu.setBackground(new Color(64, 164, 156));
                pDoanhThu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pDoanhThu.setBackground(new Color(29, 117, 109));
            }
        });

        lDoanhThu.setFont(new java.awt.Font("Sitka Text", 0, 16)); // NOI18N
        lDoanhThu.setForeground(new java.awt.Color(255, 255, 255));
        lDoanhThu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lDoanhThu.setText("Doanh Thu");
        labelX = (pHoaDon.getWidth() - lDoanhThu.getPreferredSize().width) / 2;
        labelY = (pHoaDon.getHeight() - lDoanhThu.getPreferredSize().height) / 2;
        lDoanhThu.setBounds(labelX, labelY, lDoanhThu.getPreferredSize().width, lDoanhThu.getPreferredSize().height);

        pNhanVien.setLayout(null);
        pNhanVien.setBackground(new java.awt.Color(29, 117, 109));
        pNhanVien.setBounds(0, 325, 200, 50);
        
        // Nút hiện QL Nhân Viên
        pNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClearTableALL();
                isMenuVisible = true;
                Pane_content.setVisible(isMenuVisible);
                cardlayout.show(Pane_content, "QL Nhân Viên");
                nvView.getTable();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pNhanVien.setBackground(new Color(64, 164, 156));
                pNhanVien.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pNhanVien.setBackground(new Color(29, 117, 109));
            }
        });

        lNhanVien.setFont(new java.awt.Font("Sitka Text", 0, 16)); // NOI18N
        lNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        lNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lNhanVien.setText("QL Nhân Viên");
        labelX = (pHoaDon.getWidth() - lNhanVien.getPreferredSize().width) / 2;
        labelY = (pHoaDon.getHeight() - lNhanVien.getPreferredSize().height) / 2;
        lNhanVien.setBounds(labelX, labelY, lNhanVien.getPreferredSize().width, lNhanVien.getPreferredSize().height);

        pNhaCC.setLayout(null);
        pNhaCC.setBackground(new java.awt.Color(29, 117, 109));
        pNhaCC.setBounds(0, 375, 200, 50);
        pNhaCC.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClearTableALL();
                isMenuVisible = true;
                Pane_content.setVisible(isMenuVisible);
                cardlayout.show(Pane_content, "Nhà Cung Cấp");
                nccView.getTable();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pNhaCC.setBackground(new Color(64, 164, 156));
                pNhaCC.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pNhaCC.setBackground(new Color(29, 117, 109));
            }
        });

        lNhaCC.setFont(new java.awt.Font("Sitka Text", 0, 16)); // NOI18N
        lNhaCC.setForeground(new java.awt.Color(255, 255, 255));
        lNhaCC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lNhaCC.setText("Nhà Cung Cấp");
        labelX = (pHoaDon.getWidth() - lNhaCC.getPreferredSize().width) / 2;
        labelY = (pHoaDon.getHeight() - lNhaCC.getPreferredSize().height) / 2;
        lNhaCC.setBounds(labelX, labelY, lNhaCC.getPreferredSize().width, lNhaCC.getPreferredSize().height);

        pQuanLy.setLayout(null);
        pQuanLy.setBackground(new java.awt.Color(29, 117, 109));
        pQuanLy.setBounds(0, 425, 200, 50);
        
        // Nút hiện QL Khách Hàng
        pQuanLy.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClearTableALL();
                isMenuVisible = true;
                Pane_content.setVisible(isMenuVisible);
                cardlayout.show(Pane_content, "QL Khách Hàng");
                khView.getTable();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pQuanLy.setBackground(new Color(64, 164, 156));
                pQuanLy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pQuanLy.setBackground(new Color(29, 117, 109));
            }
        });

        lQuanLy.setFont(new java.awt.Font("Sitka Text", 0, 16)); // NOI18N
        lQuanLy.setForeground(new java.awt.Color(255, 255, 255));
        lQuanLy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lQuanLy.setText("QL Khách Hàng");
        labelX = (pHoaDon.getWidth() - lQuanLy.getPreferredSize().width) / 2;
        labelY = (pHoaDon.getHeight() - lQuanLy.getPreferredSize().height) / 2;
        lQuanLy.setBounds(labelX, labelY, lQuanLy.getPreferredSize().width, lQuanLy.getPreferredSize().height);
        

        pTaiKhoan.setLayout(null);
        pTaiKhoan.setBackground(new java.awt.Color(29, 117, 109));
        pTaiKhoan.setBounds(0, 475, 200, 50);
        pTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Hiển thị module Quản lý Tài Khoản
                ClearTableALL();
                isMenuVisible = true;
                Pane_content.setVisible(isMenuVisible);
                cardlayout.show(Pane_content, "Tài Khoản");
                tkView.loadData();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pTaiKhoan.setBackground(new Color(64, 164, 156));
                pTaiKhoan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pTaiKhoan.setBackground(new Color(29, 117, 109));
            }
        });

        lTaiKhoan.setFont(new java.awt.Font("Sitka Text", 0, 16)); // NOI18N
        lTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        lTaiKhoan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lTaiKhoan.setText("Tài Khoản");
        labelX = (pHoaDon.getWidth() - lTaiKhoan.getPreferredSize().width) / 2;
        labelY = (pHoaDon.getHeight() - lTaiKhoan.getPreferredSize().height) / 2;
        lTaiKhoan.setBounds(labelX, labelY, lTaiKhoan.getPreferredSize().width, lTaiKhoan.getPreferredSize().height);

        pDangXuat.setLayout(null);
        pDangXuat.setBackground(new java.awt.Color(29, 117, 109));
        pDangXuat.setBounds(0, 525, 200, 50);
        pDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Đăng xuất: đóng TrangChu và mở FrameLogin
                FrameLogin fl = new FrameLogin();
                TrangChu.this.dispose();
                fl.setVisible(true);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pDangXuat.setBackground(new Color(64, 164, 156));
                pDangXuat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pDangXuat.setBackground(new Color(29, 117, 109));
            }
        });

        lDangXuat.setFont(new java.awt.Font("Sitka Text", 0, 16)); // NOI18N
        lDangXuat.setForeground(new java.awt.Color(255, 255, 255));
        lDangXuat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lDangXuat.setText("Đăng Xuất");
        labelX = (pHoaDon.getWidth() - lDangXuat.getPreferredSize().width) / 2;
        labelY = (pHoaDon.getHeight() - lDangXuat.getPreferredSize().height) / 2;
        lDangXuat.setBounds(labelX, labelY, lDangXuat.getPreferredSize().width, lDangXuat.getPreferredSize().height);


        MenuPanel.add(pHoaDon);
        MenuPanel.add(pChiTietHD);
        MenuPanel.add(pDanhMuc);
        MenuPanel.add(pSanPham);
        MenuPanel.add(pDoanhThu);
        MenuPanel.add(pNhanVien);
        MenuPanel.add(pQuanLy);
        MenuPanel.add(pNhaCC);
        MenuPanel.add(pDangXuat);
        MenuPanel.add(pTaiKhoan);
        
        pHoaDon.setFocusable(true);
        pChiTietHD.setFocusable(true);
        pDanhMuc.setFocusable(true);
        pSanPham.setFocusable(true);
        pDoanhThu.setFocusable(true);
        pNhanVien.setFocusable(true);
        pQuanLy.setFocusable(true);
        pNhaCC.setFocusable(true);
        pTaiKhoan.setFocusable(true);
        pDangXuat.setFocusable(true);
        
        pHoaDon.add(lHoaDon);
        pChiTietHD.add(lChiTietHD);
        pDanhMuc.add(lDanhMuc);
        pSanPham.add(lSanPham);
        pDoanhThu.add(lDoanhThu);
        pNhanVien.add(lNhanVien);
        pQuanLy.add(lQuanLy);
        pNhaCC.add(lNhaCC);
        pTaiKhoan.add(lTaiKhoan);
        pDangXuat.add(lDangXuat);
        
        
        
        Pane_cover.add(MenuPanel);
        Pane_cover.add(Pane_content);
        this.add(Pane_cover);
        
    }

    // Variables declaration - do not modify 
    public javax.swing.JPanel MenuPanel;
    public javax.swing.JPanel Pane_cover;
    public javax.swing.JPanel Pane_content;
    public javax.swing.JLabel lChiTietHD;
    public javax.swing.JLabel lDanhMuc;
    public javax.swing.JLabel lDoanhThu;
    public javax.swing.JLabel lHoaDon;
    public javax.swing.JLabel lNhaCC;
    public javax.swing.JLabel lNhanVien;
    public javax.swing.JLabel lSanPham;
    public javax.swing.JLabel lQuanLy;
    public javax.swing.JLabel lTaiKhoan;
    public javax.swing.JLabel lDangXuat;
    public javax.swing.JPanel pChiTietHD;
    public javax.swing.JPanel pDanhMuc;
    public javax.swing.JPanel pDoanhThu;
    public javax.swing.JPanel pHoaDon;
    public javax.swing.JPanel pNhaCC;
    public javax.swing.JPanel pNhanVien;
    public javax.swing.JPanel pSanPham;
    public javax.swing.JPanel pQuanLy;
    public javax.swing.JPanel pTaiKhoan;
    public javax.swing.JPanel pDangXuat;
    // End of variables declaration                   

  
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TrangChu("Kahng").setVisible(true);
            }
        });
    }
}
