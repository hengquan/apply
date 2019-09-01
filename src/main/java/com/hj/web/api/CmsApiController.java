package com.hj.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.common.ControllerBase;
import com.hj.web.entity.Module;
import com.hj.web.services.ModuleService;

@Controller
@RequestMapping("/api")
public class CmsApiController extends ControllerBase {

	@Autowired
	ModuleService moduleService;
	
	//获取全部部门信息
	@RequestMapping(value= "getModuleList")
	@ResponseBody
	public Map<String, Object> getModuleList(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Module> moduleList = moduleService.getDataAll();
		if(moduleList!=null && moduleList.size()>0) {
			map.put("code", "200");
			map.put("DataList", moduleList);
		}else {
			map.put("code", "500");
			map.put("msg", "部门信息列表为空");
		}
		return map;
	}
	
	
}
