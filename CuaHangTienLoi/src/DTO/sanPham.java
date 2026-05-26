/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author votru
 */
public class sanPham {
    private String masp;
    private String tensp;
    private int soluong;
    private String maloaisp;
    private String mancc;
    private long dongia;
    private String img;

    public String getTensp() {
        return tensp;
    }

    public int getSoluong() {
        return soluong;
    }
    
    public String getMaloaisp() {
        return maloaisp;
    }

    public String getMancc() {
        return mancc;
    }

    public long getDongia() {
        return dongia;
    }

    public String getImg() {
        return img;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
    
    public void setMaloaisp(String maloaisp) {
        this.maloaisp = maloaisp;
    }

    public void setMancc(String mancc) {
        this.mancc = mancc;
    }

    public void setImg(String img) {
        this.img = img;
    }

    
    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public void setDongia(long dongia) {
        this.dongia = dongia;
    }

    public sanPham() {
    }
    
    public sanPham(String masp, String tensp, String maloaisp, int soluong, long dongia, String img) {
        this(masp, tensp, maloaisp, null, soluong, dongia, img);
    }

    public sanPham(String masp, String tensp, String maloaisp, String mancc, int soluong, long dongia, String img) {
        this.masp = masp;
        this.tensp = tensp;
        this.soluong = soluong;
        this.maloaisp = maloaisp;
        this.mancc = mancc;
        this.dongia = dongia;
        this.img = img;
    }
    
    @Override
    public String toString() {
        return "sanPham{" + "masp=" + masp + ", tensp=" + tensp + ", soluong=" + soluong + ", maloaisp=" + maloaisp + ", mancc=" + mancc + ", dongia=" + dongia + ", img=" + img + '}';
    }

    
}
