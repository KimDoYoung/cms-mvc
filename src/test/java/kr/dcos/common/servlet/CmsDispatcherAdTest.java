package kr.dcos.common.servlet;

import static org.junit.Assert.*;
import kr.dcos.common.servlet.session.IAuthorityChecker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CmsDispatcherAdTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws ClassNotFoundException {
		Class<?> superClass = Class.forName("kr.kalpa.controller.AuthorityChecker").getSuperclass();
		System.out.println(superClass); // prints "class com.journaldev.reflection.BaseClass"
		System.out.println(Object.class.getSuperclass()); // prints "null"
		System.out.println(String[][].class.getSuperclass());
	}

	@Test
	public void test2() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		Class<? extends IAuthorityChecker> authorityChecker; // our expected class
		Class<?> clazz = Class.forName("kr.kalpa.controller.AuthorityChecker"); // our
																				// unknown
																				// class

		// check if our unknown class can be cast to our expected class
		if ((authorityChecker = clazz.asSubclass(IAuthorityChecker.class)) != null) {
			IAuthorityChecker i = authorityChecker.newInstance();
			System.out.println(i.toString());
	//		i.hasAuthority(sessionInfo, controllerName, methodName)
		}
	}
}
