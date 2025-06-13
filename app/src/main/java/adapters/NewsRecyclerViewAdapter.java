package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;

import java.util.List;

import activities.WebActivity;
import models.NewsModel;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    private Context context;
    private List<NewsModel> newsList;

    public NewsRecyclerViewAdapter(Context context, List<NewsModel> newsList){
        this.context = context;
        this.newsList = newsList;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        ImageView newsImageView;
        TextView titleTextView;
        TextView subTitleTextView;
        TextView descriptionTextView;
        public  NewsViewHolder(View itemView){
            super(itemView);
            newsImageView = itemView.findViewById(R.id.newsImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            subTitleTextView = itemView.findViewById(R.id.subTitleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }

    @NonNull
    @Override
    public NewsRecyclerViewAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.NewsViewHolder holder, int position) {
        NewsModel newsModel = newsList.get(position);

        String subtitle = "by " + newsModel.getAuthor() + " | " + newsModel.getPublishedAt();

        holder.titleTextView.setText(newsModel.getTitle());
        holder.subTitleTextView.setText(subtitle);
        holder.descriptionTextView.setText(newsModel.getDescription());

        Glide.with(context)
                .load(newsModel.getPosterUrl())
                .into(holder.newsImageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("url", newsModel.getUrl());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {return newsList.size();}
}
