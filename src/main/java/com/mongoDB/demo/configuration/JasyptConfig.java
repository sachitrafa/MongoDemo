package com.mongoDB.demo.configuration;

import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Value("${jasypt.encryptor.password}")
    private String password;

    @Bean
    public TextEncryptor textEncryptor(){
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword(password);// Choose an encryption algorithm
        return encryptor;
    }
}
