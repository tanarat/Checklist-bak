package org.silk.checklist.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Answersheet implements Serializable, ListItem{
	private int answersheetId;
	private String answersheetName;
	private Paper paper;
	private BPartner bpartner;
	private List<Integer> auditorIds = new ArrayList<Integer>();
	private List<String> attendances = new ArrayList<String>();

	private List<Integer> answerIds = new ArrayList<Integer>();
	private Date date = new Date();
	private Date startTime = new Date();
	private Date finishTime = new Date();
	
	
	
//	public Answersheet(int answersheetId, Paper paper, BPartner bpartner,
//			List<Integer> auditorIds, List<String> attendances,
//			List<Integer> answerIds, Date date, Date startTime, Date finishTime) {
//		super();
//		this.answersheetId = answersheetId;
//		this.paper = paper;
//		this.bpartner = bpartner;
//		this.auditorIds = auditorIds;
//		this.attendances = attendances;
//		this.answerIds = answerIds;
//		this.date = date;
//		this.startTime = startTime;
//		this.finishTime = finishTime;
//	}
	@Override
	public long getItemId() {
		// TODO Auto-generated method stub
		return getAnswersheetId();
	}
	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return getAnswersheetName();
	}
	
	
	public int getAnswersheetId() {
		return answersheetId;
	}
	public void setAnswersheetId(int answersheetId) {
		this.answersheetId = answersheetId;
	}
	public Paper getPaper() {
		return paper;
	}
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	public BPartner getBpartner() {
		return bpartner;
	}
	public void setBpartner(BPartner bpartner) {
		this.bpartner = bpartner;
	}
	public List<Integer> getAuditorIds() {
		return auditorIds;
	}
	public void setAuditorIds(List<Integer> auditorIds) {
		this.auditorIds = auditorIds;
	}
	public List<String> getAttendances() {
		return attendances;
	}
	public void setAttendances(List<String> attendances) {
		this.attendances = attendances;
	}
	public List<Integer> getAnswerIds() {
		return answerIds;
	}
	public void setAnswerIds(List<Integer> answerIds) {
		this.answerIds = answerIds;
	}
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getAnswersheetName() {
		return answersheetName;
	}
	public void setAnswersheetName(String answersheetName) {
		this.answersheetName = answersheetName;
	}
	@Override
	public String getItemDescription() {
		// TODO Auto-generated method stub
//		StringBuilder desc = new StringBuilder();
//		if(paper != null)
//			desc.append(paper.getName() + " ");
//		if(bpartner != null)
//			desc.append("["+bpartner.getName()+"]");
		return getPaper().getName();
	}
	@Override
	public String getItemDescription2() {
		// TODO Auto-generated method stub
		return getStartTime().toLocaleString();
	}
	
	
}
