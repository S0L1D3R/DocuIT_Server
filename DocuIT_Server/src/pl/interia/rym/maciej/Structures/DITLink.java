package pl.interia.rym.maciej.Structures;

public class DITLink {

	private int id1;
	private int id2;
	
	public DITLink(int id1, int id2) {
		this.id1 = id1;
		this.id2 = id2;
	}
	
	public boolean containsID(int idToCheck) {
		if(id1 == idToCheck || id2 == idToCheck) {
			return true;
		}
		return false;
	}
	
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public int getId2() {
		return id2;
	}
	public void setId2(int id2) {
		this.id2 = id2;
	}
	
	
	
}
