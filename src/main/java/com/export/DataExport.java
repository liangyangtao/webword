package com.export;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 数据导出写XLS文件的类
 * 
 * @author qzl
 * 
 */
public class DataExport {

	private WritableWorkbook workbook;

	/**
	 * 列宽度
	 */
	private static final int COLUMN_VIEW = 30;

	/**
	 * 转成excel
	 * 
	 * @param title
	 *            指标集合
	 * @param unit
	 *            单位集合
	 * @param data
	 *            数据集合
	 * @param file
	 *            导出文件路径
	 * @param len
	 *            列数
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void convertToExcel(List<Map<String, Object>> titleAndUnit,
			List<Map<String, Object>> data, File file, int len, int fraction)
			throws IOException, RowsExceededException, WriteException {

		int total = 0;
		int row = 0;

		WorkbookSettings ws = new WorkbookSettings();
		ws.setLocale(new Locale("zh", "CN"));
		workbook = Workbook.createWorkbook(file, ws);
		WritableSheet ws0 = workbook.createSheet("银联信", 0);

		// title and unit
		ws0.setColumnView(0, COLUMN_VIEW);
		ws0.addCell(new Label(0, row, "指标名称"));
		ws0.addCell(new Label(0, row + 1, "单位"));
		for (int i = 0; i < titleAndUnit.size(); i++) {
			String title = titleAndUnit.get(i).get("value").toString();
			ws0.setColumnView(i + 1, COLUMN_VIEW);
			ws0.addCell(new Label(i + 1, row, title));

			String unit = titleAndUnit.get(i).get("unit").toString();
			ws0.addCell(new Label(i + 1, row + 1, unit));
		}

		row += 2;

		// data
		boolean flag = fraction > 0;
		for (int i = 0; i < data.size(); i++) {
			String date = data.get(i).get("date").toString();
			ws0.addCell(new Label(0, i + row, date));
			for (int j = 0; j < len; j++) {
				Object obj = data.get(i).get(String.valueOf(j));
				if (obj != null) {
					String label = null;
					if (flag) {
						label = new BigDecimal(obj.toString()).setScale(
								fraction, BigDecimal.ROUND_HALF_DOWN)
								.toString();
					} else {
						label = new BigDecimal(obj.toString())
								.stripTrailingZeros().toPlainString();
					}
					ws0.addCell(new Label(j + 1, i + row, label));

					total++;
				}
			}
		}

		row++;

		ws0.addCell(new Label(0, data.size() + row, "共有[" + total + "]条数据"));

		//
		workbook.write();
		workbook.close();

	}

	public void convertToExcel(List<ChartsData> chartsDataL,File file,  int fraction) throws RowsExceededException, WriteException, IOException {
		int total = 0;
		int row = 0;

		WorkbookSettings ws = new WorkbookSettings();
		ws.setLocale(new Locale("zh", "CN"));
		workbook = Workbook.createWorkbook(file, ws);
		WritableSheet ws0 = workbook.createSheet("银联信", 0);
		boolean flag = fraction > 0;
		Object obj;
		String label = null;
		// title and unit
		ws0.setColumnView(0, COLUMN_VIEW);
		ws0.addCell(new Label(0, row, "指标名称"));
		for (int m = 0; m < chartsDataL.get(0).getCategories().size(); m++) {
			obj = chartsDataL.get(0).getCategories().get(m);
			if (obj != null) {
				ws0.addCell(new Label(0, m+2, String.valueOf(obj)));
			}				
		}	
		ws0.addCell(new Label(0, row + 1, "单位"));
		for (int i = 0; i < chartsDataL.size(); i++) {
			String title = chartsDataL.get(i).getName();
			ws0.setColumnView(i + 1, COLUMN_VIEW);
			ws0.addCell(new Label(i + 1, row, title));

			String unit = chartsDataL.get(i).getDanwei();
			ws0.addCell(new Label(i + 1, row + 1, unit));
			for (int n = 0; n < chartsDataL.get(0).getData().size(); n++) {
				obj = chartsDataL.get(0).getData().get(n);				
				if (obj != null) {
					if (flag) {
						label = new BigDecimal(obj.toString()).setScale(
								fraction, BigDecimal.ROUND_HALF_DOWN)
								.toString();
					} else {
						label = new BigDecimal(obj.toString())
								.stripTrailingZeros().toPlainString();
					}
					ws0.addCell(new Label(i + 1, n + 2, label));

					total++;
				}		
			}
			

		}
		row++;
		ws0.addCell(new Label(0, chartsDataL.get(0).getData().size() + row+2, "共有[" + total + "]条数据"));

		//
		workbook.write();
		workbook.close();
		
	}
}
