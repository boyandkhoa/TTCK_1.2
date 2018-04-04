package vision.google.com.qrcodescanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import vision.google.com.qrcodescanner.adapter.HinhAnhAdapter;
import vision.google.com.qrcodescanner.adapter.HinhAnhAdapter2;
import vision.google.com.qrcodescanner.function.ClassAddPhone;
import vision.google.com.qrcodescanner.function.ClassSellPhone;

public class ViewActivity extends AppCompatActivity {
    DatabaseReference infoPhone = FirebaseDatabase.getInstance().getReference();
    ListView lvHinhAnh;
    ArrayList<ClassAddPhone> mangHinhAnh;
    ArrayList<ClassSellPhone> mangHinhAnh2;
    HinhAnhAdapter adapter = null;
    HinhAnhAdapter2 adapter2 = null;
    Button btnFilter;
    String string, AES = "AES", Ngay, Thang, Nam;
    Spinner spinner, spinnerNgay, spinnerThang, spinnerNam;
    final ArrayList<String> arrayNgay = new ArrayList<String>();
    final ArrayList<String> arrayThang = new ArrayList<String>();
    final ArrayList<String> arrayNam = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        AnhXa();
        setSpiner();
        final ArrayList<String> Loai = new ArrayList<String>();
        Loai.add("Kho");
        Loai.add("Đã bán");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spiner_item2, Loai);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(AddActivity.this,Loai.get(i), Toast.LENGTH_SHORT).show();
                String a = Loai.get(i).toString();
                if (a == "Kho") {
                    LoadData();
                } else {
                    LoadData2();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        lvHinhAnh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String s = (AddPhone[i]).toString();
//                Toast.makeText(ViewActivity.this,s, Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(ViewActivity.this,ManagerEmployeeActivity.class);
////                startActivity(intent);
//            }
//        });

    }

    private void LoadData() {
        mangHinhAnh = new ArrayList<ClassAddPhone>();
        adapter = new HinhAnhAdapter(this, R.layout.dong_hinh_anh, mangHinhAnh);
        lvHinhAnh.setAdapter(adapter);
        infoPhone.child("Kho").child("Kho").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ClassAddPhone classAddPhone = dataSnapshot.getValue(ClassAddPhone.class);
//                Toast.makeText(ViewActivity.this, classAddPhone.Imei, Toast.LENGTH_LONG).show();
                mangHinhAnh.add(new ClassAddPhone(classAddPhone.Imei, classAddPhone.Ten, classAddPhone.Loai, classAddPhone.GiaBan, classAddPhone.NguoiNhap, classAddPhone.NgayNhap, classAddPhone.LinkHinh, classAddPhone.BaoHanh));
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

    private void LoadData2() {
        mangHinhAnh2 = new ArrayList<ClassSellPhone>();
        adapter2 = new HinhAnhAdapter2(this, R.layout.dong_hinh_anh2, mangHinhAnh2);
        lvHinhAnh.setAdapter(adapter2);
        infoPhone.child("Kho").child("Ban").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ClassSellPhone classAddPhone = dataSnapshot.getValue(ClassSellPhone.class);
                mangHinhAnh2.add(new ClassSellPhone(classAddPhone.Imei, classAddPhone.Ten, classAddPhone.Loai, classAddPhone.GiaBan, classAddPhone.NguoiBan, classAddPhone.NgayBan, classAddPhone.LinkHinh, classAddPhone.BaoHanh, classAddPhone.NguoiMua, classAddPhone.SDTNguoiMua));
                adapter2.notifyDataSetChanged();
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

    public void AnhXa() {
        btnFilter = (Button) findViewById(R.id.btnFilter);
        lvHinhAnh = (ListView) findViewById(R.id.lisviewHinhAnh);
        spinner = (Spinner) findViewById(R.id.spinner1);
        spinnerThang = (Spinner) findViewById(R.id.spinnerThang);
        spinnerNam = (Spinner) findViewById(R.id.spinnerNam);
        spinnerNgay = (Spinner) findViewById(R.id.spinnerNgay);
    }

    private void Filter(View view){
        Toast.makeText(this, "Filter", Toast.LENGTH_SHORT).show();
    }
    private void setSpiner() {
        for (int i = 1; i <= 31; i++) {
            arrayNgay.add(String.valueOf(i));
        }
        for (int i = 1; i <= 12; i++) {
            arrayThang.add(String.valueOf(i));
        }
        for (int i = 2018; i <= 2047; i++) {
            arrayNam.add(String.valueOf(i));
        }

//set spinerNgay
        ArrayAdapter arrayAdapterNgay = new ArrayAdapter(this, R.layout.spinner_item, arrayNgay);
        arrayAdapterNgay.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerNgay.setAdapter(arrayAdapterNgay);
        spinnerNgay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Ngay = arrayNgay.get(i).toString();
                Toast.makeText(ViewActivity.this, Ngay, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//set spinerThang

        ArrayAdapter arrayAdapterThang = new ArrayAdapter(this, R.layout.spinner_item, arrayThang);
        arrayAdapterThang.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerThang.setAdapter(arrayAdapterThang);
        spinnerThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Thang = arrayThang.get(i).toString();
                Toast.makeText(ViewActivity.this, Thang, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //set spinerNam
        ArrayAdapter arrayAdapterNam = new ArrayAdapter(this, R.layout.spinner_item, arrayNam);
        arrayAdapterNam.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerNam.setAdapter(arrayAdapterNam);
        spinnerNam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Nam = arrayNam.get(i).toString();
                Toast.makeText(ViewActivity.this, Nam, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


//    private String encrypt(String string) throws Exception {
//        SecretKeySpec key = genarateKey((string));
//        Cipher cipher = Cipher.getInstance(AES);
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//        byte[] bytes = cipher.doFinal(string.getBytes());
//        String encryptedValue = Base64.encodeToString(bytes, Base64.DEFAULT);
//        return encryptedValue;
//    }
//    private SecretKeySpec genarateKey(String string) throws Exception {
//        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] bytes = string.getBytes("UTF-8");
//        digest.update(bytes, 0, bytes.length);
//        byte[] key = digest.digest();
//        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
//        return secretKeySpec;
//    }

}
