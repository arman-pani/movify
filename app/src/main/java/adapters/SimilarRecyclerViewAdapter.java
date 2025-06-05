package adapters;

import android.content.Context;
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
        TextView similarPlaceholderTextView;

        public SimilarViewHolder(View itemView) {
            super(itemView);
            similarPosterImageView = itemView.findViewById(R.id.similarPosterImageView);
            similarPlaceholderTextView = itemView.findViewById(R.id.similarPlaceholderTextView);
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

        Glide.with(context)
                .load(movieCardModel.getPosterUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.similarPosterImageView);


        TextView initials = holder.similarPlaceholderTextView;
        initials.setText(movieCardModel.getTitle());
        initials.setVisibility(View.VISIBLE);

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
                        initials.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.similarPosterImageView);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
