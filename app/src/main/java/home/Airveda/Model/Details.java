package home.airveda.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aishwarya on 8/28/2017.
 */

public class Details {
    @SerializedName("date")
    @Expose
    String date;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("url")
    @Expose
    String url;
    @SerializedName("caption")
    @Expose
    String caption;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("description")
    @Expose
    String description;

    String choice;

    public Details(String date, String title, String url, String caption, String name, String description,String choice) {
        this.date=date;
        this.title=title;
        this.url=url;
        this.caption=caption;
        this.name=name;
        this.description=description;
        this.choice=choice;
    }

    public String getDate() { return date; }

    public String getTitle() { return title; }

    public String getCaption() { return caption; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public String getChoice() { return  choice; }

    public String setChoice(String choice) {
        this.choice=choice;
        return choice;
    }

    public String getUrl() { return url; }
}