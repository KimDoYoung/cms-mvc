package kr.dcos.common.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataRow implements Row {
	
	private static Logger logger = LoggerFactory.getLogger(DataRow.class);
	

	private List<Object> valueList;
	private DataTable myTable ;
	
	public DataRow(DataTable myTable,int columnSize){
		this.myTable = myTable; 
		valueList = new ArrayList<Object>();
		for(int i=0;i<columnSize;i++){
			valueList.add(null);
		}
	}
	public DataRow(DataTable myTable) {
		this(myTable,0);
	}
	/**
	 * row의 column에 추가한다.
	 * @param object
	 */
	public void add(Object object) {
		valueList.add(object);
	}
	public void set(int index ,Object value) {
		valueList.set(index, value);
	}
	public void set(String columnName, Object value) throws JdbcTableException{
		int index = myTable.indexOf(columnName);
		set(index,value);
	}
	@Override  
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (Object o : valueList) {
			sb.append(o.toString()).append(", ");
		}
		if(sb.length() > 2){
			sb.setLength(sb.length()-2);
		}
		return sb.toString();
	}
	@Override
	public Object getByName(String columnName) {
		int index = myTable.indexOf(columnName.toUpperCase());
		if(index >=0 && index < valueList.size()){
			return valueList.get(index);
		}else{
			logger.error(columnName + " is not valid column name");
			return null;
		}
	}
	@Override
	public Object getByIndex(int columnIndex) {
		int index = columnIndex;
		if(index >=0 && index < valueList.size()){
			return valueList.get(index);
		}else{
			logger.error("column index is out of range");
			return null;
		}
	}
	

	@Override
	public List<Object> getList() {
		return valueList;
	}
	@Override
	public int getColumnSize() {
		return valueList.size();
	}
	@Override
	public Map<String, Object> getColumns() {
		Map<String,Object> map = new HashMap<String,Object>();
		for(int i=0;i<getColumnSize();i++){
			map.put(myTable.columnName(i),getByIndex(i));	
		}
		return map;
		
	}
	@Override
	public void setByIndex(int index, Object value) {
		set(index,value);
		
	}
	@Override
	public void setByName(String columnName, Object value) {
		int index = myTable.indexOf(columnName);
		set(index,value);
	}

	public String getString(String columnName) throws JdbcTableException {
		Object object = getByName(columnName);
		return object.toString();
	}
	public String getString(int index) throws JdbcTableException {
		Object object = getByIndex(index);
		if(object == null) return "";
		return object.toString();
	}
}