package com.lucene.entity.term;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lucene.entity.UbkTable;

public class Condition extends AbstractCondition {

	// property's value
	private static final String TERM_ID = "termId";
	private static final String CONDITIONS = "conditions";

	//
	private int offset;
	private int rowCount;

	//
	private int termId;
	private List<Term> terms;

	//
	private UbkTable ubkTable;

	public Condition(String filter, int offset, int rowCount)
			throws JSONException {
		this.filter = filter;
		this.offset = offset;
		this.rowCount = rowCount;
		JSONArray array = new JSONArray(filter);

		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			String field = obj.getString(PROPERTY);
			if (TERM_ID.equals(field)) {
				this.termId = obj.getInt(VALUE);
			} else if (CONDITIONS.equals(field)) {
				JSONArray cons = new JSONArray(obj.getString(VALUE));
				this.terms = Term.deserialize(cons.toString());
			}
		}
	}

	public Condition(List<Term> terms) {
		this.terms = terms;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getTermId() {
		return termId;
	}

	public List<Term> getTerms() {
		return terms;
	}

	public UbkTable getUbkTable() {
		return ubkTable;
	}

	public void setUbkTable(UbkTable ubkTable) {
		this.ubkTable = ubkTable;
	}

}
