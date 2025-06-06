package activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

import java.util.ArrayList;

import adapters.MovieGridAdapter;
import viewModel.BookmarkActivityViewModel;

public class BookmarkActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TextView placeholderTextView;
    private BookmarkActivityViewModel viewModel;


    private MovieGridAdapter movieGridAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bookmark_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadBookmarks();
    }


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_activity);

        toolbar = findViewById(R.id.bookmarkToolbar);
        recyclerView = findViewById(R.id.bookmarkRecyclerView);
        placeholderTextView = findViewById(R.id.bookmarkPlaceholderTextView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bookmarks");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        movieGridAdapter = new MovieGridAdapter(this, new ArrayList<>() );
        recyclerView.setAdapter(movieGridAdapter);

        viewModel = new ViewModelProvider(this).get(BookmarkActivityViewModel.class);

        viewModel.loadBookmarks();

        viewModel.getBookmarks().observe(this, bookmarks -> {
            if (bookmarks.isEmpty()){
                placeholderTextView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                placeholderTextView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                movieGridAdapter.updateData(bookmarks);
            }
        });

    }
}
