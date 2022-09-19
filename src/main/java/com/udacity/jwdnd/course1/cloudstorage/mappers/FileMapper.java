package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;


@Mapper
public interface FileMapper {

    @Select("select * from files where filename = #{fileName}")
    File select(String fileName);

    @Insert("insert into files (filename,contenttype,filesize,userid,filedata) values (#{fileName},#{contentType},#{fileSize},#{user.id},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Delete("delete from files where fileId = #{fileId}")
    Integer delete(Integer fileId);


}
