<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head_bootstrap.jsp"%>

<link
	href="${appRoot}/static/assets/jquery-file-upload/css/jquery.fileupload-ui.css"
	rel="stylesheet" type="text/css">
<link href="${appRoot}/static/css/style.css" rel="stylesheet">
<link href="${appRoot}/static/css/style-responsive.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${appRoot}/static/js/css/layui.css">

<script src="${appRoot}/static/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="${appRoot}/static/js/layui.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${appRoot}/static/js/layui.js"></script>
<title>${appTitle}</title>
</head>
<body>
	<section id="container" class="">
		<%@ include file="/WEB-INF/jsp/inc/header.jsp"%>
		<%@ include file="/WEB-INF/jsp/inc/sidebar.jsp"%>
		<%-- <section style="position: fixed;margin-top:70px;margin-left:190px;">
			<%@ include file="/WEB-INF/jsp/inc/zTree.jsp"%>
		</section> --%>
		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
				<!-- page start-->
				<div class="row">
					<div class="col-lg-12">
						<section class="panel">
							<header class="panel-heading">用户列表</header>
							<form action="${appRoot}/user/userList" method="post"
								id="selectCheckMessage">
								<input type="hidden" name="itemId" value="${itemId }"> 
                <input type="hidden" name="positionId" value="${positionId }">
								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<input type="text" class="btn"
										style="width: 500px; border: 1px solid #ddd; text-align: left;"
										placeholder="请输入需要查询的用户姓名/电话号码" name="userName"
										value="${userName }"><span>
										<button class="btn sr-btn-imp" style="float: right"
											onclick="seeAllMsg()">
											<i class="icon-search"></i>
										</button>
									</span>
								</div>
								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doRefresh();" class="btn mini btn-white"
										title="刷新"><i class="icon-refresh"></i></a>
								</div>
								<!-- <div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doAdd();" class="btn mini btn-white"><i
										class="icon-plus"></i></a>
								</div> -->

								<input type="hidden" value="${nowPage}" id="nowPageNumber"
									name="nowPage"> <input type="hidden"
									value="${totalPageNum }">
							</form>

							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box"
											class="group-checkable" data-set="#sample_1 .checkboxes"
											value="" /></th>
										<th class="hidden-phone">姓名</th>
										<th class="hidden-phone">电话</th>
										<th class="hidden-phone">所属部门</th>
										<th class="hidden-phone">自否自驾</th>
										<th class="hidden-phone">班车信息</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${userList}" var="u" varStatus="s">
											<tr class="odd gradeX">
												<td><input type="checkbox" name="box" class="checkboxes" value="${u.id}" /></td>
												<td class="hidden-phone">${u.realname}</td>
												<td class="hidden-phone">${u.phone}</td>
												<td class="hidden-phone">
													<c:if test="${empty u.moduleName}">${u.moduleId }</c:if>
													<c:if test="${!empty u.moduleName}">${u.moduleName }</c:if>
												</td>
												<td class="hidden-phone">
													<c:if test="${u.selfprojauth eq '0' }">非自驾</c:if>
													<c:if test="${u.selfprojauth eq '1' }">自驾</c:if>
												</td>
												<td class="hidden-phone">${u.descn}</td>
											</tr>
									</c:forEach>
								</tbody>
							</table>
							<nav class="clearfix">
								<ul class="pagination pull-left">
									<li><div class="dataTables_info" id="sample_1_info">共${totalPageNum }
											页,当前为第${nowPage}页</div></li>
								</ul>
								<ul class="pagination pull-right">
									<li><a href="javascript:doPanation(1)">首页</a></li>
									<li><a
										href="javascript:doPanation(${nowPage - 1 < 1 ? 1 : nowPage - 1})"
										aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
									</a></li>
									<c:forEach begin="${nowPage - 5 > 0 ? nowPage - 5 : 1 }"
										end="${nowPage + 5 > totalPageNum ? totalPageNum : nowPage + 5}"
										var="t">
										<li <c:if test="${nowPage == t}">class="act"</c:if>><a
											href="javascript:doPanation(${t})">${t}</a></li>
									</c:forEach>
									<li><a
										href="javascript:doPanation(${nowPage + 1 > totalPageNum ? totalPageNum : nowPage + 1})"
										aria-label="Next"> <span aria-hidden="true">&raquo;</span>
									</a></li>
									<li><a href="javascript:doPanation(${totalPageNum})">末页</a></li>
								</ul>
							</nav>
						</section>
					</div>
				</div>
				<!-- page end-->
			</section>
		</section>
	</section>



	<div class="modal fade" id="addPage" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="userTitle">添加用户</h4>
				</div>
				<div class="modal-body">
					<form action="${appRoot}/user/save" method="post"
					 class="form-horizontal" enctype="multipart/form-data" id="addUserData" name="addUserData">
						<input type="hidden" name="id" id="userinfoId">
						<input type="hidden" name="itemId" value="${itemId }"> 
            <input type="hidden" name="positionId" value="${positionId }">
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">登入帐号<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="loginname"
									name="loginname">
							</div>
						</div>
						<div class="form-group">
              <label class="col-lg-2 control-label pd-r5">用户权限<font
                style="color: red;"></font></label>
              <div class="col-lg-10">
                <select class="form-control" id="userRoleId" name="userRoleId">
                </select>
              </div>
            </div>
            <div style="form-group">
              <label class="col-lg-2 control-label pd-r5" style="margin-left: -15px;">用户头像<font
                style="color: red;"></font></label> 
              <div class="col-lg-10">
                <div>
	                <img class="my-img form-control btn" id="imgDJZS"
	                 style="float:left;width: 219px; height: 150px;margin-left: -6px;border:0px;margin-bottom: 14px;" onerror="excptionUrl(this)" />
                </div>
                <div id="titleDJZS" style="float: left;margin-top:122px;"><b>上传100*200缩略图,1M以内</b></div>
              </div>
              <input type="hidden" id="headimgurl" name="headimgurl" />
            </div>
            <div style="clear:both"></div>
            <div class="form-group">
              <label class="col-lg-2 control-label pd-r5">用户姓名<font
                style="color: red;"></font></label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="realname"
                  name="realname">
              </div>
            </div>
            <div class="form-group">
              <label class="col-lg-2 control-label pd-r5">用户性别<font
                style="color: red;"></font></label>
              <div class="col-lg-10">
                <select class="form-control" id="sex" name="sex">
                  <option value="1">男</option>
                  <option value="2">女</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-lg-2 control-label pd-r5">用户年龄<font
                style="color: red;"></font></label>
              <div class="col-lg-10">
                <input type="count" class="form-control" id="age"
                  name="age">
              </div>
            </div>
            <div class="form-group">
              <label class="col-lg-2 control-label pd-r5">联系方式<font
                style="color: red;"></font></label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="phone"
                  name="phone">
              </div>
            </div>
            <div class="form-group">
              <label class="col-lg-2 control-label pd-r5">所在地区<font
                style="color: red;"></font></label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="district"
                  name="district">
              </div>
            </div>
            <div class="form-group">
              <label class="col-lg-2 control-label pd-r5">详细地址<font
                style="color: red;"></font></label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="address"
                  name="address">
              </div>
            </div>
            <div class="form-group">
              <label class="col-lg-2 control-label pd-r5">区域语言<font
                style="color: red;"></font></label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="language"
                  name="language">
              </div>
            </div>
            <div class="form-group">
              <label class="col-lg-2 control-label pd-r5">相关项目<font
                style="color: red;"></font></label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="selfprojauth"
                  name="selfprojauth">
              </div>
            </div>
            <div class="form-group">
              <label class="col-lg-2 control-label pd-r5">用户描述<font
                style="color: red;"></font></label>
              <div class="col-lg-10">
                <textarea rows="5" cols="60" class="form-control" id="descn"
                  name="descn"></textarea>
              </div>
            </div>
            <div class="form-group">
              <label class="col-lg-2 control-label pd-r5">备注信息<font
                style="color: red;"></font></label>
              <div class="col-lg-10">
                <textarea rows="5" cols="60" class="form-control" id="remark"
                  name="remark"></textarea>
              </div>
            </div>
						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">
								<button type="button" onclick="submitData();"
									class="btn btn-send">提交</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>




	<!-- Modal -->
	<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">删除警告</h4>
				</div>
				<div class="modal-body">确定删除？</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button"
						id="quxiao">取消</button>
					<button class="btn btn-warning" type="button" onclick="Delete()">确定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->



	<!-- Modal -->
	<div class="modal fade" id="myModal3" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">降级警告</h4>
				</div>
				<div class="modal-body">是否确定要将选中用户降级为普通用户？</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button"
						id="quxiao">取消</button>
					<button class="btn btn-warning" type="button" onclick="queDing()">确定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->


	<div class="panel-body">
		<!-- Modal -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">设置专家警告</h4>
					</div>
					<div class="modal-body">是否确定要将选中用户设置成专家？</div>
					<div class="modal-body">(请确保该用户已有手机号，如果没有则不能成为专家用户)</div>
					<div class="modal-footer">
						<button data-dismiss="modal" class="btn btn-default" type="button"
							id="quxiao">取消</button>
						<button class="btn btn-warning" type="button"
							onclick="setExpert()">确定</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<div class="modal fade" id="myModalUpdatePwd" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">重置警告</h4>
				</div>
				<div class="modal-body">
					确定重置管理员密码？</br>重置后密码为:123456
				</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button"
						id="quxiao">取消</button>
					<button class="btn btn-warning" type="button"
						onclick="resetPassword()">确定</button>
				</div>
			</div>
		</div>
	</div>

	<form action="${appRoot}/user/del" method="post" id="deleForm"
		name="deleForm">
		<input type="hidden" name="itemId" value="${itemId }"> 
    <input type="hidden" name="positionId" value="${positionId }">
		<input type="hidden" name="byid" id="byid"> 
		<input type="hidden" name="boxeditId" id="boxeditId">
	</form>


	<form action="${appRoot}/user/setExpert" method="post" id="checkExpert"
		name="checkExpert">
		<input type="hidden" name="setExpertId" id="setExpertId"> <input
			type="hidden" name="setExpertIds" id="setExpertIds">
	</form>


	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>

	<script src="${appRoot}/static/js/jquery.sparkline.js"
		type="text/javascript"></script>

	<script type="text/javascript"
		src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<script src="${appRoot}/static/js/dynamic-table.js"></script>
	<script src="${appRoot}/static/js/dialog_alert.js"></script>
	<script type="text/javascript">
		//选择不同的页数
		function doPanation(number) {
			$("#nowPageNumber").val(number);
			seeAllMsg();
		}

		function seeAllMsg(){
			$("#selectCheckMessage").submit();
		}
		
		$(function() {
			$('.input-group').hide();
			$('#sample_1_info').hide();
			$('.dataTables_paginate').hide();
			$("#sample_1_length .form-control").hide();
			$("#sample_1_length .js-add").hide();
			$("#sample_1_length .js-ref").hide();
			$("#sample_1_length .js-del").hide();
		});

		function doRefresh() {
			location.reload();
		}

		function checkbox() {
			var str = document.getElementsByName("box");
			var objarray = str.length;
			var chestr = "";
			var jy = false;
			for (i = 0; i < objarray; i++) {
				if (str[i].checked == true) {
					jy = true;
					chestr += str[i].value + ",";
				}
			}
			if (!jy) {
				windowShow("请选择要删除的数据！")
				$('#quxiao').click();
				return false;
			} else {
				$("#boxeditId").val(chestr);
				return true;
			}
		}

		function doDelete() {
			var flag = checkbox();
			if (flag) {
				var $modal = $('#myModal2');
				$modal.modal();
			}
		}
		
		function delOne(id){
			$("#boxeditId").val(id);
			var $modal = $('#myModal2');
      $modal.modal();
		}

		function Del() {
			$("#deleForm").submit();
		}

		function checkboxUser() {
			var str = document.getElementsByName("box");
			var objarray = str.length;
			var chestr = "";
			var jy = false;
			for (i = 0; i < objarray; i++) {
				if (str[i].checked == true) {
					jy = true;
					chestr += str[i].value + ",";
				}
			}
			if (!jy) {
				windowShow("请选择要降级的用户！")
				$('#quxiao').click();
				return false;
			} else {
				$("#boxUserId").val(chestr);
				return true;
			}
		}

		function setExpert() {
			$("#checkExpert").submit();
		}

		function Delete() {
			$("#deleForm").submit();
		}

		//弹出添加用户页面
		function doAdd() {
			document.getElementById("addUserData").reset();
			$("#userinfoId").val("");
			$("#imgDJZS").attr("src",'${appRoot}/static/img/zanwu1.png');
			$("#userTitle").html("添加用户信息");
		}
		//提交添加用户请求
		function submitData(){
			$("#addUserData").submit();
		}
		//打开修改页面
		function doUpdate(id,rolename){
			document.getElementById("addUserData").reset();
			$("#imgDJZS").attr("src",'');
			$("#userTitle").html("修改用户信息");
			$.ajax({
        type : 'post',
        data : {"id":id},
        url : '${appRoot}/user/getData',
        dataType : 'json',
        success : function(data) {
          if (data.msg == 0) {
            var userInfo = data.Data;
            console.log(userInfo);
            //填充信息
            $("#userinfoId").val(userInfo.id);
            $("#loginname").val(userInfo.loginname);
            $("#realname").val(userInfo.realname);
            $("#age").val(userInfo.age);
            $("#sex").val(userInfo.sex);
            $("#phone").val(userInfo.phone);
            $("#district").val(userInfo.district);
            $("#address").val(userInfo.address);
            $("#language").val(userInfo.language);
            $("#descn").text(userInfo.descn);
            $("#remark").text(userInfo.remark);
            $("#selfprojauth").val(userInfo.selfprojauth);
            var headimgurl = userInfo.headimgurl;
            if(headimgurl !=null && headimgurl != ''){
            	$("#imgDJZS").attr("src",headimgurl);
            }
          } else {
            windowShow("获取用户信息失败", "");
          }
        }
      });
			//填充
      addRolePage(rolename);
		}
		//填充权限页面
		function addRolePage(rolename){
			$.ajax({
        type : 'post',
        data : {"type":"1"},
        url : '${appRoot}/role/getAllList',
        dataType : 'json',
        success : function(data) {
          if (data.msg == 0) {
            var html;
            var roleList = data.roleList;
            for(var i=0;i<roleList.length;i++){
            	if(roleList[i].roleName == rolename){
            		html += '<option value="'+ roleList[i].id +'" selected>'+ roleList[i].roleName +'</option>'
            	}else{
                html += '<option value="'+ roleList[i].id +'">'+ roleList[i].roleName +'</option>'
            	}
            }
            $("#userRoleId").html(html);
            var $modal = $('#addPage');
            $modal.modal();
          } else if(data.msg == 1){
            windowShow("您不是该站点负责人", "");
          }else{
            windowShow("获取权限列表失败", "");
          }
        }
      });
		}
		
    //layui处理文件上传
    layui.use(['form', 'upload'], function(){
       form = layui.form;
       upload = layui.upload;
       initImgForGroup();
       form.render();
     });
        
    function initImgForGroup() {
      upload.render({
        elem: '#imgDJZS,#titleDJZS', //绑定元素
        url: '${appRoot}/apply/uploadFile' ,//上传接口
        size:'1024',
        before: function(obj){
          console.log(obj);
          //预读本地文件示例，不支持ie8
          obj.preview(function(index, file, result){
            $('#imgDJZS').attr('src', result); //图片链接（base64）
          });
          $("#imgDJZS").attr("title", "点击更换缩略图");
        },
        data:{"tableName":"hl_kj","fileType":"主图片"},
        done: function(res) {
        console.log(res);
          var fileName = res.fileName;
          $("#headimgurl").val(fileName);
        }
      });
    }
    
    function excptionUrl(obj){
    	var obj = $(obj);
    	obj.attr("src",'${appRoot}/static/img/zanwu1.png');
    }
	</script>
</body>
</html>

