package kr.dcos.common.sql;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import kr.dcos.common.sql.Column.DataType;
import kr.dcos.common.sql.utils.ValueConverter;
import kr.dcos.common.sql.utils.ValueConverterFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcTableTest {
	
	private static Logger logger = LoggerFactory
			.getLogger(JdbcTableTest.class);
	

	private Connection conn ;
	@Before
	public void setUp() throws Exception {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://220.76.203.236:1433;DatabaseName=DVD_DB";
		String id = "kalpadb";
		String pw = "kalpadb987";
		conn = getConnection(driverName,url,id,pw);
		assertNotNull(conn);
	}

	@After
	public void tearDown() throws Exception {
		conn.close();
	}

	private static Connection getConnection(String driverName,String url, String id, String password){
		Connection connection = null;
		try{
			Class.forName(driverName);		
			connection = DriverManager.getConnection(url, id, password);
		}catch(Exception e){
			return null;
		}
		return connection;
	}
	
	@Test
	public void test() throws SQLException, JdbcTableException {
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select top 100 * from movie ";
		
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		
		Table table = new JdbcTable();
		
		ResultSetMetaData rsmd = rs.getMetaData();
		//
		// Create columns
		//
		for(int i=1;i<=rsmd.getColumnCount();i++){
			String name = rsmd.getColumnLabel(i);
			ValueConverter vc  = ValueConverterFactory.getConverter(rsmd, i);
			//vc.getValue(rs, i);
			DataType dataType = vc.getType();
			Column column = new Column(name,dataType);
			table.addColumn(column);
			
//			
//			 String classname = rsmd.getColumnClassName(i);
//			  String dbTypeName = rsmd.getColumnTypeName(i);
//			  int precision = rsmd.getPrecision(i);
//			  int scale = rsmd.getScale(i);
//			  int sqlType = rsmd.getColumnType(i);
//			  logger.debug("label:"+name);
//			  logger.debug("classname:"+classname);
//			  logger.debug("dbTypeName:"+precision);
//			  logger.debug("precision:"+precision);
//			  logger.debug("scale:"+sqlType);
//			  logger.debug("sqlType:"+sqlType);
			  
		}
		logger.debug("column Size:"+table.getColumnSize());
		assertTrue(table.getColumnSize()>0);
		//
		// Create rows
		//
		
		while(rs.next()){
			//Row row = new SqlResultRow(table);
			Row row = table.newRow();
			for(int i=1; i<=rsmd.getColumnCount();i++){
				ValueConverter vc  = ValueConverterFactory.getConverter(rsmd, i);
				row.setByIndex(i-1,vc.getValue(rs, i));
			}
			//table.addRow(row);
		}
		assertTrue(table.isEmpty()==false);
		logger.debug("row size:"+table.getRowSize());
		for(int rowIndex = 0;rowIndex< table.getRowSize();rowIndex++){
			for(int colIndex = 0; colIndex < table.getColumnSize(); colIndex++){
				System.out.print(table.getString(rowIndex,colIndex)+" ");
			}
			System.out.println("\n");
		}
				
	}
	
	@Test
	public void testJdbc() throws SQLException, JdbcTableException {
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select top 10 * from member ";
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		//
		//ResultSet으로 테이블 만들기
		//
		Table table = new JdbcTable();
		table.setUpWithResultSet(rs);
		assertNotNull(table);
		
		//System.out.println(table.toString());
		//column name Looping
		String[] columnNames = table.getHeaders();
		for (String name : columnNames) {
			System.out.println(""+name);
		}
		//row looping
		for (Row row : table.getRows()) {
			for (Object value : row.getList()){
				System.out.print(value+" ");
			}
			System.out.println("\n");
		}
		
		rs.close();
		stmt.close();
	}
	
}
