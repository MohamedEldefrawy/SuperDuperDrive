package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("select * from notes where userid = #{userId}")
    List<Note> selectNotes(Integer userId);

    @Insert("insert into Notes(notetitle,notedescription,userid) values (#{title},#{description},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insert(Note note);

    @Update("update notes set notetitle = #{title}, notedescription = #{description} where  noteid = #{noteId}")
    Integer edit(Note note);

    @Delete("delete from notes where noteid = #{noteId}")
    Integer delete(Integer noteId);

}
