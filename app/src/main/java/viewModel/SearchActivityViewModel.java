package viewModel;

import static models.SearchItemModel.convertMovieList;
import static models.SearchItemModel.convertTVList;
import static models.SearchItemModel.convertToSearchItem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import models.MovieCardModel;
import models.SearchItemModel;
import models.TVItemModel;
import repository.MovieRepository;

public class SearchActivityViewModel extends ViewModel {

    private  final MovieRepository repository = new MovieRepository();

    private final MutableLiveData<List<SearchItemModel>> movies = new MutableLiveData<>();

    private final MutableLiveData<List<SearchItemModel>> tvs = new MutableLiveData<>();

    public LiveData<List<SearchItemModel>> getMovies() {return movies;}

    public  LiveData<List<SearchItemModel>> getTVs() {return tvs;}

    public void searchMovies(String query) {
        repository.getMoviesByQuery(query, new MovieRepository.MovieSearchCallback() {
            @Override
            public void onSuccess(List<MovieCardModel> data) {
                movies.postValue(convertMovieList(data));
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error: " + error);
            }
        });
    }

    public void searchTVs(String query) {
        repository.getTVByQuery(query, new MovieRepository.TVSearchCallback() {
            @Override
            public void onSuccess(List<TVItemModel> data) {
                tvs.postValue(convertTVList(data));
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error: " + error);
            }
        });
    }
}
