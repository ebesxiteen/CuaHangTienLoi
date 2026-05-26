package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

import Database.Connect;

public class DoanhThuWorkspace extends JPanel {
    private JSpinner spFrom;
    private JSpinner spTo;
    private SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
    private javax.swing.JComboBox<String> cboGranularity;

    private DefaultTableModel timeModel;
    private DefaultTableModel detailModel;
    private DefaultTableModel topProductModel;
    private DefaultTableModel topCategoryModel;

    private SimpleLineChart chartPanel;
    private ArrayList<String> chartLabels = new ArrayList<>();
    private ArrayList<Long> chartValues = new ArrayList<>();

    private JLabel lblTotalRevenue;
    private JLabel lblInvoiceCount;
    private JLabel lblAvgPerInvoice;

    public DoanhThuWorkspace() {
        setLayout(new BorderLayout());

        JPanel filter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filter.add(new JLabel("Từ (YYYY-MM-DD):"));
        SpinnerDateModel mFrom = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        spFrom = new JSpinner(mFrom);
        spFrom.setEditor(new JSpinner.DateEditor(spFrom, "yyyy-MM-dd"));
        filter.add(spFrom);
        filter.add(new JLabel("Đến:"));
        SpinnerDateModel mTo = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        spTo = new JSpinner(mTo);
        spTo.setEditor(new JSpinner.DateEditor(spTo, "yyyy-MM-dd"));
        filter.add(spTo);
        cboGranularity = new javax.swing.JComboBox<>(new String[] { "Ngày", "Tháng", "Quý", "Năm" });
        filter.add(new JLabel("Chi tiết theo:"));
        filter.add(cboGranularity);
        JButton btnApply = new JButton("Áp dụng");
        btnApply.addActionListener(e -> refreshDashboard());
        filter.add(btnApply);
        JButton btnExport = new JButton("Xuất CSV");
        btnExport.addActionListener(e -> exportTimeSeriesCsv());
        filter.add(btnExport);
        add(filter, BorderLayout.NORTH);

        JPanel kpi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblTotalRevenue = new JLabel("Tổng doanh thu: 0");
        lblInvoiceCount = new JLabel("Số hóa đơn: 0");
        lblAvgPerInvoice = new JLabel("TB / HĐ: 0");
        kpi.add(lblTotalRevenue);
        kpi.add(lblInvoiceCount);
        kpi.add(lblAvgPerInvoice);
        add(kpi, BorderLayout.SOUTH);

        JPanel center = new JPanel(new BorderLayout());
        timeModel = new DefaultTableModel(new String[] { "Thời điểm", "Doanh thu" }, 0);
        chartPanel = new SimpleLineChart();
        center.add(chartPanel, BorderLayout.CENTER);

        JPanel right = new JPanel(new BorderLayout());
        topProductModel = new DefaultTableModel(new String[] { "Mã SP", "Tên SP", "Doanh thu" }, 0);
        topCategoryModel = new DefaultTableModel(new String[] { "Mã loại", "Tên loại", "Doanh thu" }, 0);
        right.add(new JScrollPane(new JTable(topProductModel)), BorderLayout.NORTH);
        right.add(new JScrollPane(new JTable(topCategoryModel)), BorderLayout.CENTER);
        center.add(right, BorderLayout.EAST);

        detailModel = new DefaultTableModel(new String[] { "Mã HĐ", "Ngày", "Khách", "Nhân viên", "Tổng" }, 0);
        JTable tblDetail = new JTable(detailModel);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, center, new JScrollPane(tblDetail));
        split.setResizeWeight(0.6);
        add(split, BorderLayout.CENTER);

        initDateRangeFromInvoices();
        refreshDashboard();
    }

    public void refreshDashboard() {
        String from = dateFmt.format((Date) spFrom.getValue());
        String to = dateFmt.format((Date) spTo.getValue());
        String gran = (String) cboGranularity.getSelectedItem();
        loadTimeSeries(from, to, gran);
        loadKPIs(from, to);
        loadTopProducts(from, to);
        loadTopCategories(from, to);
        loadDetails(from, to);
    }

    public void reloadAllInvoicesDashboard() {
        initDateRangeFromInvoices();
        refreshDashboard();
    }

    private void loadTimeSeries(String from, String to, String gran) {
        String groupExpr;
        switch (gran) {
            case "Tháng":
                groupExpr = "CONCAT(SUBSTRING(ngaytao,1,4), '-', SUBSTRING(ngaytao,6,2))";
                break;
            case "Quý":
                groupExpr = "CONCAT(SUBSTRING(ngaytao,1,4), '-Q', CEIL(CAST(SUBSTRING(ngaytao,6,2) AS UNSIGNED)/3))";
                break;
            case "Năm":
                groupExpr = "SUBSTRING(ngaytao,1,4)";
                break;
            default:
                groupExpr = "ngaytao";
        }
        String sql = "SELECT " + groupExpr + " AS period, SUM(tongtien) AS doanhthu FROM hoadon";
        if (!from.isEmpty() && !to.isEmpty()) sql += " WHERE ngaytao BETWEEN '" + from + "' AND '" + to + "'";
        sql += " GROUP BY period ORDER BY period";
        chartLabels.clear(); chartValues.clear();
        try (Connection c = Connect.getConnection(); PreparedStatement pst = c.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                chartLabels.add(rs.getString("period"));
                chartValues.add(rs.getLong("doanhthu"));
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        chartPanel.setData(chartLabels, chartValues);
    }

    private void loadKPIs(String from, String to) {
        long total = 0;
        int count = 0;
        String sql = "SELECT COUNT(*) AS cnt, SUM(tongtien) AS total FROM hoadon";
        if (!from.isEmpty() && !to.isEmpty()) sql += " WHERE ngaytao BETWEEN '" + from + "' AND '" + to + "'";
        try (Connection c = Connect.getConnection(); PreparedStatement pst = c.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt("cnt");
                total = rs.getLong("total");
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        lblTotalRevenue.setText("Tổng doanh thu: " + total);
        lblInvoiceCount.setText("Số hóa đơn: " + count);
        lblAvgPerInvoice.setText("TB / HĐ: " + (count==0?0:total/count));
    }

    private void loadTopProducts(String from, String to) {
        topProductModel.setRowCount(0);
        String sql = "SELECT c.masp, s.tensp, SUM(c.thanhtien) AS doanhthu FROM cthoadon c JOIN hoadon h ON c.mahd=h.mahd JOIN sanpham s ON c.masp=s.masp";
        if (!from.isEmpty() && !to.isEmpty()) sql += " WHERE h.ngaytao BETWEEN '" + from + "' AND '" + to + "'";
        sql += " GROUP BY c.masp, s.tensp ORDER BY doanhthu DESC LIMIT 10";
        try (Connection c = Connect.getConnection(); PreparedStatement pst = c.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) topProductModel.addRow(new Object[] { rs.getString("masp"), rs.getString("tensp"), rs.getLong("doanhthu") });
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void loadTopCategories(String from, String to) {
        topCategoryModel.setRowCount(0);
        String sql = "SELECT d.maloai, d.tenloai, SUM(c.thanhtien) AS doanhthu FROM cthoadon c JOIN hoadon h ON c.mahd=h.mahd JOIN sanpham s ON c.masp=s.masp JOIN danhmuc d ON s.maloaisp=d.maloai";
        if (!from.isEmpty() && !to.isEmpty()) sql += " WHERE h.ngaytao BETWEEN '" + from + "' AND '" + to + "'";
        sql += " GROUP BY d.maloai, d.tenloai ORDER BY doanhthu DESC LIMIT 10";
        try (Connection c = Connect.getConnection(); PreparedStatement pst = c.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) topCategoryModel.addRow(new Object[] { rs.getString("maloai"), rs.getString("tenloai"), rs.getLong("doanhthu") });
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void loadDetails(String from, String to) {
        detailModel.setRowCount(0);
        String sql = "SELECT mahd, ngaytao, makh, manv, tongtien FROM hoadon";
        if (!from.isEmpty() && !to.isEmpty()) sql += " WHERE ngaytao BETWEEN '" + from + "' AND '" + to + "'";
        sql += " ORDER BY ngaytao DESC";
        try (Connection c = Connect.getConnection(); PreparedStatement pst = c.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) detailModel.addRow(new Object[] { rs.getString("mahd"), rs.getString("ngaytao"), rs.getString("makh"), rs.getString("manv"), rs.getLong("tongtien") });
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void initDateRangeFromInvoices() {
        String sql = "SELECT MIN(ngaytao) AS from_date, MAX(ngaytao) AS to_date FROM hoadon";
        try (Connection c = Connect.getConnection();
             PreparedStatement pst = c.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                Date fromDate = parseDate(rs.getString("from_date"));
                Date toDate = parseDate(rs.getString("to_date"));
                if (fromDate != null) {
                    spFrom.setValue(fromDate);
                }
                if (toDate != null) {
                    spTo.setValue(toDate);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Date parseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return dateFmt.parse(value);
        } catch (ParseException ex) {
            return null;
        }
    }

    private void exportTimeSeriesCsv() {
        if (chartLabels.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất.", "Export", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Lưu CSV");
        int res = chooser.showSaveDialog(this);
        if (res != JFileChooser.APPROVE_OPTION) return;
        File f = chooser.getSelectedFile();
        try (BufferedWriter w = new BufferedWriter(new FileWriter(f))) {
            w.write("period,doanhthu\n");
            for (int i = 0; i < chartLabels.size(); i++) {
                w.write(String.format("%s,%d\n", chartLabels.get(i), chartValues.get(i)));
            }
            javax.swing.JOptionPane.showMessageDialog(this, "Xuất CSV thành công.", "Export", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi xuất CSV: " + ex.getMessage(), "Export", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

}
