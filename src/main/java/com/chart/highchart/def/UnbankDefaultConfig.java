package com.chart.highchart.def;

import com.chart.highchart.config.Credits.CreditsBuilder;
import com.chart.highchart.config.Exporting.ExportingBuilder;
import com.chart.highchart.config.Global.GlobalBuilder;
import com.chart.highchart.config.Lang.LangBuilder;
import com.chart.highchart.config.Title.SubtitleBuilder;

/**
 * 默认配置
 * 
 * @author zile
 * 
 */
public class UnbankDefaultConfig implements DefaultConfig {

	@Override
	public boolean global(GlobalBuilder builder) {
		// builder.VMLRadialGradientURL("");
		// builder.canvasToolsURL("");
		builder.timezoneOffset(0);
		builder.useUTC(true);
		return true;
	}

	@Override
	public boolean lang(LangBuilder builder) {
		builder.contextButtonTitle("图表菜单");
		builder.downloadJPEG("下载JPEG格式图像");
		builder.downloadPDF("下载PDF文档");
		builder.downloadPNG("下载PNG格式图像");
		builder.downloadSVG("下载SVG矢量图");
		builder.noData("没有数据");
		builder.thousandsSep("");
		return true;
	}

	@Override
	public String[] colors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean credits(CreditsBuilder builder) {
		builder.href("http://www.unbank.info");
		builder.text("www.unbank.info");
		return true;
	}

	@Override
	public boolean exporting(ExportingBuilder builder) {
		builder.filename("chart");
		builder.url("http://export.highcharts.com"); // 自己创建导出服务器
		return true;
	}

	@Override
	public boolean subtitle(SubtitleBuilder builder) {
		builder.text("来源: 银联信");
		builder.x(-20);
		return true;
	}

}
