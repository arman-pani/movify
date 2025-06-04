package activities;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import adapters.SearchRecyclerViewAdapter;
import viewModel.SearchActivityViewModel;

public class SearchActivity extends AppCompatActivity {

    private SearchActivityViewModel viewModel;
    private SearchView searchView;

    private TabLayout tabLayout;

    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;

    private void performSearch(String query, int tabPosition) {
        switch (tabPosition) {
            case 0:
                viewModel.searchMovies(query);
                viewModel.getMovies().observe(this, movies -> searchRecyclerViewAdapter.updateSearchList(movies));
                break;
            case 1:
                viewModel.searchTVs(query);
                viewModel.getTVs().observe(this, tvs -> searchRecyclerViewAdapter.updateSearchList(tvs));

                break;
//            case 2:
//                viewModel.searchPeople(query);
//                break;
            default:
                break;
        }
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        viewModel = new ViewModelProvider(this).get(SearchActivityViewModel.class);

        searchView = findViewById(R.id.directSearchView);

        tabLayout = findViewById(R.id.searchTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Movies"));
        tabLayout.addTab(tabLayout.newTab().setText("TV Shows"));
//        tabLayout.addTab(tabLayout.newTab().setText("People"));


        RecyclerView searchRecyclerView = findViewById(R.id.searchRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(this, new ArrayList<>());
        searchRecyclerView.setAdapter(searchRecyclerViewAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query.trim(), tabLayout.getSelectedTabPosition());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText.trim(), tabLayout.getSelectedTabPosition());
                return true;
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String currentQuery = searchView != null ? searchView.getQuery().toString().trim() : "";

                if (!currentQuery.isEmpty()) {
                    performSearch(currentQuery, tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


    }
}
