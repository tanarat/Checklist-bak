package org.hsc.silk.model;

import java.io.Serializable;

public class Auditor implements Serializable, ListItem{
	private int auditorId;
	private String name;
	
	public Auditor() {
		super();
	}
	public Auditor(String name) {
		super();
		this.name = name;
	}
	public Auditor(int auditorId, String name) {
		super();
		this.auditorId = auditorId;
		this.name = name;
	}
	public int getAuditorId() {
		return auditorId;
	}
	public void setAuditorId(int auditorId) {
		this.auditorId = auditorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
	@Override
	public long getItemId() {
		// TODO Auto-generated method stub
		return getAuditorId();
	}
	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return getName();
	}
	@Override
	public String getItemDescription() {
		// TODO Auto-generated method stub
		return "";
	}
	@Override
	public String getItemDescription2() {
		// TODO Auto-generated method stub
		return "";
	}
	
	
}
