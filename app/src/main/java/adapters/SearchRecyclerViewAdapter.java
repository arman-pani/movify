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

import activities.MovieActivity;
import models.MovieCardModel;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchViewHolder> {
    private Context context;
    private List<MovieCardModel> moviesList;

    public SearchRecyclerViewAdapter(Context context, List<MovieCardModel> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView searchPosterImageView;
        TextView searchTitleTextView;
        TextView searchSubTitleTextView;

        public  SearchViewHolder(View itemView){
            super(itemView);
            searchPosterImageView = itemView.findViewById(R.id.searchPosterImageView);
            searchTitleTextView = itemView.findViewById(R.id.searchTitleTextView);
            searchSubTitleTextView = itemView.findViewById(R.id.searchSubTitleTextView);

        }

    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_card, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        MovieCardModel movieCardModel = moviesList.get(position);
        String fullUrl = "https://image.tmdb.org/t/p/w500" + movieCardModel.getPosterUrl();

        holder.searchTitleTextView.setText(movieCardModel.getTitle());
        holder.searchSubTitleTextView.setText(movieCardModel.getReleaseDate());


        Glide.with(context)
                .load(fullUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.searchPosterImageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieActivity.class);
            intent.putExtra("movieId", movieCardModel.getId());
            context.startActivity(intent);
        });
    }

    public void updateMovieList(List<MovieCardModel> newMovies) {
        this.moviesList = newMovies;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
