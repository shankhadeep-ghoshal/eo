package org.eolang.io.net.http;

import java.net.http.HttpRequest;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eolang.EOByteArray;
import org.eolang.EOstring;
import org.eolang.core.EOObject;
import org.eolang.internal.net.http.HttpClient;
import org.eolang.internal.net.http.HttpResponse;

/**
 * Represents an HTTP response
 **/
public class EOHttpResponse extends EOObject {

		private final HttpResponse response;

		public EOHttpResponse(final EOHttpClient client) {
				HttpClient httpClient = (HttpClient) client._getData().toObject();
				response = new HttpResponse(httpClient.buildClient());
		}

		public EOstring stringResponse(final EOHttpRequest request, final EOstring charset) {
				response.stringResponse((HttpRequest) request._getData().toObject(),
						Charset.forName(charset._getData().toString()));
				return new EOstring((String) response.getHttpResponse().body());
		}

		public EOstring fileResponse(final EOHttpRequest request, final EOstring path) {
				response.fileResponse((HttpRequest) request._getData().toObject(),
						Paths.get(path._getData().toString()));
				return new EOstring(((Path) response.getHttpResponse().body()).toAbsolutePath().toString());
		}

		public EOByteArray byteArrayResponse(final EOHttpRequest request) {
				response.byteResponse((HttpRequest) request._getData().toObject());
				return new EOByteArray((byte[]) response.getHttpResponse().body());
		}
}
