package com.xiu.common.web.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : mike@xiu.com
 * @DATE :2013-5-22 下午3:28:58
 *       </p>
 **************************************************************** 
 */
public class StringUtil {
    public static final String EMPTY_STRING = "";

    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));
    }

    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.length() > 0));
    }

    public static boolean isBlank(String str) {
        int length;
        if ((str == null) || ((length = str.length()) == 0))
            return true;
        for (int i = 0; i < length; ++i) {
            if (!(Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNotBlank(String str) {
        int length;
        if ((str == null) || ((length = str.length()) == 0))
            return false;
        for (int i = 0; i < length; ++i) {
            if (!(Character.isWhitespace(str.charAt(i)))) {
                return true;
            }
        }

        return false;
    }

    public static String defaultIfNull(String str) {
        return ((str == null) ? "" : str);
    }

    public static String defaultIfNull(String str, String defaultStr) {
        return ((str == null) ? defaultStr : str);
    }

    public static String defaultIfEmpty(String str) {
        return ((str == null) ? "" : str);
    }

    public static String defaultIfEmpty(String str, String defaultStr) {
        return (((str == null) || (str.length() == 0)) ? defaultStr : str);
    }

    public static String defaultIfBlank(String str) {
        return ((isBlank(str)) ? "" : str);
    }

    public static String defaultIfBlank(String str, String defaultStr) {
        return ((isBlank(str)) ? defaultStr : str);
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return (str2 == null);
        }

        return str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return (str2 == null);
        }

        return str1.equalsIgnoreCase(str2);
    }

    public static boolean isAlpha(String str) {
        if (str == null) {
            return false;
        }

        int length = str.length();

        for (int i = 0; i < length; ++i) {
            if (!(Character.isLetter(str.charAt(i)))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAlphaSpace(String str) {
        if (str == null) {
            return false;
        }

        int length = str.length();

        for (int i = 0; i < length; ++i) {
            if ((!(Character.isLetter(str.charAt(i)))) && (str.charAt(i) != ' ')) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAlphanumeric(String str) {
        if (str == null) {
            return false;
        }

        int length = str.length();

        for (int i = 0; i < length; ++i) {
            if (!(Character.isLetterOrDigit(str.charAt(i)))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAlphanumericSpace(String str) {
        if (str == null) {
            return false;
        }

        int length = str.length();

        for (int i = 0; i < length; ++i) {
            if ((!(Character.isLetterOrDigit(str.charAt(i)))) && (str.charAt(i) != ' ')) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }

        int length = str.length();

        for (int i = 0; i < length; ++i) {
            if (!(Character.isDigit(str.charAt(i)))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNumericSpace(String str) {
        if (str == null) {
            return false;
        }

        int length = str.length();

        for (int i = 0; i < length; ++i) {
            if ((!(Character.isDigit(str.charAt(i)))) && (str.charAt(i) != ' ')) {
                return false;
            }
        }

        return true;
    }

    public static boolean isWhitespace(String str) {
        if (str == null) {
            return false;
        }

        int length = str.length();

        for (int i = 0; i < length; ++i) {
            if (!(Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }

        return true;
    }

    public static String toUpperCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toUpperCase();
    }

    public static String toLowerCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toLowerCase();
    }

    public static String[] split(String str) {
        return split(str, null, -1);
    }

    public static String[] split(String str, String separatorChars, int max) {
        if (str == null) {
            return null;
        }

        int length = str.length();

        if (length == 0) {
            return new String[0];
        }

        List list = new ArrayList();
        int sizePlus1 = 1;
        int i = 0;
        int start = 0;
        boolean match = false;

        if (separatorChars == null) {
            while (i < length)
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }

                        list.add(str.substring(start, i));
                        match = false;
                    }

                    start = ++i;
                } else {
                    match = true;
                    ++i;
                }
        } else if (separatorChars.length() == 1) {
            char sep = separatorChars.charAt(0);

            while (i < length)
                if (str.charAt(i) == sep) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }

                        list.add(str.substring(start, i));
                        match = false;
                    }

                    start = ++i;
                } else {
                    match = true;
                    ++i;
                }
        } else {
            do
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }

                        list.add(str.substring(start, i));
                        match = false;
                    }

                    start = ++i;
                } else {
                    match = true;
                    ++i;
                }
            while (i < length);
        }

        if (match) {
            list.add(str.substring(start, i));
        }

        return ((String[]) list.toArray(new String[list.size()]));
    }

    public static String[] split(String str, char separatorChar) {
        if (str == null) {
            return null;
        }

        int length = str.length();

        if (length == 0) {
            return new String[0];
        }

        List list = new ArrayList();
        int i = 0;
        int start = 0;
        boolean match = false;

        while (i < length) {
            if (str.charAt(i) == separatorChar) {
                if (match) {
                    list.add(str.substring(start, i));
                    match = false;
                }

                start = ++i;
            } else {
                match = true;
                ++i;
            }
        }
        if (match) {
            list.add(str.substring(start, i));
        }

        return ((String[]) list.toArray(new String[list.size()]));
    }

    public static String[] split(String str, String separatorChars) {
        return split(str, separatorChars, -1);
    }

    public static String join(Object[] array) {
        return join(array, null);
    }

    public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }

        int arraySize = array.length;
        int bufSize = (arraySize == 0) ? 0 : (((array[0] == null) ? 16 : array[0].toString().length()) + 1) * arraySize;
        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = 0; i < arraySize; ++i) {
            if (i > 0) {
                buf.append(separator);
            }

            if (array[i] != null) {
                buf.append(array[i]);
            }
        }

        return buf.toString();
    }

    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }

        if (separator == null) {
            separator = "";
        }

        int arraySize = array.length;

        int bufSize = (arraySize == 0) ? 0 : arraySize
                * (((array[0] == null) ? 16 : array[0].toString().length()) + ((separator != null) ? separator.length()
                        : 0));

        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = 0; i < arraySize; ++i) {
            if ((separator != null) && (i > 0)) {
                buf.append(separator);
            }

            if (array[i] != null) {
                buf.append(array[i]);
            }
        }

        return buf.toString();
    }

    public static String join(Iterator iterator, char separator) {
        if (iterator == null) {
            return null;
        }

        StringBuffer buf = new StringBuffer(256);

        while (iterator.hasNext()) {
            Object obj = iterator.next();

            if (obj != null) {
                buf.append(obj);
            }

            if (iterator.hasNext()) {
                buf.append(separator);
            }
        }

        return buf.toString();
    }

    public static String join(Iterator iterator, String separator) {
        if (iterator == null) {
            return null;
        }

        StringBuffer buf = new StringBuffer(256);

        while (iterator.hasNext()) {
            Object obj = iterator.next();

            if (obj != null) {
                buf.append(obj);
            }

            if ((separator != null) && (iterator.hasNext())) {
                buf.append(separator);
            }
        }

        return buf.toString();
    }

    public static int indexOf(String str, char searchChar) {
        if ((str == null) || (str.length() == 0)) {
            return -1;
        }

        return str.indexOf(searchChar);
    }

    public static int indexOf(String str, char searchChar, int startPos) {
        if ((str == null) || (str.length() == 0)) {
            return -1;
        }

        return str.indexOf(searchChar, startPos);
    }

    public static int indexOf(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        return str.indexOf(searchStr);
    }

    public static int indexOf(String str, String searchStr, int startPos) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        if ((searchStr.length() == 0) && (startPos >= str.length())) {
            return str.length();
        }

        return str.indexOf(searchStr, startPos);
    }

    public static int indexOfAny(String str, char[] searchChars) {
        if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length == 0)) {
            return -1;
        }

        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);

            for (int j = 0; j < searchChars.length; ++j) {
                if (searchChars[j] == ch) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int indexOfAny(String str, String searchChars) {
        if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length() == 0)) {
            return -1;
        }

        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);

            for (int j = 0; j < searchChars.length(); ++j) {
                if (searchChars.charAt(j) == ch) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int indexOfAny(String str, String[] searchStrs) {
        if ((str == null) || (searchStrs == null)) {
            return -1;
        }

        int sz = searchStrs.length;

        int ret = 2147483647;

        int tmp = 0;

        for (int i = 0; i < sz; ++i) {
            String search = searchStrs[i];

            if (search == null) {
                continue;
            }

            tmp = str.indexOf(search);

            if (tmp == -1) {
                continue;
            }

            if (tmp < ret) {
                ret = tmp;
            }
        }

        return ((ret == 2147483647) ? -1 : ret);
    }

    public static int indexOfAnyBut(String str, String searchChars) {
        if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length() == 0)) {
            return -1;
        }

        for (int i = 0; i < str.length(); ++i) {
            if (searchChars.indexOf(str.charAt(i)) < 0) {
                return i;
            }
        }

        return -1;
    }

    public static int lastIndexOf(String str, char searchChar) {
        if ((str == null) || (str.length() == 0)) {
            return -1;
        }

        return str.lastIndexOf(searchChar);
    }

    public static int lastIndexOf(String str, char searchChar, int startPos) {
        if ((str == null) || (str.length() == 0)) {
            return -1;
        }

        return str.lastIndexOf(searchChar, startPos);
    }

    public static int lastIndexOf(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        return str.lastIndexOf(searchStr);
    }

    public static int lastIndexOf(String str, String searchStr, int startPos) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        return str.lastIndexOf(searchStr, startPos);
    }

    public static int lastIndexOfAny(String str, String[] searchStrs) {
        if ((str == null) || (searchStrs == null)) {
            return -1;
        }

        int searchStrsLength = searchStrs.length;
        int index = -1;
        int tmp = 0;

        for (int i = 0; i < searchStrsLength; ++i) {
            String search = searchStrs[i];

            if (search == null) {
                continue;
            }

            tmp = str.lastIndexOf(search);

            if (tmp > index) {
                index = tmp;
            }
        }

        return index;
    }

    public static boolean contains(String str, char searchChar) {
        if ((str == null) || (str.length() == 0)) {
            return false;
        }

        return (str.indexOf(searchChar) >= 0);
    }

    public static boolean contains(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return false;
        }

        return (str.indexOf(searchStr) >= 0);
    }

    public static boolean containsNone(String str, char[] invalid) {
        if ((str == null) || (invalid == null)) {
            return true;
        }

        int strSize = str.length();
        int validSize = invalid.length;

        for (int i = 0; i < strSize; ++i) {
            char ch = str.charAt(i);

            for (int j = 0; j < validSize; ++j) {
                if (invalid[j] == ch) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean containsNone(String str, String invalidChars) {
        if ((str == null) || (invalidChars == null)) {
            return true;
        }

        return containsNone(str, invalidChars.toCharArray());
    }

    public static int countMatches(String str, String subStr) {
        if ((str == null) || (str.length() == 0) || (subStr == null) || (subStr.length() == 0)) {
            return 0;
        }

        int count = 0;
        int index = 0;

        while ((index = str.indexOf(subStr, index)) != -1) {
            ++count;
            index += subStr.length();
        }

        return count;
    }

    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }

        if (start > str.length()) {
            return "";
        }

        return str.substring(start);
    }

    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        if (end < 0) {
            end = str.length() + end;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return "";
        }

        if (start < 0) {
            start = 0;
        }

        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }

        if (len < 0) {
            return "";
        }

        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }

        if (len < 0) {
            return "";
        }

        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);
    }

    public static String mid(String str, int pos, int len) {
        if (str == null) {
            return null;
        }

        if ((len < 0) || (pos > str.length())) {
            return "";
        }

        if (pos < 0) {
            pos = 0;
        }

        if (str.length() <= pos + len) {
            return str.substring(pos);
        }
        return str.substring(pos, pos + len);
    }

    public static String substringBefore(String str, String separator) {
        if ((str == null) || (separator == null) || (str.length() == 0)) {
            return str;
        }

        if (separator.length() == 0) {
            return "";
        }

        int pos = str.indexOf(separator);

        if (pos == -1) {
            return str;
        }

        return str.substring(0, pos);
    }

    public static String substringAfter(String str, String separator) {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }

        if (separator == null) {
            return "";
        }

        int pos = str.indexOf(separator);

        if (pos == -1) {
            return "";
        }

        return str.substring(pos + separator.length());
    }

    public static String substringBeforeLast(String str, String separator) {
        if ((str == null) || (separator == null) || (str.length() == 0) || (separator.length() == 0)) {
            return str;
        }

        int pos = str.lastIndexOf(separator);

        if (pos == -1) {
            return str;
        }

        return str.substring(0, pos);
    }

    public static String substringAfterLast(String str, String separator) {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }

        if ((separator == null) || (separator.length() == 0)) {
            return "";
        }

        int pos = str.lastIndexOf(separator);

        if ((pos == -1) || (pos == str.length() - separator.length())) {
            return "";
        }

        return str.substring(pos + separator.length());
    }

    public static String substringBetween(String str, String tag) {
        return substringBetween(str, tag, tag, 0);
    }

    public static String substringBetween(String str, String open, String close) {
        return substringBetween(str, open, close, 0);
    }

    public static String substringBetween(String str, String open, String close, int fromIndex) {
        if ((str == null) || (open == null) || (close == null)) {
            return null;
        }

        int start = str.indexOf(open, fromIndex);

        if (start != -1) {
            int end = str.indexOf(close, start + open.length());

            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }

        return null;
    }

    public static String deleteWhitespace(String str) {
        if (str == null) {
            return null;
        }

        int sz = str.length();
        StringBuffer buffer = new StringBuffer(sz);

        for (int i = 0; i < sz; ++i) {
            if (!(Character.isWhitespace(str.charAt(i)))) {
                buffer.append(str.charAt(i));
            }
        }

        return buffer.toString();
    }

    public static String replaceOnce(String text, String repl, String with) {
        return replace(text, repl, with, 1);
    }

    public static String replace(String text, String repl, String with) {
        return replace(text, repl, with, -1);
    }

    public static String replace(String text, String repl, String with, int max) {
        if ((text == null) || (repl == null) || (with == null) || (repl.length() == 0) || (max == 0)) {
            return text;
        }

        StringBuffer buf = new StringBuffer(text.length());
        int start = 0;
        int end = 0;

        while ((end = text.indexOf(repl, start)) != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();

            if (--max == 0) {
                break;
            }
        }

        buf.append(text.substring(start));
        return buf.toString();
    }

    public static String replaceChars(String str, char searchChar, char replaceChar) {
        if (str == null) {
            return null;
        }

        return str.replace(searchChar, replaceChar);
    }

    public static String replaceChars(String str, String searchChars, String replaceChars) {
        if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length() == 0)) {
            return str;
        }

        char[] chars = str.toCharArray();
        int len = chars.length;
        boolean modified = false;

        int i = 0;
        for (int isize = searchChars.length(); i < isize; ++i) {
            char searchChar = searchChars.charAt(i);

            if ((replaceChars == null) || (i >= replaceChars.length())) {
                int pos = 0;

                for (int j = 0; j < len; ++j) {
                    if (chars[j] != searchChar)
                        chars[(pos++)] = chars[j];
                    else {
                        modified = true;
                    }
                }

                len = pos;
            } else {
                for (int j = 0; j < len; ++j) {
                    if (chars[j] == searchChar) {
                        chars[j] = replaceChars.charAt(i);
                        modified = true;
                    }
                }
            }
        }

        if (!(modified)) {
            return str;
        }

        return new String(chars, 0, len);
    }

    public static String overlay(String str, String overlay, int start, int end) {
        if (str == null) {
            return null;
        }

        if (overlay == null) {
            overlay = "";
        }

        int len = str.length();

        if (start < 0) {
            start = 0;
        }

        if (start > len) {
            start = len;
        }

        if (end < 0) {
            end = 0;
        }

        if (end > len) {
            end = len;
        }

        if (start > end) {
            int temp = start;

            start = end;
            end = temp;
        }

        return (len + start - end + overlay.length() + 1) + str.substring(0, start) + overlay + str.substring(end);
    }

    public static String chomp(String str) {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }

        if (str.length() == 1) {
            char ch = str.charAt(0);

            if ((ch == '\r') || (ch == '\n')) {
                return "";
            }
            return str;
        }

        int lastIdx = str.length() - 1;
        char last = str.charAt(lastIdx);

        if (last == '\n') {
            if (str.charAt(lastIdx - 1) == '\r')
                --lastIdx;
        } else if (last != '\r') {
            ++lastIdx;
        }

        return str.substring(0, lastIdx);
    }

    public static String chomp(String str, String separator) {
        if ((str == null) || (str.length() == 0) || (separator == null)) {
            return str;
        }

        if (str.endsWith(separator)) {
            return str.substring(0, str.length() - separator.length());
        }

        return str;
    }

    public static String chop(String str) {
        if (str == null) {
            return null;
        }

        int strLen = str.length();

        if (strLen < 2) {
            return "";
        }

        int lastIdx = strLen - 1;
        String ret = str.substring(0, lastIdx);
        char last = str.charAt(lastIdx);

        if ((last == '\n') && (ret.charAt(lastIdx - 1) == '\r')) {
            return ret.substring(0, lastIdx - 1);
        }

        return ret;
    }

    public static String alignLeft(String str, int size) {
        return alignLeft(str, size, ' ');
    }

    public static String alignLeft(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }

        int pads = size - str.length();

        if (pads <= 0) {
            return str;
        }

        return alignLeft(str, size, String.valueOf(padChar));
    }

    public static String alignLeft(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }

        if ((padStr == null) || (padStr.length() == 0)) {
            padStr = " ";
        }

        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;

        if (pads <= 0) {
            return str;
        }

        if (pads == padLen)
            return str.concat(padStr);
        if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        }
        char[] padding = new char[pads];
        char[] padChars = padStr.toCharArray();

        for (int i = 0; i < pads; ++i) {
            padding[i] = padChars[(i % padLen)];
        }

        return str.concat(new String(padding));
    }

    public static String alignRight(String str, int size) {
        return alignRight(str, size, ' ');
    }

    public static String alignRight(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }

        int pads = size - str.length();

        if (pads <= 0) {
            return str;
        }

        return alignRight(str, size, String.valueOf(padChar));
    }

    public static String alignRight(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }

        if ((padStr == null) || (padStr.length() == 0)) {
            padStr = " ";
        }

        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;

        if (pads <= 0) {
            return str;
        }

        if (pads == padLen)
            return padStr.concat(str);
        if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        }
        char[] padding = new char[pads];
        char[] padChars = padStr.toCharArray();

        for (int i = 0; i < pads; ++i) {
            padding[i] = padChars[(i % padLen)];
        }

        return new String(padding).concat(str);
    }

    public static String center(String str, int size) {
        return center(str, size, ' ');
    }

    public static String center(String str, int size, char padChar) {
        if ((str == null) || (size <= 0)) {
            return str;
        }

        int strLen = str.length();
        int pads = size - strLen;

        if (pads <= 0) {
            return str;
        }

        str = alignRight(str, strLen + pads / 2, padChar);
        str = alignLeft(str, size, padChar);
        return str;
    }

    public static String center(String str, int size, String padStr) {
        if ((str == null) || (size <= 0)) {
            return str;
        }

        if ((padStr == null) || (padStr.length() == 0)) {
            padStr = " ";
        }

        int strLen = str.length();
        int pads = size - strLen;

        if (pads <= 0) {
            return str;
        }

        str = alignRight(str, strLen + pads / 2, padStr);
        str = alignLeft(str, size, padStr);
        return str;
    }

    public static String reverse(String str) {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }

        return new StringBuffer(str).reverse().toString();
    }

    public static String abbreviate(String str, int maxWidth) {
        return abbreviate(str, 0, maxWidth);
    }

    public static String abbreviate(String str, int offset, int maxWidth) {
        if (str == null) {
            return null;
        }

        if (maxWidth < 4) {
            maxWidth = 4;
        }

        if (str.length() <= maxWidth) {
            return str;
        }

        if (offset > str.length()) {
            offset = str.length();
        }

        if (str.length() - offset < maxWidth - 3) {
            offset = str.length() - (maxWidth - 3);
        }

        if (offset <= 4) {
            return str.substring(0, maxWidth - 3) + "...";
        }

        if (maxWidth < 7) {
            maxWidth = 7;
        }

        if (offset + maxWidth - 3 < str.length()) {
            return "..." + abbreviate(str.substring(offset), maxWidth - 3);
        }

        return "..." + str.substring(str.length() - (maxWidth - 3));
    }

    private static int min(int a, int b, int c) {
        if (b < a) {
            a = b;
        }
        if (c < a) {
            a = c;
        }
        return a;
    }

}
