package org.eolang.io.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eolang.EOByteArray;
import org.eolang.EOint;
import org.eolang.EOstring;
import org.eolang.core.EOObject;

/**
 * An object that can be used to perform file reading operations
 *
 * @version %I%, %G%
 */
public class EOFileReader extends EOObject {

    private final Path filePath;
    private final FileInputStream fis;

    public EOFileReader(EOstring filePath, EOstring charset, EOint byteBufferSize) {
        final var path = filePath._getData().toString();
        final var characterSet = charset._getData().toString();
        final var bufSize = byteBufferSize._getData().toInt();

        this.filePath = Paths.get(path);
        final var charset1 = Charset.forName(characterSet);

        this.fis = new FileInputStream(this.filePath, charset1, Math.toIntExact(bufSize));
    }

    /**
     * Reads a bunch of stuff into a buffer
     *
     * @return returns the number of bytes read or -1 if end of stream is read
     * @apiNote to access the data, call the {@link EOFileReader#getReadData()} method after each
     * function call to get the data in byes
     */
    public EOint read() {
        return new EOint(fis.read());
    }

    /**
     * @return a single line read as string
     */
    public EOstring readLine() {
        return new EOstring(fis.readLine());
    }

    /**
     * @return an array of bytes as a result of reading all the contents of the file
     * @apiNote Not to be used for reading large files
     */
    public EOByteArray readAllBytes() {
        try {
            return new EOByteArray(Files.readAllBytes(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return new EOByteArray();
        }
    }

    /**
     * @return the byte array
     */
    public EOByteArray getReadData() {
        return new EOByteArray(fis.getByteArray());
    }

    /**
     * Cleans up resources like streams
     * @return 1 as a formality
     */
    public EOint cleanup() {
        fis.cleanup();
        return new EOint(1L);
    }
}
