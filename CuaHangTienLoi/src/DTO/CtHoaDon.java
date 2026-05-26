/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author votru
 */
public class CtHoaDon {
    private String mahd;
    private String masp;
    private int soluong;
    private int dongia;
    private int thanhtien;

    public CtHoaDon(String mahd, String masp, int soluong, int dongia, int thanhtien) {
        this.mahd = mahd;
        this.masp = masp;
        this.soluong = soluong;
        this.dongia = dongia;
        this.thanhtien = thanhtien;
    }

    public String getMahd() {
        return mahd;
    }

    public String getMasp() {
        return masp;
    }

    public int getSoluong() {
        return soluong;
    }

    public int getDongia() {
        return dongia;
    }

    public int getThanhtien() {
        return thanhtien;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }

    public void setThanhtien(int thanhtien) {
        this.thanhtien = thanhtien;
    }

    @Override
    public String toString() {
        return "CtHoaDon{" + "mahd=" + mahd + ", masp=" + masp + ", soluong=" + soluong + ", dongia=" + dongia + ", thanhtien=" + thanhtien + '}';
    }
    
    
    
}
