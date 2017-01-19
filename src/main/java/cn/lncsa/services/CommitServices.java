package cn.lncsa.services;

import cn.lncsa.data.model.Commit;
import cn.lncsa.data.repository.ICommitDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by cattenlinger on 2017/1/16.
 */
@Service
public class CommitServices {
    private ICommitDAO commitDAO;

    @Autowired
    private void setCommitDAO(ICommitDAO commitDAO) {
        this.commitDAO = commitDAO;
    }

    public void save(Commit commit) {
        commitDAO.save(commit);
    }

    public Commit get(Integer commitId) {
        return commitDAO.getOne(commitId);
    }

    public Page<Commit> getCommitList(Integer articleId, Pageable pageable) {
        return commitDAO.findAll((root, query, cb) -> cb.equal(root.get("targetArticle").get("id"), articleId), pageable);
    }
}
