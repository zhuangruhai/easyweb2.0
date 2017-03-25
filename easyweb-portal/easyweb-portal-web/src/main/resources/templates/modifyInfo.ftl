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
						<h5>修改个人信息</h5>
					</div>

					<div class="ibox-content">
						<form class="form-horizontal" role="form" id="modifyInfoForm"
							onsubmit="return false;">
							<input type="hidden" name="loginName" id="staffLoginName" />
							<div class="form-group">
								<label class="control-label col-sm-3 no-padding-right">成员名：</label>
								<div class="col-sm-4">
									<p id="loginName" class="form-control-static"></p>
								</div>
							</div>
							<div class="form-group">
								<label for="staffRealName"
									class="control-label col-sm-3 no-padding-right"
									for="staffRealName"> <span class="red">*</span> 姓名：
								</label>
								<div class="col-sm-4">
									<input type="text" name="realName" id="staffRealName"
										class="form-control input-sm" />
								</div>

							</div>
							<div class="form-group">
								<label class="control-label col-sm-3 no-padding-right">性别：</label>
								<div class="col-sm-4">
									<label for="male"> <input type="radio" value="MALE"
										name="sex" id="male" class="ace" /> <span class="lbl">男</span>
									</label> <label for="female"> <input type="radio"
										value="FEMALE" name="sex" id="female" class="ace" /> <span
										class="lbl">女</span>
									</label>
								</div>
							</div>
							<div class="form-group">
								<label for="staffTelephone"
									class="control-label col-sm-3 no-padding-right">电话号码：</label>
								<div class="col-sm-4">
									<input type="text" name="telephone" id="staffTelephone"
										class="form-control input-sm" />
								</div>

							</div>

							<div class="form-group">
								<label for="staffMobile"
									class="control-label col-sm-3 no-padding-right"> <span
									class="red">*</span> 手机：
								</label>
								<div class="col-sm-4">
									<input type="text" name="mobile" id="staffMobile"
										class="form-control input-sm" />
								</div>
							</div>
							<div class="form-group">
								<label for="staffEmail"
									class="control-label col-sm-3 no-padding-right"> <span
									class="red">*</span> Email：
								</label>
								<div class="col-sm-4">
									<input type="text" name="email" id="staffEmail"
										class="form-control input-sm" />
								</div>
							</div>
							<div class="form-group" style="display: none">
								<label for="staffDepartmentName"
									class="control-label col-sm-3 no-padding-right"> 所属组织：
								</label>
								<div class="col-sm-6">
									<p class="field form-control-static" id="staffDepartmentName"
										name="others.departmentName"></p>
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
	<script type="text/javascript" src="${ctx}/js/views/modifyInfo.js"></script>
</body>
</html>