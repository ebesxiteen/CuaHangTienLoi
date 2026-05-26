
package DAL;

import DTO.nhanVien;
import Database.Connect;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.*;

public class DALnhanVien implements DALinterface<nhanVien> {
    
    public static DALnhanVien getinstance() {
	return new DALnhanVien();
    }

    @Override
    public int insert(nhanVien t) {
        int count = 0;
        try {
            Connection conn = Connect.getConnection();
            String sql = "INSERT INTO nhanvien (manv,ho,ten,gioitinh,sdt,ngaysinh,chucvu,luong) "
                    + " VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, t.getManv());
            pst.setString(2, t.getHo());
            pst.setString(3, t.getTen());
            pst.setString(4, t.getGioitinh());
            pst.setString(5, t.getSdt());
            pst.setString(6, t.getNgaySinh());
            pst.setString(7, t.getChucVu());
            pst.setLong(8, t.getLuong());

            count = pst.executeUpdate();

            Connect.closeConnection(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int update(nhanVien t) {
        int count = 0;
        try {
            Connection con = Connect.getConnection();
            String sql = "UPDATE nhanvien "
                    + "SET "
                    + "ho=? "
                    + ",ten=? "
                    + ",gioitinh=? "
                    + ",sdt=? "
                    + ",ngaysinh=? "
                    + ",chucvu=? "
                    + ",luong=? "
                    + "WHERE manv = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getHo());
            pst.setString(2, t.getTen());
            pst.setString(3, t.getGioitinh());
            pst.setString(4, t.getSdt());
            pst.setString(5, t.getNgaySinh());
            pst.setString(6, t.getChucVu());
            pst.setLong(7, t.getLuong());
            pst.setString(8, t.getManv());

            count = pst.executeUpdate();

            Connect.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    @Override
    public int updateALL(nhanVien t, String maOld) {
        int kq = 0;
        try {
            Connection con = Connect.getConnection();
            String sql = "UPDATE nhanvien "
                    + "SET "
                    + "manv=? "
                    + ",ho=? "
                    + ",ten=? "
                    + ",gioitinh=? "
                    + ",sdt=? "
                    + ",ngaysinh=? "
                    + ",chucvu=? "
                    + ",luong=? "
                    + "WHERE manv = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getManv());
            pst.setString(2, t.getHo());
            pst.setString(3, t.getTen());
            pst.setString(4, t.getGioitinh());
            pst.setString(5, t.getSdt());
            pst.setString(6, t.getNgaySinh());
            pst.setString(7, t.getChucVu());
            pst.setLong(8, t.getLuong());
            pst.setString(9, maOld);

            kq = pst.executeUpdate();

            Connect.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public int delete(nhanVien t) {
        int count = 0;
        try {
            Connection conn = Connect.getConnection();
            String sql = "DELETE FROM nhanvien WHERE manv = ?";
            PreparedStatement pts = conn.prepareStatement(sql);
            pts.setString(1, t.getManv());
            count = pts.executeUpdate();
            Connect.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return count;
    }

    @Override
    public int delete(String t) {
        return 0;
        
    }

    @Override
    public ArrayList<nhanVien> selectAll() {
        ArrayList<nhanVien> nvs = new ArrayList<>();
        try {
            Connection conn = Connect.getConnection();
            String sql = "select * from nhanvien";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String manv = rs.getString("manv");
                String ho = rs.getString("ho");
                String ten = rs.getString("ten");
                String gioitinh = rs.getString("gioitinh");
                String sdt = rs.getString("sdt");
                String ngaysinh = rs.getString("ngaysinh");
                String chucvu = rs.getString("chucvu");
                long luong = rs.getLong("luong");
                nhanVien nv = new nhanVien(manv,ho,ten,gioitinh,sdt,ngaysinh,chucvu,luong);
                nvs.add(nv);
            }
            Connect.closeConnection(conn);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return nvs;
    }

    @Override
    public nhanVien selectById(nhanVien t) {
        nhanVien nv = new nhanVien();
        try {
            Connection con = Connect.getConnection();
            String sql = "select * from nhanvien where manv = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getManv());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String manv = rs.getString("manv");
                String ho = rs.getString("ho");
                String ten = rs.getString("ten");
                String gioitinh = rs.getString("gioitinh");
                String sdt = rs.getString("sdt");
                String ngaysinh = rs.getString("ngaysinh");
                String chucvu = rs.getString("chucvu");
                long luong = rs.getLong("luong");
                nv = new nhanVien(manv,ho,ten,gioitinh,sdt,ngaysinh,chucvu,luong);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return nv;
    }

    @Override
    public nhanVien selectById(String T) {
        return null;
    }

    @Override
    public ArrayList<nhanVien> selectByCondition(String condition) {
        return null;
    }
    
}
