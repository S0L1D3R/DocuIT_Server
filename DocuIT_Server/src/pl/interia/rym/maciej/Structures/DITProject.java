package pl.interia.rym.maciej.Structures;

import java.util.ArrayList;
import java.util.Random;

import pl.interia.rym.maciej.DITServerApp;
import pl.interia.rym.maciej.IO.DataParser_Project;

public class DITProject {
	
	private String name;
	private int id;
	
	private ArrayList<DITProjectElement> elements = new ArrayList<DITProjectElement>();
	private ArrayList<DITLink> links = new ArrayList<DITLink>();
	
	public DITProject(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public DITProject(int id, String name, ArrayList<DITProjectElement> elements, ArrayList<DITLink> links) {
		this.name = name;
		this.id = id;
		this.elements = elements;
		this.links = links;
	}
	
	public DITProject(String name, ArrayList<DITProjectElement> elements, ArrayList<DITLink> links) {
		this.name = name;
		this.elements = elements;
		this.links = links;
	}
	////
	
//	public static void saveProject(DITProject projectToSave) {
//		System.out.println("project received ID " + projectToSave.getId());
//		if(containsProjectWithID(projectToSave.getId())){
//			DITProject foundProject = getProjectByID(projectToSave.getId());
//			foundProject.setName(projectToSave.getName());
//			foundProject.setElements(projectToSave.getElements());
//			foundProject.setLinks(projectToSave.getLinks());
//			DataParser_Project parser = new DataParser_Project(DITServerApp.getPath_Folder_Data() + "\\Projects\\" + (projectToSave.getId()+"_"+projectToSave.getName()));
//			parser.saveProject(projectToSave);
//			return;
//		}
//		addNewProject(projectToSave);
//		DataParser_Project parser = new DataParser_Project(DITServerApp.getPath_Folder_Data() + "\\Projects\\" + (projectToSave.getId()+"_"+projectToSave.getName()));
//		parser.saveProject(projectToSave);
//	}
	
	public boolean containsLink(DITLink link) {
		for (DITLink ditLink : links) {
			if( ditLink.containsID(link.getId1()) && ditLink.containsID(link.getId2()) ) {
				return true;
			}
		}
		return false;
	}

	public void addElement(DITProjectElement el) {
		elements.add(el);
	}
	
	public void addLink(DITLink link) {
		for (DITLink ditLink : links) {
			if(ditLink.containsID(link.getId1()) && ditLink.containsID(link.getId2())) {
				return;
			}
		}
		links.add(link);
	}
	
	public void linkTwoElements(DITProjectElement el1, DITProjectElement el2) {
		links.add(new DITLink(el1.getId(), el2.getId()));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<DITProjectElement> getElements() {
		return elements;
	}

	public void setElements(ArrayList<DITProjectElement> elements) {
		this.elements = elements;
	}

	public ArrayList<DITLink> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<DITLink> links) {
		this.links = links;
	}
	
	public void displayAllInfoAboutProject() {
		System.out.println("ID: " + id);
		System.out.println("Name: " + name);
		System.out.println("----elements---");
		for (DITProjectElement el : elements) {
			System.out.println("El_ID: " + el.getId());
			System.out.println("El_X: " + el.getX());
			System.out.println("El_Y: " + el.getY());
			System.out.println("El_Title: " + el.getTitle());
			System.out.println("El_Content: " + el.getContent());
		}

		System.out.println("----links---");
		for (DITLink ditLink : links) {
			System.out.print("ID1: " + ditLink.getId1());
			System.out.println(" <> ID2: " + ditLink.getId2());
		}
	}
}
