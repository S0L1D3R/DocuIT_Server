package pl.interia.rym.maciej.Threading.Network;

import pl.interia.rym.maciej.IO.Network.DataNetworkParser;
import pl.interia.rym.maciej.Structures.DITUser;

public class DITNetworkProtocol {

	private DITClientConnection connection;
	DataNetworkParser parser;
	
	public DITNetworkProtocol(DITClientConnection connection) {
		this.connection = connection;
		parser = new DataNetworkParser(connection.getInStream(), connection.getOutStream());
	}

	public void resolveMessage(String inputFromClient) {
		String[] parsedMessage = parseClientMessage(inputFromClient);
		
		if(parsedMessage.length < 2) {
			respondToClient("Request bad format");
			return;
		}
		
		String action = parsedMessage[0];
		String values = parsedMessage[1];
		String[] splittedValues = values.split(",");
		
		switch (action) {
		case "SignIn":
			System.out.println("SignIn attempt: " + inputFromClient);
			if(splittedValues.length < 2) {
				respondToClient("Request bad format");
			}
			
			String login = splittedValues[0];
			String password = splittedValues[1];
			
			authenticateUser(login, password);
			
			break;

		case "Request":
			if(splittedValues.length > 1 && !splittedValues[0].equals("Data")) {
				respondToClient("Request bad format");
				break;
			}
			parser.sendDataToClient();
			
			break;
			
		case "Save":
			if(splittedValues.length > 1 && !splittedValues[0].equals("Project")) {
				respondToClient("Request bad format");
				break;
			}
			System.out.println("Request to save project received");
			parser.getProjectFromStream();
			break;
			
		default:
			respondToClient("Request not recognized");
			break;
		}
	}
	
	private void authenticateUser(String login, String password) {
		if(DITUser.checkCredentials(login, password)) {
			parser.setUserLogin(login);
			respondToClient("Authenticated");
			return;
		}
		respondToClient("Login or password is not recognized");
	}
	
	private String[] parseClientMessage(String inputFromClient) {
		String[] message = inputFromClient.split("->");
		return message;
	}
	
	public void respondToClient(String message) {
		connection.getOutStream().println(message);
	}
	
}
