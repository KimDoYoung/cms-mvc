package kr.dcos.common.sql.database;

import kr.dcos.common.sql.SqlParam;
import kr.dcos.common.sql.Table;
import kr.dcos.common.sql.exception.SqlExecutorException;

public interface ServiceInterface {
	Table select(String databaseName,String sqlId, SqlParam param) throws SqlExecutorException;
	int insert(String databaseName,String sqlId, SqlParam param);
	int update(String databaseName,String sqlId, SqlParam param);
	int delete(String databaseName,String sqlId, SqlParam param);
	
}
