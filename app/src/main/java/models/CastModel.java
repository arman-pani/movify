package models;

import com.google.gson.annotations.SerializedName;

public class CastModel {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_path")
    private String profilePath;
    @SerializedName("character")
    private String character;

    public CastModel(int id, String name, String profilePath, String character){
        this.id = id;
        this.name = name;
        this.profilePath = profilePath;
        this.character = character;
    }

    public int getId(){ return id;}
    public String getName(){return name;}
    public String getProfilePath(){return "https://image.tmdb.org/t/p/w500" + profilePath;}
    public String getCharacter(){return character;}

}
