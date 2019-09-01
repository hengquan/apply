package com.hj.web.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.hj.web.entity.Module;
import com.hj.web.mapping.ModuleMapper;
import com.hj.web.services.IKeyGen;
import com.hj.web.services.ModuleService;

@Component
public class ModuleServiceImpl implements ModuleService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private ModuleMapper dao;

	@Override
	public boolean insert(Module entity) throws Exception {
		entity.setId(keyGen.getUUIDKey());
		return dao.insert(entity) > 0 ? true : false;
	}

	@Override
	public boolean update(Module entity) throws Exception {
		return dao.update(entity) > 0 ? true : false;
	}

	@Override
	public void del(Module entity) throws Exception {
		dao.del(entity.getId());
	}

	@Override
	public List<Module> getDataMessge(Map<String, Object> map) {
		return dao.getDataMessge(map);
	}

	@Override
	public Integer getDataMessgeCount(Map<String, Object> map) {
		return dao.getDataMessgeCount(map);
	}

	@Override
	public Module get(String id) {
		return dao.get(id);
	}

	@Override
	public Boolean save(Module project) {
		Boolean result = false;
		String id = project.getId();
		if (StringUtils.isNotEmpty(id)) {
			result = dao.update(project) > 0 ? true : false;
		} else {
			project.setId(keyGen.getUUIDKey());
			result = dao.insert(project) > 0 ? true : false;
		}
		return result;
	}

	@Override
	public Boolean deletes(String boxeditId) {
		return dao.deletes(boxeditId) > 0 ? true : false;
	}

	@Override
	public List<Module> getDataAll() {
		return dao.getDataAll();
	}
}
