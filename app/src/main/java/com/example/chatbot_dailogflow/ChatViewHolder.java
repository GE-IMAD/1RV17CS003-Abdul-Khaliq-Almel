package com.example.chatbot_dailogflow;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    TextView user;
    TextView bot;
    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        user=(TextView) itemView.findViewById(R.id.userText);
        bot=(TextView) itemView.findViewById(R.id.botText);



    }
}
