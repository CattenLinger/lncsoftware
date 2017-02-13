package cn.lncsa.controller;

import cn.lncsa.data.model.Article;
import cn.lncsa.data.model.Commit;
import cn.lncsa.data.model.User;
import cn.lncsa.services.ArticleServices;
import cn.lncsa.services.CommitServices;
import cn.lncsa.services.UserServices;
import cn.lncsa.view.CommitDTO;
import cn.lncsa.view.SessionUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by cattenlinger on 2017/1/16.
 */
@Controller
@RequestMapping("/commit")
public class CommitController {

    private CommitServices commitServices;
    private UserServices userServices;
    private ArticleServices articleServices;

    @Autowired
    private void setCommitServices(CommitServices commitServices){
        this.commitServices = commitServices;
    }

    @Autowired
    private void setUserServices(UserServices userServices) {
        this.userServices = userServices;
    }

    @Autowired
    private void setArticleServices(ArticleServices articleServices) {
        this.articleServices = articleServices;
    }

    /**
     *
     * Get commits from an article
     *
     * @param articleId
     * @param page
     * @param pageCount
     * @param model
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Model get(
            @RequestParam(value = "target") Integer articleId,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "pageCount",defaultValue = "10") int pageCount,
            Model model)
    {
        Page<Commit> commits = commitServices.getCommitList(articleId,new PageRequest(page,pageCount, Sort.Direction.DESC,"date"));
        model.addAttribute("commitPage",commits);
        model.addAttribute("commitList", CommitDTO.convert(commits.getContent()));
        return model;
    }

    /**
     *
     * Get a commit
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Model get(@PathVariable("id") int id,Model model){
        Commit commit = commitServices.get(id);
        if(commit != null){
            model.addAttribute("success",true);
            model.addAttribute("user",commit.getUser().getId());
            if(commit.getReplyTo() != null){
                model.addAttribute("replyTo",commit.getReplyTo().getId());
            }
            return model;
        }
        model.addAttribute("success",false);
        return model;
    }

    /**
     *
     * Creating commit for an article
     *
     * @param content
     * @param targetArticleId
     * @param replyToId
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Model create(
            @RequestParam(value = "targetArticleId") Integer targetArticleId,
            @RequestParam(value = "replyToId",required = false) Integer replyToId,
            @RequestParam(value = "content") String content,
            Model model,
            HttpSession session){
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if(sessionUserBean == null) {
            return null;
        }

        Article article = articleServices.get(targetArticleId);
        if(article == null || article.getId() == null) return null;

        User user = userServices.get(sessionUserBean.getUserId());

        Commit commit = new Commit(content,article,user);
        if(replyToId != null){
            Commit replyTarget = commitServices.get(replyToId);
            if(replyTarget!= null){
                commit.setReplyTo(replyTarget);
            }else return null;
        }
        commitServices.save(commit);
        model.addAttribute("success",true);
        return model;
    }

    /**
     *
     * Delete a commit
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Model delete(@PathVariable("id")int id, Model model){
        commitServices.delete(id);
        model.addAttribute("success",true);
        return model;
    }
}
