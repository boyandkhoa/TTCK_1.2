package vision.google.com.qrcodescanner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.drive.events.CompletionListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    TextView txtUserName, txtName, txtPhone, txtActive, txtAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        AnhXa();
        ShowInfo();

    }

    public void AnhXa() {
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtName = (TextView) findViewById(R.id.txtName);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtActive = (TextView) findViewById(R.id.txtActive);
        txtAdmin = (TextView) findViewById(R.id.txtAdmin);
    }

    public void ShowInfo() {
        SharedPreferences prefs = this.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        txtUserName.setText("Username:  " + prefs.getString("username", ""));
        txtName.setText("Họ tên:  " + prefs.getString("name", ""));
        txtPhone.setText("Phone:  " + prefs.getString("phone", ""));
        if (prefs.getInt("admin", 0) == 0)
            txtAdmin.setText("Chức vụ:  Nhân viên");
        else
            txtAdmin.setText("Chức vụ:  Admin");

    }

    public void btChangePassword(View view) {
        final SharedPreferences prefs = getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        final Dialog dialog = new
                Dialog(this);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.changepassword);
        final EditText edtMatKhauCu = (EditText) dialog.findViewById(R.id.edtMatKhauCu);
        final EditText edtMatKhauMoi = (EditText) dialog.findViewById(R.id.edtMatKhauMoi);
        final EditText edtMatKhauMoi2 = (EditText) dialog.findViewById(R.id.edtMatKhauMoi2);
        Button btLuu = (Button) dialog.findViewById(R.id.btnPost);
        Button btSua = (Button) dialog.findViewById(R.id.btnSua);
        dialog.show();
        btLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtMatKhauCu.getText().toString().equals(prefs.getString("password", "DEFAULT"))) {
                    if (edtMatKhauMoi.getText().toString().equals(edtMatKhauMoi2.getText().toString()) == false) {
                        edtMatKhauMoi2.setError("Xác nhận chưa đúng");
                    } else {
                        if (edtMatKhauMoi.getText().toString().trim().length() < 6) {
                            edtMatKhauMoi.setError("Mật khẩu ít nhất 6 ký tự");
                        } else {
                            final Dialog uploading = new
                                    Dialog(ProfileActivity.this);
                            uploading.setCancelable(false);
                            uploading.setContentView(R.layout.uploading);
                            uploading.show();
                            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            reference.child("User").child(prefs.getString("keyuser", "DEFAULT")).child("PassWord").setValue(edtMatKhauMoi.getText().toString(),new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError == null){
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString("username", "");
                                        editor.commit();
                                        uploading.cancel();
                                        Toast.makeText(ProfileActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                        Intent myIntent = new Intent(ProfileActivity.this, Login.class);
                                        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(myIntent);
                                        finish();
                                    }
                                    else{
                                        uploading.cancel();
                                        Toast.makeText(ProfileActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                } else {
                    edtMatKhauCu.setError("Mật khẩu củ khống đúng");
//                    Toast.makeText(ProfileActivity.this, "Mật khẩu củ không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }


    public void btChangePhoneNumber(View view) {
        final SharedPreferences prefs = getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        final Dialog dialog = new
                Dialog(this);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.changephonenumber);
        final EditText edtMatKhau = (EditText) dialog.findViewById(R.id.edtMatKhau);
        final EditText edtNewPhone = (EditText) dialog.findViewById(R.id.edtNewPhone);
        Button btLuu = (Button) dialog.findViewById(R.id.btnPost);
        Button btSua = (Button) dialog.findViewById(R.id.btnSua);
        dialog.show();
        btLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtMatKhau.getText().toString().equals(prefs.getString("password", "DEFAULT"))) {
                        if (edtNewPhone.getText().toString().trim().length() < 10) {
                            edtNewPhone.setError("Số điện thoại sai");
                        } else {
                            final Dialog uploading = new
                                    Dialog(ProfileActivity.this);
                            uploading.setCancelable(false);
                            uploading.setContentView(R.layout.uploading);
                            uploading.show();
                            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            reference.child("User").child(prefs.getString("keyuser", "DEFAULT")).child("NumberPhone").setValue(edtNewPhone.getText().toString(),new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError == null){
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString("phone", edtNewPhone.getText().toString());
                                        editor.commit();
                                        uploading.cancel();
                                        Toast.makeText(ProfileActivity.this, "Đổi số điện thoại thành công", Toast.LENGTH_SHORT).show();
                                        Intent myIntent = new Intent(ProfileActivity.this, ProfileActivity.class);
                                        startActivity(myIntent);
                                        finish();
                                    }
                                    else{
                                        uploading.cancel();
                                        Toast.makeText(ProfileActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                } else {
                    edtMatKhau.setError("Mật khẩu củ khống đúng");
//                    Toast.makeText(ProfileActivity.this, "Mật khẩu củ không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

}
