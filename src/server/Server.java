package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	private ArrayList<PrintWriter> clientOutputStreams;

	public static void main(String[] args) throws IOException {
		new Server().setUpNetwork();
	}

	public void setUpNetwork() throws IOException {
		clientOutputStreams = new ArrayList<>();
		ServerSocket serverSock = new ServerSocket(5000);
		while (true) {
			try {
				Socket clientSocket = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				new Thread(new ClientHandler(clientSocket));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
