package viewModel;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import helpers.BookmarkDatabaseHelper;
import models.CastModel;
import models.CrewModel;
import models.MovieCardModel;
import models.MovieDetailModel;
import repository.MovieRepository;

public class MovieActivityViewModel extends AndroidViewModel {

    private BookmarkDatabaseHelper bookmarkDatabaseHelper;
    private final MovieRepository repository = new MovieRepository();
    private final MutableLiveData<MovieDetailModel> movieDetails = new MutableLiveData<>();

    private final MutableLiveData<List<CrewModel>> crewList = new MutableLiveData<>();
    private  final MutableLiveData<List<CastModel>> castList = new MutableLiveData<>();
    private final MutableLiveData<List<MovieCardModel>> similarMovies = new MutableLiveData<>();
    public LiveData<MovieDetailModel> getMovieDetails() { return  movieDetails;}
    public LiveData<List<CrewModel>> getCrewList() {return crewList;}
    public LiveData<List<CastModel>> getCastList() {return castList;}
    public LiveData<List<MovieCardModel>> getSimilarMovies() {return similarMovies;}

    public int getMovieId() { return  movieDetails.getValue().getId();}

    private boolean isBookmarked = false;
    public boolean isBookmarked() { return isBookmarked; }

    public void checkBookmarkStatus() {
        if (movieDetails.getValue() != null) {
            isBookmarked = bookmarkDatabaseHelper.isBookmarked(getMovieId());
        }
    }

    public MovieActivityViewModel(@NotNull Application application){
        super(application);
        bookmarkDatabaseHelper = new BookmarkDatabaseHelper(application);
    }

    public void removeBookmark(){
        isBookmarked = false;
        if(bookmarkDatabaseHelper.isBookmarked(getMovieId())){
            bookmarkDatabaseHelper.removeBookmark(getMovieId());
            Toast.makeText(getApplication(), "Movie is removed from bookmarks!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addBookmark(){
        isBookmarked = true;
        MovieCardModel movie = new MovieCardModel(
                movieDetails.getValue().getId(),
                movieDetails.getValue().getPosterPath(),
                movieDetails.getValue().getTitle(),
                movieDetails.getValue().getReleaseDate()
        );

        if(!bookmarkDatabaseHelper.isBookmarked(getMovieId())){
            bookmarkDatabaseHelper.insertBookmark(movie);
            Toast.makeText(getApplication(), "Movie is bookmarked!", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadMovieData (int movieId){
        repository.getMovieDetailsById(movieId, new MovieRepository.MovieDetailCallback() {
            @Override
            public void onSuccess(MovieDetailModel data) {
                movieDetails.postValue(data);
                isBookmarked = bookmarkDatabaseHelper.isBookmarked(data.getId());
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
