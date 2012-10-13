package org.silk.checklist.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Question implements ListItem{
	private int id;
	private String questionText;
	private Map<Integer, Choice> mapChoices = new LinkedHashMap<Integer, Choice>();
	private String groupName = "";
	
	//Constructors
	public Question() {
		super();
	}
	
	public Question(int id, String questionText) {
		super();
		this.id = id;
		this.questionText = questionText;
	}

	
	public Question(int id, String questionText, String questionGroup) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.questionText = questionText;
		this.groupName = questionGroup;
	}

	//Setters, getters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	
	@Override
	public long getItemId() {
		// TODO Auto-generated method stub
		return getId();
	}

	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return getQuestionText();
	}

	public Map<Integer, Choice> getMapChoices() {
		return mapChoices;
	}

	public void setMapChoices(Map<Integer, Choice> mapChoices) {
		this.mapChoices = mapChoices;
	}
	public void addChoice(Choice choice){
		this.mapChoices.put(choice.getChoiceId(), choice);
	}
	public Choice getChoice(int choiceId){
		return this.mapChoices.get(choiceId);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
