package repository;

import java.util.List;

import models.CastModel;
import models.CreditsResponse;
import models.CrewModel;
import models.MovieCardModel;
import models.MovieDetailModel;
import models.MovieResponse;
import models.TVItemModel;
import models.TVResponse;
import network.ApiService;
import network.TMDBApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieRepository {

    private final TMDBApiClient apiClient = ApiService.getTMDBApiClient();

    public MovieRepository() {}

    public interface PopularMoviesCallback {
        void onSuccess(List<MovieCardModel> movies);
        void onFailure(String error);
    }

    public interface MovieDetailCallback {
        void onSuccess(MovieDetailModel movieDetails);
        void onFailure(String error);
    }

    public interface CreditsCallback {
        void onSuccess(List<CastModel> castList, List<CrewModel> crewList);
        void onFailure(String error);
    }

    public interface MovieSearchCallback {
        void onSuccess(List<MovieCardModel> searchData);
        void onFailure(String error);
    }

    public  interface SimilarMoviesCallback {
        void onSuccess(List<MovieCardModel> movies);
        void onFailure(String error);
    }
    public interface TVSearchCallback {
        void onSuccess(List<TVItemModel> searchData);
        void onFailure(String error);
    }



    public void getPopularMovies(PopularMoviesCallback callback) {
        apiClient.getPopularMovies("en-US", 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getResults());
                } else {
                    callback.onFailure("Failed to load movies");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });




    }

    public void getMovieDetailsById(int movieId,MovieDetailCallback callback) {

        apiClient.getMovieDetailsById(movieId).enqueue(new Callback<MovieDetailModel>() {
            @Override
            public void onResponse(Call<MovieDetailModel> call, Response<MovieDetailModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to load movie details");
                }
            }

            @Override
            public void onFailure(Call<MovieDetailModel> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void getMovieCreditsById(int movieId, CreditsCallback callback) {

        apiClient.getMovieCreditsById(movieId).enqueue(new Callback<CreditsResponse>() {
            @Override
            public void onResponse(Call<CreditsResponse> call, Response<CreditsResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    callback.onSuccess(response.body().getCast(), response.body().getCrew());
                } else {
                    callback.onFailure("Failed to load movie credits");
                }
            }

            @Override
            public void onFailure(Call<CreditsResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void getMoviesByQuery(String query, MovieSearchCallback callback) {

        apiClient.getMoviesByQuery(query).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getResults());
                } else {
                    callback.onFailure("Failed to load movies");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void getSimilarMoviesById(int movieId, SimilarMoviesCallback callback ){

        apiClient.getSimilarMoviesById(movieId, "en-US", 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    callback.onSuccess(response.body().getResults());
                    System.out.println("Similar Movies List : " + response.body().getResults());
                } else {
                    callback.onFailure("Failed to load similar movies");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void getTVByQuery(String query, TVSearchCallback callback) {

        apiClient.getTVByQuery(query).enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getResults());
                } else {
                    callback.onFailure("Failed to load movies");
                }
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }


}
