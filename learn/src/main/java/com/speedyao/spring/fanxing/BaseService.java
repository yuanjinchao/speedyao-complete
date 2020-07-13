package com.speedyao.spring.fanxing;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseService<T> {
    @Autowired
    private IDao<T> dao;

    public IDao<T> getDao() {
        return dao;
    }

    public void setDao(IDao<T> dao) {
        this.dao = dao;
    }

    public void save(T t){
        dao.save(t);
    }
}
