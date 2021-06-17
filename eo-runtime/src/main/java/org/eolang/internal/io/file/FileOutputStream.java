package org.eolang.internal.io.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileOutputStream {

    private final java.io.FileOutputStream fos;
    private final ObjectOutputStream objectOutputStream;
    private final ByteArrayOutputStream byteArrayOutputStream;
    private final Path fileLocation;
    private final boolean isAppendable;

    public FileOutputStream(final Path location, final boolean appendable) throws IOException {
        this.isAppendable = appendable;
        this.fileLocation = location;
        this.fos = new java.io.FileOutputStream(this.fileLocation.toAbsolutePath().toString(),
            this.isAppendable);
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    }

    public int write(Object objectToWrite) {
        try {
            final var byteBuffer = ByteBuffer.wrap(convertObjectToByteArray(objectToWrite));
            return fos.getChannel().write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int writeString(String stringToWrite, Charset charset) {
        final var bytesToWrite = stringToWrite.getBytes(charset);
        return writeBytes(bytesToWrite);
    }

    public int writeBytes(final byte[] bytesToWrite) {
        try {
            Files.write(fileLocation, bytesToWrite, getStandardOpenOptions(isAppendable));
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int cleanup() {
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return 1;
    }

    private byte[] convertObjectToByteArray(Object object) throws IOException {
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    private StandardOpenOption[] getStandardOpenOptions(final boolean toAppend) {
        return toAppend
            ? new StandardOpenOption[]{StandardOpenOption.CREATE,
            StandardOpenOption.WRITE,
            StandardOpenOption.APPEND}
            : new StandardOpenOption[]{StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING};
    }
}
