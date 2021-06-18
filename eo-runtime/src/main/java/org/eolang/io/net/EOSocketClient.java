package org.eolang.io.net;

import org.eolang.EOByteArray;
import org.eolang.EOint;
import org.eolang.EOstring;
import org.eolang.internal.io.net.SocketClient;

/**
 * Represents a client socket
 **/
public class EOSocketClient {

		private final SocketClient socketClient;

		public EOSocketClient(final EOstring hostOrIp, final EOint port, final EOint byteBufferLength) {
				final var addr = hostOrIp._getData().toString();
				final var bindPort = port._getData().toInt();
				final var buffLength = byteBufferLength._getData().toInt();

				this.socketClient = new SocketClient(addr, Math.toIntExact(bindPort),
						Math.toIntExact(buffLength));
		}

		public EOint EOreadSoket() {
				return new EOint(socketClient.readSocket());
		}

		public EOint EOwriteToSocket(final EOByteArray bytes) {
				socketClient.writeToSocket(bytes.getUnderlyingByteArrayOfEoByteArrayObj(bytes));

				return new EOint(0L);
		}

		public EOByteArray EOgetUnderlyingByteBuffer() {
				return new EOByteArray(socketClient.getUnderlyingByteArray());
		}

		public EOint EOcloseSocketChannel() {
				socketClient.closeChannel();
				return new EOint(1L);
		}
}
