package com.hj.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hj.common.ControllerBase;
import com.hj.utils.Configurations;
import com.hj.utils.JsonUtils;
import com.hj.utils.MD5Utils;
import com.hj.utils.UploadUtils;
import com.hj.web.dao.SysItemRoleDao;
import com.hj.web.entity.UserInfo;
import com.hj.web.services.FileService;
import com.hj.web.services.IKeyGen;
import com.hj.web.services.PageService;
import com.hj.web.services.SysRoleService;
import com.hj.web.services.UserInfoService;
import com.hj.web.services.UserRoleService;

/**
 * 
 * @ClassName: UserController
 * @Description: TODO(用户管理)
 * @author weilesi
 * @date 2016年2月26日 上午16:38:30
 *
 */
@Controller
public class UserController extends ControllerBase {

	@Resource
	private IKeyGen keyGen;
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	SysRoleService roleService;
	@Autowired
	FileService fileService;
	@Autowired
	PageService pageService;

	/**
	 * 主页
	 */
	@RequestMapping("/user/index")
	public String index(ModelMap map) {
		String pageUrl = "user/index";

		try {
			Object obj = request.getSession().getAttribute("adminSession");
			if (null != obj) {
				UserInfo admin = (UserInfo) obj;
				map.put("currentUser", admin);
			}

			map.put("activeFlag", "user");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pageUrl;
	}

	/**
	 * 修改用户密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/updatePwd")
	@ResponseBody
	public String updatePwd() throws Exception {
		String oldPwd = getTrimParameter("oldpwd");
		String newpwd = getTrimParameter("newpwd");
		Object obj = request.getSession().getAttribute("adminSession");
		if (null != obj) {
			UserInfo userInfo = (UserInfo) obj;
			if (userInfo.getPassword().equals(MD5Utils.MD5(oldPwd))) {
				userInfo.setPassword(MD5Utils.MD5(newpwd));
				userInfoService.update(userInfo);
			} else {
				return "{\"code\":\"error\"}";
			}

		}
		return "{\"code\":\"ok\"}";

	}

	@RequestMapping("/user/wpPwd")
	public String upPwd() {
		return "admin/update_pwd";
	}

	// 用户列表
	@RequestMapping(value = "/user/userList")
	public String userList(PageService page, ModelMap model) throws Exception {
		String pageUrl = "user/list";
		// 纪录总数
		Integer listMessgeCount = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		String userName = getTrimParameter("userName");
		map.put("userName", userName);
		// 存页面起始位置信息
		pageService.getPageLocation(page, map);
		try {
			// 获取用户所有的信息
			List<UserInfo> selectList = userInfoService.getDataList(map);
			listMessgeCount = userInfoService.getDataListCount(map);
			// 获取页面信息
			pageService.getPageData(listMessgeCount, model, page);
			model.put("userName", userName);
			model.addAttribute("userList", selectList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageUrl = super.userIRoleItem(model, pageUrl);
		return pageUrl;
	}

	// 添加项目
	@RequestMapping(value = "/user/save")
	public String addUser(ModelMap model, UserInfo userInfo,
			@RequestParam(value = "headFile", required = false) MultipartFile headFile) {
		try {
			if (headFile != null && headFile.getSize() > 0) {
				String filePath = fileService.getFilePath(request);
				Map<String, Object> mapData = UploadUtils.upload(headFile, request, filePath);
				if (mapData != null) {
					String fileName = mapData.get("fileName") == null ? "" : mapData.get("fileName").toString();
					if (StringUtils.isNotEmpty(fileName))
						userInfo.setHeadimgurl(fileName);
				}
			}
			UserInfo userData = super.getUserInfo();
			if (userData != null) {
				String parentId = userData.getParentId();
				if (StringUtils.isNotEmpty(parentId)) {
					if (parentId.equals("0"))
						userInfo.setParentId("1");
					else
						userInfo.setParentId(userData.getId());
				}
			}
			userInfoService.save(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String itemId = getTrimParameter("itemId");
		String positionId = getTrimParameter("positionId");
		model.addAttribute("itemId", itemId);
		model.addAttribute("positionId", positionId);
		return "redirect:userList";
	}

	// 删除项目
	@RequestMapping(value = "/user/del")
	public String deleteUser(ModelMap model) {
		try {
			String byid = getTrimParameter("byid");
			UserInfo userInfo = userInfoService.get(byid);
			String boxeditId = getTrimParameter("boxeditId");
			if (StringUtils.isNotEmpty(byid)) {
				userInfoService.del(userInfo);
			} else {
				userInfoService.deletes(boxeditId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String itemId = getTrimParameter("itemId");
		String positionId = getTrimParameter("positionId");
		model.addAttribute("itemId", itemId);
		model.addAttribute("positionId", positionId);
		return "redirect:userList";
	}

	// 根据ID获取用户信息
	@RequestMapping(value = "/user/getData")
	@ResponseBody
	public String getData(ModelMap model, UserInfo userInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userInfo = userInfoService.get(userInfo);
			userInfo = urlManage(userInfo);
			if (userInfo != null) {
				map.put("msg", "0");
				map.put("Data", userInfo);
			}
		} catch (Exception e) {
			map.put("msg", "1");
		}
		return JsonUtils.map2json(map);
	}

	// 处理图片访问地址
	public UserInfo urlManage(UserInfo userInfo) {
		if (userInfo != null) {
			String headimgurl = userInfo.getHeadimgurl();
			if (StringUtils.isNotEmpty(headimgurl)) {
				String path = Configurations.getAccessUrl();
				if (StringUtils.isNotEmpty(path)) {
					headimgurl = path + headimgurl;
					userInfo.setHeadimgurl(headimgurl);
				}
			}
		}
		return userInfo;
	}

}
