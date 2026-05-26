package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import BLL.HoaDonBLL;
import DAL.DALDanhMuc;
import DAL.DALkhachHang;
import DAL.DALnhanVien;
import DAL.DALsanPham;
import DAL.HoaDonDAL;
import DTO.CtHoaDon;
import DTO.DanhMuc;
import DTO.HoaDon;
import DTO.khachHang;
import DTO.nhanVien;
import DTO.sanPham;

public class HoaDonWorkspace extends JPanel {

    private final HoaDonBLL hoaDonBLL = new HoaDonBLL();
    private final HoaDonDAL hoaDonDAL = new HoaDonDAL();
    private final DALkhachHang khachHangDAL = DALkhachHang.getinstance();
    private final DALnhanVien nhanVienDAL = DALnhanVien.getinstance();
    private final DALDanhMuc danhMucDAL = DALDanhMuc.getinstance();
    private final DALsanPham sanPhamDAL = DALsanPham.getintance();

    private final JTable invoiceTable = new JTable();
    private final JTable detailTable = new JTable();

    private final JTextField txtMaHD = new JTextField();
    private final JTextField txtMaKH = new JTextField();
    private final JTextField txtMaNV = new JTextField();
    private final JTextField txtNgayTao = new JTextField();
    private final JTextField txtTongTien = new JTextField();
    private final JTextField txtInvoiceSearch = new JTextField();
    private final JComboBox<String> cboMaHDFilter = new JComboBox<>();
    private final JComboBox<String> cboMaKHFilter = new JComboBox<>();
    private final JComboBox<String> cboMaNVFilter = new JComboBox<>();
    private final JComboBox<String> cboNgayTaoFilter = new JComboBox<>();
    private final ArrayList<HoaDon> invoiceList = new ArrayList<>();
    private boolean updatingInvoiceFilters;

    public HoaDonWorkspace() {
        buildUI();
        wireEvents();
        loadInvoices();
    }

    public void loadInvoices() {
        invoiceList.clear();
        invoiceList.addAll(hoaDonBLL.getAll());
        updateInvoiceFilterCombos();
        applyInvoiceFilters();
    }

    private void renderInvoices(List<HoaDon> list) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Mã HĐ", "Mã KH", "Mã NV", "Ngày tạo", "Tổng tiền"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (HoaDon hd : list) {
            model.addRow(new Object[]{hd.getMahd(), hd.getMakh(), hd.getManv(), hd.getNgaytao(), hd.getTongtien()});
        }
        invoiceTable.setModel(model);
    }

    private void updateInvoiceFilterCombos() {
        updatingInvoiceFilters = true;
        Object selectedMaHD = cboMaHDFilter.getSelectedItem();
        Object selectedMaKH = cboMaKHFilter.getSelectedItem();
        Object selectedMaNV = cboMaNVFilter.getSelectedItem();
        Object selectedNgayTao = cboNgayTaoFilter.getSelectedItem();

        cboMaHDFilter.removeAllItems();
        cboMaKHFilter.removeAllItems();
        cboMaNVFilter.removeAllItems();
        cboNgayTaoFilter.removeAllItems();

        cboMaHDFilter.addItem("Tất cả mã HĐ");
        cboMaKHFilter.addItem("Tất cả mã KH");
        cboMaNVFilter.addItem("Tất cả mã NV");
        cboNgayTaoFilter.addItem("Tất cả ngày");

        for (HoaDon hd : invoiceList) {
            addUniqueComboItem(cboMaHDFilter, hd.getMahd());
            addUniqueComboItem(cboMaKHFilter, hd.getMakh());
            addUniqueComboItem(cboMaNVFilter, hd.getManv());
            addUniqueComboItem(cboNgayTaoFilter, hd.getNgaytao());
        }

        restoreComboSelection(cboMaHDFilter, selectedMaHD, 0);
        restoreComboSelection(cboMaKHFilter, selectedMaKH, 0);
        restoreComboSelection(cboMaNVFilter, selectedMaNV, 0);
        restoreComboSelection(cboNgayTaoFilter, selectedNgayTao, 0);
        updatingInvoiceFilters = false;
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

    private void applyInvoiceFilters() {
        if (updatingInvoiceFilters) {
            return;
        }

        String selectedMaHD = String.valueOf(cboMaHDFilter.getSelectedItem());
        String selectedMaKH = String.valueOf(cboMaKHFilter.getSelectedItem());
        String selectedMaNV = String.valueOf(cboMaNVFilter.getSelectedItem());
        String selectedNgayTao = String.valueOf(cboNgayTaoFilter.getSelectedItem());
        String keyword = txtInvoiceSearch.getText().trim().toLowerCase();

        boolean filterMaHD = selectedMaHD != null && !"Tất cả mã HĐ".equals(selectedMaHD);
        boolean filterMaKH = selectedMaKH != null && !"Tất cả mã KH".equals(selectedMaKH);
        boolean filterMaNV = selectedMaNV != null && !"Tất cả mã NV".equals(selectedMaNV);
        boolean filterNgayTao = selectedNgayTao != null && !"Tất cả ngày".equals(selectedNgayTao);
        boolean filterKeyword = !keyword.isEmpty();

        ArrayList<HoaDon> result = new ArrayList<>();
        for (HoaDon hd : invoiceList) {
            boolean matchesMaHD = !filterMaHD || selectedMaHD.equals(hd.getMahd());
            boolean matchesMaKH = !filterMaKH || selectedMaKH.equals(hd.getMakh());
            boolean matchesMaNV = !filterMaNV || selectedMaNV.equals(hd.getManv());
            boolean matchesNgayTao = !filterNgayTao || selectedNgayTao.equals(hd.getNgaytao());
            boolean matchesKeyword = !filterKeyword
                    || containsKeyword(hd.getMahd(), keyword)
                    || containsKeyword(hd.getMakh(), keyword)
                    || containsKeyword(hd.getManv(), keyword)
                    || containsKeyword(hd.getNgaytao(), keyword)
                    || containsKeyword(String.valueOf(hd.getTongtien()), keyword);

            if (matchesMaHD && matchesMaKH && matchesMaNV && matchesNgayTao && matchesKeyword) {
                result.add(hd);
            }
        }

        renderInvoices(result);
    }

    private boolean containsKeyword(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    public void clearSelection() {
        invoiceTable.clearSelection();
        clearDetailPanel();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1015, 690));

        JLabel title = new JLabel("HÓA ĐƠN", SwingConstants.LEFT);
        title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 26));
        title.setForeground(new Color(0, 102, 102));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel titlePanel = new JPanel(new BorderLayout(8, 0));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(title, BorderLayout.WEST);

        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(0, 102, 102));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setBorderPainted(false);
        txtInvoiceSearch.setPreferredSize(new Dimension(240, 28));
        btnSearch.setPreferredSize(new Dimension(100, 28));
        JPanel searchPanel = new JPanel(new BorderLayout(6, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        searchPanel.add(txtInvoiceSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);
        titlePanel.add(searchPanel, BorderLayout.EAST);
        add(titlePanel, BorderLayout.NORTH);

        invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        invoiceTable.getTableHeader().setReorderingAllowed(false);
        invoiceTable.getTableHeader().setResizingAllowed(false);
        invoiceTable.setRowHeight(24);

        detailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        detailTable.getTableHeader().setReorderingAllowed(false);
        detailTable.getTableHeader().setResizingAllowed(false);
        detailTable.setRowHeight(24);

        DocumentListener invoiceSearchListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applyInvoiceFilters();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applyInvoiceFilters();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applyInvoiceFilters();
            }
        };
        txtInvoiceSearch.getDocument().addDocumentListener(invoiceSearchListener);
        btnSearch.addActionListener(e -> applyInvoiceFilters());
        cboMaHDFilter.addActionListener(e -> applyInvoiceFilters());
        cboMaKHFilter.addActionListener(e -> applyInvoiceFilters());
        cboMaNVFilter.addActionListener(e -> applyInvoiceFilters());
        cboNgayTaoFilter.addActionListener(e -> applyInvoiceFilters());

        JPanel leftPanel = new JPanel(new BorderLayout(8, 8));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setMinimumSize(new Dimension(480, 0));

        JPanel allFilterPanel = new JPanel(new BorderLayout(0, 8));
        allFilterPanel.setBackground(Color.WHITE);
        JLabel allLabel = new JLabel("Tất cả hóa đơn");
        allLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        allLabel.setForeground(new Color(0, 102, 102));
        allFilterPanel.add(allLabel, BorderLayout.NORTH);

        JPanel columnFilterPanel = new JPanel(new GridLayout(1, 5, 0, 0));
        columnFilterPanel.setBackground(Color.WHITE);
        columnFilterPanel.add(cboMaHDFilter);
        columnFilterPanel.add(cboMaKHFilter);
        columnFilterPanel.add(cboMaNVFilter);
        columnFilterPanel.add(cboNgayTaoFilter);
        columnFilterPanel.add(new JLabel(""));
        allFilterPanel.add(columnFilterPanel, BorderLayout.CENTER);

        leftPanel.add(allFilterPanel, BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(invoiceTable), BorderLayout.CENTER);

        JButton btnCreate = new JButton("Tạo hóa đơn");
        JButton btnDelete = new JButton("Xóa hóa đơn");
        JButton btnRefresh = new JButton("Làm mới");
        btnCreate.setBackground(new Color(0, 102, 0));
        btnCreate.setForeground(Color.WHITE);
        btnDelete.setBackground(new Color(153, 0, 0));
        btnDelete.setForeground(Color.WHITE);
        btnRefresh.setBackground(new Color(102, 102, 102));
        btnRefresh.setForeground(Color.WHITE);

        JPanel leftBottom = new JPanel(new GridLayout(1, 3, 10, 0));
        leftBottom.setBackground(Color.WHITE);
        leftBottom.setPreferredSize(new Dimension(0, 38));
        leftBottom.add(btnCreate);
        leftBottom.add(btnDelete);
        leftBottom.add(btnRefresh);
        leftPanel.add(leftBottom, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout(8, 8));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setMinimumSize(new Dimension(420, 0));

        JLabel detailTitle = new JLabel("Chi Tiết Hóa Đơn");
        detailTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));
        detailTitle.setForeground(new Color(0, 102, 102));

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        txtMaHD.setEditable(false);
        txtMaKH.setEditable(false);
        txtMaNV.setEditable(false);
        txtNgayTao.setEditable(false);
        txtTongTien.setEditable(false);

        addInfoRow(infoPanel, gbc, 0, "Mã HĐ:", txtMaHD, "Mã KH:", txtMaKH);
        addInfoRow(infoPanel, gbc, 1, "Mã NV:", txtMaNV, "Ngày tạo:", txtNgayTao);
        addInfoRow(infoPanel, gbc, 2, "Tổng tiền:", txtTongTien, null, null);

        JPanel detailTablePanel = new JPanel(new BorderLayout(6, 6));
        detailTablePanel.setBackground(Color.WHITE);
        detailTablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm trong hóa đơn"));
        detailTablePanel.add(new JScrollPane(detailTable), BorderLayout.CENTER);

        JPanel rightTop = new JPanel(new BorderLayout(0, 8));
        rightTop.setBackground(Color.WHITE);
        rightTop.add(detailTitle, BorderLayout.NORTH);
        rightTop.add(infoPanel, BorderLayout.CENTER);

        rightPanel.add(rightTop, BorderLayout.NORTH);
        rightPanel.add(detailTablePanel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.52);
        splitPane.setDividerLocation(520);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);

        btnCreate.addActionListener(e -> {
            CreateHoaDonDialog dialog = new CreateHoaDonDialog(SwingUtilities.getWindowAncestor(this));
            dialog.setVisible(true);
            if (dialog.isSaved()) {
                loadInvoices();
                selectInvoice(dialog.getCreatedInvoiceId());
            }
        });

        btnDelete.addActionListener(e -> {
            int row = invoiceTable.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Chọn 1 hóa đơn để xóa");
                return;
            }
            String mahd = String.valueOf(invoiceTable.getValueAt(row, 0));
            int confirm = JOptionPane.showConfirmDialog(this, "Xóa hóa đơn " + mahd + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (hoaDonBLL.deleteHoaDon(mahd)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    loadInvoices();
                    clearDetailPanel();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                }
            }
        });

        btnRefresh.addActionListener(e -> {
            txtInvoiceSearch.setText("");
            cboMaHDFilter.setSelectedIndex(0);
            cboMaKHFilter.setSelectedIndex(0);
            cboMaNVFilter.setSelectedIndex(0);
            cboNgayTaoFilter.setSelectedIndex(0);
            loadInvoices();
            clearDetailPanel();
        });
    }

    private void wireEvents() {
        invoiceTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = invoiceTable.getSelectedRow();
                    if (row >= 0) {
                        String mahd = String.valueOf(invoiceTable.getValueAt(row, 0));
                        showInvoiceDetails(mahd);
                    }
                }
            }
        });
    }

    private void selectInvoice(String mahd) {
        if (mahd == null || mahd.isBlank()) {
            return;
        }
        for (int i = 0; i < invoiceTable.getRowCount(); i++) {
            if (mahd.equals(String.valueOf(invoiceTable.getValueAt(i, 0)))) {
                invoiceTable.setRowSelectionInterval(i, i);
                showInvoiceDetails(mahd);
                break;
            }
        }
    }

    private void showInvoiceDetails(String mahd) {
        HoaDon hd = hoaDonDAL.selectById(mahd);
        if (hd == null || hd.getMahd() == null) {
            clearDetailPanel();
            return;
        }
        txtMaHD.setText(hd.getMahd());
        txtMaKH.setText(hd.getMakh());
        txtMaNV.setText(hd.getManv());
        txtNgayTao.setText(hd.getNgaytao());
        txtTongTien.setText(String.valueOf(hd.getTongtien()));

        ArrayList<CtHoaDon> details = hoaDonBLL.getCtByMa(mahd);
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (CtHoaDon ct : details) {
            sanPham sp = sanPhamDAL.selectById(ct.getMasp());
            String ten = sp != null ? sp.getTensp() : "";
            model.addRow(new Object[]{ct.getMasp(), ten, ct.getSoluong(), ct.getDongia(), ct.getThanhtien()});
        }
        detailTable.setModel(model);
    }

    private void clearDetailPanel() {
        txtMaHD.setText("");
        txtMaKH.setText("");
        txtMaNV.setText("");
        txtNgayTao.setText("");
        txtTongTien.setText("");
        detailTable.setModel(new DefaultTableModel(new Object[]{"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"}, 0));
    }

    private void addInfoRow(JPanel panel, GridBagConstraints gbc, int row, String label1, JTextField field1, String label2, JTextField field2) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.25;
        panel.add(new JLabel(label1), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        panel.add(field1, gbc);
        if (label2 != null && field2 != null) {
            gbc.gridx = 2;
            gbc.weightx = 0.25;
            panel.add(new JLabel(label2), gbc);
            gbc.gridx = 3;
            gbc.weightx = 0.75;
            panel.add(field2, gbc);
        }
    }

    private static class CategoryOption {
        private final String code;
        private final String name;

        private CategoryOption(String code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    private static class ProductOption {
        private final String masp;
        private final String tensp;
        private final String maloai;
        private final int dongia;

        private ProductOption(String masp, String tensp, String maloai, int dongia) {
            this.masp = masp;
            this.tensp = tensp;
            this.maloai = maloai;
            this.dongia = dongia;
        }

        @Override
        public String toString() {
            return masp;
        }
    }

    private static class ProductLine {
        private final String masp;
        private final String tensp;
        private int soluong;
        private final int dongia;

        private ProductLine(String masp, String tensp, int soluong, int dongia) {
            this.masp = masp;
            this.tensp = tensp;
            this.soluong = soluong;
            this.dongia = dongia;
        }

        private int getThanhTien() {
            return soluong * dongia;
        }
    }

    private class CreateHoaDonDialog extends JDialog {
        private final JComboBox<String> cboMaKH = new JComboBox<>();
        private final JComboBox<String> cboMaNV = new JComboBox<>();
        private final JTextField txtMaHDDialog = new JTextField();
        private final JTextField txtNgayTaoDialog = new JTextField();
        private final JTextField txtTongTienDialog = new JTextField();
        private final JTable detailDraftTable = new JTable();
        private final Map<String, ProductLine> lines = new LinkedHashMap<>();
        private boolean saved = false;
        private String createdInvoiceId = null;

        private CreateHoaDonDialog(Window owner) {
            super(owner, "Tạo hóa đơn", Dialog.ModalityType.APPLICATION_MODAL);
            buildDialog();
            loadCombos();
            refreshDraftTable();
            updateTotal();
            pack();
            setMinimumSize(new Dimension(920, 620));
            setLocationRelativeTo(owner);
        }

        private void buildDialog() {
            setLayout(new BorderLayout(10, 10));
            JPanel header = new JPanel(new GridBagLayout());
            header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(4, 6, 4, 6);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;

            txtMaHDDialog.setEditable(false);
            txtNgayTaoDialog.setEditable(false);
            txtTongTienDialog.setEditable(false);

            txtMaHDDialog.setText(hoaDonDAL.getNextMaHD());
            txtNgayTaoDialog.setText(java.time.LocalDate.now().toString());

            gbc.gridy = 0;
            gbc.gridx = 0;
            header.add(new JLabel("Mã HĐ:"), gbc);
            gbc.gridx = 1;
            header.add(txtMaHDDialog, gbc);
            gbc.gridx = 2;
            header.add(new JLabel("Ngày tạo:"), gbc);
            gbc.gridx = 3;
            header.add(txtNgayTaoDialog, gbc);

            gbc.gridy = 1;
            gbc.gridx = 0;
            header.add(new JLabel("Mã KH:"), gbc);
            gbc.gridx = 1;
            header.add(cboMaKH, gbc);
            gbc.gridx = 2;
            header.add(new JLabel("Mã NV:"), gbc);
            gbc.gridx = 3;
            header.add(cboMaNV, gbc);

            add(header, BorderLayout.NORTH);

            detailDraftTable.getTableHeader().setResizingAllowed(false);
            detailDraftTable.getTableHeader().setReorderingAllowed(false);
            JPanel center = new JPanel(new BorderLayout(8, 8));
            center.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            center.add(new JScrollPane(detailDraftTable), BorderLayout.CENTER);

            JButton btnAddProduct = new JButton("Thêm sản phẩm");
            JButton btnRemoveProduct = new JButton("Xóa dòng");
            JButton btnSave = new JButton("Lưu hóa đơn");
            JButton btnCancel = new JButton("Hủy");
            btnSave.setBackground(new Color(0, 102, 0));
            btnSave.setForeground(Color.WHITE);
            btnCancel.setBackground(new Color(102, 102, 102));
            btnCancel.setForeground(Color.WHITE);

            JPanel bottom = new JPanel(new BorderLayout(8, 8));
            JPanel draftButtons = new JPanel(new GridLayout(1, 2, 8, 0));
            draftButtons.add(btnAddProduct);
            draftButtons.add(btnRemoveProduct);
            bottom.add(draftButtons, BorderLayout.WEST);

            JPanel totalPanel = new JPanel(new GridBagLayout());
            GridBagConstraints tgbc = new GridBagConstraints();
            tgbc.insets = new Insets(4, 6, 4, 6);
            tgbc.fill = GridBagConstraints.HORIZONTAL;
            tgbc.weightx = 1.0;
            tgbc.gridx = 0;
            totalPanel.add(new JLabel("Tổng tiền:"), tgbc);
            tgbc.gridx = 1;
            totalPanel.add(txtTongTienDialog, tgbc);
            bottom.add(totalPanel, BorderLayout.CENTER);

            JPanel actionPanel = new JPanel(new GridLayout(1, 2, 8, 0));
            actionPanel.add(btnCancel);
            actionPanel.add(btnSave);
            bottom.add(actionPanel, BorderLayout.EAST);

            JPanel south = new JPanel(new BorderLayout(8, 8));
            south.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            south.add(bottom, BorderLayout.CENTER);

            add(center, BorderLayout.CENTER);
            add(south, BorderLayout.SOUTH);

            btnAddProduct.addActionListener(e -> {
                ProductLine line = pickProductLine();
                if (line == null) {
                    return;
                }
                ProductLine existing = lines.get(line.masp);
                if (existing != null) {
                    existing.soluong += line.soluong;
                } else {
                    lines.put(line.masp, line);
                }
                refreshDraftTable();
                updateTotal();
            });

            btnRemoveProduct.addActionListener(e -> {
                int row = detailDraftTable.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(this, "Chọn 1 sản phẩm để xóa");
                    return;
                }
                String masp = String.valueOf(detailDraftTable.getValueAt(row, 0));
                lines.remove(masp);
                refreshDraftTable();
                updateTotal();
            });

            btnSave.addActionListener(e -> {
                String makh = String.valueOf(cboMaKH.getSelectedItem());
                String manv = String.valueOf(cboMaNV.getSelectedItem());
                if (makh == null || makh.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Chọn mã khách hàng");
                    return;
                }
                if (manv == null || manv.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Chọn mã nhân viên");
                    return;
                }
                if (lines.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Thêm ít nhất 1 sản phẩm");
                    return;
                }
                int tongtien = 0;
                ArrayList<CtHoaDon> details = new ArrayList<>();
                for (ProductLine line : lines.values()) {
                    tongtien += line.getThanhTien();
                    details.add(new CtHoaDon(txtMaHDDialog.getText().trim(), line.masp, line.soluong, line.dongia, line.getThanhTien()));
                }
                HoaDon hd = new HoaDon(txtMaHDDialog.getText().trim(), makh, manv, txtNgayTaoDialog.getText().trim(), tongtien);
                if (hoaDonBLL.createHoaDon(hd, details)) {
                    saved = true;
                    createdInvoiceId = hd.getMahd();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể lưu hóa đơn");
                }
            });

            btnCancel.addActionListener(e -> dispose());
        }

        private void loadCombos() {
            for (khachHang kh : khachHangDAL.selectAll()) {
                cboMaKH.addItem(kh.getMakh());
            }
            for (nhanVien nv : nhanVienDAL.selectAll()) {
                cboMaNV.addItem(nv.getManv());
            }
        }

        private ProductLine pickProductLine() {
            List<DanhMuc> categories = danhMucDAL.selectAll();
            List<sanPham> products = sanPhamDAL.selectAll();

            JComboBox<CategoryOption> cboCategory = new JComboBox<>();
            JComboBox<ProductOption> cboProduct = new JComboBox<>();
            JLabel lblName = new JLabel("");
            JLabel lblPrice = new JLabel("");
            JSpinner spinnerQty = new JSpinner(new SpinnerNumberModel(1, 1, 999999, 1));

            for (DanhMuc category : categories) {
                cboCategory.addItem(new CategoryOption(category.getMaloai(), category.getTenloai()));
            }

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(4, 6, 4, 6);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;

            gbc.gridy = 0;
            gbc.gridx = 0;
            panel.add(new JLabel("Loại sản phẩm:"), gbc);
            gbc.gridx = 1;
            panel.add(cboCategory, gbc);

            gbc.gridy = 1;
            gbc.gridx = 0;
            panel.add(new JLabel("Mã sản phẩm:"), gbc);
            gbc.gridx = 1;
            panel.add(cboProduct, gbc);

            gbc.gridy = 2;
            gbc.gridx = 0;
            panel.add(new JLabel("Tên sản phẩm:"), gbc);
            gbc.gridx = 1;
            panel.add(lblName, gbc);

            gbc.gridy = 3;
            gbc.gridx = 0;
            panel.add(new JLabel("Đơn giá:"), gbc);
            gbc.gridx = 1;
            panel.add(lblPrice, gbc);

            gbc.gridy = 4;
            gbc.gridx = 0;
            panel.add(new JLabel("Số lượng:"), gbc);
            gbc.gridx = 1;
            panel.add(spinnerQty, gbc);

            final CategoryOption[] selectedCategory = new CategoryOption[1];

            java.util.function.Consumer<CategoryOption> fillProducts = (category) -> {
                cboProduct.removeAllItems();
                for (sanPham sp : products) {
                    if (sp.getMaloaisp().equals(category.code)) {
                        cboProduct.addItem(new ProductOption(sp.getMasp(), sp.getTensp(), sp.getMaloaisp(), (int) sp.getDongia()));
                    }
                }
                if (cboProduct.getItemCount() > 0) {
                    cboProduct.setSelectedIndex(0);
                } else {
                    lblName.setText("");
                    lblPrice.setText("");
                }
            };

            if (cboCategory.getItemCount() > 0) {
                selectedCategory[0] = (CategoryOption) cboCategory.getItemAt(0);
                fillProducts.accept(selectedCategory[0]);
            }

            cboCategory.addActionListener(ev -> {
                CategoryOption category = (CategoryOption) cboCategory.getSelectedItem();
                if (category != null) {
                    selectedCategory[0] = category;
                    fillProducts.accept(category);
                }
            });

            cboProduct.addActionListener(ev -> {
                ProductOption product = (ProductOption) cboProduct.getSelectedItem();
                if (product != null) {
                    lblName.setText(product.tensp);
                    lblPrice.setText(String.valueOf(product.dongia));
                }
            });

            int result = JOptionPane.showConfirmDialog(this, panel, "Chọn sản phẩm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) {
                return null;
            }
            ProductOption product = (ProductOption) cboProduct.getSelectedItem();
            if (product == null) {
                JOptionPane.showMessageDialog(this, "Chọn sản phẩm");
                return null;
            }
            int qty = ((Number) spinnerQty.getValue()).intValue();
            if (qty <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
                return null;
            }
            return new ProductLine(product.masp, product.tensp, qty, product.dongia);
        }

        private void refreshDraftTable() {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            for (ProductLine line : lines.values()) {
                model.addRow(new Object[]{line.masp, line.tensp, line.soluong, line.dongia, line.getThanhTien()});
            }
            detailDraftTable.setModel(model);
        }

        private void updateTotal() {
            int total = 0;
            for (ProductLine line : lines.values()) {
                total += line.getThanhTien();
            }
            txtTongTienDialog.setText(String.valueOf(total));
        }

        public boolean isSaved() {
            return saved;
        }

        public String getCreatedInvoiceId() {
            return createdInvoiceId;
        }
    }
}
