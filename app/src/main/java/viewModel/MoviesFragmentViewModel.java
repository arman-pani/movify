package viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import models.MovieCardModel;
import repository.MovieRepository;

public class MoviesFragmentViewModel extends ViewModel {
    private final MovieRepository movieRepository = new MovieRepository();

    private final MutableLiveData<List<MovieCardModel>> popularMovies = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private List<MovieCardModel> currentMovieList = new ArrayList<>();
    public LiveData<List<MovieCardModel>> getPopularMovies() {
        return  popularMovies;
    }

    public  LiveData<String> getErrorMessage(){
        return errorMessage;
    }

    private boolean isLoading = false;
    private boolean isLastPage = false;

    private int currentPage = 1;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isLoading() {return isLoading;}

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {isLastPage = lastPage;}

    public void setLoading(boolean loading) {isLoading = loading;}

    public  void fetchPopularMovies(){

        movieRepository.getPopularMovies(1, new MovieRepository.PopularMoviesCallback() {
            @Override
            public void onSuccess(List<MovieCardModel> movies) {
                currentMovieList.clear();
                currentMovieList.addAll(movies);
                popularMovies.postValue(currentMovieList);
            }

            @Override
            public void onFailure(String error) {
                errorMessage.postValue(error);
            }
        });
    }

    public void fetchMorePopularMovies(int page) {
        movieRepository.getPopularMovies(page, new MovieRepository.PopularMoviesCallback() {
            @Override
            public void onSuccess(List<MovieCardModel> newMovies) {
                if (newMovies.isEmpty()) {
                    isLastPage = true;
                } else {
                    currentMovieList.addAll(newMovies);
                    popularMovies.postValue(currentMovieList);
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(String error) {
                isLoading = false;
            }
        });
    }
}
