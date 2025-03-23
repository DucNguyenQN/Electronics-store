package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.activity.ChiTietSanPham;
import com.example.appbanhang.databinding.ItemSearchBinding;
import com.example.appbanhang.model.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemHolder> {
    private Context context;
    private List<SanPham> lstSanPham;
    private List<SanPham> lstSanPhamfilter;
    public SearchAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<SanPham> list){
        this.lstSanPham = new ArrayList<>(list);
        this.lstSanPhamfilter = new ArrayList<>(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchBinding binding = ItemSearchBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return lstSanPhamfilter.size();
    }
    public class ItemHolder extends RecyclerView.ViewHolder {
        private final ItemSearchBinding binding;
        public ItemHolder(@NonNull ItemSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            SanPham sanPham = lstSanPhamfilter.get(position);
            if (sanPham == null){
                return;
            }
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            Long gia = Long.valueOf(sanPham.getGia());
            binding.txtFoodName.setText(sanPham.getTensanpham());
            binding.txtPrice.setText(decimalFormat.format(gia)+" VND");
            Glide.with(context).load(sanPham.getHinhanh()).into(binding.imgProduct);
        }
    }
    public void filter(String text) {
        Log.d("text", text+"");
        lstSanPhamfilter.clear();
        if (text.isEmpty()) {
            lstSanPhamfilter.addAll(lstSanPham);
        }
        else {
            for (SanPham item : lstSanPham) {
                if (item.getTensanpham().toLowerCase().contains(text.toLowerCase())) {
                    lstSanPhamfilter.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}

