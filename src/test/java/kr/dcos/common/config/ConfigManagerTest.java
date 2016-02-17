package kr.dcos.common.config;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigManagerTest {
	
	private static Logger logger = LoggerFactory
			.getLogger(ConfigManagerTest.class);
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		File file = new File("c:/application.properties");
		ConfigManager.getInstance().load(file);
		logger.debug(ConfigManager.getInstance().toString());
		String s = ConfigManager.getInstance().get("ApplicationName");
		assertEquals(s, "CmsMvc");
		assertEquals(ConfigManager.getInstance().get("WebMasterName"),"김도영");
	}

}
