package vision.google.com.qrcodescanner.function;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import vision.google.com.qrcodescanner.R;

/**
 * Created by Khoa Tran on 11-01-2018.
 */

public class Add_Function {
    //Number Format
    public TextWatcher onTextChangedListener(final EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    editText.setText(formattedString);
                    editText.setSelection(editText.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                editText.addTextChangedListener(this);
            }
        };
    }

    public void ShowInfo(final Context context, String Ten, String Imei, String Loai, String Gia, String NguoiNhap, String NgayNhap) {
        final Dialog dialog = new
                Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.info_upload);
        TextView txtTen = (TextView) dialog.findViewById(R.id.txtTen);
        TextView txtImei = (TextView) dialog.findViewById(R.id.txtImei);
        TextView txtLoai = (TextView) dialog.findViewById(R.id.txtLoai);
        TextView txtGia = (TextView) dialog.findViewById(R.id.txtGia);
        TextView txtNguoiNhap = (TextView) dialog.findViewById(R.id.txtNguoiNhap);
        TextView txtNgayNhap = (TextView) dialog.findViewById(R.id.txtNgayNhap);
        Button btLuu = (Button) dialog.findViewById(R.id.btnPost);
        Button btSua = (Button) dialog.findViewById(R.id.btnSua);
        txtTen.setText("Tên: " + Ten);
        txtImei.setText("IMEI: " + Imei);
        txtLoai.setText("Loại: " + Loai);
        txtGia.setText("Giá: " + Gia);
        txtNguoiNhap.setText("Người nhập: Khoa Trần");
        txtNgayNhap.setText("Ngày nhập: " + NgayNhap);
        btLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                final Dialog uploading = new
                        Dialog(context);
                uploading.setCancelable(true);
                uploading.setContentView(R.layout.uploading);
                uploading.show();

            }
        });
        btSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public boolean CheckInfo(String ten, String imei, String gia,String baohanh) {
        if (ten.trim().length() <= 0 || imei.matches("") || gia.matches("")|| baohanh.matches("")) {
            return false;
        } else {
            return true;
        }
    }
}
