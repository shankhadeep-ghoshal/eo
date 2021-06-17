package org.eolang.io.net.http;

import java.nio.file.Paths;
import org.eolang.EOByteArray;
import org.eolang.EOint;
import org.eolang.EOstring;
import org.eolang.core.EOObject;
import org.eolang.core.data.EOData;
import org.eolang.internal.net.http.HttpRequest;

/**
 * Represents an HTTP request
 **/
public class EOHttpRequest extends EOObject {

		private final HttpRequest httpRequest;

		public EOHttpRequest() {
				this.httpRequest = new HttpRequest();
		}

		public EOHttpRequest EOsetUri(final EOstring uri) {
				httpRequest.setUri(uri._getData().toString());
				return this;
		}

		public EOHttpRequest EOsetVersion(final EOint version) {
				httpRequest.setVersion(Math.toIntExact(version._getData().toInt()));
				return this;
		}

		public EOHttpRequest EOsetDuration(final EOint duration) {
				httpRequest.setDuration(duration._getData().toInt());
				return this;
		}

		public EOHttpRequest EOget() {
				httpRequest.get();
				return this;
		}

		public EOHttpRequest EOpost(final EOstring string) {
				httpRequest.post(string._getData().toString());
				return this;
		}

		public EOHttpRequest EOpostFile(final EOstring path) {
				httpRequest.post(Paths.get(path._getData().toString()));
				return this;
		}

		public EOHttpRequest EOpost(final EOByteArray byteArray) {
				httpRequest.post(byteArray.getUnderlyingByteArrayOfEoByteArrayObj(byteArray));
				return this;
		}

		public EOHttpRequest EOput(final EOstring string) {
				httpRequest.put(string._getData().toString());
				return this;
		}

		public EOHttpRequest EOputFile(final EOstring string) {
				httpRequest.put(Paths.get(string._getData().toString()));
				return this;
		}

		public EOHttpRequest EOput(final EOByteArray byteArray) {
				httpRequest.put(byteArray.getUnderlyingByteArrayOfEoByteArrayObj(byteArray));

				return this;
		}

		@Override
		public EOData _getData() {
				return new EOData(httpRequest.buildRequest());
		}
}
