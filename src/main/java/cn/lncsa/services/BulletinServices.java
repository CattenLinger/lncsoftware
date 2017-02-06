package cn.lncsa.services;

import cn.lncsa.data.model.Bulletin;
import cn.lncsa.data.repository.IBulletinDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by cattenlinger on 2017/1/1.
 */
@Service
public class BulletinServices extends BaseServices<Bulletin>{

    private IBulletinDAO bulletinDAO;

    @Autowired
    private void setBulletinDAO(IBulletinDAO bulletinDAO) {
        this.bulletinDAO = bulletinDAO;
        setRepository(bulletinDAO);
    }

    public Page<Bulletin> get(String type, Pageable pageable) {
        return bulletinDAO.getByType(type, pageable);
    }

    public Page<Bulletin> get(Pageable pageable) {
        return bulletinDAO.findAll(pageable);
    }

    @Deprecated
    public Bulletin getLatestOne() {
        return bulletinDAO.findAll(new PageRequest(0, 1, Sort.Direction.DESC, "createDate")).getContent().get(0);
    }

    public List<Bulletin> getAvailableItems(int count) {
        return bulletinDAO.findAll((root, query, cb) -> cb.or(
                        cb.greaterThanOrEqualTo(root.get("periodOfValidity"), new Date()),
                root.get("periodOfValidity").isNull()),
                new PageRequest(
                        0,
                        count,
                        Sort.Direction.DESC,
                        "createDate")).getContent();
    }

    public List<String> getAllTags() {
        return bulletinDAO.getAllTags();
    }
}
