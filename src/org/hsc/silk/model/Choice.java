package org.hsc.silk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Choice implements Serializable{
	
	private int choiceId;
	private int questionId;
	private String choiceText;
	private Map<Integer,Option> mapOptions = new LinkedHashMap<Integer,Option>();
	
	
	public Choice() {
		super();
	}
	public Choice(int id, String choiceText){
		this.choiceId = id;
		this.choiceText = choiceText;
	}
	public Choice(int id, int questionId, String choiceText) {
		super();
		this.choiceId = id;
		this.questionId = questionId;
		this.choiceText = choiceText;
	}

	public Choice(String choiceText) {
		// TODO Auto-generated constructor stub
		this.choiceText = choiceText;
	}
	
	
	public int getChoiceId() {
		return choiceId;
	}

	public void setChoiceId(int choiceId) {
		this.choiceId = choiceId;
	}

	public String getChoiceText() {
		return choiceText;
	}
	public void setChoiceText(String choiceText) {
		this.choiceText = choiceText;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Choice another = (Choice)obj;
		if(choiceId == another.getChoiceId() && choiceId != 0)
			return true;
		else
			return false;
	}
	@Override
	public String toString() {
		return "Choice [id=" + choiceId + ", choiceText=" + choiceText + "]";
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public Map<Integer,Option> getMapOptions() {
		return mapOptions;
	}
	public void setMapOptions(Map<Integer,Option> mapOptions) {
		this.mapOptions = mapOptions;
	}
	public void addOption(Option option){
		this.mapOptions.put(option.getOptionId(), option);
	}
	public Option getOption(int optionId){
		return this.mapOptions.get(optionId);
	}
}
