package cn.lncsa.controller;

import cn.lncsa.data.model.Article;
import cn.lncsa.data.model.ArticleBody;
import cn.lncsa.data.model.Topic;
import cn.lncsa.data.model.User;
import cn.lncsa.services.ArticleServices;
import cn.lncsa.services.CommitServices;
import cn.lncsa.services.TopicServices;
import cn.lncsa.services.UserServices;
import cn.lncsa.view.SessionUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
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

    @Autowired
    private void setArticleServices(ArticleServices articleServices) {
        this.articleServices = articleServices;
    }

    @Autowired
    private void setUserServices(UserServices userServices) {
        this.userServices = userServices;
    }

    @Autowired
    private void setTopicServices(TopicServices topicServices) {
        this.topicServices = topicServices;
    }

    /*
    *
    * Control methods
    *
    * */

    /**
     * Get public articles
     *
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/public", method = RequestMethod.GET)
    public
    @ResponseBody
    Page<Article> get(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        return articleServices.get(
                new PageRequest(
                        page,
                        pageSize,
                        Sort.Direction.DESC,
                        "createDate"),
                Article.STATUS_PUBLISHED);
    }

    /**
     * Get one article
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Model get(
            @PathVariable("id") Integer id,
            Model model) {
        Article article = articleServices.get(id);
        if (article == null) return model;

        model.addAttribute("head", article);
        model.addAttribute("author", article.getAuthor().getName());

        if(article.getBody().getLatestModifiedDate() == null)
            model.addAttribute("modifiedDate", article.getCreateDate());
        else model.addAttribute("modifiedDate", article.getBody().getLatestModifiedDate());

        model.addAttribute("content", article.getBody().getContent());

        return model;
    }

    /**
     * Get articles by topic
     *
     * @param topicId
     * @param page
     * @param pageCount
     * @param model
     * @return
     */
    @RequestMapping(value = "/topic/{topicId}", method = RequestMethod.GET)
    public Model getByTopic(
            @PathVariable("topicId") int topicId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageCount", defaultValue = "10") int pageCount,
            Model model) {
        if (pageCount > 30) pageCount = 30;

        Topic topic = topicServices.get(topicId);
        if (topic == null) return model;

        model.addAttribute("articles", articleServices.getByTopic(topic, new PageRequest(
                page,
                pageCount,
                Sort.Direction.DESC,
                "createDate"), Article.STATUS_PUBLISHED));

        return model;
    }

    /**
     * Create an article
     *
     * @param article
     * @param topicIds
     * @param body
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Model create(
            @ModelAttribute Article article,
            @RequestParam(value = "topic_list",required = false) List<Integer> topicIds,
            @RequestParam("article_body") String body,
            Model model,
            HttpSession session) {
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if (sessionUserBean == null) {
            model.addAttribute("success", false);
            return model;
        }

        article.setAuthor(userServices.get(sessionUserBean.getUserId()));

        if (topicIds != null && topicIds.size() > 0) {
            article.setTopics(new HashSet<>(topicServices.get(topicIds)));
        }

        articleServices.save(article, new ArticleBody(body));

        model.addAttribute("success", true);

        return model;
    }


    /**
     *
     * Update an article
     *
     * @param article
     * @param topicIds
     * @param body
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Model update(
            @PathVariable("id") int id,
            @ModelAttribute Article article,
            @RequestParam(value = "topic_list",required = false) List<Integer> topicIds,
            @RequestParam("article_body") String body,
            Model model,
            HttpSession session) {

        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if (sessionUserBean == null) {
            model.addAttribute("success", false);
            return model;
        }

        Article origin = articleServices.get(id);
        if(origin == null || !sessionUserBean.getUserId().equals(origin.getAuthor().getId())){
            model.addAttribute("success",false);
            return model;
        }

        origin.getBody().setContent(body);
        origin.getBody().setLatestModifiedDate(new Date());

        if (topicIds != null && topicIds.size() > 0) {
            origin.setTopics(new HashSet<>(topicServices.get(topicIds)));
        }else {
            origin.setTopics(null);
        }

        articleServices.save(origin);

        model.addAttribute("success",true);

        return model;
    }

    /**
     * Delete an article
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Model delete(@PathVariable("id") int id, Model model) {
        articleServices.delete(id);
        model.addAttribute("success", true);
        return model;
    }

}
