<%--
  Created by IntelliJ IDEA.
  User: Howell
  Date: 21/10/16
  Time: AM10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/webCommon/SecurityPassword/model/LoginPassword.jsp"%>

<div>
<LoginPassword:write
        clazz="loginpwd"
        id="loginpwd"
        name="loginpwd"
        rdName="login-rd"
        sessionKey="login-pwd-ses-key"
        params="pwdmark:0"/>
</div>