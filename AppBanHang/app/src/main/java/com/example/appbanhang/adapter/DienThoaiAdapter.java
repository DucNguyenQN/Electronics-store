package com.example.appbanhang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DienThoaiAdapter extends BaseAdapter {
    private Context context;
    private List<SanPham> lstDienThoai;

    public DienThoaiAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<SanPham> list){
        this.lstDienThoai = new ArrayList<>(list);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if(lstDienThoai != null){
            return lstDienThoai.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return lstDienThoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txtten, txtgia, txtmota;
        ImageView imgDienThoai;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.dong_dienthoai, null);
            holder.txtten = view.findViewById(R.id.txtDienThoai);
            holder.txtgia = view.findViewById(R.id.txtDienThoaiGia);
            holder.txtmota = view.findViewById(R.id.txtDienThoaiMoTa);
            holder.imgDienThoai = view.findViewById(R.id.imgDienThoai);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = lstDienThoai.get(i);
        holder.txtten.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        Double gia = Double.parseDouble(sanPham.getGia());
        holder.txtgia.setText("Giá: "+ decimalFormat.format(gia)+" VND");
        holder.txtmota.setMaxLines(2);
        holder.txtmota.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtmota.setText(sanPham.getMota());
        Glide.with(context).load(sanPham.getHinhanh()).into(holder.imgDienThoai);
        return view;
    }
}
