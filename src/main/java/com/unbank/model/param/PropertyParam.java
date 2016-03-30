package com.unbank.model.param;

/**
 * 
 * @author rrq
 *
 */
public class PropertyParam implements UnbankParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6394805742545219519L;

	private int propertyId;

	private String propertyName;
	
	public PropertyParam() {
		
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyNameById(int propertyId){
		String propertyName = "";
		switch (propertyId) {
			case 1:
				propertyName="住宅";
				break;
			case 2:
				propertyName="写字楼";
				break;			
			case 3:
				propertyName="商业营业用房";
				break;
		}
		
		return propertyName;
	}
}
