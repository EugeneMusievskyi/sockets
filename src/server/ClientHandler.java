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
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			inputStream = new ObjectInputStream(clientSocket.getInputStream());
			start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				readMessages();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void readMessages() throws IOException, ClassNotFoundException {
		int[] array = (int[]) inputStream.readObject();
		Arrays.sort(array);

		int[] result = Arrays.stream(array).limit(10).toArray();
		for (int i = 0; i < 10; i++) {
			outputStream.writeObject(result);
		}
	}
}
