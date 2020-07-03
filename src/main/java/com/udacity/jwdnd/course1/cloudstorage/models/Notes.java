package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Notes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notes {

    private Integer noteid;
    private String notetitle;
    private String noteDescription;
    private Integer userId;
    
}

