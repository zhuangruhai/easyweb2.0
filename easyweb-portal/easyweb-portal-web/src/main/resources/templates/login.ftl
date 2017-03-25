<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${result.title}</title>
<#include "/include/header.ftl">
 <link href="${ctx}/css/login.css" rel="stylesheet">
 <style>
 .index-login-img {
    background-image: url(${ctx}/img/bg_index.jpg);
    background-repeat: no-repeat;
    background-position: 25% 4px;
    height: 417px;
}
 </style>
<script type="text/javascript" src="${ctx}/servlet/facade.script?action=getLoginInfo"></script>
<script type="text/javascript">
	if(window.top !== window.self){ window.top.location = window.location;}
    window.title      = '${result.title}';
	window.requestUrl = '';
	window.modulus    = '${result.modulus}';
	window.exponent   = '${result.exponent}';
	if (window != top){
		top.location.href = window.ctPath + '/'; 
	} 
</script>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/js/crypt/jsbn.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/js/crypt/prng4.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/js/crypt/rng.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/js/crypt/rsa.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/js/crypt/base64.js"></script>
</head>

<body class="signin">
    <div class="signinpanel">
        <div class="row">
            <div class="col-sm-7">
            </div>
            <div class="col-sm-5">
                <form id="loginForm" onsubmit="return false;">
                    <h4 class="no-margins">登录：</h4>
                    <p class="m-t-md">${result.title}</p>
                    <input type="text" class="form-control uname" placeholder="用户名" name="loginName"/>
                    <input type="password" class="form-control pword m-b" placeholder="密码" name="password"/>
                    <div class="form-group" style="display: none">
					<input type="text" id="checkCodeImg" src="${ctx}/portal/code.ajax"
						class="form-control" width="78" height="38">
						<a href="javascript:;" id="checkCode"></a>
					</div>
					<p id="errorDisplayArea" class="text-danger" style="display:none;"></p>
                    <button class="btn btn-success btn-block" id="loginButton">登录</button>
                </form>
            </div>
        </div>
        <div class="signup-footer">
            <div class="pull-left">
                &copy; 2017 All Rights Reserved.
            </div>
        </div>
    </div>
    	<!-- 全局js -->
	<script src="${ctx}/js/jquery.min.js?v=2.1.4"></script>
	<script src="${ctx}/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${ctx}/js/views/login.js"></script>
</body>
</html>
