package kr.dcos.common.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class StrUtils {
	public static final String EMPTY_STRING = "";
	public static enum TIMEPOINT { START_TIME, END_TIME };
	
	public static final String BASIC_DATE_FORMAT = "yyyy-MM-dd HH:mm:sss"; 
			
	/**
	 * 스트링 리스트를 구분자로 연결된 문자열로 만들어서 리턴한다
	 * @param list String list 합칠려는 문자들의 리스트
	 * @param delimitor 구분자
	 * @return 구분자로 연결된 문자열
	 */
	public static String listToString(List<String> list,char delimitor){
		String[] array = list.toArray(new String[list.size()]);
		return arrayToString(array,delimitor);
	}
	/**
	 * 문자열배열을 한개의 문자열로 결합한다. 결합시 delimitor를 붙여서 결합한다
	 * @param strArray
	 * @param delimitor
	 * @return
	 */
	public static String arrayToString(String[] strArray,char delimitor){
		StringBuilder sb = new StringBuilder();
		for(String s : strArray){
			sb.append(s);
			sb.append(delimitor);
		}
		String s = sb.toString();
		if(s.length()>0){
			return s.substring(0,s.length()-1);
		}
		return "";		
	}

	/**
	 * 문자열 orgString이 postfixString으로 끝난다면 postfixString을 제거한 문자열을 리턴한다
	 * 주로 폴더명의 뒤에 "/" 를 붙이거나 뗄때  사용한다.  
	 * s = "abc , def , soso," 
	 * s = removePostfix(s,",");
	 * s = "abc, def, soso"
	 * @param orgString
	 * @param postfixString
	 * @return
	 */
	public static String removePostfix(String orgString, String postfixString) {
		if(postfixString == null || postfixString.length() < 1)return orgString;
		if(orgString==null || orgString.isEmpty()) return orgString;
		
		if(orgString.endsWith(postfixString)){
			return orgString.substring(0,orgString.length()-postfixString.length());
		}
		return orgString;
	}
	public static String addPostfix(String orgString,String postfixString){
		if(postfixString == null || postfixString.length() < 1)return orgString;
		if(orgString==null || orgString.isEmpty()) return postfixString;
		
		if(orgString.endsWith(postfixString)){
			return orgString;
		}
		
		return orgString + postfixString;
		
	}
	/**
	 * 문자열 str이 null인지 또는 공백문자들로만 채워진 문자들인지 체크한다.
	 * 
	 */
	public static boolean isNullOrEmpty(String str) {
        if ( str == null || str.trim().equals("")){
        	return true;
        }
        return false;
	}
	/**
	 * 문자열이 integer로 변환될 수 있는지 체크한다
	 * integer로 변환가능하면 true를 불가시 false리턴
	 * null이거나 공백문자일경우에 false를 리턴한다.
	 * @param s
	 * @return
	 */
	public static boolean IsInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static String toCharset(String s, String fromCharset, String toCharset) {
		if(isNullOrEmpty(s))return s;
		try {
			return new String(s.getBytes(fromCharset), toCharset);
		} catch (UnsupportedEncodingException e) {
			return s;
		}
		
	}
	/**
	 * utf-8로 된 문자열 s를 toCharset으로 변화시킨다.
	 * @param s
	 * @param toCharset
	 * @return
	 */
	public static String toCharset(String s, String toCharset) {
		return toCharset(s,"UTF-8",toCharset);
	}
	/**
	 * 현재 시간을 기본dateformat을 사용하여 변환하여 리턴한다
	 * @return
	 */
	public static String NowToString() {
		SimpleDateFormat format = new SimpleDateFormat(BASIC_DATE_FORMAT);
		return format.format(Calendar.getInstance().getTime());
	}
	/**
	 * 기본 dateformat을 사용하여 문자열로 된 날짜를 Date형으로 변환한다
	 * @param time
	 * @return
	 */
	public static Date StringToDate(String stringDate,TIMEPOINT timePoint) {
		
		if(isNullOrEmpty(stringDate)) return null;

		String s = stringDate;
		//2010-10-10 과 같이 날짜 부분만 있다면 
		if(stringDate.length()==10){
			if(timePoint==TIMEPOINT.END_TIME){
				s = stringDate + " " + "23:59:59";
			}else{
				s = stringDate + " " + "00:00:00";
			}
		}
		SimpleDateFormat format = new SimpleDateFormat(BASIC_DATE_FORMAT);
		try {
			return format.parse(s);
		} catch (ParseException e) {
			return null;
		}
	}
	public static Date StringToDate(String stringDate) {
		return StrUtils.StringToDate(stringDate,TIMEPOINT.START_TIME);
	}
	/**
	 * 기본 dateformat을 사용하여 날짜형을 문자형으로 바꾸러 리턴한다
	 * @param d
	 * @return
	 */
	public static String DateToString(Date date) {
		if(date == null) return null;
		
		SimpleDateFormat format = new SimpleDateFormat(BASIC_DATE_FORMAT);
		return format.format(date);
	}
	public static String[] toArray(List<String> list) {
		return list.toArray(new String[list.size()]);
	}
	public static Object ifEmpty(Object value, Object defaultValue) {
		
		if(value instanceof String){
			if(isNullOrEmpty(value.toString())){
				return defaultValue;
			}else{
				return value;
			}
		}else if(value == null){
			return defaultValue;
		}
		return value; 
	}
	// Abc_def = abcDef와 같이 변경
	//
	/**
	 * Abc_def = abcDef와 같이 변경
	 * example: ETC_01 => etc01, COMMUNITY_ID = communityId로 변경
	 * @param name
	 * @return
	 */
	public static String propertyName(String name){
		String ss = name.toUpperCase();
		String[] tmp = ss.split("_");
		String property = "";
		for (String s : tmp) {
			property += s.substring(0,1).toUpperCase()+s.substring(1).toLowerCase();
		}
		return property.substring(0,1).toLowerCase()+property.substring(1);
	}
	/**
	 * 첫글자만 대문자로 한다
	 * @param columnName
	 * @return
	 */
	public static String firstUpper(String s) {
		if (isNullOrEmpty(s)) return s;
		return s.substring(0,1).toUpperCase() + s.substring(1);
	}
	/**
	 * yyyy_MM_dd_HH_mm_ss 형태로 리턴한다.
	 * @return
	 */
	public static String getTimeStringForBackup() {
		return getTimeStringForBackup("yyyy_MM_dd_HH_mm_SSS");
	}
	public static String getTimeStringForBackup(String format) {
		java.util.Date today = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(today);
	}

	/**
	 * jsonString을  List<ValueText>로 만든다. 
	 * @param jsonString
	 * @return
	 */
	public static List<ValueText> jsonStringToValueTextList(String jsonString) {
		Type listType = new TypeToken<ArrayList<ValueText>>() {}.getType();
		Gson gson = new Gson();
		return gson.fromJson(jsonString, listType);
	}
	/**
	 * jsonString 을 List<String>으로 만든다
	 * @param jsonString
	 * @return
	 */
	public static List<String> jsonStringToStringList(String jsonString) {
		Type listType = new TypeToken<ArrayList<String>>() {}.getType();
		Gson gson = new Gson();
		return gson.fromJson(jsonString, listType);
	}
	/**
	 * jsonString을 Map<String,String>으로 만든다.
	 * @param output
	 * @return
	 */
	public static Object jsonStringToMap(String jsonString) {
		List<ValueText> list = jsonStringToValueTextList(jsonString);
		Map<String,String> map = new HashMap<String,String>();
		for (ValueText valueText : list) {
			map.put(valueText.getValue(), valueText.getText());
		}
		return map;
	}
	/**
	 * 파일명에서 확장자를 뽑아서 리턴한다
	 * @param name
	 * @return
	 */
	public static String getFileExtension(String fileName) {
		String extension = "";
		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

		if (i > p) {
		    extension = fileName.substring(i+1);
		}
		return extension;
	}
	/**
	 * stringLength 만큼의 Random 문자열을 리턴한다.
	 * @param stringLength
	 * @return
	 */
	public static String randomString(int stringLength) {
		RandomString rs = new RandomString(stringLength);
		return rs.nextString();
	}
	
}
