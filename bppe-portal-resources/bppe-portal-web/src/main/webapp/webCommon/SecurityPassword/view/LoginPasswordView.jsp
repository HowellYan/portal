<%--
  Created by IntelliJ IDEA.
  User: Howell
  Date: 21/10/16
  Time: AM10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/webCommon/SecurityPassword/model/LoginPassword.jsp"%>
<%--<script type="text/javascript" src="http://localhost:9090/lib/js/thirdParty/jquery-1.12.4.min.js?v=1.0.0"></script>--%>
<%--<script type="text/javascript" src="http://localhost:9090/lib/js/SecurityPassword/Base64.js"></script>--%>
<%--<script type="text/javascript" src="http://localhost:9090/lib/js/SecurityPassword/PassGuardCtrl.js"></script>--%>

<div>
<LoginPassword:write
        clazz="old-pwd pwd-small"
        id="loginpwd"
        name="password"
        rdName="login-rd"
        sessionKey="login-pwd-ses-key"
        params="pwdmark:0"/>
</div>