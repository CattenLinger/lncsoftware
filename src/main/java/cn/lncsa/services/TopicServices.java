package cn.lncsa.services;

import cn.lncsa.data.model.Article;
import cn.lncsa.data.model.Topic;
import cn.lncsa.data.model.User;
import cn.lncsa.data.repository.IArticleDAO;
import cn.lncsa.data.repository.ITopicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by cattenlinger on 2017/1/7.
 */
@Service
public class TopicServices{

    private ITopicDAO topicDAO;
    private IArticleDAO articleDAO;



    @Autowired
    private void setTopicDAO(ITopicDAO topicDAO){
        this.topicDAO = topicDAO;
    }

    @Autowired
    private void setArticleDAO(IArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public Topic save(Topic topic) {
        return topicDAO.save(topic);
    }

    public Boolean hasTopic(String title){
        return topicDAO.findByTitle(title) != null;
    }

    public List<Topic> mostWeightTopics(Integer count){
        return topicDAO.findAll(new PageRequest(0,count, Sort.Direction.DESC, "weight")).getContent();
    }

    public Page<Topic> getUserCreatedTopic(User user, Pageable pageable){
        return topicDAO.findAll((root, query, cb) -> cb.equal(root.get("creator"),user),pageable);
    }

    public Page<Topic> getUserJoinTopic(User user, Pageable pageable){
        return topicDAO.findAll((root, query, cb) -> cb.equal(root.join("article").get("author"),user),pageable);
    }

    public void delete(Integer tagId) {

    }

    public void taggingArticle(Integer articleId, List<Topic> topicList) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

    }

    public void removeTagFromArticle(Integer articleId, List<Topic> topicList) {

    }

    public void removeAllTagsFromArticle(Integer articleId) {

    }

    public Topic get(Integer tagId) {
        return topicDAO.getOne(tagId);
    }

    public List<Topic> get(List<Integer> topicIds) {
        return topicDAO.findAll(topicIds);
    }

    public List<Topic> getByName(List<String> tagName) {
        return null;
    }

    public List<Topic> queryByArticleId(Integer articleId) {
        return null;
    }

    public Page<Article> queryArticlesUnderTopic(Integer tagId, List<String> status, Pageable pageable) {
        return null;
    }

    public Page<Topic> getAll(Pageable pageable) {
        return topicDAO.findAll(pageable);
    }
}
