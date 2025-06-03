package activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

import java.util.ArrayList;

import adapters.SearchRecyclerViewAdapter;
import viewModel.SearchActivityViewModel;

public class SearchActivity extends AppCompatActivity {

    private SearchActivityViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        EditText searchEditText = findViewById(R.id.searchEditText);
        RecyclerView searchRecyclerView = findViewById(R.id.searchRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchRecyclerView.setLayoutManager(layoutManager);
        SearchRecyclerViewAdapter adapter = new SearchRecyclerViewAdapter(this, new ArrayList<>());
        searchRecyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SearchActivityViewModel.class);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.searchMovies(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Observe ViewModel's LiveData
        viewModel.getMovies().observe(this, movies -> {
            adapter.updateMovieList(movies);
        });
    }
}
