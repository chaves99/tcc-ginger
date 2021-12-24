package com.ginger.core.login;

import java.util.Optional;

import com.ginger.core.user.DefaultUserOutput;
import com.ginger.core.user.User;
import com.ginger.core.user.UserRepository;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequestMapping("login")
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {

    UserRepository userRepository;

    @PostMapping
    ResponseEntity<DefaultUserOutput> login(@RequestBody LoginInput input) {
        Optional<User> user = userRepository
                .findByUsernameAndPassword(input.getUsername(), input.getPassword());
        if (user.isEmpty()) {
            user = userRepository.findByEmailAndPassword(input.getUsername(), input.getPassword());
        }
        if (user.isPresent()) {
            return ResponseEntity.ok(new DefaultUserOutput().fromUser(user.get()));
        }
        return ResponseEntity.notFound().build();
    }

}
