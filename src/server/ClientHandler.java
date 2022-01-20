package server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler extends Thread {

	private final ObjectInputStream inputStream;
	private final ObjectOutputStream outputStream;

	public ClientHandler(Socket clientSocket) {
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
			readMessages();
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void readMessages() throws IOException, ClassNotFoundException, InterruptedException {
		int[] array = (int[]) inputStream.readObject();
		Arrays.sort(array);

		int[] result = Arrays.stream(array).limit(10).toArray();
		outputStream.writeObject(result);
	}
}
