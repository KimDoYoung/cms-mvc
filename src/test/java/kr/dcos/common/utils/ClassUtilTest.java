package kr.dcos.common.utils;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kr.dcos.common.sql.JdbcDataType;
import kr.dcos.common.sql.SqlExecuterTest;

import kr.dcos.common.sql.SqlParam;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtilTest {
	
	private static Logger logger = LoggerFactory.getLogger(ClassUtilTest.class);
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsPrimitive() {
		Object s="123";
		assertTrue(ClassUtil.isPrimitive(s));
	}
	@Test
	public void testHasMethod() {
		SqlParam param = new SqlParam();
		boolean b = ClassUtil.hasMethod(param,"get");
		assertTrue(b);
	}
	public class TypeName{
		public String string1;
		public Integer integer1;
		public Date date1;
		public Double double1;
	}
	@Test
	public void testExtractValue() {
		
		Object o = null;
		SqlParam param = new SqlParam();
		param.put("key1", "ABC");
		param.put("key2", 123);
		
		PageAttribute pa = new PageAttribute(50);
		pa.setPageSize(10);
		o = ClassUtil.getValueFromClass(pa, "pageSize");
		
		assertNotNull(o);
		assertEquals(o.toString(),"10");
		
		o = ClassUtil.getValueFromClass(pa, "no_field");
		assertNull(o);
		
		String t = ClassUtil.getTypeFromClass(pa,"pageSize");
		logger.debug(t);
		
		TypeName tn = new TypeName();
		logger.debug(ClassUtil.getTypeFromClass(tn,"string1"));
		logger.debug(ClassUtil.getTypeFromClass(tn,"integer1"));
		logger.debug(ClassUtil.getTypeFromClass(tn,"date1"));
		logger.debug(ClassUtil.getTypeFromClass(tn,"double1"));
	}

	@Test
	public void testTypeOfField() {
		Book book = new Book();
		assertEquals(JdbcDataType.fromString(ClassUtil.getTypeFromClass(book, "title")),JdbcDataType.String);
		assertEquals(JdbcDataType.fromString(ClassUtil.getTypeFromClass(book, "birth")),JdbcDataType.Date);
		assertEquals(JdbcDataType.fromString(ClassUtil.getTypeFromClass(book, "cost")),JdbcDataType.Double);
		assertEquals(JdbcDataType.fromString(ClassUtil.getTypeFromClass(book, "age")),JdbcDataType.Integer);
		
	}
}
