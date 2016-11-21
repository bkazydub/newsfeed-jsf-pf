package com.test.model;

import java.io.Serializable;
import java.util.List;

/**
 * Response model for <a href=" https://newsapi.org/v1/sources">newsapi.org/v1/sources</a> endpoint.
 * Contains response status, list of sources. If {@code status == "error"} then {@code message} is populated
 * with message describing what the error is about.
 */
public class SourcesResponse implements Serializable {
    // represents response status (either "ok" or "error")
    private String status;

    // IMPORTANT
    // Regarding to why  NewsapiService#getArticlesResponse and NewsapiService#getSourcesResponse
    // are throwing InternalServerErrorException on response.status == "error" instead of
    // 'normal' approach (i.e. proceed as if it is all good, and perform status check on the consumer's side):
    // the problem is I keep getting json parser exception while trying to parse response json, which I think
    // (couldn't get actual text message describing what the problem is) is due to response
    // deserialization problems on missing List<Source>, perhaps? I could not fix this by annotating
    // response entities (SourceResponse & ArticlesResponse) with @XMLRootElement and its properties
    // with @XmlElement with nillable set to true and required to false.
    // Probably I should have tried com.fasterxml.jackson.*, but I haven't atm.
    // (What a weak excuse this is!)

    // populated in case if status == "error"
    private String message;
    // array of sources in response (if status == "ok")
    private List<Source> sources;

    public SourcesResponse() {
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

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
