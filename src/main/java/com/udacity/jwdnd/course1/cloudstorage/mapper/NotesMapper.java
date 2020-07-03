package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.models.Notes;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NotesMapper {
    
    @Select("SELECT * FROM NOTES")
    public List<Notes> getNotes();

    @Select("SELECT * FROM NOTES where noteid = #{id} ")
    public Notes getCredentialById(Integer id);

    @Insert("INSERT INTO NOTES (notetitle,noteDescription, userId ) VALUES(#{notetitle},#{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    public int insert(Notes note);
}