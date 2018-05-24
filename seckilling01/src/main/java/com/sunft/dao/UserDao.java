package com.sunft.dao;

import com.sunft.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by sunft on 2018/5/22.
 * 用户Dao
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    User getById(@Param("id") int id);

    @Insert("insert into user(id, name)values(#{id}, #{name})")
    int insert(User user);

}
