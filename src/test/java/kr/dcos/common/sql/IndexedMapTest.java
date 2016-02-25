package kr.dcos.common.sql;

import static org.junit.Assert.*;
import kr.dcos.common.sql.Column.DataType;
import kr.dcos.common.sql.utils.IndexedMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IndexedMapTest {
	
	private static Logger logger = LoggerFactory
			.getLogger(IndexedMapTest.class);
	


	private IndexedMap<String, Column> indexedMap;
	@Before
	public void setUp() throws Exception {
		indexedMap = new IndexedMap<String, Column>();
		indexedMap.put("a", new Column("name1",DataType.String));
		indexedMap.put("b", new Column("name2",DataType.String));
		indexedMap.put("c", new Column("name3",DataType.String));
		indexedMap.put("d", new Column("name4",DataType.String));
		indexedMap.add("e", new Column("name5",DataType.Integer));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAdd() {
		
		Column c = indexedMap.getByIndex(1);
		logger.debug(c.toString());
		assertNotNull(c);
		assertEquals(c.getName().toUpperCase(), "name2".toUpperCase());
		
	}

	@Test
	public void testGetByIndex() {
		for(int i=0;i<indexedMap.size();i++){
			Column c = indexedMap.getByIndex(i);
			assertNotNull(c);
		}
	}

}
