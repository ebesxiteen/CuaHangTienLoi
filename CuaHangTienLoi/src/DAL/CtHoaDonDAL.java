/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.CtHoaDon;
import Database.Connect;


/**
 *
 * @author votru
 */
public class CtHoaDonDAL implements DALinterface<CtHoaDon>{
    public static CtHoaDonDAL getintance() {
		return new CtHoaDonDAL();
}
    		

    @Override
    public int insert(CtHoaDon t) {
        int ketqua = 0;
		try {
			Connection con = Connect.getConnection();
			String sql = "INSERT INTO cthoadon (mahd, masp, soluong, dongia, thanhtien) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, t.getMahd());
			pst.setString(2, t.getMasp());
			pst.setInt(3, t.getSoluong());
			pst.setInt(4, t.getDongia());
			pst.setInt(5, t.getThanhtien());
			ketqua = pst.executeUpdate();
			Connect.closeConnection(con);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ketqua;
    }

    @Override
    public int update(CtHoaDon t) {
        int ketqua = 0;
		try {
			Connection con = Connect.getConnection();
			String sql = "UPDATE cthoadon SET mahd=?, masp=?, soluong=?, dongia=?, thanhtien=? WHERE mahd = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, t.getMahd());
			pst.setString(2, t.getMasp());
			pst.setInt(3, t.getSoluong());
			pst.setInt(4, t.getDongia());
			pst.setInt(5, t.getThanhtien());
			pst.setString(6, t.getMahd());
			
			ketqua = pst.executeUpdate();
			
			Connect.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ketqua;
    
    }

    @Override
    public int delete(CtHoaDon t) {
        int kq = 0;
		try {
			Connection c = Connect.getConnection();
			
			String sql = "DELETE FROM cthoadon WHERE mahd = ?";
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
			Connection con = Connect.getConnection();
			String sql = "DELETE FROM cthoadon WHERE mahd = ?";
			PreparedStatement pts = con.prepareStatement(sql);
			pts.setString(1, t);
			kq = pts.executeUpdate();
			Connect.closeConnection(con);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return kq;
    }

    @Override
    public ArrayList<CtHoaDon> selectAll() {
		ArrayList<CtHoaDon> ketqua = new ArrayList<CtHoaDon>();
		try {
			Connection con = Connect.getConnection();
			String sql = "SELECT * FROM cthoadon";
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String mahd = rs.getString("mahd");
				String masp = rs.getString("masp");
				int soluong = rs.getInt("soluong");
				int dongia = rs.getInt("dongia");
				int thanhtien = rs.getInt("thanhtien");
				CtHoaDon ct = new CtHoaDon(mahd, masp, soluong, dongia, thanhtien);
				ketqua.add(ct);
			}
			Connect.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ketqua;
    }

    @Override
    public CtHoaDon selectById(CtHoaDon t) {
		CtHoaDon kq = null;
		try {
			Connection con = Connect.getConnection();
			String sql = "SELECT * FROM cthoadon WHERE mahd = ? AND masp = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, t.getMahd());
			pst.setString(2, t.getMasp());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String mahd = rs.getString("mahd");
				String masp = rs.getString("masp");
				int soluong = rs.getInt("soluong");
				int dongia = rs.getInt("dongia");
				int thanhtien = rs.getInt("thanhtien");
				kq = new CtHoaDon(mahd, masp, soluong, dongia, thanhtien);
			}
			Connect.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kq;
    }

    @Override
    public CtHoaDon selectById(String T) {
		CtHoaDon kq = null;
		try {
			Connection con = Connect.getConnection();
			String sql = "SELECT * FROM cthoadon WHERE mahd = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, T);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String mahd = rs.getString("mahd");
				String masp = rs.getString("masp");
				int soluong = rs.getInt("soluong");
				int dongia = rs.getInt("dongia");
				int thanhtien = rs.getInt("thanhtien");
				kq = new CtHoaDon(mahd, masp, soluong, dongia, thanhtien);
			}
			Connect.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kq;
    }

    @Override
    public ArrayList<CtHoaDon> selectByCondition(String condition) {
		ArrayList<CtHoaDon> ketqua = new ArrayList<CtHoaDon>();
		try {
			Connection con = Connect.getConnection();
			String sql = "SELECT * FROM cthoadon WHERE " + condition;
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String mahd = rs.getString("mahd");
				String masp = rs.getString("masp");
				int soluong = rs.getInt("soluong");
				int dongia = rs.getInt("dongia");
				int thanhtien = rs.getInt("thanhtien");
				CtHoaDon ct = new CtHoaDon(mahd, masp, soluong, dongia, thanhtien);
				ketqua.add(ct);
			}
			Connect.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ketqua;
    }

    @Override
    public int updateALL(CtHoaDon t, String ma) {
        int ketqua = 0;
		try {
			Connection con = Connect.getConnection();
			String sql = "UPDATE cthoadon SET mahd=?, masp=?, soluong=?, dongia=?, thanhtien=? WHERE mahd = ? AND masp = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, t.getMahd());
			pst.setString(2, t.getMasp());
			pst.setInt(3, t.getSoluong());
			pst.setInt(4, t.getDongia());
			pst.setInt(5, t.getThanhtien());
			pst.setString(6, ma);
			pst.setString(7, t.getMasp());

			ketqua = pst.executeUpdate();
			Connect.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ketqua;
    }
    
}
    