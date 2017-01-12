package cn.lncsa.controller;

import cn.lncsa.data.model.Bulletin;
import cn.lncsa.data.model.User;
import cn.lncsa.services.BulletinServices;
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

    @RequestMapping("/")
    public String bulletins(@RequestParam(value = "page",defaultValue = "0") int page, Model model){
        model.addAttribute("bulletins",bulletinServices.get(new PageRequest(page,10)));
        model.addAttribute("tags",bulletinServices.getAllTags());
        return "bulletins";
    }

    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    public String listByType(@PathVariable("type") String type,@RequestParam(value = "page",defaultValue = "0") int page ,Model model){
        model.addAttribute("currentType",type);
        model.addAttribute("bulletins",bulletinServices.get(type, new PageRequest(page,10, Sort.Direction.DESC, "createDate")));
        model.addAttribute("tags",bulletinServices.getAllTags());
        return "bulletins";
    }

    @RequestMapping(value = "/write",method = RequestMethod.GET)
    public String write(){
        return "writeBulletin";
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String write(@ModelAttribute Bulletin bulletin, Model model, HttpSession session){
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if(sessionUserBean == null) return "redirect:/user/login";

        User user = userServices.get(sessionUserBean.getUserId());
        bulletin.setAuthor(user);
        bulletin.setCreateDate(new Date());

        //if(periodOfValidity != null) bulletin.setPeriodOfValidity(periodOfValidity);
        bulletinServices.save(bulletin);

        model.addAttribute("bulletin",bulletin);

        return "dialogs/bulletinSaved";
    }
}
