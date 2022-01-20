package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		new Server().setUpNetwork();
	}

	public void setUpNetwork() throws IOException {
		ServerSocket serverSock = new ServerSocket(5000);
		while (true) {
			try {
				Socket clientSocket = serverSock.accept();
				new Thread(new ClientHandler(clientSocket));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
