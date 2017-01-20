<#import "mainTemp.ftl" as main>
<#macro articleListItem item imglink>
<a class="list-group-item" href="/article/${item.id}">
    <div class="media">
        <div class="media-left">
            <img src="${imglink!""}">
        </div>
        <div class="media-body">
            <h4 class="media-heading">${item.title!""}</h4>
            <p>${item.subtitle!""}</p>
        </div>
        <div align="right"><small>at ${item.createDate}</small></div>
    </div>
</a>
</#macro>

<#macro bulletinListItem item>
<a class="list-group-item" href="${item.link!"#"}">
    <div class="media">
        <#if (item.imageLink?? && item.imageLink != "")>
        <div class="media-left">
            <img src="${item.imageLink}" width="64pt" height="64pt">
        </div>
        </#if>
        <div class="media-body">
            <p>${item.content?html}</p>
        </div>
        <div align="right"><small>posted at ${item.createDate}, tagged as</small> <span class="label label-info">${item.type}</span></div>
    </div>
</a>
</#macro>

<#macro mediaGroupListPageItem itemset type>
<div class="list-group">
    <#if ( itemset?? && (itemset.content?size > 0) ) >
        <#switch type>
            <#case "article">
                <#list itemset.content as i>
                    <@articleListItem item=i imglink="holder.js/64x64/gray" />
                </#list>
                <#break>
            <#case "bulletin">
                <#list itemset.content as i>
                    <@bulletinListItem item=i />
                </#list>
                <#break>
        </#switch>
    <#else>
        <a class="list-group-item disabled" href="#">No Content here...</a>
    </#if>
</div>
</#macro>

<#macro mediaGroupListItem itemset type>
<div class="list-group">
    <#if ( itemset?? && (itemset?size > 0) ) >
        <#switch type>
            <#case "article">
                <#list itemset as i>
                    <@articleListItem item=i imglink="holder.js/64x64/gray" />
                </#list>
                <#break>
            <#case "bulletin">
                <#list itemset as i>
                    <@bulletinListItem item=i />
                </#list>
                <#break>
        </#switch>
    <#else>
        <a class="list-group-item disabled" href="#">No Content here...</a>
    </#if>
</div>
</#macro>

<#macro bulletinBoardBody bulletin title>
<div class="panel-body tab-content">
    <span style="font-size: 20pt;">${title}</span>
    <#if (bulletin.link?? && bulletin.link != "")>
        <a class="btn btn-success pull-right" href="${bulletin.link!""}">Detail &gt;&gt;</a>
    </#if>
    <hr>
    <div class="media">
        <#if bulletin.imageLink?? && bulletin.imageLink != "">
            <div class="media-left">
                <img src="${bulletin.imageLink}" onload="DrawImage(this,64,64)" >
            </div>
        </#if>
        <div class="media-right">
            <p>${bulletin.content}</p>
        </div>
        <p align="right">
            at ${bulletin.createDate} tagged as <span class="label label-info">${bulletin.type}</span></p>
    </div>
</div>
</#macro>

<#macro commitItem commitModel>
<div class="list-group-item" data-id="${commitModel.id}">
    <div class="media">
        <a href="/user/${commitModel.userId!""}" class="media-left"> <img src="${commitModel.headPic!"holder.js/48x48"}" onload="DrawImage(this,48,48)"></a>
        <div class="media-body">
            <h3 class="media-heading" data-role="commit-user">${commitModel.shownName}</h3>
            <p>at ${commitModel.createDate}
                <#if (commitModel.replyTo??)>
                    , reply to <a href="/commit/${commitModel.replyTo!""}"><@main.cutInlineStringInSize text=commitModel.replyToContent length=20 suffix="..."/></a>
                </#if>
            </p>
            <p data-role="content">${commitModel.content?html?replace("\n","<br>")}</p>
        </div>
        <div class="btn-toolbar pull-right">
            <div class="btn-group btn-group-sm">
                <button class="btn btn-success" data-role="button-reply">Reply</button>
                <button class="btn btn-success" data-role="button-quote">Quote</button>
            </div>
            <#if (Session.session_user?? && Session.session_user.userId == commitModel.userId)>
            <div class="btn-group btn-group-sm">
                <button class="btn btn-danger" data-role="button-delete">Delete</button>
            </div>
            </#if>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
</#macro>