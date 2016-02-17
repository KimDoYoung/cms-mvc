package kr.dcos.common.sql;

public class Column  {
	public enum DataType { undefined, Integer, Double, String , Date };
	private String name ;
	private DataType dataType;
	
	public  Column (DataType dataType,String name){
		this.name = name;
		this.dataType = dataType;
	}
	public  Column (String columnName,DataType dataType){
		this(dataType,columnName);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	@Override 
	public String toString(){
		return name + "("+dataType.toString()+")";
	}
}
