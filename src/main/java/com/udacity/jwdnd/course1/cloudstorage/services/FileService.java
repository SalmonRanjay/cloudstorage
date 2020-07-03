package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.udacity.jwdnd.course1.cloudstorage.exception.FileExistException;
import com.udacity.jwdnd.course1.cloudstorage.exception.NullFileException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import com.udacity.jwdnd.course1.cloudstorage.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private static Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService){
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public List<Files> fetchAll(){
        return fileMapper.getFiles();
    }

    public void save(MultipartFile file, Principal principal){
        String filename = file.getOriginalFilename();

        Files existingFile = fileMapper.getFileByName(filename);

        if(file.getSize() == -1){
            throw new NullFileException("Sorry File missing");
        }

        if(existingFile != null){
            throw new FileExistException("File name already exists");
        }
        /*
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
        */
        User user = userService.getUser(principal.getName());
        try {
            Files newFile = new Files(filename, file.getContentType(), String.valueOf(file.getSize()), user.getUserId(),
                    compressBytes(file.getBytes()));

            fileMapper.insert(newFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }

    // compress the image bytes before storing it in the database
    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    private byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
            LOGGER.error("IO ERROR", ioe);
        } catch (DataFormatException e) {
            LOGGER.error("Error Compressing Image", e);
        }
        return outputStream.toByteArray();
    }
    
}