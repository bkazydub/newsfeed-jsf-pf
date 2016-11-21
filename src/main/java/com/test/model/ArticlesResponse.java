package com.test.model;

import java.io.Serializable;
import java.util.List;

/**
 * Response model for <a href=" https://newsapi.org/v1/articles">newsapi.org/v1/articles</a> endpoint.
 * Contains response status, list of sources. If {@code status == "error"} then {@code message} is populated
 * with message describing what the error is about.
 */
public class ArticlesResponse implements Serializable {
    private String status;
    // populated in case if status == "error"
    private String message;
    private String source;
    private String sortBy;
    private List<Article> articles;

    public ArticlesResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
