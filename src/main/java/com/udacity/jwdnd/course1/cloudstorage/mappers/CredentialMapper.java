package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CredentialMapper {
    @Insert("insert into CREDENTIALS (url,username,key,password,userid) values (#{url},#{userName},#{key}),#{password},#{userId}")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insert(Credential credential);

    @Update("update CREDENTIALS set url = #{url}, username = #{userName},key=#{key},password=#{password} where  credentialid = #{credentialId}")
    Integer edit(Credential credential);

    @Delete("delete from CREDENTIALS where credentialid = #{credentialId}")
    Integer delete(Integer credentialId);

    @Select("select * from CREDENTIALS where userid = #{userId}")
    List<Credential> selectCredentials(Integer credentialId);

    @Select("select * from CREDENTIALS where credentialid = #{credentialId}")
    Credential selectCredential(Integer credentialId);
}
