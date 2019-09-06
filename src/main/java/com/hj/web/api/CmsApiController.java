/*package com.hj.web.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.PageData;

import org.apache.commons.lang3.StringUtils;
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
		String url = request.getParameter("url");
		Map<String, Object> map = new HashMap<String, Object>();
		String strUrl = "";
		if (StringUtils.isNotEmpty(url)) {
			strUrl = url;
		} else {
			strUrl = "http://ws.ruikj.cn/apply/page/sign.html"; // 请求页面或其他地址
		}
		System.out.println(strUrl);
		WinXinEntity wx = WeinXinUtil.getWinXinEntity(strUrl);
		// 将wx的信息到给页面
		map.put("data", wx);
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		System.out.println(JSONObject.fromObject(wx));
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		System.out.println(strUrl);
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		return map;
	}

	*//**
	 * 导出报表
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/export")
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取数据
		List<PageData> list = null;
		// excel标题
		String[] title = { "名称", "性别", "年龄", "学校", "班级" };
//excel文件名
String fileName = "学生信息表"+System.currentTimeMillis()+".xls";

 //sheet名
String sheetName = "学生信息表";

for(int i = 0; i < list.size(); i++) {
          content[i] = new String[title.length];
          PageData obj = list.get(i);
          content[i][0] = obj.get("stuName").tostring();
          content[i][1] = obj.get("stuSex").tostring();
          content[i][2] = obj.get("stuAge").tostring();
          content[i][3] = obj.get("stuSchoolName").tostring();
          content[i][4] = obj.get("stuClassName").tostring();
　　　　}

　　　　//创建HSSFWorkbook 
　　　　HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

　　　　//响应到客户端
　　　　try {
　　　　　　this.setResponseHeader(response, fileName);
     　　　　OutputStream os = response.getOutputStream();
     　　　　wb.write(os);
     　　　　os.flush();
     　　　　os.close();
　　　　　} catch (Exception e) {
     　　　　e.printStackTrace();
　　　　　}
	}

	// 发送响应流方法
	public void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
*/