package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CredentialsMapper {
    @Select("SELECT * FROM credentials")
    public List<Credentials> getCredentials();

    @Select("SELECT * FROM credentials where credentialid = #{id} ")
    public Credentials getCredentialById(Integer id);

    @Insert("INSERT INTO CREDENTIALS (url,username, key, password, userId) VALUES(#{url},#{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    public int insert(Credentials credential);
}