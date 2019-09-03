package com.hj.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.common.ControllerBase;
import com.hj.web.entity.Module;
import com.hj.web.entity.UserInfo;
import com.hj.web.services.ModuleService;
import com.hj.web.services.UserInfoService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/api")
public class CmsApiController extends ControllerBase {

	@Autowired
	ModuleService moduleService;
	@Autowired
	UserInfoService userInfoService;

	// 跨域请求
	public void responseInfo(HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8;");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,DELETE,PUT");
	}

	// 获取全部部门信息
	@RequestMapping(value = "getModuleList")
	@ResponseBody
	public Map<String, Object> getModuleList(HttpServletResponse response) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Module> moduleList = moduleService.getDataAll();
		if (moduleList != null && moduleList.size() > 0) {
			map.put("code", "200");
			map.put("DataList", moduleList);
		} else {
			map.put("code", "500");
			map.put("msg", "部门信息列表为空");
		}
		return map;
	}

	// 保存信息
	@RequestMapping(value = "saveApplyData")
	@ResponseBody
	public Map<String, Object> saveApplyData(HttpServletResponse response, UserInfo userInfo) throws Exception {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		boolean insert = userInfoService.insert(userInfo);
		if (insert) {
			map.put("code", "200");
		} else {
			map.put("code", "500");
			map.put("msg", "信息保存失败请重试");
		}
		return map;
	}

	@RequestMapping(value = "getWxData")
	@ResponseBody
	public Map<String, Object> share(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String strUrl = "http://ws.ruikj.cn/apply/page/index.html"; // 参数
		WinXinEntity wx = WeinXinUtil.getWinXinEntity(strUrl);
		// 将wx的信息到给页面
		map.put("data", wx);
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		System.out.println(JSONObject.fromObject(wx));
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		return map;
	}
}
