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
<!-- Custom styles for this template -->
<link href="${appRoot}/static/css/style.css" rel="stylesheet">
<link href="${appRoot}/static/css/style-responsive.css" rel="stylesheet" />
<title>${appTitle}</title>
<style type="text/css">
  @font-face {
    font-family: mFont;
    src: url('${appRoot}/static/fonts/MNR8102.ttf');
  }
</style>
</head>
<body>

	<section id="container" class="">
		<!--header start-->
		<%@ include file="/WEB-INF/jsp/inc/header.jsp"%>
		<!--header end-->

		<!--sidebar start-->
		<%@ include file="/WEB-INF/jsp/inc/sidebar.jsp"%>
		<!--sidebar end-->

		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
				<!-- page start-->
				<div class="row">
					<div class="col-lg-12">
						<section class="panel">
							<header class="panel-heading">角色管理 </header>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box"
											class="group-checkable" data-set="#sample_1 .checkboxes"
											value="" /></th>
										<th>角色名称</th>
										<th class="hidden-phone">拼音</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${roleList}" var="info" varStatus="s">
										<tr class="odd gradeX">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${info.id}" /></td>
											<td>${info.roleName}</td>
											<td class="hidden-phone">${info.pinyin}</td>
											<td class="hidden-phone">
												<button class="btn btn-primary btn-xs"
													onclick="doEdit('${info.id}','${info.roleName}','${info.pinyin}','${info.remark}')">
													<i class="icon-pencil"></i>
												</button> &nbsp;
												<button class="btn btn-danger btn-xs"
													onclick="doDel('${info.id}')">
													<i class="icon-trash"></i>
												</button> &nbsp; <a
												href="${appRoot}/roleItem/listItem?roleId=${info.id}&roleName=${info.roleName}&itemId=${itemId}&id=${positionId}">
													<button class="btn btn-danger btn-xs">
														<i class="icon-eye-open">查看角色权限</i>
													</button>
											</a>
											</td>
										</tr>
										<c:forEach items="${info.roleList}" var="role" varStatus="s">
                    <tr class="odd gradeX">
                      <td><input type="checkbox" name="box" class="checkboxes"
                        value="${role.id}" /></td>
                      <td>----${role.roleName}</td>
                      <td class="hidden-phone">${role.pinyin}</td>
                      <td class="hidden-phone">${role.languageName }</td>
                      <td class="hidden-phone">
                        <button class="btn btn-primary btn-xs"
                          onclick="doEdit('${role.id}','${role.roleName}','${role.pinyin}','${role.remark}','${role.languageId }','${role.languages }')">
                          <i class="icon-pencil"></i>
                        </button> &nbsp;
                        <button class="btn btn-danger btn-xs"
                          onclick="doDel('${role.id}')">
                          <i class="icon-trash"></i>
                        </button> &nbsp; <a
                        href="${appRoot}/roleItem/listItem?roleId=${role.id}&roleName=${role.roleName}&itemId=${itemId}&id=${positionId}">
                          <button class="btn btn-danger btn-xs">
                            <i class="icon-eye-open">查看角色权限</i>
                          </button>
                      </a>
                      </td>
                    </tr>
                  </c:forEach>
									</c:forEach>
								</tbody>
							</table>
						</section>
					</div>
				</div>
				<!-- page end-->
			</section>
		</section>

		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">添加角色</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="roleForm"
							name="roleForm">
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">角色名称<font
									style="color: red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="roleName"
										name="roleName" placeholder="请输入角色名称">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">角色拼音<font
									style="color: red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="pinyin"
										name="pinyin" placeholder="请输入角色拼音">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">备注<font
									style="color: red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="remark"
										name="remark" placeholder="请输入备注">
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
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</section>



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

	<div class="panel-body">
		<!-- Modal -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
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
						<button class="btn btn-warning" type="button" onclick="Del()">确定</button>
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
					确定重置角色密码？</br>重置后密码为:123456
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

	<form action="${appRoot}/role/del" method="post" id="deleForm"
		name="deleForm">
		<input type="hidden" name="byid" id="byid"> <input
			type="hidden" name="boxeditId" id="boxeditId">
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
		function doAdd() {
			//添加
			$("#editId").val('');
			$('#roleName').val('');
			$('#pinyin').val('');
			$('#remark').val('');
			var $modal = $('#myModal');
			$modal.modal();
		}

		function doEdit(id, roleName, pinyin, remark) {
			$('#editId').val(id);
      $('#roleName').val(roleName);
      $('#pinyin').val(pinyin);
      $('#remark').val(remark);
      var $modal = $('#myModal');
      $('#modal-title').text("修改角色信息");
      $modal.modal();
		}

		function submitData() {
			var result = yanZheng();
			if(!result){
				return;
			}
			//开始提交
			$.ajax({
        type : 'post',
        data : $('#roleForm').serialize(),
        url : '${appRoot}/role/addAndUpdate',
        dataType : 'json',
        success : function(data) {
          if (data.msg == "iscz") {
            windowShow("该角色名称已存在!请重新输入角色名称", "");
            return false;
          } else if (data.msg == "isc") {
            $('#myModal').modal('hide');
            windowShow("添加角色成功", "");
            location.reload();
          } else if (data.msg == "isupdate") {
            $('#myModal').modal('hide');
            windowShow("修改角色成功", "");
            location.reload();
          } else {
            $('#myModal').modal('hide');
            windowShow("操作失败", "");
          }
        },
        error : function(data) {
          windowShow("操作失败", "");
        }
      });
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

		function doDel(id) {
			var $modal = $('#myModal1');
			$("#byid").val(id);
			$modal.modal();
		}

		function Del() {
			$("#deleForm").submit();
		}

		function Delete() {
			$("#deleForm").submit();
		}
		
		$(function(){
			console.log('${roleList}')
			//addLanguage();
		})
		
		//验证
		function yanZheng(){
			var result = true;
      var result1 = true;
			if ($('#roleName').val() == null || $('#roleName').val() == '') {
        windowShow("角色名称不能为空", "");
        result = false;
      }
      if ($('#pinyin').val() == null || $('#pinyin').val() == '') {
        windowShow("角色拼音不能为空", "");
        result = false;
      }
      if(!result1)
    	  windowShow("角色语言不允许为空，请仔细检查！", "");
      return result && result1;
		}
	</script>
</body>
</html>
