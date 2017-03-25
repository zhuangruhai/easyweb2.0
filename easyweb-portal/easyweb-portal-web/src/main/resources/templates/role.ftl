<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">

<title>主页</title>
<#include "/include/header.ftl">
</head>
<body>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox ">
					<div class="ibox-content">
                        <form role="form" class="form-inline" id="queryForm" onsubmit="return false;">
                            <div class="form-group">
                                <button id="addNewRoleBtn" class="btn btn-sm btn-success"
									style="display:none"
									permCheck="auth_admin_sys_role_roleManage,ADD,hide">
									<i class="ace-icon fa fa-hand-o-right white"></i> 新增角色
								</button>
                            </div>
                            <div class="form-group">
                               <input id="searchText" type="text"
								class="input-sm form-control" placeholder="输入关键字" />
                            </div>
                             <button type="button" id="seachBtn"  class="btn btn-sm btn-primary"> 查找<i class="fa fa-search icon-on-right bigger-110"></i></button>
                             <button class="btn btn-sm" type="button" id="resetBtn">
								<i class="fa fa-undo bigger-110"></i> 重 置
							</button>
                        </form>
                        <hr>
                        <div class="jqGrid_wrapper">
                            <table id="role-table"></table>
                            <div id="grid-pager"></div>
                        </div>
                    </div>
				</div>
			</div>
		</div>
	</div>
	<!--  新增角色  -->
	<div id="addnewrole-form" class="modal" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<form class="form-horizontal" role="form" id="add-role-form"
				onsubmit="return false;">
				<input type="hidden" name="roleId" id="roleId" />
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="blue bigger">新增角色</h4>
					</div>

					<div class="modal-body">

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right"
								for="roleKey"> <span class="red">*</span>角色助记码:
							</label>
							<div class="col-sm-6">
								<input type="text" name="roleKey" id="roleKey" placeholder=""
									class="form-control input-sm" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right"
								for="roleName"> <span class="red">*</span>角色名称:
							</label>

							<div class="col-sm-6">
								<input type="text" name="roleName" id="roleName" placeholder=""
									class="form-control input-sm" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right text-right"
								for="roleDesc"> 角色描述: </label>

							<div class="col-sm-6">
								<textarea name="roleDesc" id="roleDesc" cols="10" rows="3"
									class="form-control input-sm"></textarea>
							</div>
						</div>

					</div>


					<div class="modal-footer">
						<button class="btn btn-sm" type="button" data-dismiss="modal">
							<i class="ace-icon fa fa-times"></i> 取消
						</button>

						<button id="save-role-btn" class="btn btn-sm btn-success"
							type="submit">
							<i class="ace-icon fa fa-check"></i> 保存
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!--  角色权限设置  -->
	<div id="role-auth-form" class="modal" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="blue bigger">
						角色[<span id="role-auth-form-rolename"
							class="green txt-overflow-hidden" style="max-width: 400px;"></span>]权限设置
					</h4>
				</div>

				<div id="roleTree" class="modal-body">
					<!--
						<div  class="dd">
						</div>
						-->
				</div>


				<div class="modal-footer">
					<button class="btn btn-sm" type="button" data-dismiss="modal">
						<i class="ace-icon fa fa-times"></i> 取消
					</button>

					<button id="save-auth-btn" class="btn btn-sm btn-success">
						<i class="ace-icon fa fa-check"></i> 保存
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-- page specific plugin scripts -->
	<#include "/include/footer.ftl">
	<script src="${ctx}/js/plugins/nestable/jquery.nestable.js"></script>
	<script type="text/javascript" src="${ctx}/js/views/role.js"></script>
</body>
</html>