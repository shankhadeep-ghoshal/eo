package org.eolang.internal.io.net.http;


import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient.Redirect;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class HttpClient {

    private final java.net.http.HttpClient.Builder builder = java.net.http.HttpClient.newBuilder();

    public HttpClient setPriority(final int priority) {
        builder.priority(priority);
        return this;
    }

    public HttpClient setTimeout(final long durationInMillis) {
        builder.connectTimeout(Duration.of(durationInMillis, ChronoUnit.MILLIS));
        return this;
    }

    public HttpClient setFollowRedirect(final int redirectConstant) {
        switch (redirectConstant) {
            case 1: {
                builder.followRedirects(Redirect.ALWAYS);
                break;
            }
            case 2: {
                builder.followRedirects(Redirect.NEVER);
                break;
            }
            case 3:
            default: {
                builder.followRedirects(Redirect.NORMAL);
                break;
            }
        }

        return this;
    }

    public HttpClient proxy(final String hostname, final int port) {
        if (hostname == null) {
            builder.proxy(ProxySelector.of(new InetSocketAddress(port)));
        } else {
            builder.proxy(ProxySelector.of(new InetSocketAddress(hostname, port)));
        }

        return this;
    }

    public java.net.http.HttpClient buildClient() {
        return builder.build();
    }
}
