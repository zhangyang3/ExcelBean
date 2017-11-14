package com.imzy.excel.util;

/**
 * String工具
 * @author yangzhang7
 *
 */
public class StringUtils {
	public static final String EMPTY = "";

	public static boolean isNotBlank(final CharSequence cs) {
		return !isBlank(cs);
	}

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
