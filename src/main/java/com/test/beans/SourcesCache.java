package com.test.beans;

import com.test.model.Source;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * The instance of the class holds (acts like a cache, hence the name) all {@link com.test.model.Source}s available by <a href="https://newsapi.org">newsapi.org</a>.
 */
// Application scope is chosen because those sources are the same for every user
// and they are very unlikely to change.
@Named @ApplicationScoped
public class SourcesCache {

    // You may think that this is not an appropriate place to store following
    // possible values (namely, categories, languages and conutries). In fact I do.
    // It may be better to store those in NewsapiService as they are a part of the API, but
    // for convenience of reaching these they are there.
    /**
     * All possible values for {@code category} parameter for /sources endpoint
     */
    private final String[] categories = {
            "business", "entertainment", "gaming",
            "general", "music", "science-and-nature",
            "sport", "technology"
    };

    /**
     * All possible values for {@code language} parameter for /sources endpoint
     */
    private final String[] languages = {
            "en", "de", "fr"
    };

    /**
     * All possible values for {@code country} parameter for /sources endpoint
     */
    private final String[] countries = {
            "au", "de", "gb", "in", "it", "us"
    };

    private List<Source> sources;
    @Inject
    private NewsapiService service;

    @PostConstruct
    private void init() {
        sources = service.getSourcesResponse().getSources();
    }

    /**
     * Get cached list of sources.
     * @return list of all sources available
     */
    public List<Source> getSources() {
        // It may be a good idea to do the following
        // but as I am the only one to use the class, and I am positive I will not modify the list
        // I will not do so. (Though this may be plain stupid.)
        // return Collections.unmodifiableList(sources);
        return sources;
    }

    public String[] getCategories() {
        return categories;
    }

    public String[] getLanguages() {
        return languages;
    }

    public String[] getCountries() {
        return countries;
    }
}
