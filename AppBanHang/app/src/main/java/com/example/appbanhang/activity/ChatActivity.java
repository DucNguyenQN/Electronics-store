package com.example.appbanhang.activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.ChatAdapter;
import com.example.appbanhang.databinding.ActivityChatBinding;
import com.example.appbanhang.model.ChatMessage;
import com.example.appbanhang.model.EvenBus.TinhTong;
import com.example.appbanhang.ulttil.Ultils;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.SimpleFormatter;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    FirebaseFirestore db;
    ChatAdapter adapter;
    List<ChatMessage> lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = FirebaseFirestore.getInstance();
        binding.recycleChat.setLayoutManager(new LinearLayoutManager(this));
        lst = new ArrayList<>();
        adapter = new ChatAdapter(this, String.valueOf(Ultils.user_current.getId()));
        innitControl();
        listenMess();
        adapter.setData(lst);
        binding.recycleChat.setAdapter(adapter);
        insertUser();
    }

    private void insertUser() {
        Map<String, Object> user = new HashMap<>();
        user.put("id", Ultils.user_current.getId());
        user.put("username", Ultils.user_current.getUsername());
        db.collection("users").document(String.valueOf(Ultils.user_current.getId())).set(user);
    }

    private void listenMess(){
        db.collection(Ultils.PATH_CHAT)
                .whereEqualTo(Ultils.SENDID, String.valueOf(Ultils.user_current.getId()))
                .whereEqualTo(Ultils.RECEIVEID, Ultils.ID_RECEIVE)
                .addSnapshotListener(eventListener);

        db.collection(Ultils.PATH_CHAT)
                .whereEqualTo(Ultils.SENDID, Ultils.ID_RECEIVE)
                .whereEqualTo(Ultils.RECEIVEID, String.valueOf(Ultils.user_current.getId()))
                .addSnapshotListener(eventListener);
    }
    private final EventListener<QuerySnapshot> eventListener =  ((value, error) -> {
        if (error != null){
            return;
        }
        if (value != null){
            int count = lst.size();
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.sendId = documentChange.getDocument().getString(Ultils.SENDID);
                    chatMessage.recivedId = documentChange.getDocument().getString(Ultils.RECEIVEID);
                    chatMessage.mess = documentChange.getDocument().getString(Ultils.MESS);
                    chatMessage.dateObj = documentChange.getDocument().getDate(Ultils.DATETIME);
                    chatMessage.datetime = formatDate(documentChange.getDocument().getDate(Ultils.DATETIME));
                    lst.add(chatMessage);
                }
            }
            Collections.sort(lst, (obj1, obj2)-> obj1.dateObj.compareTo(obj2.dateObj));
            if (count == 0){
                adapter.notifyDataSetChanged();
            }else {
                adapter.notifyItemRangeInserted(lst.size(), lst.size());
                binding.recycleChat.smoothScrollToPosition(lst.size()-1);
            }
        }
    });
    private String formatDate(Date date){
        return  new SimpleDateFormat("MMMM dd, yyyy- hh:mm -a", Locale.getDefault()).format(date);
    }
    private void innitControl() {
        binding.imgchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessToFirebase();
            }
        });
    }

    private void sendMessToFirebase() {
        String str_mess = binding.edtinputtext.getText().toString().trim();
        if (str_mess.isEmpty()){

        }else {
            Map<String, Object> message = new HashMap<>();
            message.put(Ultils.SENDID, String.valueOf(Ultils.user_current.getId()));
            message.put(Ultils.RECEIVEID, Ultils.ID_RECEIVE);
            message.put(Ultils.MESS, str_mess);
            message.put(Ultils.DATETIME, new Date());
            db.collection(Ultils.PATH_CHAT).add(message);
            binding.edtinputtext.setText("");
        }
    }
}