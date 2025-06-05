package models;

import java.util.ArrayList;
import java.util.List;

public class SearchItemModel {
    private int id;
    private String title;
    private String subTitle;
    private String posterPath;

    public SearchItemModel(int id, String title, String subTitle, String posterPath){
        this.id = id;
        this.posterPath = posterPath;
        this.subTitle = subTitle;
        this.title = title;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getSubTitle() { return subTitle; }
    public String getPosterPath() { return posterPath; }

    public static SearchItemModel convertToSearchItem(MovieCardModel movie) {
        return new SearchItemModel(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseDate(),
                movie.getPosterUrl()
        );
    }

    public  static SearchItemModel convertToSearchItem(TVItemModel tv) {
        return new SearchItemModel(
                tv.getId(),
                tv.getTitle(),
                tv.getReleaseDate(),
                tv.getPosterUrl()
        );
    }

    public static List<SearchItemModel> convertMovieList(List<MovieCardModel> movieList) {
        List<SearchItemModel> searchItems = new ArrayList<>();
        for (MovieCardModel movie : movieList) {
            searchItems.add(convertToSearchItem(movie));
        }
        return searchItems;
    }

    public static List<SearchItemModel> convertTVList(List<TVItemModel> tvList) {
        List<SearchItemModel> searchItems = new ArrayList<>();
        for (TVItemModel tv : tvList) {
            searchItems.add(convertToSearchItem(tv));
        }
        return searchItems;
    }

}
