package com.chart.highchart.def;

import com.chart.highchart.config.Credits.CreditsBuilder;
import com.chart.highchart.config.Exporting.ExportingBuilder;
import com.chart.highchart.config.Global.GlobalBuilder;
import com.chart.highchart.config.Lang.LangBuilder;
import com.chart.highchart.config.Title.SubtitleBuilder;

public interface DefaultConfig {

	//

	boolean global(GlobalBuilder builder);

	boolean lang(LangBuilder builder);

	//

	// boolean chart(HighchartBuilder builder);

	String[] colors();

	boolean credits(CreditsBuilder builder);

	// boolean drilldown(DrilldownBuilder builder);

	boolean exporting(ExportingBuilder builder);

	// boolean labels(LabelsBuilder builder);

	// boolean legend(LegendBuilder builder);

	// boolean loading(LoadingBuilder builder);

	// boolean navigation(NavigationBuilder builder);

	// boolean noData(NoDataBuilder builder);

	// boolean pane(PaneBuilder builder);

	// boolean plotOptions(PlotOptionsBuilder builder);

	// boolean series( builder);

	boolean subtitle(SubtitleBuilder builder);

	// boolean title(TitleBuilder builder);

	// boolean tooltip(TooltipBuilder builder);

	// boolean xAxis(XAxisBuilder builder);

	// boolean yAxis(YAxisBuilder builder);

}
