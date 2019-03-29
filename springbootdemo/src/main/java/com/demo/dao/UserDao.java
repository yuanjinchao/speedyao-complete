package com.demo.dao;

import com.demo.po.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by speedyao on 2019/3/28.
 */
@Repository
public interface UserDao extends CrudRepository<User,Long> {
}
