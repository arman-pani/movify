package viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import helpers.BookmarkDatabaseHelper;
import models.MovieCardModel;

public class BookmarkActivityViewModel extends AndroidViewModel {
    private BookmarkDatabaseHelper bookmarkDatabaseHelper;
    private final MutableLiveData<List<MovieCardModel>> bookmarks = new MutableLiveData<>();

    public LiveData<List<MovieCardModel>> getBookmarks(){return bookmarks;}


    public BookmarkActivityViewModel(@NotNull Application application){
        super(application);
        bookmarkDatabaseHelper = new BookmarkDatabaseHelper(application);
    }
    public void loadBookmarks(){
        bookmarks.postValue(bookmarkDatabaseHelper.getAllBookmarks());
    }

}
