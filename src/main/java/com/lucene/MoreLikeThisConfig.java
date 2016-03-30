package com.lucene;

public class MoreLikeThisConfig {

	private int minTermFreq = 4;

	private int minDocFreq = 3;

	private int minWordLen = 2;

	public MoreLikeThisConfig() {
		// TODO Auto-generated constructor stub
	}

	public int getMinTermFreq() {
		return minTermFreq;
	}

	public void setMinTermFreq(int minTermFreq) {
		this.minTermFreq = minTermFreq;
	}

	public int getMinDocFreq() {
		return minDocFreq;
	}

	public void setMinDocFreq(int minDocFreq) {
		this.minDocFreq = minDocFreq;
	}

	public int getMinWordLen() {
		return minWordLen;
	}

	public void setMinWordLen(int minWordLen) {
		this.minWordLen = minWordLen;
	}

}
