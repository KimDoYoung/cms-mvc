package kr.dcos.common.message;

import static org.junit.Assert.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import kr.dcos.common.sql.utils.StopWatch;
import kr.dcos.common.utils.PrintMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class MessageManagerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws URISyntaxException, MalformedURLException {
		File file = new File(this.getClass().getResource("/messages").toURI());
		URL[] urls = {file.toURI().toURL()};
		ClassLoader loader = new URLClassLoader(urls);
		ResourceBundle bundle = ResourceBundle.getBundle("message",Locale.getDefault(),loader);
		System.out.println(bundle.getString("1.jsp.title"));
		assertNotNull(bundle);
	}
	@Test
	public void test1(){
		Locale locale = new Locale("ko","KR");
		StopWatch sw = new StopWatch();
		sw.start();
		for(int i=0;i<100;i++){
			String s = MessageManager.getInstance().getString(locale, "1.jsp.title");
			System.out.println(i+":"+sw.toString());
			sw.start();
		}
		sw.stop();
		//assertTrue(s.length()>0);
	}
	/**
	 * default locale 로 가져온다.
	 */
	@Test
	public void test2(){
		String s = MessageManager.getInstance().getString("1.jsp.title");
		System.out.println(s);
	}
	/**
	 * subkey 로 가져온다.
	 */
	@Test
	public void test3(){
		Map<String,String> msg = MessageManager.getInstance().getAll(new Locale("ko","KR"),"field");
		System.out.println((new PrintMap(msg)).toString());
	}
}
