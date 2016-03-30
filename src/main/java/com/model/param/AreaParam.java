package com.model.param;

/**
 * 
 * @author zile
 *
 */
public class AreaParam implements UnbankParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 895376963522820059L;

	private int areaId;

	private String name;

	public AreaParam() {
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
