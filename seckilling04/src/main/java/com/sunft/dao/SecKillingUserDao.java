package com.sunft.dao;

import com.sunft.domain.SecKillingUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by sunft on 2018/5/27.
 * 秒杀用户Dao接口
 */
@Mapper
public interface SecKillingUserDao {

    @Select("select * from seckilling_user where id = #{id}")
    public SecKillingUser getById(@Param("id") long id);

}
