package org.eolang.internal.net;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketClient {

    private final SocketChannel socketChannel;
    private final ByteBuffer byteBuffer;

    public SocketClient(final String hostOrIp, final int port, final int byteBufferLength) {
        InetSocketAddress address;
        SocketChannel soc;
        try {
            address = new InetSocketAddress(InetAddress.getByName(hostOrIp), port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            address = new InetSocketAddress(InetAddress.getLoopbackAddress(), port);
        }
        try {
            soc = SocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
            soc = null;
        }

        InetSocketAddress socketAddress = address;
        socketChannel = soc;
        try {
            this.socketChannel.connect(socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.byteBuffer = ByteBuffer.allocate(byteBufferLength);
    }

    public int readSocket() {
        try {
            this.byteBuffer.clear();
            return socketChannel.read(this.byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void writeToSocket(final byte[] dataToWrite) {
        this.byteBuffer.clear();
        this.byteBuffer.put(dataToWrite);
        this.byteBuffer.flip();

        while (this.byteBuffer.hasRemaining()) {
            try {
                socketChannel.write(this.byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getUnderlyingByteArray() {
        return this.byteBuffer.array();
    }

    public void closeChannel() {
        try {
            if (socketChannel != null) {
                socketChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
