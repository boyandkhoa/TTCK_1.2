package vision.google.com.qrcodescanner;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import vision.google.com.qrcodescanner.function.Add_Function;
import vision.google.com.qrcodescanner.function.All_Function;
import vision.google.com.qrcodescanner.function.CheckInternet;
import vision.google.com.qrcodescanner.function.ClassAddPhone;

public class AddActivity extends AppCompatActivity {
    DatabaseReference infoPhone = FirebaseDatabase.getInstance().getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Button scanbtn, submit;
    TextView result, txtTitle;
    EditText name, imei, giaban, baohanh;
    Spinner spinnerLoai, spinnerHang;
    String LoaiPhone = "", HangDT = "";
    ImageView imgHinh;
    int REQUEST_CODE_IMGHinh = 1;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    Add_Function add_function = new Add_Function();
    All_Function all_function = new All_Function();
    CheckInternet checkInternet = new CheckInternet();
    final ArrayList<String> Hang = new ArrayList<String>();
    final ArrayList<String> Loai = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add);
        AnhXa();
        setSpinner();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://phuongnammobile-8106e.appspot.com");


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkInternet.isNetworkConnected(AddActivity.this) == false) {
                    checkInternet.ShowFail(AddActivity.this);
                } else {
                    if (add_function.CheckInfo(name.getText().toString(), imei.getText().toString(), giaban.getText().toString(), baohanh.getText().toString())) {
                        final Calendar calendar = Calendar.getInstance();
                        final String NgayNhap = (String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/") + String.valueOf(calendar.get(Calendar.YEAR));
                        final SharedPreferences prefs = getSharedPreferences("infoUser", Context.MODE_PRIVATE);
                        final Dialog dialog = new Dialog(AddActivity.this);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.info_upload);
                        TextView txtTen = (TextView) dialog.findViewById(R.id.txtTen);
                        TextView txtImei = (TextView) dialog.findViewById(R.id.txtImei);
                        TextView txtLoai = (TextView) dialog.findViewById(R.id.txtLoai);
                        TextView txtGia = (TextView) dialog.findViewById(R.id.txtGia);
                        TextView txtBaoHanh = (TextView) dialog.findViewById(R.id.txtBaoHanh);
                        TextView txtNguoiNhap = (TextView) dialog.findViewById(R.id.txtNguoiNhap);
                        TextView txtNgayNhap = (TextView) dialog.findViewById(R.id.txtNgayNhap);
                        Button btLuu = (Button) dialog.findViewById(R.id.btnPost);
                        Button btSua = (Button) dialog.findViewById(R.id.btnSua);
                        txtTen.setText("Tên: " + HangDT + " " + name.getText());
                        txtImei.setText("IMEI: " + imei.getText());
                        txtLoai.setText("Loại: " + LoaiPhone);
                        txtGia.setText("Giá: " + giaban.getText());
                        txtBaoHanh.setText("Bảo hành: " + baohanh.getText() + " tháng");
                        txtNguoiNhap.setText("Người nhập: " + prefs.getString("name", ""));
                        txtNgayNhap.setText("Ngày nhập: " + NgayNhap);
                        btLuu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                                final Dialog uploading = new
                                        Dialog(AddActivity.this);
                                uploading.setCancelable(false);
                                uploading.setContentView(R.layout.uploading);
                                uploading.show();
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                Query query = reference
                                        .child("Kho")
                                        .child("Kho")
                                        .orderByChild("Imei")
                                        .equalTo(imei.getText().toString());
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            // dataSnapshot is the "issue" node with all children with id 0
                                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                                ;// do something with the individual "issues"
                                            }
                                            uploading.cancel();
                                            Toast.makeText(AddActivity.this, "Imei đã tồn tại", Toast.LENGTH_SHORT).show();
                                        } else {
                                            StorageReference mountainsRef = storageRef.child("img" + calendar.getTimeInMillis() + "png");//
                                            // Get the data from an ImageView as bytes
                                            imgHinh.setDrawingCacheEnabled(true);
                                            imgHinh.buildDrawingCache();
                                            Bitmap bitmap = imgHinh.getDrawingCache();
                                            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                            byte[] data = baos.toByteArray();
                                            UploadTask uploadTask = mountainsRef.putBytes(data);
                                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {
                                                    // Handle unsuccessful uploads
                                                    Toast.makeText(AddActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                                            Toast.makeText(AddActivity.this, "Luu hinh thanh cong", Toast.LENGTH_SHORT).show();
                                                    ClassAddPhone classAddPhone = new ClassAddPhone(imei.getText().toString(),
                                                            HangDT.toUpperCase() + " " + name.getText().toString().toUpperCase(),
                                                            LoaiPhone,
                                                            giaban.getText().toString(),
                                                            prefs.getString("name", ""),
                                                            NgayNhap,
                                                            String.valueOf(downloadUrl),
                                                            baohanh.getText().toString());
                                                    infoPhone.child("Kho").child("Kho").push().setValue(classAddPhone, new DatabaseReference.CompletionListener() {
                                                        @Override
                                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                            if (databaseError == null) {
                                                                uploading.cancel();
                                                                Intent myIntent = new Intent(AddActivity.this, AddActivity.class);
                                                                startActivity(myIntent);
                                                                finish();
                                                                Toast.makeText(AddActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();


                                                            } else {
                                                                uploading.cancel();
                                                                Intent myIntent = new Intent(AddActivity.this, AddActivity.class);
                                                                startActivity(myIntent);
                                                                finish();
                                                                Toast.makeText(AddActivity.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();

                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {


                                    }
                                });


                            }
                        });
                        btSua.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    } else {
                        Toast.makeText(AddActivity.this, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_IMGHinh);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                result.post(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(barcode.displayValue);
                    }
                });
            }
        }
        if (requestCode == REQUEST_CODE_IMGHinh && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.exit(1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void AnhXa() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
//        txtTitle.setTypeface(Typeface.createFromAsset(getAssets(),"VINHAN.TTF"));
        name = (EditText) findViewById(R.id.Name);
        imei = (EditText) findViewById(R.id.result);
        giaban = (EditText) findViewById(R.id.GiaBan);
        baohanh = (EditText) findViewById(R.id.BaoHanh);
        scanbtn = (Button) findViewById(R.id.scanbtn);
        submit = (Button) findViewById(R.id.submit);
        spinnerLoai = (Spinner) findViewById(R.id.spinnerLoai);
        spinnerHang = (Spinner) findViewById(R.id.spinnerHang);
        result = (TextView) findViewById(R.id.result);
        giaban.addTextChangedListener(add_function.onTextChangedListener(giaban));
        imgHinh = (ImageView) findViewById(R.id.imgHinh);
    }

    public void setSpinner() {

        Loai.add("SmartPhone");
        Loai.add("Tablet");
        Loai.add("Normal");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_item, Loai);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerLoai.setAdapter(arrayAdapter);
        spinnerLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LoaiPhone = Loai.get(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Hang.add("Acer");
        Hang.add("Apple");
        Hang.add("Asanzo");
        Hang.add("Asus");
        Hang.add("Avvio");
        Hang.add("Bavapen");
        Hang.add("BenQ");
        Hang.add("BlackBerry");
        Hang.add("BKAV");
        Hang.add("Fujitsu");
        Hang.add("FPT");
        Hang.add("Gionee");
        Hang.add("Huawei");
        Hang.add("HTC");
        Hang.add("K-Touch");
        Hang.add("Masstel");
        Hang.add("Meizu");
        Hang.add("Mobiistar");
        Hang.add("Motorola");
        Hang.add("Nokia");
        Hang.add("Oppo");
        Hang.add("Panasonic");
        Hang.add("Philips");
        Hang.add("QMobile");
        Hang.add("Sanyo");
        Hang.add("Samsung");
        Hang.add("Sony");
        Hang.add("TCL");
        Hang.add("Toshiba");
        Hang.add("Viettel");
        Hang.add("Vivo");
        Hang.add("VNPT");
        Hang.add("Xiaomi");
        Hang.add("Wiko");
        Hang.add("ZTE");

        ArrayAdapter arrayAdapterHang = new ArrayAdapter(this, R.layout.spinner_item, Hang);
        arrayAdapterHang.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerHang.setAdapter(arrayAdapterHang);
        spinnerHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HangDT = Hang.get(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
