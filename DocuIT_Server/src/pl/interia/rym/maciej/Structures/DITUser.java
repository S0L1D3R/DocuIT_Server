package pl.interia.rym.maciej.Structures;

import java.util.ArrayList;
import java.util.Random;

public class DITUser {

	private static ArrayList<DITUser> users;
	private ArrayList<Integer> accessibleFolders;
	
	private int userID;
	private String name, surname, login, password;
	private boolean isActive;
	
	/**
	 * Empty constructor. Create empty user, fill his data AND THEN add him to the poll.
	 */
	public DITUser() {};
	
	public DITUser(String name, String surname, String login, String password, ArrayList<Integer> accessibleFolders,  boolean isActive) {
		this.setName(name);
		this.setSurname(surname);
		this.login = login;
		this.password = password;
		this.setAccessibleFolders(accessibleFolders);
		this.setActive(isActive);
	}
	
	public DITUser(int userID, String name, String surname, String login, String password, ArrayList<Integer> accessibleFolders,  boolean isActive) {
		this.setUserID(userID);
		this.setName(name);
		this.setSurname(surname);
		this.login = login;
		this.password = password;
		this.setAccessibleFolders(accessibleFolders);
		this.setActive(isActive);
	}
	
	public static boolean checkCredentials(String login, String password) {
		DITUser user = getUserByLogin(login);
		if(user != null) {
			if(user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
	
	public static DITUser getUserByLogin(String login) {
		for (DITUser ditUser : users) {
			if(ditUser.getLogin().equals(login)) {
				return ditUser;
			}
		}
		return null;
	}
	
	public static void addNewUser(DITUser userToAdd) {
		int newID = 0;
		boolean isNotUsed = true;
		while(isNotUsed) {
			newID = new Random().nextInt(1000);
			
			if(containsUserWithID(newID)) {
				continue;
			}
			userToAdd.setUserID(newID);
			isNotUsed = false;
		}
	}
	
	public static void loadExistingUser(DITUser user) {
		users.add(user);
	}
	
	public static void removeUser(int idOfUser) {
		for (DITUser user : getusers()) {
			if(user.getUserID() == idOfUser) {
				getusers().remove(user);
			}
		}
	}
	
	public static boolean containsUserWithID(int idOfUser) {
		for (DITUser user : getusers()) {
			if(user.getUserID() == idOfUser) {
				return true;
			}
		}
		return false;
	}
	
	public void displayInfoInConsole() {
		System.out.println(getUserID());
		System.out.println(getName());
		System.out.println(getSurname());
		System.out.println(getLogin());
		System.out.println(getPassword());
		System.out.println(getAccessibleFolders());
		System.out.println("-");
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private static ArrayList<DITUser> getusers() {
		return users;
	}

	public ArrayList<Integer> getAccessibleFolders() {
		return accessibleFolders;
	}

	public void setAccessibleFolders(ArrayList<Integer> accessibleFolders) {
		this.accessibleFolders = accessibleFolders;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public static ArrayList<DITUser> getUsers() {
		return users;
	}

	public static void setUsers(ArrayList<DITUser> users) {
		DITUser.users = users;
	}
	
}
