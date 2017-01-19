<#import "template/mainTemp.ftl" as template>
<#import "template/articleTemp.ftl" as articleTemp>
<@template.body title="Article">
<div class="container">
    <div class="page-header">
        <h1>Software Association
            <small>at LingNan College</small>
        </h1>
    </div>
    <@template.mainNav activeOrder=1/>
    <div class="page-header">
        <h2>${article.title}</h2>
        <p>Author : ${author!""}, wrote at ${article.createDate!""}<#if modifiedDate??> , latest modified
            at ${modifiedDate!""} </#if></p>
        <#if (article.topics?? && article.topics?size > 0)>
            <p>Topics :
                <#list article.topics as topic>
                    <label class="label label-info">${topic.title}</label>
                </#list>
            </p>
        </#if>
    </div>
    <section>${content?html?replace("\n","<br>")}</section>
    <hr>
    <div>
        <h3>Commit</h3>
        <hr>
        <div class="row">
            <div class="col-sm-8">
                <#if (!commitList?? || commitList?size == 0)>
                    <div class="page-header">
                        <h1 align="center">No Commit yet...</h1>
                    </div>
                <#else >
                    <div class="list-group">
                    <#list commitList as commit>
                        <@articleTemp.commitItem commitModel=commit/>
                    </#list>
                    </div>
                    <@template.defaultPager total=commitPage.totalPages current=commitPage.number length=10 path="?commitPage=" />
                </#if>
            </div>
            <div class="col-sm-4">
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4>Create a commit</h4>
                    </div>
                    <div class="panel-body">
                        <form method="post" action="/commit/create" id="commit-form">
                            <input type="hidden" name="replyToId" value="">
                            <input type="hidden" name="targetArticleId" value="${article.id}">
                            <div class="form-group">
                                <textarea class="form-control" rows="5" name="content"></textarea>
                            </div>
                            <div class="form-group">
                                <div class="col-lg-4 col-lg-offset-4">
                                    <input type="submit" class="btn btn-success btn-block" value="Submit">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@template.body>