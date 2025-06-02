package models;

import com.google.gson.annotations.SerializedName;

public class MovieCardModel {
    @SerializedName("id")
    private int id;

    @SerializedName("poster_path")
    private String posterPath;
    public MovieCardModel(int id, String posterUrl){
        this.posterPath = posterUrl;
        this.id = id;
    }

    public int getId() { return id; }

    public String getPosterUrl(){ return posterPath;}


}
