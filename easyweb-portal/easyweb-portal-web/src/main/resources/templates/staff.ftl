<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">

<title>主页</title>
<#include "/include/header.ftl">
<script type="text/javascript">
	window.modulus    = '${result.modulus%}';
	window.exponent   = '${result.exponent}';
	';
</script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox ">
					<div class="ibox-content">
						<form role="form" class="form-inline" id="queryForm"
							onsubmit="return false;">
							<div class="form-group">
								<select id="statusQuery" name="params['status']"
									class="input-sm form-control input-s-sm inline">
									<option value="">选择状态</option>
									<option value="NORMAL">正常</option>
									<option value="LOCKED">锁定</option>
									<option value="INITIAL">初创</option>
									<option value="PASSWORD_EXPIRED">密码过期</option>
								</select>
							</div>
							<div class="form-group">
								<input id="searchText" name="params['keyword']" type="text"
									class="input-sm form-control" placeholder="用户名/真实姓名" />
							</div>
							<button type="button" id="seachBtn"
								class="btn btn-sm btn-primary">
								查找<i class="fa fa-search icon-on-right bigger-110"></i>
							</button>
							<button class="btn btn-sm" type="button" id="resetBtn">
								<i class="fa fa-undo bigger-110"></i> 重 置
							</button>
						</form>
						<hr>
						<div class="jqGrid_wrapper">
							<table id="staff-table"></table>
							<div id="grid-pager"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!--  修改用户  -->
	<div id="editstaff-modal" class="modal" tabindex="-1" role="dialog"
		role="dialog">
		<div class="modal-dialog">
			<form id="editForm" class="form-horizontal" role="form"
				onsubmit="return false;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							&times;</button>
						<h4 class="blue bigger">修改用户</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right"
								for="updLoginName"> 用户名: </label>

							<div class="col-sm-5">
								<input type="hidden" name="staffId" /> <input type="text"
									name="loginName" id="updLoginName" readonly="true"
									class="form-control input-sm" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right"
								for="editForm-realName"><span class="red">*</span>真实姓名:
							</label>

							<div class="col-sm-5">
								<input type="text" name="realName" id="editForm-realName"
									placeholder="" class="form-control input-sm" />

							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3 no-padding-right">性别：</label>
							<div class="col-sm-5">
								<label for="editForm-male"> <input type="radio"
									value="MALE" name="sex" id="editForm-male" class="ace" /> <span
									class="lbl"> 男 </span>
								</label> <label for="editForm-female"> <input type="radio"
									value="FEMALE" name="sex" id="editForm-female" class="ace" />
									<span class="lbl"> 女 </span>
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right"
								for="editForm-mobile"><span class="red">*</span>手机: </label>

							<div class="col-sm-5">
								<input type="text" name="mobile" id="editForm-mobile"
									placeholder="" class="form-control input-sm" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right"
								for="editForm-telephone"> 固定电话: </label>

							<div class="col-sm-5">
								<input type="text" name="telephone" id="editForm-telephone"
									placeholder="" class="form-control input-sm" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right"
								for="editForm-email"><span class="red">*</span>邮箱: </label>

							<div class="col-sm-5">
								<input type="text" name="email" id="editForm-email"
									placeholder="" class="form-control input-sm" />
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<button class="btn btn-sm" type="button" data-dismiss="modal">
							<i class="ace-icon fa fa-times"></i> 取消
						</button>
						<button id="reset-password-btn" class="btn btn-sm btn-primary"
							type="button" data-dismiss="modal" style="display: none"
							permCheck="auth_admin_sys_staff_staffManage,RESETPWD,hide">
							<i class="ace-icon glyphicon glyphicon-refresh"></i> 重置密码
						</button>
						<button type="submit" class="btn btn-sm btn-success">
							<i class="ace-icon fa fa-check"></i> 保存
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!--  重置密码  -->
	<div id="resetpassword-modal" class="modal" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<form class="form-horizontal" role="form" onsubmit="return false;">
					<input type="hidden" name="loginName" id="loginName2" />
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							&times;</button>
						<h4 class="blue bigger">重置用户密码</h4>
					</div>

					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right"
								for="password2"><span class="red">*</span>新密码: </label>

							<div class="col-sm-5">
								<input type="password" name="password" id="password2"
									placeholder="" class="form-control input-sm" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right"
								for="pwdConfirm2"><span class="red">*</span>密码确认: </label>

							<div class="col-sm-5">
								<input type="password" name="pwdConfirm" id="pwdConfirm2"
									placeholder="" class="form-control input-sm" />
							</div>
						</div>

					</div>

					<div class="modal-footer">
						<button id="reset-cancel-btn" class="btn btn-sm" type="button">
							<i class="ace-icon fa fa-times"></i> 取消
						</button>
						<button class="btn btn-sm btn-success" type="submit">
							<i class="ace-icon fa fa-check"></i> 确定
						</button>
					</div>
				</form>
			</div>

		</div>
	</div>

	<!-- 添加角色窗口 -->
	<div id="role-to-staff" class="modal" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content col-xs-8">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						&times;</button>
					<h4 class="blue bigger">
						给用户【<span name="loginName" class="green txt-overflow-hidden"
							style="max-width: 120px;"></span>】分配角色
					</h4>
				</div>

				<div class="modal-body">
					<input type="hidden" name="staffId" />
					<div class="jqGrid_wrapper">
						<table id="role-table"></table>
					</div>
				</div>

			</div>
		</div>
	</div>
	<#include "/include/footer.ftl">
	<script type="text/javascript" src="${ctx}/js/crypt/jsbn.js"></script>
	<script type="text/javascript" src="${ctx}/js/crypt/prng4.js"></script>
	<script type="text/javascript" src="${ctx}/js/crypt/rng.js"></script>
	<script type="text/javascript" src="${ctx}/js/crypt/rsa.js"></script>
	<script type="text/javascript" src="${ctx}/js/crypt/base64.js"></script>
	<script type="text/javascript"
		src="${ctx}/servlet/facade.script?action=getLoginInfo"></script>
	<script type="text/javascript" src="${ctx}/js/views/staff.js"></script>
</body>
</html>