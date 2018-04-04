package vision.google.com.qrcodescanner.function;

import java.text.NumberFormat;

/**
 * Created by Khoa Tran on 19-01-2018.
 */

public class ClassAddUser {
    public String UserName;
    public String PassWord;
    public String HoTen;
    public String NumberPhone;
    public String Active;
    public String Admin;

    public ClassAddUser() {
    }

    public ClassAddUser(String userName, String passWord, String hoTen, String numberPhone, String active, String admin) {
        UserName = userName;
        PassWord = passWord;
        HoTen = hoTen;
        NumberPhone = numberPhone;
        Active = active;
        Admin = admin;
    }
}
