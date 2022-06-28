package pl.interia.rym.maciej.Threading.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import pl.interia.rym.maciej.IO.LogHandler;

public class DITClientConnection extends Thread {

	private String nameOfConnection;
	private Socket socket;
	
	private PrintWriter outStream;
	private BufferedReader inStream;
	
	private DITNetworkProtocol protocol;
	
	public DITClientConnection(String nameOfConnection, Socket socket) {
		this.setNameOfConnection(nameOfConnection);
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			outStream = new PrintWriter(socket.getOutputStream(), true);
			inStream = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			protocol = new DITNetworkProtocol(this);
		} catch (Exception e) {
			LogHandler.writeConnectionLog(nameOfConnection + " encountered problems while opening streams");
			LogHandler.writeException(e);
		}
		
		String inputFromClient;
		try {
			
			while((inputFromClient = inStream.readLine()) != null) {
				protocol.resolveMessage(inputFromClient);
			}
			
		} catch (IOException e) {
			LogHandler.writeConnectionLog(nameOfConnection + " encountered problems when listening from client");
			LogHandler.writeException(e);
		}
		
		stopConnection();
		
	}
	
	public void stopConnection() {
		try {
			inStream.close();
			outStream.close();
			socket.close();
		} catch (IOException e) {
			LogHandler.writeConnectionLog(nameOfConnection + " encountered problems while closing streams and socket");
			LogHandler.writeException(e);
		}
	}
	
	public void sendMessageToClient(String message) {
		outStream.println("-> Client: " + message);
		System.out.println("-> Client: " + message);
	}
	
	public void displayMessageFromClient(String message) {
		System.out.println("<- Client: " + message);
	}

	public String getNameOfConnection() {
		return nameOfConnection;
	}

	public void setNameOfConnection(String nameOfConnection) {
		this.nameOfConnection = nameOfConnection;
	}

	public DITNetworkProtocol getProtocol() {
		return protocol;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public PrintWriter getOutStream() {
		return outStream;
	}

	public void setOutStream(PrintWriter outStream) {
		this.outStream = outStream;
	}

	public BufferedReader getInStream() {
		return inStream;
	}

	public void setInStream(BufferedReader inStream) {
		this.inStream = inStream;
	}

	public void setProtocol(DITNetworkProtocol protocol) {
		this.protocol = protocol;
	}
}
