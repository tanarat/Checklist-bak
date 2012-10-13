package org.silk.checklist.activity;



public class MenuButton {
	private int id;
	private String name;
	private int imgResource;
	
	
	public MenuButton(int id, String name, int imgResource) {
		super();
		this.id = id;
		this.name = name;
		this.setImgResource(imgResource);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getImgResource() {
		return imgResource;
	}
	public void setImgResource(int imgResource) {
		this.imgResource = imgResource;
	}
	
	
}
