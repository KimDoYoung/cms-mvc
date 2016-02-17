package kr.dcos.common.sql;

import static org.junit.Assert.*;
import kr.dcos.common.sql.Column.DataType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ColumnDefinesTest {
	ColumnDefines columns;
	@Before
	public void setUp() throws Exception {
		columns = new ColumnDefines();
		columns.add(new Column("name",DataType.String));
		columns.add(new Column("age",DataType.Integer));
		columns.add(new Column("birth",DataType.Date));
		System.out.println(columns.toString());
		assertEquals(columns.size(),3);
		assertEquals(columns.getDataType("name"),DataType.String);
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void test1() {
		assertEquals(columns.headers().size(),columns.size());
		assertEquals(columns.get(1),columns.get("age"));
	}

}
