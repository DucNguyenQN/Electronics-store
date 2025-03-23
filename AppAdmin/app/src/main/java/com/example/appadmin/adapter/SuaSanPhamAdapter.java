package com.example.appadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appadmin.SuaActivity;
import com.example.appadmin.databinding.ItemEditBinding;
import com.example.appadmin.model.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SuaSanPhamAdapter extends RecyclerView.Adapter<SuaSanPhamAdapter.SuaSanPhamViewHolder>{
    private Context context;
    private List<SanPham> lstSanPham;

    public SuaSanPhamAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<SanPham> list){
        this.lstSanPham = new ArrayList<>(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SuaSanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEditBinding binding = ItemEditBinding.inflate(LayoutInflater.from(context), parent, false);
        return new SuaSanPhamViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SuaSanPhamViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (lstSanPham != null){
            return lstSanPham.size();
        }
        return 0;
    }

    public class SuaSanPhamViewHolder extends RecyclerView.ViewHolder {
        private final ItemEditBinding binding;
        public SuaSanPhamViewHolder(ItemEditBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            SanPham sanPham = lstSanPham.get(position);
            if (sanPham == null){
                return;
            }
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            Long gia = Long.valueOf(sanPham.getGia());
            binding.txtFoodName.setText(sanPham.getTensanpham());
            binding.txtPrice.setText(decimalFormat.format(gia)+" VND");
            Glide.with(context).load(sanPham.getHinhanh()).into(binding.imgProduct);
            binding.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SuaActivity.class);
                    intent.putExtra("item", sanPham);
                    context.startActivity(intent);
                }
            });
        }
    }
}
