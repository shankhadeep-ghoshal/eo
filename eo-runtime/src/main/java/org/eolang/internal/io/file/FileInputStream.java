package org.eolang.internal.io.file;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileInputStream {

    private final java.io.FileInputStream fis;
    private final ByteBuffer byteBuffer;
    private final BufferedReader br;

    public FileInputStream(final Path filePath, final Charset charset, final int bufferSize) {
        java.io.FileInputStream temp;
        BufferedReader buffRead;

        this.byteBuffer = ByteBuffer.allocate(bufferSize);

        try {
            temp = new java.io.FileInputStream(filePath.toFile());
            buffRead = Files.newBufferedReader(filePath, charset);

        } catch (IOException e) {
            e.printStackTrace();
            temp = null;
            buffRead = null;
        }

        this.fis = temp;
        this.br = buffRead;
    }

    public int read() {
        try {
            return fis.getChannel().read(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String readLine() {
        try {
            final var line = br.readLine();
            if (line != null && line.length() > 0) {
                return line;
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public byte[] getByteArray() {
        return byteBuffer.array();
    }

    public void cleanup() {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
