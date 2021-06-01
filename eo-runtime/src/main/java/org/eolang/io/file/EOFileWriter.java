package org.eolang.io.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.eolang.EOstring;
import org.eolang.core.EOObject;

/**
 * An object that can be used to perform file writing operations
 **/
public class EOFileWriter extends EOObject {

    private final Path filePath;
    private final String dataToWrite;
    private final Charset charset;
    private final StandardOpenOption[] standardOpenOptions;

    /**
     * @param dataToWrite the string data to be written
     * @param filePath    the path to the file
     * @param charset     the character set to be used
     * @param toAppend    whether to append or not
     */
    public EOFileWriter(final EOObject dataToWrite,
        final EOObject filePath,
        final EOObject charset,
        final EOObject toAppend) {
        this.filePath = Paths.get(filePath._getData().toString());
        this.dataToWrite = dataToWrite._getData().toString();
        this.charset = Charset.forName(charset._getData().toString());

        standardOpenOptions = getStandardOpenOptions(toAppend);
    }

    /**
     * Writes data to file. Creates the file if not preent
     *
     * @return the absolute path of the file or empty string if some error occurs
     */
    public EOstring EOwriteToFile() {
        final var strToBytes = dataToWrite.getBytes(charset);
        try {
            return new EOstring(
                Files.write(filePath, strToBytes, standardOpenOptions).toAbsolutePath().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new EOstring();
    }

    /**
     * Creates a temp file with the default prefix and suffix in the default location as per the OS
     *
     * @return the absolute path of the file or empty string if some error occurs
     */
    public EOstring EOwriteToTempFile() {
        return new EOstring(writeToTempFile(null, null, false));
    }

    /**
     * Create a temporary file with the specified prefix and suffix
     *
     * @param prefix the prefix string to be used in generating the file's name
     * @param suffix the suffix string to be used in generating the file's name
     * @return the absolute path of the file or empty string if some error occurs
     */
    public EOstring EOwriteToTempFile(EOObject prefix, EOObject suffix) {
        final var prf = prefix == null ? null : prefix._getData().toString();
        final var sfx = suffix == null ? null : suffix._getData().toString();
        return new EOstring(writeToTempFile(prf, sfx, true));
    }

    private String writeToTempFile(String prefix, String suffix, boolean defaultLocation) {
        try {
            final var tempFile = defaultLocation
                ? Files.createTempFile(prefix, suffix)
                : Files.createTempFile(filePath, prefix, suffix);
            return Files.write(tempFile, dataToWrite.getBytes(charset),
                StandardOpenOption.APPEND).toAbsolutePath().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private StandardOpenOption[] getStandardOpenOptions(EOObject toAppend) {
        return toAppend._getData().toBoolean()
            ? new StandardOpenOption[]{StandardOpenOption.CREATE,
            StandardOpenOption.WRITE,
            StandardOpenOption.APPEND}
            : new StandardOpenOption[]{StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING};
    }
}
