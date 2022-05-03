package com.example.chatapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {


    private List<ChatMessage> messageList;

    private Calendar calendar = Calendar.getInstance();

    public MessagesAdapter(List<ChatMessage> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessagesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);


        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        if(u!=null) {
            if(u.getUid().equals(message.getSenderId()))
                holder.itemView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        Glide.with(holder.itemView.getContext()).load(message.getSenderImageUrl()).into(holder.image);

        calendar.setTimeInMillis(message.getDateInMillis());
        String dateString = calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
        holder.date.setText(dateString);
        holder.name.setText(message.getSenderName());
        holder.content.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MessagesViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView date;
        TextView content, name;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.messageImage);
            this.content = itemView.findViewById(R.id.messageContent);
            this.date = itemView.findViewById(R.id.messageDate);
            this.name = itemView.findViewById(R.id.messageName);
        }
    }
}
