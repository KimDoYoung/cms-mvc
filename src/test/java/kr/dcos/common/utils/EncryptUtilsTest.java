package kr.dcos.common.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptUtilsTest {
	
	private static Logger logger = LoggerFactory
			.getLogger(EncryptUtilsTest.class);
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBase64encode() {
		String s = "abc 123 한글~";
		String t = EncryptUtils.base64encode(s);
		logger.debug(t);
		String so = EncryptUtils.base64decode(t);
		assertEquals(s, so);
	}

	@Test
	public void testXorMessage() {
		String s = "abc 123 한글~";
		String t = EncryptUtils.base64encode(EncryptUtils.xorMessage(s, "kdy987"));
		logger.debug(t);
		String u = EncryptUtils.xorMessage(EncryptUtils.base64decode(t),"kdy987");
		logger.debug(u);
		assertEquals(s,u);
	}

}
