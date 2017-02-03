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
    <div data-role="article-body">
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
        <p data-role="content">${content?string}</p>
    </div>
    <hr>
    <div>
        <h3>Commit</h3>
        <div class="row">
            <div class="col-sm-8">
                <#if (!commitList?? || commitList?size == 0)>
                    <div class="page-header">
                        <h1 align="center">No Commit yet...</h1>
                    </div>
                <#else >
                    <div class="list-group" data-role="commit-bodies">
                    <#list commitList as commit>
                        <@articleTemp.commitItem commitModel=commit/>
                    </#list>
                    </div>
                    <@template.defaultPager total=commitPage.totalPages current=commitPage.number length=10 path="?commit_page=" />
                </#if>
            </div>
            <div class="col-sm-4">
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4>Create a commit</h4>
                    </div>
                    <div class="panel-body">
                        <form method="post" action="/commit/create" data-role="commit-form">
                            <div class="alert alert-success" data-role="announce" style="display: none;">
                                <button type="button" class="close" data-role="cancel" onclick="$commitForm.cancelReply()">
                                    <span>&times;</span>
                                </button><span data-role="announce-text">Reply To :</span>
                            </div>
                            <input type="hidden" data-role="reply-target" name="replyToId" value="">
                            <input type="hidden" name="targetArticleId" value="${article.id}">
                            <div class="form-group">
                                <textarea data-role="content" class="form-control" rows="10" name="content"></textarea>
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
<div class="modal fade">
    <div class="modal-dialog" id="commit-dialog" role="dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">New Commit</h4>
            </div>
            <div class="modal-body">
                <p>One fine body&hellip;</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</@template.body>
<script>
    $(document).ready(function () {
        commitList();

        var articleContentNode = $("[data-role='article-body']").find("[data-role='content']");
        $(articleContentNode).html(marked(articleContentNode.html()));
    });
</script>