package org.it.utils.sgd.delivery;

import java.util.Date;

public class ContinueAfter implements After{
	
	private String context;
	private Date date;
	
	public ContinueAfter(String context, Date date){
		this.context = context;
		this.date = date;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

}
