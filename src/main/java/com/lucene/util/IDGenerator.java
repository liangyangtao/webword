package com.lucene.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.lucene.dao.IDGen;


/**
 * 
 * @author quzile
 * 
 */
public class IDGenerator {

	/**
	 * Use volatile to force JVM Do Not Reordering Instruction!
	 */
	private static volatile Map<String, AtomicLong> generator = new ConcurrentHashMap<String, AtomicLong>();

	/**
	 * double check, up to version jdk1.5!
	 * 
	 * @param tablename
	 * @return
	 */
	public static long generateSerialNumber(String tablename, IDGen gen) {
		if (!generator.containsKey(tablename))
			synchronized (generator) {
				if (!generator.containsKey(tablename))
					generator.put(tablename,
							new AtomicLong(gen.findMaxId() + 1));
			}
		return generator.get(tablename).getAndIncrement();
	}

}
