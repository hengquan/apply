package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.hj.wxmp.mobile.entity.ProjUserRole;
import com.hj.wxmp.mobile.mapping.ProjUserRoleMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjUserRoleService;

@Component
public class ProjUserRoleServiceImpl implements ProjUserRoleService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private ProjUserRoleMapper dao;
	@Override
	public boolean insert(ProjUserRole entity) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean update(ProjUserRole entity) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<ProjUserRole> listEntity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(ProjUserRole entity) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ProjUserRole saveEntity(ProjUserRole entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ProjUserRole findById(String sys_uuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ProjUserRole findByOpenid(String openid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int perfectUserInfo(ProjUserRole sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<ProjUserRole> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<Map<String, Object>> selectByUserId(String userId) {
		return dao.selectByUserId(userId);
	}


}
