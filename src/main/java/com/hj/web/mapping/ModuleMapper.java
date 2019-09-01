package com.hj.web.mapping;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.Module;

public interface ModuleMapper {
	int del(String id);

	int insert(Module record);

	Module get(String id);

	int update(Module record);

	List<Module> getDataMessge(Map<String, Object> map);

	Integer getDataMessgeCount(Map<String, Object> map);

	Integer deletes(String boxeditId);

	List<Module> getDataAll();
}