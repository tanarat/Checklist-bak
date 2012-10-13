package org.silk.checklist.model;

import java.io.Serializable;


public class BPartner implements Serializable{

	private int bpartnerId;
	private String name;
	private String office;
	private String factory;
	private int numOfEmployee;
	private int numOfMuslim;
	private int numOfForeigner;
	
	
	public BPartner() {
		super();
	}
	public BPartner(int bpartnerId, String name, String office, String factory,
			int numOfEmployee, int numOfMuslim, int numOfForeigner) {
		super();
		this.bpartnerId = bpartnerId;
		this.name = name;
		this.office = office;
		this.factory = factory;
		this.numOfEmployee = numOfEmployee;
		this.numOfMuslim = numOfMuslim;
		this.numOfForeigner = numOfForeigner;
	}
	public int getBpartnerId() {
		return bpartnerId;
	}
	public void setBpartnerId(int bpartnerId) {
		this.bpartnerId = bpartnerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public int getNumOfEmployee() {
		return numOfEmployee;
	}
	public void setNumOfEmployee(int numOfEmployee) {
		this.numOfEmployee = numOfEmployee;
	}
	public int getNumOfMuslim() {
		return numOfMuslim;
	}
	public void setNumOfMuslim(int numOfMuslim) {
		this.numOfMuslim = numOfMuslim;
	}
	public int getNumOfForeigner() {
		return numOfForeigner;
	}
	public void setNumOfForeigner(int numOfForeigner) {
		this.numOfForeigner = numOfForeigner;
	}
	
}
