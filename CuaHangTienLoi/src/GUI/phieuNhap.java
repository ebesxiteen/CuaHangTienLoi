package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

public class phieuNhap extends JPanel {

    public phieuNhap() {
        initComponents();
    }
                     
    // Variables declaration - do not modify                     
    public javax.swing.JButton btTimKiem;
    public javax.swing.JComboBox<String> cbLoc;
    public javax.swing.JComboBox<String> cbSapXep;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lLoc;
    public javax.swing.JLabel lSapXep;
    public javax.swing.JTable tbPhieuNhap;
    public javax.swing.JTextField thanhTimKiem;
    public javax.swing.JPopupMenu menu;
    public javax.swing.JMenuItem them;
    public javax.swing.JMenuItem sua;
    public javax.swing.JMenuItem xoa;
    // End of variables declaration                   

    // Code của initComponents() được copy từ class khác
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPhieuNhap = new javax.swing.JTable();
        thanhTimKiem = new javax.swing.JTextField();
        btTimKiem = new javax.swing.JButton();
        cbSapXep = new javax.swing.JComboBox<>();
        lSapXep = new javax.swing.JLabel();
        cbLoc = new javax.swing.JComboBox<>();
        lLoc = new javax.swing.JLabel();
        menu = new JPopupMenu();
        them = new JMenuItem("Thêm");
        sua = new JMenuItem("Sửa");
        xoa = new JMenuItem("Xóa");

        // Thêm các menu item vào JPopupMenu
        menu.add(them);
        menu.add(sua);
        menu.add(xoa);
        
        setBackground(new java.awt.Color(255, 255, 255));

        tbPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã phiếu nhập", "Mã nhà cung cấp", "Mã nhân viên", "Ngày tạo", "Tổng tiền"
            }
        ));
        jScrollPane1.setViewportView(tbPhieuNhap);

        btTimKiem.setText("Tìm kiếm");
        btTimKiem.setBackground(new java.awt.Color(0, 102, 102));
        btTimKiem.setForeground(new java.awt.Color(255, 255, 255));

        cbSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tăng dần", "Giảm dần" }));

        lSapXep.setForeground(new java.awt.Color(0, 102, 102));
        lSapXep.setText("Sắp xếp:");

        cbLoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã phiếu nhập", "Mã nhà cung cấp", "Mã nhân viên", "Ngày tạo", "Tổng tiền", " " }));

        lLoc.setForeground(new java.awt.Color(0, 102, 102));
        lLoc.setText("Lọc theo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(140, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(thanhTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btTimKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lLoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lSapXep)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(140, 140, 140))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(thanhTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btTimKiem)
                    .addComponent(cbSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lSapXep)
                    .addComponent(cbLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lLoc))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        
        tbPhieuNhap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = tbPhieuNhap.rowAtPoint(e.getPoint());
                    int column = tbPhieuNhap.columnAtPoint(e.getPoint());
                    if (row >= 0 && column >= 0) {
                        menu.show(e.getComponent(), e.getX(), e.getY());
                        // Xử lý sự kiện click chuột phải tại dòng và cột tương ứng
                        System.out.println("Right clicked on row: " + row + ", column: " + column);
                    }
                }
            }
        });
        
        them.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("\"Them\" clicked");
                // Thực hiện hành động mong muốn khi them được nhấp
            }
        });

        // Thêm ActionListener cho sua
        sua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("\"Sua\" clicked");
                // Thực hiện hành động mong muốn khi sua được nhấp
            }
        });

        // Thêm ItemListener cho xoa
        xoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("\"Xoa\" clicked");
                // Thực hiện hành động mong muốn khi xoa được nhấp
            }
        });
    }                
}