package repository;

import androidx.annotation.NonNull;

import java.util.List;

import models.NewsModel;
import models.NewsResponse;
import network.ApiService;
import network.NewsApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApiClient apiClient = ApiService.getNewsApiClient();

    public NewsRepository() {}

    public interface NewsCallback {
        void onSuccess(List<NewsModel> newsList);
        void onFailure(String error);
    }

    public void getMovieNews(NewsCallback callback) {
        apiClient.getMovieNews("movie").enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getArticles());
                } else {
                    callback.onFailure("Failed to load news blogs");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}
