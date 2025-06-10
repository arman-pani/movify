package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

import java.util.List;

import models.ChatMessage;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<ChatMessage> messageList;

    public ChatAdapter(List<ChatMessage> messageList, Context context){
        this.messageList = messageList;
        this.context = context;
    }

    public void addMessage(ChatMessage message){
        messageList.add(message);
        notifyItemInserted(messageList.size() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).getType();
    }

    static class UserChatViewHolder extends RecyclerView.ViewHolder{
        TextView userMessageTextView;

        public UserChatViewHolder(@NonNull View itemView) {
            super(itemView);
            userMessageTextView = itemView.findViewById(R.id.userMessageTextView);
        }
    }

    static class BotChatViewHolder extends RecyclerView.ViewHolder {
        TextView botMessageTextView;

        public BotChatViewHolder(@NonNull View itemView) {
            super(itemView);
            botMessageTextView = itemView.findViewById(R.id.botMessageTextView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ChatMessage.TYPE_USER){
         View view = LayoutInflater.from(context).inflate(R.layout.user_chat_item, parent, false);
         return new UserChatViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.bot_chat_item, parent, false);
            return new BotChatViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);

        if (holder.getItemViewType() == ChatMessage.TYPE_USER){
            ((UserChatViewHolder) holder).userMessageTextView.setText(message.getMessage());
        } else {
            ((BotChatViewHolder) holder).botMessageTextView.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
