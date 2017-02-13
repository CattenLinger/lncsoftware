package cn.lncsa.controller;

import cn.lncsa.data.model.Article;
import cn.lncsa.data.model.Topic;
import cn.lncsa.services.ArticleServices;
import cn.lncsa.services.TopicServices;
import cn.lncsa.services.UserServices;
import cn.lncsa.view.ArticleDTO;
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
import java.util.List;

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

        if (article.getBody().getLatestModifiedDate() == null)
            model.addAttribute("modifiedDate", article.getCreateDate());
        else model.addAttribute("modifiedDate", article.getBody().getLatestModifiedDate());

        model.addAttribute("content", article.getBody().getContent());

        return model;
    }

    /**
     * Create an article
     *
     * @param topicIds
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Model create(
            ArticleDTO articleDTO,
            @RequestParam(value = "topic_list", required = false) List<Integer> topicIds,
            Model model,
            HttpSession session) {
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if (sessionUserBean == null) {
            model.addAttribute("success", false);
            return model;
        }

        if (articleDTO.getStatus().matches("auditing||banned")) {
            model.addAttribute("success", false);
            return model;
        }

        articleDTO.setAuthor(userServices.get(sessionUserBean.getUserId()));
        articleDTO.setModifiedDate(new Date());
        articleDTO.setCreateDate(new Date());

        if (topicIds != null && topicIds.size() > 0) {
            articleDTO.setTopics(topicIds, topicServices);
        }

        Article article = articleDTO.parse();

        articleServices.save(article, article.getBody());

        model.addAttribute("success", true);

        return model;
    }


    /**
     * Update an article
     *
     * @param topicIds
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Model update(
            @ModelAttribute ArticleDTO articleDTO,
            @RequestParam(value = "topic_list", required = false) List<Integer> topicIds,
            Model model,
            HttpSession session) {

        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if (sessionUserBean == null) {
            model.addAttribute("success", false);
            return model;
        }

        if (articleDTO.getStatus().matches("auditing||banned")) {
            model.addAttribute("success", false);
            return model;
        }

        Article origin = articleServices.get(articleDTO.getId());
        if (origin == null
                || !sessionUserBean.getUserId().equals(origin.getAuthor().getId())
                || origin.getStatus().matches("auditing||banned")) {
            model.addAttribute("success", false);
            return model;
        }

        articleDTO.setTopics(topicIds, topicServices);
        articleDTO.setModifiedDate(new Date());

        articleServices.save(articleDTO.merge(origin));

        model.addAttribute("success", true);

        return model;
    }

    /**
     * Update article status.
     *
     * @param id
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public Model updateStatus(
            @RequestParam("id") int id,
            @RequestParam("status") String status,
            Model model) {
        if (status.matches("^(draft|submitted|published|private|delete|banned|auditing)$")) {
            Article article = articleServices.get(id);
            article.setStatus(status);
            articleServices.save(article);
            model.addAttribute("success", true);
            return model;
        }
        model.addAttribute("success", false);
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
