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
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void sendArray() throws IOException, ClassNotFoundException {
		int[] array = new int[1000000];
		var random = new Random();
		for (int i = 0; i < array.length; i++) {
			array[i] = random.nextInt(1000);
		}
		outputStream.writeObject(array);

		int[] result = (int[]) inputStream.readObject();
		System.out.println(Arrays.toString(result));
	}

}
