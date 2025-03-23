package com.example.appbanhang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.databinding.ItemReceiveMessBinding;
import com.example.appbanhang.databinding.ItemSendMessBinding;
import com.example.appbanhang.model.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<ChatMessage> lstChatMesssage;
    private String senId;
    private static final  int TYPE_SEND = 1;
    private static final  int TYPE_RECEIVE = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_SEND){
            ItemSendMessBinding binding = ItemSendMessBinding.inflate(LayoutInflater.from(context), parent, false);
            return new SendViewHolder(binding);
        }else {
            ItemReceiveMessBinding binding = ItemReceiveMessBinding.inflate(LayoutInflater.from(context), parent, false);
            return new ReceivedViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_SEND){
            ((SendViewHolder) holder).bind(position);
        }else {
            ((ReceivedViewHolder) holder).bind(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("ChatAdapter", "senId: " + senId);
        Log.d("ChatAdapter", "Message sendId: " + lstChatMesssage.get(position).sendId);

        if (lstChatMesssage.get(position).sendId.equals(senId)) {
            Log.d("ChatAdapter", "Message type: SEND");
            return TYPE_SEND;
        } else {
            Log.d("ChatAdapter", "Message type: RECEIVE");
            return TYPE_RECEIVE;
        }
//        if (lstChatMesssage.get(position).sendId.equals(senId)){
//            return TYPE_SEND;
//        }else {
//            return TYPE_RECEIVE;
//        }

    }

    @Override
    public int getItemCount() {
        if (lstChatMesssage != null){
            return lstChatMesssage.size();
        }
        return 0;
    }

    public void setData(List<ChatMessage> lstChatMesssage){
        this.lstChatMesssage  = lstChatMesssage;
        notifyDataSetChanged();
    }
    public ChatAdapter(Context context, String senId) {
        this.context = context;
        this.senId = senId;
    }

    public class SendViewHolder extends RecyclerView.ViewHolder {
        private final ItemSendMessBinding binding;
        public SendViewHolder(@NonNull ItemSendMessBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            binding.txtmess.setText(lstChatMesssage.get(position).mess);
            binding.thoigian.setText(lstChatMesssage.get(position).datetime);
        }
    }
    public class ReceivedViewHolder extends RecyclerView.ViewHolder {
        private final ItemReceiveMessBinding binding;
        public ReceivedViewHolder(@NonNull ItemReceiveMessBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            binding.txtmess.setText(lstChatMesssage.get(position).mess);
            binding.thoigian.setText(lstChatMesssage.get(position).datetime);
        }
    }
}
