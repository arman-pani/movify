package models;

import com.google.gson.annotations.SerializedName;

public class CrewModel {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_path")
    private String profilePath;
    @SerializedName("job")
    private String job;

    public CrewModel(int id, String name, String profilePath, String job){
        this.id = id;
        this.name = name;
        this.profilePath = profilePath;
        this.job = job;
    }

    public int getId(){ return id;}
    public String getName(){return name;}
    public String getProfilePath(){return profilePath;}
    public String getJob(){return job;}

}
