package vision.google.com.qrcodescanner.function;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * Created by Khoa Tran on 10-01-2018.
 */

public class CheckInternet {

    public void CheckToast(Context context, Activity activity) {
        if (isNetworkConnected(context) == false) {
            ShowFailToast(activity);
        }
        else {

        }
    }
    public void Check(Context context, Activity activity) {
        if (isNetworkConnected(context) == false) {
            ShowFail(activity);
        }
        else {

        }
    }
    public void CheckFocus(Context context, Activity activity) {
        if (isNetworkConnected(context) == false) {
            ShowFailFoCus(activity);
        }
        else {

        }
    }


    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void ShowFailToast(Activity activity) {
        Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
    }
    public void ShowFail(Activity activity) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setMessage("Không có kết nối internet, vui lòng kiểm tra và thử lại sau!").setPositiveButton("Yes", dialogClickListener).show();
    }
    public void ShowFailFoCus(Activity activity) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setMessage("Không có kết nối internet, vui lòng kiểm tra và thử lại sau!").setPositiveButton("Yes", dialogClickListener).setCancelable(false).show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    System.exit(1);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };
}
