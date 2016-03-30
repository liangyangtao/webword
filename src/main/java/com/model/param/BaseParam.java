package com.model.param;

import com.chart.highchart.type.ThemeType;

/**
 * 
 * @author zile
 *
 */
public class BaseParam implements UnbankParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999604190734659207L;

	/**
	 * <p>
	 * 插件文本中图片链接的前缀, e.g http://10.0.0.1/ubkdata/updown
	 * </p>
	 * 
	 * <p>
	 * 模板引擎中通过此属性添加链接前缀
	 * </p>
	 * 
	 * @deprecated 客户端切面中处理来替代这种方式
	 */
	@Deprecated
	private String linkPrefix;

	/**
	 * 主题类型
	 */
	private ThemeType type;

	public BaseParam() {
	}

	@Deprecated
	public String getLinkPrefix() {
		return linkPrefix;
	}

	@Deprecated
	public void setLinkPrefix(String linkPrefix) {
		this.linkPrefix = linkPrefix;
	}

	public ThemeType getType() {
		return type;
	}

	public void setType(ThemeType type) {
		this.type = type;
	}

}
