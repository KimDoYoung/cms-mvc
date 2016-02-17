package kr.dcos.common.sql.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import kr.dcos.common.sql.Column.DataType;

public class ValueConverterFactory {
	public static final ValueConverter DOUBLE_MAPPER = new DoubleConverter();
	public static final ValueConverter INTEGER_MAPPER = new IntegerConverter();
	public static final ValueConverter DATE_MAPPER = new DateConverter();
	public static final ValueConverter STRING_MAPPER = new StringConverter();

	public static ValueConverter getConverter(ResultSetMetaData rsmd,
			int columnIndex) throws SQLException {
		
		String classname = rsmd.getColumnClassName(columnIndex);
		int precision = rsmd.getPrecision(columnIndex);
		int scale = rsmd.getScale(columnIndex);
		int sqlType = rsmd.getColumnType(columnIndex);

		if (sqlType == Types.INTEGER
				|| classname.equalsIgnoreCase("java.lang.Integer")
				|| (classname.equalsIgnoreCase("java.math.BigDecimal")
						&& precision == 10 && scale == 0))
			return INTEGER_MAPPER;

		if (classname.equalsIgnoreCase("java.math.BigDecimal"))
			return DOUBLE_MAPPER;

		if (classname.equalsIgnoreCase("java.sql.Timestamp"))
			return DATE_MAPPER;

		if (classname.equalsIgnoreCase("java.String"))
			return STRING_MAPPER;

		// default is string
		return STRING_MAPPER;
	}

	public static class IntegerConverter implements ValueConverter {

		@Override
		public DataType getType() {
			return DataType.Integer;
		}

		@Override
		public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
		   return new Integer(rs.getInt(columnIndex));
		}

	}

	public static class DateConverter implements ValueConverter {

		@Override
		public DataType getType() {
			return DataType.Date;
		}

		@Override
		public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
			//return rs.getDate(columnIndex);
			return rs.getTimestamp(columnIndex);
		}

	}

	public static class DoubleConverter implements ValueConverter {

		@Override
		public DataType getType() {
			return DataType.Double;
		}

		@Override
		public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
			return new Double(rs.getDouble(columnIndex));
			
		}

	}

	public static class StringConverter implements ValueConverter {

		@Override
		public DataType getType() {
			return  DataType.String;
		}

		@Override
		public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
			return rs.getString(columnIndex);
		}

	}
}
