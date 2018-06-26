package cn.tianya.codegenerator;

public class Field {

	private String name;
	
	private String upperName;
	
	private String type;
	
	private String jdbcType;
	
	private String desc;
	
	private String columName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUpperName() {
		return upperName;
	}

	public void setUpperName(String upperName) {
		this.upperName = upperName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getColumName() {
		return columName;
	}

	public void setColumName(String columName) {
		this.columName = columName;
	}
	
}
