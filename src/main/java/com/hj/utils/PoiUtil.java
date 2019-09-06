package com.hj.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hj.web.entity.UserInfo;

public class PoiUtil {
	/**
	 * 导出Excel
	 * 
	 * @param sheetName    sheet名称
	 * @param title        标题
	 * @param userInfoList 内容
	 * @param wb           HSSFWorkbook对象
	 * @return
	 */
	public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, List<UserInfo> userInfoList,
			HSSFWorkbook wb) {

		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if (wb == null) {
			wb = new HSSFWorkbook();
		}

		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);

		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
		HSSFRow row = sheet.createRow(0);

		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		// 声明列对象
		HSSFCell cell = null;

		// 创建标题
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}

		// 创建内容
		for (int i = 0; i < userInfoList.size(); i++) {
			row = sheet.createRow(i + 1);
			UserInfo userInfo = userInfoList.get(i);
			// 名字
			String realname = userInfo.getRealname();
			// 电话
			String phone = userInfo.getPhone();
			// 部门
			String moduleName = userInfo.getModuleName();
			if (StringUtils.isEmpty(moduleName)) {
				moduleName = userInfo.getModuleId();
			}
			// 是否乘坐班车
			String selfprojauth = userInfo.getSelfprojauth();
			if(selfprojauth.equals("0")) {
				selfprojauth = "否";
			}else {
				selfprojauth = "是";
			}
			// 具体班车信息
			String descn = userInfo.getDescn();
			if (selfprojauth.equals("否"))
				descn = "";
			// 时间
			Date ctime = userInfo.getCtime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
			String dateString = formatter.format(ctime);
			// 将内容按顺序赋给对应的列对象
			row.createCell(0).setCellValue(realname);
			row.createCell(1).setCellValue(phone);
			row.createCell(2).setCellValue(moduleName);
			row.createCell(3).setCellValue(selfprojauth);
			row.createCell(4).setCellValue(descn);
			row.createCell(5).setCellValue(dateString);
		}
		return wb;
	}
}
