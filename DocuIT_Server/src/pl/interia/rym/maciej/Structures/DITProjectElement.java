package pl.interia.rym.maciej.Structures;

public class DITProjectElement {

	private int id;
	
	private int x,y;
	
	private String title, content;

	public DITProjectElement(int id, int x, int y, String title, String content) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.title = title;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
