package cn.lncsa.services;

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
import java.util.List;

/**
 * Created by cattenlinger on 2017/1/7.
 */
@Service
public class TopicServices extends BaseServices<Topic> {

    private ITopicDAO topicDAO;
    private IArticleDAO articleDAO;

    @Autowired
    private void setTopicDAO(ITopicDAO topicDAO) {
        this.topicDAO = topicDAO;
        setRepository(topicDAO);
    }

    @Autowired
    private void setArticleDAO(IArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public Boolean hasTopic(String title) {
        return topicDAO.findByTitle(title) != null;
    }

    public List<Topic> mostWeightTopics(Integer count) {
        return findAll(new PageRequest(0, count, Sort.Direction.DESC, "weight")).getContent();
    }

    public Page<Topic> getUserCreatedTopic(User user, Pageable pageable) {
        return findAll((root, query, cb) -> cb.equal(root.get("creator"), user), pageable);
    }

    public Page<Topic> getUserJoinTopic(User user, Pageable pageable) {
        return findAll((root, query, cb) -> cb.equal(root.join("article").get("author"), user), pageable);
    }

    public List<Topic> get(List<Integer> topicIds) {
        return topicDAO.findAll(topicIds);
    }

    public Page<Topic> get(Pageable pageable) {
        return findAll(pageable);
    }

    public long getUserTopicCount(User user) {
        return topicDAO.count((root, query, cb) -> cb.equal(root.get("creator"),user));
    }
}
