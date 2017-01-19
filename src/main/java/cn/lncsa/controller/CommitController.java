package cn.lncsa.controller;

import cn.lncsa.data.model.Article;
import cn.lncsa.data.model.Commit;
import cn.lncsa.data.model.User;
import cn.lncsa.services.ArticleServices;
import cn.lncsa.services.CommitServices;
import cn.lncsa.services.UserServices;
import cn.lncsa.view.SessionUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String commit(
            @RequestParam("content") String content,
            @RequestParam("targetArticleId") Integer targetArticleId,
            @RequestParam(value = "replyToId",required = false) Integer replyToId,
            HttpSession session){
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if(sessionUserBean == null) return "redirect:/user/login";

        Article article = articleServices.get(targetArticleId);
        if(article == null || article.getId() == null) return "dialog/commitFailed";

        User user = userServices.get(sessionUserBean.getUserId());

        Commit commit = new Commit(content,article,user);
        if(replyToId != null){
            Commit replyTarget = commitServices.get(replyToId);
            if(replyTarget!= null && replyTarget.getId()!=null){
                commit.setReplyTo(replyTarget);
            }else {
                return "dialog/commitFailed";
            }
        }
        commitServices.save(commit);
        return "redirect:/article/" + targetArticleId;
    }
}
