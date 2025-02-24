package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.LoaiSanPham;

import java.util.List;

public class LoaispAdapter extends BaseAdapter {
    private Context context;
    private List<LoaiSanPham> lstLoai;

    public LoaispAdapter(Context context,List<LoaiSanPham> lstLoai) {
        this.context = context;
        this.lstLoai = lstLoai;
    }

    @Override
    public int getCount() {
        return lstLoai.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class Viewholder{
        TextView txtLoaisp;
        ImageView imgLoaisp;
        LinearLayout layout_category;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewholder holder;
        if(view == null){
            holder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp,null);

            holder.txtLoaisp = view.findViewById(R.id.item_tensp);
            holder.imgLoaisp = view.findViewById(R.id.item_image);
            holder.layout_category = view.findViewById(R.id.layout_category);

            view.setTag(holder);
        }else{
            holder = (Viewholder) view.getTag();
        }
        LoaiSanPham loaiSanPham = lstLoai.get(i);
        holder.txtLoaisp.setText(loaiSanPham.getTensanpham());
        Glide.with(context).load(loaiSanPham.getHinhanh()).into(holder.imgLoaisp);
        return view;
    }
}
