package models;

import java.util.List;

public class NewsResponse {
    private String status;
    private int totalResults;
    private List<NewsModel> articles;
    public List<NewsModel> getArticles() { return articles;}

}
