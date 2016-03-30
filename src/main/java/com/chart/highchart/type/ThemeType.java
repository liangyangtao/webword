package com.chart.highchart.type;

import com.chart.highchart.theme.DarkBlue;
import com.chart.highchart.theme.DarkGreen;
import com.chart.highchart.theme.DarkUnica;
import com.chart.highchart.theme.Gray;
import com.chart.highchart.theme.Grid;
import com.chart.highchart.theme.GridLight;
import com.chart.highchart.theme.SandSignika;
import com.chart.highchart.theme.Skies;
import com.chart.highchart.theme.Theme;

/**
 * 
 * @author zile
 *
 */
public enum ThemeType {

	DARK_BLUE("dark-blue") {
		@Override
		public Theme theme() {
			return new DarkBlue();
		}
	},

	DARK_GREEN("dark-green") {
		@Override
		public Theme theme() {
			return new DarkGreen();
		}
	},

	DARK_UNICA("dark-unica") {
		@Override
		public Theme theme() {
			return new DarkUnica();
		}
	},

	GRAY("gray") {
		@Override
		public Theme theme() {
			return new Gray();
		}
	},

	GRID("grid") {
		@Override
		public Theme theme() {
			return new Grid();
		}
	},

	GRID_LIGHT("grid-light") {
		@Override
		public Theme theme() {
			return new GridLight();
		}
	},

	SAND_SIGNIKA("sand-signika") {
		@Override
		public Theme theme() {
			return new SandSignika();
		}
	},

	SKIES("skies") {
		@Override
		public Theme theme() {
			return new Skies();
		}
	};

	private final String name;

	private ThemeType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract Theme theme();

}
