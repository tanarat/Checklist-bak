package org.silk.checklist.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Paper implements ListItem,Serializable{
	private int paperId;
	private String name;
//	private Map<Integer, Question> questionsMap = new LinkedHashMap<Integer, Question>();
	private Set<Integer> questions = new LinkedHashSet<Integer>();
	public Paper(int id, String name, Set<Integer> questions) {
		// TODO Auto-generated constructor stub
		this.paperId = id;
		this.name = name;
		this.questions = questions;
		
	}
	public Paper(int id, String name) {
		// TODO Auto-generated constructor stub
		this.paperId = id;
		this.name = name;
	}
		
	public int getPaperId() {
		return paperId;
	}
	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Integer> getQuestionsSet() {
		return this.questions;
	}
	public void setQuestions(Set<Integer> questions) {
		this.questions = questions;
	}
	
	@Override
	public long getItemId() {
		// TODO Auto-generated method stub
		return getPaperId();
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
