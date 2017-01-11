package cn.lncsa.data.repository;

import cn.lncsa.data.model.Article;
import cn.lncsa.data.model.Topic;
import cn.lncsa.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * Created by catten on 2016/6/12.
 */
public interface IArticleDAO extends IBaseDAO<Article>{
    /**
     * Find all article
     *
     * @param status      what status allow
     * @param pageable
     * @return
     */
    @Query("select a from Article a where a.status in ?1")
    Page<Article> findAll(String[] status, Pageable pageable);

    @Query("select a from Article a join a.topics t where t in ?1 and a.status in ?2")
    Page<Article> findByTopic(Topic topic,String[] status,Pageable pageable);

    @Query("select count(a.id) from Article a join a.author au where au = ?1 and a.status in ?2")
    Integer countUserArticle(User user, String[] status);
}
