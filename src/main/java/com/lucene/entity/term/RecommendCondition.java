package com.lucene.entity.term;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lucene.MoreLikeThisConfig;

public class RecommendCondition extends AbstractCondition {

	// property's value
	private static final String TEMPLATE_ID = "templateId";
	private static final String STRUCTURE_ID = "structureId";

	private static final String MIN_TERM_FREQ = "minTermFreq";
	private static final String MIN_DOC_FREQ = "minDocFreq";
	private static final String MIN_WORD_LEN = "minWordLen";

	private int templateId;

	private int structureId;

	private MoreLikeThisConfig mltc = new MoreLikeThisConfig();

	public RecommendCondition(String filter) throws JSONException {
		JSONArray array = new JSONArray(filter);
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			String field = obj.getString(PROPERTY);
			if (TEMPLATE_ID.equals(field)) {
				this.templateId = obj.getInt(VALUE);
			} else if (STRUCTURE_ID.equals(field)) {
				this.structureId = obj.getInt(VALUE);
			} else if (MIN_TERM_FREQ.equals(field)) {
				mltc.setMinTermFreq(obj.getInt(VALUE));
			} else if (MIN_DOC_FREQ.equals(field)) {
				mltc.setMinDocFreq(obj.getInt(VALUE));
			} else if (MIN_WORD_LEN.equals(field)) {
				mltc.setMinWordLen(obj.getInt(VALUE));
			}
		}
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getStructureId() {
		return structureId;
	}

	public void setStructureId(int structureId) {
		this.structureId = structureId;
	}

	public MoreLikeThisConfig getMltc() {
		return mltc;
	}

	public void setMltc(MoreLikeThisConfig mltc) {
		this.mltc = mltc;
	}

}
