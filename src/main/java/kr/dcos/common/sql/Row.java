package kr.dcos.common.sql;

import java.util.List;
import java.util.Map;


public interface Row {
	
	public int getColumnSize();
	/**
	 * row의 value를 List형태로 리턴한다
	 * @return value값으로 이루어진 List
	 */
	public List<Object> getList() ;
	/**
	 * 키가 컬럼명인 map을 리턴한다.
	 * @return Map
	 */
	public Map<String,Object> getColumns();
	/**
	 * colIndex로 값을 찾아서 리턴한다.
	 * @param colIndex
	 * @return
	 */
	public Object getByIndex(int colIndex);
	/**
	 * 컬럼명으로 값을 찾아서 리턴한다.
	 * @param name
	 * @return
	 */
	public Object getByName(String name);
	/**
	 * 
	 * @param i
	 * @param value
	 */
	public void setByIndex(int i, Object value);
	public void setByName(String columnName, Object value);
}
