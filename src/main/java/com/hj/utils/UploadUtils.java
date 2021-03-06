package com.hj.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

// 	 				 _ooOoo_ 
//    			    o8888888o 
//    				88" . "88 
//     	 			(| -_- |) 
//      			 O\ = /O 
//	 			 ____/`---*\____ 
//				 . * \\| |// `. 
//				/ \\||| : |||// \ 
//			  / _||||| -:- |||||- \ 
//				| | \\\ - /// | | 
//			  | \_| **\---/** | | 
//			  \  .-\__ `-` ___/-. / 
//			___`. .* /--.--\ `. . __ 
//		 ."" *< `.___\_<|>_/___.* >*"". 
//  	| | : `- \`.;`\ _ /`;.`/ - ` : | | 
//        \ \ `-. \_ __\ /__ _/ .-` / / 
//======`-.____`-.___\_____/___.-`____.-*====== 
//					 `=---=* 
//
//............................................. 
//			佛祖保佑 				 永无BUG 
//				佛曰:  
//				写字楼里写字间，写字间里程序员；  
//				程序人员写程序，又拿程序换酒钱。  
//				酒醒只在网上坐，酒醉还来网下眠；  
//				酒醉酒醒日复日，网上网下年复年。  
//				但愿老死电脑间，不愿鞠躬老板前；  
//				奔驰宝马贵者趣，公交自行程序员。  
//				别人笑我忒疯癫，我笑自己命太贱；  
//				不见满街漂亮妹，哪个归得程序员？ 

/**
 * 
 * @ClassName: UploadUtils
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 王志勇
 * @date 2016年5月17日 上午10:02:09
 *
 */
public class UploadUtils {

	/**
	 * @param place
	 *          要储存的文件所在的文件夹
	 * @param file
	 *          上传的文件
	 * @param request
	 * @param filePath
	 * @return 数据库储存的位置，用于读取文件
	 */
	public static Map<String, Object> upload(MultipartFile file, HttpServletRequest request, String filePath) {
		Map<String, Object> mapData = new HashMap<String, Object>();
		try {
			if (file != null && file.getSize() > 0) {
				String oldname = file.getOriginalFilename(); // 得到上传时的文件名
				int i = oldname.lastIndexOf(".");
				String fileName = System.currentTimeMillis() + oldname.trim().substring(i, oldname.length());
				if (StringUtils.isEmpty(fileName))
					filePath = Configurations.getFileRepository();
				FileUtils.writeByteArrayToFile(new File(filePath, fileName), file.getBytes());
				mapData.put("fileName", fileName);
				mapData.put("filePath", filePath);
			} else {
				new RuntimeException("上传参数错误，请检查!");
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapData;
	}

}
