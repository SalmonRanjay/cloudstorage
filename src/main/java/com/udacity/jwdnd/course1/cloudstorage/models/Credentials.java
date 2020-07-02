package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Credentials
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {

    private Integer credentialid;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userid;
}