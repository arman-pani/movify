package network;

import repository.MovieDetailResponse;
import repository.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDBApiClient {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<MovieDetailResponse> getMovieDetailsById(
            @Query("movie_id") int movieId
    );
}
