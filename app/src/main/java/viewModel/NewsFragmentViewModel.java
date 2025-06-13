package viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import models.NewsModel;
import repository.NewsRepository;

public class NewsFragmentViewModel extends ViewModel {

    private final NewsRepository newsRepository = new NewsRepository();
    private final MutableLiveData<List<NewsModel>> currentNewsList = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<List<NewsModel>> getNewsList() {return currentNewsList;}

    public LiveData<String> getErrorMessage() { return  errorMessage;}

    public void fetchNews(){
        newsRepository.getMovieNews(new NewsRepository.NewsCallback() {
            @Override
            public void onSuccess(List<NewsModel> newsList) {
                currentNewsList.postValue(newsList);
            }

            @Override
            public void onFailure(String error) {
                errorMessage.postValue(error);
            }
        });
    }
}
