package vision.google.com.qrcodescanner.function;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import vision.google.com.qrcodescanner.HomeActivity;
import vision.google.com.qrcodescanner.SignInActivity;
import vision.google.com.qrcodescanner.function.CheckInternet;

/**
 * Created by Khoa Tran on 10-01-2018.
 */

public class Login_Function {
    CheckInternet checkInternet = new CheckInternet();

    public void btLogin(Activity activity, Context context, View view) {
        if (checkInternet.isNetworkConnected(activity) == false) {
            Toast.makeText(activity, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
        } else {
            Intent myIntent = new Intent(view.getContext(), HomeActivity.class);
            view.getContext().startActivity(myIntent);
        }
    }

    public void txtDangKy(Activity activity, Context context, View view) {
        if (checkInternet.isNetworkConnected(activity) == false) {
            Toast.makeText(activity, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
        } else {
            Intent myIntent = new Intent(view.getContext(), SignInActivity.class);
            view.getContext().startActivity(myIntent);
        }
    }
}
