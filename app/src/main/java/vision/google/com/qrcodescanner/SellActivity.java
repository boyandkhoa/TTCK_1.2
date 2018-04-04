package vision.google.com.qrcodescanner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import vision.google.com.qrcodescanner.function.CheckInternet;
import vision.google.com.qrcodescanner.function.ClassAddPhone;
import vision.google.com.qrcodescanner.function.ClassSellPhone;

public class SellActivity extends AppCompatActivity {
    DatabaseReference infoPhone = FirebaseDatabase.getInstance().getReference();
    EditText edtImei, edtCumstomer, edtPhoneCustomer;
    TextView txtName, txtImei, txtGia, txtBaoHanh, txtLoai, txtNgayNhap, txtNguoiNhap,txtKhachHang,txtSdtKhachHang,txtDaBan;
    ImageView imgHinh;
    LinearLayout layoutShow, infoCustomer;
    public static final int REQUEST_CODE = 100;
    Button btSell2;
    CheckInternet checkInternet;
    public String Name, Imei, Gia, Loai, LinkHinh, BaoHanh, Key;
    public Integer a = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        AnhXa();

    }

    public void AnhXa() {
        edtCumstomer = (EditText) findViewById(R.id.edtCustomer);
        edtPhoneCustomer = (EditText) findViewById(R.id.edtPhoneCustomer);
        btSell2 = (Button) findViewById(R.id.btSell2);
        infoCustomer = (LinearLayout) findViewById(R.id.infoCustomer);
        layoutShow = (LinearLayout) findViewById(R.id.layoutShow);
        imgHinh = (ImageView) findViewById(R.id.imgHinh);
        edtImei = (EditText) findViewById(R.id.edtImei);
        txtName = (TextView) findViewById(R.id.txtName);
        txtImei = (TextView) findViewById(R.id.txtImei);
        txtLoai = (TextView) findViewById(R.id.txtLoai);
        txtGia = (TextView) findViewById(R.id.txtGia);
        txtBaoHanh = (TextView) findViewById(R.id.txtBaoHanh);
        txtNgayNhap = (TextView) findViewById(R.id.txtNgayNhap);
        txtNguoiNhap = (TextView) findViewById(R.id.txtNguoiNhap);
        txtKhachHang = (TextView) findViewById(R.id.txtKhachHang);
        txtSdtKhachHang = (TextView) findViewById(R.id.txtSdtKhachHang);
        txtDaBan = (TextView) findViewById(R.id.txtDaBan);
    }

    public void Search(final View view) {
        final Dialog uploading = new
                Dialog(this);
        uploading.setCancelable(false);
        uploading.setContentView(R.layout.uploading);
        uploading.show();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final Query query = reference
                .child("Kho")
                .child("Kho")
                .orderByChild("Imei")
                .equalTo(edtImei.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    uploading.cancel();
                    layoutShow.setVisibility(View.VISIBLE);
                    infoCustomer.setVisibility(View.VISIBLE);
                    txtKhachHang.setVisibility(View.GONE);
                    txtSdtKhachHang.setVisibility(View.GONE);
                    txtDaBan.setVisibility(View.GONE);
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        ClassAddPhone classAddPhone = singleSnapshot.getValue(ClassAddPhone.class);
                        Key = singleSnapshot.getKey();
                        Picasso.with(SellActivity.this).load(classAddPhone.LinkHinh).into(imgHinh);
                        txtName.setText("Tên: " + classAddPhone.Ten);
                        Name = classAddPhone.Ten;
                        txtImei.setText("Imei: " + classAddPhone.Imei);
                        Imei = classAddPhone.Imei;
                        txtLoai.setText("Loại: " + classAddPhone.Loai);
                        Loai = classAddPhone.Loai;
                        txtGia.setText("Giá: " + classAddPhone.GiaBan);
                        Gia = classAddPhone.GiaBan;
                        txtBaoHanh.setText("BH: " + classAddPhone.BaoHanh + " tháng");
                        BaoHanh = classAddPhone.BaoHanh;
                        txtNguoiNhap.setText("NV: " + classAddPhone.NguoiNhap);
                        txtNgayNhap.setText("Ngày: " + classAddPhone.NgayNhap);
                        LinkHinh = classAddPhone.LinkHinh;

                    }
                } else {
                    layoutShow.setVisibility(View.GONE);
                    final Query query = reference
                            .child("Kho")
                            .child("Ban")
                            .orderByChild("Imei")
                            .equalTo(edtImei.getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                uploading.cancel();
                                layoutShow.setVisibility(View.VISIBLE);
                                txtDaBan.setVisibility(View.VISIBLE);
                                txtKhachHang.setVisibility(View.VISIBLE);
                                txtSdtKhachHang.setVisibility(View.VISIBLE);
                                infoCustomer.setVisibility(View.GONE);
                                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                    ClassSellPhone classSellPhone = singleSnapshot.getValue(ClassSellPhone.class);
                                    Picasso.with(SellActivity.this).load(classSellPhone.LinkHinh).into(imgHinh);
                                    txtName.setText("Tên: " + classSellPhone.Ten);
                                    Name = classSellPhone.Ten;
                                    txtImei.setText("Imei: " + classSellPhone.Imei);
                                    Imei = classSellPhone.Imei;
                                    txtLoai.setText("Loại: " + classSellPhone.Loai);
                                    Loai = classSellPhone.Loai;
                                    txtGia.setText("Giá: " + classSellPhone.GiaBan);
                                    Gia = classSellPhone.GiaBan;
                                    txtNguoiNhap.setText("NV: " + classSellPhone.NguoiBan);
                                    txtNgayNhap.setText("Ngày : " + classSellPhone.NgayBan);
                                    txtKhachHang.setText("KH: " + classSellPhone.NguoiMua);
                                    txtSdtKhachHang.setText("SĐT KH: " + classSellPhone.SDTNguoiMua);
                                    LinkHinh = classSellPhone.LinkHinh;

                                }
                            } else {
                                layoutShow.setVisibility(View.GONE);
                                uploading.cancel();
                                Toast.makeText(SellActivity.this, "Không tìm thấy điện thoại", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void Scan(View view) {
        Intent intent = new Intent(SellActivity.this, ScanActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }


    public void Sell2(View view) {
//        if (checkInternet.isNetworkConnected(SellActivity.this) == false) {
//            checkInternet.ShowFail(this);
//        }
//        else {
        if (edtCumstomer.getText().toString().trim().length() > 0
                && edtPhoneCustomer.getText().toString().trim().length() > 0) {
            final Calendar calendar = Calendar.getInstance();
            final String NgayBan = (String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/") + String.valueOf(calendar.get(Calendar.YEAR));
            final SharedPreferences prefs = getSharedPreferences("infoUser", Context.MODE_PRIVATE);
            final Dialog dialog = new
                    Dialog(this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.info_sell);
            TextView txtTen = (TextView) dialog.findViewById(R.id.txtTen);
            TextView txtImei = (TextView) dialog.findViewById(R.id.txtImei);
            TextView txtLoai = (TextView) dialog.findViewById(R.id.txtLoai);
            TextView txtGia = (TextView) dialog.findViewById(R.id.txtGia);
            TextView txtBaoHanh = (TextView) dialog.findViewById(R.id.txtBaoHanh);
            TextView txtNguoiBan = (TextView) dialog.findViewById(R.id.txtNguoiBan);
            TextView txtNgayBan = (TextView) dialog.findViewById(R.id.txtNgayBan);
            TextView txtCustomer = (TextView) dialog.findViewById(R.id.txtCustomer);
            TextView txtPhoneCustomer = (TextView) dialog.findViewById(R.id.txtPhoneCustomer);
            Button btLuu = (Button) dialog.findViewById(R.id.btnPost);
            Button btSua = (Button) dialog.findViewById(R.id.btnSua);
            Button btSendSMS = (Button) dialog.findViewById(R.id.btnSendSMS);
            txtTen.setText("Tên: " + Name);
            txtImei.setText("IMEI: " + Imei);
            txtLoai.setText("Loại: " + Loai);
            txtGia.setText("Giá: " + Gia);
            txtBaoHanh.setText("BH: " + BaoHanh+ " tháng");
            txtNguoiBan.setText("NV: " + prefs.getString("name", ""));
            txtNgayBan.setText("Ngày: " + NgayBan);
            txtCustomer.setText("KH: " + edtCumstomer.getText().toString());
            txtPhoneCustomer.setText("SĐT KH: " + edtPhoneCustomer.getText().toString());
            btSendSMS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+edtPhoneCustomer.getText().toString()+";0946507010"));
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", edtPhoneCustomer.getText().toString(), null));
                    intent.putExtra("sms_body", "CỬA HÀNG ĐTDT PHƯƠNG NAM"
                            + "\n" + "Thông tin bán hàng "
                            + "\n" + "Tên ĐT: " + Name
                            + "\n" + "IMEI: " + Imei
                            + "\n" + "Giá: " + Gia
                            + "\n" + "Ngày bán: " + NgayBan
                            + "\n" + "Bảo hành: " + BaoHanh + " tháng"
                            + "\n" + "Xin cảm ơn quý khách");
                    a = 1;
                    startActivity(intent);
                }
            });

            btLuu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (a != 0) {
                        a=0;
                        dialog.cancel();
                        final Dialog uploading = new
                                Dialog(SellActivity.this);
                        uploading.setCancelable(false);
                        uploading.setContentView(R.layout.uploading);
                        uploading.show();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        Query query = reference
                                .child("Kho")
                                .child("Ban")
                                .orderByChild("Imei")
                                .equalTo(Imei);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    // dataSnapshot is the "issue" node with all children with id 0
                                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                        ;// do something with the individual "issues"
                                    }
                                    uploading.cancel();
                                    Toast.makeText(SellActivity.this, "Imei đã tồn tại", Toast.LENGTH_SHORT).show();
                                } else {
                                    ClassSellPhone classSellPhone = new ClassSellPhone(Imei,
                                            Name,
                                            Loai,
                                            Gia,
                                            prefs.getString("name", ""),
                                            NgayBan,
                                            LinkHinh,
                                            BaoHanh,
                                            edtCumstomer.getText().toString(),
                                            edtPhoneCustomer.getText().toString());
                                    infoPhone.child("Kho").child("Ban").push().setValue(classSellPhone, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            if (databaseError == null) {
                                                infoPhone.child("Kho").child("Kho").child(Key).removeValue();
                                                uploading.cancel();
                                                Intent myIntent = new Intent(SellActivity.this, SellActivity.class);
                                                startActivity(myIntent);
                                                finish();
                                                Toast.makeText(SellActivity.this, "Thành công", Toast.LENGTH_SHORT).show();


                                            } else {
                                                uploading.cancel();
                                                Intent myIntent = new Intent(SellActivity.this, SellActivity.class);
                                                startActivity(myIntent);
                                                finish();
                                                Toast.makeText(SellActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    } else {
                        Toast.makeText(SellActivity.this, "Bạn chưa gữi tin nhắn xác nhận", Toast.LENGTH_SHORT).show();
                    }
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
            Toast.makeText(SellActivity.this, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
        }
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                edtImei.post(new Runnable() {
                    @Override
                    public void run() {
                        edtImei.setText(barcode.displayValue);
                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
