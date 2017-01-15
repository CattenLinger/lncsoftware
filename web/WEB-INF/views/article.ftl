<#import "template/mainTemp.ftl" as template>
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
                <div class="panel panel-success">
                    <div class="panel-body">
                        <div class="media">
                            <a href="#" class="media-left"> <img src="holder.js/48x48"></a>
                            <div class="media-body">
                                <h3 class="media-heading">
                                    Username
                                </h3>
                                <p>Commit at yyyy-MM-dd HH:mm:ss, reply to <a href="#">#id</a></p>
                                <p>Foooooooooooooo content and something other</p>
                            </div>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <div class="pull-left">
                            <div class="btn-group btn-group-sm">
                                <a href="#" class="btn btn-default">Reply</a>
                                <a href="#" class="btn btn-default">Quote</a>
                                <a href="#" class="btn btn-default">...</a>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4>Create a commit</h4>
                    </div>
                    <div class="panel-body">
                        <form method="post" action="">
                            <div class="form-group">
                                <textarea class="form-control" rows="5" name="content"></textarea>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-4 col-xs-offset-4">
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