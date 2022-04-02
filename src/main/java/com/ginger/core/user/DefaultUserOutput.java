package com.ginger.core.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultUserOutput {

    private Long id;

    private String name;

    private String lastName;

    private String username;

    private String email;

    public DefaultUserOutput fromUser(User user) {
        setId(user.getId());
        setName(user.getName());
        setLastName(user.getLastName());
        setUsername(user.getUsername());
        setEmail(user.getEmail());
        return this;
    }
}
