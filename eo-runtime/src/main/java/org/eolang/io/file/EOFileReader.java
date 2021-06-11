package org.eolang.io.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eolang.EOarray;
import org.eolang.EOstring;
import org.eolang.core.EOObject;

/**
 * An object that can be used to perform file reading operations
 */
public class EOFileReader extends EOObject {

    private final Path filePath;
    private final Charset charset;
    private final boolean isLargeFile;

    /**
     * @param path          the location of the file
     * @param charsetString the charset that the file to be read is in
     * @param isLargeFile   if the file is large or not
     */
    public EOFileReader(EOObject path, EOObject charsetString, EOObject isLargeFile) {
        if (path == null || charsetString == null || isLargeFile == null) {
            throw new IllegalArgumentException("the file path or the charset string or the large "
                + "file specifier cannot be null");
        }
        this.filePath = Paths.get(path._getData().toString());
        this.charset = Charset.forName(charsetString._getData().toString());
        this.isLargeFile = isLargeFile._getData().toBoolean();
    }

    /**
     * @return an array of lines read from a file
     */
    public EOarray EOreadFileLines() {
        return convertJavaStringStreamToEOArray(readFileLines());
    }

    /**
     * @return a string representing the entire text read
     */
    public EOstring EOreadFile() {
        return new EOstring(readFile());
    }

    private EOarray convertJavaStringStreamToEOArray(final Supplier<Stream<String>> list) {
        return new EOarray(list
            .get()
            .map(EOstring::new)
            .toArray(EOstring[]::new));
    }

    private Supplier<Stream<String>> readFileLines() {
        checkIsDirectory();

        if (isLargeFile) {
            return readLargeFileLines();
        } else {
            return readFileLinesNoBufferedReader();
        }
    }

    private String readFile() {
        checkIsDirectory();

        if (isLargeFile) {
            return readLargeFile();
        } else {
            return readLargeFileNoBufferedReader();
        }
    }

    private String readLargeFileNoBufferedReader() {
        try {
            return Files.readString(filePath, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String readLargeFile() {
        try (final var line =
            Files.newBufferedReader(filePath, charset).lines()) {
            return line.collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Supplier<Stream<String>> readLargeFileLines() {
        return () -> {
            try {
                return Files.newBufferedReader(filePath, charset).lines();
            } catch (IOException e) {
                e.printStackTrace();
                return Stream.empty();
            }
        };
    }

    private Supplier<Stream<String>> readFileLinesNoBufferedReader() {
        return () -> {
            try {
                return Files.lines(filePath, charset);
            } catch (IOException e) {
                e.printStackTrace();
                return Stream.empty();
            }
        };
    }

    private void checkIsDirectory() {
        if (isDirectory()) {
            try {
                throw new IllegalAccessException("The specified path is that of a directory and "
                    + "not a file");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isDirectory() {
        return Files.isDirectory(filePath);
    }
}
