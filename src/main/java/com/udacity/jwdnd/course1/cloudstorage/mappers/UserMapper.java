package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    final String getAll = "SELECT * FROM USERS WHERE username = #{userName}";

    @Select(getAll)
    User getUser(String userName);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> selectFiles(Integer userId);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> selectNotes(Integer userId);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{userName}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer insert(User user);
}
