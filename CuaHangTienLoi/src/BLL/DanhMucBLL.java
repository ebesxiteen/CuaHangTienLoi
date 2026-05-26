/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;


import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import DAL.DALDanhMuc;


public class DanhMucBLL implements BLLinterface<DTO.DanhMuc>{
    
    private Component dmView;
    private DALDanhMuc daldm = new DALDanhMuc();
    private ArrayList<DTO.DanhMuc> list = new ArrayList<>();

    public DanhMucBLL(Component dm) {
        this.dmView = dm;
    }

    public DanhMucBLL() {
     
    }

    private void showMsg(String msg) {
        if (dmView != null) {
            JOptionPane.showMessageDialog(dmView, msg);
        } else {
            JOptionPane.showMessageDialog(null, msg);
        }
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
            showMsg("Mã loại đã tồn tại");
            return;
        }
        try {
                if (daldm.insert(dm) > 0) {
                showMsg("Thêm thành công");
            } else {
                showMsg("Thêm thất bại");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    @Override
    public void delete(DTO.DanhMuc dm) {
         try {
                if (daldm.delete(dm) > 0) {
                showMsg("Xóa thành công");
             } else {
                 showMsg("Xóa thất bại");
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
            showMsg("Mã loại đã tồn tại");
        } else {
            try {
                if (daldm.updateALL(dm, maOld) > 0) {
                    showMsg("Sửa thành công");
                } else {
                    showMsg("Sửa thất bại");
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public void update(DTO.DanhMuc dm) {
        try {
            if (daldm.update(dm) > 0) {
                showMsg("Sửa thành công");
            } else {
                showMsg("Sửa thất bại");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
}
