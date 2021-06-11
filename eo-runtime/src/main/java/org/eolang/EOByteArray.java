package org.eolang;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.eolang.core.EOObject;
import org.eolang.core.data.EOData;

/**
 * Represents a byte array type
 *
 * @version %I%, %G%
 */
public class EOByteArray extends EOObject {

    private byte[] byteArray;

    public EOByteArray() {
        this.byteArray = null;
    }

    public EOByteArray getBytesFromString(final EOstring string, final EOstring charsetString) {
        final var charset = Charset.forName(charsetString._getData().toString());
        this.byteArray = string._getData().toString().getBytes(charset);
        return this;
    }

    public EOstring getStringFromByes(final EOByteArray eoByteArray, EOstring charsetString) {
        final var data = getUnderlyingByteArrayOfEoByteArrayObj(eoByteArray);
        final var charset = Charset.forName(charsetString._getData().toString());
        return new EOstring(new String(data, charset));
    }

    public EOByteArray getBytesFromInt(final EOint integer) {
        final var longVal = integer._getData().toInt();
        final var buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(longVal);
        this.byteArray = buffer.array();
        return this;
    }

    public EOint getIntFromBytes(final EOByteArray eoByteArray) {
        final var buffer = ByteBuffer.allocate(Long.BYTES);
        final var bytes = getUnderlyingByteArrayOfEoByteArrayObj(eoByteArray);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return new EOint(buffer.getLong());
    }

    public byte[] getUnderlyingByteArrayOfEoByteArrayObj(final EOByteArray byteArray) {
        return (byte[]) byteArray._getData().toObject();
    }

    @Override
    public EOData _getData() {
        return new EOData(byteArray);
    }
}
