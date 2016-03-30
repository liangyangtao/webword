package com.unbank.model.param;

public class DatafieldParam implements UnbankParam {

	private static final long serialVersionUID = 5697334471552912264L;
	
	private int bulkId;
	private String bulkName;
	
	public int getBulkId() {
		return bulkId;
	}
	public void setBulkId(int bulkId) {
		this.bulkId = bulkId;
	}
	public String getBulkName() {
		return bulkName;
	}
	public void setBulkName(String bulkName) {
		this.bulkName = bulkName;
	}

}
