package network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMDg5MjFkNzE5OWI5NDczZmFjZTcxNjljY2NkM2RlZSIsIm5iZiI6MTc0ODg1MjkzOS45ODEsInN1YiI6IjY4M2Q2MGNiMGI4Y2MzNWFjYTdmNDI3YyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.HWGw5C7NS2wVdIAKMIagjO-sGqmNslG0hdUPANC3UUM";

    private static TMDBApiClient apiClient;

    public static TMDBApiClient getTMDBApiClient() {
        if (apiClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Authorization", BEARER_TOKEN)
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    })
                    .addInterceptor(logging)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            apiClient = retrofit.create(TMDBApiClient.class);
        }

        return apiClient;
    }
}
