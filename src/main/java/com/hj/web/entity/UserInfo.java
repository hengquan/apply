package com.hj.web.entity;

import java.util.Date;
import java.util.List;

public class UserInfo {
	private String id;

  private String loginname;

  private String password;

  private String realname;

  private Integer age;

  private String sex;

  private String phone;

  private String headimgurl;

  private String district;

  private String address;

  private String language;

  private String selfprojauth;

  private Date ctime;

  private Integer isvalidate;

  private String remark;

  private String descn;
  
  private String userRoleId;
  
  private String roleName;
  
  private String parentId;
  
  private String moduleId;
  
  private String moduleName;
  
  private List<UserInfo> userList;

  public String getModuleName() {
	return moduleName;
}

public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
}

public String getModuleId() {
	return moduleId;
}

public void setModuleId(String moduleId) {
	this.moduleId = moduleId;
}

public List<UserInfo> getUserList() {
		return userList;
	}

	public void setUserList(List<UserInfo> userList) {
		this.userList = userList;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getId() {
      return id;
  }

  public void setId(String id) {
      this.id = id == null ? null : id.trim();
  }

  public String getLoginname() {
      return loginname;
  }

  public void setLoginname(String loginname) {
      this.loginname = loginname == null ? null : loginname.trim();
  }

  public String getPassword() {
      return password;
  }

  public void setPassword(String password) {
      this.password = password == null ? null : password.trim();
  }

  public String getRealname() {
      return realname;
  }

  public void setRealname(String realname) {
      this.realname = realname == null ? null : realname.trim();
  }

  public Integer getAge() {
      return age;
  }

  public void setAge(Integer age) {
      this.age = age;
  }

  public String getSex() {
      return sex;
  }

  public void setSex(String sex) {
      this.sex = sex == null ? null : sex.trim();
  }

  public String getPhone() {
      return phone;
  }

  public void setPhone(String phone) {
      this.phone = phone == null ? null : phone.trim();
  }

  public String getHeadimgurl() {
      return headimgurl;
  }

  public void setHeadimgurl(String headimgurl) {
      this.headimgurl = headimgurl == null ? null : headimgurl.trim();
  }

  public String getDistrict() {
      return district;
  }

  public void setDistrict(String district) {
      this.district = district == null ? null : district.trim();
  }

  public String getAddress() {
      return address;
  }

  public void setAddress(String address) {
      this.address = address == null ? null : address.trim();
  }

  public String getLanguage() {
      return language;
  }

  public void setLanguage(String language) {
      this.language = language == null ? null : language.trim();
  }

  public String getSelfprojauth() {
      return selfprojauth;
  }

  public void setSelfprojauth(String selfprojauth) {
      this.selfprojauth = selfprojauth == null ? null : selfprojauth.trim();
  }

  public Date getCtime() {
      return ctime;
  }

  public void setCtime(Date ctime) {
      this.ctime = ctime;
  }

  public Integer getIsvalidate() {
      return isvalidate;
  }

  public void setIsvalidate(Integer isvalidate) {
      this.isvalidate = isvalidate;
  }

  public String getRemark() {
      return remark;
  }

  public void setRemark(String remark) {
      this.remark = remark == null ? null : remark.trim();
  }

  public String getDescn() {
      return descn;
  }

  public void setDescn(String descn) {
      this.descn = descn == null ? null : descn.trim();
  }
}