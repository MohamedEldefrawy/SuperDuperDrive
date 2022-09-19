package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    final String getAll = "SELECT * FROM USERS WHERE username = #{userName}";

    @Select(getAll)
    @Results(value = {
            @Result(property = "userId", javaType = Integer.class, column = "userid"),
            @Result(property = "userName", javaType = String.class, column = "username"),
            @Result(property = "salt", javaType = String.class, column = "salt"),
            @Result(property = "password", javaType = String.class, column = "password"),
            @Result(property = "firstName", javaType = String.class, column = "firstname"),
            @Result(property = "lastName", javaType = String.class, column = "lastname"),
            @Result(property = "files", javaType = List.class, many = @Many(select = "selectFiles"),column = "userid")
    })
    User getUser(String userName);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    @Results(value = {
            @Result(property = "fileId", javaType = Integer.class, column = "fileId"),
            @Result(property = "fileName", javaType = String.class, column = "filename"),
            @Result(property = "contentType", javaType = String.class, column = "contenttype"),
            @Result(property = "fileSize", javaType = String.class, column = "filesize"),
            @Result(property = "fileData", javaType = byte[].class, column = "filedata")
    })
    List<File> selectFiles(Integer userId);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{userName}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer insert(User user);
}
