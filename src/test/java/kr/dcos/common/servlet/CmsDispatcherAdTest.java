package kr.dcos.common.servlet;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import kr.dcos.common.servlet.session.IAuthorityChecker;

public class CmsDispatcherAdTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test @Ignore
	public void test() throws ClassNotFoundException {
		Class<?> superClass = Class.forName("kr.kalpa.controller.AuthorityChecker").getSuperclass();
		System.out.println(superClass); 
		System.out.println(Object.class.getSuperclass()); 
		System.out.println(String[][].class.getSuperclass());
	}
 
	@Test @Ignore
	public void test2() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		Class<? extends IAuthorityChecker> authorityChecker; // our expected class
		Class<?> clazz = Class.forName("kr.kalpa.controller.AuthorityChecker"); // our

		// check if our unknown class can be cast to our expected class
		if ((authorityChecker = clazz.asSubclass(IAuthorityChecker.class)) != null) {
			IAuthorityChecker i = authorityChecker.newInstance();
			System.out.println(i.toString());
	//		i.hasAuthority(sessionInfo, controllerName, methodName)
		}
	}
}
