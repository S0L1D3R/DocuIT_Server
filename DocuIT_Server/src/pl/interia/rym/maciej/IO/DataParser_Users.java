package pl.interia.rym.maciej.IO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import pl.interia.rym.maciej.Structures.DITFolder;
import pl.interia.rym.maciej.Structures.DITUser;

public class DataParser_Users extends DataParser {

	private ArrayList<DataBlock> blocks = new ArrayList<DataBlock>();
	
	public DataParser_Users(String pathToFile) {
		super(pathToFile + "Users.txt", true);
	}
	
	public ArrayList<DITUser> getUsers() {
		ArrayList<DITUser> users = new ArrayList<DITUser>();
		
		for (DataBlock block : blocks) {
			int id = Integer.parseInt(block.getValue("ID"));
			String name = block.getValue("Name");
			String surname = block.getValue("Surname");
			String login = block.getValue("Login");
			String password = block.getValue("Password");
			
			String[] accessibleFolders = block.getValue("AccessToFolders").split(",");
			Integer[] accessibleFoldersIDs = new Integer[accessibleFolders.length];
			for (int i = 0; i < accessibleFoldersIDs.length; i++) {
				accessibleFoldersIDs[i] = Integer.parseInt(accessibleFolders[i]);
			}
			
			ArrayList<Integer> folders = new ArrayList<>(Arrays.asList(accessibleFoldersIDs));;
			boolean isActive = Boolean.parseBoolean(block.getValue("IsActive"));
			
			Iterator<Integer> foldersIterator = folders.iterator();
			
			while (foldersIterator.hasNext()) {
				if(!DITFolder.containsFolderWithID(foldersIterator.next())) {
					foldersIterator.remove();
				}
			}
			
			users.add(new DITUser(id, name, surname, login, password, folders, isActive));
		}
		return users;
	}
	
	public void readFile() {
		blocks = readFileAsBlocks();
	}

	public ArrayList<DataBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(ArrayList<DataBlock> blocks) {
		this.blocks = blocks;
	}
	
}
