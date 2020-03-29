package com.example.androidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int ASSISTANT_TYPE = 0;
    private static final int USER_TYPE = 1;

    public List<Message> messageList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == USER_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message, parent, false);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assistant_message, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        MessageViewHolder messageViewHolder = new MessageViewHolder(holder.itemView);
        messageViewHolder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public int getItemViewType(int index) {
        Message message = messageList.get(index);
        if (message.isSend) {
            return USER_TYPE;
        }
        return ASSISTANT_TYPE;
    }
}
