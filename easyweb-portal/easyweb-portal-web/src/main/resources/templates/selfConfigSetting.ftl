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
			<div class="col-xs-12">
				<form id="setupForm" class="form-horizontal margin-top-10"
					role="form" onsubmit="return false;">
					<div class="ibox ">
						<div class="ibox-title">
							<h5>短信待办提醒</h5>
						</div>

						<div class="ibox-content">
							<!--查询-->

							<div class="form-group">
								<label class="col-sm-2 control-label">手机号码：</label>
								<div class="col-sm-4">
									<p id="staffMobile" class="form-control-static"></p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"> <span class="red">*</span>
									开通状态：
								</label>
								<div class="col-sm-4">
									<label> <input type="radio" value="1" name="sendsms"
										id="sendsmsYRadio" class="ace" /> <span class="lbl">是</span>
									</label> <label> <input type="radio" value="0" name="sendsms"
										id="sendsmsNRadio" class="ace" checked="checked" /> <span
										class="lbl">否</span>
									</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"> <span class="red">*</span>
									提醒时间：
								</label>
								<div id="smsSendTimeSet" class="col-sm-8">
									<label> <input type="checkbox" value="08"
										name="smsSendTime" class="ace"> <span class="lbl">08:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="09"
										name="smsSendTime" class="ace"> <span class="lbl">09:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="10"
										name="smsSendTime" class="ace"> <span class="lbl">10:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="11"
										name="smsSendTime" class="ace"> <span class="lbl">11:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="12"
										name="smsSendTime" class="ace"> <span class="lbl">12:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="13"
										name="smsSendTime" class="ace"> <span class="lbl">13:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="14"
										name="smsSendTime" class="ace"> <span class="lbl">14:00</span>
										&nbsp;
									</label> <br> <label> <input type="checkbox" value="15"
										name="smsSendTime" class="ace"> <span class="lbl">15:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="16"
										name="smsSendTime" class="ace"> <span class="lbl">16:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="17"
										name="smsSendTime" class="ace"> <span class="lbl">17:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="18"
										name="smsSendTime" class="ace"> <span class="lbl">18:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="19"
										name="smsSendTime" class="ace"> <span class="lbl">19:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="20"
										name="smsSendTime" class="ace"> <span class="lbl">20:00</span>
										&nbsp;
									</label>
								</div>
							</div>
						</div>
					</div>
					<div class="ibox ">
						<div class="ibox-title">
							<h5>邮件待办提醒</h5>
						</div>
						<div class="ibox-content">
							<div class="form-group">
								<label class="col-sm-2 control-label">邮件地址：</label>
								<div class="col-sm-4">
									<p id="staffEmail" class="form-control-static"></p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"> <span class="red">*</span>
									开通状态：
								</label>
								<div class="col-sm-4">
									<label> <input type="radio" value="1" name="sendemail"
										id="sendemailYRadio" class="ace" /> <span class="lbl">是</span>
									</label> <label> <input type="radio" value="0" name="sendemail"
										id="sendemailNRadio" class="ace" checked="checked" /> <span
										class="lbl">否</span>
									</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"> <span class="red">*</span>
									提醒时间：
								</label>
								<div id="emailSendTimeSet" class="col-sm-8">
									<label> <input type="checkbox" value="08"
										name="emailSendTime" class="ace"> <span class="lbl">08:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="09"
										name="emailSendTime" class="ace"> <span class="lbl">09:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="10"
										name="emailSendTime" class="ace"> <span class="lbl">10:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="11"
										name="emailSendTime" class="ace"> <span class="lbl">11:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="12"
										name="emailSendTime" class="ace"> <span class="lbl">12:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="13"
										name="emailSendTime" class="ace"> <span class="lbl">13:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="14"
										name="emailSendTime" class="ace"> <span class="lbl">14:00</span>
										&nbsp;
									</label> <br> <label> <input type="checkbox" value="15"
										name="emailSendTime" class="ace"> <span class="lbl">15:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="16"
										name="emailSendTime" class="ace"> <span class="lbl">16:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="17"
										name="emailSendTime" class="ace"> <span class="lbl">17:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="18"
										name="emailSendTime" class="ace"> <span class="lbl">18:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="19"
										name="emailSendTime" class="ace"> <span class="lbl">19:00</span>
										&nbsp;
									</label> <label> <input type="checkbox" value="20"
										name="emailSendTime" class="ace"> <span class="lbl">20:00</span>
										&nbsp;
									</label>
								</div>
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-4 col-sm-offset-3">
							<button class="btn btn-info" type="submit" id="submitBtn">
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
	<#include "/include/footer.ftl">
	<script type="text/javascript"
		src="${ctx}/js/views/selfConfigSetting.js"></script>
</body>
</html>