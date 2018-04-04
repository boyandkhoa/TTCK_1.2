package vision.google.com.qrcodescanner.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import vision.google.com.qrcodescanner.function.ClassAddUser;
import vision.google.com.qrcodescanner.ManagerEmployeeActivity;
import vision.google.com.qrcodescanner.R;

/**
 * Created by Khoa Tran on 10-02-2018.
 */

public class NhanVienAdapter extends BaseAdapter {
    Activity context;
    LayoutInflater layoutInflater;
    int mylayout;
    List<ClassAddUser> arrayHinh;
    public  String NameUser = "";

    public NhanVienAdapter(Activity context, int mylayout, List<ClassAddUser> arrayHinh) {
        this.context = context;
        this.mylayout = mylayout;
        this.arrayHinh = arrayHinh;
    }

    @Override
    public int getCount() {
        return arrayHinh.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayHinh.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        TextView txtTen, txtSDTNhanVien;
        Button btnActive, btnAdmin;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = view;
        ViewHolder holder = new ViewHolder();
        if (rowview == null) {
            rowview = inflater.inflate(mylayout, null);
            holder.txtTen = (TextView) rowview.findViewById(R.id.txtRowNhanVien);
            holder.txtSDTNhanVien = (TextView) rowview.findViewById(R.id.txtRowSDTNhanVien);
            holder.btnActive = (Button) rowview.findViewById(R.id.btnRowActive);
            holder.btnAdmin = (Button) rowview.findViewById(R.id.btnRowAdmin);
//            UserName = arrayHinh.get(i).UserName.toString();
            rowview.setTag(holder);
        } else {
            holder = (ViewHolder) rowview.getTag();
        }
        holder.txtTen.setText(arrayHinh.get(i).HoTen);
        holder.txtSDTNhanVien.setText(arrayHinh.get(i).NumberPhone);
        if (arrayHinh.get(i).Active.equals("0")) {
            holder.btnActive.setText("Khóa");
            holder.btnActive.setBackgroundResource(R.drawable.radius_button4);
        } else {
            holder.btnActive.setText("Mở");
            holder.btnActive.setBackgroundResource(R.drawable.radius_button2);
        }

        if (arrayHinh.get(i).Admin.equals("0")) {
            holder.btnAdmin.setText("NV");
            holder.btnAdmin.setBackgroundResource(R.drawable.radius_button);
        } else {
            holder.btnAdmin.setText("QL");
            holder.btnAdmin.setBackgroundResource(R.drawable.radius_button3);
        }

        holder.btnActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Active
                NameUser = arrayHinh.get(i).UserName;
                SharedPreferences prefs = context.getSharedPreferences("infoUser", Context.MODE_PRIVATE);

                if (prefs.getString("username", "").equals(NameUser)){
                    Toast.makeText(context, "Bạn không thể tự khóa bản thân", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayHinh.get(i).Active.equals("0")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final AlertDialog show = builder.setTitle("Thông báo")
                            .setMessage("Bạn muốn kích hoạt tài khoản của: " + arrayHinh.get(i).HoTen)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    final Dialog uploading = new Dialog(context);
                                    uploading.setCancelable(false);
                                    uploading.setContentView(R.layout.uploading);
                                    uploading.show();
                                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                    final Query query = reference
                                            .child("User")
                                            .orderByChild("UserName")
                                            .equalTo(NameUser);
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                                    ClassAddUser classAddUser = singleSnapshot.getValue(ClassAddUser.class);
                                                    reference.child("User").child(singleSnapshot.getKey()).child("Active").setValue("1", new DatabaseReference.CompletionListener() {
                                                        @Override
                                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                            if (databaseError == null) {

                                                                uploading.cancel();
                                                                Toast.makeText(context, "Kích hoạt thành công", Toast.LENGTH_SHORT).show();
                                                                Intent myIntent = new Intent(context, ManagerEmployeeActivity.class);
//                                                                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                context.startActivity(myIntent);
                                                                context.finish();
                                                            } else {
                                                                uploading.cancel();
                                                                Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            } else {
                                                uploading.cancel();
                                                Toast.makeText(context, "Fail!", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("No", null).show();
                }
//                Lock
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final AlertDialog show = builder.setTitle("Thông báo")
                            .setMessage("Bạn muốn khóa tài khoản của: " + arrayHinh.get(i).HoTen)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    final Dialog uploading = new Dialog(context);
                                    uploading.setCancelable(false);
                                    uploading.setContentView(R.layout.uploading);
                                    uploading.show();
                                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                    final Query query = reference
                                            .child("User")
                                            .orderByChild("UserName")
                                            .equalTo(NameUser);
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                                    ClassAddUser classAddUser = singleSnapshot.getValue(ClassAddUser.class);
                                                    reference.child("User").child(singleSnapshot.getKey()).child("Active").setValue("0", new DatabaseReference.CompletionListener() {
                                                        @Override
                                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                            if (databaseError == null) {

                                                                uploading.cancel();
                                                                Toast.makeText(context, "Khóa thành công", Toast.LENGTH_SHORT).show();
                                                                Intent myIntent = new Intent(context, ManagerEmployeeActivity.class);
//                                                                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                context.startActivity(myIntent);
                                                                context.finish();
                                                            } else {
                                                                uploading.cancel();
                                                                Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            } else {
                                                uploading.cancel();
                                                Toast.makeText(context, "Fail!", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("No", null).show();
                }
            }
        });

        holder.btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Admin", Toast.LENGTH_SHORT).show();
            }
        });

        return rowview;
    }


}
