package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.activity.ChiTietSanPham;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.ulttil.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    private Context context;
    private List<SanPham> lstSanPham;

    public SanPhamAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<SanPham> list){
        this.lstSanPham = new ArrayList<>(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoi, null);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SanPham sanPham = lstSanPham.get(position);
        if (sanPham == null){
            return;
        }
        holder.txtTensp.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        Double gia = Double.parseDouble(sanPham.getGia());
        holder.txtgiasp.setText("Giá: "+ decimalFormat.format(gia)+" VND");
        Glide.with(context).load(sanPham.getHinhanh()).into(holder.imgHinhsp);
    }

    @Override
    public int getItemCount() {
        return lstSanPham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imgHinhsp;
        public TextView txtTensp, txtgiasp;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhsp = itemView.findViewById(R.id.imgSanpham);
            txtTensp = itemView.findViewById(R.id.txtTensp);
            txtgiasp = itemView.findViewById(R.id.txtGiasp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham",lstSanPham.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //CheckConnection.showToast_Short(context, lstSanPham.get(getAdapterPosition()).getTen());
                    context.startActivity(intent);
                }
            });
        }
    }
}
