package vision.google.com.qrcodescanner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import vision.google.com.qrcodescanner.function.All_Function;
import vision.google.com.qrcodescanner.function.CheckInternet;
import vision.google.com.qrcodescanner.function.Home_Function;

public class HomeActivity extends AppCompatActivity {

    private int currentApiVersion;
    CheckInternet check = new CheckInternet();
    Home_Function home_function = new Home_Function();
    All_Function all_function = new All_Function();

    @Override
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentApiVersion = android.os.Build.VERSION.SDK_INT;
        setContentView(R.layout.activity_home);
        home_function.onKeyDown(1, this);
        all_function.CheckActive(this);

    }

//    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            switch (which) {
//                case DialogInterface.BUTTON_POSITIVE:
//                    //Yes button clicked
//                    System.exit(1);
//                    break;
//                case DialogInterface.BUTTON_NEGATIVE:
//                    //No button clicked
//                    break;
//            }
//        }
//    };
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
//            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//            builder.setTitle("Exit")
//                    .setMessage("Bạn có chắc muốn thoát?")
//                    .setPositiveButton("Yes", dialogClickListener)
//                    .setNegativeButton("No", dialogClickListener).show();
//
//            return true;
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    public void OpenAddPhoneActivity(View view) {
        home_function.OpenAddPhoneActivity(view);
    }

    public void OpenSellPhoneActivity(View view) {
        home_function.OpenSellPhoneActivity(view);
    }

    public void OpenViewActivity(View view) {
        home_function.OpenViewActivity(view);
    }

    public void OpenProfileActivity(View view) {
        home_function.OpenProfileActivity(view);
    }

    public void OpenManagerEmployeeActivity(View view) {
        home_function.OpenManagerEmployeeActivity(HomeActivity.this);
    }

    public void Logout(View view) {
        home_function.Logout(HomeActivity.this);
    }

}
