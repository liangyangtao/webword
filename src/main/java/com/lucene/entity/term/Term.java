package com.lucene.entity.term;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.lucene.Builder;


public class Term {

	protected static Logger logger = Logger.getLogger(Term.class);

	/**
	 * 序列化到json字符串
	 * 
	 * @param terms
	 * @return
	 */
	public static String serialize(List<Term> terms) {
		try {
			return JSONUtil.serialize(terms);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 反序列化至条件对象集合
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Term> deserialize(String json) {
		List<Term> terms = new ArrayList<Term>();
		try {
			List<Map<String, Object>> list = (List<Map<String, Object>>) JSONUtil
					.deserialize(json);
			for (Map<String, Object> map : list) {
				Object objKeyword = map.get("keyword");
				if (objKeyword != null) {
					TermBuilder builder = new Term.TermBuilder(
							objKeyword.toString());

					Object objFrom = map.get("from");
					Object objTo = map.get("to");

					//
					Object objPeriod = map.get("period");
					int objPeriodType = Integer.parseInt(map.get("periodType")
							.toString());
					boolean local = new Boolean(map.get("local").toString());
					boolean crawl = new Boolean(map.get("crawl").toString());
					Object objWebsites = map.get("websites");
					Object objStructures = map.get("structures");
					Object objStructureLabels = map.get("structureLabels");

					//
					if (objFrom != null && objTo != null) {
						builder.date(objFrom.toString(), objTo.toString());
					}

					builder.local(local);
					builder.crawl(crawl);

					//
					if (objWebsites != null) {
						List<Long> websites = (List<Long>) objWebsites;
						for (long websiteId : websites) {
							builder.addWebsite((int) websiteId);
						}
					}

					//
					if (objStructures != null) {
						List<Long> structures = (List<Long>) objStructures;
						for (long structureId : structures) {
							builder.addStructure((int) structureId);
						}
					}

					//
					if (objStructureLabels != null) {
						List<String> labels = (List<String>) objStructureLabels;
						for (String label : labels) {
							builder.addStructureLabels(label);
						}
					}

					// 定制周期
					if (objPeriod != null) {
						Map<String, Object> p = (Map<String, Object>) objPeriod;

						Period period = null;
						switch (objPeriodType) {
						case 1:
							Object pDays = p.get("days");
							period = new PeriodDay(Integer.parseInt(pDays
									.toString()));
							break;
						case 2:
							Object pFrom = p.get("from");
							Object pTo = p.get("to");
							period = new PeriodMonth(pFrom.toString(),
									pTo.toString());
							break;
						default:
							break;
						}
						if (period != null) {
							builder.period(period);
						}
					}

					terms.add(builder.build());
				}

			}
		} catch (JSONException e) {
			logger.error("搜索条件解析错误! JSON:" + json, e);
		}
		return terms;
	}

	/**
	 * 关键词
	 */
	private final String keyword;

	/**
	 * 开始时间
	 */
	private String from;

	/**
	 * 结束时间
	 */
	private String to;

	/**
	 * 定制周期类型
	 */
	private int periodType;

	/**
	 * 定制周期
	 */
	private Period period;

	/**
	 * 本地编辑文章
	 */
	private final boolean local;

	/**
	 * 网络采集文章
	 */
	private final boolean crawl;
	private final List<Integer> websites; // 特定网址ID, website_id

	/**
	 * 产品属性ID集合
	 */
	private final List<Integer> structures; // 特定structure_id
	private final List<String> structureLabels; //

	private Term(TermBuilder builder) {
		this.keyword = builder.keyword;
		this.from = builder.from;
		this.to = builder.to;
		this.periodType = builder.periodType;
		this.period = builder.period;

		//
		this.local = builder.local;
		this.crawl = builder.crawl;
		this.websites = builder.websites;
		this.structures = builder.structures;
		this.structureLabels = builder.structureLabels;
	}

	/**
	 * 建造模式
	 * 
	 * @author Administrator
	 * 
	 */
	public static class TermBuilder implements Builder<Term> {

		private String keyword;

		// Optional parameters
		private String from;
		private String to;
		private int periodType;
		private Period period;

		//
		private boolean local;
		private boolean crawl;
		private List<Integer> websites = new ArrayList<Integer>();
		private List<Integer> structures = new ArrayList<Integer>();
		private List<String> structureLabels = new ArrayList<String>(); //

		public TermBuilder(String keyword) {
			// if (keyword == null || "".equals(keyword)) {
			// throw new NullPointerException("keyword can not be empty!");
			// }
			this.keyword = keyword;
		}

		public TermBuilder date(String from, String to) {
			if (from == null || to == null) {
				throw new IllegalArgumentException("date params error!");
			}
			this.from = from;
			this.to = to;
			return this;
		}

		public TermBuilder period(Period period) {

			// if (period == null) {
			// throw new IllegalArgumentException("period params error!");
			// }

			if (period != null) {
				this.periodType = period.periodType();
				this.period = period;
			}

			return this;
		}

		public TermBuilder local(boolean local) {
			this.local = local;
			return this;
		}

		public TermBuilder crawl(boolean crawl) {
			this.crawl = crawl;
			return this;
		}

		public TermBuilder addWebsite(int websiteId) {
			websites.add(websiteId);
			return this;
		}

		public TermBuilder addStructure(int structureId) {
			structures.add(structureId);
			return this;
		}

		public TermBuilder addStructureLabels(String label) {
			structureLabels.add(label);
			return this;
		}

		@Override
		public Term build() {
			return new Term(this);
		}

	}

	public void updateFrom(String from) {
		this.from = from;
	}

	public void updateTo(String to) {
		this.to = to;
	}

	public void updatePeriod(Period period) {
		if (period == null)
			this.periodType = 0;
		else
			this.periodType = period.periodType();
		this.period = period;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public int getPeriodType() {
		return periodType;
	}

	public Period getPeriod() {
		return period;
	}

	public boolean isLocal() {
		return local;
	}

	public boolean isCrawl() {
		return crawl;
	}

	public List<Integer> getWebsites() {
		return websites;
	}

	public List<Integer> getStructures() {
		return structures;
	}

	public List<String> getStructureLabels() {
		return structureLabels;
	}

}
