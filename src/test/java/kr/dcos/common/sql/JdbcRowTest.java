package kr.dcos.common.sql;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import kr.dcos.common.sql.Column.DataType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JdbcRowTest {
	JdbcTable table=null;
	JdbcRow row = null;
	@Before
	public void setUp() throws Exception {
		 table = new JdbcTable();
		 table.addColumn(new Column(DataType.String,"c1"));
		 table.addColumn(new Column(DataType.String,"c2"));
		 table.addColumn(new Column(DataType.String,"c3"));
		 row = new JdbcRow(table);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void test() throws JdbcTableException {
		
		row.setByIndex(0, "1");
		row.setByIndex(1, "2");
		row.setByIndex(2, "3");
		assertEquals(row.getByIndex(0),"1");
		assertEquals(row.getColumnSize(),3);
		row.clear();
		assertEquals(row.getColumnSize(),3);
		assertEquals(row.getByIndex(2),null);
		
	}
	
	@Test
	public void test2() throws JdbcTableException {
		row.setByIndex(0, "aaaa");
		row.setByIndex(1, "bbbb");
		row.setByIndex(2, "cccc");
		
		Map<String,Object> columns = row.getColumns();
		assertEquals(columns.size(),3);
		assertEquals(columns.get("c1"),"aaaa");
		
		//List<Row> rows = table.getRows();
	}

}
