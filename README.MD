#软件园协会网站

##写点什么做介绍？

给师弟们写个网站什么的 ˊ_>ˋ，做了点微小的工作

##怎么跑起来？

请先安装 maven， 然后终端里跑起来这句话

    mvn package

好了，在 target 文件夹里面就有 war 包了，自己拿到心意的 servlet 容器里跑起来就好了。

对了别忘记查看 applicationContext.xml 里面有关数据库的配置，配置好链接并创建好数据库。
不需要建表的， Hibernate 会帮你弄好这些琐碎事 ˊ_>ˋ

##主要技术

后端技术

- Spring MVC
- JPA
- Spring
- Freemarker

前端技术

- Bootstrap

##参考资料

- [Bootstrap 中文文档](http://v3.bootcss.com/)
- [Bootstrap - Buttons inside List Group Item](http://stackoverflow.com/questions/38373842/bootstrap-buttons-inside-list-group-item) 
- [Animate.css](https://daneden.github.io/animate.css/)
- [Holder.js](http://holderjs.com/)
- [Font Awesome](http://fontawesome.io/)
- [Spring验证的错误返回------BindingResult](http://zyjustin9.iteye.com/blog/2002606)
- [Hibernate Validation各注解的用法](http://tcrct.iteye.com/blog/1329823)
- [正则表达式](https://zh.wikipedia.org/wiki/%E6%AD%A3%E5%88%99%E8%A1%A8%E8%BE%BE%E5%BC%8F)
- [FreeMarker教程](http://relive123-yahoo-com-cn.iteye.com/blog/818013)
- [FreeMarker 获取页面request、session](http://blog.csdn.net/feiyu8607/article/details/6557159)
- [FreeMarker 中对空字符串"" 和 null 的判断](http://www.liuqianfei.com/article/87f8317eb7ee4d3f80cd41e64d4e6240)
- [【原创】纯干货，Spring-data-jpa详解，全方位介绍](http://www.cnblogs.com/dreamroute/p/5173896.html)
- [JPA select from collection with many-to-many association](http://stackoverflow.com/questions/15153877/jpa-select-from-collection-with-many-to-many-association)

还有一只懒喵

##唠叨话

强烈谴责腾讯云域名转移时把我域名卡在新网然后不承认我已经备案了的域名 (╯‵□′)╯︵┻━┻