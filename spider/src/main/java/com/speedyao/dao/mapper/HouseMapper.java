package com.speedyao.dao.mapper;

import com.speedyao.dao.model.House;

import java.util.List;

public interface HouseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(House record);

    int insertSelective(House record);

    House selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(House record);

    int updateByPrimaryKey(House record);
    List<House> selectSelective(House record);
}