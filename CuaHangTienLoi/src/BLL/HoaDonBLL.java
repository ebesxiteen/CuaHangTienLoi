package BLL;

import java.util.ArrayList;

import DAL.CtHoaDonDAL;
import DAL.HoaDonDAL;
import DTO.CtHoaDon;
import DTO.HoaDon;

public class HoaDonBLL {
    private HoaDonDAL hdDal = new HoaDonDAL();
    private CtHoaDonDAL ctDal = new CtHoaDonDAL();

    public ArrayList<HoaDon> getAll() {
        return hdDal.selectAll();
    }

    public ArrayList<CtHoaDon> getCtByMa(String mahd) {
        // CtHoaDonDAL doesn't yet implement selectByCondition; use selectByCondition if available
        try {
            return ctDal.selectByCondition("mahd='" + mahd + "'");
        } catch (Exception ex) {
            // fallback: empty list
            return new ArrayList<CtHoaDon>();
        }
    }

    public boolean createHoaDon(DTO.HoaDon hd, ArrayList<CtHoaDon> details) {
        try {
            int r = hdDal.insert(hd);
            if (r <= 0) return false;
            if (details != null) {
                for (CtHoaDon c : details) {
                    ctDal.insert(c);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHoaDon(String mahd) {
        try {
            // delete details first
            CtHoaDon tmp = new CtHoaDon(mahd, "", 0, 0, 0);
            ctDal.delete(tmp);
            DTO.HoaDon hd = new DTO.HoaDon();
            hd.setMahd(mahd);
            int r = hdDal.delete(hd);
            return r > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
