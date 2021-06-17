package org.eolang.internal.net.http;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class HttpResponse {

    private final HttpClient httpClient;
    private java.net.http.HttpResponse<?> httpResponse;


    public HttpResponse(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void byteResponse(final HttpRequest httpRequest) {
        try {
            httpResponse = httpClient.send(httpRequest, BodyHandlers.ofByteArray());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            httpResponse = null;
        }
    }

    public void stringResponse(final HttpRequest httpRequest, Charset charset) {
        try {
            httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString(charset));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            httpResponse = null;
        }
    }

    public void fileResponse(final HttpRequest httpRequest, final Path path) {
        try {
            httpResponse = httpClient.send(httpRequest, BodyHandlers.ofFile(path));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public java.net.http.HttpResponse<?> getHttpResponse() {
        return this.httpResponse;
    }
}
