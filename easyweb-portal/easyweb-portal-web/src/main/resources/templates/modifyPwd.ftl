<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">

<title>主页</title>
<#include "/include/header.ftl">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>修改密码</h5>
					</div>
					
					<div class="ibox-content">
						<form id="modifyPwdForm" class="form-horizontal" role="form"
							onsubmit="return false;">
							<div class="form-group">
								<label for="oldPassword"
									class="col-sm-3 control-label no-padding-right"><span
									class="red">*</span>旧密码：</label>
								<div class="col-sm-4">
									<input type="password" name="oldPassword" id="oldPassword"
										class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"></label>
								<div class="col-sm-9">
									<p class="green">
										(密码长度最短6位，最长20位，数字、字母、特殊符号组成，并且必须同时包含数字和字母)</p>
								</div>
							</div>
							<div class="form-group">
								<label for="newPassword"
									class="col-sm-3 control-label no-padding-right"><span
									class="red">*</span>新密码：</label>
								<div class="col-sm-4">
									<input type="password" name="newPassword" id="newPassword"
										class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label for="newPasswordConfirm"
									class="col-sm-3 control-label no-padding-right"><span
									class="red">*</span>再次确认新密码：</label>
								<div class="col-sm-4">
									<input type="password" name="newPasswordConfirm"
										id="newPasswordConfirm" class="form-control" />
								</div>
							</div>
							<div class="form-group">
                                <div class="col-sm-4 col-sm-offset-3">
                                   <button class="btn btn-info" type="submit" id="submitButton">
										<i class="ace-icon fa fa-check bigger-110"></i> 保 存
									</button>

									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="button" id="resetBtn">
										<i class="ace-icon fa fa-undo bigger-110"></i> 重 置
									</button>
                                </div>
                            </div>
						</form>
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
	<script type="text/javascript"
		src="${ctx}/js/views/modifyPwd.js"></script>
</body>
</html>
