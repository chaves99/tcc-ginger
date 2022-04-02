package com.ginger.core.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserInput {

    private String name;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private UserType userType;

    public User toUser() {
        return User.builder()
                .username(username)
                .name(name)
                .lastName(lastName)
                .password(password)
                .email(email)
                .type(userType)
                .build();
    }

}
