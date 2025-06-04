package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;

import java.util.List;

import models.MovieCardModel;

public class SimilarRecyclerViewAdapter extends  RecyclerView.Adapter<SimilarRecyclerViewAdapter.SimilarViewHolder>{

    private Context context;
    private List<MovieCardModel> moviesList;

    public SimilarRecyclerViewAdapter(Context context, List<MovieCardModel> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    public static class SimilarViewHolder extends RecyclerView.ViewHolder {
        ImageView similarPosterImageView;

        public SimilarViewHolder(View itemView) {
            super(itemView);
            similarPosterImageView = itemView.findViewById(R.id.similarPosterImageView);
        }
    }

    @NonNull
    @Override
    public SimilarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.similar_movie_card, parent, false);
        return new SimilarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarViewHolder holder, int position) {
        MovieCardModel movieCardModel = moviesList.get(position);

        String fullUrl = "https://image.tmdb.org/t/p/w500" + movieCardModel.getPosterUrl();

        Glide.with(context)
                .load(fullUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.similarPosterImageView);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
