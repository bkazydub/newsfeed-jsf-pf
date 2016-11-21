package com.test.beans;

import com.test.model.Article;
import com.test.model.DataContainer;
import com.test.model.Source;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.InternalServerErrorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Backing bean for {@code newsfeed.xhtml} view.
 */
@Named @ViewScoped
public class NewsfeedView implements Serializable {
    @Inject
    private NewsapiService service;
    @Inject
    private SourcesCache sourcesCache;
    // Holds sources that match queried parameters
    private List<Source> customCache;
    // all loaded sources with articles
    private List<DataContainer> data;

    // request parameters
    private String category;
    private String country;
    private String language;
    // single selected source
    private Source source;
    // Indicates if query contains any request parameter, for filtering sources.
    // If custom is set to false, then all sources, found in SourcesCache, will be returned.
    private boolean custom = false;

    // last source that was added to data container
    private int last = 0;
    // the field specifies how many sources are to be loaded in each #loadNext call
    // Could've been parameterizable or declared final instead
    private int quantity = 2;

    @PostConstruct
    void init() {
        data = new ArrayList<>();
        loadNext();
    }

    /**
     * Populate {@see data} with next {@code n} sources if there are any. Number of
     * sources to be loaded are specified by effectively final field which is set to 2.
     */
    // It may be nice to parameterize the method with number of sources to
    // load, but I suppose that hardcoded value of 2 will do.
    public void loadNext() {
        int to = last + quantity;
        List<Source> cache;
        if (custom) {
            cache = customCache;
        } else {
            cache = sourcesCache.getSources();
        }
        int cacheSize = cache.size();
        for (;last < to && last < cacheSize; last++) {
            Source source = cache.get(last);
            List<Article> articles;
            try {
                 articles = service.getArticlesResponse(source.getId()).getArticles();
            } catch (InternalServerErrorException e) {
                articles = new ArrayList<>();
                // logging goes here
                // log.info("Can not fetch articles for " + source.getId() + ". Reason: " + e.getMessage());
            }
            data.add(new DataContainer(source, articles));
        }
    }

    /**
     * Filter sources based on query parameters. Subsequent calls to {@link #loadNext()} will load data
     * from the filtered sources. Note that if {@code this.source != null} the source will be the only one
     * available in {@link #getData()}, i.e. it takes precedence over other request parameters.
     * If all parameters are absent or empty then all available
     * (by <a href="https://newsapi.org">news service</a>) sources will be used.
     * @return empty {@code String} to recreate a view
     */
    public String filterSources() {
        last = 0;
        data.clear();
        if (source == null) {
            if (category == null && language == null && country == null) {
                // SourcesCache for the rescue!
                custom = false;
            } else {
                custom = true;
                try {
                    // Retrieve sources accordingly to request
                    customCache = service.getSourcesResponse(category, language, country).getSources();
                } catch (InternalServerErrorException e) {
                    // This means there are no sources for specified criteria.
                    // Please refer to NewsapiService#getSourcesResponse to see why it is handled this way
                    customCache = new ArrayList<>();
                }
            }
        } else {
            // if source is specified as request parameter, then it is going to be
            // the only customCache entry
            customCache = new ArrayList<>();
            customCache.add(source);
            custom = true;
        }
        loadNext();
        // create new view
        return "";
    }

    /**
     * List of {@code Source}s with {@code Article}s.
     * @return data list
     */
    public List<DataContainer> getData() {
        return data;
    }

    /**
     * {@code category} request parameter for News API sources endpoint.
     * If any of these parameters (other are {@link #getCountry()} and {@link #getLanguage()}) is set to value,
     * other than {@code null}, sources will be retrieved (in {@link #filterSources()} based on these parameters
     * @return currently set value.
     * @see NewsapiService
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set current {@code category} request parameter.
     * @param category valid values are given in {@link SourcesCache#getCategories()}
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * {@code country} request parameter for News API sources endpoint.
     * @return currently set value.
     * @see NewsapiService, getCategory()
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set current {@code country} request parameter.
     * @param country valid values are given in {@link SourcesCache#getCountries()}
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * {@code language} request parameter for News API sources endpoint.
     * @return currently set value.
     * @see NewsapiService, getCategory()
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Set current {@code language} request parameter.
     * @param language valid values are given in {@link SourcesCache#getLanguages()}
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Retrieve list of currently used sources.
     * @return list of currently used sources
     */
    public List<Source> getSourcesCache() {
        if (custom) {
            return customCache;
        } else {
            return sourcesCache.getSources();
        }
    }

    /**
     * Get specified source if any.
     * @return currently selected {@code source}
     */
    public Source getSource() {
        return source;
    }

    /**
     * Set {@code Source} requested in query
     * @param source the source being requested
     */
    public void setSource(Source source) {
        this.source = source;
    }
}
