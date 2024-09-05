package com.mongoDB.demo.model;

import com.mongoDB.demo.model.Enum.RoleTypes;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String userName;
    private String password;
    private Set<RoleTypes> roles;

}
