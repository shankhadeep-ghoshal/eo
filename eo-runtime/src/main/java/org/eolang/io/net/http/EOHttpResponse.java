package org.eolang.io.net.http;

import java.net.http.HttpRequest;
import java.nio.charset.Charset;
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

		public EOstring EOstringResponse(final EOHttpRequest request, final EOstring charset) {
				return new EOstring(response.stringResponse((HttpRequest) request._getData().toObject(),
						Charset.forName(charset._getData().toString())).body());
		}

		public EOstring EOfileResponse(final EOHttpRequest request, final EOstring path) {
				return new EOstring(response.fileResponse((HttpRequest) request._getData().toObject(),
						Paths.get(path._getData().toString())).body().toAbsolutePath().toString());
		}

		public EOByteArray EObyteArrayResponse(final EOHttpRequest request) {
				return new EOByteArray(
						response.byteResponse((HttpRequest) request._getData().toObject()).body());
		}
}
