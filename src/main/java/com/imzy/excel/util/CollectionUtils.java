package com.imzy.excel.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CollectionUtils {

	/**
	 * Null-safe check if the specified collection is not empty.
	 * <p>
	 * Null returns false.
	 * 
	 * @param coll  the collection to check, may be null
	 * @return true if non-null and non-empty
	 * @since Commons Collections 3.2
	 */
	public static boolean isNotEmpty(Collection coll) {
		return !CollectionUtils.isEmpty(coll);
	}

	// -----------------------------------------------------------------------
	/**
	 * Null-safe check if the specified collection is empty.
	 * <p>
	 * Null returns true.
	 * 
	 * @param coll  the collection to check, may be null
	 * @return true if empty or null
	 * @since Commons Collections 3.2
	 */
	public static boolean isEmpty(Collection coll) {
		return (coll == null || coll.isEmpty());
	}

	/** 
	 * Selects all elements from input collection which match the given predicate
	 * into an output collection.
	 * <p>
	 * A <code>null</code> predicate matches no elements.
	 * 
	 * @param inputCollection  the collection to get the input from, may not be null
	 * @param predicate  the predicate to use, may be null
	 * @return the elements matching the predicate (new list)
	 * @throws NullPointerException if the input collection is null
	 */
	public static Collection select(Collection inputCollection, Predicate predicate) {
		ArrayList answer = new ArrayList(inputCollection.size());
		select(inputCollection, predicate, answer);
		return answer;
	}

	/** 
	 * Selects all elements from input collection which match the given predicate
	 * and adds them to outputCollection.
	 * <p>
	 * If the input collection or predicate is null, there is no change to the 
	 * output collection.
	 * 
	 * @param inputCollection  the collection to get the input from, may be null
	 * @param predicate  the predicate to use, may be null
	 * @param outputCollection  the collection to output into, may not be null
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

	public interface Predicate {
		boolean evaluate(Object object);
	}
}
