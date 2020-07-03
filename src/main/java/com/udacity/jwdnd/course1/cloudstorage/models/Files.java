package com.udacity.jwdnd.course1.cloudstorage.models;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Files
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Files {

    private String filename;
    private String contenttype;
    private String filesize;
    private Integer userId;
    private byte[] filedata;
}