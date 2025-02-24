package com.example.appbanhang.adapter;

import static com.example.appbanhang.activity.MainActivity.manggiohang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.ImageClickListener;
import com.example.appbanhang.R;
import com.example.appbanhang.model.EvenBus.TinhTong;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.SanPham;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder>{
    private Context context;
    private List<GioHang> lstgiohang;
    public void setData(List<GioHang> lstgiohang){
        this.lstgiohang = lstgiohang;
        notifyDataSetChanged();
    }

    public GioHangAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {
        GioHang gioHang = lstgiohang.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText(decimalFormat.format(gioHang.getGiasp())+"");
        holder.item_giohang_sl.setText(gioHang.getSoluongsp()+"");
        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        holder.setListener(new ImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1){
                     if(lstgiohang.get(pos).getSoluongsp() > 1){
                         int slm = lstgiohang.get(pos).getSoluongsp()-1;
                         lstgiohang.get(pos).setSoluongsp(slm);
                         holder.item_giohang_sl.setText(lstgiohang.get(pos).getSoluongsp()+"");
                         EventBus.getDefault().postSticky(new TinhTong());
                     } else if (lstgiohang.get(pos).getSoluongsp() == 1) {
                         AlertDialog.Builder dialog = new AlertDialog.Builder(view.getRootView().getContext());
                         dialog.setTitle("Thông báo");
                         dialog.setMessage("Bạn có muốn xóa sản phẩm khỏi giỏ hàng");
                         dialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 manggiohang.remove(pos);
                                 notifyDataSetChanged();
                                 EventBus.getDefault().postSticky(new TinhTong());
                             }
                         });
                         dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 dialogInterface.dismiss();
                             }
                         });
                         dialog.show();
                     }
                }else if(giatri == 2) {
                    if(lstgiohang.get(pos).getSoluongsp() < 11){
                        int slm = lstgiohang.get(pos).getSoluongsp()+1;
                        lstgiohang.get(pos).setSoluongsp(slm);
                        holder.item_giohang_sl.setText(lstgiohang.get(pos).getSoluongsp()+"");
                        EventBus.getDefault().postSticky(new TinhTong());
                    }
                }

            }
        });
//        holder.item_giohang_tru.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (gioHang.getSoluongsp() >1){
//                    int soluong = gioHang.getSoluongsp() - 1;
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return lstgiohang.size();
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView item_giohang_image, item_giohang_tru, item_giohang_cong;
        TextView item_giohang_tensp, item_giohang_gia, item_giohang_sl;
        ImageClickListener listener;
        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_sl = itemView.findViewById(R.id.item_giohang_sl);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tru = itemView.findViewById(R.id.item_giohang_tru);
            item_giohang_cong = itemView.findViewById(R.id.item_giohang_cong);

            item_giohang_tru.setOnClickListener(this);
            item_giohang_cong.setOnClickListener(this);
        }

        public void setListener(ImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if (view == item_giohang_tru){
                listener.onImageClick(view, getAdapterPosition(),1);
            }else if(view == item_giohang_cong){
                listener.onImageClick(view , getAdapterPosition(), 2);
            }
        }
    }
}
