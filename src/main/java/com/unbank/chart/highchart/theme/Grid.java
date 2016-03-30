package com.unbank.chart.highchart.theme;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.unbank.chart.highchart.config.Animation;
import com.unbank.chart.highchart.config.Animation.AnimationBuilder;
import com.unbank.chart.highchart.config.Color;
import com.unbank.chart.highchart.config.Color.ColorBuilder;
import com.unbank.chart.highchart.config.Credits;
import com.unbank.chart.highchart.config.Credits.CreditsBuilder;
import com.unbank.chart.highchart.config.Global.GlobalBuilder;
import com.unbank.chart.highchart.config.Highchart;
import com.unbank.chart.highchart.config.Highchart.HighchartBuilder;
import com.unbank.chart.highchart.config.HighchartsConfig.HighchartsConfigBuilder;
import com.unbank.chart.highchart.config.Lang.LangBuilder;
import com.unbank.chart.highchart.config.OptionsConfig.OptionsConfigBuilder;
import com.unbank.chart.highchart.config.Position.PositionBuilder;
import com.unbank.chart.highchart.config.Style;
import com.unbank.chart.highchart.config.ThemeConfig.ThemeConfigBuilder;
import com.unbank.chart.highchart.type.Align;

/**
 * Grid theme for Highcharts JS
 * 
 * @author zile
 *
 */
public class Grid extends AbstractTheme {

	@Override
	protected void config(ThemeConfigBuilder themeConfigBuilder) {
		// themeConfigBuilder.colors("#058DC7", "#50B432", "#ED561B", "#DDDF00",
		// "#24CBE5", "#64E572", "#FF9655", "#FFF263", "#6AF9C4");

	}

	@Override
	public String themename() {
		return "grid";
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Gson g = new Gson();

		HighchartBuilder b = new HighchartBuilder(null);
		AnimationBuilder bb = new AnimationBuilder();
		bb.duration(1500).easing("easeout");
		Animation animation = bb.build();
		b.alignTicks(true);
		b.animation(animation);

		//
		ColorBuilder bcb = new ColorBuilder();
		bcb.linearGradient(0, 0, 500, 500);
		bcb.stops(0, "rgb(255, 255, 255)");
		bcb.stops(1, "rgb(200, 200, 255)");
		Color backgroundColor = bcb.build();

		b.backgroundColor(backgroundColor);

		// css
		Style cssObject = new Style();
		cssObject.put("font", 12);
		cssObject.put("margin", "0px 0px 10px 10px");
		b.style(cssObject);

		//

		Highchart ccc = b.build();
		JsonElement je = g.toJsonTree(ccc, ccc.getClass());
		if (je instanceof JsonObject) {
			// System.out.println("ROOT:" + je);
		}

		//
		HighchartsConfigBuilder hb = new HighchartsConfigBuilder();
		hb.chart(ccc);

		//
		CreditsBuilder crb = new CreditsBuilder();
		PositionBuilder pob = new PositionBuilder();

		crb.href("www.baidu.com").position(
				pob.align(Align.CENTER).x(3).y(66).build());
		Credits credits = crb.build();

		hb.credits(credits);
		System.out.println(hb.build().jsonObject());

		// global
		OptionsConfigBuilder ob = new OptionsConfigBuilder();
		GlobalBuilder gb = new GlobalBuilder();
		gb.canvasToolsURL("url");
		gb.timezoneOffset(24354);
		ob.global(gb.build());

		// lang
		LangBuilder lb = new LangBuilder();
		lb.downloadJPEG("jpeg");
		lb.downloadPDF("pdf");
		ob.lang(lb.build());

		//
		System.out.println(ob.build().jsonObject());

	}

}
