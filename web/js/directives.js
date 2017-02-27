var lncsaDirectives = angular.module('lncsaDirectives',[]);

lncsaDirectives.run(function () {
    console.debug("Directives loading.");
});

lncsaDirectives.directive('nav-user',function () {
    return {
        restrict : "AE",
        link : function (scope, element, attrs) {
            console.debug(attrs);
        }
    }
});

// lncsaDirectives.directive("hello",function () {
//     return{
//         restrict : "AE",
//         link : function (scope,element,attrs) {
//             element.bind('mouseenter',function () {
//                 console.log("hello");
//             })
//         }
//     }
// });
//
// lncsaDirectives.directive("lnc-main-nav",function () {
//     return{
//         restrict : "AE",
//         link : function (scope, element, attrs) {
//             var children = element.children();
//
//             //var active = attr.lnc-nav-active;
//             for (var i in children){
//                 // item.removeClass("active");
//                 // if(item.attr.lnc-nav-mapTo == active){
//                 //     item.addClass("active");
//                 // }
//                 i.bind('onclick', function () {
//                     console.log("navigator-click-"+i.$attrs.lnc-nav-mapto);
//                 });
//             }
//         }
//     }
// });