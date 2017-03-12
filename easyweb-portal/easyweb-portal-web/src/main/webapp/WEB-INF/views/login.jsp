<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.hichlink.easyweb.portal.common.config.Config,com.hichlink.easyweb.portal.common.util.RSAUtil"%>
<%
	String requestUrl = request.getParameter("requestUrl");
	if (requestUrl != null) {
	    requestUrl = java.net.URLEncoder.encode(requestUrl, "UTF-8");
	}
	String modulus = RSAUtil.bigIntToHexStr(RSAUtil.getDefaultPublicKey().getModulus());
	String exponent = RSAUtil.bigIntToHexStr(RSAUtil.getDefaultPublicKey().getPublicExponent());
	
	/*
	boolean enableCheckCode = Config.getInstance().enableCheckCode();
	*/
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>登录</title>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript" src="${ctx}/servlet/facade.script?action=getLoginInfo"></script>
<script type="text/javascript">
    window.title      = '${title}';
	window.requestUrl = '<%=requestUrl%>';
	window.modulus    = '<%=modulus%>';
	window.exponent   = '<%=exponent%>';
	if (window != top){
		top.location.href = window.ctPath + '/portal/login.jsp'; 
	} 
</script>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/static/js/crypt/jsbn.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/static/js/crypt/prng4.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/static/js/crypt/rng.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/static/js/crypt/rsa.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/static/js/crypt/base64.js"></script>
</head>

<body class="gray-bg">

	<div class="middle-box text-center loginscreen  animated fadeInDown">
		<div>
			<div>
				<h1 class="logo-name">H+</h1>
			</div>
			<h3>欢迎使用 H+</h3>

			<form class="m-t" role="form" id="loginForm">
				<div class="form-group">
					<input type="text" name="loginName" class="form-control"
						placeholder="用户名">
				</div>
				<div class="form-group">
					<input type="password" name="password" class="form-control"
						placeholder="密码">
				</div>
				<div class="form-group" style="display: none">
					<input type="text" id="checkCodeImg" src="${ctx}/portal/code.ajax"
						class="form-control" width="78" height="38">
						<a href="javascript:;" id="checkCode"></a>
				</div>
				<button type="button" id="loginButton" class="btn btn-primary block full-width m-b">登
					录</button>

				<p class="text-muted text-center">
					<span id="errorDisplayArea" class="text-danger"></span>
				</p>

			</form>
		</div>
	</div>

	<!-- 全局js -->
	<script src="${ctx}/static/js/jquery.min.js?v=2.1.4"></script>
	<script src="${ctx}/static/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${ctx}/static/js/views/login.js"></script>
</body>

</html>
