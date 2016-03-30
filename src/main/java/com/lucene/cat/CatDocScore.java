package com.lucene.cat;

public class CatDocScore implements Comparable<CatDocScore> {

	private int score;

	private DocMapper dm;

	public CatDocScore() {
		// TODO Auto-generated constructor stub
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public DocMapper getDm() {
		return dm;
	}

	public void setDm(DocMapper dm) {
		this.dm = dm;
	}

	@Override
	public int compareTo(CatDocScore o) {

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


}
