package pl.interia.rym.maciej.IO;

import java.io.File;
import java.util.ArrayList;

import pl.interia.rym.maciej.DITServerApp;
import pl.interia.rym.maciej.Structures.DITLink;
import pl.interia.rym.maciej.Structures.DITProject;
import pl.interia.rym.maciej.Structures.DITProjectElement;

public class DataParser_Project extends DataParser {
	
	private ArrayList<DataBlock> blocks = new ArrayList<DataBlock>();

	public DataParser_Project(String pathToFile) {
		super(pathToFile, true);
	}
	
	
	public DITProject getProject() {
		int projectID = Integer.parseInt(file.getName().split("_")[0]);
		String projectName = file.getName().split("_")[1].replace(".txt", "");
		
		DITProject project = new DITProject(projectID, projectName);
		
		for (DataBlock block : blocks) {
			int id = Integer.parseInt(block.getValue("ID"));
			int x = Integer.parseInt(block.getValue("X"));
			int y = Integer.parseInt(block.getValue("Y"));
			String title = block.getValue("Title");
			String content = block.getValue("Content");
			
			DITProjectElement element = new DITProjectElement(id, x, y, title, content);
			project.addElement(element);
			
//			//System.out.println("----" + project.getId());
//			//System.out.println(block.getHashes());
			
			String[] linksValue = null;
			try {
				linksValue = block.getValue("Links").split(",");
			} catch (NullPointerException e) {
				LogHandler.writeFileLog("Empty links in project [" + project.getId() + "], in element [" + element.getId() + "]");
			}
			
			if(linksValue == null) {
				continue;
			}
			
			Integer[] linksIDs = new Integer[linksValue.length];
			for (int i = 0; i < linksIDs.length; i++) {
				linksIDs[i] = Integer.parseInt(linksValue[i]);
			}
			
			for (Integer integer : linksIDs) {
				DITLink link = new DITLink(id, integer);
				project.addLink(link);
			}
			
		}
		
		return project;
	}
	
	public void saveProject(DITProject project) {
		//System.out.println("Saving file to: " + file.getAbsolutePath());
		openOutputStream();
		for (DITProjectElement element : project.getElements()) {
			outStream.println("ID->"+element.getId());
			outStream.println("X->"+element.getX());
			outStream.println("Y->"+element.getY());
			outStream.println("Title->"+element.getTitle());
			outStream.println("Content->"+element.getContent());
			outStream.print("Links->");
			for (DITLink link : project.getLinks()) {
				if(link.containsID(element.getId())) {
					if(link.getId1() == element.getId()) {
						//System.out.println("Zapisuje id linku: " + link.getId1());
						outStream.print(link.getId2()+",");
					}
					else {
						//System.out.println("Zapisuje id linku: " + link.getId2());
						outStream.print(link.getId1()+",");
					}
				}
			}
			outStream.println();
			outStream.println("-");
		}
		closeOutputStream();
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
