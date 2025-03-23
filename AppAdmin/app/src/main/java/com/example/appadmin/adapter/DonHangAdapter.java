package com.example.appadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appadmin.databinding.IttemDonhangBinding;
import com.example.appadmin.model.HistoryResponse;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewHolder>{
    private Context context;
    private List<HistoryResponse> lstDonHang;
    ChiTietAdapter adapter;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public DonHangAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<HistoryResponse> lstDonHang){
        this.lstDonHang = lstDonHang;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IttemDonhangBinding binding = IttemDonhangBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DonHangViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (lstDonHang != null){
            return lstDonHang.size();
        }
        return 0;
    }
    private String TrangThai(int status){
        String result = "";
        switch (status){
            case 0:
                result = "Đơn hàng đang được xử lý";
                break;
            case 1:
                result = "Đơn hàng đã được chấp nhận";
                break;
            case 2:
                result = "Đơn hàng đã giao cho đơn vị vận chuyển";
                break;
            case 3:
                result = "Thành công";
                break;
            case 4:
                result = "Đơn hàng đã hủy";
                break;
        }
        return  result;
    }
    public class DonHangViewHolder extends RecyclerView.ViewHolder {
        private final IttemDonhangBinding binding;
        public DonHangViewHolder(IttemDonhangBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            HistoryResponse donhang = lstDonHang.get(position);
            binding.iddonhang.setText("Đơn hàng: "+donhang.getId());
            binding.tinhtrang.setText(TrangThai(donhang.getTrangthai()));
            binding.recycleChitiet.setLayoutManager(new LinearLayoutManager(context));
            adapter = new ChiTietAdapter(context);
            adapter.setData(donhang.getItems());
            binding.recycleChitiet.setAdapter(adapter);
            binding.recycleChitiet.setRecycledViewPool(viewPool);
        }
    }
}
