package com.hj.web.mapping;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.UserInfo;

public interface UserInfoMapper {
    int del(String id);

    int insert(UserInfo record);

    UserInfo get(String id);

    int update(UserInfo record);

		List<UserInfo> findAll();

		List<UserInfo> getDataList(Map<String, Object> map);

		Integer getDataListCount(Map<String, Object> map);

		void deletes(String boxeditId);

		UserInfo selectByLoginId(String loginId);

		List<UserInfo> getParentId(String parentId);

		List<UserInfo> upLoadExport(Map<String, Object> map);
}