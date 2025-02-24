package com.example.appbanhang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.databinding.ItemChitietBinding;
import com.example.appbanhang.model.Item;

import java.util.List;

public class ChiTietAdapter extends RecyclerView.Adapter<ChiTietAdapter.ChiTietViewHolder> {
    private Context context;
    private List<Item> lstitem;

    public ChiTietAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Item> lstitem){
        this.lstitem = lstitem;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ChiTietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChitietBinding binding = ItemChitietBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ChiTietViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (lstitem != null){
            return lstitem.size();
        }
        return 0;
    }

    public class ChiTietViewHolder extends RecyclerView.ViewHolder {
        private final ItemChitietBinding binding;
        public ChiTietViewHolder(ItemChitietBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            Item item = lstitem.get(position);
            binding.itemTensp.setText(item.getTensanpham());
            binding.itemSoluong.setText(item.getSoluong()+"");
            Glide.with(context).load(item.getHinhanh()).into(binding.itemImgchitiet);
        }
    }
}
