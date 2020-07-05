package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CredentialsMapper {
    @Select("SELECT * FROM credentials")
    public List<Credentials> getCredentials();

    @Select("SELECT * FROM credentials where credentialId = #{id} ")
    public Credentials getCredentialById(Integer id);

    @Insert("INSERT INTO CREDENTIALS (url,username, key, password, userId) VALUES(#{url},#{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    public int insert(Credentials credential);


    @Delete("DELETE FROM CREDENTIALS where credentialId= #{id}")
    public int delete(Integer id);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} where credentialId = #{id}")
    public int updateNote(Integer id,String url, String username,String password);
}