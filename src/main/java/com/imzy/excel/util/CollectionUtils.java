package com.imzy.excel.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 集合工具
 * @author yangzhang7
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CollectionUtils {

	/**
	 * 不为空
	 * @param coll
	 * @return
	 */
	public static boolean isNotEmpty(Collection coll) {
		return !CollectionUtils.isEmpty(coll);
	}

	/**
	 * 为空
	 * @param coll
	 * @return
	 */
	public static boolean isEmpty(Collection coll) {
		return (coll == null || coll.isEmpty());
	}

	/**
	 * 筛选元素
	 * @param inputCollection
	 * @param predicate
	 * @return
	 */
	public static Collection select(Collection inputCollection, Predicate predicate) {
		ArrayList answer = new ArrayList(inputCollection.size());
		select(inputCollection, predicate, answer);
		return answer;
	}

	/**
	 * 筛选元素
	 * @param inputCollection
	 * @param predicate
	 * @param outputCollection
	 */
	public static void select(Collection inputCollection, Predicate predicate, Collection outputCollection) {
		if (inputCollection != null && predicate != null) {
			for (Iterator iter = inputCollection.iterator(); iter.hasNext();) {
				Object item = iter.next();
				if (predicate.evaluate(item)) {
					outputCollection.add(item);
				}
			}
		}
	}

	/**
	 * 断言接口
	 * @author yangzhang7
	 *
	 */
	public interface Predicate {
		boolean evaluate(Object object);
	}
}
