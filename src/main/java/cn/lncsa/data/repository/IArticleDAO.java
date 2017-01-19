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

}
