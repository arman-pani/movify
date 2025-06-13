package network;

import models.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiClient {
    @GET("everything")
    Call<NewsResponse> getMovieNews(
            @Query("q") String query
    );
}
