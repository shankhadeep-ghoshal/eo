package org.eolang.internal.net.http;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class HttpRequest {

    private final java.net.http.HttpRequest.Builder httpRequestBuilder;

    public HttpRequest() {
        this.httpRequestBuilder = java.net.http.HttpRequest.newBuilder();
    }

    public void setUri(String uri) {
        try {
            httpRequestBuilder.uri(new URI(uri));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void get() {
        httpRequestBuilder.GET();
    }

    public void post(final byte[] byteArray) {
        httpRequestBuilder.POST(BodyPublishers.ofByteArray(byteArray));
    }

    public void post(final String data) {
        httpRequestBuilder.POST(BodyPublishers.ofString(data));
    }

    public void post(final Path path) {
        try {
            httpRequestBuilder.POST(BodyPublishers.ofFile(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void put(final byte[] byteArray) {
        httpRequestBuilder.PUT(BodyPublishers.ofByteArray(byteArray));
    }

    public void put(final String data) {
        httpRequestBuilder.PUT(BodyPublishers.ofString(data));
    }

    public void put(final Path path) {
        try {
            httpRequestBuilder.PUT(BodyPublishers.ofFile(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        httpRequestBuilder.DELETE();
    }

    public void setHeaders(final List<Header> headersList) {
        headersList
            .forEach(header -> httpRequestBuilder.setHeader(header.getKey(), header.getValue()));
    }

    public void setVersion(int version) {
        if (version == 2) {
            httpRequestBuilder.version(Version.HTTP_2);
        } else {
            httpRequestBuilder.version(Version.HTTP_1_1);
        }
    }

    public void setDuration(long durationMagnitude) {
        httpRequestBuilder.timeout(Duration.of(durationMagnitude, ChronoUnit.MILLIS));
    }

    public java.net.http.HttpRequest buildRequest() {
        return httpRequestBuilder.build();
    }
}
