package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
        TextView moviePlaceholderTextView;

        public  MovieViewHolder(View itemView){
            super(itemView);
            imageViewMovieCard = itemView.findViewById(R.id.imageViewMovieCard);
            moviePlaceholderTextView = itemView.findViewById(R.id.moviePlaceholderTextView);
        }
    }

    public void updateData(List<MovieCardModel> newList) {
        this.moviesList = newList;
        notifyDataSetChanged();
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

        TextView placeHolder = holder.moviePlaceholderTextView;
        placeHolder.setText(movieCardModel.getTitle());
        placeHolder.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(movieCardModel.getPosterUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model,
                                                   Target<Drawable> target, @NonNull com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        placeHolder.setVisibility(View.GONE);
                        return false;
                    }
                })
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
