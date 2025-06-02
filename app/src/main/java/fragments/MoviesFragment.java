package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

import java.util.List;

import adapters.MovieGridAdapter;
import models.MovieCardModel;
import models.MovieDetailModel;
import viewModel.MoviesFragmentViewModel;

public class MoviesFragment extends Fragment {
    private MoviesFragmentViewModel viewModel;

    public MoviesFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_fragment, container, false);

        RecyclerView recyclerViewMovies = view.findViewById(R.id.recyclerViewMovies);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this.getContext(), 4));

        viewModel = new ViewModelProvider(this).get(MoviesFragmentViewModel.class);

        viewModel.getPopularMovies().observe(getViewLifecycleOwner(), movieCardModels -> {
            if (movieCardModels != null){
                MovieGridAdapter movieGridAdapter = new MovieGridAdapter(getActivity(), movieCardModels);
                recyclerViewMovies.setAdapter(movieGridAdapter);
            }
        });

        viewModel.fetchPopularMovies();

        return view;
    }
}
