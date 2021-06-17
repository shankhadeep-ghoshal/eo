package org.eolang.io.net.http;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.eolang.EOByteArray;
import org.eolang.EOarray;
import org.eolang.EOint;
import org.eolang.EOstring;
import org.eolang.core.EOObject;
import org.eolang.core.data.EOData;
import org.eolang.internal.net.http.Header;
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

		public EOHttpRequest EOsetHeaders(final EOarray headers) {
				final var eoData = headers._getData();
				try {
						final var array = eoData.getClass().getDeclaredField("_array");
						array.setAccessible(true);
						List<EOObject> objArr = (List<EOObject>) array.get(eoData);
						final var headersList = objArr.stream()
								.map(eoObject -> (EOHeader) eoObject._getData().toObject()).collect(
										Collectors.toList());
						final var headersListJava =
								headersList.stream().map(
										eoHeader -> new Header(eoHeader.EOgetKey()._getData().toString(),
												eoHeader.EOgetValue()._getData().toString())).collect(Collectors.toList());
						httpRequest.setHeaders(headersListJava);
				} catch (NoSuchFieldException | IllegalAccessException e) {
						e.printStackTrace();
				}
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

		public EOHttpRequest EOdelete() {
				httpRequest.delete();
				return this;
		}

		@Override
		public EOData _getData() {
				return new EOData(httpRequest.buildRequest());
		}
}
