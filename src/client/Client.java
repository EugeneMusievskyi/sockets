package client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

public class Client {

	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;

	public static void main(String[] args) {
		new Client().setUpNetwork();
	}

	public void setUpNetwork() {
		try {
			socket = new Socket("localhost", 5000);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			System.out.println("networking established");
			sendArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendArray() throws IOException {
		int[] array = new int[1000000];
		var random = new Random();
		for (int i = 0; i < array.length; i++) {
			array[i] = random.nextInt(1000);
		}

		outputStream.writeObject(array);
		readResult();
	}

	private void readResult() throws IOException {
		if (inputStream.available() > 0) {
			try {
				int[] array = (int[]) inputStream.readObject();
				System.out.println(Arrays.toString(array));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
