package com.hj.web.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hj.utils.Configurations;
import com.hj.utils.MD5Utils;
import com.hj.web.entity.UserInfo;
import com.hj.web.entity.UserRole;
import com.hj.web.mapping.UserInfoMapper;
import com.hj.web.mapping.UserRoleMapper;
import com.hj.web.services.IKeyGen;
import com.hj.web.services.UserInfoService;

@Component
public class UserInfoServiceImpl implements UserInfoService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private UserInfoMapper userInfoMapper;
	@Resource
	private UserRoleMapper userRoleMapper;

	@Override
	public boolean insert(UserInfo entity) throws Exception {
		entity.setId(keyGen.getUUIDKey());
		boolean result = userInfoMapper.insert(entity) > 0 ? true : false;
		return result;
	}

	@Override
	public boolean update(UserInfo entity) throws Exception {
		return userInfoMapper.update(entity) > 0 ? true : false;
	}

	@Override
	public List<UserInfo> findAll() {
		return userInfoMapper.findAll();
	}

	@Override
	public List<UserInfo> getDataList(Map<String, Object> map) {
		return userInfoMapper.getDataList(map);
	}

	@Override
	public Integer getDataListCount(Map<String, Object> map) {
		return userInfoMapper.getDataListCount(map);
	}

	@Override
	public void del(String byid) {
		userInfoMapper.del(byid);
	}

	@Override
	public void deletes(String boxeditId) {
		userInfoMapper.deletes(boxeditId);
	}

	@Override
	public void del(UserInfo entity) throws Exception {
		userInfoMapper.del(entity.getId());
	}

	@Override
	public UserInfo get(String sys_uuid) throws Exception {
		return userInfoMapper.get(sys_uuid);
	}

	@Override
	public UserInfo get(UserInfo sysUser) {
		return userInfoMapper.get(sysUser.getId());
	}

	@Override
	public Boolean save(UserInfo entity) throws Exception {
		boolean result = false;
		String id = entity.getId();
		String roleId = entity.getUserRoleId();
		if (StringUtils.isNotEmpty(id)) {
			UserInfo userInfo = userInfoMapper.get(id);
			if (userInfo != null) {
				result = userInfoMapper.update(entity) > 0 ? true : false;
			}
		} else {
			entity.setId(keyGen.getUUIDKey());
			String passWord = Configurations.getPassWord();
			if (StringUtils.isNotEmpty(passWord)) {
				entity.setPassword(MD5Utils.MD5(passWord));
			}
			result = userInfoMapper.insert(entity) > 0 ? true : false;
		}
		if (result) {
			UserRole userRole = userRoleMapper.selectByUserId(entity.getId());
			if (userRole != null) {
				userRole.setRoleid(roleId);
				result = userRoleMapper.updateByPrimaryKeySelective(userRole) > 0 ? true : false;
			} else {
				userRole = new UserRole();
				userRole.setId(keyGen.getUUIDKey());
				userRole.setUserid(entity.getId());
				userRole.setRoleid(roleId);
				result = userRoleMapper.insertSelective(userRole) > 0 ? true : false;
			}
		}
		return result;
	}

	@Override
	public List<UserInfo> getParentId(String parentId) {
		return userInfoMapper.getParentId(parentId);
	}

	@Override
	public List<UserInfo> upLoadExport(Map<String, Object> map) {
		return userInfoMapper.upLoadExport(map);
	}

}
