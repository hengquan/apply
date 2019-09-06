package com.hj.web.services;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.UserInfo;

public interface UserInfoService {

	public boolean insert(UserInfo entity) throws Exception;

	boolean update(UserInfo entity) throws Exception;

	public void del(UserInfo entity) throws Exception;

	public void del(String byid);

	public Boolean save(UserInfo entity) throws Exception;

	public UserInfo get(String sys_uuid) throws Exception;

	public UserInfo get(UserInfo sysUser);

	public List<UserInfo> findAll();

	public List<UserInfo> getDataList(Map<String, Object> map);

	public Integer getDataListCount(Map<String, Object> map);

	public void deletes(String boxeditId);

	public List<UserInfo> getParentId(String parentId);

	public List<UserInfo> upLoadExport(Map<String, Object> map);
}
