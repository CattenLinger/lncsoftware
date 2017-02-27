var lncsaCtrls = angular.module('lncsaControllers',[]);

lncsaCtrls.controller('IndexController', ['$scope','$state',"bulletinServices",
    function($scope,$state,bulletinServices){
        bulletinServices.opBulletin(function (data) {
            $scope.bulletinBoard = data;
        });

        $scope.showBoard = false;

        $scope.$watch("bulletinBoard",function(oldVal,newVal){
            $scope.showBoard = $scope.bulletinBoard != null;
        });
	}]);

lncsaCtrls.controller('IndexNavController',['$scope','$state',"userServices",
    function ($scope, $state, userServices) {

        $scope.loginInfo = {
            wasLogin : false,
            userInfo : {}
        };

        userServices.startLoadCurrentUserInfo($scope.loginInfo);

        $scope.logout = function () {
            userServices.logout($scope.loginInfo);
        };
    }
]);

lncsaCtrls.controller("LoginController",["$scope","$state","userServices",
    function ($scope,$state,userServices) {

        $scope.loginForm = {
            username : "",
            password : ""
        };

        $scope.login = function () {
            userServices.login($scope.loginForm.username,$scope.loginForm.password,function (msg) {
                console.debug(msg);
            });
        }

    }
]);

/**
 * 这里接着往下写，如果控制器的数量非常多，需要分给多个开发者，可以借助于grunt来合并代码
 */