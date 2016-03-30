package com.unbank.model.param;

public class BankParam implements UnbankParam {

	private static final long serialVersionUID = 895376963522820059L;

	private int typeId;
	private String typeName;

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
