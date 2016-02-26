package kr.dcos.common.sql.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.dcos.common.sql.Column.DataType;

/**
 * Converter 인터페이스
 * 
 * @author Kim Do Young
 *
 */
public interface ValueConverter {
	public DataType getType();
	public Object getValue(ResultSet rs,int columnIndex) throws SQLException;
}
