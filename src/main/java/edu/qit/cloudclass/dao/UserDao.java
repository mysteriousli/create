package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author nic
 * @version 1.0
 */
@Mapper
public interface UserDao {

    @Insert("insert into user values (#{user.id},#{user.name},#{user.password},#{user.createTime},#{user.identity},#{user.email});")
    void register(@Param("user") User user);

    @Select("select * from user where name = #{name};")
    User login(@Param("name") String name);

    @Select("select * from user where id = #{id};")
    User getInfo(@Param("id") String id);
}
