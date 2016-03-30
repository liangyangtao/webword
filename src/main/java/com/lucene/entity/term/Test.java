package com.lucene.entity.term;

import java.util.ArrayList;
import java.util.List;

import com.lucene.entity.term.Term.TermBuilder;

public class Test {

	public void a() {
		List<Term> terms = new ArrayList<Term>();

		//

		TermBuilder builder = new Term.TermBuilder("我的关键词");
		builder.date("2014-01-01", "2014-01-09");
		for (int i = 0; i < 15; i++) {
			builder.addStructure(i);
			builder.addWebsite(i * 3);
		}

		builder.local(true);
		builder.crawl(true);
		Period period = new PeriodDay(30);
		builder.period(period);

		Term term = builder.build();
		terms.add(term);

		//

		builder = new Term.TermBuilder("aaaa");
		builder.date("2018-08-01", "2018-08-09");
		for (int i = 66; i < 88; i++) {
			builder.addStructure(i);
			builder.addWebsite(i * 3);
		}

		builder.local(true);
		builder.crawl(true);
		period = new PeriodMonth("2011-01-01", "2011-04-05");
		builder.period(period);

		term = builder.build();
		terms.add(term);

		String json = Term.serialize(terms);
		System.out.println(json);

		List<Term> list = Term.deserialize(json);

		for (Term tt : list) {
			System.out.println(tt);
		}

	}

	public static void main(String[] args) {
		Test t = new Test();
		t.a();

	}

}
