<%--
  Created by IntelliJ IDEA.
  User: Howell
  Date: 21/10/16
  Time: AM10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="cn.com.bestpay.portal.SecurityPassword.impl.Password"%>
<%@ taglib uri="/webCommon/SecurityPassword/taglib/SecurityPassword.tld" prefix="LoginPassword"%>
<%Password.initPwdImpl("cn.com.bestpay.portal.SecurityPassword.impl.PassGuardCtrl",request);%>