package com.ginger.core.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserInput {

    private String name;

    private String username;

    private String password;

    private String email;

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }

}
