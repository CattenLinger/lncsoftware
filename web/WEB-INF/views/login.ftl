<#import "template/mainTemp.ftl" as template>
<@template.body title="Login">
<div class="container">
    <div class="page-header">
        <h1 align="center">Login</h1>
    </div>
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <form data-role="login-form">
                <div class="form-group">
                    <label for="name">Username</label>
                    <input id="name" class="form-control" type="text" data-role="username" name="name">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input id="password" class="form-control" type="password" data-role="password" name="password">
                </div>
                <div class="col-md-4 col-md-offset-4">
                    <button type="button" class="btn btn-block btn-success" data-role="login-submit">Submit</button>
                    <button type="button" class="btn btn-default btn-block" id="loginBack">Back</button>
                </div>
            </form>
        </div>
    </div>
</div>
<@template.alertDialog id="resultDialog"/>
</@template.body>
<script>
    $(document).ready(function () {

        var dialog = $('#resultDialog');
        dialog.modal({
            backdrop : "static", keyboard : false, show : false
        });

        makeLoginForm(makeDialogModel(dialog));

        $(document.getElementById("loginBack")).click(
            function () {
                window.history.back();
            }
        );
    });
</script>