package kr.dcos.common.sql.sqlpicker;

import static org.junit.Assert.*;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.dcos.common.sql.exception.SqlPickerException;
import kr.dcos.common.sql.sqlpicker.SqlPicker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlPickerTest {
	
	private static Logger logger = LoggerFactory.getLogger(SqlPickerTest.class);
	SqlPicker sqlPicker = null;

	@Before
	public void setUp() throws Exception {
		sqlPicker = new SqlPicker();
		sqlPicker.loadFromResource("/sqls.xml");
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 2개이상의 xml을 load한다. 같은 id가 있으면 exception
	 * @throws SqlPickerException
	 */
	@Test
	public void testSqlPicker() throws SqlPickerException {
		String xmlFileName = "/sqls.xml";
		File file = SqlPicker.getFileFromResourcePath(xmlFileName);
		SqlPicker sqlPicker = new SqlPicker(file.getAbsolutePath());
		int i = sqlPicker.size();
		assertTrue(sqlPicker.size()>0);
		
		//load another xml
		sqlPicker.loadFromResource("/sqls2.xml");
		int j = sqlPicker.size();
		assertTrue(j >= i);
	}
	/**
	 * aaa1 테스트 #name#가 포함된 것을 해석한다.
	 * parameter로 넘길 수 있는 것은 string,map,integer , double이다.
	 * @throws Exception
	 */
	@Test
	public void testAaa1() throws Exception {
		
		Integer number = 25;
		String sql = sqlPicker.sqlString("aaa1", number);
		logger.debug("sql:["+sql+"]");
		assertNotNull(sql);
		assertTrue(sql.indexOf(number.toString())>=0);
		
		String key = "Kim";
		sql = sqlPicker.sqlString("aaa1", key);
		logger.debug("aaa1:["+sql+"]");
		assertNotNull(sql);
		assertTrue(sql.indexOf(key)>=0);
		
		
	}
	/**
	 * 아무것도 parameter가 기술되어 있지 않을 때.
	 * select * from table ;
	 * @throws Exception
	 */
	@Test
	public void testAaa2() throws Exception {
		String sql = sqlPicker.sqlString("aaa2", null);
		assertNotNull(sql);
		
		sql = sqlPicker.sqlString("aaa2");
		assertNotNull(sql);
		System.out.println(sql);
		
	}
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAaa3() throws Exception {
		//
		SqlPicker sqlPicker = new SqlPicker();
		sqlPicker.loadFromResource("/sqls.xml");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name","kim do yung");
		map.put("age",23);
		String sql = sqlPicker.sqlString("bbb", map);
		System.out.println("sql:["+sql+"]");
		assertNotNull(sql);
	}
	/**
	 * Person class로 parameter를 넘겨서 sql문장을 구해본다
	 * @throws Exception
	 */
	@Test
	public void testSqlString3() throws Exception {
		SqlPicker sqlPicker = new SqlPicker();
		sqlPicker.loadFromResource("/sqls2.xml");

		Person person = new Person();
		person.setName("kimdoyoung");
		person.setAge(55);
		
		String sql = sqlPicker.sqlString("insert.person", person);
		System.out.println("sql:["+sql+"]");
		assertNotNull(sql);

	}
	/**
	 * sql 안에 < > 문자허용
	 * @throws Exception
	 */
	@Test
	public void testSqlString4() throws Exception {
		SqlPicker sqlPicker = new SqlPicker();
		sqlPicker.loadFromResource("/sqls.xml");

		Person person = new Person();
		person.setName("kimdoyoung111");
		person.setAge(55);
		
		String sql = sqlPicker.sqlString("ccc", person);
		System.out.println("sql:["+sql+"]");
		assertNotNull(sql);

	}
	@Test
	public void testSqlString5() throws Exception {
		SqlPicker sqlPicker = new SqlPicker();
		sqlPicker.loadFromResource("/sqls.xml");

		String sql = sqlPicker.sqlString("ddd", "Kim,25");
		System.out.println("sql:["+sql+"]");
		assertEquals(sql,"insert into table1(name,age) values ('Kim','25')");

	}	
	@Test
	public void testSqlString6() throws Exception {
		SqlPicker sqlPicker = new SqlPicker();
		sqlPicker.loadFromResource("/sqls.xml");

		String sql = sqlPicker.sqlString("eee", "Kim");
		System.out.println("sql:["+sql+"]");
		assertEquals(sql,"insert into table1(name,name) values ('Kim','Kim')");

	}	
	@Test
	public void testGetValueFromClass() throws Exception {
		SqlPicker sqlPicker = new SqlPicker();
		sqlPicker.loadFromResource("/sqls.xml");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("name","Kim");
		param.put("age", 99);
		String sql = sqlPicker.sqlString("aaa3",param);
		logger.debug(sql);
	}
	/**
	 * map으로 보내기와 POJO 객체로 보내기
	 * @throws Exception
	 */
	@Test
	public void testGetValueFromClass1() throws Exception {
		SqlPicker sqlPicker = new SqlPicker();
		sqlPicker.loadFromResource("/sqls.xml");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("name","Kim");
		//param.put("age", null);
		String sql = sqlPicker.sqlString("aaa4",param);
		logger.debug("--->["+sql+"]");
		
		Person person = new Person();
		person.setName("Kim");
		
		String sql2 = sqlPicker.sqlString("aaa4",person);
		logger.debug("--->["+sql2+"]");
		//두개의 결과가 같아야한다.
		assertEquals(sql,sql2);
	}
	@Test
	public void testMapParameter1() throws Exception {
		SqlPicker sqlPicker = new SqlPicker();
		sqlPicker.loadFromResource("/sqls.xml");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("name","Kim");
		param.put("age",26);
		param.put("score", 10.5);
		param.put("jobs",new String[] {"aaa","bbb","ccc"});
		List<String> list = new ArrayList<String>();
		list.add("111");
		list.add("222");
		list.add("333");
		param.put("goods", list);
		String sql1 = sqlPicker.sqlString("aaa5",param);
		logger.debug("===>["+sql1+"]");
	}
	@Test
	public void testPreparedStatement() throws Exception {
		SqlPicker sqlPicker = new SqlPicker();
		sqlPicker.loadFromResource("/sqls.xml");
		String sql1 = sqlPicker.getPreparedStatment("aaa0",null);
		assertEquals(sql1,"select * from table");
		
		sql1 = sqlPicker.getPreparedStatment("aaa1",null);
		assertEquals(sql1,"select * from table where name= ? ;");
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("desc", "we are the world");
		sql1 = sqlPicker.getPreparedStatment("p1",map);
		assertEquals(sql1,"insert into gamdok (kname,ename,sdesc,oname) value ( ? , ? ,'we are the world','abc')");
		logger.debug(sql1);
	}
}
