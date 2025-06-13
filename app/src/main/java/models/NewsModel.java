package models;

import com.google.gson.annotations.SerializedName;

import helpers.DateUtils;

public class NewsModel {
    @SerializedName("author")
    private String author;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("url")
    private String url;
    @SerializedName("urlToImage")
    private String urlToImage;
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("content")
    private String content;

    public NewsModel(String author, String title, String description, String url, String urlToImage, String publishedAt, String content){
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public String getTitle(){return title;}
    public String getAuthor(){return author;}
    public String getDescription(){return description;}
    public String getUrl(){return url;}
    public String getPosterUrl(){return urlToImage;}
    public String getPublishedAt(){return DateUtils.formatNewsDate(publishedAt);}
    public String getContent(){return content;}

}
