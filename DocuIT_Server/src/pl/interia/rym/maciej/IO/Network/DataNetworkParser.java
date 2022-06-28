package pl.interia.rym.maciej.IO.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import pl.interia.rym.maciej.IO.DataBlock;
import pl.interia.rym.maciej.IO.LogHandler;
import pl.interia.rym.maciej.Structures.DITFolder;
import pl.interia.rym.maciej.Structures.DITLink;
import pl.interia.rym.maciej.Structures.DITProject;
import pl.interia.rym.maciej.Structures.DITProjectElement;
import pl.interia.rym.maciej.Structures.DITUser;

public class DataNetworkParser {
	
	protected BufferedReader inputStream;
	protected PrintWriter outStream;
	
	private String userLogin;
	
	public DataNetworkParser(BufferedReader inputStream, PrintWriter outStream) {
		this.inputStream = inputStream;
		this.outStream = outStream;
	}
	//////
	
	/**
	 * Reads file line by line and returns ArrayList of lines. Classes that inherit from this will read the returned list.
	 */
	public Integer getProjectFromStream() {
		ArrayList<DataBlock> blocksOfElements = new ArrayList<DataBlock>();
		ArrayList<DataBlock> blocksOfLinks = new ArrayList<DataBlock>();
		
		DataBlock block = null;
		DITProject project = null;
		
		try {
			//System.out.println("Start reading");
			int folderID = Integer.parseInt(inputStream.readLine());
			//System.out.println("received folder ID: " + folderID);
			DITFolder folder = DITFolder.getFolderByID(folderID);
			project = new DITProject(
					Integer.parseInt(inputStream.readLine()), 
					inputStream.readLine()
					);
			//System.out.println("ID: " + project.getId() + ", Name: " + project.getName());
			String currentline;
			block = new DataBlock();
			while ((currentline = inputStream.readLine()) != null) {
				if(currentline.startsWith("--/")) {
					break;
				}
				if(currentline.startsWith("#")) {
					continue;
				}
				if(currentline.startsWith("-")) {
					blocksOfElements.add(block);
					block = new DataBlock();
					continue;
				}
				String[] splittedLine = currentline.split("->");
				//System.out.println(splittedLine.toString());
				block.addHash(splittedLine[0], splittedLine[1]);
			}
			if(block.wasFullyRead()) {
				blocksOfElements.add(block);
			}
			
			while ((currentline = inputStream.readLine()) != null) {
				if(currentline.startsWith("---/")) {
					break;
				}
				if(currentline.startsWith("#")) {
					continue;
				}
				if(currentline.startsWith("-")) {
					blocksOfLinks.add(block);
					block = new DataBlock();
					continue;
				}
				String[] splittedLine = currentline.split("->");
				block.addHash(splittedLine[0], splittedLine[1]);
			}
			if(block.wasFullyRead()) {
				blocksOfLinks.add(block);
			}
			
			
			//System.out.println("Elements");
			for (DataBlock dataBlock : blocksOfElements) {
				DITProjectElement el = new DITProjectElement(
						Integer.parseInt(dataBlock.getValue("ID")), 
						Integer.parseInt(dataBlock.getValue("X")), 
						Integer.parseInt(dataBlock.getValue("Y")), 
						dataBlock.getValue("Title"),
						dataBlock.getValue("Content"));
				project.addElement(el);
			}
			
			//System.out.println("Links");
			for (DataBlock dataBlock : blocksOfLinks) {
				DITLink link = new DITLink(
						Integer.parseInt(dataBlock.getValue("ID1")), 
						Integer.parseInt(dataBlock.getValue("ID2")));
				project.addLink(link);
			}
			
			folder.saveProject(project);
			
			DITFolder.saveFolders();
			
			outStream.println("UpdateCurrentProject_ID");
			outStream.println(project.getId());
			
		} catch (IOException e) {
			LogHandler.writeFileLog("DataNetworkParser error while reading stream: " + e.getLocalizedMessage());
			LogHandler.writeException(e);
		}
		
		return project.getId();
	}
	
	public void sendDataToClient() {
		DITUser user = DITUser.getUserByLogin(userLogin);
		outStream.println("Returning_Data:Start");
		
		for (DITFolder folder : DITFolder.getFolders()) {
			for (Integer folderID : user.getAccessibleFolders()) {
				if(folder.getFolderID() == folderID && folder.isActive()) {
					outStream.println("<Folder");
					outStream.println(folder.getFolderID());
					outStream.println(folder.getName());
					sendProjectsFromFolder(folder);
					outStream.println("Folder>");
				}
			}
			
		}
		outStream.println("Returning_Data:Stop");
	}
	
	private void sendProjectsFromFolder(DITFolder folder) {
		for (DITProject project : folder.getProjects()) {
//			//System.out.println("  " + project.getName());
			outStream.println("<Project");
			outStream.println(project.getId());
			outStream.println(project.getName());
//			//System.out.println(project.getName());
			
			for (DITProjectElement element : project.getElements()) {
				outStream.println("<Element");
				outStream.println(element.getId());
				outStream.println(element.getX());
				outStream.println(element.getY());
				outStream.println(element.getTitle());
				outStream.println(element.getContent());
				outStream.println("Element>");
				
//				//System.out.println(element.getId());
//				//System.out.println(element.getX());
//				//System.out.println(element.getY());
//				//System.out.println(element.getTitle());
//				//System.out.println(element.getContent());
			}
			outStream.println("Elements>");
			
			for (DITLink link : project.getLinks()) {
				outStream.println("<Link");
				outStream.println(link.getId1());
				outStream.println(link.getId2());
				outStream.println("Link>");
				
//				//System.out.println("  " + link.getId1());
//				//System.out.println("  " + link.getId2());
			}
			outStream.println("Links>");
			outStream.println("Project>");
//			//System.out.println(project.getName());
		}
		outStream.println("Projects>");
	}

//	private void sendProjectsFromFolder(DITFolder folder) {
//		for (DITProject project : folder.getProjects()) {
//			outStream.println("<Project");
//			outStream.println("ID:" + project.getId());
//			outStream.println("Name:" + project.getName());
//			
//			for (DITProjectElement element : project.getElements()) {
//				outStream.println("<Element");
//				outStream.println("ID:" + element.getId());
//				outStream.println("X:" + element.getX());
//				outStream.println("Y:" + element.getY());
//				outStream.println("Title:" + element.getTitle());
//				outStream.println("Content:" + element.getContent());
//			}
//			for (DITLink link : project.getLinks()) {
//				outStream.println("<Link");
//				outStream.println("ID1:" + link.getId1());
//				outStream.println("ID2:" + link.getId2());
//			}
//			outStream.println("Project>");
//		}
//	}
	
	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public BufferedReader getInputStream() {
		return inputStream;
	}

	public void setInputStream(BufferedReader inputStream) {
		this.inputStream = inputStream;
	}

	public PrintWriter getOutStream() {
		return outStream;
	}

	public void setOutStream(PrintWriter outStream) {
		this.outStream = outStream;
	}
}
