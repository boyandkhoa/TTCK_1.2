package vision.google.com.qrcodescanner;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import vision.google.com.qrcodescanner.function.CheckInternet;
import vision.google.com.qrcodescanner.function.ClassAddUser;

public class SignInActivity extends AppCompatActivity {
    DatabaseReference infoUser = FirebaseDatabase.getInstance().getReference();
    EditText edtUserName, edtPassWord, edtPrePassWord, edtName, edtPhoneNumber;
    CheckInternet checkInternet = new CheckInternet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        AnhXa();
    }


    public void btSignIn(View view) {
        if (CheckInfo()) {
            if (checkInternet.isNetworkConnected(SignInActivity.this) == false) {
                checkInternet.ShowFail(SignInActivity.this);
            } else {
                final Dialog uploading = new
                        Dialog(SignInActivity.this);
                uploading.setCancelable(false);
                uploading.setContentView(R.layout.uploading);
                uploading.show();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference
                        .child("User")
                        .orderByChild("UserName")
                        .equalTo(edtUserName.getText().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // dataSnapshot is the "issue" node with all children with id 0
                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                ;// do something with the individual "issues"
                            }
                            uploading.cancel();
                            Toast.makeText(SignInActivity.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                        } else {

//                                            Toast.makeText(AddActivity.this, "Luu hinh thanh cong", Toast.LENGTH_SHORT).show();
                            ClassAddUser classAdduser = new ClassAddUser(edtUserName.getText().toString(),
                                    edtPassWord.getText().toString(),
                                    edtName.getText().toString(),
                                    edtPhoneNumber.getText().toString(),
                                    "0",
                                    "0");
                            infoUser.child("User").push().setValue(classAdduser, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError == null) {
                                        uploading.cancel();
                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SignInActivity.this);
                                        builder.setMessage("Đã gữi thông tin đăng ký, chờ xét duyệt!").setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        }).setCancelable(false).show();
                                    } else {
                                        uploading.cancel();
                                        Toast.makeText(SignInActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }
                });
            }
        }
    }


    public void AnhXa() {
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPrePassWord = (EditText) findViewById(R.id.edtPrePassWord);
        edtPassWord = (EditText) findViewById(R.id.edtPassWord);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
    }

    public boolean CheckInfo() {
        if (edtUserName.getText().toString().trim().length() <= 0 ||
                edtPassWord.getText().toString().trim().length() <= 0 ||
                edtPrePassWord.getText().toString().trim().length() <= 0 ||
                edtName.getText().toString().trim().length() <= 0 ||
                edtPhoneNumber.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtPassWord.getText().toString().equals(edtPrePassWord.getText().toString()) == false) {
            edtPrePassWord.setError("Xác nhận mật khẩu chưa đúng");
            return false;
        } else if (edtPassWord.length() < 6) {
            edtPassWord.setError("Mật khẩu ít nhất 6 ký tự");
            return false;
        } else
            return true;
    }

}
