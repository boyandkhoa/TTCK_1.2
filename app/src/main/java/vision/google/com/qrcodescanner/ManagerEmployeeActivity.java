package vision.google.com.qrcodescanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import vision.google.com.qrcodescanner.adapter.NhanVienAdapter;
import vision.google.com.qrcodescanner.function.ClassAddUser;

public class ManagerEmployeeActivity extends AppCompatActivity {
    DatabaseReference infoUser = FirebaseDatabase.getInstance().getReference();
    ListView lvNhanVien;
    ArrayList<ClassAddUser> mangNhanVien;
    NhanVienAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_employee);
        AnhXa();
        LoadData();
    }

    private void AnhXa() {
        lvNhanVien = (ListView) findViewById(R.id.lisviewNhanVien);
    }

    private void LoadData() {
        mangNhanVien = new ArrayList<ClassAddUser>();
        adapter = new NhanVienAdapter(this, R.layout.dong_nhan_vien, mangNhanVien);
        lvNhanVien.setAdapter(adapter);
        infoUser.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ClassAddUser classAddUser = dataSnapshot.getValue(ClassAddUser.class);
                mangNhanVien.add(new ClassAddUser(classAddUser.UserName, classAddUser.PassWord, classAddUser.HoTen, classAddUser.NumberPhone, classAddUser.Active, classAddUser.Admin));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
