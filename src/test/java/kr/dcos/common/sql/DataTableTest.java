package kr.dcos.common.sql;

import static org.junit.Assert.*;
import kr.dcos.common.sql.Column.DataType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataTableTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws JdbcTableException {
		//table 생성
		DataTable table = new DataTable();
		assertTrue(table.isEmpty());

		//header 생성 추가
		Column dh = new Column(DataType.String, "name");
		table.addColumn(dh);
		assertEquals(table.getColumnSize(),1);

		dh = new Column("Age",DataType.Integer);
		table.addColumn(dh);
		assertEquals(table.getColumnSize(),2);
		
		//새로운 row
		Row row = table.newRow();
		row.setByName("name", "kim");
		assertTrue(!table.isEmpty());
		assertEquals(table.getRowSize(),1);
		
		row = table.newRow();
		row.setByIndex(0, "Do");
		assertEquals(table.getRowSize(),2);
		
		
	}

}
