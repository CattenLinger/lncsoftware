package cn.lncsa.controller;

import cn.lncsa.data.model.Topic;
import cn.lncsa.services.TopicServices;
import cn.lncsa.services.UserServices;
import cn.lncsa.view.SessionUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by cattenlinger on 2017/1/7.
 */
@Controller
@RequestMapping("/topic")
public class TopicController {

    private TopicServices topicServices;
    private UserServices userServices;

    @Autowired
    private void setTopicServices(TopicServices topicServices) {
        this.topicServices = topicServices;
    }

    @Autowired
    private void setUserServices(UserServices userServices) {
        this.userServices = userServices;
    }

    /**
     * Create a topic
     *
     * @param topic
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Model create(
            @ModelAttribute Topic topic,
            Model model,
            HttpSession session) {
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("session_user");
        if (sessionUserBean == null || topicServices.hasTopic(topic.getTitle())) {
            model.addAttribute("success", false);
            return model;
        }

        topic.setCreateDate(new Date());
        topic.setCreator(userServices.get(sessionUserBean.getUserId()));
        topic.setWeight(1000);

        topicServices.save(topic);
        model.addAttribute("success", true);
        return model;
    }

    /**
     * Get hot topics
     *
     * @param count
     * @return
     */
    @RequestMapping(value = "/hot", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Topic> hotTopics(@RequestParam(defaultValue = "10") int count) {
        if (count > 30) count = 30;
        return topicServices.mostWeightTopics(count);
    }

    /**
     *
     * Get topics
     *
     * @param page
     * @param pageCount
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Topic> get(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageCount", defaultValue = "10") int pageCount) {

        return topicServices.get(new PageRequest(page, pageCount));
    }

    /**
     *
     * Delete a topic
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Model delete(@PathVariable("id") int id, Model model){
        topicServices.delete(id);
        model.addAttribute("success",true);
        return model;
    }

    /**
     *
     * Get a topic
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Model get(@PathVariable("id") int id, Model model){
        Topic topic = topicServices.get(id);
        if(topic == null) return null;
        model.addAttribute("creator",topic.getCreator().getId());
        model.addAttribute("data",topic);
        return model;
    }

}
