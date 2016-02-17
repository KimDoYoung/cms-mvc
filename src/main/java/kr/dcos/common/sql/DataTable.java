package kr.dcos.common.sql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DataTable implements Table {

	private ColumnDefines columns;
	private List<Row> rowList;

	public DataTable() {
		rowList = new ArrayList<Row>();
		columns = new ColumnDefines();
	}

	public void addRow(Row row) {
		if (row != null) {
			rowList.add(row);
		}
	}

	public List<Row> getRows() {
		return rowList;
	}

	public void addRows(DataRow[] rows) {
		if (rows != null) {
			for (int i = 0; i < rows.length; i++) {
				addRow(rows[i]);
			}
		}
	}

	public void setRows(DataRow[] rows) {
		addRows(rows);
	}

	public Row getRow(int i) {
		return rowList.get(i);
	}

	public void setRow(int i, DataRow row) {
		rowList.set(i, row);
	}



	public Iterator<Row> iterator() {
		return rowList.iterator();
	}

	/**
	 * headerName에 해당하는 index 를 찾아서 리턴한다. 찾지 못하면 -1을 리턴한다.
	 * 
	 * @param columnName
	 * @return
	 */
	public int indexOf(String columnName) {
		if (columnName == null)
			return -1;
		String name = columnName;
		for (int i = 0; i < columns.size(); i++) {
			Column column = columns.get(i);
			if (column.getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * row 갯수가 0 이면 empty를 의미한다, 이 경우 true를 리턴 row 갯수가 1개 이상이면 false를 리턴한다.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return rowList.size() == 0;
	}

	/**
	 * 현재 row의 갯수를 리턴한다.
	 * 
	 * @return
	 */
	public int getRowSize() {
		return rowList.size();
	}

	/**
	 * column의 갯수를 리턴한다.
	 * 
	 * @return
	 */
	@Override
	public int getColumnSize() {
		return columns.size();
	}

	/**
	 * 새로운 row를 생성해서 리턴한다.
	 * 
	 * @return
	 */
	public DataRow newRow() {
		DataRow row = new DataRow(this, getColumnSize());
		rowList.add(row);
		return row;
	}


	@Override
	public Object getValue(int rowIndex, int colIndex)
			throws JdbcTableException {
		if (rowIndex >= 0 && rowIndex < getRowSize() && colIndex >= 0
				&& colIndex < getColumnSize()) {
			Row row = rowList.get(rowIndex);
			return row.getByIndex(colIndex);
		} else {
			throw new JdbcTableException("index out of range");
		}
	}

	@Override
	public String columnName(int index) {
		return columns.getName(index);
	}

	@Override
	public void addColumn(Column column) throws JdbcTableException {
		columns.add(column);
	}

	@Override
	public String getString(int rowIndex, int colIndex)
			throws JdbcTableException {
		Object o = getValue(rowIndex, colIndex);
		if (o == null)
			return "";
		else
			return o.toString();
	}

	@Override
	public void setUpWithResultSet(ResultSet rs) throws JdbcTableException {
		throw new NotImplementedException();
	}

	@Override
	public String[] getHeaders() {
		return columns.headers().toArray(new String[0]);
	}

	
	@Override
	public String toString() {

		if (rowList.size() == 0)
			return "DataTable is empty";

		StringBuffer sb = new StringBuffer();
		for (Row r : rowList) {
			sb.append(r.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
}