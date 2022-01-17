package com.chigov.firebase_chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends  RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>{


    List<Message> messages;

    //empty constructor
    public MessagesAdapter() {
        messages = new ArrayList<>();
    }
    //setter  и геттер на List<Message> messages;
    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }
    public List<Message> getMessages() {
        return messages;
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_message,parent,false);
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        holder.textViewAuthor.setText(messages.get(position).getAuthor());
        holder.textViewMessage.setText(messages.get(position).getMessageText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewAuthor;
        private TextView textViewMessage;

        public MessagesViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
        }

    }
}
