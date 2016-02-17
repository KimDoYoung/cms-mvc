package kr.dcos.common.sql.exception;

public class SqlExecutorException extends Exception  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8324032696432570069L;
	public SqlExecutorException(String msg){
		super(msg);
	}
	public SqlExecutorException(String sqlId,String sql,String dbErrMsg){
		super("sqlId:"+sqlId+":["+sql+"]"+" error message:["+dbErrMsg+"]");
	}
	public SqlExecutorException(String sql, String dbErrMsg) {
		super("["+sql+"]"+" error message:["+dbErrMsg+"]");
	}
}
