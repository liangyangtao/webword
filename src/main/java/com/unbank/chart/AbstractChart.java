package com.unbank.chart;

import java.util.ArrayList;
import java.util.List;

import com.unbank.chart.highchart.config.ChartConfig;

/**
 * 缺省适配器
 * 
 * @author zile
 * 
 * @param <T>
 *            图形 核心配置器
 * @param <V>
 *            实体信息以及额外信息, 包括异常信息, 时间, 等等
 */
public abstract class AbstractChart<T extends ChartConfig, V extends ResultEntity<T>>
		implements Chart<T> {

	/**
	 * 图形生成过程的异常信息记录列表
	 */
	protected List<Throwable> messages = new ArrayList<Throwable>();
	protected long time; // 执行时间

	public AbstractChart() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public T chartConfigEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 模板方法, 非public, 通过Charts类统一调用
	 */
	protected void template() {
		long start = System.currentTimeMillis();
		process();
		long end = System.currentTimeMillis();
		time = end - start;
	};

	/**
	 * 处理过程
	 */
	protected abstract void process();

	/**
	 * 结果实体
	 * 
	 * @return
	 */
	protected abstract V exportForEntity();

}
