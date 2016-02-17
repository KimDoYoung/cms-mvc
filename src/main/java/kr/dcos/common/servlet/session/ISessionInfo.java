package kr.dcos.common.servlet.session;

public interface ISessionInfo {

	String getLevel();
	void setValue(String key,Object value);
	Object getValue(String key);

}
