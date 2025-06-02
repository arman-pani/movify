package viewModel;

//import static network.ApiService.apiClient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import models.MovieCardModel;
import network.ApiService;
import repository.MovieRepository;
import repository.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragmentViewModel extends ViewModel {
    private final MovieRepository movieRepository = new MovieRepository();

    private final MutableLiveData<List<MovieCardModel>> popularMovies = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<List<MovieCardModel>> getPopularMovies() {
        return  popularMovies;
    }

    public  LiveData<String> getErrorMessage(){
        return errorMessage;
    }

    public  void fetchPopularMovies(){

//        ApiService.getTMDBApiClient().getPopularMovies("en-US",1).enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//                System.out.println(response.body());;
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                System.out.println(t.toString());
//            }
//        });
        movieRepository.getPopularMovies(new MovieRepository.PopularMoviesCallback() {
            @Override
            public void onSuccess(List<MovieCardModel> movies) {
                popularMovies.postValue(movies);
            }

            @Override
            public void onFailure(String error) {
                errorMessage.postValue(error);
            }
        });
    }
}
