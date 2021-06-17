package org.eolang.internal.io.net.http;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class HttpResponse {

    private final HttpClient httpClient;

    public HttpResponse(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public java.net.http.HttpResponse<byte[]> byteResponse(final HttpRequest httpRequest) {
        try {
            return httpClient.send(httpRequest, BodyHandlers.ofByteArray());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public java.net.http.HttpResponse<String> stringResponse(final HttpRequest httpRequest,
        Charset charset) {
        try {
            return httpClient.send(httpRequest, BodyHandlers.ofString(charset));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public java.net.http.HttpResponse<Path> fileResponse(final HttpRequest httpRequest,
        final Path path) {
        try {
            return httpClient.send(httpRequest, BodyHandlers.ofFile(path));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
