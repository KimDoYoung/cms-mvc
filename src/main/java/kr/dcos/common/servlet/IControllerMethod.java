package kr.dcos.common.servlet;


public interface IControllerMethod {
	//public ForwardInfo invoke(RequestInfo requestInfo) throws Throwable; 
	public Object invoke(RequestInfo requestInfo) throws Throwable; 
}
