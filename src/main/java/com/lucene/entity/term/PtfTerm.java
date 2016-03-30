package com.lucene.entity.term;

import java.util.List;

import com.lucene.entity.UbkTable;

public class PtfTerm extends UbkTable {

	private int termId;

	private int structureId;

	private int displayOrder;

	private String title;

	private String term;

	private List<Term> terms;

	public PtfTerm() {
		// TODO Auto-generated constructor stub
	}

	public int getTermId() {
		return termId;
	}

	public void setTermId(int termId) {
		this.termId = termId;
	}

	public int getStructureId() {
		return structureId;
	}

	public void setStructureId(int structureId) {
		this.structureId = structureId;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public List<Term> getTerms() {
		return terms;
	}

	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}

}
