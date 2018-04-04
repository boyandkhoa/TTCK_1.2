package vision.google.com.qrcodescanner.function;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Khoa Tran on 10-01-2018.
 */

public class Sell_Function {

    public boolean CheckInfo(String Customer, String PhoneCustomer, String AddressCustomer) {
        if (Customer.trim().length() <= 0 || PhoneCustomer.matches("") || AddressCustomer.matches("")) {
            return false;
        } else {
            return true;
        }
    }
}
