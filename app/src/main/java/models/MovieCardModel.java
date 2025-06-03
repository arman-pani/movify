package models;

import com.google.gson.annotations.SerializedName;

public class MovieCardModel {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("poster_path")
    private String posterPath;
    public MovieCardModel(int id, String posterUrl, String title, String releaseDate){
        this.posterPath = posterUrl;
        this.title = title;
        this.releaseDate = releaseDate;
        this.id = id;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getReleaseDate() { return releaseDate; }
    public String getPosterUrl(){ return posterPath;}


}
