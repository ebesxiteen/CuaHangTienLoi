/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author votru
 */
public class DanhMuc {
    private String maloai;
    private String tenloai;
    private String img;

    public DanhMuc(String maloai, String tenloai, String img) {
        this.maloai = maloai;
        this.tenloai = tenloai;
        this.img = img;
    }
    
    public DanhMuc(){
        
    }

    public String getMaloai() {
        return maloai;    }

    public String getTenloai() {
        return tenloai;
    }

    public String getImg() {
        return img;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "DanhMuc{" + "maloaisp=" + maloai + ", tenloai=" + tenloai + ", img=" + img + '}';
    }
    
    
    
}
