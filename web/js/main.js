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
*
* APIs :
*   After the commit from generated, a "$commitForm" object will export to window
*
*   $commitForm :
*       announce :
*           show() : Show the announce banner
*           hide() : Hide the announce banner
*           setText(text) : Set text on the announce banner
*
*        setReplyTarget(id) : Set replyToId param
*        clearReplyTarget() : Clear the param
*
*        content:
*           set(text) : change the content
*           get() : get text in the content
*
*        cancelReply() : Do things that cancel reply status
*
* */
function commitList(){
    var commitFormNodes = $(document).find("[data-role='commit-form']:first").find("[data-role]");
    if(!commitFormNodes){
        console.error("commit-form not found! will not create the commit list.");
        return;
    }

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
                    $(element).html(marked(commitItem.content));
                    break;
                case "commit-user":
                    commitItem.username = $(element).text();
                    break;
                default:
                    break;
            }
        });

        //Add functions on each buttons
        $(element).find("[data-role|='button']").each(function (index, element) {

            switch ($(element).attr("data-role")){

                case "button-reply":
                    $(element).click(function () {
                        commitForm.setReplyTarget(commitItem.id);
                        commitForm.announce.setText("Reply To : " + commitItem.username);
                        commitForm.announce.show();
                    });
                    break;

                case "button-quote":
                    $(element).click(function () {
                        commitForm.setReplyTarget(commitItem.id);
                        commitForm.announce.setText("Reply To : " + commitItem.username);
                        commitForm.content.set(">" + commitItem.cutContent(20) + "\n\n");
                        commitForm.announce.show();
                    });
                    break;
                case "button-delete":
                    $(element).click(function () {
                        alert("Waiting for implement >_< ");
                        console.debug("Delete operate");
                    });
                    break;
                default:
                    break;
            }
        });
    });
}

function makeDialogModel(dialogNode){
    var _dialogNode = $(dialogNode);

    var dialogModel = {};

    _dialogNode.find("[data-role|='dialog']").each(function (index, element) {
        var _element = $(element);
        switch(_element.attr("data-role")){
            case "dialog-title":
                console.debug("Dialog title found.");
                dialogModel.setTitle = function (text) {
                    _element.text(text);
                };
                break;

            case "dialog-content":
                console.debug("Dialog content found");
                dialogModel.setContent = function (content) {
                    _element.html(content);
                };
                break;

            case "dialog-positive":
                console.debug("Dialog positive button found");
                dialogModel.positiveButton = element;
                break;

            case "dialog-negative":
                console.debug("Dialog negative button found");
                dialogModel.negativeButton = element;
                break;

            default:
                break;
        }
    });

    dialogModel.show = function () {
        _dialogNode.modal("show");
    };

    dialogModel.hide = function () {
        _dialogNode.modal("hide");
    };

    return dialogModel;
}

function makeLoginForm(dialogModel){

    var loginForm = {
        name : "",
        password : ""
    };

    if(!window.lncsa) window.lncsa = {};
    window.lncsa.loginForm = loginForm;

    $(document).find("[data-role='login-form']:first").find("[data-role]").each(function (index, element) {
        var _element = $(element);
        switch (_element.attr("name")){
            case "name":
            case "username":
                console.debug("Login Form : username field found");
                _element.change(function () {
                    window.lncsa.loginForm.name = _element.val();
                });
                break;

            case "password":
                //loginForm.password = md5(md5(_element.val()) + makeLoginForm.username);
                console.debug("Login Form : password field found");
                _element.change(function () {
                    window.lncsa.loginForm.password = _element.val();
                });
                break;

            default:
                if(_element.attr("data-role") == "login-submit"){
                    console.debug("Login Form : submit button found");
                    _element.click(function () {
                        console.debug(loginForm);
                        _element.text("Submitting...");
                        _element.addClass("disabled");
                        $.post("/user/login", loginForm, "json").success(
                            function (data) {
                                console.debug(data);
                                if(data.result){
                                    console.debug("login success : " + data.user.name);

                                    dialogModel.setTitle("Login Success");
                                    dialogModel.setContent("Welcome " + data.user.name);
                                    dialogModel.show();
                                    $(dialogModel.positiveButton).click(function () {
                                        window.location.href = "/";
                                    });

                                }else {
                                    console.debug("login failed");

                                    dialogModel.setTitle("Login Failed");
                                    dialogModel.setContent("Login failed, may you input wrong username or password");
                                    $(dialogModel.positiveButton).click(function () {
                                        dialogModel.hide();
                                    });
                                    dialogModel.show();

                                    _element.text("Submit");
                                    _element.removeClass("disabled");
                                }
                            }
                        ).error(
                            function () {
                                //
                                console.debug("login request failed");
                                _element.text("ERROR");
                                _element.removeClass("btn-success");
                                _element.addClass("btn-warning");
                            }
                        );
                    });
                }
                break;
        }
    });
}