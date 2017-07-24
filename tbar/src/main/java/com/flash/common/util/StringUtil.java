/**
 * StringUtil.java Created on May 15, 2009
 * Copyright oooo3d
 * All right reserved.
 */
package com.flash.common.util;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author lynn
 * @Time 5:01:04 PM
 */
public class StringUtil {

    public static final String SPLIT_COMMA_STRING = "#%2C#";
    public static final String SPLIT_COMMA = ",";
    public static final String SPLIT_VERTICAL_STRING = "#%7C#";
    public static final String SPLIT_VERTICAL = "|";
    public static final String SPLIT_RETURNANDCHANGEROW = "\r\n";
    public static final String HTML_QUOT_STRING = "&quot;";
    public static final String HTML_QUOT = "\"";
    public static final String HTML_AMP_STRING = "&amp;";
    public static final String HTML_AMP = "&";
    public static final String HTML_LT_STRING = "&lt;";
    public static final String HTML_LT = "<";
    public static final String HTML_GT_STRING = "&gt;";
    public static final String HTML_GT = ">";

    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNull(Object str) {
        //return (str == null || str.toString().trim().equals("")) ? true : false;
        return (str == null || str.toString().trim().length() <= 0);
    }

    @SuppressWarnings("unchecked")
    public static String filterStringForCell(String input) {
        if (isNull(input)) return "";
        return input.replaceAll(SPLIT_RETURNANDCHANGEROW, " ").replaceAll(HTML_QUOT, "“");
    }

    /**
     * Object to String
     *
     * @param obj
     * @return
     */
    public static String getStr(Object obj) {

        return obj != null ? String.valueOf(obj) : "";
    }

    /**
     * 使用Map中的value替换Input中的Key
     *
     * @param input
     * @param m
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String filter(String input, Map m) {
        if (isNull(input)) return "";
        if (m == null)
            return input.replaceAll(SPLIT_COMMA_STRING, SPLIT_COMMA).replaceAll(SPLIT_VERTICAL_STRING, SPLIT_VERTICAL);
        Set st = m.keySet();
        Iterator<String> iter = st.iterator();
        while (iter.hasNext()) {
            String mk = iter.next();
            input = input.replaceAll(mk, (String) m.get(mk));
        }
        return input;
    }

    public static String filterHtml(String input) {
        if (!hasSpecialChars(input)) {
            return input;
        }
        StringBuffer filtered = new StringBuffer(input.length());
        char c;
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            switch (c) {
                case '<':
                    filtered.append("&lt;");
                    break;
                case '>':
                    filtered.append("&gt;");
                    break;
                case '"':
                    filtered.append("&quot;");
                    break;
                case '&':
                    filtered.append("&amp;");
                    break;
                default:
                    filtered.append(c);
            }
        }
        return filtered.toString();
    }

    @SuppressWarnings("unused")
    private static boolean hasSpecialChars(String input) {
        boolean flag = false;
        if (input != null && input.length() > 0) {
            char c;
            for (int i = 0; i < input.length(); i++) {
                c = input.charAt(i);
                switch (c) {
                    case '<':
                        flag = true;
                        break;
                    case '>':
                        flag = true;
                        break;
                    case '"':
                        flag = true;
                        break;
                    case '&':
                        flag = true;
                        break;
                }
            }
        }
        return flag;
    }

    /**
     * 判断两个字符是否相等
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean isEqual(String s1, String s2) {
        if (s1 == null && s2 == null) return true;
        if (s1 == null) return false;
        return (s1.compareTo(s2) == 0) ? true : false;
    }

    /**
     * 字符串是否全是数字
     *
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static boolean isNum(char ch) {
        return Character.isDigit(ch);
    }

    /**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
     *
     * @param c 需要判断的字符
     * @return 返回true, Ascill字符
     */
    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 字符串是否以数字结尾
     *
     * @param str
     * @return
     */
    public static boolean endWithNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]+$");
        Matcher haveNum = pattern.matcher(str);
        return haveNum.find();
    }

    /**
     * 将字符串中的表达式分割出来(表达式分隔符不能嵌套)
     *
     * @param str       字符串
     * @param startFlag 开始标记
     * @param endFlag   结束标记
     * @return 表达式列
     */
    public static Set<String> splitBySBrackets(String str, String startFlag, String endFlag) {
        str = str.trim();
        Set<String> names = new TreeSet<String>();
        int sj = str.indexOf(startFlag);
        int sk = str.indexOf(endFlag, sj);
        while ((sj > -1) && (sk > sj)) {
            String tn = str.substring(sj, ++sk);
            names.add(tn);
            str = str.substring(sk);
            sj = str.indexOf(startFlag);
            sk = str.indexOf(endFlag, sj);
        }
        return names;
    }

    /**
     * 获得首字母
     *
     * @param s
     * @return
     */
    public static String getInitial(String s) {
        return s.substring(0, 1);
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param s 需要得到长度的字符串
     * @return 得到的字符串长度
     */
    public static int Len(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
//		return s.getBytes().length;
    }

    /**
     * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
     *
     * @param origin 原始字符串
     * @param len    截取长度(一个汉字长度按2算的)
     * @param c      后缀
     * @return 返回的字符串
     */
    public static String substring(String origin, int len, String c) {
        if (origin == null || origin.equals("") || len < 1)
            return "";
        byte[] strByte = new byte[len];
        if (len > Len(origin)) {
            return origin + c;
        }
        try {
            System.arraycopy(origin.getBytes("GBK"), 0, strByte, 0, len);
            int count = 0;
            for (int i = 0; i < len; i++) {
                int value = (int) strByte[i];
                if (value < 0) {
                    count++;
                }
            }
            if (count % 2 != 0) {
                len = (len == 1) ? ++len : --len;
            }
            return new String(strByte, 0, len, "GBK") + c;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static int f(int x) {
        int c = 0;
        while (x != 0) {
            System.out.print(x + "||" + (x - 1) + " is");
            System.out.print(x + "->");
            x = x & (x - 1);
            System.out.println(x);
            c++;
        }
        return c;
    }

    public static long get() {
        long c = 0;
        char a = 0x48;
        char b = 0x52;

        c = b << 8 | a;
        return c;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String t1 = "abc123'dsda'dasda\"\"dsa'\r\ndasdad";
        //String t2 = "吴江综合办公室展板等制作费（清偿）";
        //SysLog.log
        int a = 8;
        //System.out.println(f(a)+"---->");
        System.out.println(get());
        //System.out.println(filterStringForCell(t1));

		/*System.out.println(substring(t1, 5, ""));
		System.out.println(substring(t2, 17, ""));
		System.out.println(substring(t2, 20, ""));*/

        String s = "176839703545251841,0|176839703545251842,0|176839703545251843,0";
        List<Long> list = StringUtil.parseString(s);
        System.out.println("--------------------------");
        for (Long l : list) {
            System.out.println(l);
        }


    }

    /**
     * 替换字符
     *
     * @param parentStr
     * @param ch        被替换字符
     * @param rep       代替字符
     * @return
     */
    public static String replaceStr(String parentStr, String ch, String rep) {
        int i = parentStr.indexOf(ch);
        StringBuffer sb = new StringBuffer();
        if (i == -1)
            return parentStr;
        sb.append(parentStr.substring(0, i) + rep);
        if (i + ch.length() < parentStr.length())
            sb.append(replaceStr(parentStr.substring(i + ch.length(), parentStr.length()), ch, rep));
        return sb.toString();
    }

    /**
     * @param arg 格式：176839703545251841,0|176839703545251841,0|176839703545251841,0
     * @return
     */
    public static List<Long> parseString(String arg) {
        List<Long> resultList = new ArrayList<Long>();
        String regex = "(([123456789]\\d*),[01])(\\|[123456789]\\d*,[01])*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(arg);
        if (m.matches()) {
            boolean b = m.find(0);
            while (b) {
                int nStart = m.end(1);
                long v = Long.valueOf(m.group(2));
                resultList.add(v);
                b = m.find(nStart);
            }
        }
        return resultList;
    }
    
    public static boolean isNotNullOrEmpty(String... args){
		boolean flag = true;
		for (String s : args){
	         if(s==null||"".equals(s)){
	        	 flag = false;
	         }
	    }
		return flag;
	}
}
