package com.hj.wxmp.mobile.controller;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.utils.JsonUtils;
import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.entity.SysItemRole;
import com.hj.wxmp.mobile.entity.SysRole;
import com.hj.wxmp.mobile.entity.UserCustRef;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.entity.UserRole;
import com.hj.wxmp.mobile.services.AccessRecord01Service;
import com.hj.wxmp.mobile.services.AccessRecord02Service;
import com.hj.wxmp.mobile.services.AccessRecord03Service;
import com.hj.wxmp.mobile.services.CustomerService;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjUserRoleService;
import com.hj.wxmp.mobile.services.ProjectService;
import com.hj.wxmp.mobile.services.SysRoleService;
import com.hj.wxmp.mobile.services.UserCustRefService;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.hj.wxmp.mobile.services.UserRoleService;

//项目管理
@Controller
public class CustomerController extends ControllerBase {

	private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);
	private HashSessions hashSession = HashSessions.getInstance();

	@Resource
	private IKeyGen keyGen;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	UserRoleService sysUserRoleService;
	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	ProjUserRoleService projUserRoleService;
	@Autowired
	UserCustRefService userCustRefService;
	@Autowired
	ProjectService projectService;
	@Autowired
	CustomerService customerService;
	@Autowired
	AccessRecord01Service accessRecord01Service;
	@Autowired
	AccessRecord02Service accessRecord02Service;
	@Autowired
	AccessRecord03Service accessRecord03Service;

	// 客户列表
	@RequestMapping(value = "/customer/customerList")
	public String userList(@RequestParam(value="nowPage",defaultValue="1") int nowPage,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize,
			@RequestParam(value="isValidate",defaultValue="") String isValidate,ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		//纪录总数
		Integer listMessgeCount = 0;
		String name = getTrimParameter("custName");
		Integer start = ((nowPage - 1) * pageSize);
		map.put("page", start);
		map.put("pageSize", pageSize);
		map.put("isValidate", isValidate);
		String pageUrl = "customer/list";
		try {
			//用户角色权限信息
			UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
			String roleId = userRole.getRoleid();
			SysRole role = sysRoleService.findById(roleId);
			if(name == null){
				map.put("name", "");
			}else{
				map.put("name", name);
			}
			// 获取所有用户信息
			List<Map<String,Object>> userMsg = userCustRefService.selectByUserMessge(map);
			//所有信息数量
			listMessgeCount = userCustRefService.selectByUserMessgeCount(map);
		 	Integer totalCount = listMessgeCount%pageSize;
			Integer totalPageNum = 0;
			if(totalCount==0){
				totalPageNum = listMessgeCount/pageSize;
			}else{
				totalPageNum = (listMessgeCount/pageSize)+1;
			}
			model.put("nowPage", nowPage);
			model.put("totalPageNum", totalPageNum);
			model.addAttribute("userMsg", userMsg);
			model.addAttribute("role", role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		model.addAttribute("itemId", itemId);
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("isValidate", isValidate);
		return pageUrl;
	}

	
	
	
	//查询该项目里面的所有用户信息
	@ResponseBody
	@RequestMapping(value = "/customer/getUserList")
	public String getUserList(ModelMap model) {
		Map<String,Object>map=new HashMap<String,Object>();
		String projId = getTrimParameter("projId");
		try {
			//用户角色权限信息
			UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
			String roleId = userRole.getRoleid();
			SysRole role = sysRoleService.findById(roleId);
			List<UserInfo> userData = null;
			if(role.getRoleName().equals("管理员")) userData = projUserRoleService.selectProjUserDataByProjIdGLY(projId);
			if(role.getRoleName().equals("项目负责人")) userData = projUserRoleService.selectProjUserDataByProjIdFZR(projId);
			map.put("msg", "100");
			map.put("userData", userData);
			map.put("projId", projId);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}
	
	
	//转移客户信息
	@RequestMapping(value = "/customer/transferCustMsg")
	public String transferCustMsg(ModelMap model) {
		try {
			Map<String,Object>parmeterMap=new HashMap<String,Object>();
			String projId = getTrimParameter("projId");
			String custId = getTrimParameter("custId");
			//转移到该用户名下
			String userId = getTrimParameter("userId");
			parmeterMap.put("projId", projId);
			parmeterMap.put("custId", custId);
			parmeterMap.put("userId", userId);
			//用户客户表
			userCustRefService.updateByProjIdAndCustId(parmeterMap);
			//首访
			accessRecord01Service.updateByProjIdAndCustId(parmeterMap);
			//复访
			accessRecord02Service.updateByProjIdAndCustId(parmeterMap);
			//成交
			accessRecord03Service.updateByProjIdAndCustId(parmeterMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:customerList";
	}




	//查询该项目里面的所有用户信息(多选)
	@ResponseBody
	@RequestMapping(value = "/customer/getUserLists")
	public String getUserLists(ModelMap model) {
		Map<String,Object>map=new HashMap<String,Object>();
		String userCustIds = getTrimParameter("userCustIds");
		try {
			if(userCustIds!=null){
				//项目id
				String projId = null;
				//原权限人id
				String custId = null;
				String[] userCustIdList = userCustIds.split(",");
				for(String userCustId : userCustIdList){
					UserCustRef userCustRef = userCustRefService.findById(userCustId);
					String projid = userCustRef.getProjid();
					custId += userCustRef.getCustid()+",";
					//对比是否为同一个项目
					if(projId==null){
						projId = projid;
					}else{
						if(!projId.equals(projid)){
							map.put("msg", "105");
							return JsonUtils.map2json(map);
						}
					}
				}
				//用户角色权限信息
				UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
				String roleId = userRole.getRoleid();
				SysRole role = sysRoleService.findById(roleId);
				List<UserInfo> userData = null;
				if(role.getRoleName().equals("管理员")) userData = projUserRoleService.selectProjUserDataByProjIdGLY(projId);
				if(role.getRoleName().equals("项目负责人")) userData = projUserRoleService.selectProjUserDataByProjIdFZR(projId);
				map.put("msg", "100");
				map.put("userData", userData);
				map.put("projId", projId);
				map.put("custId", custId);
			}else{
				map.put("msg", "200");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}
	
	
	
	
	
	
	
	
	
	//查询结果导出excel
	@ResponseBody
	@RequestMapping(value = "/customer/customerMsgExcel")
	public String customerMsgExcel(@RequestParam(value="isValidate",defaultValue="") String isValidate,
			ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		//所有条件
		//首访接待开始时间
		String record01Begin = getTrimParameter("record01Begin");
		//首访接待结束时间
		String record01End = getTrimParameter("record01End");
		//复访接待开始时间
		String record02Begin = getTrimParameter("record02Begin");
		//复访接待结束时间
		String record02End = getTrimParameter("record02End");
		//成交接待开始时间
		String record03Begin = getTrimParameter("record03Begin");
		//成交接待结束时间
		String record03End = getTrimParameter("record03End");
		//年龄段
		String agegroup = getTrimParameter("agegroups");
		//认知渠道
		String knowway = getTrimParameter("knowways");
		//房屋面积
		String liveacreage = getTrimParameter("liveacreages");
		//接受价格区段
		String pricesection = getTrimParameter("pricesections");
		//客户评价
		String custscore = getTrimParameter("custscores");
		//名字
		String name = getTrimParameter("custName");
		map.put("isValidate", isValidate);
		try {
			if(name == null){
				map.put("name", "");
			}else{
				map.put("name", name);
			}
			map.put("record01Begin", record01Begin);
			map.put("record01End", record01End);
			map.put("record02Begin", record02Begin);
			map.put("record02End", record02End);
			map.put("record03Begin", record03Begin);
			map.put("record03End", record03End);
			map.put("agegroup", agegroup);
			map.put("knowway", knowway);
			map.put("liveacreage", liveacreage);
			map.put("pricesection", pricesection);
			map.put("custscore", custscore);
			// 获取所有用户信息
			List<Map<String,Object>> userMsg = userCustRefService.downloadExcel(map);
			// 第一步，创建一个webbook，对应一个Excel文件  
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        //样式
	        //设置表格样式
			HSSFCellStyle style = wb.createCellStyle();  
			//生成一个字体
			HSSFFont font=wb.createFont();
			font.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
			font.setFontHeightInPoints((short)12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
			//把字体应用到当前的样式
			style.setFont(font);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("客户查询结果");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //第一行
	        HSSFRow row0 = sheet.createRow((int) 0);  
	        HSSFCell createCell = row0.createCell((short) 0);
	        createCell.setCellValue("客户查询结果");  
	        createCell.setCellStyle(style); 
	        sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
	        //第二行
	        HSSFRow row1 = sheet.createRow((int) 1);  
	        HSSFCell createCell1 = row1.createCell((short) 0);
	        createCell1.setCellValue("导出人:张恒全");
	        createCell1.setCellStyle(style); 
	        sheet.addMergedRegion(new CellRangeAddress(1,1,0,1));
	        //第二行第一列
	        HSSFCell createCell2 = row1.createCell((short) 4);
	        createCell2.setCellValue("导出时间:2017-09-11");
	        createCell2.setCellStyle(style); 
	        //合并单元格
	        sheet.addMergedRegion(new CellRangeAddress(1,1,4,5));
	        //第四列
	        HSSFCell createCell3 = row1.createCell((short) 6);
	        createCell3.setCellValue("总条数:26条");
	        createCell3.setCellStyle(style); 
	        //合并单元格
	        sheet.addMergedRegion(new CellRangeAddress(1,1,6,8));
	        //五列及以后
	        HSSFRow row = sheet.createRow((int) 2);
	        HSSFCell cell = row.createCell((short) 0);  
	        cell.setCellValue("客户姓名");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 1);  
	        cell.setCellValue("客户电话");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("性别");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("项目名称");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 4);  
	        cell.setCellValue("关注时间");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("顾问名称");  
	        cell.setCellStyle(style);  
	        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
	        //List list = CreateSimpleExcelToDisk.getStudent();  
	        for (int i = 0; i < userMsg.size(); i++)  
	        {  
	            row = sheet.createRow((int) i + 3);  
	            Map<String, Object> map2 = userMsg.get(i);  
	            // 第四步，创建单元格，并设置值  
	            row.createCell((short) 0).setCellValue(map2.get("custName").toString());  
	            row.createCell((short) 1).setCellValue(map2.get("phoneNum").toString());  
	            row.createCell((short) 2).setCellValue(map2.get("custSex").toString());  
	            row.createCell((short) 3).setCellValue(map2.get("projName").toString());  
	            cell = row.createCell((short) 4);  
	            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map2.get("cTime")));  
	            row.createCell((short) 5).setCellValue(map2.get("realName").toString());  
	        }  
	         sheet.autoSizeColumn((short)0); //调整第一列宽度
	         sheet.autoSizeColumn((short)1); //调整第二列宽度
	         sheet.autoSizeColumn((short)2); //调整第三列宽度
	         sheet.autoSizeColumn((short)3); //调整第四列宽度
	         sheet.autoSizeColumn((short)4); //调整第四列宽度
	         sheet.autoSizeColumn((short)5); //调整第四列宽度
	        // 第六步，将文件存到指定位置  
            FileOutputStream fout = new FileOutputStream("D:/cust.xls");  
            wb.write(fout);  
            fout.close();  
            map.put("msg", "100");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		return JsonUtils.map2json(map);
	}
	
	
	
	
	
	
	
	
	
	
	// 综合查询
	@RequestMapping(value = "/customer/integratedQuery")
	public String integratedQuery(@RequestParam(value="nowPage",defaultValue="1") int nowPage,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize,ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		//所有条件
		//首访接待开始时间
		String record01Begin = getTrimParameter("record01Begin");
		//首访接待结束时间
		String record01End = getTrimParameter("record01End");
		//复访接待开始时间
		String record02Begin = getTrimParameter("record02Begin");
		//复访接待结束时间
		String record02End = getTrimParameter("record02End");
		//成交接待开始时间
		String record03Begin = getTrimParameter("record03Begin");
		//成交接待结束时间
		String record03End = getTrimParameter("record03End");
		//年龄段
		String agegroup = getTrimParameter("agegroups");
		//认知渠道
		String knowway = getTrimParameter("knowways");
		//房屋面积
		String liveacreage = getTrimParameter("liveacreages");
		//接受价格区段
		String pricesection = getTrimParameter("pricesections");
		//客户评价
		String custscore = getTrimParameter("custscores");
		//纪录总数
		Integer listMessgeCount = 0;
		String name = getTrimParameter("custName");
		Integer start = ((nowPage - 1) * pageSize);
		map.put("page", start);
		map.put("pageSize", pageSize);
		String pageUrl = "inquiryStatistics/synthesize/list";
		try {
			//用户角色权限信息
			UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
			String roleId = userRole.getRoleid();
			SysRole role = sysRoleService.findById(roleId);
			if(name == null){
				map.put("name", "");
			}else{
				map.put("name", name);
			}
			map.put("record01Begin", record01Begin);
			map.put("record01End", record01End);
			map.put("record02Begin", record02Begin);
			map.put("record02End", record02End);
			map.put("record03Begin", record03Begin);
			map.put("record03End", record03End);
			map.put("agegroup", agegroup);
			map.put("knowway", knowway);
			map.put("liveacreage", liveacreage);
			map.put("pricesection", pricesection);
			map.put("custscore", custscore);
			// 获取所有用户信息
			List<Map<String,Object>> userMsg = userCustRefService.selectZongHe(map);
			//所有信息数量
			listMessgeCount = userCustRefService.selectZongHeCount(map);
		 	Integer totalCount = listMessgeCount%pageSize;
			Integer totalPageNum = 0;
			if(totalCount==0){
				totalPageNum = listMessgeCount/pageSize;
			}else{
				totalPageNum = (listMessgeCount/pageSize)+1;
			}
			model.put("nowPage", nowPage);
			model.put("totalPageNum", totalPageNum);
			model.addAttribute("userMsg", userMsg);
			model.addAttribute("role", role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		model.addAttribute("itemId", itemId);
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		return pageUrl;
	}
	
	
}
