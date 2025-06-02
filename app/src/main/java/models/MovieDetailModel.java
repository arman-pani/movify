package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MovieDetailModel {
    private String name;
    private String directorName;
    private String yearOfRelease;

    private int durationInMinutes;

    private String description;

    private String posterLink;
    private String trailerLink;

    private  String headerImageLink;

    public MovieDetailModel(String name, String directorName, String yearOfRelease, int durationInMinutes, String description, String posterLink, String trailerLink, String headerImageLink) {
        this.name = name;
        this.directorName = directorName;
        this.yearOfRelease = yearOfRelease;
        this.durationInMinutes = durationInMinutes;
        this.description = description;
        this.posterLink = posterLink;
        this.trailerLink = trailerLink;
        this.headerImageLink = headerImageLink;

    }

    public String getName() {
        return name;
    }

    public String getDirectorName() {
        return directorName;
    }

    public String getYearOfRelease() {
        return yearOfRelease;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public String getDescription() {
        return description;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public String getHeaderImageLink() {
        return headerImageLink;
    }
}
