package network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String TMDB_BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMDg5MjFkNzE5OWI5NDczZmFjZTcxNjljY2NkM2RlZSIsIm5iZiI6MTc0ODg1MjkzOS45ODEsInN1YiI6IjY4M2Q2MGNiMGI4Y2MzNWFjYTdmNDI3YyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.HWGw5C7NS2wVdIAKMIagjO-sGqmNslG0hdUPANC3UUM";

    private static final String NEWS_API_BASE_URL = "https://newsapi.org/v2/";
    private static final String NEWS_API_KEY = "a208afabd7a145e9bb9b2fa9c5853dca";

    private static TMDBApiClient tmdbApiClient;
    private static NewsApiClient newsApiClient;

    private static OkHttpClient createClient(String authHeader) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor headerInterceptor = chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", authHeader)
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        };

        return new OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(logging)
                .build();
    }

    private static Retrofit createRetrofit(String baseUrl, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static TMDBApiClient getTMDBApiClient() {
        if (tmdbApiClient == null) {
            OkHttpClient client = createClient(TMDB_BEARER_TOKEN);
            tmdbApiClient = createRetrofit(TMDB_BASE_URL, client).create(TMDBApiClient.class);
        }
        return tmdbApiClient;
    }

    public static NewsApiClient getNewsApiClient() {
        if (newsApiClient == null) {
            OkHttpClient client = createClient(NEWS_API_KEY);
            newsApiClient = createRetrofit(NEWS_API_BASE_URL, client).create(NewsApiClient.class);
        }
        return newsApiClient;
    }
}


