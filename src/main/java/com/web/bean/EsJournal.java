package com.web.bean;

import java.util.List;

public class EsJournal {

	private String journalId;
	
	private String journalName;
	
	private String journalTitle;
	
	private String journalTotal;
	
	private String searchCount;
	
	public String getJournalTotal() {
		return journalTotal;
	}

	public void setJournalTotal(String journalTotal) {
		this.journalTotal = journalTotal;
	}

	public String getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(String searchCount) {
		this.searchCount = searchCount;
	}

	private List<Document> docs;
	

	public String getJournalId() {
		return journalId;
	}

	public void setJournalId(String journalId) {
		this.journalId = journalId;
	}

	public String getJournalName() {
		return journalName;
	}

	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}

	public List<Document> getDocs() {
		return docs;
	}

	public void setDocs(List<Document> docs) {
		this.docs = docs;
	}

	public String getJournalTitle() {
		return journalTitle;
	}

	public void setJournalTitle(String journalTitle) {
		this.journalTitle = journalTitle;
	}

	
	
}
