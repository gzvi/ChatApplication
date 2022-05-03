package com.example.chatapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {


    private RecyclerView rvMessages;

    EditText messageField;
    Button sendButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        rvMessages = findViewById(R.id.rvMessages);
        sendButton = findViewById(R.id.sendBtn);
        messageField = findViewById(R.id.sendEt);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        rvMessages.setLayoutManager(new LinearLayoutManager(this));

        FirebaseFirestore.getInstance()
                .collection("chat")
                .orderBy("dateInMillis")
                .addSnapshotListener((value, error) -> {

                    if (error == null && value != null) {
                        List<ChatMessage> messageList = new ArrayList<>();
                        for (DocumentSnapshot doc : value.getDocuments()) {
                            messageList.add(doc.toObject(ChatMessage.class));
                        }
                        rvMessages.setAdapter(new MessagesAdapter(messageList));
                        rvMessages.scrollToPosition(messageList.size() - 1);
                    }else {
                        Toast.makeText(this,"Err",Toast.LENGTH_LONG).show();
                    }
                });

        sendButton.setOnClickListener((v) -> sendMessage());
    }


    public void sendMessage() {
        if (!messageField.getText().toString().isEmpty()) {
            FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
            if (u != null) {
                ChatMessage chatMessage = new ChatMessage(messageField.getText().toString(), u.getUid(), u.getEmail().split("@")[0], u.getPhotoUrl() != null ? u.getPhotoUrl().toString() : null);
                messageField.getText().clear();
                FirebaseFirestore.getInstance()
                        .collection("chat")
                        .add(chatMessage);
                sendNotification(chatMessage);
            }
        }
    }


    public void sendNotification(ChatMessage chatMessage) {
        MessageNotificationSender messageNotificationSender = new MessageNotificationSender(chatMessage, this, this);
        messageNotificationSender.sendNotification();
    }
}
