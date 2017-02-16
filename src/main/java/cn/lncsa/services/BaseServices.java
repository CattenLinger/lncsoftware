package cn.lncsa.services;

import cn.lncsa.data.model.IBaseModel;
import cn.lncsa.data.repository.IBaseDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by cattenlinger on 2017/2/6.
 */
public abstract class BaseServices<M extends IBaseModel<Integer>> {

    protected IBaseDAO<M> repository;

    protected void setRepository(IBaseDAO<M> repository){
        this.repository = repository;
    }

    public M get(Integer id){
        return repository.findOne(id);
    }

    public M get(Specification<M> specification){
        return repository.findOne(specification);
    }

    public M save(M model){
        return repository.save(model);
    }

    public void delete(Integer id){
        repository.delete(id);
    }

    public void delete(M model){
        repository.delete(model);
    }

    public Page<M> findAll(Specification<M> specification, Pageable pageable){
        return repository.findAll(specification,pageable);
    }

    public Page<M> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }
}
