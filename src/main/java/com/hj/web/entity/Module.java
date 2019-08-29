package com.hj.web.entity;

import java.util.Date;

public class Module {
    private String id;

    private String moduleName;

    private Integer sort;

    private Integer isValidate;

    private Date cTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIsValidate() {
		return isValidate;
	}

	public void setIsValidate(Integer isValidate) {
		this.isValidate = isValidate;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

}