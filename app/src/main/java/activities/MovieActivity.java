package activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.movieapp.R.*;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import adapters.CastRecyclerViewAdapter;
import adapters.CrewRecyclerViewAdapter;
import adapters.SimilarRecyclerViewAdapter;
import viewModel.MovieActivityViewModel;

public class MovieActivity extends AppCompatActivity {
    private int movieId;

    private MovieActivityViewModel viewModel;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_appbar_menu, menu);
        MenuItem bookmarkItem = menu.findItem(R.id.toolbar_bookmark);

        viewModel.getMovieDetails().observe(this, movie -> {
            if (movie != null) {
                if (viewModel.isBookmarked()) {
                    bookmarkItem.setIcon(R.drawable.bookmark_saved);
                } else {
                    bookmarkItem.setIcon(R.drawable.bookmark);
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.toolbar_bookmark) {
            if (viewModel.isBookmarked()) {
                viewModel.removeBookmark();
                item.setIcon(R.drawable.bookmark);
            } else {
                viewModel.addBookmark();
                item.setIcon(R.drawable.bookmark_saved);
            }
        } else if (item.getItemId() == R.id.toolbar_save) {
            shareMovieMethod();
        }
        return true;
    }

    private void shareMovieMethod(){
        viewModel.getMovieDetails().observe(this, details->{
            String message = "🎬 Movie Title: " + details.getTitle() + "\n\n"
                    + "⏱ Duration: " + details.getRuntime() + " mins" + "\n\n"
                    + "📝 Description: " + details.getOverview();

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, "Share via");
            startActivity(shareIntent);
        });

    }

    private void showBottomSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MovieActivity.this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(layout.movie_bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        TextView shareButton = bottomSheetView.findViewById(R.id.shareButton);
        shareButton.setOnClickListener(v -> {
            shareMovieMethod();
            bottomSheetDialog.dismiss();
        });

        TextView bookmarkButton = bottomSheetView.findViewById(R.id.bookmarkButton);
        bookmarkButton.setOnClickListener(v -> {
            viewModel.addBookmark();
            bottomSheetDialog.dismiss();
        });
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.movie_activity);

        ImageView headerImageView = findViewById(R.id.headerImageView);
        TextView movieNameTextView = findViewById(R.id.movieNameTextView);
        TextView taglineTextView = findViewById(R.id.taglineTextView);
        TextView directorNameTextView = findViewById(R.id.directorNameTextView);
        ImageView posterImageView = findViewById(R.id.posterImageView);
        TextView yearTextView = findViewById(R.id.yearTextView);
        TextView durationTextView = findViewById(R.id.durationTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView trailerButton = findViewById(R.id.trailerTextButton);
        RecyclerView castRecyclerView = findViewById(R.id.castRecyclerView);
        RecyclerView crewRecyclerView = findViewById(R.id.crewRecyclerView);
        RecyclerView similarRecyclerView = findViewById(R.id.similarMoviesRecyclerView);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingToolbar);
        FloatingActionButton movieFAB = findViewById(R.id.movieFAB);


        movieFAB.setOnClickListener(v -> showBottomSheet());

        Toolbar toolbar = findViewById(R.id.movieToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());



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
            collapsingToolbar.setTitle(details.getTitle());

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


    }
}
