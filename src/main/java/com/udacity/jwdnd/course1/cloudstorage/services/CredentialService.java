package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.exception.GenericException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.User;

import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private CredentialsMapper credentialsMapper;
    private UserMapper userMapper;
    private EncryptionService encryptionService;

    private static final String ENC_KEY = "asdewcasdawegs231sav";

    private String encryptionKey = "";

    public CredentialService(CredentialsMapper credentialsMapper, UserMapper userMapper,
            EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    @PostConstruct
    public void init() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        this.encryptionKey = Base64.getEncoder().encodeToString(key);
    }

    public List<Credentials> getAllCredentials() {
        return credentialsMapper.getCredentials();
    }

    public void save(String username, String password, String url, Principal principal) {
        User user = userMapper.getUser(principal.getName());
        if (user == null)
            throw new GenericException("Unable to Find user information for user:  " + user.getUsername());

        String encryptedPassword = encryptionService.encryptValue(password, this.encryptionKey);

        int result = credentialsMapper
                .insert(new Credentials(null, url, username, this.encryptionKey, encryptedPassword, user.getUserId()));

        if (result != 1)
            throw new GenericException("There was an issue saving Credentials. Unable to Save");
    }

    public Credentials getCredential(Integer id) {
        Credentials creds = credentialsMapper.getCredentialById(id);
        if (creds == null)
            throw new GenericException("Unable to find credentials with Id: " + id);

        creds.setPassword(encryptionService.decryptValue(creds.getPassword(), this.encryptionKey));
        return creds;
    }

    public void deleteeCredential(Integer id) {
        int result = credentialsMapper.delete(id);
        if (result != 1)
            throw new GenericException("Unable to Delete Credentials");
    }

    public void updateCredential(Integer id, String url, String password, String username) {
        Credentials dbCreds = credentialsMapper.getCredentialById(id);
        if (dbCreds == null)
            throw new GenericException("Unable to find credentials with id: " + id);

        int result = credentialsMapper.updateNote(id, url, username, password);

        if (result != 1)
            throw new GenericException("Unable to update Credentials : " + id);
    }
}