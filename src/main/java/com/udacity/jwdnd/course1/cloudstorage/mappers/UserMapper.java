package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS WHERE username = #{userName}")
    User getUser(String userName);

    @Insert("insert into users (username,salt,password,firstname,lastname) " +
            "values (#{userName},#{password},#{salt},#{firstName},#{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertUser(User user);

    @Select("SELECT * FROM USERS")
    List<User> getUsers();

    @Update("update users set username = #{userName} ," +
            "salt=#{salt},password=#{password},firstname=#{firstName}," +
            "lastname=#{lastName}) where id = #{id} ")
    void updateUser(User user);

    @Delete("DELETE from users where id = #{id}")
    void deleteUser(Integer id);
}
