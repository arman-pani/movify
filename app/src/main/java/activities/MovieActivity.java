package activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.movieapp.R.*;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;

import models.MovieDetailModel;

public class MovieActivity extends AppCompatActivity {
    private int movieId;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.movie_activity);

        ImageView headerImageView = findViewById(R.id.headerImageView);
        TextView movieNameTextView = findViewById(R.id.movieNameTextView);
        TextView directorNameTextView = findViewById(R.id.directorNameTextView);
        ImageView posterImageView = findViewById(R.id.posterImageView);
        TextView yearTextView = findViewById(R.id.yearTextView);
        TextView durationTextView = findViewById(R.id.durationTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView trailerButton = findViewById(R.id.trailerTextButton);


        movieId = (int) getIntent().getSerializableExtra("movieId");

        if (movieDetailModel != null) {
            movieNameTextView.setText(movieDetailModel.getName());
            directorNameTextView.setText(movieDetailModel.getDirectorName());
            yearTextView.setText(movieDetailModel.getYearOfRelease());
            durationTextView.setText(movieDetailModel.getDurationInMinutes() + " " + getString(string.mins));
            descriptionTextView.setText(movieDetailModel.getDescription());

            Glide.with(this)
                    .load(movieDetailModel.getHeaderImageLink())
                    .placeholder(drawable.poster)
                    .into(headerImageView);

            Glide.with(this)
                    .load(movieDetailModel.getPosterLink())
                    .placeholder(drawable.poster)
                    .into(posterImageView);

            trailerButton.setOnClickListener(v -> {
                if (movieDetailModel.getTrailerLink() != null) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieDetailModel.getTrailerLink())));
                }
            });

        }

    }
}
