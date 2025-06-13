package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

import adapters.NewsRecyclerViewAdapter;
import viewModel.NewsFragmentViewModel;

public class NewsFragment extends Fragment {
    private NewsFragmentViewModel viewModel;
    private RecyclerView recyclerView;
    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;


    public NewsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = view.findViewById(R.id.newsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        viewModel = new ViewModelProvider(this).get(NewsFragmentViewModel.class);

        viewModel.getNewsList().observe(getViewLifecycleOwner(), newsList ->{
            if(newsList != null){
                newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(getContext(), newsList);
                recyclerView.setAdapter(newsRecyclerViewAdapter);
            }
        });

        viewModel.fetchNews();
        return view;
    }
}
