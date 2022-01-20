package client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

public class Client {

	private Socket socket;
	private ObjectInputStream serverInput;
	private ObjectOutputStream serverOutput;

	public static void main(String[] args) {
		new Client().setUpNetwork();
	}

	public void setUpNetwork() {
		try {
			socket = new Socket("localhost", 5000);
			serverOutput = new ObjectOutputStream(socket.getOutputStream());
			serverInput = new ObjectInputStream(socket.getInputStream());
			System.out.println("networking established");
			sendArray();
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sendArray() throws IOException, ClassNotFoundException, InterruptedException {
		int[] array = new int[1000000];
		var random = new Random();
		for (int i = 0; i < array.length; i++) {
			array[i] = random.nextInt(Integer.MAX_VALUE);
		}
		serverOutput.writeObject(array);

		int[] result = (int[]) serverInput.readObject();
		System.out.println(Arrays.toString(result));
	}

}
