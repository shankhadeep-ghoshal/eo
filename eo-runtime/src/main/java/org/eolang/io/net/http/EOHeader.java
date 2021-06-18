package org.eolang.io.net.http;

import org.eolang.EOstring;
import org.eolang.core.EOObject;
import org.eolang.internal.io.net.http.Header;

/**
 * A class denoting an HTTP header
 */
public class EOHeader extends EOObject {

    private final Header header;

    public EOHeader(final EOstring key, final EOstring value) {
        this.header = new Header(key._getData().toString(), value._getData().toString());
    }

    public EOstring EOgetKey() {
        return new EOstring(header.getKey());
    }

    public EOstring EOgetValue() {
        return new EOstring(header.getValue());
    }
}
