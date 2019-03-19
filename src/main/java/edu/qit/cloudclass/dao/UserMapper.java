package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.User;
import org.apache.ibatis.annotations.*;

/**
 * @author nic
 * @version 1.0
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user(id, name, password, createTime, identity, email) " +
            "values (#{user.id},#{user.name},#{user.password},#{user.createTime},#{user.identity},#{user.email});")
    void register(@Param("user") User user);

    @Select("select * from user where name = #{name};")
    User login(@Param("name") String name);

    @Update("update user set taken = #{taken} where id = #{id};")
    void registerAutoLogin(@Param("id") String id,@Param("taken") String taken);

    @Select("select * from user where taken = #{taken}")
    User autoLogin(@Param("taken") String taken);
}
