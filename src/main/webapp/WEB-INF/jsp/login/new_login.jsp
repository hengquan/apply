<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<!DOCTYPE html>
<html lang="zh-Hans-CN">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
	<title>后台登录</title>
	<link rel="stylesheet" href="${appRoot}/static/new/css/bootstrap.font.min.css"/>
	
	<link rel="stylesheet" href="${appRoot}/static/new/lib/swiper.min.css"/>
	<link rel="stylesheet" href="${appRoot}/static/new/css/style.css"/>
</head>
<body>
<div class="swiper-container log-bg">
	<div class="swiper-wrapper">
		<div class="swiper-slide" style="background-image:url(${appRoot}/static/new/img/bg1.jpg)"></div>
		<div class="swiper-slide" style="background-image:url(${appRoot}/static/new/img/bg2.jpg)"></div>
		<div class="swiper-slide" style="background-image:url(${appRoot}/static/new/img/bg3.jpg)"></div>
	</div>
</div>

<div class="loginbox">
	<div class="carent-logo" style="font-family:楷体;font-size:45px;">
		<b>活动报名管理系统</b>
	</div>
	<div class="login-content" style="margin-top:-20px;">
		<form action="${appRoot}/login.ky" id="myForm" name="myForm" method="post">
			
			<c:if test="${loginFaild !=''}">
				<div style="text-align: center;color: #FF5722;" id="wxmp-info-div">
					<label style="font-weight: 200 !important;" id="wxmp-info-text">${loginFaild}</label>
				</div>
			</c:if>
			<div class="form-group">
				<i class="fa fa-user"></i>
				<input type="text" class="form-control ms-inp" placeholder="请输入用户名" name="userName">
			</div>
			<div class="form-group">
				<i class="fa fa-unlock-alt"></i>
				<input type="password" class="form-control ms-inp" placeholder="请输入密码" name="password">
			</div>
			<button type="submit" class="btn btn-login">登录</button>
			<div class="checkbox">
				<label>
					<input type="checkbox"> 记住我
				</label>
			</div>
		</form>
	</div>
</div>

<script src="${appRoot}/static/new/js/jquery.min.js"></script>
<script src="${appRoot}/static/new/js/bootstrap.min.js"></script>
<script src="${appRoot}/static/new/js/main.js"></script>

<!-- Swiper JS -->
<script src="${appRoot}/static/new/lib/swiper.jquery.min.js"></script>
<script>
	var swiper = new Swiper('.swiper-container', {
		effect: 'fade',
		autoplay: 4000
	});
	
	
	function doSubmit(){
  		if(isValidate()){
  			$("#myForm").submit();
  		}
  	}
  	
  	function isValidate(){
  		if(""==$('#userName').val().trim()){
  			showAlert("请输入用户名!");
  			return false;
  		}else if(""==$('#password').val().trim()){
  			showAlert("请输入密码!");
  			return false;
  		}
  		return true;
  	}
</script>
</body>
</html>