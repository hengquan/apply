package com.hj.web.services;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.Module;

public interface ModuleService {
	/**
	 * 新增数据
	 * 
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(Module entity) throws Exception;

	/**
	 * 更新数据
	 * 
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(Module entity) throws Exception;

	/**
	 * 删除数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public void del(Module entity) throws Exception;

	/**
	 * 保存数据
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public Boolean save(Module entity) throws Exception;

	/**
	 * 通过ID查找
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Module get(String id) throws Exception;

	public List<Module> getDataMessge(Map<String, Object> map);

	public Integer getDataMessgeCount(Map<String, Object> map);

	public Boolean deletes(String boxeditId);

	public List<Module> getDataAll();
}
