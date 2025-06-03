package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;

import java.util.List;

import activities.MovieActivity;
import models.MovieCardModel;

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieViewHolder> {
    private Context context;
    private List<MovieCardModel> moviesList;

    public MovieGridAdapter(Context context, List<MovieCardModel> moviesList){
        this.context = context;
        this.moviesList = moviesList;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewMovieCard;

        public  MovieViewHolder(View itemView){
            super(itemView);
            imageViewMovieCard = itemView.findViewById(R.id.imageViewMovieCard);
        }
    }

    @NonNull
    @Override
    public MovieGridAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_card, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieGridAdapter.MovieViewHolder holder, int position) {
        MovieCardModel movieCardModel = moviesList.get(position);
        String fullUrl = "https://image.tmdb.org/t/p/w500" + movieCardModel.getPosterUrl();

        Glide.with(context)
                .load(fullUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageViewMovieCard);


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieActivity.class);
            intent.putExtra("movieId", movieCardModel.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
