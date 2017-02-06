package cn.lncsa.controller;

import cn.lncsa.data.model.Article;
import cn.lncsa.data.model.User;
import cn.lncsa.data.model.UserProfile;
import cn.lncsa.services.ArticleServices;
import cn.lncsa.services.TopicServices;
import cn.lncsa.services.UserServices;
import cn.lncsa.view.SessionUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by catten on 12/31/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private UserServices userServices;
    private ArticleServices articleServices;
    private TopicServices topicServices;

    @Autowired
    private void setUserServices(UserServices userServices) {
        this.userServices = userServices;
    }

    @Autowired
    private void setArticleServices(ArticleServices articleServices) {
        this.articleServices = articleServices;
    }

    @Autowired
    private void setTopicServices(TopicServices topicServices){
        this.topicServices = topicServices;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute @Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()) return "dialogs/registerFailed";

        User theUser = userServices.getByName(user.getName());
        if (theUser != null) return "dialogs/registerFailed";

        theUser = userServices.save(user);
        model.addAttribute("reg_username", theUser.getName());
        return "dialogs/registerSuccess";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Model login(@ModelAttribute @Valid User userForm, BindingResult result, Model model, HttpSession session) {

        User user = userServices.getByName(userForm.getName());

        if (result.hasErrors() || user == null || !user.getPassword().equals(userForm.getPassword())) {
            model.addAttribute("result",false);
            return model;
        }

        model.addAttribute("result",true);
        session.setAttribute("session_user",new SessionUserBean(user));

        return model;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Model logout(Model model,HttpSession session) {
        session.removeAttribute("session_user");
        model.addAttribute("result",true);
        return model;
    }

    @RequestMapping("/self")
    public String userCenter(Model model,HttpSession session){
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if(sessionUserBean == null) return "redirect:/user/login";

        User user = userServices.get(sessionUserBean.getUserId());
        model.addAttribute("user_model",user);
        model.addAttribute("user_info",userServices.getProfile(user,true));
        model.addAttribute("user_latest_articles",articleServices.getByUser(user,new PageRequest(0,5, Sort.Direction.DESC, "createDate")).getContent());
        model.addAttribute("user_article_count",articleServices.userArticleCount(user,
                Article.STATUS_PUBLISHED,Article.STATUS_AUDITING,Article.STATUS_BANNED,Article.STATUS_SUBMITTED,Article.STATUS_PRIVATE));
        return "userCenter";
    }

    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public String userProfile(@RequestParam(value = "action", defaultValue = "show") String action, HttpSession session){
        switch (action){
            case "create": {
                SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
                if(sessionUserBean == null) return "redirect:/user/login";

                User user = userServices.get(sessionUserBean.getUserId());
                if(user.getProfile() != null) return "dialogs/createUserProfileFailed";

                userServices.saveProfile(sessionUserBean.getUserId(),new UserProfile());

                return "dialogs/createUserProfileSuccess";
            }

            case "show":
            default:
                return "redirect:/user/self/profile";
        }
    }

    @RequestMapping(value = "/self/profile",method = RequestMethod.GET)
    public String selfProfile(Model model, HttpSession session){
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if(sessionUserBean == null) return "redirect:/user/login";

        User user = userServices.get(sessionUserBean.getUserId());
        model.addAttribute("user_model",user);
        model.addAttribute("user_info",user.getProfile());
        return "userSelfProfile";
    }

    @RequestMapping(value = "/self/profile", method = RequestMethod.POST)
    public String updateProfile(@ModelAttribute UserProfile userProfile, Model model, HttpSession session){
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if(sessionUserBean == null) return "redirect:/user/login";

        User user = userServices.get(sessionUserBean.getUserId());
        UserProfile originProfile = user.getProfile();
        originProfile.setNickname(userProfile.getNickname());
        originProfile.setGender(userProfile.getGender());
        originProfile.setHeadPic(userProfile.getHeadPic());

        userServices.saveProfile(originProfile);
        session.setAttribute("session_user",new SessionUserBean(user));

        return "dialogs/updateProfileSuccess";
    }

    @RequestMapping(value = "/self/profile/secret",method = RequestMethod.POST)
    public String secretProfile(@RequestParam(value = "secret",defaultValue = "false") Boolean secret, HttpSession session){
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if(sessionUserBean == null) return "redirect:/user/login";

        UserProfile userProfile = userServices.getProfile(sessionUserBean.getUserId(),true);
        userProfile.setSecret(secret);
        userServices.saveProfile(userProfile);
        return "dialogs/updateProfileSuccess";
    }
}
