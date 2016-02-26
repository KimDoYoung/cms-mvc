package kr.dcos.common.sql;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.dcos.common.sql.Column.DataType;
import kr.dcos.common.sql.utils.IndexedMap;

/**
 * Column들을 가지고 있다.
 * sql result 로 리턴되는 결과에 대한 각 column들의 정의를 가지고 있다.
 * @author Kim Do Young
 *
 */
public class ColumnDefines extends IndexedMap<String,Column> {
	
	private static Logger logger = LoggerFactory.getLogger(ColumnDefines.class);
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -340711596839165950L;

	public ColumnDefines(){
	}

	public void add(Column column){
		super.put(column.getName(),column);
	}

	public Column get(int index)  {
		return super.getByIndex(index);
	}

	public Column get(String columnName)  {
		return super.get(columnName);
	}
	
	public String getName(int index)  {
		return super.get(index).getName();
	}

	public int getIndex(String columnName) {
		for(int i=0;i< super.size();i++){
			Column c = super.getByIndex(i);
			if(c.getName().equals(columnName)){
				return i;
			}
		}
		logger.error(columnName + " is not found");
		return -1;
		
	}

	/**
	 * column의 타이틀(헤더)문자열을 배열 형태로 리턴한다
	 * @return
	 * @throws JdbcTableException 
	 */
	public List<String> headers()  {
		List<String> titleList = new ArrayList<String>();
		for(int i=0;i<size();i++){
			titleList.add(getByIndex(i).getName());
		}
		return titleList;
	}

	public DataType getDataType(String columnName) {
		Column c = get(columnName);
		if(c != null){
			return c.getDataType();
		}
		return DataType.undefined;
	}
	@Override 
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<size();i++){
			Column c = get(i);
			sb.append(c.toString() + ",");
		}
		if(sb.toString().endsWith(",")){
			return sb.toString().substring(0,sb.toString().length()-1);
		}
		return sb.toString();
		
	}
	
}
