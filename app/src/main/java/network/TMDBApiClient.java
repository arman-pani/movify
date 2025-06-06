package network;

import models.CreditsResponse;
import models.MovieDetailModel;
import models.MovieResponse;
import models.TVResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBApiClient {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<MovieDetailModel> getMovieDetailsById(
            @Path("movie_id") int movieId
    );

    @GET("movie/{movie_id}/credits")
    Call<CreditsResponse> getMovieCreditsById(
            @Path("movie_id") int movieId
    );

    @GET("search/movie")
    Call<MovieResponse> getMoviesByQuery(
            @Query("query") String query
    );

    @GET("movie/{movie_id}/similar")
    Call<MovieResponse> getSimilarMoviesById(
            @Path("movie_id") int movieId,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("search/tv")
    Call<TVResponse> getTVByQuery(
            @Query("query") String query
    );

    @GET("discover/tv")
    Call<TVResponse> getPopularTV(
            @Query("language") String language,
            @Query("page") int page,
            @Query("sort_by") String sortBy
    );



}
