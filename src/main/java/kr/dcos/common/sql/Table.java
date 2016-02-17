package kr.dcos.common.sql;

import java.sql.ResultSet;
import java.util.List;

public interface Table {
	/**
	 * row를 table에 추가한다.
	 * row가 이미 존재하면 추가하지 않는다. 
	 * 이미 존재한다는 것은 row 의 내용이 아니라 row object자체이다.
	 * @param row
	 */
	public void addRow(Row row);
	/**
	 * find index by column name if not found return -1
	 * @param columnName
	 * @return
	 * @throws JdbcTableException 
	 */
	public int indexOf(String columnName) throws JdbcTableException;
	/**
	 * index에 해당하는 column name을 리턴한다
	 * @param index
	 * @return
	 */
	public String columnName(int index) throws JdbcTableException;
	public boolean isEmpty() ;
	
	/**
	 * 새로운 row를 만들어서 리턴한다.
	 * ex) Row row = table.newRow();
	 * @return
	 */
	public Row newRow();
	
	public void addColumn(Column column) throws JdbcTableException;
	
	public Object getValue(int rowIndex, int colIndex) throws JdbcTableException;
	/**
	 * rowIndex,colIndex에 해당하는 value를 문자열로 리턴한다.
	 * null인경우 "" 를 리턴한다
	 * @param rowIndex
	 * @param colIndex
	 * @return
	 * @throws JdbcTableException
	 */
	public String getString(int rowIndex, int colIndex) throws JdbcTableException;
	/**
	 * Jdbc ResultSet으로 table을 구성한다.
	 * Table table = new SqlResultTable();
	 * table.setUpWithResultSet(rs);
	 */
	public void setUpWithResultSet(ResultSet rs)throws JdbcTableException;
	/**
	 * 테이블의 헤더 타이틀들을 배열형태로 리턴한다
	 * @return
	 */
	public String[] getHeaders();
	/**
	 * Row 리스트를 리턴한다.
	 * @return
	 */
	public List<Row> getRows();
	/**
	 * row(행) 갯수를 리턴한다
	 * @return
	 */
	public int getRowSize();
	/**
	 * column(열) 갯수를 리턴한다.
	 * @return
	 */
	public int getColumnSize();
	
	@Override
	public String toString();
	
	

}
