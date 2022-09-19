package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;


@Mapper
public interface FileMapper {
    @Insert("insert into files (filename,contenttype,filesize,userid,filedata) values (#{fileName},#{contentType},#{fileSize},#{user.id},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Delete("delete from files where fileId = #{fileId}")
    void delete(Integer fileId);
}
