package com.hj.wxmp.mobile.mode;
import com.hj.wxmp.mobile.entity.UserInfo;


/**
 * 
* @ClassName: UserVO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Wangzhiyong
* @date 2016年5月26日 上午10:19:57
*
 */
public class UserVO extends UserInfo{
	
		private String id;

	    private String honor;

	    private String teachRange;

	    private String brief;

	    private String imgUrl;

	    private String school;

	    private Integer fansCount;
	    
	    private String sendTime;
	    
	    private String content;

	    
	    public String getSendTime() {
			return sendTime;
		}

		public void setSendTime(String sendTime) {
			this.sendTime = sendTime;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id == null ? null : id.trim();
	    }

	    public String getHonor() {
	        return honor;
	    }

	    public void setHonor(String honor) {
	        this.honor = honor == null ? null : honor.trim();
	    }

	    public String getTeachRange() {
	        return teachRange;
	    }

	    public void setTeachRange(String teachRange) {
	        this.teachRange = teachRange == null ? null : teachRange.trim();
	    }

	    public String getBrief() {
	        return brief;
	    }

	    public void setBrief(String brief) {
	        this.brief = brief == null ? null : brief.trim();
	    }

	    public String getImgUrl() {
	        return imgUrl;
	    }

	    public void setImgUrl(String imgUrl) {
	        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
	    }

	    public String getSchool() {
	        return school;
	    }

	    public void setSchool(String school) {
	        this.school = school == null ? null : school.trim();
	    }

	    public Integer getFansCount() {
	        return fansCount;
	    }

	    public void setFansCount(Integer fansCount) {
	        this.fansCount = fansCount;
	    }
}
