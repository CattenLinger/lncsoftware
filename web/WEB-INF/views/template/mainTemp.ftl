<#setting datetime_format="yyyy-MM-dd HH:mm">
<#macro body title>
<html>
<head>
    <title>LNCSA - ${title?html}</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="//cdn.bootcss.com/animate.css/3.5.2/animate.min.css">
    <link href="/css/bootstrap-toggle.min.css" rel="stylesheet">
    <link href="/css/lncsa_main.css" rel="stylesheet">
    <link href="/css/font-awesome.min.css" rel="stylesheet">
    <script src="/js/tools.js"></script>
</head>
<body>
    <#nested>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="/js/bootstrap-toggle.min.js"></script>
<script src="/js/holder.min.js"></script>
</body>
</html>
</#macro>

<#macro mainNav activeOrder>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li <#if activeOrder==0>class="active"</#if>><a href="/">Home</a></li>
            <li <#if activeOrder==1>class="active"</#if>><a href="/article/">Articles</a></li>
            <li <#if activeOrder==2>class="active"</#if>><a href="/bulletin/">Bulletins</a></li>
            <li <#if activeOrder==3>class="active"</#if>><a href="/about">About Us</a></li>
        </ul>
        <#if (Session.session_user??) >
            <ul class="nav navbar-nav navbar-right">
                <li <#if activeOrder==4>class="active"</#if>><a href="/user/self">Welcome,
                    <#if Session.session_user.nickname?? && Session.session_user.nickname != "">
                        ${Session.session_user.nickname}
                    <#else >
                        ${Session.session_user.username}
                    </#if></a></li>
                <li><a href="/user/logout">Logout</a></li>
            </ul>
        <#else>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/user/login">Login</a></li>
                <li><a href="/user/register">Register</a></li>
            </ul>
        </#if>
    </div>
</nav>
</#macro>

<#macro defaultPager total current length path>
<div class="container-fluid">
    <nav style="text-align: center">
        <ul class="pagination">
                <#-- Left side -->
            <#if current == 0>
                <li class="disabled"><a href="#">&laquo;</a></li>
                <li class="disabled"><a href="#">&lsaquo;</a></li>
            <#else>
                <li><a href="${path + 0}">&laquo;</a></li>
                <li><a href="${path + (current - 1)}">&lsaquo;</a></li>
            </#if>

            <#-- Middle page list -->
            <#if (total <= length)>
                <@listPagerMiddle
                    start=0
                    end=(total - 1)
                    current=current path=path/>

            <#elseif (total > length)>
                <#if (current <= (length / 2)?floor)>
                    <@listPagerMiddle
                        start=0
                        end=(length - 1)
                        current=current path=path/>

                <#elseif ((current > (length / 2)?floor) && (current < (total - length / 2)?floor)) >
                    <@listPagerMiddle
                        start=current + 1 - (length / 2)?ceiling
                        end=current + (length / 2)?floor
                        current=current path=path/>

                <#elseif (current + (length / 2)?floor >= total - 1)>
                    <@listPagerMiddle
                        start=(total-length)
                        end=(total - 1)
                        current=current path=path/>
                </#if>
            </#if>

                <#-- Right side -->
            <#if current == (total - 1)>
                <li class="disabled"><a href="#">&rsaquo;</a></li>
                <li class="disabled"><a href="#">&raquo;</a></li>
            <#else >
                <li><a href="${path + (current + 1)}">&rsaquo;</a></li>
                <li><a href="${path + (total - 1)}">&raquo;</a></li>
            </#if>
        </ul>
    </nav>
</div>
</#macro>

<#macro listPagerMiddle start end current path>
    <#list start..end as i>
    <li ${(i == current)?string('class="active"','')}><a href=${path + i}>${i + 1}</a></li>
    </#list>
</#macro>

<#macro dialog mainTitle subtitle>
<div class="container">
    <div class="page-header">
        <h1 align="center">${mainTitle}</h1>
        <#if subtitle??>
            <p align="center">${subtitle}</p>
        </#if>
    </div>
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <#nested >
        </div>
    </div>
</div>
</#macro>