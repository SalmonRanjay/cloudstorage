package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.models.Notes;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NotesMapper {
    
    @Select("SELECT * FROM NOTES")
    public List<Notes> getNotes();

    @Select("SELECT * FROM NOTES where noteid = #{id} ")
    public Notes getNoteById(Integer id);

    @Insert("INSERT INTO NOTES (notetitle,noteDescription, userId ) VALUES(#{notetitle},#{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    public int insert(Notes note);

    @Delete("DELETE FROM NOTES where id= #{id}")
    public int delete(Integer id);

    @Update("UPDATE NOTES SET notetitle = #{title}, noteDescription = #{description} where noteid = #{id}")
    public int updateNote(Integer id,String title, String description);
}