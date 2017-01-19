<#import "../template/mainTemp.ftl" as template>
<@template.body title=mainTitle>
    <@template.dialog mainTitle=mainTitle subtitle=subTitle>
        <#list buttonList as button>
        <a class="btn btn-block btn-${button.role}" href="${button.link}">${button.title}</a>
        </#list>
    </@template.dialog>
</@template.body>