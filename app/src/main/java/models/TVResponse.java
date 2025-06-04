package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<TVItemModel> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {return page;}

    public List<TVItemModel> getResults() {return results;}

    public int getTotalPages() { return totalPages;}
    public int getTotalResults() { return totalResults;}
}
