package kr.dcos.common.servlet.session;

public interface IAuthorityChecker {

	boolean hasAuthority(ISessionInfo sessionInfo, String controllerName, String methodName);

}
