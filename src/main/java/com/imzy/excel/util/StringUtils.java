package com.imzy.excel.util;

public class StringUtils {
	public static final String EMPTY = "";

	/**
	* <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
	*
	* <pre>
	* StringUtils.isNotBlank(null)      = false
	* StringUtils.isNotBlank("")        = false
	* StringUtils.isNotBlank(" ")       = false
	* StringUtils.isNotBlank("bob")     = true
	* StringUtils.isNotBlank("  bob  ") = true
	* </pre>
	*
	* @param cs  the CharSequence to check, may be null
	* @return {@code true} if the CharSequence is
	*  not empty and not null and not whitespace
	* @since 2.0
	* @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
	*/
	public static boolean isNotBlank(final CharSequence cs) {
		return !isBlank(cs);
	}

	/**
	* <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
	*
	* <pre>
	* StringUtils.isBlank(null)      = true
	* StringUtils.isBlank("")        = true
	* StringUtils.isBlank(" ")       = true
	* StringUtils.isBlank("bob")     = false
	* StringUtils.isBlank("  bob  ") = false
	* </pre>
	*
	* @param cs  the CharSequence to check, may be null
	* @return {@code true} if the CharSequence is null, empty or whitespace
	* @since 2.0
	* @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
	*/
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Compares two CharSequences, returning {@code true} if they represent
	 * equal sequences of characters.</p>
	 *
	 * <p>{@code null}s are handled without exceptions. Two {@code null}
	 * references are considered to be equal. The comparison is case sensitive.</p>
	 *
	 * <pre>
	 * StringUtils.equals(null, null)   = true
	 * StringUtils.equals(null, "abc")  = false
	 * StringUtils.equals("abc", null)  = false
	 * StringUtils.equals("abc", "abc") = true
	 * StringUtils.equals("abc", "ABC") = false
	 * </pre>
	 *
	 * @see Object#equals(Object)
	 * @param cs1  the first CharSequence, may be {@code null}
	 * @param cs2  the second CharSequence, may be {@code null}
	 * @return {@code true} if the CharSequences are equal (case-sensitive), or both {@code null}
	 * @since 3.0 Changed signature from equals(String, String) to equals(CharSequence, CharSequence)
	 */
	public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
		if (cs1 == cs2) {
			return true;
		}
		if (cs1 == null || cs2 == null) {
			return false;
		}
		if (cs1 instanceof String && cs2 instanceof String) {
			return cs1.equals(cs2);
		}
		return CharSequenceUtils.regionMatches(cs1, false, 0, cs2, 0, Math.max(cs1.length(), cs2.length()));
	}

	/**
	 * <p>Compares two CharSequences, returning {@code true} if they represent
	 * equal sequences of characters, ignoring case.</p>
	 *
	 * <p>{@code null}s are handled without exceptions. Two {@code null}
	 * references are considered equal. Comparison is case insensitive.</p>
	 *
	 * <pre>
	 * StringUtils.equalsIgnoreCase(null, null)   = true
	 * StringUtils.equalsIgnoreCase(null, "abc")  = false
	 * StringUtils.equalsIgnoreCase("abc", null)  = false
	 * StringUtils.equalsIgnoreCase("abc", "abc") = true
	 * StringUtils.equalsIgnoreCase("abc", "ABC") = true
	 * </pre>
	 *
	 * @param str1  the first CharSequence, may be null
	 * @param str2  the second CharSequence, may be null
	 * @return {@code true} if the CharSequence are equal, case insensitive, or
	 *  both {@code null}
	 * @since 3.0 Changed signature from equalsIgnoreCase(String, String) to equalsIgnoreCase(CharSequence, CharSequence)
	 */
	public static boolean equalsIgnoreCase(final CharSequence str1, final CharSequence str2) {
		if (str1 == null || str2 == null) {
			return str1 == str2;
		} else if (str1 == str2) {
			return true;
		} else if (str1.length() != str2.length()) {
			return false;
		} else {
			return CharSequenceUtils.regionMatches(str1, true, 0, str2, 0, str1.length());
		}
	}
}
