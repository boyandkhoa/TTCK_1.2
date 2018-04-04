package vision.google.com.qrcodescanner.function;

import java.util.Date;

/**
 * Created by Khoa Tran on 01-01-2018.
 */

public class ClassAddPhone {
    public String Imei;
    public String Ten;
    public String Loai;
    public String GiaBan;
    public String NguoiNhap;
    public String NgayNhap;
    public String LinkHinh;
    public String BaoHanh;


    public ClassAddPhone() {
    }

    public ClassAddPhone(String imei, String ten, String loai, String giaBan, String nguoiNhap, String ngayNhap, String linkHinh, String baoHanh) {
        Imei = imei;
        Ten = ten;
        Loai = loai;
        GiaBan = giaBan;
        NguoiNhap = nguoiNhap;
        NgayNhap = ngayNhap;
        LinkHinh = linkHinh;
        BaoHanh = baoHanh;
    }
}
