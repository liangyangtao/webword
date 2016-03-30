package com.unbank.model.param;

public class PublishParam implements UnbankParam {

	/*
	 * / author：rrq
	 */

	private static final long serialVersionUID = 895376963522820059L;

	private int publisherId;
	private String publisherName;

	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String publishDimensionality(int publisherId) {

		String publisherName = "";
		switch (publisherId) {
		case 1:
			publisherName = "个人";

			break;

		case 2:
			publisherName = "机构";

			break;
		case 3:
			publisherName = "个人和机构";

			break;

		}
		return publisherName;
	}

}
