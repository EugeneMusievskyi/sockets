package server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler extends Thread {

	private final ObjectInputStream clientInput;
	private final ObjectOutputStream clientOutput;

	public ClientHandler(Socket clientSocket) {
		try {
			clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
			clientInput = new ObjectInputStream(clientSocket.getInputStream());
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
		int[] array = (int[]) clientInput.readObject();
		Arrays.sort(array);

		int[] result = Arrays.stream(array).limit(10).toArray();
		clientOutput.writeObject(result);
	}
}
