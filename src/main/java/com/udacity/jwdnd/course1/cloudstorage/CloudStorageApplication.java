package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
public class CloudStorageApplication implements CommandLineRunner{

    private UserService userService;

    public CloudStorageApplication(UserService userService){
        this.userService = userService;
    }

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}

	@Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20971520); // 20MB
        multipartResolver.setMaxInMemorySize(1048576); // 1MB
        return multipartResolver;
    }

    @Override
    public void run(String... args) throws Exception {
        
        User rj = new User(null, "rj", "salt","password", "rj", "user");
        int userid = userService.createUser(rj);

    }

}
