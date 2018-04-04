package vision.google.com.qrcodescanner.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vision.google.com.qrcodescanner.function.ClassAddPhone;
import vision.google.com.qrcodescanner.R;

/**
 * Created by Khoa Tran on 10-02-2018.
 */

public class HinhAnhAdapter extends BaseAdapter {
    Activity context;
    LayoutInflater layoutInflater;
    int mylayout;
    List<ClassAddPhone> arrayHinh;

    public HinhAnhAdapter(Activity context, int mylayout, List<ClassAddPhone> arrayHinh) {
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
        ImageView imageHinh;
        TextView txtTen, txtIMEI, txtGia, txtLoai, txtNguoiNhap, txtNgayNhap, txtBaoHanh;
        LinearLayout layoutDong;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = view;
        ViewHolder holder = new ViewHolder();
        if (rowview == null) {
            rowview = inflater.inflate(mylayout, null);
            holder.txtIMEI = (TextView) rowview.findViewById(R.id.RowIMEI);
            holder.txtTen = (TextView) rowview.findViewById(R.id.RowName);
            holder.txtLoai = (TextView) rowview.findViewById(R.id.RowLoai);
            holder.txtGia = (TextView) rowview.findViewById(R.id.RowGia);
            holder.txtBaoHanh = (TextView) rowview.findViewById(R.id.RowBH);
            holder.txtNgayNhap = (TextView) rowview.findViewById(R.id.RowNgayNhap);
            holder.txtNguoiNhap = (TextView) rowview.findViewById(R.id.RowNguoiNhap);
            holder.imageHinh = (ImageView) rowview.findViewById(R.id.RowImg);
            holder.layoutDong = (LinearLayout) rowview.findViewById(R.id.layoutDong);
            rowview.setTag(holder);
        } else {
            holder = (ViewHolder) rowview.getTag();
        }
        holder.txtIMEI.setText("IMEI: " + arrayHinh.get(i).Imei);
        holder.txtTen.setText("Tên: " + arrayHinh.get(i).Ten);
        holder.txtLoai.setText("Loại: " + arrayHinh.get(i).Loai);
        holder.txtGia.setText("Giá: " + arrayHinh.get(i).GiaBan);
        holder.txtBaoHanh.setText("BH: " + arrayHinh.get(i).BaoHanh + " tháng");
        holder.txtNgayNhap.setText("Ngày: " + arrayHinh.get(i).NgayNhap);
        holder.txtNguoiNhap.setText("NV: " + arrayHinh.get(i).NguoiNhap);
        Picasso.with(context).load(arrayHinh.get(i).LinkHinh).into(holder.imageHinh);

        holder.layoutDong.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                Toast.makeText(context,"Long Click", Toast.LENGTH_SHORT).show();
                return false;

            }
        });
        holder.layoutDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,arrayHinh.get(i).Imei, Toast.LENGTH_SHORT).show();
            }
        });
        return rowview;
    }



}
