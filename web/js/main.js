/**
 * Created by cattenlinger on 16/9/19.
 */

/*
*
* Create a commit list, and add function to wired committing form
*
* Marking components using "data-role" attribute
*
* Components :
*   commit-form : An form that submit commit. Marking it at a FORM element.
*       There're sub-components:
*           announce : An alert on form's input area to announce the user if it is replying a commit.
*           announce-text : Text on announce
*           reply-target : An INPUT holding an id represented the commit for replying.
*           content : An INPUT or TEXTAREA holding content of commit.
*
*   commit-body: The root element that contained commit items.
*       Attributes :
*           data-id : The id of commit, tagging it on child elements (required)
*           commit-user : The user that shown.
*           content : An element contains commit content
*           button : action buttons.There are three type of buttons in a commit item : reply, quote and delete.
*                    They will be wire functions according to commit-body.
*
*                   button-reply : Set value of "reply-target" as data-id
*                   button-quote : Set value of "reply-target" as data-id and copy content to from's content;
*                   button-delete : Execute delete action.
* */
function commitList(){
    var commitFormNodes = $(document).find("[data-role='commit-form']:first").find("[data-role]");
    if(!commitFormNodes){
        console.error("commit-form not found! will not create the commit list.");
        return;
    }
    console.debug("Commit Form found. " + commitFormNodes.length + " components.");
    var commitForm = {};
    $(commitFormNodes).each(function (index, element) {
        var announceText;
        switch($(element).attr("data-role")){
            case "announce":
                commitForm.announce = {
                    show : function () {
                        $(element).show();
                    },
                    hide : function () {
                        $(element).hide();
                    },
                    setText : function (text) {
                        $(element).find("[data-role='announce-text']:first").text(text);
                    }
                };
                break;

            case "reply-target":
                commitForm.setReplyTarget = function (id) {
                    $(element).attr("value",id);
                };

                commitForm.clearReplyTarget = function () {
                    $(element).attr("value","");
                };

                break;

            case "content":
                commitForm.content = {
                    set : function (text) {
                        $(element).val(text);
                    },
                    get : function () {
                        return $(element).val();
                    }
                };
                break;

            case "cancel":
                commitForm.cancelReply = function () {
                    this.clearReplyTarget();
                    this.announce.hide();
                };
                break;

            case "announce-text":
                announceText = element;
                break;

            default:
                break;
        }
    });

    window.$commitForm = commitForm;

    var commitBodyNodes = $(document).find("[data-role='commit-bodies']").children("[data-id]");
    console.debug("There are " + commitBodyNodes.length + " commits.");
    //Iterating each commit, prepare functions for each buttons.
    var commit_body = [];
    $(commitBodyNodes).each(function (index, element) {
        var commitItem = {
            id : $(element).attr("data-id"),
            index : index,
            content : null,
            username : null,
            cutContent : function (size) {
               if(commitItem.content.length > size){
                   return commitItem.content.substring(0,size) + "...";
               }
               return commitItem.content;
            }
        };

        $(element).find("[data-role]").each(function (index,element) {
            switch ($(element).attr("data-role")){
                case "content":
                    commitItem.content = $(element).text();
                    break;
                case "commit-user":
                    commitItem.username = $(element).text();
                    break;
                default:
                    break;
            }
        });

        console.debug("The " + (commitItem.index + 1) + " commit body.");
        console.debug("Commit id : " + commitItem.id);
        console.debug("Username : " + commitItem.username);
        console.debug(commitItem.content);

        console.debug("Buttons :");
        //Add functions on each buttons
        $(element).find("[data-role|='button']").each(function (index, element) {

            switch ($(element).attr("data-role")){

                case "button-reply":
                    console.debug( "\t\t\t" + index + " : Reply Button.");
                    $(element).click(function () {
                        commitForm.setReplyTarget(commitItem.id);
                        commitForm.announce.setText("Reply To : " + commitItem.username);
                        commitForm.announce.show();

                        console.debug("Reply operate");
                    });
                    break;

                case "button-quote":
                    console.debug( "\t\t\t" + index + " : Quote Button.");
                    $(element).click(function () {
                        commitForm.setReplyTarget(commitItem.id);
                        commitForm.announce.setText("Reply To : " + commitItem.username);
                        commitForm.content.set("> " + commitItem.cutContent(20) + "\n\n");
                        commitForm.announce.show();

                        console.debug("Quote operate;");
                    });
                    break;
                case "button-delete":
                    console.debug( "\t\t\t" + index + " : Delete Button.");
                    $(element).click(function () {
                        console.debug("Delete operate");
                    });
                    break;
                default:
                    break;
            }
        });
    });
}