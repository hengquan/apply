package com.hj.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.common.ControllerBase;
import com.hj.utils.HashSessions;
import com.hj.utils.JsonUtils;
import com.hj.utils.MD5Utils;
import com.hj.web.entity.UserInfo;
import com.hj.web.services.LoginService;
import com.hj.web.services.UserInfoService;

/**
 * 
 * @ClassName: LoginController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jun.hai
 * @date 2015年1月9日 下午8:21:12
 *
 */
@Controller
public class LoginController extends ControllerBase {

	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

	private HashSessions hashSession = HashSessions.getInstance();

	@Resource
	private LoginService loginService;
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping(value = "/login.ky", method = RequestMethod.GET)
	public String login(ModelMap map) {
		return "login/new_login";
	}

	@RequestMapping(value = "/login.ky", method = RequestMethod.POST)
	public String doLogin(ModelMap map) {
		String userName = getTrimParameter("userName");
		String password = getTrimParameter("password");

		String fallbackUrl = "/";
		String successUrl = null;
		try {
			UserInfo userInfo = loginService.getUserInfo(userName);
			if (null != userInfo) {
				UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
				Subject subject = SecurityUtils.getSubject();
				subject.login(token);

				SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
				if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
					successUrl = savedRequest.getRequestUrl();
				}

				if (successUrl == null) {
					successUrl = fallbackUrl;
				}

				hashSession.setUserInfoSession(request, userInfo);
				if (successUrl.indexOf("cms") > -1) {
					successUrl = successUrl.substring(successUrl.indexOf("cms")).replaceAll("cms", "");
				}

				String newpassword = MD5Utils.MD5(password);
				Integer isvalidate = userInfo.getIsvalidate();
				if (newpassword.equals(userInfo.getPassword()) && isvalidate == 1) {
					successUrl = "/item/list";
					successUrl = "redirect:" + successUrl;
				} else {
					map.put("loginId", userName);
					map.put("loginFaild", "用户密码错误，请核实或联系管理员！");
					successUrl = "login/new_login";
				}
			} else {
				map.put("loginId", userName);
				map.put("loginFaild", "该用户未注册，请核实或联系管理员！");
				successUrl = "login/new_login";
			}
		} catch (Exception e) {
			map.put("loginId", userName);
			e.printStackTrace();
			map.put("loginFaild", "密码/用户名不符合，请重新输入！");
			successUrl = "login/new_login";
		}

		return successUrl;
	}

	@RequestMapping(value = "/logout.ky")
	public String logout(ModelMap map) {

		return "redirect:login.ky";
	}

	@RequestMapping(value = "/loginMsg")
	@ResponseBody
	public String loginMsg() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户角色权限信息
			String id = hashSession.getCurrentAdmin(request).getId();
			UserInfo userInfo = userInfoService.get(id);
			map.put("msg", "100");
			map.put("userInfo", userInfo);
		} catch (Exception e) {
			map.put("msg", "100");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}

}
