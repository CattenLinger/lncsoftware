package cn.lncsa.data.repository;

import cn.lncsa.data.model.Bulletin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by catten on 2016/6/12.
 */
public interface IBulletinDAO extends IBaseDAO<Bulletin> {
    /**
     * Get bulletins directly by type name
     *
     * @param type type name
     * @return a list of bulletin match the type
     */
    @Query("select b from Bulletin b where b.type = ?1 order by b.createDate")
    Page<Bulletin> getByType(String type, Pageable pageable);

    @Query("select distinct b.type, b.shownName from Bulletin b")
    List<Object[]> getAllTags();
}
