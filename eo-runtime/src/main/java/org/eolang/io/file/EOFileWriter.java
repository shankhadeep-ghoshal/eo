package org.eolang.io.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import org.eolang.EOByteArray;
import org.eolang.EOint;
import org.eolang.EOstring;
import org.eolang.core.EOObject;
import org.eolang.internal.io.file.FileOutputStream;

/**
 * An object that can be used to perform file writing operations
 **/
public class EOFileWriter extends EOObject {

    private final Charset charset;
    private final FileOutputStream fos;

    /**
     * @param filePath the path to the file
     * @param charset  the character set to be used
     * @param toAppend whether to append or not
     */
    public EOFileWriter(
        final EOObject filePath,
        final EOObject charset,
        final EOObject toAppend) {
        this.charset = Charset.forName(charset._getData().toString());
        final var filePath1 = Paths.get(filePath._getData().toString());
        final var appendable = toAppend._getData().toBoolean();

        FileOutputStream temp;
        try {
            temp = new FileOutputStream(filePath1, appendable);
        } catch (IOException e) {
            e.printStackTrace();
            temp = null;
        }

        fos = temp;
    }

    /**
     * @param array the byte array from which to write the data
     * @return 1 if successful, -1 if anything else, like errors or exceptions
     */
    public EOint writeBytes(EOByteArray array) {
        return new EOint(fos.writeBytes((byte[]) array._getData().toObject()));
    }

    /**
     * @param object the object that is to be written to the file
     * @return 1 if successful, -1 if anything else, like errors or exceptions
     */
    public EOint writeObject(EOObject object) {
        return new EOint(fos.write(object._getData().toObject()));
    }

    /**
     * @param string the string data that is to be written to he file
     * @return 1 if successful, -1 if anything else, like errors or exceptions
     */
    public EOint writeString(EOstring string) {
        return new EOint(fos.writeString(string._getData().toString(), charset));
    }

    /**
     * Closes any streams that are open
     * @return 1 as a formality
     */
    public EOint cleanup() {
        return new EOint(fos.cleanup());
    }
}
