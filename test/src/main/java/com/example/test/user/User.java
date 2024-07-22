package com.example.test.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    private String userId;

    private String pwd;

    private String email;
}