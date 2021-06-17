package org.eolang.io.net.http;

import org.eolang.EOint;
import org.eolang.EOstring;
import org.eolang.core.EOObject;
import org.eolang.core.data.EOData;
import org.eolang.internal.net.http.HttpClient;

/**
 * A class representing an HTTP client
 */
public class EOHttpClient extends EOObject {

    private final HttpClient httpClient;

    public EOHttpClient() {
        httpClient = new HttpClient();
    }

    public EOHttpClient EOsetPriority(final EOint priority) {
        httpClient.setPriority(Math.toIntExact(priority._getData().toInt()));
        return this;
    }

    public EOHttpClient EOsetFollowRedirect(final EOint redirect) {
        httpClient.setFollowRedirect(Math.toIntExact(redirect._getData().toInt()));
        return this;
    }

    public EOHttpClient EOsetProxy(final EOstring hostname, final EOint port) {
        httpClient.proxy(hostname._getData().toString(), Math.toIntExact(port._getData().toInt()));
        return this;
    }

    @Override
    public EOData _getData() {
        return new EOData(this.httpClient);
    }
}
