package com.example.security;
import com.example.security.properties.BaseURLProperties;
import com.example.security.properties.AdminProperties;
import com.example.security.properties.RsaKeyProperties;
import com.example.security.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties({RsaKeyProperties.class, BaseURLProperties.class, AdminProperties.class})
@SpringBootApplication
public class YorkshireArboretum {

    public static void main(String[] args) {
        SpringApplication.run(YorkshireArboretum.class, args);
    }

    @Bean
    public CommandLineRunner makeInitialUserRunner(UserService userService, AdminProperties adminProperties) {
        return args -> {
            userService.createInitial(adminProperties.getInitialUsername(), adminProperties.getInitialPassword());
        };
    }

}
