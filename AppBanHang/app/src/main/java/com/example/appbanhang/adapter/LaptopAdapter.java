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

public class LaptopAdapter extends BaseAdapter {
    private Context context;
    private List<SanPham> lstLapTop;

    public LaptopAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<SanPham> list){
        this.lstLapTop = new ArrayList<>(list);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if(lstLapTop != null){
            return lstLapTop.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return lstLapTop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txtten, txtgia, txtmota;
        ImageView imgLaptop;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.dong_laptop, null);
            holder.txtten = view.findViewById(R.id.txtLaptop);
            holder.txtgia = view.findViewById(R.id.txtGiaLaptop);
            holder.txtmota = view.findViewById(R.id.txtMotaLaptop);
            holder.imgLaptop = view.findViewById(R.id.imgLaptop);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = lstLapTop.get(i);
        holder.txtten.setText(sanPham.getTensanpham());
        Double gia = Double.parseDouble(sanPham.getGia());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgia.setText("Giá: "+ decimalFormat.format(gia)+" VND");
        holder.txtmota.setMaxLines(2);
        holder.txtmota.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtmota.setText(sanPham.getMota());
        Glide.with(context).load(sanPham.getHinhanh()).into(holder.imgLaptop);
        return view;
    }
}
