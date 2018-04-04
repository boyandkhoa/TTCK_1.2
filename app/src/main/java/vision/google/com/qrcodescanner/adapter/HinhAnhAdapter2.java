package vision.google.com.qrcodescanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vision.google.com.qrcodescanner.function.ClassSellPhone;
import vision.google.com.qrcodescanner.R;

/**
 * Created by Khoa Tran on 10-02-2018.
 */

public class HinhAnhAdapter2 extends BaseAdapter {
    Context context;
    int mylayout;
    List<ClassSellPhone> arrayHinh;

    public HinhAnhAdapter2(Context context, int mylayout, List<ClassSellPhone> arrayHinh) {
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
        TextView txtTen, txtIMEI, txtGia, txtLoai, txtNguoiNhap, txtNgayNhap, txtBaoHanh,txtKhachHang,txtSdtKhachHang;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = view;
        ViewHolder holder = new ViewHolder();
        if (rowview == null) {
            rowview = inflater.inflate(mylayout, null);
            holder.txtIMEI = (TextView) rowview.findViewById(R.id.RowIMEI2);
            holder.txtTen = (TextView) rowview.findViewById(R.id.RowName2);
            holder.txtLoai = (TextView) rowview.findViewById(R.id.RowLoai2);
            holder.txtGia = (TextView) rowview.findViewById(R.id.RowGia2);
            holder.txtBaoHanh = (TextView) rowview.findViewById(R.id.RowBH2);
            holder.txtNgayNhap = (TextView) rowview.findViewById(R.id.RowNgayNhap2);
            holder.txtNguoiNhap = (TextView) rowview.findViewById(R.id.RowNguoiNhap2);
            holder.txtKhachHang = (TextView) rowview.findViewById(R.id.RowKhachHang2);
            holder.txtSdtKhachHang = (TextView) rowview.findViewById(R.id.RowSdtKhachHang2);
            holder.imageHinh = (ImageView) rowview.findViewById(R.id.RowImg2);
            rowview.setTag(holder);
        } else {
            holder = (ViewHolder) rowview.getTag();
        }
        holder.txtIMEI.setText("IMEI: " + arrayHinh.get(i).Imei);
        holder.txtTen.setText("Tên: " + arrayHinh.get(i).Ten);
        holder.txtLoai.setText("Loại: " + arrayHinh.get(i).Loai);
        holder.txtGia.setText("Giá: " + arrayHinh.get(i).GiaBan);
        holder.txtBaoHanh.setText("BH: " + arrayHinh.get(i).BaoHanh + " tháng");
        holder.txtNgayNhap.setText("Ngày: " + arrayHinh.get(i).NgayBan);
        holder.txtNguoiNhap.setText("NV: " + arrayHinh.get(i).NguoiBan);
        holder.txtKhachHang.setText("KH: " + arrayHinh.get(i).NguoiMua);
        holder.txtSdtKhachHang.setText("SĐT KH: " + arrayHinh.get(i).SDTNguoiMua);
        Picasso.with(context).load(arrayHinh.get(i).LinkHinh).into(holder.imageHinh);
        return rowview;
    }
}
