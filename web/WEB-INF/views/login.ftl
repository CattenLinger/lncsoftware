<#import "template/temp.ftl" as templage>
<@templage.body title="Login">
    <div class="container">
        <div class="page-header">
            <h1 align="center">Login</h1>
        </div>
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <form method="post" action="/user/login">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input id="username" class="form-control" type="text" name="name">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input id="password" class="form-control" type="password" name="password">
                    </div>
                    <div class="col-md-4 col-md-offset-4">
                        <input type="submit" value="Login" class="btn btn-block btn-success">
                    </div>
                </form>
            </div>
        </div>
    </div>
</@templage.body>