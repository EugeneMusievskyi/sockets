package server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler extends Thread {

	private final ObjectInputStream inputStream;
	private final ObjectOutputStream outputStream;
	private final Socket clientSocket;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			inputStream = new ObjectInputStream(clientSocket.getInputStream());
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		try {
			readMessages();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void readMessages() throws IOException, ClassNotFoundException {
		while (inputStream.available() > 0) {
			int[] array = (int[]) inputStream.readObject();

			Arrays.sort(array);

			int[] result = Arrays.stream(array).limit(10).toArray();
			for (int i = 0; i < 10; i++) {
				outputStream.writeObject(result);
			}
		}
	}
}
