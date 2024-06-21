package com.example.spring.security.saveCO;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonUpdateCO {

    @NotNull
    Long id;
    String name;
    String contact;
    String password;
    String email;
}
