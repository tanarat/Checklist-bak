package org.hsc.silk.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Answer implements ListItem{
	private int answerId;
	private int answersheetId;
	private int questionId;
	private String questionText;
	private Question question;
	private String remark;
	private Map<Integer, List<Integer>> mapChoices = new LinkedHashMap<Integer, List<Integer>>();
	
	public Answer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Answer(int answersheetId, int questionId, String remark) {
		super();
		this.answersheetId = answersheetId;
		this.questionId = questionId;
		this.remark = remark;
	}
	public Answer(int answersheetId, int questionId) {
		// TODO Auto-generated constructor stub
		this.answersheetId = answersheetId;
		this.questionId = questionId;
	}
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	public int getAnswersheetId() {
		return answersheetId;
	}
	public void setAnswerSheetId(int answerSheetId) {
		this.answersheetId = answerSheetId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Map<Integer, List<Integer>> getMapChoices() {
		return mapChoices;
	}
	public void setMapChoices(Map<Integer, List<Integer>> mapChoices) {
		this.mapChoices = mapChoices;
	}
	@Override
	public long getItemId() {
		// TODO Auto-generated method stub
		return getAnswerId();
	}
	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return getQuestionText();
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
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
