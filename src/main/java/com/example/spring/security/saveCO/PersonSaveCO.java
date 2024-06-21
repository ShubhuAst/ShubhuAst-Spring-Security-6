package com.example.spring.security.saveCO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonSaveCO {

    @NotNull
    String name;

    String contact;

    @NotNull
    String password;

    @NotNull
    @Email
    String email;

    @NotNull
    String role;
}

