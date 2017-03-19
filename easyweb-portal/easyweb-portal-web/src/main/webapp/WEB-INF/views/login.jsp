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
 <link href="${ctx}/static/css/login.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/servlet/facade.script?action=getLoginInfo"></script>
<script type="text/javascript">
	if(window.top !== window.self){ window.top.location = window.location;}
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

<body class="signin">
    <div class="signinpanel">
        <div class="row">
            <div class="col-sm-7">
                <div class="signin-info">
                    <div class="logopanel m-b">
                        <h1>[ H+ ]</h1>
                    </div>
                    <div class="m-b"></div>
                    <h4>欢迎使用 <strong>H+ 后台主题UI框架</strong></h4>
                    <ul class="m-b">
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势一</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势二</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势三</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势四</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势五</li>
                    </ul>
                    <strong>还没有账号？ <a href="#">立即注册&raquo;</a></strong>
                </div>
            </div>
            <div class="col-sm-5">
                <form id="loginForm" onsubmit="return false;">
                    <h4 class="no-margins">登录：</h4>
                    <p class="m-t-md">登录到H+后台主题UI框架</p>
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
                &copy; 2015 All Rights Reserved. H+
            </div>
        </div>
    </div>
    	<!-- 全局js -->
	<script src="${ctx}/static/js/jquery.min.js?v=2.1.4"></script>
	<script src="${ctx}/static/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${ctx}/static/js/views/login.js"></script>
</body>
</html>
