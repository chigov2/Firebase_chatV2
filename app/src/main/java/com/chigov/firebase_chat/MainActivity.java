package com.chigov.firebase_chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMessages;
    private MessagesAdapter adapter;
    private EditText editTextEnterMassage;
    private ImageView imageViewAddImage;
    private ImageView imageViewSendMessage;
    //private List <Message> messages;
    private String author;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MessagesAdapter();
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextEnterMassage = findViewById(R.id.editTextEnterMassage);
        imageViewAddImage = findViewById(R.id.imageViewAddImage);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        author = "Serge";
        imageViewSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage();
            }
        });

        db.collection("messages")
                .orderBy("date")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    List<Message> messages = value.toObjects(Message.class);
                    adapter.setMessages(messages);
                }
            }
        });

        //проверка
        if (mAuth.getCurrentUser() != null){
            Toast.makeText(this, "Logged!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Not registered!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
        }
    }

    private void sendMessage(){
        String messageText = editTextEnterMassage.getText().toString().trim();
        if (!messageText.isEmpty()){
            //messages.add(new Message(author,messageText));
            //adapter.setMessages(messages);
            db.collection("messages")
                    .add(new Message(author,messageText,System.currentTimeMillis()))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.i("test", "DocumentSnapshot added with ID: " + documentReference.getId());
                            editTextEnterMassage.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("test", "Error adding document", e);
                        }
                    });


            recyclerViewMessages.scrollToPosition(adapter.getItemCount()-1);
        }
    }

}