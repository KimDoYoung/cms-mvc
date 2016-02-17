package kr.dcos.common.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StrUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRemovePostfix() {
		String gMailId = "kdy987@gmail.com";
		gMailId = StrUtils.removePostfix(gMailId, "@gmail.com");
		assertEquals(gMailId,"kdy987");
	}

}
