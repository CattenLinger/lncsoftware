<%@ page import="cn.lncsoftware.data.User" %>
<%@ page import="cn.lncsoftware.common.RegexTools" %><%--
  Created by IntelliJ IDEA.
  User: catten
  Date: 16/2/11
  Time: 上午1:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>岭南软件园 用户中心</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/main.css">
</head>
<%
    User passport = (User)session.getAttribute("passport");
    request.setCharacterEncoding("utf-8");
    String action = request.getParameter("action");
    String statusFlag = "";

    if(passport == null){
        statusFlag = "no login";
    }else{
        if(action != null){
            switch (action){
                case "update":{
                    String contact = request.getParameter("contact");
                    String password = request.getParameter("password");
                    String conPassword = request.getParameter("conPassword");
                    if(RegexTools.legalPassword(password)){
                        if(password.equals(conPassword)){
                            passport.setPassword(password);
                        }else{
                            statusFlag = "password not same";
                            break;
                        }
                    }else if(passport != null && !"".equals(password)){
                        statusFlag = "illegal password";
                        break;
                    }
                    if(RegexTools.legalContactInfo(contact)){
                        passport.setContactInfo(contact);
                        User.getDao().update(passport);
                        session.setAttribute("passport",passport);
                        statusFlag = "update success";
                    }else{
                        statusFlag = "illegal contact";
                    }
                }
            }
        }
    }
%>
<body>
<div class="container">
    <jsp:include page="navbar.jsp"/>
    <script>
        document.getElementById("nav-user").setAttribute("class","active");
    </script>
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-success" id="userPanel">
                <div class="panel-heading">用户信息</div>
                <div class="panel-body">
                    <div class="container-fluid">
                        <%
                            switch (statusFlag){
                                case "no login":
                        %>
                        <div class="alert alert-warning">请先 <a class="alert-link" href="index.jsp">登录</a>.</div>
                        <%
                                break;

                            case "illegal password":
                        %>
                        <div class="alert alert-warning">密码格式不正确</div>
                        <%
                                break;

                            case "update success":
                        %>
                        <div class="alert alert-success">用户信息更改成功</div>
                        <%
                                break;

                            case "illegal contact":
                        %>
                        <div class="alert alert-warning">联系方式格式不正确</div>
                        <%
                                break;
                            case "password not same":
                        %>
                        <div class="alert alert-warning">密码不一致</div>
                        <%
                                break;
                            }

                            if(passport != null){
                        %>
                        <form class="form-horizontal" action="user.jsp" method="post">
                            <input type="hidden" name="action" value="update">
                            <div class="form-group">
                                <label class="control-label">用户名 </label>
                                <input type="text" class="form-control" value="<%=passport.getName()%>" disabled>
                            </div>
                            <div class="form-group">
                                <label class="control-label">权限</label>
                                <div class="form-control">
                                    <%
                                        for(String s : passport.getRights()){
                                            switch (s){
                                                case "login":
                                    %>
                                    <span class="label label-success">Login</span>
                                    <%
                                                    break;

                                                case "admin":
                                    %>
                                    <span class="label label-danger">Admin</span>
                                    <%
                                                    break;

                                                case "article":
                                    %>
                                    <span class="label label-info">Writer</span>
                                    <%
                                                    break;
                                            }
                                        }
                                    %>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label">密码 </label>
                                <input class="form-control" type="password" name="password" placeholder="如果不想更改请留空">
                                <small>此网站并没有加密用户密码，所以请不要把您的常用密码泄露给我们，谢谢</small>
                            </div>
                            <div class="form-group">
                                <label class="control-label">确认密码 </label>
                                <input class="form-control" type="password" name="conPassword" placeholder="再确认一次密码">
                                <small>请确认并牢记您的新密码，我们还没有密码找回机制</small>
                            </div>
                            <div class="form-group">
                                <label class="control-label">联系信息 </label>
                                <input class="form-control" type="text" name="contact" value="<%=passport.getContactInfo()%>">
                            </div>
                            <div class="form-group">
                                <input class="btn btn-primary col-md-12" type="submit" value="更新">
                            </div>
                            <div class="form-group">
                                <a href="index.jsp" class="btn btn-default col-md-12">回到主页</a>
                            </div>
                        </form>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
