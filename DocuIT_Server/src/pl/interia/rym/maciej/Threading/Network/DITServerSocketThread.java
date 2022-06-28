package pl.interia.rym.maciej.Threading.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import pl.interia.rym.maciej.DITServerApp;
import pl.interia.rym.maciej.IO.LogHandler;

public class DITServerSocketThread extends Thread {
	
	private ServerSocket serverSocket;
	private boolean listening = true;
	private static final int port = 5454;
	private ArrayList<DITClientConnection> clientsConnected = new ArrayList<DITClientConnection>();
	
	private DITServerApp appObject;
	
	public DITServerSocketThread(DITServerApp appObject) {
		try {
			this.setAppObject(appObject);
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		LogHandler.writeServerSocketLog("Starting ServerSocketThread...");
		try {
			while (listening) {
				Socket socket = serverSocket.accept();
				displayMessageInTerminal("Socket accepted: " + socket.isConnected());
				DITClientConnection newConnection = new DITClientConnection(socket.getRemoteSocketAddress().toString(), socket);
				newConnection.start();
				clientsConnected.add(newConnection);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void displayMessageInTerminal(String message) {
		System.out.println("[InfoServer] " + message);
	}

	public DITServerApp getAppObject() {
		return appObject;
	}

	public void setAppObject(DITServerApp appObject) {
		this.appObject = appObject;
	}
	
}
