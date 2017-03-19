<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">

<title>主页</title>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<style type="text/css">
.tree {
	padding-left: 9px;
	overflow-x: hidden;
	overflow-y: auto;
	position: relative;
}

.tree:before {
	display: inline-block;
	content: "";
	position: absolute;
	top: -20px;
	bottom: 16px;
	left: 0;
	z-index: 1;
	border: 1px dotted #67b2dd;
	border-width: 0 0 0 1px;
}

.tree .tree-folder {
	width: auto;
	min-height: 20px;
	cursor: pointer;
}

.tree .tree-folder .tree-folder-header {
	position: relative;
	height: 20px;
	line-height: 20px;
}

.tree .tree-folder .tree-folder-header:hover {
	background-color: #F0F7FC;
}

.tree .tree-folder .tree-folder-header .tree-folder-name, .tree .tree-item .tree-item-name
	{
	display: inline;
	z-index: 2;
}

.tree .tree-folder .tree-folder-header>.ace-icon:first-child, .tree .tree-item>.ace-icon:first-child
	{
	display: inline-block;
	position: relative;
	z-index: 2;
	top: -1px;
}

.tree .tree-folder .tree-folder-header .tree-folder-name {
	margin-left: 2px;
}

.tree .tree-folder .tree-folder-header>.ace-icon:first-child {
	margin: -2px 0 0 -2px;
}

.tree .tree-folder:last-child:after {
	display: inline-block;
	content: "";
	position: absolute;
	z-index: 1;
	top: 15px;
	bottom: 0;
	left: -15px;
	border-left: 1px solid #FFF;
}

.tree .tree-folder .tree-folder-content {
	margin-left: 23px;
	position: relative;
}

.tree .tree-folder .tree-folder-content:before {
	display: inline-block;
	content: "";
	position: absolute;
	z-index: 1;
	top: -14px;
	bottom: 16px;
	left: -14px;
	border: 1px dotted #67b2dd;
	border-width: 0 0 0 1px;
}

.tree .tree-item {
	position: relative;
	height: 20px;
	line-height: 20px;
	cursor: pointer;
}

.tree .tree-item:hover {
	background-color: #F0F7FC;
}

.tree .tree-item .tree-item-name {
	margin-left: 3px;
}

.tree .tree-item .tree-item-name>.ace-icon:first-child {
	margin-right: 3px;
}

.tree .tree-item>.ace-icon:first-child {
	margin-top: -1px;
}

.tree .tree-folder, .tree .tree-item {
	position: relative;
}

.tree .tree-folder:before, .tree .tree-item:before {
	display: inline-block;
	content: "";
	position: absolute;
	top: 14px;
	left: -13px;
	width: 18px;
	height: 0;
	border-top: 1px dotted #67b2dd;
	z-index: 1;
}

.tree .tree-selected {
	background-color: rgba(98, 168, 209, 0.1);
	color: #6398B0;
}

.tree .tree-selected:hover {
	background-color: rgba(98, 168, 209, 0.1);
}

.tree .tree-item, .tree .tree-folder {
	border: 1px solid #FFF;
}

.tree .tree-folder .tree-folder-header {
	border-radius: 0;
}

.tree .tree-item, .tree .tree-folder .tree-folder-header {
	margin: 0;
	padding: 5px;
	color: #4D6878;
	-webkit-box-sizing: content-box;
	-moz-box-sizing: content-box;
	box-sizing: content-box;
}

.tree .tree-item>.ace-icon:first-child {
	color: #F9E8CE;
	width: 13px;
	height: 13px;
	line-height: 13px;
	font-size: 11px;
	text-align: center;
	border-radius: 3px;
	-webkit-box-sizing: content-box;
	-moz-box-sizing: content-box;
	box-sizing: content-box;
	background-color: #FAFAFA;
	border: 1px solid #CCC;
	box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.tree .tree-selected>.ace-icon:first-child {
	background-color: #F9A021;
	border-color: #F9A021;
	color: #FFF;
}

.tree .tree-plus.ace-icon:first-child, .tree .tree-minus.ace-icon:first-child
	{
	font-style: normal;
	border: 1px solid #DDD;
	vertical-align: middle;
	height: 11px;
	width: 11px;
	-webkit-box-sizing: content-box;
	-moz-box-sizing: content-box;
	box-sizing: content-box;
	text-align: center;
	border: 1px solid #8BAEBF;
	line-height: 10px;
	background-color: #FFF;
	position: relative;
	z-index: 1;
}

.tree .tree-plus.ace-icon:first-child:before, .tree .tree-minus.ace-icon:first-child:before
	{
	content: "";
	display: block;
	width: 7px;
	height: 0;
	border-top: 1px solid #4D6878;
	position: absolute;
	top: 5px;
	left: 2px;
}

.tree .tree-plus.ace-icon:first-child:after {
	content: "";
	display: block;
	height: 7px;
	width: 0;
	border-left: 1px solid #4D6878;
	position: absolute;
	top: 2px;
	left: 5px;
}

.tree .tree-unselectable .tree-item>.ace-icon:first-child {
	color: #5084A0;
	width: 13px;
	height: 13px;
	line-height: 13px;
	font-size: 10px;
	text-align: center;
	border-radius: 0;
	background-color: transparent;
	border: none;
	box-shadow: none;
}

.tree .ace-icon[class*="-down"] {
	transform: rotate(-45deg);
}

.tree .fa-spin {
	height: auto;
}

.tree .tree-loading {
	margin-left: 36px;
}

.tree img {
	display: inline;
	veritcal-align: middle;
}

.margin-left4 {
	margin-left: 4px;
}

.margin-right32 {
	margin-right: 51px;
}

.margin-left-32 {
	margin-left: -51px;
}
/*模拟对角线*/
.out {
	border-top: 120px #F5FFFA solid; /*上边框宽度等于表格第一行行高*/
	width: 0px; /*让容器宽度为0*/
	height: 0px; /*让容器高度为0*/
	border-left: 180px #F5F5DC solid; /*左边框宽度等于表格第一行第一格宽度*/
	position: relative; /*让里面的两个子容器绝对定位*/
}

.th_1 {
	width: 20px;
	height: 100px;
	text-align: center;
	overflow: hidden;
}

b {
	font-style: normal;
	display: block;
	position: absolute;
	top: -80px;
	left: -55px;
	width: 35px;
}

em {
	font-style: normal;
	display: block;
	position: absolute;
	top: -40px;
	left: -130px;
	width: 55x;
}

.t1 {
	background: #BDBABD;
}

.tree-folder {
	white-space: nowrap;
}
</style>

<script type="text/javascript"
	src="${ctx}/servlet/facade.script?action=listCity"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-4">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>组织列表</h5>
					</div>

					<div class="ibox-content">

						<div class="widget-box widget-color-blue2">
							<div class="widget-body">
								<div class="widget-main padding-8">
									<div id="tree1" class="tree"
										style="height: 522px; width: 100%; overflow: auto;"></div>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
			<div class="col-sm-8">
				<div class="ibox ">
					<div class="ibox-title">
						<h5 id="departmentName">
							<i class="ace-icon fa fa-arrow-left icon-on-left"></i>请先选择组织
						</h5>
					</div>

					<div class="ibox-content">
						<div class="widget-box widget-color-blue2">

							<div class="widget-body">
								<div class="widget-main">
									<div>
										<label for="form-field-8">组织角色</label> <input
											id="departmentId" type="hidden">
										<textarea id="dept-roles" class="form-control" rows="6"
											readonly=""></textarea>
									</div>
									<hr />
									<div>
										<label for="form-field-8">组织用户</label>
										<textarea id="dept-staffs" class="form-control" rows="6"
											readonly=""></textarea>
									</div>
									<hr>
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9" id="authBtnGrp"
											style="display: none">
											<button id="add-role-btn" class="btn btn-info" type="button"
												style="display: none"
												permCheck="auth_admin_sys_dept_deptManage,ROLE2DEPT,hide">
												<i class="ace-icon fa fa-users bigger-110"></i> 添加角色
											</button>
											&nbsp; &nbsp; &nbsp;
											<button class="btn btn-info" type="button" id="add-staff-btn"
												style="display: none"
												permCheck="auth_admin_sys_dept_deptManage,STAFF2DEPT,hide">
												<i class="ace-icon glyphicon glyphicon-user bigger-110"></i>
												添加用户
											</button>
											&nbsp; &nbsp; &nbsp;
											<button class="btn btn-info" type="button"
												id="auth-staff-btn" style="display: none"
												permCheck="auth_admin_sys_dept_deptManage,AUTH2STAFF,hide">
												<i class="ace-icon glyphicon glyphicon-lock bigger-110"></i>
												对成员授权
											</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--  新增部门  -->
	<div id="add-dept-modal" class="modal" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<form id="add-dept-form" class="form-horizontal" role="form"
				onsubmit="return false;">
				<input type="hidden" name="departmentId" /> <input type="hidden"
					name="parentId" /> <input type="hidden" name="others['roles']"
					id="adddept-roles" />
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="blue bigger">新增组织</h4>
					</div>

					<div class="modal-body">
						<div class="row">
							<div class="form-group">
								<label
									class="col-sm-3 control-label no-padding-right text-right"
									for="add-dept-form-departmentName"><span class="red">*</span>组织名称:</label>

								<div class="col-sm-6">
									<input type="text" name="departmentName"
										id="add-dept-form-departmentName" placeholder=""
										class="form-control input-sm" />
								</div>
							</div>
							<div class="form-group">
								<label
									class="col-sm-3 control-label no-padding-right text-right"
									for="add-dept-form-departmentDesc">组织描述:</label>

								<div class="col-sm-6">
									<textarea name="departmentDesc"
										id="add-dept-form-departmentDesc" rows="3"
										class="form-control input-sm"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label
									class="col-sm-3 control-label no-padding-right text-right"
									for="add-dept-form-departmentDesc"><span class="red">*</span>管理域:</label>
								<div class="col-sm-6">
									<select name="domain" class="form-control input-sm">
										<option value="">--请选择--</option>
										<option value="SYS_ADMIN">管理员</option>
										<option value="SP">商户</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label
									class="col-sm-3 control-label no-padding-right text-right"
									for="add-dept-form-email">Email:</label>

								<div class="col-sm-6">
									<input type="text" name="email" id="add-dept-form-email"
										placeholder="" class="form-control input-sm" />
								</div>
							</div>
							<div class="form-group">
								<label
									class="col-sm-3 control-label no-padding-right text-right"
									for="add-dept-form-address">地址:</label>

								<div class="col-sm-6">
									<input type="text" name="address" id="add-dept-form-address"
										placeholder="" class="form-control input-sm" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<h5 class="header smaller lighter blue">角色分配</h5>
								<div class="form-group">
									<div class="col-sm-12" id="add-dept-form-roleList"></div>
								</div>
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<button class="btn btn-sm" type="button" data-dismiss="modal">
							<i class="ace-icon fa fa-times"></i> 取消
						</button>

						<button id="save-dept-btn" type="submit"
							class="btn btn-sm btn-success">
							<i class="ace-icon fa fa-check"></i> 保存
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!--  新增用户到组织  -->
	<div id="add-staff-modal" class="modal" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<form class="form-horizontal" role="form" onsubmit="return false;"
				id="add-staff-form">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="blue bigger">选择用户</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<div class="col-sm-4">
								<label> <input type="radio" value="0" name="searchType"
									id="searchType0" class="ace" /> <span class="lbl">所有可选用户</span>
								</label> <label> <input type="radio" value="1" name="searchType"
									id="searchType1" class="ace" checked="checked" /> <span
									class="lbl">检索</span>
								</label>
							</div>
							<div class="col-sm-8">
								<div class="row" id="staff-list-seach-area">
									<div class="col-sm-8">
										<input type="text" placeholder="用户名/真实姓名"
											class="form-control input-sm" id="searchText2"
											name="searchtxt">
									</div>
									<div class="col-sm-4">
										<button class="btn btn-purple btn-sm" type="button"
											id="staff-list-seach-btn">
											查找 <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
										</button>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-5">
								<label for="staffList">用户列表</label> <select multiple="multiple"
									id="staff-list" class="form-control" style="height: 200px;"></select>
							</div>
							<div class="col-sm-2">
								<div style="height: 200px; padding: 48px 8px">
									<button class="btn btn-xs btn-white btn-primary margin-top-8"
										id="staff-list-add-btn" type="button">
										&nbsp;添加 <i class="ace-icon fa fa-arrow-right icon-on-right"></i>&nbsp;
									</button>
									<button class="btn btn-xs  btn-white btn-warning margin-top-8"
										id="staff-list-del-btn" type="button">
										&nbsp;<i class="ace-icon fa fa-arrow-left icon-on-left"></i>
										删除&nbsp;
									</button>
									<button class="btn btn-xs btn-white btn-primary margin-top-8"
										id="staff-list-addall-btn" type="button">全部添加</button>
									<button class="btn btn-xs btn-white btn-warning margin-top-8"
										id="staff-list-delall-btn" type="button">全部删除</button>
								</div>
							</div>
							<div class="col-sm-5">
								<label for="selectedStaffList">已选列表</label> <select
									multiple="multiple" id="selected-staff-list"
									class="form-control" style="height: 200px;"></select>
							</div>
						</div>
						<div class="form-group">
							<div id="staff-list-selected-tip" class="col-sm-6 green"></div>
						</div>
					</div>

					<div class="modal-footer">
						<button class="btn btn-sm" type="button" data-dismiss="modal">
							<i class="ace-icon fa fa-times"></i> 取消
						</button>
						<button id="save-staff-btn" type="button"
							class="btn btn-sm btn-success">
							<i class="ace-icon fa fa-check"></i> 确定
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!--  选择要授权的用户  -->
	<div id="auth-staff-selected-modal" class="modal" role="dialog"
		tabindex="-1">
		<div class="modal-dialog">
			<form class="form-horizontal" role="form" onsubmit="return false;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="blue bigger">
							选择要进行授权的用户<span class="red" id="selected-auth-staff-tip"></span>
						</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<div class="col-sm-5">
								<label for="staffList">用户列表</label> <select multiple="multiple"
									id="auth-staff-list" class="form-control"
									style="height: 200px;"></select>
							</div>
							<div class="col-sm-2">
								<div style="height: 200px; padding: 48px 8px">
									<button class="btn btn-xs btn-white btn-primary margin-top-8"
										id="auth-staff-list-add-btn" type="button">
										&nbsp;添加 <i class="ace-icon fa fa-arrow-right icon-on-right"></i>&nbsp;
									</button>
									<button class="btn btn-xs btn-white btn-warning margin-top-8"
										id="auth-staff-list-del-btn" type="button">
										&nbsp;<i class="ace-icon fa fa-arrow-left icon-on-left"></i>
										删除&nbsp;
									</button>
									<button class="btn btn-xs btn-white btn-primary margin-top-8"
										id="auth-staff-list-addall-btn" type="button">全部添加</button>
									<button class="btn btn-xs btn-white btn-warning margin-top-8"
										id="auth-staff-list-delall-btn" type="button">全部删除</button>
								</div>
							</div>
							<div class="col-sm-5">
								<label for="selectedStaffList">已选列表<span class="red">[最多不能超过10个]</span></label>
								<select multiple="multiple" id="selected-auth-staff-list"
									class="form-control" style="height: 200px;"></select>
							</div>
						</div>
						<div class="form-group">
							<div id="auth-staff-list-selected-tip" class="col-sm-6 green"></div>
						</div>
					</div>

					<div class="modal-footer">
						<button class="btn btn-sm" type="button" data-dismiss="modal">
							<i class="ace-icon fa fa-times"></i> 取消
						</button>
						<button id="save-selected-auth-staff-btn" type="submit"
							class="btn btn-sm btn-success">
							<i class="ace-icon fa fa-check"></i> 确定
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!--  对用户进行授权 -->
	<div id="auth-staff-modal" class="modal" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<form class="form-horizontal" role="form" onsubmit="return false;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="blue bigger">
							对组织【<span class="green txt-overflow-hidden"
								style="max-width: 320px;" id="auth-staff-department"></span>】内成员授权
						</h4>
					</div>
					<div class="modal-body">
						<table class="table table-bordered table-striped"
							id="auth-staff-table" style="width: auto;"></table>
					</div>

					<div class="modal-footer">
						<button class="btn btn-sm" type="button" data-dismiss="modal">
							<i class="ace-icon fa fa-times"></i> 取消
						</button>
						<button id="save-auth-staff-btn" type="submit"
							class="btn btn-sm btn-success">
							<i class="ace-icon fa fa-check"></i> 确定
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!-- 添加角色窗口 -->
	<div id="role-to-dept" class="modal" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content col-xs-8">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="blue bigger">
						【 <span name="departmentName" class="green txt-overflow-hidden"
							style="max-width: 180px;"></span> 】角色分配
					</h4>
				</div>

				<div class="modal-body">
					<input type="hidden" name="departmentId" />
					<div class="jqGrid_wrapper">
						<table id="role-table"></table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!--  新增用户  -->
	<div id="addnewstaff-modal" class="modal" role="dialog" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<form class="form-horizontal" id="addForm" role="form"
				onSubmit="return false;">
				<input type="hidden" name="departmentId" id="addstaff-departmentId" />
				<input type="hidden" name="others['roles']" id="addstaff-roles" />
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="blue bigger">新增用户</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right text-right"
										for="loginName"> <span class="red">*</span>用户名:
									</label>
									<div class="col-sm-5">
										<input type="text" name="loginName" id="loginName"
											class="form-control input-sm" />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right text-right"
										for="addForm-realName"> <span class="red">*</span>真实姓名:
									</label>

									<div class="col-sm-5">
										<input type="text" name="realName" id="addForm-realName"
											placeholder="不能超过50个汉字" class="form-control input-sm" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
									<label class="control-label col-sm-3 no-padding-right">性别：</label>
									<div class="col-sm-5">
										<label for="addForm-male"> <input type="radio"
											value="MALE" name="sex" id="addForm-male" class="ace" checked /><span
											class="lbl"> 男 </span>
										</label> <label for="addForm-female"> <input type="radio"
											value="FEMALE" name="sex" id="addForm-female" class="ace" />
											<span class="lbl"> 女 </span>
										</label>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right text-right"
										for="addForm-mobile"> <span class="red">*</span>手机:
									</label>

									<div class="col-sm-5">
										<input type="text" name="mobile" id="addForm-mobile"
											placeholder="" class="form-control input-sm" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right text-right"
										for="addForm-telephone"> 固定电话: </label>

									<div class="col-sm-5">
										<input type="text" name="telephone" id="addForm-telephone"
											placeholder="" class="form-control input-sm" />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right text-right"
										for="addForm-email"> <span class="red">*</span>邮箱:
									</label>

									<div class="col-sm-5">
										<input type="text" name="email" id="addForm-email"
											placeholder="" class="form-control input-sm" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right text-right"
										for="password"> <span class="red">*</span>初始密码:
									</label>

									<div class="col-sm-5">
										<input type="password" name="password" id="password"
											placeholder="" class="form-control input-sm" />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right text-right"
										for="addForm-pwdConfirm"> <span class="red">*</span>密码确认:
									</label>

									<div class="col-sm-5">
										<input type="password" name="pwdConfirm"
											id="addForm-pwdConfirm" placeholder=""
											class="form-control input-sm" />
									</div>
								</div>
							</div>
						</div>
						<div class="row  hide" id="cityArea">
							<div class="col-sm-6">
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right text-right"
										for="addForm-pwdConfirm"><span class="red">*</span>所属城市:
									</label>
									<div class="col-sm-5">
										<select name="cityId" id="staff-cityid"
											class="form-control input-sm">
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<h4 class="header smaller lighter blue">
									<span class="red">*</span>角色分配
								</h4>
								<div class="form-group">
									<div class="col-sm-12" id="staff-roleList"></div>
								</div>
							</div>
						</div>
					</div>



					<div class="modal-footer" style="margin-top: -1px;">
						<button class="btn btn-sm" type="button" data-dismiss="modal">
							<i class="ace-icon fa fa-times"></i> 取消
						</button>

						<button id="save-staff-btn" type="submit"
							class="btn btn-sm btn-success">
							<i class="ace-icon fa fa-check"></i> 保存
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!-- page specific plugin scripts -->
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	<script type="text/javascript"
		src="${ctx}/static/js/plugins/fuelux/fuelux.tree.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/static/js/custom/mult-select2.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/views/dept.js?12"></script>
</body>
</html>