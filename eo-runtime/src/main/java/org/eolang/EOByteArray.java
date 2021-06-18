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

    public EOByteArray(EOint size) {
        this.byteArray = new byte[size._getData().toInt().intValue()];
    }

    public EOByteArray(final byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public EOByteArray EOgetBytesFromString(final EOstring string, final EOstring charsetString) {
        final var charset = Charset.forName(charsetString._getData().toString());
        this.byteArray = string._getData().toString().getBytes(charset);
        return this;
    }

    public EOstring EOgetStringFromByes(final EOByteArray eoByteArray, EOstring charsetString) {
        final var data = getUnderlyingByteArrayOfEoByteArrayObj(eoByteArray);
        final var charset = Charset.forName(charsetString._getData().toString());
        return new EOstring(new String(data, charset));
    }

    public EOByteArray EOgetBytesFromInt(final EOint integer) {
        final var longVal = integer._getData().toInt();
        final var buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(longVal);
        this.byteArray = buffer.array();
        return this;
    }

    public EOint EOgetIntFromBytes(final EOByteArray eoByteArray) {
        final var buffer = ByteBuffer.allocate(Long.BYTES);
        final var bytes = getUnderlyingByteArrayOfEoByteArrayObj(eoByteArray);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return new EOint(buffer.getLong());
    }

    public EOByteArray EOcopyIntoNewByteArray(EOByteArray existing) {
        final var arr = (byte[]) existing._getData().toObject();
        try {
            if (this.byteArray.length - arr.length > 0) {
                System.arraycopy(arr, 0, this.byteArray, 0, arr.length);
            } else {
                final var tempByteArr = new byte[this.byteArray.length + arr.length];
                System.arraycopy(this.byteArray, 0, tempByteArr, 0, this.byteArray.length);
                System.arraycopy(arr, 0, tempByteArr, this.byteArray.length, arr.length);
                this.byteArray = tempByteArr;
            }
        } catch (OutOfMemoryError outOfMemoryError) {
            outOfMemoryError.printStackTrace();
        }

        return new EOByteArray(this.byteArray);
    }

    public byte[] getUnderlyingByteArrayOfEoByteArrayObj(final EOByteArray byteArray) {
        return (byte[]) byteArray._getData().toObject();
    }

    @Override
    public EOData _getData() {
        return new EOData(byteArray);
    }
}
