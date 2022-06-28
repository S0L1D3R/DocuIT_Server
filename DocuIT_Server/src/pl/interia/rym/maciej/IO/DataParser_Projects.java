package pl.interia.rym.maciej.IO;

import java.io.File;
import java.util.ArrayList;

import pl.interia.rym.maciej.Structures.DITProject;

public class DataParser_Projects {

	private File directory;
	
	public DataParser_Projects(String path_Folder_Projects) {
		directory = new File(path_Folder_Projects + "\\Projects\\");
	}
	
	public ArrayList<DITProject> getProjects() {
		ArrayList<DITProject> projects = new ArrayList<DITProject>();
		
		for (File file : directory.listFiles()) {
			if(file.length() == 0) {
				LogHandler.writeFileLog("File was empty on: " + file.getAbsolutePath());
				continue;
			}
			DataParser_Project parser = new DataParser_Project(file.getAbsolutePath());
			parser.readFile();
			
			DITProject project = parser.getProject();
			projects.add(project);
		}
		
		return projects;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}
	
}
