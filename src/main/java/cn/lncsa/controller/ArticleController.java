package cn.lncsa.controller;

import cn.lncsa.data.model.Article;
import cn.lncsa.data.model.ArticleBody;
import cn.lncsa.data.model.Commit;
import cn.lncsa.data.model.Topic;
import cn.lncsa.services.ArticleServices;
import cn.lncsa.services.CommitServices;
import cn.lncsa.services.TopicServices;
import cn.lncsa.services.UserServices;
import cn.lncsa.view.ArticleCommitDTO;
import cn.lncsa.view.SessionUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by catten on 12/31/16.
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    private ArticleServices articleServices;
    private UserServices userServices;
    private TopicServices topicServices;
    private CommitServices commitServices;

    @Autowired
    private void setArticleServices(ArticleServices articleServices) {
        this.articleServices = articleServices;
    }

    @Autowired
    private void setUserServices(UserServices userServices){
        this.userServices = userServices;
    }

    @Autowired
    private void setTopicServices(TopicServices topicServices) {
        this.topicServices = topicServices;
    }

    @Autowired
    private void setCommitServices(CommitServices commitServices){
        this.commitServices = commitServices;
    }

    /*
    *
    * Control methods
    *
    * */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String articles(@RequestParam(value = "page",defaultValue = "0") int page, Model model){
        model.addAttribute("articles",articleServices.get(new PageRequest(page,5, Sort.Direction.DESC, "createDate"), Article.STATUS_PUBLISHED));
        model.addAttribute("topics",topicServices.mostWeightTopics(10));
        return "articles";
    }

    @RequestMapping(value = "/public/all",method = RequestMethod.GET)
    public @ResponseBody Page<Article> publicArticles(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize
    ){
        return articleServices.get(new PageRequest(page,pageSize,Sort.Direction.DESC, "createDate"), Article.STATUS_PUBLISHED);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String article(
            @PathVariable("id") Integer id,
            @RequestParam(value = "commit_page",defaultValue = "0") int commitPage,
            Model model) {
        Article article = articleServices.get(id);
        if(article == null || article.getId() == null) return "dialogs/articleNotFound";

        model.addAttribute("article",article);
        model.addAttribute("author",article.getAuthor().getName());
        model.addAttribute("modifiedDate",article.getBody().getLatestModifiedDate());
        model.addAttribute("content",article.getBody().getContent());

        Page<Commit> commits = commitServices.getCommitList(id,new PageRequest(commitPage,10, Sort.Direction.DESC,"date"));
        model.addAttribute("commitPage",commits);
        model.addAttribute("commitList", ArticleCommitDTO.convert(commits.getContent()));

        return "article";
    }

    @RequestMapping(value = "/topic/{topicId}",method = RequestMethod.GET)
    public String topic(@PathVariable("topicId") int topicId,@RequestParam(value = "page",defaultValue = "0") int page ,Model model){
        Topic topic = topicServices.get(topicId);
        model.addAttribute("articles",articleServices.getByTopic(topic,new PageRequest(page,5, Sort.Direction.DESC, "createDate"),Article.STATUS_PUBLISHED));
        model.addAttribute("topics",topicServices.mostWeightTopics(10));
        model.addAttribute("current_topic",topic.getTitle());
        return "articles";
    }

    @RequestMapping(value = "/write",method = RequestMethod.GET)
    public String write(Model model){
        model.addAttribute("topic_list",topicServices.getAll(new PageRequest(0,10, Sort.Direction.DESC,"createDate")).getContent());
        return "writeArticle";
    }

    @RequestMapping(value = "/write",method = RequestMethod.POST)
    public String write(@ModelAttribute Article article, @RequestParam("topic_list") List<Integer> topicIds, @RequestParam("article_body") String body, Model model, HttpSession session){
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if(sessionUserBean == null) return "redirect:/user/login";

        article.setAuthor(userServices.get(sessionUserBean.getUserId()));

        articleServices.save(article,new ArticleBody(body));

        if(topicIds != null && topicIds.size() > 0){
            Set<Topic> topicSet = new HashSet<>();
            topicSet.addAll(topicServices.get(topicIds));
            article.setTopics(topicSet);
            articleServices.saveHead(article);
        }

        model.addAttribute("article_title",article.getTitle());

        return "dialogs/articlePosted";
    }

}
