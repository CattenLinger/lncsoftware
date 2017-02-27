package cn.lncsa.controller;

import cn.lncsa.data.model.Article;
import cn.lncsa.data.model.User;
import cn.lncsa.data.model.UserProfile;
import cn.lncsa.services.ArticleServices;
import cn.lncsa.services.UserServices;
import cn.lncsa.view.SessionUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private void setUserServices(UserServices userServices) {
        this.userServices = userServices;
    }

    @Autowired
    private void setArticleServices(ArticleServices articleServices) {
        this.articleServices = articleServices;
    }

    /**
     * Register an user
     *
     * @param user
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Model register(
            @ModelAttribute @Valid User user,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("success", false);
            return model;
        }

        User theUser = userServices.getByName(user.getName());
        if (theUser != null) {
            model.addAttribute("success", false);
            return model;
        }

        userServices.save(user);
        model.addAttribute("success", true);
        return model;
    }

    /**
     * Login
     *
     * @param userForm
     * @param result
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Model login(@Valid User userForm, BindingResult result, Model model, HttpSession session) {

        User user = userServices.getByName(userForm.getName());

        if (result.hasErrors() || user == null || !user.getPassword().equals(userForm.getPassword())) {
            model.addAttribute("result", false);
            return model;
        }

        model.addAttribute("result", true);
        session.setAttribute("session_user", new SessionUserBean(user));

        return model;
    }

    @RequestMapping("/login")
    public Model login(Model model, HttpSession session){
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if(sessionUserBean == null){
            model.addAttribute("wasLogin", false);
            return model;
        }
        model.addAttribute("wasLogin",true);
        model.addAttribute("userInfo",sessionUserBean);
        return model;
    }

    /**
     * Logout
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Model logout(Model model, HttpSession session) {
        session.removeAttribute("session_user");
        model.addAttribute("result", true);
        return model;
    }

    /**
     * User's homepage data, load them all at same time
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/self/dashboard")
    public Model dashboard(Model model, HttpSession session) {
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if (sessionUserBean == null) {
            model.addAttribute("success", false);
            return model;
        }

        User user = userServices.get(sessionUserBean.getUserId());

        model.addAttribute("user_model", user);
        model.addAttribute("user_info", userServices.getProfile(user, true));

        model.addAttribute("user_latest_articles", articleServices.getByUser(user, new PageRequest(
                0,
                5,
                Sort.Direction.DESC,
                "createDate")).getContent());

        model.addAttribute("user_article_count", articleServices.userArticleCount(user,
                Article.STATUS_PUBLISHED,
                Article.STATUS_AUDITING,
                Article.STATUS_BANNED,
                Article.STATUS_SUBMITTED,
                Article.STATUS_PRIVATE));

        return model;
    }

    /**
     * Get user self profile
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/self/profile", method = RequestMethod.GET)
    public Model getSelfProfile(Model model, HttpSession session) {
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if (sessionUserBean == null) {
            model.addAttribute("success", false);
            return model;
        }

        User user = userServices.get(sessionUserBean.getUserId());
        model.addAttribute("success", true);
        model.addAttribute("user", user);
        model.addAttribute("data", user.getProfile());

        return model;
    }

    /**
     * Get user profile
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}/profile", method = RequestMethod.GET)
    public Model getProfile(@PathVariable("id") int id, Model model) {
        User user = userServices.get(id);
        if (user == null) {
            model.addAttribute("success", false);
            return model;
        }

        model.addAttribute("success", true);
        UserProfile userProfile = user.getProfile();
        if (userProfile != null && userProfile.getSecret()) userProfile = null;
        model.addAttribute("user", user);
        model.addAttribute("data", userProfile);
        return model;
    }

    /**
     * Create user profile
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/self/profile", method = RequestMethod.POST)
    public Model createProfile(Model model, HttpSession session) {
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if (sessionUserBean == null) {
            model.addAttribute("success", false);
            return model;
        }

        User user = userServices.get(sessionUserBean.getUserId());
        if (user.getProfile() != null) {
            model.addAttribute("success", false);
            return model;
        }

        userServices.saveProfile(sessionUserBean.getUserId(), new UserProfile());
        model.addAttribute("success", true);

        return model;
    }

    /**
     * Update user self profile
     *
     * @param userProfile
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/self/profile", method = RequestMethod.PUT)
    public Model updateSelfProfile(@ModelAttribute UserProfile userProfile, Model model, HttpSession session) {
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if (sessionUserBean == null) {
            model.addAttribute("success", false);
            return model;
        }

        User user = userServices.get(sessionUserBean.getUserId());
        UserProfile originProfile = user.getProfile();
        originProfile.setNickname(userProfile.getNickname());
        originProfile.setGender(userProfile.getGender());
        originProfile.setHeadPic(userProfile.getHeadPic());

        userServices.saveProfile(originProfile);

        model.addAttribute("success", true);

        return model;
    }

    /**
     * Set user self profile secret
     *
     * @param secret
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/self/profile/secret", method = RequestMethod.PUT)
    public Model secretSelfProfile(
            @RequestParam(value = "secret", defaultValue = "false") Boolean secret,
            Model model,
            HttpSession session) {
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if (sessionUserBean == null) {
            model.addAttribute("success", false);
            return model;
        }

        UserProfile userProfile = userServices.getProfile(sessionUserBean.getUserId(), true);
        userProfile.setSecret(secret);
        userServices.saveProfile(userProfile);

        model.addAttribute("success", true);
        return model;
    }
}
