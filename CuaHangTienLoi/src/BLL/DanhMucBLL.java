/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;


import java.util.ArrayList;

import javax.swing.JOptionPane;

import DAL.DALDanhMuc;
import GUI.DanhMuc;


public class DanhMucBLL implements BLLinterface<DTO.DanhMuc>{
    
    private DanhMuc dmView;
    private DALDanhMuc daldm = new DALDanhMuc();
    private ArrayList<DTO.DanhMuc> list = new ArrayList<>();

    public DanhMucBLL(DanhMuc dm) {
        this.dmView = dm;
    }

    public DanhMucBLL() {
     
    }
    
    public ArrayList<DTO.DanhMuc> getALL() {
        return daldm.selectAll();
    }
   
    @Override
    public void add(DTO.DanhMuc dm) {
        list = daldm.selectAll();
        boolean flag = false;
        for (DTO.DanhMuc tmp : list) {
            if (tmp.getMaloai().equals(dm.getMaloai())) {
                flag = true;
            }
        }
        if (flag) {
            JOptionPane.showMessageDialog(dmView, "Mã loại đã tồn tại");
            return;
        }
        try {
            if (daldm.insert(dm) > 0) {
                JOptionPane.showMessageDialog(dmView, "Thêm thành công");
            } else {
                JOptionPane.showMessageDialog(dmView, "Thêm thất bại");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    @Override
    public void delete(DTO.DanhMuc dm) {
         try {
             if (daldm.delete(dm) > 0) {
                JOptionPane.showMessageDialog(dmView, "Xóa thành công");
             } else {
                 JOptionPane.showMessageDialog(dmView, "Xóa thất bại");
             }
         } catch (Exception ex) {
             System.out.println(ex);
         }
    }
    
    @Override
    public void update(DTO.DanhMuc dm, String maOld) {
         list = daldm.selectAll();
        boolean found = false;
        if (!dm.getMaloai().equals(maOld)) {
            for (DTO.DanhMuc tmp : list) {
                if (tmp.getMaloai().equals(dm.getMaloai())) {
                    found = true;
                    break;
                }
            }
        }
        if (found) {
            JOptionPane.showMessageDialog(dmView, "Mã loại đã tồn tại");
        } else {
            try {
                if (daldm.updateALL(dm, maOld) > 0) {
                    JOptionPane.showMessageDialog(dmView, "Sửa thành công");
                } else {
                    JOptionPane.showMessageDialog(dmView, "Sửa thất bại");
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public void update(DTO.DanhMuc dm) {
        try {
            if (daldm.update(dm) > 0) {
                JOptionPane.showMessageDialog(dmView, "Sửa thành công");
            } else {
                JOptionPane.showMessageDialog(dmView, "Sửa thất bại");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
}
