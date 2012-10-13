package org.silk.checklist.model;

import java.io.Serializable;

public class Option implements Serializable{
	private int optionId;
	private int choiceId;
	private String optionText;
	
	public Option() {
		super();
	}
	
	public Option(int optionId, String optionText) {
		super();
		this.optionId = optionId;
		this.optionText = optionText;
	}

	public Option(int optionId, int choiceId,String optionText) {
		super();
		this.optionId = optionId;
		this.choiceId = choiceId;
		this.optionText = optionText;
	}

	public Option(String optionText) {
		super();
		this.optionText = optionText;
	}
	

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	@Override
	public String toString() {
		return "Option [choiceOptionText=" + optionText + "]";
	}

	public int getChoiceId() {
		return choiceId;
	}

	public void setChoiceId(int choiceId) {
		this.choiceId = choiceId;
	}
	

	
	
	
}
