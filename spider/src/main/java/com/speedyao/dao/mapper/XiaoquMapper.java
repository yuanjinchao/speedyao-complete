package com.speedyao.dao.mapper;

import com.speedyao.dao.model.Xiaoqu;

import java.util.List;

public interface XiaoquMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Xiaoqu record);

    int insertSelective(Xiaoqu record);

    Xiaoqu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Xiaoqu record);

    int updateByPrimaryKey(Xiaoqu record);
    List<Xiaoqu> selectAll();
}