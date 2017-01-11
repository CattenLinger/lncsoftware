<#import "template/mainTemp.ftl" as templates>
<#import "template/articleTemp.ftl" as articleTemp>
<#import "template/userTemp.ftl" as userTemp>
<@templates.body title="User Center">
<div class="container">
    <div class="page-header">
        <h1>Software Association <small>at LingNan College</small></h1>
    </div>
    <@templates.mainNav activeOrder=4 />
    <div class="row">
        <div class="col-md-8">
            <div class="col-md-4">
                <div class="panel panel-primary">
                    <#if (user_latest_articles?? && user_latest_articles?size > 0)>
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-4">
                                    <i class="fa fa-book fa-5x"> </i>
                                </div>
                                <div class="col-xs-8 text-right">
                                    <div class="text-huge">${user_article_count}</div>
                                    <div>Total articles</div>
                                </div>
                            </div>
                        </div>
                        <a href="/article/write">
                            <div class="panel-footer">
                                <span class="pull-left">See all</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"> </i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                        <div class="list-group">
                            <#list user_latest_articles as a >
                                <a class="list-group-item" href="/article/${a.id}">${a.title}</a>
                            </#list>
                        </div>
                    <#else >
                        <div class="panel-body" align="center">
                            <h2>You have no article yet..</h2>
                            <hr>
                            <a href="/article/write" class="btn btn-primary">Create an article</a>
                        </div>
                    </#if>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-4">
                                <i class="fa fa-pencil fa-5x"></i>
                            </div>
                            <div class="col-xs-8 text-right">
                                <div class="text-huge">0</div>
                                <div>Topic create</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <@userTemp.rightPanel userInfo=user_info!"" userModel=user_model active=0/>
        </div>
    </div>
</div>
</@templates.body>