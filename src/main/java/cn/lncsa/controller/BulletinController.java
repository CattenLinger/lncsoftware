package cn.lncsa.controller;

import cn.lncsa.data.model.Bulletin;
import cn.lncsa.data.model.User;
import cn.lncsa.services.BulletinServices;
import cn.lncsa.services.UserServices;
import cn.lncsa.view.SessionUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by cattenlinger on 2017/1/1.
 */
@Controller
@RequestMapping("/bulletin")
public class BulletinController {

    private BulletinServices bulletinServices;

    private UserServices userServices;

    @Autowired
    private void setBulletinServices(BulletinServices bulletinServices){
        this.bulletinServices = bulletinServices;
    }

    @Autowired
    private void setUserServices(UserServices userServices) {
        this.userServices = userServices;
    }

    /**
     *
     * Get bulletins
     *
     *
     *
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Model get(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "pageCount") int pageCount,
            Model model)
    {
        if(pageCount > 20) pageCount = 20;
        model.addAttribute("bulletins",bulletinServices.get(new PageRequest(page,pageCount)));
        return model;
    }

    /**
     *
     * get all bulletin tags
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/tags",method = RequestMethod.GET)
    public Model tags(Model model){
        model.addAttribute("tags",bulletinServices.getAllTags());
        return model;
    }

    /**
     *
     * Get a bulletin
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Bulletin get(@PathVariable("id") int id){
        return bulletinServices.get(id);
    }

    /**
     *
     * Update a bulletin
     *
     * @param bulletin
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Model update(
            @ModelAttribute Bulletin bulletin,
            Model model,
            HttpSession session){
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if(sessionUserBean == null){
            model.addAttribute("success",false);
            return model;
        }

        Integer id = bulletin.getId();
        if(id == null) {
            model.addAttribute("success",false);
            return model;
        }

        bulletin.setCreateDate(new Date());
        bulletinServices.save(bulletin);
        model.addAttribute("success",true);
        return model;
    }

    /**
     *
     * Get bulletins by type
     *
     * @param type
     * @param page
     * @return
     */
    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    public Page<Bulletin> getByType(
            @PathVariable("type") String type,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "pageCount",defaultValue = "10") int pageCount)
    {
        return bulletinServices.get(type,new PageRequest(
                page,
                pageCount,
                Sort.Direction.DESC,
                "date"));
    }

    /**
     *
     * Create a bulletin
     *
     * @param bulletin
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/write", method = RequestMethod.PUT)
    public Model create(@ModelAttribute Bulletin bulletin, Model model, HttpSession session){
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if(sessionUserBean == null){
            model.addAttribute("success",false);
            return model;
        }

        User user = userServices.get(sessionUserBean.getUserId());
        bulletin.setAuthor(user);
        bulletin.setCreateDate(new Date());

        bulletinServices.save(bulletin);

        model.addAttribute("success",true);

        return model;
    }
}
