package com.mongoDB.demo.model;

import com.mongoDB.demo.model.Enum.RoleTypes;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Data
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String userName;
    private String password;
    private Set<RoleTypes> roles;

}
