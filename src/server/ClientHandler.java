package server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler implements Runnable {

	private final DataInputStream inputStream;
	private final DataOutputStream outputStream;
	private final Socket clientSocket;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			inputStream = new DataInputStream(clientSocket.getInputStream());
			outputStream = new DataOutputStream(clientSocket.getOutputStream());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		try {
			readMessages();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readMessages() throws IOException {
		while (inputStream.available() > 0) {
			int[] array = new int[1000000];
			for (int i = 0; i < array.length; i++) {
				array[i] = inputStream.readInt();
			}

			Arrays.sort(array);
			for (int i = 0; i < 10; i++) {
				outputStream.writeInt(array[i]);
			}
		}
	}
}
