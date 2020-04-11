package com.example.chatbot_dailogflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    public Context context;
    public List<ChatMessage> messageList;

    public ChatAdapter(Context context, List<ChatMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        ChatViewHolder vh = new ChatViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        final ChatMessage cm= messageList.get(position);
        TextView user=holder.user;
        TextView bot = holder.bot;
        if(cm.k==0){
            bot.setVisibility(View.VISIBLE);
            user.setVisibility(View.INVISIBLE);
            bot.setText(cm.message);
        }
        else{
            user.setVisibility(View.VISIBLE);
            bot.setVisibility(View.INVISIBLE);
            user.setText(cm.message);
        }




    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}

