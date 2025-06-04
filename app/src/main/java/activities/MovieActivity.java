package activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.example.movieapp.R.*;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;

import adapters.CastRecyclerViewAdapter;
import adapters.CrewRecyclerViewAdapter;
import adapters.SimilarRecyclerViewAdapter;
import viewModel.MovieActivityViewModel;
import viewModel.MoviesFragmentViewModel;

public class MovieActivity extends AppCompatActivity {
    private int movieId;

    private MovieActivityViewModel viewModel;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.movie_activity);

//        ProgressBar loadingProgressBar = findViewById(R.id.loadingProgressBar);
//        ConstraintLayout movieLayout = findViewById(R.id.movieLayout);
//        loadingProgressBar.setVisibility(View.VISIBLE);
//        movieLayout.setVisibility(View.GONE);

        ImageView headerImageView = findViewById(R.id.headerImageView);
        TextView movieNameTextView = findViewById(R.id.movieNameTextView);
        TextView taglineTextView = findViewById(R.id.taglineTextView);
        TextView directorNameTextView = findViewById(R.id.directorNameTextView);
        ImageView posterImageView = findViewById(R.id.posterImageView);
        TextView yearTextView = findViewById(R.id.yearTextView);
        TextView durationTextView = findViewById(R.id.durationTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView showMoreTextView = findViewById(R.id.showMoreTextView);
        TextView trailerButton = findViewById(R.id.trailerTextButton);
        RecyclerView castRecyclerView = findViewById(R.id.castRecyclerView);
        RecyclerView crewRecyclerView = findViewById(R.id.crewRecyclerView);
        RecyclerView similarRecyclerView = findViewById(R.id.similarMoviesRecyclerView);



        LinearLayoutManager castLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager crewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager similarLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        castRecyclerView.setLayoutManager(castLayoutManager);
        crewRecyclerView.setLayoutManager(crewLayoutManager);
        similarRecyclerView.setLayoutManager(similarLayoutManager);

        movieId = (int) getIntent().getSerializableExtra("movieId");

        viewModel = new ViewModelProvider(this).get(MovieActivityViewModel.class);

        viewModel.loadMovieData(movieId);

        viewModel.getMovieDetails().observe(this, details -> {
            movieNameTextView.setText(details.getTitle());
            directorNameTextView.setText(details.getTitle());
            taglineTextView.setText(details.getTagline());
            yearTextView.setText(details.getReleaseDate());
            durationTextView.setText(details.getRuntime() + " " + getString(string.mins));
            descriptionTextView.setText(details.getOverview());



            Glide.with(this)
                    .load( details.getBackdropPath())
                    .placeholder(drawable.ic_launcher_background)
                    .into(headerImageView);

            Glide.with(this)
                    .load( details.getPosterPath())
                    .placeholder(drawable.ic_launcher_background)
                    .into(posterImageView);

            trailerButton.setOnClickListener(v -> {
                if (details.getPosterPath() != null) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(details.getPosterPath())));
                }
            });
        });

        descriptionTextView.post(() -> {
            if (descriptionTextView.getLineCount() > 3) {
                showMoreTextView.setVisibility(View.VISIBLE);
            }
        });

        showMoreTextView.setOnClickListener(v -> {
            if (showMoreTextView.getText().toString().equals("Show more")) {
                descriptionTextView.setMaxLines(Integer.MAX_VALUE);
                showMoreTextView.setText("Show less");
            } else {
                descriptionTextView.setMaxLines(3);
                showMoreTextView.setText("Show more");
            }
        });

        viewModel.getCastList().observe(this, castList -> {
            CastRecyclerViewAdapter castAdapter = new CastRecyclerViewAdapter(castList, this);
            castRecyclerView.setAdapter(castAdapter);


        });

        viewModel.getCrewList().observe(this, crewList -> {
            CrewRecyclerViewAdapter crewAdapter = new CrewRecyclerViewAdapter(crewList, this);
            crewRecyclerView.setAdapter(crewAdapter);
        });

        viewModel.getSimilarMovies().observe(this, similarMovies -> {
            SimilarRecyclerViewAdapter similarAdapter = new SimilarRecyclerViewAdapter(this, similarMovies);
            similarRecyclerView.setAdapter(similarAdapter);
        });

//        loadingProgressBar.setVisibility(View.GONE);
//        movieLayout.setVisibility(View.VISIBLE);

    }
}
