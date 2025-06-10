package activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

import java.util.ArrayList;
import java.util.List;

import adapters.ChatAdapter;
import helpers.GenerativeModelHelper;
import models.ChatMessage;

public class ChatbotActivity extends AppCompatActivity {

    GenerativeModelHelper modelHelper = new GenerativeModelHelper();

    RecyclerView recyclerView;
    EditText inputEditText;
    ImageButton sendButton;
    ChatAdapter chatAdapter;

    Toolbar toolbar;
    private void sendMessage() {
        String message = inputEditText.getText().toString().trim();
        if (!message.isEmpty()) {
            chatAdapter.addMessage(new ChatMessage("User: " + message, ChatMessage.TYPE_USER));
            inputEditText.setText("");

            recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
            modelHelper.generateResponse(message, new GenerativeModelHelper.ResponseCallback() {
                @Override
                public void onSuccess(String response) {
                    runOnUiThread(() -> {
                        chatAdapter.addMessage(new ChatMessage("Bot: " + response, ChatMessage.TYPE_BOT));
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                    });
                }

                @Override
                public void onFailure(Throwable throwable) {
                    runOnUiThread(() -> chatAdapter.addMessage(new ChatMessage("Failed to get response: " + throwable.getMessage(), ChatMessage.TYPE_BOT)));
                }
            });

        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        List<ChatMessage> chatMessages = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.chatRecyclerView);
        inputEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chatAdapter = new ChatAdapter(chatMessages,this);
        recyclerView.setAdapter(chatAdapter);

        recyclerView.postDelayed(() -> {
            if (chatAdapter.getItemCount() > 0) {
                recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
            }
        }, 100);

        sendButton.setOnClickListener(v -> sendMessage());

    }
}
