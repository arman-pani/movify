package viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import models.CastModel;
import models.CrewModel;
import models.MovieCardModel;
import models.MovieDetailModel;
import repository.MovieRepository;

public class MovieActivityViewModel extends ViewModel {
    private final MovieRepository repository = new MovieRepository();

    private final MutableLiveData<MovieDetailModel> movieDetails = new MutableLiveData<>();
    private final MutableLiveData<List<CrewModel>> crewList = new MutableLiveData<>();
    private  final MutableLiveData<List<CastModel>> castList = new MutableLiveData<>();
    private final MutableLiveData<List<MovieCardModel>> similarMovies = new MutableLiveData<>();
    public LiveData<MovieDetailModel> getMovieDetails() { return  movieDetails;}
    public LiveData<List<CrewModel>> getCrewList() {return crewList;}
    public LiveData<List<CastModel>> getCastList() {return castList;}

    public LiveData<List<MovieCardModel>> getSimilarMovies() {return similarMovies;}



    public void loadMovieData (int movieId){
        repository.getMovieDetailsById(movieId, new MovieRepository.MovieDetailCallback() {
            @Override
            public void onSuccess(MovieDetailModel data) {
                movieDetails.postValue(data);
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error: " + error);
            }
        });
        repository.getMovieCreditsById(movieId, new MovieRepository.CreditsCallback() {
            @Override
            public void onSuccess(List<CastModel> cast, List<CrewModel> crew) {
                crewList.postValue(crew);
                castList.postValue(cast);
            }
            @Override
            public void onFailure(String error) {
                System.out.println("Error: " + error);
            }
        });

        repository.getSimilarMoviesById(movieId, new MovieRepository.SimilarMoviesCallback() {
            @Override
            public void onSuccess(List<MovieCardModel> movies) {
                similarMovies.postValue(movies);
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error: " + error);
            }
        });
    }
}
