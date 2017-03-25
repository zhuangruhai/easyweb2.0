<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>主页</title>
<#include "/include/header.ftl">
<style>
.checkbox LABEL {
	display: inline-block;
}

.dd2-content {
	display: block;
	margin: 5px 0;
	padding: 5px 10px;
	color: #333;
	text-decoration: none;
	border: 1px solid #e7eaec;
	background: #f5f5f5;
	border-radius: 3px;
	box-sizing: border-box;
	-moz-box-sizing: border-box;
}

.dd3-content {
	display: block;
	margin: 5px 0;
	padding: 5px 10px;
	color: #333;
	text-decoration: none;
	border: 1px solid #e7eaec;
	border-radius: 3px;
	box-sizing: border-box;
	-moz-box-sizing: border-box;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-8">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>权限分配</h5>
					</div>
					<div class="ibox-content">
						<form onsubmit="return false;">
							<!--查询-->
							<div id="roleTree"></div>
							<hr>
							<div class="actions clearfix">
								<div class="col-sm-4 col-sm-offset-3">
									<button class="btn btn-info" type="button" id="save-auth-btn">
										<i class="ace-icon fa fa-check bigger-110"></i> 保 存
									</button>
									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="button" id="backBtn">
										<i class="ace-icon fa fa-arrow-left"></i> 返 回
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>角色信息</h5>
					</div>
					<div class="ibox-content">
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-4 control-label right">角色助记码：</label>
							<div class="col-sm-8">
								<p name="roleKey" class="form-control-static"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">角色名称：</label>
							<div class="col-sm-8">
								<p name="roleName" class="form-control-static"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">角色描述：</label>
							<div class="col-sm-8">
								<p name="roleDesc" class="form-control-static"></p>
							</div>
						</div>
						</form>
					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- page specific plugin scripts -->
	<#include "/include/footer.ftl">
	<script src="${ctx}/js/plugins/nestable/jquery.nestable.js"></script>
	<script type="text/javascript"
		src="${ctx}/js/views/roleSetting.js"></script>
</body>
</html>