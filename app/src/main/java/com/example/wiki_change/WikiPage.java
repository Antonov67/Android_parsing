package com.example.wiki_change;

public class WikiPage {

    private String url;
    private String date;
    private String newDate;
    private boolean isChange;

    public WikiPage(String url) {
        isChange = false;
        date = "";
        newDate = "";
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getNewDate() {
        return newDate;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public void setChange(boolean change) {
        isChange = change;
    }

    @Override
    public String toString() {
        return "WikiPage{" +
                "url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", newDate='" + newDate + '\'' +
                ", isChange=" + isChange +
                '}';
    }
}
