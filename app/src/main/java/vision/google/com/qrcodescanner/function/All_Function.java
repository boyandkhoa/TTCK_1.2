package vision.google.com.qrcodescanner.function;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vision.google.com.qrcodescanner.Login;
import vision.google.com.qrcodescanner.ProfileActivity;

/**
 * Created by Khoa Tran on 10-01-2018.
 */

public class All_Function {
    final DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();

    public void CheckActive(final Activity activity) {
        final Handler handler = new Handler();
        final int delay = 5000; //milliseconds
        final SharedPreferences prefs = activity.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        final boolean[] stop = {false};

        handler.postDelayed(new Runnable() {
            public void run() {
                //do something
                DatabaseReference active= firebase.child("User")
                    .child(prefs.getString("keyuser", ""))
                    .child("Active");
                active.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        if (value.equals("0")){
                            stop[0] = true;
                            SharedPreferences prefs = activity.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("username", "");
                            editor.commit();
                            Toast.makeText(activity, "Tài khoản của bạn đã bị khóa", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(activity, Login.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(myIntent);
                            activity.finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                if (!stop[0]) {
                handler.postDelayed(this, delay);
                }
            }
        },0);
    }


}
