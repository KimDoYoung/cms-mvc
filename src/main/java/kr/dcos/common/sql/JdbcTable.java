package kr.dcos.common.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.dcos.common.sql.Column.DataType;
import kr.dcos.common.sql.utils.ValueConverter;
import kr.dcos.common.sql.utils.ValueConverterFactory;

public class JdbcTable implements Table{
	private List<Row> rowList;
	private ColumnDefines columns;

	public JdbcTable() {
		rowList = new ArrayList<Row>();
		columns = new ColumnDefines();

	}
	public void setUpWithResultSet(ResultSet rs) throws JdbcTableException{
		columns.clear();
		setupColumnsWithResultSet(rs);
		setupValueWithResultSet(rs);
	}


	private void setupValueWithResultSet(ResultSet rs) throws JdbcTableException {
		
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
			while(rs.next()){
				JdbcRow row = (JdbcRow)newRow();
				for(int i=1; i<=columns.size();i++){
					ValueConverter vc  = ValueConverterFactory.getConverter(rsmd, i);
					row.setByIndex(i-1,vc.getValue(rs, i));
				}	
			}
		} catch (SQLException e) {
			throw new JdbcTableException(e.getMessage());
		} 

		
	}
	/**
	 * jdbc ResultSet으로 table의 Column 정의를 다시 만든다
	 * @param rs
	 * @throws JdbcTableException
	 */
	private void setupColumnsWithResultSet(ResultSet rs) throws JdbcTableException {
		
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i=1;i<=rsmd.getColumnCount();i++){
				String name = rsmd.getColumnLabel(i);
				ValueConverter vc  = ValueConverterFactory.getConverter(rsmd, i);
				DataType dataType = vc.getType();
				Column column = new Column(name,dataType);
				addColumn(column);
			}
		} catch (SQLException e) {
			throw new JdbcTableException(e.getMessage());
		}
	}
	@Override
	public void addRow(Row row) {
		if(rowList.indexOf(row) < 0){
			rowList.add(row);
		}
	}


	@Override
	public int indexOf(String columnName)  {
		return columns.getIndex(columnName);
	}
	@Override
	public String columnName(int index)  {
		return columns.get(index).getName();
	}
	@Override
	public boolean isEmpty() {
		return (rowList.size() == 0);
	}

	@Override
	public int getRowSize() {
		return rowList.size();
	}

	@Override
	public int getColumnSize() {
		return columns.size();
	}

	@Override
	public Row newRow() {
		Row row = new JdbcRow(this);
		rowList.add(row);
		return row;
	}

	@Override
	public Object getValue(int rowIndex, int colIndex)
			throws JdbcTableException {
		if(rowIndex >= 0 && rowIndex < getRowSize() && colIndex >= 0 && colIndex < getColumnSize()){
			Row row = rowList.get(rowIndex);
			return row.getByIndex(colIndex);
		}else{
			throw new JdbcTableException("index out of range");
		}
	}
	
	@Override
	public String getString(int rowIndex, int colIndex)
			throws JdbcTableException {
		Object value = getValue(rowIndex,colIndex);
		if(value == null) {
			return "";
		}else{
			return value.toString();
		}
	}
	@Override
	public void addColumn(Column column)  {
		columns.add(column);
		
	}
	@Override
	public String[] getHeaders() {
		return columns.headers().toArray(new String[0]);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String[] headers = getHeaders();
		for (String h : headers) {
			sb.append(h).append("\t");
		}
		sb.append("\n");
		for (Row row : rowList) {
			for(int i=0;i< getColumnSize();i++){
				sb.append(row.getByIndex(i));
				sb.append("\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public List<Row> getRows() {
		return rowList;
	}



}
