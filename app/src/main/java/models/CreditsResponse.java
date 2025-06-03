package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditsResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("cast")
    private List<CastModel> cast;
    @SerializedName("crew")
    private List<CrewModel> crew;

    public CreditsResponse(int id, List<CastModel> cast, List<CrewModel> crew){
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    public int getId() {return id;}
    public List<CastModel> getCast() {return cast;}
    public List<CrewModel> getCrew() {return crew;}
}
