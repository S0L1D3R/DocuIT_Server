package pl.interia.rym.maciej;

import java.util.ArrayList;

import pl.interia.rym.maciej.IO.DataParser_Folders;
import pl.interia.rym.maciej.IO.DataParser_Options;
import pl.interia.rym.maciej.IO.DataParser_Project;
import pl.interia.rym.maciej.IO.DataParser_Projects;
import pl.interia.rym.maciej.IO.DataParser_Users;
import pl.interia.rym.maciej.IO.LogHandler;
import pl.interia.rym.maciej.Structures.DITFolder;
import pl.interia.rym.maciej.Structures.DITLink;
import pl.interia.rym.maciej.Structures.DITProject;
import pl.interia.rym.maciej.Structures.DITProjectElement;
import pl.interia.rym.maciej.Structures.DITUser;
import pl.interia.rym.maciej.Threading.Network.DITServerSocketThread;

public class DITServerApp {

	private DITServerSocketThread serverSocketThread;
	
	private static String appDir = "C:\\Program Files\\DocuITServer\\";
	private static String testingSettingsFile = appDir + "ServerOptions.txt";
	
	//Before server fully starts it writes to file in program files. All further logs will be saved in specified file in the options
	private static String path_File_LogsBeforeFullStart = "C:\\Program Files\\DocuITServer\\ServerLogs_BeforeFullStart.txt";
	
	private static String serverName;
	private static int port;
	private static String path_Folder_Data;
	private static String path_File_Logs;
	
	public DITServerApp() {
		LogHandler.setLogFile(path_File_LogsBeforeFullStart);
		loadOptions();
		LogHandler.setLogFile(path_File_Logs);
		
		loadFolders();
		loadUsers();
		loadProjects();
		
		serverSocketThread = new DITServerSocketThread(this);
		serverSocketThread.start();
	}
	
	public void loadProjects() {
		DataParser_Projects parser = new DataParser_Projects(path_Folder_Data);
		ArrayList<DITProject> projects =  parser.getProjects();
		
		for (DITFolder folder : DITFolder.getFolders()) {
			for (Integer idOfAssignedProject : folder.getprojectsIds()) {
				for (DITProject ditProject : projects) {
					if(ditProject.getId() == idOfAssignedProject) {
						folder.addProject(ditProject);;
					}
				}
			}
		}
	}
	
	private void loadFolders() {
		DataParser_Folders parser = new DataParser_Folders(path_Folder_Data);
		parser.readFile();
		
		DITFolder.setUsers(parser.getFolders());
	}
	
	private void loadUsers() {
		DataParser_Users parser = new DataParser_Users(path_Folder_Data);
		parser.readFile();
		
		DITUser.setUsers(parser.getUsers());
	}
	
	private void loadOptions() {
		DataParser_Options parser = new DataParser_Options(testingSettingsFile);
		parser.readFile();
		
		serverName = parser.getServerName();
		port = parser.getPort();
		path_Folder_Data = parser.getLocation_DataFolder();
		path_File_Logs = parser.getLocation_LogFile();
		
		LogHandler.writeGeneralInfo("Starting server...");
		LogHandler.writeGeneralInfo("ServerName: " + serverName);
		LogHandler.writeGeneralInfo("Port: " + port);
		LogHandler.writeGeneralInfo("Logs file: " + path_File_Logs);
		LogHandler.writeGeneralInfo("Data folder: " + path_Folder_Data);
	}

	public DITServerSocketThread getServerSocketThread() {
		return serverSocketThread;
	}

	public void setServerSocketThread(DITServerSocketThread serverSocketThread) {
		this.serverSocketThread = serverSocketThread;
	}

	public static String getAppDir() {
		return appDir;
	}

	public static void setAppDir(String appDir) {
		DITServerApp.appDir = appDir;
	}

	public String getpath_File_Logs() {
		return path_File_Logs;
	}

	public static void setpath_File_Logs(String path_File_Logs) {
		DITServerApp.path_File_Logs = path_File_Logs;
	}

	public static String getServerName() {
		return serverName;
	}

	public static void setServerName(String serverName) {
		DITServerApp.serverName = serverName;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		DITServerApp.port = port;
	}

	public static String getPath_Folder_Data() {
		return path_Folder_Data;
	}

	public static void setpath_Folder_Data(String path_Folder_Data) {
		DITServerApp.path_Folder_Data = path_Folder_Data;
	}
	
	
	
}