package com.test.model;

import java.util.List;

/**
 * The sole purpose of the class it to tie {@link Source} and relevant {@link Article} objects
 * for convenient presentation (that is in view).
 */
public class DataContainer {
    private Source source;
    private List<Article> articles;

    public DataContainer(Source source, List<Article> articles) {
        this.source = source;
        this.articles = articles;
    }

    /**
     * {@code Source} object.
     * @return a {@code source}
     */
    public Source getSource() {
        return source;
    }

    /**
     * Article headlines that belong to {@code source}.
     * @return list of articles
     */
    public List<Article> getArticles() {
        return articles;
    }
}
