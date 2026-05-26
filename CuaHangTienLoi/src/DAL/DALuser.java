
package DAL;

import DTO.khachHang;
import DTO.user;
import Database.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DALuser implements DALinterface<user>{
    
    public static DALuser getinstance() {
	return new DALuser();
    }
    
    public int checkStatus(String uname, String pass) {
        int result = -1;
        try {
            Connection conn = Connect.getConnection();
            String sql = "SELECT * FROM login;";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int status = rs.getInt("status");

                if (username.equals(uname) && password.equals(pass)) {
                    result = status;
                }
            }
            Connect.closeConnection(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    

    @Override
    public int insert(user t) {
       int count = 0;
        try {
            Connection conn = Connect.getConnection();
            String sql = "INSERT INTO login (username,password,status) "
                    + " VALUES (?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, t.getUsername());
            pst.setString(2, t.getPassword());
            pst.setInt(3, t.getStatus());

            count = pst.executeUpdate();

            Connect.closeConnection(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int update(user t) {
 
        return 0;
 
    }

    @Override
    public int delete(user t) {
      
        return 0;
      
    }

    @Override
    public int delete(String t) {
        int count = 0;
        try {
            Connection conn = Connect.getConnection();
            String sql = "DELETE FROM login WHERE username = ?";
            PreparedStatement pts = conn.prepareStatement(sql);
            pts.setString(1,t);
            count = pts.executeUpdate();
            Connect.closeConnection(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public ArrayList<user> selectAll() {
       ArrayList<user> users = new ArrayList<>();
        try {
            Connection conn = Connect.getConnection();
            String sql = "select * from login";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int status = rs.getInt("status");
                user user = new user(username, password, status) {};
                users.add(user);
            }
            Connect.closeConnection(conn);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return users;
    }

    @Override
    public user selectById(user t) {
      user user = new user() {};
        try {
            Connection con = Connect.getConnection();
            String sql = "select * from login where username = ? and password = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getUsername());
            pst.setString(2, t.getPassword());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int status = rs.getInt("status");
                user = new user(username, password, status) {};
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public user selectById(String T) {
        
        return null;
        
    }

    @Override
    public ArrayList<user> selectByCondition(String condition) {
       
        return null;
       
    }

    @Override
    public int updateALL(user t, String maOld) {
       int kq = 0;
        try {
            Connection con = Connect.getConnection();
            String sql = "UPDATE login "
                    + "SET "
                    + "username=? "
                    + ",password=? "
                    + ",status=? "
                    + "WHERE username = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getUsername());
            pst.setString(2, t.getPassword());
            pst.setInt(3, t.getStatus());
            pst.setString(4, maOld);

            kq = pst.executeUpdate();

            Connect.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kq;
    }
    
}
