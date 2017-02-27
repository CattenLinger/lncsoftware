var lncsaServices = angular.module('lncsaServices', []);

lncsaServices.service('userServices', ['$http',
    function ($http) {
        var userInfo = {};

        return {
            startLoadCurrentUserInfo : function (loginInfo) {
                $http({
                    method : 'GET',
                    url : '/user/login'
                }).success(function (data, status, header, config) {
                    loginInfo.hasLogin = data.hasLogin;
                    loginInfo.userInfo = data.userInfo;

                    userInfo = loginInfo;
                });
            },
            logout : function (loginInfo) {
                $http({
                    method : 'GET',
                    url : '/user/logout'
                }).success(function (data) {
                    loginInfo.hasLogin = false;
                    loginInfo.userInfo = {};

                    userInfo = loginInfo;
                });
            },
            login : function(username, password, loginInfo, onError){
                return $http({
                    method : 'POST',
                    url : '/user/login',
                    data : {
                        name : username,
                        password : password
                    }
                }).success(function (data, status, header, config) {
                    if(data){
                        if(data.result){
                            userServices.startLoadCurrentUserInfo(loginInfo);
                        }else{
                            onError();
                        }
                    }
                }).error(function (msg) {
                    onError();
                });
            },
            userInfo : userInfo
        }
    }
]);

lncsaServices.service('bulletinServices',['$http',
    function ($http) {

        var bulletinContent = null;

        return{
            opBulletin : function (callBack) {
                if(bulletinContent == null){
                    $http({
                        method : 'GET',
                        url : '/bulletin/important/latest'
                    }).success(function (data) {
                        bulletinContent = data.bulletin;
                        callBack(bulletinContent);
                    });
                } else callBack(bulletinContent);
            }
        }
    }
]);

// bookStoreServices.service('bookStoreService_1', ['$scope',
//     function($scope) {}
// ]);

// bookStoreServices.service('bookStoreService_2', ['$scope',
//     function($scope) {}
// ]);
