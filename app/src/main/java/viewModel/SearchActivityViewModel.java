package viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import models.MovieCardModel;
import repository.MovieRepository;

public class SearchActivityViewModel extends ViewModel {

    private  final MovieRepository repository = new MovieRepository();

    private final MutableLiveData<List<MovieCardModel>> movies = new MutableLiveData<>();

    public LiveData<List<MovieCardModel>> getMovies() {return movies;}

    public void searchMovies(String query) {
        repository.getMoviesByQuery(query, new MovieRepository.SearchCallback() {
            @Override
            public void onSuccess(List<MovieCardModel> data) {
                movies.postValue(data);
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error: " + error);
            }
        });
    }
}
