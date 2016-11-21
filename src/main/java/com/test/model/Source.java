package com.test.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

public class Source implements Serializable {

    /**
     * All possible options for {@code category} parameter you may get sources for
     */
    /*public static final String[] categories = {
        "business", "entertainment", "gaming",
        "general", "music", "science-and-nature",
        "sport", "technology"
    };

    public static final String[] languages = {
        "en", "de", "fr"
    };

    public static final String[] countries = {
        "au", "de", "gb", "in", "it", "us"
    };*/

    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;
    // private String[] urlsToLogos;
    // todo: decide what to do with this one
    private Logos urlsToLogos;
    private String[] sortBysAvailable;
    // non-json
    /*@XmlTransient
    private List<Article> articles;*/

    public Source() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Logos getUrlsToLogos() {
        return urlsToLogos;
    }

    public void setUrlsToLogos(Logos urlsToLogos) {
        this.urlsToLogos = urlsToLogos;
    }

    public String[] getSortBysAvailable() {
        return sortBysAvailable;
    }

    public void setSortBysAvailable(String[] sortBysAvailable) {
        this.sortBysAvailable = sortBysAvailable;
    }

    public static class Logos implements Serializable {
        private String small;
        private String medium;
        private String large;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }

    /*public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }*/
}
