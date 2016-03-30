package com.lucene.entity.doc;


public class Keyword implements Comparable<Keyword> {

	/**
	 * 关键词
	 */
	private String name;

	/**
	 * 出现次数
	 */
	private int count;

	/**
	 * 评分
	 */
	private int score;

	public Keyword() {
		// TODO Auto-generated constructor stub
	}

	public Keyword(String name) {
		this.name = name;
	}

	public Keyword(String name, int count, int score) {
		this.name = name;
		this.count = count;
		this.score = score;
	}

	@Override
	public int compareTo(Keyword o) {
		if (o.score > score) {
			return 1;
		} else if (score == o.score) {
			return 0;
		} else {
			return -1;
		}
		
	}
	@Override
	public boolean equals(Object o) { 
		return this == o;
	}
	@Override
	public int hashCode() {
		return score;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
