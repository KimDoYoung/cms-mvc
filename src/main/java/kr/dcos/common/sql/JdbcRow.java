package kr.dcos.common.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.dcos.common.sql.utils.IndexedMap;
import kr.dcos.common.utils.KeyValue;

public class JdbcRow extends IndexedMap<String, Object> implements Row {
	
	private static Logger logger = LoggerFactory.getLogger(JdbcRow.class);
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6623488624013087454L;
	private JdbcTable myTable ;
	
	public JdbcRow(JdbcTable myTable) {
		this.myTable = myTable; 
		//초기에 null로 column갯수만큼 채운다
		for(int i=0;i<this.myTable.getColumnSize();i++){
			super.put(this.myTable.columnName(i),null);
		}
	}
	
	private boolean isValidIndex(int columnIndex) {
		return columnIndex >=0 && columnIndex < myTable.getColumnSize();
	}
	@Override
	public void setByIndex(int index ,Object value) {
		if(!isValidIndex(index)){
			logger.error("column index is out of bound");		
		}else{
			super.setByIndex(index, value);
		}
	}
	@Override
	public void setByName(String columnName, Object value){
		int index = myTable.indexOf(columnName);
		if(index < 0){
			logger.error(columnName + " is not valid column name");
		}else{
			setByIndex(index,value);
		}
	}
	
	@Override
	public Object getByName(String columnName) {
		int index = myTable.indexOf(columnName);
		if(!isValidIndex(index)){
			logger.error(columnName + " is not valid column name");	
			return null;
		}else{
			return getByIndex(index);
		}
	}

	@Override
	public List<Object> getList(){
		List<Object> list = new ArrayList<Object>();
		for(int i=0;i< myTable.getColumnSize();i++){
			list.add(super.getByIndex(i));
		}
		return list;
	}
	@Override
	public int getColumnSize() {
		return size();
	}

	@Override  
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<myTable.getColumnSize();i++){
			sb.append((String)super.getByIndex(i)+" ");
		}
		return sb.toString();
	}

	@Override
	public Map<String,Object> getColumns() {
		Map<String,Object> map = new HashMap<String,Object>();
		for(int i=0;i<size();i++){
			String key = myTable.columnName(i);
			Object value = getByIndex(i);
			map.put(key,value);
		}
		return map;
	}
	/**
	 * 모든 column value를 null로 채운다
	 */
	@Override
	public void clear(){
		for(int index=0;index<size();index++){
		super.setByIndex(index, null);
		}
	}
	
	
	public String getString(String columnName) {
		Object object = get(columnName);
		return object.toString();
	}
	public String getString(int index) {
		Object object = get(index);
		if(object == null) return "";
		return object.toString();
	}

}