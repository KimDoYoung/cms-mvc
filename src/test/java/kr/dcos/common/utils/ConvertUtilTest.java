package kr.dcos.common.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConvertUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals(0, ConvertUtil.toDouble("0.0") ,0);
		assertEquals(0, ConvertUtil.toDouble("0") ,0);
		Object o = "0";
		assertEquals(0, ConvertUtil.toDouble(o) ,0);
	
	}
	@Test
	public void testDouble() {
		 assertNull(ConvertUtil.toDouble(null));
	}
		
}
