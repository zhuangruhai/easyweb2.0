if (self != top && top.prmGloableCache)
	top.location.reload();
var defaultInpuTip = {
	loginName : '请输入用户名',
	checkCode : '验证码',
	error_loginName : '请输入账户名!',
	error_password : '请输入密码',
	error_checkCode : '请输入验证码!'
};
var valueEqTip = function(jq, tip) {
	var val = jq.val();
	if (val && tip == val) {
		return true;
	}
	return false;
};
var valueIsEmpty = function(jq) {
	var val = jq.val();
	if (val == null || val == '') {
		return true;
	}
	return false;
};

var errorTip = function(errorMsg) {
	$('#errorDisplayArea').text(errorMsg);
	if ('' == errorMsg) {
		$('#errorDisplayArea').hide();
	} else {
		$('#errorDisplayArea').show();
	}
};

var validFiledSets = {
	_isEmpty : function(field) {
		if (field.value == null || '' == field.value
				|| field.value == defaultInpuTip[field.name]) {
			errorTip(defaultInpuTip['error_' + field.name]);
			$('#' + field.name).focus();
			return true;
		}
		return false;
	},
	loginName : function(field) {
		return !this._isEmpty(field);
	},
	password : function(field) {
		if (this._isEmpty(field)) {
			$('#artiPwd').focus();
		} else {
			return true;
		}
	},
	checkCode : function(field) {
		return !this._isEmpty(field);
	}
}

var refreshCheckCode = function(jqO) {
	jqO.attr('src', ctxPaths + 'portal/code.ajax?timeStamp='
			+ new Date().getTime());
}

var enterSubmit = function(selector, submitHandler) {
	var targets = $(selector);
	if (targets.length == 0) {
		return;
	}
	if (!(submitHandler instanceof Function))
		return;
	targets.bind('keydown', function(e) {
		if (e.which == 13) {
			submitHandler.apply();
			// 阻止默认行为和冒泡
			return false;
		}

	});
}

$()
		.ready(
				function() {

					// 验证码链接
					$('#checkCodeImg').bind('click', function() {
						refreshCheckCode($('#checkCodeImg'));
					});

					// 登录动作
					var loginAction = function() {
						// 校验与序列化数据
						var params = {};
						var fields = $('#loginForm').serializeArray()
						var checkResult = true;
						jQuery.each(fields, function(i, field) {
							checkResult = validFiledSets[field.name](field)
							if (checkResult) {
								if (field.name == 'password') {
									var rsa = new RSAKey();
									rsa.setPublic(modulus, exponent);
									var passwordDe = rsa.encrypt(field.value);
									params[field.name] = passwordDe;
								} else {
									params[field.name] = field.value;
								}
							} else {
								return false;
							}
						});
						if (!checkResult) {
							return;
						}
						// 清除错误
						errorTip('');
						// 登录按钮灰显
						$('#loginButton').attr("disabled", true);
						// 提交
						$
								.ajax({
									url : ctxPaths + '/portal/login.ajax',
									type : 'POST',
									timeout : 30000,
									dataType : 'json',
									data : params,
									success : function(data) {
										if (data.success) {
											// 跳转
											window.location.href=ctxPaths + '/';
										} else {
											if (data.message) {
												errorTip(data.message);
											} else {
												errorTip('登录失败，原因未知');
											}
											refreshCheckCode($('#checkCodeImg'));
											$('#loginButton').removeAttr(
													"disabled");
											/** }* */
										}
									},
									error : function(jqXHR, errorMsg) {
										errorTip('登录失败，原因未知');
										refreshCheckCode($('#checkCodeImg'));
										$('#loginButton')
												.removeAttr("disabled");
									}
								});
					}
					$('#loginButton').bind('click', loginAction);
					$("#password,#checkCode").keydown(function(e) {
						var curKey = e.which;
						if (curKey == 13) {
							$('#loginButton').trigger('click');
							return false;
						}
					});
					// 回车提交动作
					enterSubmit('#loginButton', loginAction);

					$('#forgetPwdPanel .checkCodeImg').bind('click',
							function() {
								refreshCheckCode($(this));

							})
					$('#forgetPwd')
							.unbind('click')
							.bind(
									'click',
									function() {
										$('#forgetPwdPanel')
												.floatDiv(
														{
															clsBtn : $('#forgetPwdPanel .closeitem a'),
															show : true
														});
										refreshCheckCode($('#forgetPwdPanel .checkCodeImg'));
										// 校验
										var validation = $(
												"#forgetPwdPanel form")
												.Validform(
														{
															showAllError : true,
															tiptype : 3,
															datatype : {
																"checkCode" : dataType.checkCode
															}

														});
										validation.resetForm();
										// 提交
										$("#forgetPwdPanel a.fp_confirm")
												.bind(
														"click",
														function() {
															validation
																	.config({
																		beforeSubmit : function(
																				curform) {
																			$
																					.ajaxSubmit(
																							ctxPaths
																									+ "/resetPwd!email.ajax",
																							curform
																									.serializeArray(),
																							function(
																									data) {
																								if (data.success) {
																									alert('重置密码链接已经发送到您的邮箱中');
																								} else {
																									if (data.data
																											&& data.message) {
																										alert(data.message);
																									} else {
																										alert("未知错误");
																									}
																									refreshCheckCode($('#forgetPwdPanel .checkCodeImg'));
																								}
																							});
																			return false;
																		}
																	});
															validation
																	.submitForm();
														});

									});
				});