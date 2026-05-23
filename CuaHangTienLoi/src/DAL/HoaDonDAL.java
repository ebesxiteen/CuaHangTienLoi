/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.HoaDon;
import Database.Connect;

/**
 *
 * @author votru
 */
public class HoaDonDAL implements DALinterface<HoaDon>{
    public static HoaDonDAL getintance() {
		return new HoaDonDAL();
}

    @Override
    public int insert(HoaDon t) {
        
        int ketqua = 0;
		try {
			Connection con = Connect.getConnection();
            String sql = "INSERT INTO hoadon (mahd, makh, manv, ngaytao, tongtien) VALUES (?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMahd());
            pst.setString(2, t.getMakh());
            pst.setString(3, t.getManv());
            pst.setString(4, t.getNgaytao());
            pst.setInt(5, t.getTongtien());
			ketqua = pst.executeUpdate();
			Connect.closeConnection(con);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ketqua;
    }

    @Override
    public int update(HoaDon t) {
        
        int ketqua = 0;
		try {
			Connection con = Connect.getConnection();
            String sql = "UPDATE hoadon " +
                        "SET mahd=?, makh=?, manv=?, ngaytao=?, tongtien=? " +
                        "WHERE mahd = ?";
			PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMahd());
			pst.setString(2, t.getMakh());
			pst.setString(3, t.getManv());
			pst.setString(4, t.getNgaytao());
            pst.setInt(5, t.getTongtien());
            pst.setString(6, t.getMahd());
			
			ketqua = pst.executeUpdate();
			
			Connect.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ketqua;
    }

    @Override
    public int delete(HoaDon t) {
        
        int kq = 0;
		try {
			Connection c = Connect.getConnection();
			
            String sql = "DELETE FROM hoadon WHERE mahd = ?";
            PreparedStatement pts = c.prepareStatement(sql);
            pts.setString(1, t.getMahd());
			kq = pts.executeUpdate();
			
			Connect.closeConnection(c);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kq;
    }

    @Override
    public int delete(String t) {
        int kq = 0;
        try {
            Connection c = Connect.getConnection();
            String sql = "DELETE FROM hoadon WHERE mahd = ?";
            PreparedStatement pts = c.prepareStatement(sql);
            pts.setString(1, t);
            kq = pts.executeUpdate();
            Connect.closeConnection(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public ArrayList<HoaDon> selectAll() {
        ArrayList<HoaDon> ketqua = new ArrayList<HoaDon>();
        try {
//		BƯỚC 1: TẠO KẾT NỐI ĐẾN CSDL
            Connection con = Connect.getConnection();
//		BƯỚC 2: TẠO RA ĐỐI TƯỢNG STATEMENT
            String sql = "SELECT * FROM hoadon";
            PreparedStatement pst = con.prepareStatement(sql);
//		BƯỚC 3: THỰC THI CÂU LỆNH SQL
//		System.out.println(sql);
            ResultSet rs = pst.executeQuery();
            // BƯỚC 4 XỬ LÝ KẾT QUẢ
//		String manhacungcap, String tennhacungcap, String diachi, String sdt, String email
            while (rs.next()) {
                String mhd = rs.getString("mahd");
                String mkh = rs.getString("makh");
                String mnv = rs.getString("manv");
                String ngaytao = rs.getString("ngaytao");
                int tt = rs.getInt("tongtien");

                HoaDon hd = new HoaDon(mhd, mkh, mnv, ngaytao, tt);
                ketqua.add(hd);
            }
//		BƯỚC 5: NGẮT KẾT NỐI
            Connect.closeConnection(con);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ketqua;

    }

    // Generate next invoice id like hd001, hd002 ... based on existing records
    public String getNextMaHD() {
        String next = "hd001";
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT MAX(CAST(SUBSTRING(mahd,3) AS UNSIGNED)) AS maxn FROM hoadon";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int maxn = 0;
            if (rs.next()) {
                maxn = rs.getInt("maxn");
            }
            int newn = maxn + 1;
            next = String.format("hd%03d", newn);
            Connect.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return next;
    }

    @Override
    public HoaDon selectById(HoaDon t) {
        
        
		
		HoaDon kq  = new HoaDon();
		try {
			Connection con = Connect.getConnection();
            String sql = "select * from hoadon where mahd = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMahd());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
                                String mhd = rs.getString("mahd");
                                String mkh = rs.getString("makh");
                                String mnv = rs.getString("manv");
                                String ngaytao = rs.getString("ngaytao");
                                int tt = rs.getInt("tongtien");
            kq = new HoaDon(mhd, mkh, mnv, ngaytao, tt);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kq;
        }

    @Override
    public HoaDon selectById(String T) {
        HoaDon kq = null;
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM hoadon WHERE mahd = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, T);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String mhd = rs.getString("mahd");
                String mkh = rs.getString("makh");
                String mnv = rs.getString("manv");
                String ngaytao = rs.getString("ngaytao");
                int tt = rs.getInt("tongtien");
                kq = new HoaDon(mhd, mkh, mnv, ngaytao, tt);
            }
            Connect.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public ArrayList<HoaDon> selectByCondition(String condition) {
        ArrayList<HoaDon> ketqua = new ArrayList<HoaDon>();
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM hoadon WHERE " + condition;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String mhd = rs.getString("mahd");
                String mkh = rs.getString("makh");
                String mnv = rs.getString("manv");
                String ngaytao = rs.getString("ngaytao");
                int tt = rs.getInt("tongtien");
                ketqua.add(new HoaDon(mhd, mkh, mnv, ngaytao, tt));
            }
            Connect.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketqua;
    }

    @Override
    public int updateALL(HoaDon t, String ma) {
        int ketqua = 0;
        try {
            Connection con = Connect.getConnection();
            String sql = "UPDATE hoadon SET mahd=?, makh=?, manv=?, ngaytao=?, tongtien=? WHERE mahd = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMahd());
            pst.setString(2, t.getMakh());
            pst.setString(3, t.getManv());
            pst.setString(4, t.getNgaytao());
            pst.setInt(5, t.getTongtien());
            pst.setString(6, ma);
            ketqua = pst.executeUpdate();
            Connect.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketqua;
    }
}
