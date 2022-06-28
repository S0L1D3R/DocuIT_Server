package pl.interia.rym.maciej.Structures;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import pl.interia.rym.maciej.DITServerApp;
import pl.interia.rym.maciej.IO.DataParser_Folders;
import pl.interia.rym.maciej.IO.DataParser_Project;

public class DITFolder {

	private static ArrayList<DITFolder> folders = new ArrayList<DITFolder>();
	private ArrayList<DITProject> projects = new ArrayList<DITProject>();
	private ArrayList<Integer> projectsIds = new ArrayList<Integer>();
	
	private int folderID;
	private String name;
	private boolean isActive = true;
	
	/**
	 * Constructor for creating empty Folders, to fill with loaded data and then adding to the folders
	 */
	public DITFolder() {
	}
	
	/**
	 * Constructor with parameters if we have all needed data already.
	 * @param folderID
	 * @param name
	 * @param permittedUsersIDs
	 */
	public DITFolder(int folderID, String name, boolean isActive) {
		this.folderID = folderID;
		this.name = name;
		this.isActive = isActive;
	}
	
	public DITFolder(int folderID, String name, boolean isActive, ArrayList<Integer> projectsIds) {
		this.folderID = folderID;
		this.name = name;
		this.isActive = isActive;
		this.projectsIds = projectsIds;
	}
	
	//////
	
	public boolean containsProjectWithID(int projectID) {
		for (DITProject ditProject : projects) {
			if(ditProject.getId() == projectID) {
				return true;
			}
		}
		return false;
	}
	
	public void saveProject(DITProject projectToSave) {
		System.out.println("project received ID " + projectToSave.getId());
		if(containsProjectWithID(projectToSave.getId())){
			DITProject foundProject = getProjectByID(projectToSave.getId());
			foundProject.setName(projectToSave.getName());
			foundProject.setElements(projectToSave.getElements());
			foundProject.setLinks(projectToSave.getLinks());
			DataParser_Project parser = new DataParser_Project(DITServerApp.getPath_Folder_Data() + "\\Projects\\" + (projectToSave.getId()+"_"+projectToSave.getName()));
			parser.saveProject(projectToSave);
			return;
		}
		addNewProject(projectToSave);
		DataParser_Project parser = new DataParser_Project(DITServerApp.getPath_Folder_Data() + "\\Projects\\" + (projectToSave.getId()+"_"+projectToSave.getName()));
		parser.saveProject(projectToSave);
	}
	
	public void addNewProject(DITProject projectToAdd) {
		int newID = 0;
		boolean isNotUsed = true;
		while(isNotUsed) {
			newID = new Random().nextInt(1000);
			
			if(containsProjectWithID(newID)) {
				continue;
			}
			projectToAdd.setId(newID);
			isNotUsed = false;
		}
		projects.add(projectToAdd);
	}
	
	public static DITFolder getFolderByID(int idOfFolderToFind) {
		for (DITFolder ditFolder : folders) {
			if(ditFolder.getFolderID() == idOfFolderToFind) {
				return ditFolder;
			}
		}
		return null;
	}
	
	public DITProject getProjectByID(int idOfProjectToFind) {
		for (DITProject ditProject : projects) {
			if(ditProject.getId() == idOfProjectToFind) {
				return ditProject;
			}
		}
		return null;
	}
	
	public void addProject(DITProject project) {
		projects.add(project);
	}
	
	public void displayAllData() {
		System.out.println("Folder");
		System.out.println("ID: " + folderID);
		System.out.println("name: " + name);
		System.out.println("Active: " + isActive);
		System.out.print("Project ids: ");
		for (Integer integer : projectsIds) {
			System.out.print(integer + ",");
		}
		System.out.println();
		
		for (DITProject ditProject : projects) {
			ditProject.displayAllInfoAboutProject();
		}
	}
	
	public static boolean containsFolderWithID(int idToCheck) {
		for (DITFolder ditFolder : folders) {
			if(ditFolder.getFolderID() == idToCheck) {
				return true;
			}
		}
		return false;
	}
	
	public static void setUsers(ArrayList<DITFolder> foldersToLoad) {
		folders = foldersToLoad;
	}
	
	public static ArrayList<DITFolder> getFolders() {
		return folders;
	}
	
	public static void addNewFolder(String name, boolean willBeActive) {
		boolean isNotUsed = true;
		int newID = 0;
		while (isNotUsed) {
			newID = new Random().nextInt(1000);
			
			for (DITFolder ditFolder : folders) {
				if(ditFolder.folderID == newID) {
					break;
				}
			}
			isNotUsed = false;
		}
		
		folders.add(new DITFolder(newID, name, willBeActive));
	}
	
	public static void addNewFolder(DITFolder folderToAdd) {
		int newID = 0;
		boolean isNotUsed = true;
		while(isNotUsed) {
			newID = new Random().nextInt(1000);
			
			if(containsFolderWithID(newID)) {
				continue;
			}
			folderToAdd.setFolderID(newID);
			isNotUsed = false;
		}
	}
	
	public static void addExistingFolder(DITFolder existingFolder) {
		folders.add(existingFolder);
	}
	
	public static boolean folderWithIdExists(int id) {
		for (DITFolder ditFolder : folders) {
			if(ditFolder.folderID == id) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean folderWithNameExists(String name) {
		for (DITFolder ditFolder : folders) {
			if(ditFolder.name == name) {
				return true;
			}
		}
		return false;
	}

	public void displayInfoInConsole() {
		System.out.println(folderID);
		System.out.println(name);
		System.out.println(isActive);
		System.out.println("-");
	}

	public int getFolderID() {
		return folderID;
	}

	public void setFolderID(int folderID) {
		this.folderID = folderID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public ArrayList<DITProject> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<DITProject> projects) {
		this.projects = projects;
	}

	public ArrayList<Integer> getprojectsIds() {
		return projectsIds;
	}

	public void setprojectsIds(ArrayList<Integer> projectsIds) {
		this.projectsIds = projectsIds;
	}

	public static void saveFolders() {
		DataParser_Folders parser = new DataParser_Folders(DITServerApp.getPath_Folder_Data());
		parser.saveFolders();
	}
	
}
