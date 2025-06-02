package repository;

import java.util.List;

import models.MovieCardModel;
import models.MovieDetailModel;
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

        public void getMovieDetailsById(MovieDetailCallback callback, int movieId) {
            apiClient.getMovieDetailsById(movieId).enqueue(new Callback<MovieDetailResponse>() {
                @Override
                public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body().);
                    } else {
                        callback.onFailure("Failed to load movies");
                    }
                }

                @Override
                public void onFailure(Call<MovieDetailResponse> call, Throwable t) {

                }
            });
        }


    }

}
