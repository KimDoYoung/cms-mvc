package kr.dcos.common.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class RandomStringTest {

	@Test
	public void testRandomString() {
		RandomString rs = new RandomString(20);
		assertEquals(rs.nextString().length(), 20);
	}


}
