<%@ page import="cn.lncsoftware.data.User" %>
<%@ page import="cn.lncsoftware.data.AppInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="org.bson.types.ObjectId" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: catten
  Date: 16/2/6
  Time: 下午11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>岭南软件园 应用链接管理</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/main.css">
</head>
<body>
<%
    User passport = (User)session.getAttribute("passport");
    request.setCharacterEncoding("utf-8");
    String statusFlag = "";
    String action = request.getParameter("action");

    if(passport == null){
        statusFlag = "no login";
    }else{
        boolean adminFlag = false;
        for(String s : passport.getRights()){
            if(s.equals("admin")){
                adminFlag = true;
                break;
            }
        }
        if(!adminFlag){
            statusFlag = "permission denied";
        }else{
            statusFlag = "permission recognition";
        }
    }

    if(passport != null && "permission recognition".equals(statusFlag)){
        if(action != null){
            switch (action){
                case "create":{
                    String title = request.getParameter("title");
                    if(title != null && title.length() < 32){
                        String link = request.getParameter("link");
                        if(link != null && link.length() < 256){
                            AppInfo appInfo = new AppInfo();
                            appInfo.setTitle(title);
                            appInfo.setLink(link);
                            String description = request.getParameter("description");
                            if(description != null) appInfo.setDescription(description);
                            String image = request.getParameter("imageCode");
                            if(image != null) appInfo.setImageCode(image);
                            String status = request.getParameter("status");
                            if(status != null) appInfo.setStatus(status); else appInfo.setStatus("disable");
                            appInfo.setDate(new Date());
                            AppInfo.getDao().insert(appInfo);
                            statusFlag = "create successful";
                        }else{
                            statusFlag = "link too long";
                        }
                    }else{
                        statusFlag = "illegal title";
                    }
                };break;

                case "update":{
                    String appID = request.getParameter("appID");
                    if(appID != null){
                        AppInfo appInfo = AppInfo.getDao().get(new ObjectId(appID));
                        if(appInfo != null){
                            String title = request.getParameter("title");
                            if(title != null && title.length() < 32){
                                String link = request.getParameter("link");
                                if(link != null && link.length() < 256){
                                    appInfo.setTitle(title);
                                    appInfo.setLink(link);
                                    String description = request.getParameter("description");
                                    if(description != null) appInfo.setDescription(description);
                                    String image = request.getParameter("imageCode");
                                    if(image != null) appInfo.setImageCode(image);
                                    String status = request.getParameter("status");
                                    if(status != null) appInfo.setStatus(status); else appInfo.setStatus("disable");
                                    AppInfo.getDao().update(appInfo);
                                    statusFlag = "update success";
                                }else{
                                    statusFlag = "link too long";
                                }
                            }else{
                                statusFlag = "illegal title";
                            }
                        }else{
                            statusFlag = "not exist";
                        }
                    }
                };break;
            }
        }
    }
%>
<div class="container">
    <jsp:include page="navbar.jsp"/>
    <script>
        document.getElementById("nav-app").setAttribute("class","active");
    </script>
    <div class="page-header">
        <h1>应用链接管理</h1>
    </div>
    <div class="row">
        <%
            if(passport != null && !"permission denied".equals(statusFlag)){
        %>
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">创建应用链接</div>
                <div class="panel-body">
                    <div class="container-fluid">
                        <form class="form-horizontal" action="AppInfoManagement.jsp" method="post">
                            <input type="hidden" name="action" value="create">
                            <div class="form-group">
                                <label class="control-label">标题:</label>
                                <input class="form-control" type="text" name="title">
                            </div>
                            <div class="form-group">
                                <label class="control-label">描述:</label>
                                <input class="form-control" type="text" name="description">
                            </div>
                            <div class="form-group">
                                <label class="control-label">缩略图:</label>
                                <input class="form-control" type="url" name="imageCode">
                            </div>
                            <div class="form-group">
                                <label class="control-label">链接:</label>
                                <input class="form-control" type="text" name="link">
                            </div>
                            <div class="form-group">
                                <label class="control-label">状态:</label>
                                <input type="radio" name="status" value="working" checked> 工作中
                                <input type="radio" name="status" value="repairing"> 维护期
                                <input type="radio" name="status" value="disable"> 已失效
                            </div>
                            <div class="form-group">
                                <input class="btn btn-primary btn-block" type="submit" value="创建">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-8">
            <div class="container-fluid">
                <form class="form-horizontal" action="AppInfoManagement.jsp" method="post">
                    <input type="hidden" name="action" value="search">
                    <div class="input-group">
                        <span class="input-group-addon"><input type="checkbox" name="useRegex" value="useRegex"> 正则表达式</span>
                        <input class="form-control" type="text" name="keyword" placeholder="搜索应用连接">
                        <span class="input-group-btn"><input class="btn btn-default" type="submit" value="搜索"></span>
                    </div>
                </form>
            </div>
            <div class="container-fluid">
                <%
                    switch (statusFlag){
                        case "create successful":
                %>
                <div class="alert alert-success">创建成功</div>
                <%
                        break;

                    case "link too long":
                %>
                <div class="alert alert-warning">链接字数请不要超过256个</div>
                <%
                        break;

                    case "illegal title":
                %>
                <div class="alert alert-warning">标题格式不正确</div>
                <%
                        break;

                    case "update success":
                %>
                <div class="alert alert-success">更新成功</div>
                <%
                        break;

                    case "item not exist":
                %>
                <div class="alert alert-warning">噢。。可能它刚刚被删除了</div>
                <%
                            break;
                    }

                    if("search".equals(action)){
                        String keyword = request.getParameter("keyword");
                        boolean useRegex = "useRegex".equals(request.getParameter("useRegex"));
                        if(keyword != null && (keyword.matches("[\\d\\w\\-_]{1,32}") || useRegex)){
                            List<AppInfo> appInfos = AppInfo.getDao().find("title",(useRegex ? keyword : ".*"+keyword+".*"));
                            if(appInfos != null && appInfos.size() > 0){
                %>
                <ul class="list-group">
                    <%
                        for(AppInfo appInfo : appInfos){
                    %>
                    <li class="list-group-item">
                        <div class="container-fluid">
                            <form class="form-horizontal" action="AppInfoManagement.jsp" method="post">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="appID" value="<%=appInfo.getObjectId().toHexString()%>">
                                <div class="form-group">
                                    <label class="control-label">标题: </label>
                                    <input class="form-control" type="text" name="title" value="<%=appInfo.getTitle()%>">
                                </div>
                                <div class="form-group">
                                    <label class="control-label">连接:</label>
                                    <input class="form-control" type="url" name="link" value="<%=appInfo.getLink()%>">
                                </div>
                                <div class="form-group">
                                    <label class="control-label">描述:</label>
                                    <input class="form-control" type="text" name="description" value="<%=appInfo.getDescription()%>">
                                </div>
                                <div class="form-group">
                                    <label class="control-label">缩略图:</label>
                                    <input class="form-control" type="url" name="imageCode" value="<%=appInfo.getImageCode()%>">
                                </div>
                                <div class="form-group">
                                    <label class="control-label">状态:</label>
                                    <input type="radio" name="status" value="working" <%=("working".equals(appInfo.getStatus()) ? "checked" : "")%>> 工作中
                                    <input type="radio" name="status" value="repairing" <%=("repairing".equals(appInfo.getStatus()) ? "checked" : "")%>> 维护期
                                    <input type="radio" name="status" value="disable" <%=("disable".equals(appInfo.getStatus()) ? "checked" : "")%>> 已失效
                                </div>
                                <input class="btn btn-primary btn-block" type="submit" value="update">
                            </form>
                        </div>
                    </li>
                    <%
                        }
                    %>
                </ul>
                <%
                            }
                        }else{
                %>
                <div class="alert-warning alert">关键字格式错误</div>
                <%
                        }
                    }
                %>
            </div>
        </div>
        <%
            }else{
        %>
        <div class="col-md-6 col-md-offset-3">
            <%
                switch (statusFlag){
                    case "no login":
            %>
            <div class="alert alert-warning">请先 <a href="../index.jsp">登录</a>.</div>
            <%
                        break;

                    case "permission denied":
            %>
            <div class="alert alert-danger">抱歉，你没有权限</div>
            <%
                        break;
                }
            %>
        </div>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
