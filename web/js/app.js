var lncsaApp = angular.module('lncsaApp',[
    'ui.router',
    'ui.bootstrap',
    'ngAnimate', 
    'lncsaControllers', 
    'lncsaDirectives', 
    'lncsaServices', 
    'lncsaFilters'
]);

lncsaApp.run(function($rootScope, $state, $stateParams) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
});

lncsaApp.config(function($stateProvider,$urlRouterProvider) {
    $urlRouterProvider.when("","/index/home");
    $urlRouterProvider.otherwise("","/index/home");

    $stateProvider.state('index',{
        url:'/index',
        templateUrl : 'tpls/root.html'

    }).state('index.home',{
        url:'/home',
        templateUrl : 'tpls/homePage.html'

    }).state('index.article',{
        url:'/article',
        templateUrl : 'tpls/articlePage.html'

    }).state('index.bulletins',{
        url:'/bulletins',
        templateUrl : 'tpls/bulletinPage.html'

    }).state('index.aboutUs',{
        url:'/aboutUs',
        templateUrl : 'tpls/aboutPage.html'

    }).state('index.login',{
        url:'/login',
        templateUrl : 'tpls/loginPage.html'
    });
});