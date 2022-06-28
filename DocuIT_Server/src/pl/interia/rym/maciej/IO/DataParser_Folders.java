package pl.interia.rym.maciej.IO;

import java.util.ArrayList;
import java.util.HashSet;

import pl.interia.rym.maciej.Structures.DITFolder;
import pl.interia.rym.maciej.Structures.DITProject;

public class DataParser_Folders extends DataParser {

	private ArrayList<DataBlock> blocks = new ArrayList<DataBlock>();
	
	public DataParser_Folders(String pathToFolder) {
		super(pathToFolder + "Folders.txt", true);
	}

	public void readFile() {
		blocks = readFileAsBlocks();
	}
	
	public ArrayList<DITFolder> getFolders() {
		ArrayList<DITFolder> folders = new ArrayList<DITFolder>();
		
		for (DataBlock block : blocks) {
			int id = Integer.parseInt(block.getValue("ID"));
			String title = block.getValue("Title");
			boolean isActive = Boolean.parseBoolean(block.getValue("IsActive"));
			
			ArrayList<Integer> projectIds = new ArrayList<Integer>();
			String[] projectsValue = block.getValue("Projects").split(",");

			for (int i = 0; i < projectsValue.length; i++) {
				projectIds.add(Integer.parseInt(projectsValue[i]));
			}
			
			projectIds = new ArrayList<>(new HashSet<>(projectIds));
			
			DITFolder folder = new DITFolder(id, title, isActive, projectIds);
			folders.add(folder);
		}
		
		return folders;
	}
	
	public void saveFolders() {
		openOutputStream();
		for (DITFolder folder : DITFolder.getFolders()) {
			outStream.println("ID->"+folder.getFolderID());
			outStream.println("Title->"+folder.getName());
			outStream.println("IsActive->"+folder.isActive());
			outStream.print("Projects->");
			for (DITProject prj : folder.getProjects()) {
				outStream.print(prj.getId()+",");
			}
			outStream.println();
			outStream.println("-");
		}
		closeOutputStream();
	}
	
}
