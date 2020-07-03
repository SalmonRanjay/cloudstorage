package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.models.Files;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES")
    public List<Files> getFiles();

    @Select("SELECT * FROM FILES where filename = #{filename} ")
    public Files getFileByName(String filename);

    @Insert("INSERT INTO FILES (filename,contenttype,filesize, userId, filedata) VALUES(#{filename},#{contenttype}, #{filesize}, #{userId},#{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    public int insert(Files file);
}