package com.lucene.entity.doc;


import java.util.ArrayList;
import java.util.List;

public class UbkSearch {

	private List<Content> documents = new ArrayList<Content>();

	private List<Crawl> crawls = new ArrayList<Crawl>();

	public UbkSearch() {
		// TODO Auto-generated constructor stub
	}

	public List<Content> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Content> documents) {
		this.documents = documents;
	}

	public List<Crawl> getCrawls() {
		return crawls;
	}

	public void setCrawls(List<Crawl> crawls) {
		this.crawls = crawls;
	}

}
