package org.eolang;

import org.eolang.core.EOObject;

/***
 * Represents a byte type
 * @version %I%, %G%
 */
public class EObyte extends EOObject {
    private final byte byteValue;

    public EObyte(EOint integer) {
        byteValue = integer._getData().toInt().byteValue();
    }

    public EObyte(EOchar characterVal) {
        byteValue = (byte) characterVal._getData().toChar().charValue();
    }
}
