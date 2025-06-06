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
import com.facebook.shimmer.ShimmerFrameLayout;

import adapters.MovieGridAdapter;
import adapters.ShimmerAdapter;
import viewModel.MoviesFragmentViewModel;

public class MoviesFragment extends Fragment {

    private MoviesFragmentViewModel viewModel;
    private ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView shimmerRecyclerView;
    private RecyclerView recyclerViewMovies;
    private MovieGridAdapter movieGridAdapter;

    private final int spanCount = 4;

    public MoviesFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_fragment, container, false);

        initViews(view);
        initViewModel();
        initScrollListener();

        viewModel.fetchPopularMovies();

        return view;
    }

    private void initViews(View view) {
        shimmerFrameLayout = view.findViewById(R.id.shimmerContainer);
        shimmerRecyclerView = view.findViewById(R.id.gridRecyclerViewShimmer);
        shimmerRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        shimmerRecyclerView.setAdapter(new ShimmerAdapter());

        recyclerViewMovies = view.findViewById(R.id.recyclerViewMovies);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MoviesFragmentViewModel.class);
    }

    private void initObservers() {
        viewModel.getPopularMovies().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerViewMovies.setVisibility(View.VISIBLE);

                if (movieGridAdapter == null) {
                    movieGridAdapter = new MovieGridAdapter(getActivity(), movies);
                    recyclerViewMovies.setAdapter(movieGridAdapter);
                } else {
                    movieGridAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObservers();
    }

    private void initScrollListener() {
        recyclerViewMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager == null) return;

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!viewModel.isLoading() && !viewModel.isLastPage()) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        loadMoreItems();
                    }
                }
            }
        });
    }

    private void loadMoreItems() {
        int nextPage = viewModel.getCurrentPage() + 1;
        viewModel.setCurrentPage(nextPage);
        viewModel.setLoading(true);
        viewModel.fetchMorePopularMovies(nextPage);
    }
}
