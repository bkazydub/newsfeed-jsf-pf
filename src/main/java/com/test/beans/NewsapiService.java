package com.test.beans;

import com.test.model.ArticlesResponse;
import com.test.model.SourcesResponse;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * <p>
 * The class represents entry point to <a href=" https://newsapi.org/">newsapi.org</a> web service,
 * enabling you to query both <a href=" https://newsapi.org/v1/articles">newsapi.org/v1/articles</a>
 * and <a href=" https://newsapi.org/v1/sources">newsapi.org/v1/sources</a> endpoints.
 * </p>
 *
 * <p>For more info, please refer to <a href="https://newsapi.org/#documentation">service documentation</a>.</p>
 */
@ApplicationScoped
public class NewsapiService {

    /** News API url */
    public static final String API_URL = "https://newsapi.org/v1/";

    private static final String ARTICLES_ENDPOINT = "articles";
    private static final String SOURCES_ENDPOINT = "sources";

    // the key is left for bootstrapping purposes only
    private String apiKey = "4773f235e779468b9445ca392c3ed7ba";

    private Client client;
    private WebTarget articlesTarget;
    private WebTarget sourcesTarget;

    @PostConstruct
    void init() {
        client = ClientBuilder.newClient();
        articlesTarget = client.target(API_URL + ARTICLES_ENDPOINT).queryParam("apiKey", apiKey);
        sourcesTarget = client.target(API_URL + SOURCES_ENDPOINT).queryParam("apiKey", apiKey);
    }

    @PreDestroy
    void dispose() {
        client.close();
    }

    // Sort by is not supported (it is nice to have it, but is not necessary).
    /**
     * Perform get request to <a href=" https://newsapi.org/v1/articles">newsapi.org/v1/articles</a> endpoint.
     * @param sourceId he identifer for the news source or blog you want headlines from. May not be null
     * @return response entity
     * @throws InternalServerErrorException is thrown when response {@code status == "error"}. For why it is
     * like that, please refer to comments in {@link SourcesResponse}.
     * @throws NullPointerException thrown if {@code sourceId == null}
     */
    public ArticlesResponse getArticlesResponse(String sourceId/*, String sortBy*/)
            throws InternalServerErrorException {
        if (sourceId == null)
            throw new NullPointerException("Source id may not be null");

        WebTarget target = articlesTarget.queryParam("source", sourceId);

        return target.request(MediaType.APPLICATION_JSON).get(ArticlesResponse.class);
    }

    /**
     * Performs get request to <a href=" https://newsapi.org/v1/sources">newsapi.org/v1/sources</a> endpoint.
     * Query parameters are used to filter sources.
     * @param category {@code category} query parameter. If {@code category == ""} sources with all possible
     *                                 categories are returned. Values {@code null} and {@code "null"} are
     *                                 treated as empty string.
     * @param language {@code language} query parameter. If {@code language == ""} sources with all possible
     *                                 languages are returned. Values {@code null} and {@code "null"} are
     *                                 treated as empty string.
     * @param country {@code country} query parameter. If {@code country == ""} sources from all possible
     *                         countries are returned. Values {@code null} and {@code "null"} are
     *                         treated as empty string.
     * @return response entity
     * @throws InternalServerErrorException is thrown when response {@code status == "error"}. For why it is
     * like that, please refer to comments in {@link SourcesResponse}.
     */
    public SourcesResponse getSourcesResponse(String category, String language, String country)
            throws InternalServerErrorException {
        WebTarget target = sourcesTarget;

        // those checks may be unnecessary, but...
        // check against "null" is because empty ('select ...') <f:selectItem itemValue="null" ...> produces "null"
        if (category != null && !(category.isEmpty() || category.equals("null")))
            target = target.queryParam("category", category);
        if (language != null && !(language.isEmpty() || language.equals("null")))
            target = target.queryParam("language", language);
        if (country != null && !(country.isEmpty() || country.equals("null")))
            target = target.queryParam("country", country);

        return target.request(MediaType.APPLICATION_JSON).get(SourcesResponse.class);
    }

    /**
     * Equivalent of {@code #getSourcesResponse(null, null, null)}.
     * @return response entity which contains all available sources
     * @throws InternalServerErrorException
     */
    public SourcesResponse getSourcesResponse() throws InternalServerErrorException {
        return getSourcesResponse(null, null, null);
    }
}
