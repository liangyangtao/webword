package com.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author zile
 *
 */
public class UnbankModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5095415360111681052L;

	/**
	 * 模型数据
	 */
	private Map<String, Object> data = new HashMap<String, Object>();

	/**
	 * 图片文件
	 */
	private List<HighchartsFile> files = new ArrayList<HighchartsFile>();

	/**
	 * 视图
	 */
	private String template;

	//

	public Object put(String key, Object value) {
		return data.put(key, value);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		data.putAll(m);
	}

	/**
	 * 添加图片文件
	 * 
	 * @param e
	 * @return
	 */
	public boolean addFile(HighchartsFile file) {
		return files.add(file);
	}

	//

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public List<HighchartsFile> getFiles() {
		return files;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
